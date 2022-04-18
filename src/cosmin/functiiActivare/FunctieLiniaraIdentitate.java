package cosmin.functiiActivare;

import java.io.Serial;
import java.io.Serializable;

/**
 * se utilizeaza in stratul de intrare
 */
public class FunctieLiniaraIdentitate implements FunctieActivare, Serializable
{
    /**
     * pentru identificarea cu compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public double valoareFunctie(Double input)
    {
        return input;
    }

    @Override
    public double valoareDerivata(Double input)
    {
        return 1d;
    }
}
