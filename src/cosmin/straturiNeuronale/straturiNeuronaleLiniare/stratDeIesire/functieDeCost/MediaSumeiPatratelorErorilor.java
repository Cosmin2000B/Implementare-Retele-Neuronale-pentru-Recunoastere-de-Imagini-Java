package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * @aythor Ionescu Cosmin
 *
 * @see FunctieDeCost
 * @see StratDeIesire
 */
public class MediaSumeiPatratelorErorilor implements FunctieDeCost, Serializable
{
    /**
     * pentru identificarea cu compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param stratDeIesire
     * @return
     * @throws IllegalArgumentException in cazul in care vectorul de valori
     * dorite nu are dimensiunea egala cu numarul de neuroni de pe ultimul
     * strat, aceasta fiind o conditie esentiala pentru structura RNA.
     */
    @Override
    public double calculeazaEroarea(@NotNull StratDeIesire stratDeIesire)
    {
        if(stratDeIesire.getNeuroni() == null)
            throw new NullPointerException("Stratul de iesire este null!");

        /*
          Dimensiunea vectorului de valori dorite trebuie sa fie egala cu dimensiunea
        stratului de iesire (numarul de neuroni de pe acesta).
          In caz contrar, se considera o eroare a utilizatorului in definirea
        seturilor de valori dorite sau a structurii stratului ascuns.
         */
        if(stratDeIesire.getNumarNeuroni() != stratDeIesire.getValoriDorite().size())
            throw new IllegalArgumentException(" Dimensiunea vectorului de valori dorite"
                    + " difera de dimensiunea stratului de iesire!");


        double mediaSumeiPatratelorErorilor = 0d;
        for(int i = 0; i < stratDeIesire.getNumarNeuroni(); ++i)
            mediaSumeiPatratelorErorilor +=
                    Math.pow(stratDeIesire.getValoriDorite().get(i)
                            - stratDeIesire.getNeuroni().get(i).getValoareIesire(), 2);

        // factorul de 1/2 este introdus pentru anularea factorului de 2 de la derivata
        // functiei
        mediaSumeiPatratelorErorilor /= (2d * stratDeIesire.getNumarNeuroni());

        return mediaSumeiPatratelorErorilor;
    }

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
     *         uronului specificat.
     */
    @Override
    public double calculeazaDerivata(Neuron input, int index, @NotNull StratDeIesire stratDeIesire)
    {
        if(stratDeIesire.getValoriDorite().isEmpty())
            throw new IllegalArgumentException("Lista cu valori dorite este goala!");

        return  (1d / stratDeIesire.getNumarNeuroni()) *
                (input.getValoareIesire() - stratDeIesire.getValoriDorite().get(index));
    }
}
