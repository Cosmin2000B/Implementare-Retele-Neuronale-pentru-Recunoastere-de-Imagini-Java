package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;

public interface FunctieDeCost
{
    /**
     *
     * @param stratDeIesire
     * @return
     */
    public double calculeazaEroarea(StratDeIesire stratDeIesire);

    /**
     *
     * @param input
     * @return
     */
    public double calculeazaDerivata(Neuron input, int index, StratDeIesire stratDeIesire);
}
