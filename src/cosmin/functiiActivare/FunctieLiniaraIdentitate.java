package cosmin.functiiActivare;

/**
 * se utilizeaza in stratul de intrare
 */
public class FunctieLiniaraIdentitate implements FunctieActivare
{
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
