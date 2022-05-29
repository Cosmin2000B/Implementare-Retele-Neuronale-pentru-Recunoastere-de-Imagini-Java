package cosmin.functiiActivare.sigmoide;

import cosmin.functiiActivare.FunctieActivare;
import org.apache.commons.math3.util.FastMath;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Ionescu Cosmin
 */

public class Logistica implements FunctieActivare, Serializable
{
    /**
     * pentru identificarea compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param input
     * @return
     */
    @Override
    public double valoareFunctie(Double input)
    {
        return 1/(1 + FastMath.exp((-1) * input));
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public double valoareDerivata(Double input)
    {
        // pentru a calcula valoarea o singura data
        double valoareFunctie = valoareFunctie(input);

        return valoareFunctie * (1 - valoareFunctie);
    }

}
