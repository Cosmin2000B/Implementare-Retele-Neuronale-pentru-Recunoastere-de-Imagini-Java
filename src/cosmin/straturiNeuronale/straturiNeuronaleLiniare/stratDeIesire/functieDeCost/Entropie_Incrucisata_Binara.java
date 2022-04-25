package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 *   Entropia incrucisata binara este un caz special al entropiei incrucisate, se
 *  se utilizeaza pentru probleme de clasificare binara, stratul de iesire avand
 *  un singur neuron si, de preferat, o functie de activare sigmoida.
 * @see cosmin.functiiActivare.sigmoide.Logistica
 * @see cosmin.functiiActivare.sigmoide.TangentaHiperbolica
 */
public class Entropie_Incrucisata_Binara implements FunctieDeCost, Serializable
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
        if(stratDeIesire.getNumarNeuroni() != 1)
            throw new IllegalArgumentException("Clasificarea binara impune existenta"
            + " unui singur neuron pe stratul de iesire!");

        // un numar mic 10^(-16) pentru evitarea calcularii ln(0)
        double corectie = 0.1e-15;

        if(stratDeIesire.getNeuroni().get(0).getValoareIesire() == 0d)
            stratDeIesire.
                    getNeuroni().
                    get(0).
                    setValoareIesire(stratDeIesire.getNeuroni().get(0).getValoareIesire() + corectie);

        if(stratDeIesire.getValoriDorite().get(0) == 1d)
            return (-1) * Math.log(Math.max(stratDeIesire.getNeuroni().get(0).getValoareIesire(), 1e-9));
        return (-1) * Math.log(Math.max(1 - stratDeIesire.getNeuroni().get(0).getValoareIesire(),
                1e-9));
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
        // un numar ff mic 10^(-15) pentru evitarea impartirii la 0
        double corectie = 0.1e-14;

        return (-1) *
                ((stratDeIesire.getValoriDorite().get(0)/
                        (stratDeIesire.getNeuroni().get(0).getValoareIesire() + corectie))
                -((1 - stratDeIesire.getValoriDorite().get(0))/
                        (1 - stratDeIesire.getNeuroni().get(0).getValoareIesire())
                + corectie)
                );
    }
}
