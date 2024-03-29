package cosmin.functiiActivare;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Ionescu Cosmin
 */
public class LeakyReLU implements FunctieActivare, Serializable
{
    /**
     * pentru identificarea compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private double panta;

    public LeakyReLU()
    {
        this.panta = 0.1;
    }

    public LeakyReLU(double panta)
    {
        this.panta = panta;
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public double valoareFunctie(Double input)
    {
        if(input > 0)
            return input;

        return input * this.panta;
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
            return 1d;

        return this.panta;
    }
}
