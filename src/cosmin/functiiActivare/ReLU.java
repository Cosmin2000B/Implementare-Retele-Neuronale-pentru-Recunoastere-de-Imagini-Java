package cosmin.functiiActivare;

/**
 * @author Ionescu Cosmin
 */

public class ReLU implements FunctieActivare
{
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
