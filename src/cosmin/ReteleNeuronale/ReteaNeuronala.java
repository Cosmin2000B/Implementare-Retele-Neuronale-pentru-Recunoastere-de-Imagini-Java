package cosmin.ReteleNeuronale;

public abstract class ReteaNeuronala
{
    private double rataInvatare = 0.1d;
    private double inertie = 0.9d;

    // --------- Constructori ----------------

    public ReteaNeuronala(){}

    public ReteaNeuronala(double rataInvatare)
    {
        this.rataInvatare = rataInvatare;
    }

    public ReteaNeuronala(double rataInvatare, double inertie)
    {
        this.rataInvatare = rataInvatare;
        this.inertie = inertie;
    }

    // --------- Sfarsit Constructori --------

    public abstract void executaPropagare();

    public abstract void executaRetroPropagare(double rataInvatare);
    public abstract void executaRetroPropagare(double rataInvatare, double inertie);

    // ------- Setteri si Getteri -------------------

    public double getRataInvatare() {
        return rataInvatare;
    }

    public void setRataInvatare(double rataInvatare) {
        this.rataInvatare = rataInvatare;
    }

    public double getInertie() {
        return inertie;
    }

    public void setInertie(double inertie) {
        this.inertie = inertie;
    }
}
