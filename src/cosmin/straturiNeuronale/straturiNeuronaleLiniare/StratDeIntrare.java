package cosmin.straturiNeuronale.straturiNeuronaleLiniare;

import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Ionescu Cosmin
 *   Stratul de intrare este conceput in aceasta aplicatie, mai degraba,
 *  drept o interfata intre valorile de intrare propuse de utilizator si
 *  structura unei retele neuronale.
 *   Asadar, neuronii de pe acest strat nu trebuie vazuti drept unitati de
 *  calcul, ci ca o "poarta" de propagare a intrarilor in restul retelei.
 */
public class StratDeIntrare extends StratNeuronalLiniar
{
    private StratNeuronalLiniar stratUlterior;

    public StratDeIntrare(int numarNeuroni)
    {
        this.setNumarNeuroni(numarNeuroni);
        this.setNeuroni(new ArrayList<>(numarNeuroni));

        for(int i = 0; i < numarNeuroni; ++i)
            this.getNeuroni().add(new Neuron());
    }

    /**
     *
     * @param valoriIesire
     */
    public StratDeIntrare(@NotNull List<Double> valoriIesire)
    {
        // numarul de neuroni de pe stratul de intrare
        // trebuie sa coincida cu marimea input-ului
        this.setNumarNeuroni(valoriIesire.size());
        this.setNeuroni(new ArrayList<>(this.getNumarNeuroni()));

        for(Double valoareIesire: valoriIesire)
        {
            Neuron neuron = new Neuron();
            neuron.setValoareIesire(valoareIesire);
            this.getNeuroni().add(neuron);
        }
    }

    /**
     *
     * @param valoriIesire
     * @param stratUlterior
     */
    public StratDeIntrare(@NotNull List<Double> valoriIesire, StratNeuronalLiniar stratUlterior)
    {
        this.setNumarNeuroni(valoriIesire.size());
        this.setNeuroni(new ArrayList<>(this.getNumarNeuroni()));

        for(Double valoareIesire: valoriIesire)
        {
            Neuron neuron = new Neuron();
            neuron.setValoareIesire(valoareIesire);
            this.getNeuroni().add(neuron);
        }

        this.stratUlterior = stratUlterior;
    }

    /**
     *
     * @param indexNeuron
     */
    public void stabilireSinapseIesireNeuron(int indexNeuron)
    {
        //TODO o formula agreabila
    }

    public void reseteazaPonderi()
    {
        for(Neuron neuron: this.getNeuroni())
        {
            ArrayList<Sinapsa> sinapse = neuron.getSinapseIesire();
            for(Sinapsa sinapsa: sinapse)
                sinapsa.setPondere(ThreadLocalRandom.current().nextDouble());
        }
    }

    /**
     *    In concordanta cu directia de parcurgere a retelei in cadrul procesului de
     *  propagare, un strat neuronal va stabilii sinapse numai cu stratul sau ulterior.
     */
    //TODO ceva gresit aici...vezi emitent->destinatar
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

    // ---------------------- Setteri si Getteri ------------------------

    public StratNeuronalLiniar getStratUlterior() {
        return stratUlterior;
    }

    public void setStratUlterior(StratNeuronalLiniar stratUlterior) {
        this.stratUlterior = stratUlterior;
    }

}
