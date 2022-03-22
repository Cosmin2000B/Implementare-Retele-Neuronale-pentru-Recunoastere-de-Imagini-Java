package cosmin.functiiActivare.sigmoide;

import cosmin.functiiActivare.FunctieActivare;

/**
 * @author Ionescu Cosmin
 */

public class Logistica implements FunctieActivare
{
    /**
     *
     * @param input
     * @return
     */
    @Override
    public double valoareFunctie(Double input)
    {
        return 1/(1 + Math.pow(Math.E, (-1) * input));
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public double valoareDerivata(Double input)
    {
        return valoareFunctie(input) * (1 - valoareFunctie(input));
    }

}
