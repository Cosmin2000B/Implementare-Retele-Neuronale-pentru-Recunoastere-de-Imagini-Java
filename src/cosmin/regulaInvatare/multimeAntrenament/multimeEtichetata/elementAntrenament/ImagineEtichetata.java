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

    private ArrayList<Matrice> valori;
    private ArrayList<Double> valoriLiniarizat;

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
    public ArrayList<Matrice> getValori()
    {
        if(valori != null)
            return this.valori;

        valori = new ArrayList<>();

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

    public void setValori(ArrayList<Matrice> valori)
    {
        this.valori = valori;
    }

    /**
     *
     * @return
     */
    public ArrayList<Double> getValoriLiniarizat()
    {
        if(this.valoriLiniarizat != null)
            return valoriLiniarizat;

        if(this.valori == null)
            this.valori = this.getValori();

        if(this.multimeImagini.getNrCanaleCulori() == 1)
            valoriLiniarizat = Matrice.liniarizare(valori.get(0));
        else
        {
            valoriLiniarizat = new ArrayList<>();
            this.valori.forEach(matrice ->
                    valoriLiniarizat.addAll(Matrice.liniarizare(matrice)));
        }

        return valoriLiniarizat;
    }

    public void setValoriLiniarizat(ArrayList<Double> valoriLiniarizat)
    {
        this.valoriLiniarizat = valoriLiniarizat;
    }

    public MultimeImagini getMultimeImagini()
    {
        return multimeImagini;
    }

    public void setMultimeImagini(MultimeImagini multimeImagini)
    {
        this.multimeImagini = multimeImagini;
    }

    public int getIndexClasa()
    {
        return indexClasa;
    }

    public void setIndexClasa(int indexClasa)
    {
        this.indexClasa = indexClasa;
    }
}
