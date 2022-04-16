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
    private double penultimaDeltaPondere = 0d;
    private double deltaPondere;

    public Bias()
    {
        // ponderea bias-ului este initializata cu 0
        this.pondere = 0d;
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

    /**
     *
     * @param rataInvatare
     * @param inertie
     */
    public void actualizeazaPondere(double rataInvatare, double inertie)
    {
        // formula utilizata cu (1 - inertie) pentru evitarea scalarii ratei de invatare
        this.pondere -=
                rataInvatare * (inertie * this.penultimaDeltaPondere + (1 - inertie) * this.deltaPondere);

        // adaugam deltaPondere actuala la penultimaDeltaPondere
        this.penultimaDeltaPondere = inertie * this.penultimaDeltaPondere + (1 - inertie) * this.deltaPondere;
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
