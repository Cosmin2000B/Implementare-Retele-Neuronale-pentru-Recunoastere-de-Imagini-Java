package cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament;

import cosmin.operatii_io.imagine.ObtinereValoriImagineRGB;
import cosmin.operatii_io.imagine.ObtinereValoriImagineAN;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import cosmin.utilStructuriAlgebrice.Matrice;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 */
public class ImagineEtichetata
{
    private MultimeImagini multimeImagini;
    private String numeIdentificare;
    private File locatieMemorie;
    private LinkedList<Matrice> valori;
    private int indexClasa;

    /**
     *
     * @param locatieMemorie
     */
    public ImagineEtichetata
    (MultimeImagini multimeImagini, File locatieMemorie, int indexClasa)
    {
        this.multimeImagini = multimeImagini;
        this.locatieMemorie = locatieMemorie;
        this.numeIdentificare = locatieMemorie.getName();
        this.indexClasa = indexClasa;
    }

    // --------------- Getteri si Setteri ------------------

    public String getNumeIdentificare()
    {
        return numeIdentificare;
    }

    public void setNumeIdentificare(String numeIdentificare)
    {
        this.numeIdentificare = numeIdentificare;
    }

    public File getLocatieMemorie()
    {
        return locatieMemorie;
    }

    public void setLocatieMemorie(File locatieMemorie)
    {
        this.locatieMemorie = locatieMemorie;
    }

    /**
     *
     * @return
     */
    public LinkedList<Matrice> getValori()
    {
        if(valori != null)
            return this.valori;

        valori = new LinkedList<Matrice>();

        if(this.multimeImagini.getNrCanaleCulori() == 1)
            this.valori.add(new Matrice(ObtinereValoriImagineAN.
                    toValoriSubunitare(this.locatieMemorie.getPath())));
        else
        {
            LinkedList<double[][]> rez = ObtinereValoriImagineRGB.
                    toValoriSubunitare(this.locatieMemorie.getPath());
            for(int i = 0; i < this.multimeImagini.getNrCanaleCulori(); ++i)
                this.valori.add(new Matrice(rez.get(i)));
        }

        return valori;
    }

    /**
     *
     * @return
     */
    public ArrayList<Double> getValoriLiniarizat()
    {
        if(this.valori == null)
            this.valori = this.getValori();

        ArrayList<Double> output;

        if(this.multimeImagini.getNrCanaleCulori() == 1)
            output = Matrice.liniarizare(valori.get(0));
        else
        {
            output = new ArrayList<>();
            this.valori.forEach(matrice ->
                    output.addAll(Matrice.liniarizare(matrice)));
        }

        return output;
    }

    public void setValori(LinkedList<Matrice> valori)
    {
        this.valori = valori;
    }
}
