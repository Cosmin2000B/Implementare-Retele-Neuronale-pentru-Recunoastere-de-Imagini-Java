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
          Inmultim log2(grad_activare_Neuron) cu valoarea de iesire dorita corespunzatoare
          In practica, se foloseste si ln
         */
        for(int i = 0; i < stratDeIesire.getNeuroni().size(); ++i)
            eroareClasificare +=
                    ((Math.log(stratDeIesire.getNeuroni().get(i).getValoareIesire()) / Math.log(2))
                            * stratDeIesire.getValoriDorite().get(i));

        return (-1) * eroareClasificare;
    }

    @Override
    public double calculeazaDerivata(@NotNull Neuron input, int index, @NotNull StratDeIesire stratDeIesire)
    {
        return input.getValoareIesire() - stratDeIesire.getValoriDorite().get(index);
    }
}
