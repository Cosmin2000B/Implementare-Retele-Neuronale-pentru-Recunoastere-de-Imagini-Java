package cosmin.functiiActivare;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Ionescu Cosmin
 */

public class ReLU implements FunctieActivare, Serializable
{
    /**
     * pentru identificarea cu compatibilitatii cu
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
        return Math.max(0, input);
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public double valoareDerivata(Double input)
    {
        if(input > 0)
            return 1;
        return 0;
    }
}
