package cosmin.straturiNeuronale.straturiNeuronaleLiniare;

import cosmin.functiiActivare.FunctieActivare;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.StratNeuronal;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
public class StratDeIntrare implements StratNeuronal
{
    private ArrayList<Neuron> neuroni;
    private StratNeuronal stratUlterior;
    private int numarNeuroni;

    /**
     *
     * @param valoriIesire
     */
    public StratDeIntrare(@NotNull List<Double> valoriIesire)
    {
        // numarul de neuroni de pe stratul de intrare
        // trebuie sa coincida cu marimea input-ului
        this.numarNeuroni = valoriIesire.size();
        this.neuroni = new ArrayList<>(this.numarNeuroni);

        for(Double valoareIesire: valoriIesire)
        {
            Neuron neuron = new Neuron();
            neuron.setValoareIesire(valoareIesire);
            this.neuroni.add(neuron);
        }
    }

    /**
     *
     * @param valoriIesire
     * @param stratUlterior
     */
    public StratDeIntrare(@NotNull List<Double> valoriIesire, StratNeuronal stratUlterior)
    {
        this.numarNeuroni = valoriIesire.size();
        this.neuroni = new ArrayList<>(this.numarNeuroni);

        for(Double valoareIesire: valoriIesire)
        {
            Neuron neuron = new Neuron();
            neuron.setValoareIesire(valoareIesire);
            this.neuroni.add(neuron);
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

    @Override // nu trebuie pt intrare
    public void calculeazaIesiri() {}

    @Override
    public void reseteazaPonderi()
    {
        for(Neuron neuron: neuroni)
        {
            ArrayList<Sinapsa> sinapse = neuron.getSinapseIesire();
            for(Sinapsa sinapsa: sinapse)
                sinapsa.setPondere(ThreadLocalRandom.current().nextDouble());
        }
    }

    // TODO Incepem retropropagarea...
    @Override
    public void actualizeazaPonderi(double rataInvatare)
    {

    }

    // -------- modificare structura strat --------------

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

    // ---------------------- Setteri si Getteri ------------------------

    public ArrayList<Neuron> getNeuroni() {
        return neuroni;
    }

    public void setNeuroni(ArrayList<Neuron> neuroni) {
        this.neuroni = neuroni;
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

}
