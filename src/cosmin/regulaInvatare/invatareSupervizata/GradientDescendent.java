package cosmin.regulaInvatare.invatareSupervizata;

import cosmin.regulaInvatare.RegulaInvatare;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeAntrenamentEtichetata;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import cosmin.reteleNeuronale.ReteaNeuronalaFeedForward;

// TODO
public class GradientDescendent extends RegulaInvatare<MultimeAntrenamentEtichetata>
{
    // 1 - default -> online
    // > 1 && < dimMultime - > antrenament cu sublumtimi (nr iteratii intr - o epoca: dimMultime / dimSubmultime
    // == dimMultime -> actualizare dupa o epoca ( epoca = iteratie)
    private int dimensiuneSubmutlime = 1;
    private double rataInvatare = 0.1d;
    private double inertie = 0.9d;

    public GradientDescendent()
    {}

    @Override
    public void antreneaza()
    {
        if(!(this.getReteaNeuronala() instanceof ReteaNeuronalaFeedForward))
            throw new IllegalArgumentException("Nu este permisa decat antrenarea cu GradientDescendent"
            + " a RNA de tip FeedForward!");

        if(!(this.getMultimeAntrenament() instanceof MultimeImagini))
            throw new IllegalStateException("Momentant, nu suporta decat MultimeImagini!");

    }

    // ---------------- Setteri si Getteri --------------------

    public int getDimensiuneSubmutlime()
    {
        return dimensiuneSubmutlime;
    }

    public void setDimensiuneSubmutlime(int dimensiuneSubmutlime)
    {
        this.dimensiuneSubmutlime = dimensiuneSubmutlime;
    }
}
