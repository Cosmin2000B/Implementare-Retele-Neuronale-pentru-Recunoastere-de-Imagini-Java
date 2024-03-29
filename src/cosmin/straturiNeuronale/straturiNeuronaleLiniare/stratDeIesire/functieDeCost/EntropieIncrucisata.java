package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

import cosmin.functiiActivare.Softmax;
import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @see Softmax
 */
public class EntropieIncrucisata implements FunctieDeCost, Serializable
{
    /**
     * pentru identificarea cu compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public double calculeazaEroarea(StratDeIesire stratDeIesire)
    {
        // nu admitem alta functie de activare inafara de Softmax
        if(!(stratDeIesire.getFunctieActivare() instanceof Softmax))
            throw new IllegalArgumentException("Functia de activare trebuie sa fie Softmax " +
                    "pentru utilizarea entropiei incrucisate!");

        double eroareClasificare = 0d;

          //Inmultim ln(grad_activare_Neuron) cu valoarea de iesire dorita corespunzatoare
          //In practica, se foloseste ln (u.m. = nat) , se poate utiliza si log2
        for(int i = 0; i < stratDeIesire.getNeuroni().size(); ++i)
            eroareClasificare +=
            // evitam un potential ln(0) prin luarea max(val,0.1^8)
        (Math.log(Math.max(stratDeIesire.getNeuroni().get(i).getValoareIesire(), 1e-9))
        // /Math.log(2) - pentru calculare in baza 2, -> u.m. = bit
        * stratDeIesire.getValoriDorite().get(i));

        return (-1) * eroareClasificare;
    }

    /* --> instabila numeric
    @Override
    public double calculeazaEroarea(@NotNull StratDeIesire stratDeIesire)
    {
        // nu admitem alta functie de activare inafara de Softmax
        if(!(stratDeIesire.getFunctieActivare() instanceof Softmax))
            throw new IllegalArgumentException("Functia de activare trebuie sa fie Softmax " +
                    "pentru utilizarea entropiei incrucisate!");

        double eroareClasificare = 0d;

          //Inmultim ln(grad_activare_Neuron) cu valoarea de iesire dorita corespunzatoare
          //In practica, se foloseste ln (u.m. = nat) , se poate utiliza si log2
        for(int i = 0; i < stratDeIesire.getNeuroni().size(); ++i)
    eroareClasificare +=
        (Math.log(stratDeIesire.getNeuroni().get(i).getValoareIesire())
        // /Math.log(2) - pentru calculare in baza 2, -> u.m. = bit
        * stratDeIesire.getValoriDorite().get(i));

        return (-1) * eroareClasificare;
    }
     */

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
    public double calculeazaDerivata(@NotNull Neuron input, int index,
                                     @NotNull StratDeIesire stratDeIesire)
    {
        return input.getValoareIesire() - stratDeIesire.getValoriDorite().get(index);
    }
}
