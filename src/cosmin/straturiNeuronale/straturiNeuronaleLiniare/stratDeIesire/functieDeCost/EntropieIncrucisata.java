package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

import cosmin.functiiActivare.Softmax;
import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class EntropieIncrucisata implements FunctieDeCost
{

    @Override
    public double calculeazaEroarea(@NotNull StratDeIesire stratDeIesire)
    {
        // nu admitem alta functie de activare inafara de Softmax
        if(!(stratDeIesire.getFunctieActivare() instanceof Softmax))
            throw new IllegalArgumentException("Functia de activare trebuie sa fie Softmax " +
                    "pentru utilizarea entropiei incrucisate!");

        double eroareClasificare = 0d;

        /*
          Inmultim ln(grad_activare_Neuron) cu valoarea de iesire dorita corespunzatoare
          In practica, se foloseste ln (u.m. = nat) , se poate utiliza si log2
         */
        for(int i = 0; i < stratDeIesire.getNeuroni().size(); ++i)
            eroareClasificare +=
                    (Math.log(stratDeIesire.getNeuroni().get(i).getValoareIesire())
                            /* /Math.log(2) - pentru calculare in baza 2, -> u.m. = bit */
                            * stratDeIesire.getValoriDorite().get(i));

        return (-1) * eroareClasificare;
    }

    /**
     *  Formula derivatei este particulara utilizarii functiei de cost EntropieIncrucisata
     * impreuna cu functia de cost Softmax.
     * @param input
     * @param index
     * @param stratDeIesire
     * @return
     */
    @Override
    public double calculeazaDerivata(@NotNull Neuron input, int index, @NotNull StratDeIesire stratDeIesire)
    {
        return input.getValoareIesire() - stratDeIesire.getValoriDorite().get(index);
    }
}
