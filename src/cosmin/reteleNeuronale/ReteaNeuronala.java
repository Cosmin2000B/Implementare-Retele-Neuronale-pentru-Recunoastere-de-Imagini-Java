package cosmin.reteleNeuronale;

import cosmin.regulaInvatare.RegulaInvatare;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ionescu Cosmin
 *   Clasa abstracta care modeleaza generalitati legate de o retea
 *  neuronala.
 * @param <Ri> reprezinta regula de invatare agreata de utilizator.
 */
public abstract class ReteaNeuronala<Ri extends RegulaInvatare> implements Serializable
{
    /**
     * pentru identificarea cu compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private String numeIdentificare;
    private transient Ri regulaInvatare;
    private  transient ArrayList<Double> valoriIesire;

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
    public void testeaza(){this.regulaInvatare.testeaza();}

    // --------- Sfarsit Constructori --------

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
        regulaInvatare.setReteaNeuronala(this);
    }

    public ArrayList<Double> getValoriIesire()
    {
        return valoriIesire;
    }

    public void setValoriIesire(ArrayList<Double> valoriIesire)
    {
        this.valoriIesire = valoriIesire;
    }
}
