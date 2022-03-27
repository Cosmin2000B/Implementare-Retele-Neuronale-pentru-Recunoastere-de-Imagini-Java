package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire;

import cosmin.functiiActivare.FunctieActivare;
import cosmin.functiiActivare.Softmax;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.StratNeuronal;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratAscuns;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratDeIntrare;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratNeuronalLiniar;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.FunctieDeCost;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Ionescu Cosmin
 */
public class StratDeIesire extends StratNeuronalLiniar implements StratNeuronal
{
    // setul de valori pe care vrem sa le
    // obtinem
    private ArrayList<Double> valoriDorite;

    private StratNeuronalLiniar stratAnterior;
    private double eroareaRetelei;

    // la nivel de strat
    private FunctieActivare functieActivare;

    // metoda preferata de calcul a erorii de clasificare
    private FunctieDeCost functieDeCost;

    // --------- Constructori -------------------

    public StratDeIesire(int numarNeuroni)
    {
        this.setNumarNeuroni(numarNeuroni);
        this.setNeuroni(new ArrayList<>(this.getNumarNeuroni()));

        for(int i = 0; i < this.getNumarNeuroni(); ++i)
        {
            Neuron neuron = new Neuron();
            this.getNeuroni().add(neuron);
        }
    }

    public StratDeIesire(@NotNull List<Double> valoriDorite)
    {
        this.setNumarNeuroni(valoriDorite.size());
        this.setNeuroni(new ArrayList<>(this.getNumarNeuroni()));

        for(int i = 0; i < this.getNumarNeuroni(); ++i)
        {
            Neuron neuron = new Neuron();
            this.getNeuroni().add(neuron);
        }

        this.valoriDorite = new ArrayList<>(valoriDorite.size());

        for(Double valoare: valoriDorite)
            this.valoriDorite.add(valoare);
    }

    public StratDeIesire(StratNeuronalLiniar stratAnterior)
    {
        this.stratAnterior = stratAnterior;
        this.setNeuroni(new ArrayList<>());
    }

    public StratDeIesire(int numarNeuroni, StratNeuronalLiniar stratAnterior, FunctieActivare functieActivare)
    {
        this.setNumarNeuroni(numarNeuroni);
        this.setNeuroni(new ArrayList<>(this.getNumarNeuroni()));

        for(int i = 0; i < this.getNumarNeuroni(); ++i)
        {
            Neuron neuron = new Neuron(functieActivare);
            this.getNeuroni().add(neuron);
        }

        this.stratAnterior = stratAnterior;
        this.functieActivare = functieActivare;
    }

    // ------------- Sfarsit Constructori --------------------------

    @Override
    public void calculeazaIesiri()
    {
        if(this.functieActivare instanceof Softmax)
        {
            for(Neuron neuron: this.getNeuroni())
                neuron.
                        setValoareIesire(this.
                        functieActivare.valoareFunctie(neuron.getValoareIntrare()));
        }
        else
        {
            for (Neuron neuron : this.getNeuroni())
                neuron.calculeazaIesire();
        }
    }

    @Override
    public void reseteazaPonderi()
    {
        for(Neuron neuron: this.getNeuroni())
        {
            ArrayList<Sinapsa> sinapse = neuron.getSinapseIntrare();
            for(Sinapsa sinapsa: sinapse)
                sinapsa.setPondere(ThreadLocalRandom.current().nextDouble());
        }
    }

    //TODO greul ramane la nivel de retea

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
        }
    }

    // TODO testeaza metoda
    public Sinapsa gasesteSinapsaIntrare(Neuron neuronEmitent, @NotNull Neuron neuronDestinatar)
    {
        for(Sinapsa sinapsa: neuronDestinatar.getSinapseIntrare())
        {
            if(sinapsa.getNeuronEmitent() == neuronEmitent)
                return sinapsa;
        }
        return null;
    }

    /**
     *
     */
    public void calculeazaEroareaRetelei()
    {
        this.eroareaRetelei = this.functieDeCost.calculeazaEroarea(this);
    }

    // ----------------- Setteri si Getteri -------------------------

    public ArrayList<Double> getValoriDorite() {
        return valoriDorite;
    }

    public void setValoriDorite(ArrayList<Double> valoriDorite) {
        this.valoriDorite = valoriDorite;
    }

    public StratNeuronalLiniar getStratAnterior() {
        return stratAnterior;
    }

    public void setStratAnterior(StratNeuronalLiniar stratAnterior)
    {
        if(stratAnterior instanceof StratDeIesire)
            throw new IllegalArgumentException("Un strat de iesire nu poate avea drept" +
                    " strat anterior un strat de iesire!");

        if(stratAnterior instanceof StratAscuns)
        {
            this.stratAnterior = stratAnterior;

            if(((StratAscuns) stratAnterior).getStratUlterior() == this)
                return;

            ((StratAscuns) stratAnterior).setStratUlterior(this);
        }

        if(stratAnterior instanceof StratDeIntrare)
        {
            this.stratAnterior = stratAnterior;

            if(((StratDeIntrare) stratAnterior).getStratUlterior() == this)
                return;

            ((StratDeIntrare) stratAnterior).setStratUlterior(this);
        }
    }

    public double getEroareaRetelei()
    {
        if(this.eroareaRetelei == 0d)
            this.calculeazaEroareaRetelei();

        return eroareaRetelei;
    }

    public void setEroareaRetelei(double eroareaRetelei) {
        this.eroareaRetelei = eroareaRetelei;
    }

    public FunctieActivare getFunctieActivare() {
        return functieActivare;
    }

    public void setFunctieActivare(FunctieActivare functieActivare)
    {
        this.functieActivare = functieActivare;

        /*
        setam functia de activare decisa la nivel de strat pentru
        fiecare neuron
        */
        for(Neuron neuron: this.getNeuroni())
            neuron.setFunctieActivare(this.functieActivare);
    }

    public FunctieDeCost getFunctieDeCost() {
        return functieDeCost;
    }

    public void setFunctieDeCost(FunctieDeCost functieDeCost) {
        this.functieDeCost = functieDeCost;
    }
}
