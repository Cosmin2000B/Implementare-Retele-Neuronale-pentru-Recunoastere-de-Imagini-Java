package cosmin.straturiNeuronale.straturiNeuronaleLiniare;

import cosmin.functiiActivare.FunctieActivare;
import cosmin.functiiActivare.ReLU;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.StratNeuronal;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @author Ionescu Cosmin
 */
public class StratAscuns extends StratNeuronalLiniar implements StratNeuronal
{

    private StratNeuronalLiniar stratAnterior;
    private StratNeuronalLiniar stratUlterior;

    private FunctieActivare functieActivare;

    public StratAscuns(int numarNeuroni)
    {
        this.setNumarNeuroni(numarNeuroni);
        this.setNeuroni(new ArrayList<>(numarNeuroni));

        for(int i = 0; i < numarNeuroni; ++i)
        {
            if(this.getFunctieActivare() != null)
            // s-a stabilit functia de activare la nivel de strat
                this.getNeuroni().add(new Neuron(this.functieActivare));
            else
            {
                // nu s-a stabilit functia la nivel de strat, aplicam varianta default
                this.functieActivare = new ReLU();
                this.getNeuroni().add(new Neuron(this.functieActivare));
            }
        }
    }

    public StratAscuns(int numarNeuroni, FunctieActivare functieActivare)
    {
        this.setNumarNeuroni(numarNeuroni);
        this.setNeuroni(new ArrayList<>(numarNeuroni));
        this.functieActivare = functieActivare;

        for(int i = 0; i < numarNeuroni; ++i)
            this.getNeuroni().add(new Neuron(functieActivare));
    }

    @Override
    public void calculeazaIesiri()
    {
        for(Neuron neuron: this.getNeuroni())
            neuron.calculeazaIesire();
    }

    @Override
    public void reseteazaPonderi()
    {
        for(Neuron neuron: this.getNeuroni())
        {
            ArrayList<Sinapsa> sinapse = neuron.getSinapseIntrare();
            for(Sinapsa sinapsa: sinapse)
                sinapsa.setPondere(ThreadLocalRandom.current().nextDouble());

            sinapse = neuron.getSinapseIesire();
            for(Sinapsa sinapsa: sinapse)
                sinapsa.setPondere(ThreadLocalRandom.current().nextDouble());
        }
    }

    //TODO de vazut si-n cazul in care se schimba stratul dens existent...
    /**
     *    In concordanta cu directia de parcurgere a retelei in cadrul procesului de
     *  propagare, un strat neuronal va stabilii sinapse numai cu stratul sau ulterior.
     */
    public void stabilesteStratDens()
    {
        if(this.stratUlterior == null)
            throw new IllegalStateException(" Stratul ulterior este null!");

        if(this.stratUlterior.getNeuroni().isEmpty())
            throw new IllegalStateException(" Stratul ulterior este gol!");

        if(this.stratUlterior instanceof StratAscuns)
        {
            for(Neuron neuronEmitent: this.getNeuroni())
            {
                for(Neuron neuronDestinatar: (((StratAscuns) this.stratUlterior).getNeuroni()))
                    neuronEmitent.adaugaSinapsaIesire(neuronDestinatar);
            }
        }
        else if(this.stratUlterior instanceof StratDeIesire)
        {
            for(Neuron neuronEmitent: this.getNeuroni())
            {
                for(Neuron neuronDestinatar: (((StratDeIesire) this.stratUlterior).getNeuroni()))
                    neuronEmitent.adaugaSinapsaIesire(neuronDestinatar);
            }
        }
    }

    /**
     *
     * @param valoriSinapse
     */
    public void stabilesteStratDens(ArrayList<Double> valoriSinapse)
    {
        if(this.stratUlterior == null)
            throw new IllegalStateException(" Stratul ulterior este null!");

        if(this.stratUlterior.getNeuroni().isEmpty())
            throw new IllegalStateException(" Stratul ulterior este gol!");

        // daca dimensiunea vectorului de valori dorite este diferita de
        // numarul de sinapse necesare
        if(valoriSinapse.size() !=
                this.getNumarNeuroni() * this.stratUlterior.getNumarNeuroni())
            throw new IllegalArgumentException(" Dimensiunea listei de valori dorite" +
                    "difera de produsul numarului de neuroni de pe straturi!");

        int  i = 0;

        if(this.stratUlterior instanceof StratAscuns)
        {
            for(Neuron neuronEmitent: this.getNeuroni())
            {
                for(Neuron neuronDestinatar: (((StratAscuns) this.stratUlterior).getNeuroni()))
                    neuronEmitent.adaugaSinapsaIesire(neuronDestinatar, valoriSinapse.get(i++));
            }
        }
        else if(this.stratUlterior instanceof StratDeIesire)
        {
            for(Neuron neuronEmitent: this.getNeuroni())
            {
                for(Neuron neuronDestinatar: (((StratDeIesire) this.stratUlterior).getNeuroni()))
                    neuronEmitent.adaugaSinapsaIesire(neuronDestinatar, valoriSinapse.get(i++));
            }
        }
    }

    public Sinapsa gasesteSinapsaIntrare(Neuron neuronEmitent, @NotNull Neuron neuronDestinatar)
    {
        for(Sinapsa sinapsa: neuronDestinatar.getSinapseIntrare())
        {
            if(sinapsa.getNeuronEmitent() == neuronEmitent)
                return sinapsa;
        }
        return null;
    }

    public Sinapsa gasesteSinapsaIesire(@NotNull Neuron neuronEmitent, Neuron neuronDestinatar)
    {
        for(Sinapsa sinapsa: neuronEmitent.getSinapseIesire())
        {
            if(sinapsa.getNeuronDestinatar() == neuronDestinatar)
                return sinapsa;
        }
        return null;
    }

    @Override
    public void actualizeazaPonderi(double rataInvatare)
    {
        for(Neuron neuron: this.getNeuroni())
        {
            neuron.getBias().actualizeazaPondere(rataInvatare);

            for(Sinapsa sinapsa: neuron.getSinapseIntrare())
                sinapsa.actualizeazaPondere(rataInvatare);
        }
    }

    @Override
    public void actualizeazaPonderi(double rataInvatare, double inertie)
    {
        for(Neuron neuron: this.getNeuroni())
        {
            neuron.getBias().actualizeazaPondere(rataInvatare, inertie);

            for(Sinapsa sinapsa: neuron.getSinapseIntrare())
                sinapsa.actualizeazaPondere(rataInvatare, inertie);

            // resetam eroarea acumulata in neuron
            neuron.setEroareNeuron(0d);
        }
    }

    // ------------- Setteri si Getteri ------------------------

    public StratNeuronalLiniar getStratAnterior() {
        return stratAnterior;
    }

    public void setStratAnterior(StratNeuronalLiniar stratAnterior)
    {
        if(stratAnterior instanceof StratDeIesire)
            throw new IllegalArgumentException("Un strat ascuns nu poate avea drept strat" +
                    " anterior un strat de iesire!");

        if(stratAnterior instanceof StratDeIntrare)
        {
            this.stratAnterior = stratAnterior;

            if(((StratDeIntrare) stratAnterior).getStratUlterior() == this)
                return;

            ((StratDeIntrare) stratAnterior).setStratUlterior(this);
        }

        if(stratAnterior instanceof StratAscuns)
        {
            this.stratAnterior = stratAnterior;

            if(((StratAscuns) stratAnterior).getStratUlterior() == this)
                return;

            ((StratAscuns) stratAnterior).setStratUlterior(this);
        }
    }

    public StratNeuronalLiniar getStratUlterior() {
        return stratUlterior;
    }

    public void setStratUlterior(StratNeuronalLiniar stratUlterior)
    {
        if(stratUlterior instanceof StratDeIntrare)
            throw new IllegalArgumentException("Un strat ascuns nu poate avea drept strat" +
                    " ulterior un strat de intrare!");

        if(stratUlterior instanceof StratAscuns)
        {
            this.stratUlterior = stratUlterior;

            if(((StratAscuns) stratUlterior).getStratAnterior() == this)
                return;

            ((StratAscuns) stratUlterior).setStratAnterior(this);
        }

        if(stratUlterior instanceof StratDeIesire)
        {
            this.stratUlterior = stratUlterior;

            if(((StratDeIesire) stratUlterior).getStratAnterior() == this)
                return;

            ((StratDeIesire) stratUlterior).setStratAnterior(this);
        }
    }

    public FunctieActivare getFunctieActivare() {
        return functieActivare;
    }

    public void setFunctieActivare(FunctieActivare functieActivare) {
        this.functieActivare = functieActivare;
    }
}
