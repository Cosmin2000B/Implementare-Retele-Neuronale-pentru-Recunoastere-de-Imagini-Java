package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire;

import cosmin.functiiActivare.FunctieActivare;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.StratNeuronal;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.FunctieDeCost;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//TODO Constructori si restul...

/**
 * @author Ionescu Cosmin
 */
public class StratDeIesire implements StratNeuronal
{
    private ArrayList<Neuron> neuroni;

    // setul de valori pe care vrem sa le
    // obtinem
    private ArrayList<Double> valoriDorite;

    private StratNeuronal stratAnterior;

    private int numarNeuroni;
    private double eroareaRetelei;

    // la nivel de strat
    private FunctieActivare functieActivare;

    // metoda preferata de calcul a erorii de clasificare
    private FunctieDeCost functieDeCost;

    public StratDeIesire(int numarNeuroni)
    {
        this.numarNeuroni = numarNeuroni;
        this.neuroni = new ArrayList<>(this.numarNeuroni);

        for(int i = 0; i < this.numarNeuroni; ++i)
        {
            Neuron neuron = new Neuron();
            this.neuroni.add(neuron);
        }
    }

    public StratDeIesire(@NotNull List<Double> valoriDorite)
    {
        this.numarNeuroni = valoriDorite.size();
        this.neuroni = new ArrayList<>(this.numarNeuroni);

        for(int i = 0; i < this.numarNeuroni; ++i)
        {
            Neuron neuron = new Neuron();
            this.neuroni.add(neuron);
        }

        this.valoriDorite = new ArrayList<>(valoriDorite.size());

        for(Double valoare: valoriDorite)
            this.valoriDorite.add(valoare);
    }

    public StratDeIesire(StratNeuronal stratAnterior)
    {
        this.stratAnterior = stratAnterior;
        this.neuroni = new ArrayList<>();
    }

    public StratDeIesire(int numarNeuroni, StratNeuronal stratAnterior, FunctieActivare functieActivare)
    {
        this.numarNeuroni = numarNeuroni;
        this.neuroni = new ArrayList<>(this.numarNeuroni);

        for(int i = 0; i < this.numarNeuroni; ++i)
        {
            Neuron neuron = new Neuron(functieActivare);
            this.neuroni.add(neuron);
        }

        this.stratAnterior = stratAnterior;
        this.functieActivare = functieActivare;
    }

    @Override
    public void calculeazaIesiri()
    {
        for(Neuron neuron: neuroni)
            neuron.calculeazaIesire();
    }

    @Override
    public void reseteazaPonderi()
    {
        for(Neuron neuron: neuroni)
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
        for(Neuron neuron: this.neuroni)
        {
            neuron.getBias().actualizeazaPondere(rataInvatare);

            for(Sinapsa sinapsa: neuron.getSinapseIntrare())
                sinapsa.actualizeazaPondere(rataInvatare);
        }
    }

    // ------ modificare structura strat -------------------

    /**
     *
     */
    public void adaugaNeuron()
    {
        Neuron neuron = new Neuron();
        this.neuroni.add(neuron);

        this.numarNeuroni++;
    }

    public void adaugaNeuron(Neuron neuron)
    {
        this.neuroni.add(neuron);

        this.numarNeuroni++;
    }

    /**
     *
     * @param functieActivare
     */
    public void adaugaNeuron(FunctieActivare functieActivare)
    {
        Neuron neuron = new Neuron(functieActivare);
        this.neuroni.add(neuron);

        this.numarNeuroni++;
    }

    /**
     *
     */
    public void eliminaNeuron()
    {
        try
        {
            this.neuroni.remove(this.neuroni.size() - 1);
            this.numarNeuroni--;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param index
     */
    public void eliminaNeuron(int index)
    {
        try
        {
            this.neuroni.remove(index);
            this.numarNeuroni--;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // ----------------------------------

    //TODO sterge
    /**
     *
     */
    public void calculeazaEroareaRetelei()
    {
        this.eroareaRetelei = this.functieDeCost.calculeazaEroarea(this);
    }

    // ----------------- Setteri si Getteri -------------------------

    public ArrayList<Neuron> getNeuroni() {
        return neuroni;
    }

    public void setNeuroni(ArrayList<Neuron> neuroni) {
        this.neuroni = neuroni;
    }

    public ArrayList<Double> getValoriDorite() {
        return valoriDorite;
    }

    public void setValoriDorite(ArrayList<Double> valoriDorite) {
        this.valoriDorite = valoriDorite;
    }

    public StratNeuronal getStratAnterior() {
        return stratAnterior;
    }

    public void setStratAnterior(StratNeuronal stratAnterior) {
        this.stratAnterior = stratAnterior;
    }

    public int getNumarNeuroni() {
        return numarNeuroni;
    }

    public void setNumarNeuroni(int numarNeuroni) {
        this.numarNeuroni = numarNeuroni;
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
