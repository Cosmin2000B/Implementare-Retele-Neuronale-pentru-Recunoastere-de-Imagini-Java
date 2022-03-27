package cosmin.reteleNeuronale;

import cosmin.regulInvatare.RegulaInvatare;
import cosmin.regulInvatare.multimeAntrenament.MultimeAntrenament;

/**
 * @author Ionescu Cosmin
 *   Clasa abstracta care modeleaza generalitati legate de o retea
 *  neuronala.
 * @param <Ri> reprezinta regula de invatare agreata de utilizator.
 */
public abstract class ReteaNeuronala<Ri extends RegulaInvatare>
{
    private String numeIdentificare;
    private Ri regulaInvatare;

    // --------- Constructori ----------------

    public ReteaNeuronala(){}

    public ReteaNeuronala(Ri regulaInvatare)
    {
        this.regulaInvatare = regulaInvatare;
    }

    public void antreneaza()
    {
        this.regulaInvatare.antreneaza();
    }

    // --------- Sfarsit Constructori --------

    public abstract void executaPropagare();

    // ------- Setteri si Getteri -------------------


    public String getNumeIdentificare()
    {
        return numeIdentificare;
    }

    public void setNumeIdentificare(String numeIdentificare)
    {
        this.numeIdentificare = numeIdentificare;
    }

    public Ri getRegulaInvatare()
    {
        return regulaInvatare;
    }

    public void setRegulaInvatare(Ri regulaInvatare)
    {
        this.regulaInvatare = regulaInvatare;
    }
}
