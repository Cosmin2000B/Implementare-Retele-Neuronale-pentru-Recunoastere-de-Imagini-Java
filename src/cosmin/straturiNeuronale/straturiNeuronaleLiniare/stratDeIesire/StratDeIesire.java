package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire;

import cosmin.functiiActivare.FunctieActivare;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.StratNeuronal;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratNeuronalLiniar;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.FunctieDeCost;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//TODO Constructori si restul...

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
        //TODO separat pt softmax
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

    public void setStratAnterior(StratNeuronalLiniar stratAnterior) {
        this.stratAnterior = stratAnterior;
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

    public void setFunctieActivare(FunctieActivare functieActivare) {
        this.functieActivare = functieActivare;
    }

    public FunctieDeCost getFunctieDeCost() {
        return functieDeCost;
    }

    public void setFunctieDeCost(FunctieDeCost functieDeCost) {
        this.functieDeCost = functieDeCost;
    }
}
