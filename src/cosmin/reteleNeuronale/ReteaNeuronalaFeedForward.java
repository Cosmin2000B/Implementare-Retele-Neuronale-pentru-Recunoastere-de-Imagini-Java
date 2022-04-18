package cosmin.reteleNeuronale;

import cosmin.regulaInvatare.RegulaInvatare;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeAntrenamentEtichetata;

import java.io.Serial;
import java.io.Serializable;

public abstract class ReteaNeuronalaFeedForward
                                                extends ReteaNeuronala<RegulaInvatare<MultimeAntrenamentEtichetata>>
                                                implements Serializable
{
    /**
     * pentru identificarea cu compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private transient double eroareaRetelei;

    public abstract void executaPropagare();
    public abstract void executaRetropropagare(int dimSubmultimeAntrenament);
    public abstract void executaOptimizare(double rataInvatare, double inertie);

    // --------- Getteri si Setteri ------------
    public double getEroareaRetelei()
    {
        return eroareaRetelei;
    }

    public void setEroareaRetelei(double eroareaRetelei)
    {
        this.eroareaRetelei = eroareaRetelei;
    }
}
