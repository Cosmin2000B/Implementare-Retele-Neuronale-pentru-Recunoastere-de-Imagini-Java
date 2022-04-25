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
     * @param input o variabila de tip Neuron, reprezentand neuronul pentru care dorim sa
     *              aflam valoarea derivatei functiei de cost in raport cu valoarea sa de
     *              iesire (gradul de activare).
     * @param index index-ul pe care il are neuronul in cadrul stratului de iesire. Acest
     *              parametru este utilizat in gestionarea corespondentei cau valoarea
     *              dorita pentru neuronul respectiv.
     * @param stratDeIesire stratul de iesire pe care se afla neuronul.
     * @return valoarea derivatei functiei de cost in raport cu valoarea de iesire a ne-
     * uronului specificat.
     */
    public double calculeazaDerivata(Neuron input, int index, StratDeIesire stratDeIesire);
}
