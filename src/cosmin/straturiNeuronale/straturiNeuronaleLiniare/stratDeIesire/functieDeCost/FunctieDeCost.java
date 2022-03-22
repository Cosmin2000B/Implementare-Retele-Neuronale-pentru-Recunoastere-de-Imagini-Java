package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

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
    public double calculeazaDerivata(double input);
}
