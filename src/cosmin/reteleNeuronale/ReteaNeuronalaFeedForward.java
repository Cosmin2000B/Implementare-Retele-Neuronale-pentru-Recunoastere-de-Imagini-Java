package cosmin.reteleNeuronale;

import cosmin.regulaInvatare.RegulaInvatare;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeAntrenamentEtichetata;

public abstract class ReteaNeuronalaFeedForward extends ReteaNeuronala<RegulaInvatare<MultimeAntrenamentEtichetata>>
{
    double eroareaRetelei;

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
