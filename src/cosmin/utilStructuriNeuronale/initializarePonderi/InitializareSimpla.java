package cosmin.utilStructuriNeuronale.initializarePonderi;

import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.reteleNeuronale.PerceptronMultiStrat;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratAscuns;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratNeuronalLiniar;

import java.util.Random;

/**
 *  Capabil sa parcurga o retea neuronala, un strat neuronal sau
 * sinapsele unui neuron si sa initializeze aleator ponderile
 */
public class InitializareSimpla
{
    /**
     * generator aleator de numere
     */
    private Random generatorAleatoriu;

    // ---------------- Constructori -------------------
    /**
     *  Constructor fara parametrii. Creaza o instanta Random pentru
     * generarea numerelor.
     */
    public InitializareSimpla()
    {
        this.generatorAleatoriu = new Random();
    }

    /**
     *  Constructor cu un parametru de tip Random. Prin apelarea cu aceasi
     * sursa de aleatorism se pot reproduce anumite procese si rezultate.
     * @param generatorAleator sursa de aleatorism stabilita de utilizator.
     */
    public InitializareSimpla(Random generatorAleator)
    {
        this.generatorAleatoriu = generatorAleator;
    }

    // --------------- Sfarsit Constructori --------------

    /**
     *
     * @param perceptronMultiStrat
     */
    public void initializeazaPonderi(PerceptronMultiStrat perceptronMultiStrat)
    {
        for(StratAscuns stratAscuns: perceptronMultiStrat.getStraturiAscunse())
            initializeazaPonderi(stratAscuns);

        initializeazaPonderi(perceptronMultiStrat.getStratDeIesire());
    }

    /**
     *
     * @param stratNeuronalLiniar
     */
    public void initializeazaPonderi(StratNeuronalLiniar stratNeuronalLiniar)
    {
        for(Neuron neuron: stratNeuronalLiniar.getNeuroni())
            initializeazaPonderi(neuron);
    }

    /**
     *   Parcurge fiecare sinapsa de intrare a neuronului si initializeaza
     * ponderea cu urmatoarea valoare returnata de metoda nextVal().
     * @param neuron neuronul pentru care dorim sa-i initializam ponderile
     *               sinapselor de intrare.
     */
    public void initializeazaPonderi(Neuron neuron)
    {
        for(Sinapsa sinapsa: neuron.getSinapseIntrare())
            sinapsa.setPondere(nextVal());
    }

    /**
     *  Furnizeaza urmatoarea valoare a generatorului aleator din
     * intervalul [-0.5, 0.5).
     *  Poate fi suprascrisa pentru definirea altui interval de va-
     * lori.
     * @return
     */
    protected double nextVal()
    {
        return generatorAleatoriu.nextDouble() - 0.5d;
    }

    // -------------- Getteri si Setteri ----------------

    public Random getGeneratorAleatoriu()
    {
        return generatorAleatoriu;
    }

    public void setGeneratorAleatoriu(Random generatorAleatoriu)
    {
        this.generatorAleatoriu = generatorAleatoriu;
    }
    // ------------- Sfarsit Getteri si Setteri -----------------
}
