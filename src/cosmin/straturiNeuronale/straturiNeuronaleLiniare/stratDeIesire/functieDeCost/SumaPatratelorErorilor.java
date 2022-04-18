package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Ionescu Cosmin
 */
public class SumaPatratelorErorilor implements FunctieDeCost, Serializable
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


        double sumaPatratelorErorilor = 0d;
        for(int i = 0; i < stratDeIesire.getNumarNeuroni(); ++i)
            sumaPatratelorErorilor +=
                    Math.pow(stratDeIesire.getValoriDorite().get(i)
                            - stratDeIesire.getNeuroni().get(i).getValoareIesire(), 2);

        // factorul de 1/2 este introdus pentru anularea factorului de 2 de la derivata
        // functiei
        sumaPatratelorErorilor /=2;

        return sumaPatratelorErorilor;
    }

    /**
     *
     * @param input
     * @param index
     * @param stratDeIesire
     * @return
     */
    @Override
    public double calculeazaDerivata(Neuron input, int index, @NotNull StratDeIesire stratDeIesire)
    {
        if(stratDeIesire.getValoriDorite().isEmpty())
            throw new IllegalArgumentException("Lista cu valori dorite este goala!");

        return input.getValoareIesire() - stratDeIesire.getValoriDorite().get(index);
    }
}
