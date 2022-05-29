package cosmin.functiiActivare.sigmoide;

import cosmin.functiiActivare.FunctieActivare;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Ionescu Cosmin
 */
public class TangentaHiperbolica implements FunctieActivare, Serializable
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
        return (Math.pow(Math.E, 2 * input) - 1) / (Math.pow(Math.E, 2 * input) + 1);
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public double valoareDerivata(Double input)
    {
        return Math.pow(2/(Math.pow(Math.E, input) + Math.pow(Math.E, (-1) * input)), 2);
    }
}
