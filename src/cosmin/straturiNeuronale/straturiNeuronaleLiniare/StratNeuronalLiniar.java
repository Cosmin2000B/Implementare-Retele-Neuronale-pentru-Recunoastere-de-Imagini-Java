package cosmin.straturiNeuronale.straturiNeuronaleLiniare;

import cosmin.functiiActivare.FunctieActivare;
import cosmin.neuron.Neuron;

import java.util.ArrayList;

/**
 * @author Ionescu Cosmin
 */
public abstract class StratNeuronalLiniar
{
    private String numeIdentificare;
    private ArrayList<Neuron> neuroni;
    private int numarNeuroni;

    /**
     *
     */
    public void adaugaNeuron()
    {
        Neuron neuron = new Neuron();
        this.neuroni.add(neuron);

        this.numarNeuroni++;
    }

    /**
     *
     * @param neuron
     */
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

    // --------------- Setteri si Getteri ----------------------


    public String getNumeIdentificare() {
        return numeIdentificare;
    }

    public void setNumeIdentificare(String numeIdentificare) {
        this.numeIdentificare = numeIdentificare;
    }

    public ArrayList<Neuron> getNeuroni() {
        return this.neuroni;
    }

    public void setNeuroni(ArrayList<Neuron> neuroni) {
        this.neuroni = neuroni;
    }

    public int getNumarNeuroni() {
        return this.numarNeuroni;
    }

    public void setNumarNeuroni(int numarNeuroni) {
        this.numarNeuroni = numarNeuroni;
    }
}
