package cosmin.neuron;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Ionescu Cosmin
 *
 * @see Neuron
 */
public class Bias
{
    private static final double SEMN = -1.0d;
    private double pondere;
    private double deltaPondere;

    public Bias()
    {
        this.pondere = ThreadLocalRandom.current().nextDouble();
    }

    public Bias(double pondere)
    {
        this.pondere = pondere;
    }

    public double calculeazaValoare()
    {
        return pondere * SEMN;
    }

    public void actualizeazaPondere(double rataInvatare)
    {
        this.pondere -= this.deltaPondere * rataInvatare;
        // cand actualizam, resetam eroarea acumulata
        this.deltaPondere = 0;
    }

    public void actualizeazaPondere(double rataInvatare, double inertie)
    {
        this.pondere = inertie * this.pondere - rataInvatare * this.deltaPondere;
        // cand actualizam, resetam eroarea acumulata
        this.deltaPondere = 0;
    }

    // --------- Setteri si Getteri --------------------

    public double getPondere()
    {
        return pondere;
    }

    public void setPondere(double pondere)
    {
        this.pondere = pondere;
    }

    public double getDeltaPondere() {
        return deltaPondere;
    }

    public void setDeltaPondere(double deltaPondere) {
        this.deltaPondere = deltaPondere;
    }

    // ---------------------------------------------------

    @Override
    public String toString()
    {
        return "* Bias:\n" + "  - pondere bias: " + this.pondere + ";\n"
                + "  - ajustare curenta pondere bias (delta pondere): " +this.deltaPondere
                + ";\n";
    }
}
