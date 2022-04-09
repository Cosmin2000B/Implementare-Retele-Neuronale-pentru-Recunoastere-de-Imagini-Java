package cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata;

import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament.ImagineEtichetata;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class MultimeImagini extends MultimeAntrenamentEtichetata
{
    private int nrCanaleCulori = 1;
    ArrayList<ImagineEtichetata> imaginiAntrenament;
    ArrayList<ImagineEtichetata> imaginiTestare;

    public MultimeImagini(File locatieMemorie, HashMap<Integer, String> corespondentaEticheta,
                          int nrCanaleCulori)
    {
        super(locatieMemorie, corespondentaEticheta);

        this.nrCanaleCulori = nrCanaleCulori;
        this.imaginiAntrenament = new ArrayList<>();
        this.imaginiTestare = new ArrayList<>();
    }

    /**
     *
     * @param locatieMemorie localizarea in memorie a fisierului ce contine
     *                       setul de date.
     * @param nrCanaleCulori numarul de canale de culoare pe care le au imaginile
     *                       din setul de date
     * @throws IllegalArgumentException
     */
    public static MultimeImagini citesteMultimeImagini(String locatieMemorie, int nrCanaleCulori)
    {
        if(locatieMemorie == null || locatieMemorie.isBlank())
            throw new IllegalArgumentException("locatieMemorie nu poate fi un String null/gol!");

        if(nrCanaleCulori <= 0)
            throw new IllegalArgumentException("nrCanaleCulori nu poate fi mai mic sau egal cu 0!");

        File[] fisiereTestingTraining = new File(locatieMemorie).listFiles();

        if(fisiereTestingTraining == null || fisiereTestingTraining.length != 2)
            throw new IllegalArgumentException("Nu exista cele 2 fisiere convenite la aceasta locatie");

        File locatieMultimeAntrenament = fisiereTestingTraining[1];
        File locatieMultimeTestare = fisiereTestingTraining[0];

        File[] claseAntrenament = locatieMultimeAntrenament.listFiles();
        int nrClase = claseAntrenament.length;

        HashMap<Integer, String> corespondentaEticheta = new HashMap<>();
        for(int i = 0; i < nrClase; ++i)
            corespondentaEticheta.put(i, claseAntrenament[i].getName());

        File[] claseTestare = locatieMultimeTestare.listFiles();

        if(nrClase != claseTestare.length)
            throw new IllegalArgumentException
                    ("Numarul de clase din fisierul test nu corespunde celui din fisierul train!");

        MultimeImagini multimeImagini= new MultimeImagini
                (new File(locatieMemorie), corespondentaEticheta, nrCanaleCulori);

        for(int i = 0; i < nrClase; ++i)
        {
            File[] elementeClasa = claseAntrenament[i].listFiles();
            for(int j = 0; j < elementeClasa.length; ++j)
            {
                ImagineEtichetata imagineEtichetata =
                        new ImagineEtichetata(multimeImagini, elementeClasa[j], i);
                multimeImagini.imaginiAntrenament.add(imagineEtichetata);
            }

            elementeClasa = claseTestare[i].listFiles();
            for(int j = 0; j < elementeClasa.length; ++j)
            {
                ImagineEtichetata imagineEtichetata =
                        new ImagineEtichetata(multimeImagini, elementeClasa[j], i);
                multimeImagini.imaginiTestare.add(imagineEtichetata);
            }
        }

        return multimeImagini;
    }

    // TODO cu sursa de aleatorism
    public static void amestecaAleator(ArrayList<ImagineEtichetata> multimeElemente)
    {
        Collections.shuffle(multimeElemente);
    }

    public static void amestecaAleator(ArrayList<ImagineEtichetata> multimeElemente, Random sursa)
    {
        Collections.shuffle(multimeElemente, sursa);
    }

    // ------ Setteri si Getteri -----------------
    
    public int getNrCanaleCulori()
    {
        return nrCanaleCulori;
    }

    public void setNrCanaleCulori(int nrCanaleCulori)
    {
        this.nrCanaleCulori = nrCanaleCulori;
    }

    public ArrayList<ImagineEtichetata> getImaginiAntrenament()
    {
        return imaginiAntrenament;
    }

    public void setImaginiAntrenament(ArrayList<ImagineEtichetata> imaginiAntrenament)
    {
        this.imaginiAntrenament = imaginiAntrenament;
    }

    public ArrayList<ImagineEtichetata> getImaginiTestare()
    {
        return imaginiTestare;
    }

    public void setImaginiTestare(ArrayList<ImagineEtichetata> imaginiTestare)
    {
        this.imaginiTestare = imaginiTestare;
    }
}
