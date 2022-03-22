package cosmin.straturiNeuronale.straturiNeuronaleLiniare;

import cosmin.functiiActivare.FunctieActivare;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.StratNeuronal;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

//TODO Constructori si restul...

/**
 * @author Ionescu Cosmin
 */
public class StratAscuns implements StratNeuronal
{
    private ArrayList<Neuron> neuroni;

    private StratNeuronal stratAnterior;
    private StratNeuronal stratUlterior;

    private int numarNeuroni;
    private FunctieActivare functieActivare;

    //TODO constructori
    public StratAscuns()
    {

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

            sinapse = neuron.getSinapseIesire();
            for(Sinapsa sinapsa: sinapse)
                sinapsa.setPondere(ThreadLocalRandom.current().nextDouble());
        }
    }

    @Override
    public void actualizeazaPonderi(double rataInvatare)
    {
        //TODO de implementat retropropagare
    }

    // ---------- modificare structura strat --------------

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

    // ------------- Setteri si Getteri ------------------------

    public ArrayList<Neuron> getNeuroni() {
        return neuroni;
    }

    public void setNeuroni(ArrayList<Neuron> neuroni) {
        this.neuroni = neuroni;
    }

    public StratNeuronal getStratAnterior() {
        return stratAnterior;
    }

    public void setStratAnterior(StratNeuronal stratAnterior) {
        this.stratAnterior = stratAnterior;
    }

    public StratNeuronal getStratUlterior() {
        return stratUlterior;
    }

    public void setStratUlterior(StratNeuronal stratUlterior) {
        this.stratUlterior = stratUlterior;
    }

    public int getNumarNeuroni() {
        return numarNeuroni;
    }

    public void setNumarNeuroni(int numarNeuroni) {
        this.numarNeuroni = numarNeuroni;
    }

    public FunctieActivare getFunctieActivare() {
        return functieActivare;
    }

    public void setFunctieActivare(FunctieActivare functieActivare) {
        this.functieActivare = functieActivare;
    }
}
