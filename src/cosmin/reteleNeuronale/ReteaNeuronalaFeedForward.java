package cosmin.reteleNeuronale;

import cosmin.regulaInvatare.RegulaInvatare;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeAntrenamentEtichetata;

public abstract class ReteaNeuronalaFeedForward extends ReteaNeuronala<RegulaInvatare<MultimeAntrenamentEtichetata>>
{
    public abstract void executaPropagare();
    public abstract void executaRetropropagare(int dimSubmultimeAntrenament);
    public abstract void executaOptimizare(double rataInvatare, double inertie);
}
