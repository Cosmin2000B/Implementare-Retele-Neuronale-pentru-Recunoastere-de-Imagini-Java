package cosmin.utilStructuriNeuronale.initializarePonderi;

/**
 *  Pentru initializarea ponderilor intr-un interval definit de tipul
 * [limitaInferioara, limitaSuperioara]
 */
public class InitializareInterval extends InitializareSimpla
{
    /**
     * limita inferioara a intervalului
     */
    protected double limitaInferioara;
    /**
     * limita superioara a intervalului
     */
    protected double limitaSuperioara;

    /**
     *
     * @param limitaInferioara limita inferioara a intervaului din care
     *                         dorim sa obtinem valori.
     * @param limitaSuperioara limita superioara a intervalului din care
     *                         dorim sa obtinem valori.
     */
    public InitializareInterval(double limitaInferioara, double limitaSuperioara)
    {
        this.limitaInferioara = limitaInferioara;
        this.limitaSuperioara = limitaSuperioara;
    }

    @Override
    /**
     *
     */
    protected double nextVal()
    {
        return limitaInferioara +
                getGeneratorAleatoriu().nextDouble() * (limitaSuperioara - limitaInferioara);
    }
}
