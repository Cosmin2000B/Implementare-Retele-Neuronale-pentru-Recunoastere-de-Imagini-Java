package cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata;

import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament.ImagineEtichetata;
import cosmin.utilStructuriAlgebrice.Matrice;

import java.io.*;
import java.util.*;

public class MultimeImagini extends MultimeAntrenamentEtichetata
{
    private int nrCanaleCulori = 1;
    /**
     * utilizate in procesul de antrenare pentru determinarea parametrilor
     * clasificatorului ( ponderi sinapse, eventual bias-uri )
     */
    ArrayList<ImagineEtichetata> imaginiAntrenament;
    /**
     * utilizate pentru a analiza performantele unui clasificator antrenat
     */
    ArrayList<ImagineEtichetata> imaginiTestare;

    public MultimeImagini(File locatieMemorie, HashMap<Integer, String> corespondentaEticheta,
                          int nrCanaleCulori)
    {
        super(locatieMemorie, corespondentaEticheta);

        this.nrCanaleCulori = nrCanaleCulori;
        this.imaginiAntrenament = new ArrayList<>();
        this.imaginiTestare = new ArrayList<>();
    }

    // ------- Varianta generica -------------------------------
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

    // ---------------------------------------------

    public static ArrayList<ImagineEtichetata> citesteMultimeMNIST
            (MultimeImagini multimeImagini, String locatieMemorieDate, String locatieMemorieEtichete)
    {
        try
        {
            DataInputStream dataInputStream =
                    new DataInputStream(new BufferedInputStream(new FileInputStream(locatieMemorieDate)));

            int nrMagic = dataInputStream.readInt();
            int nrElemente = dataInputStream.readInt();

            int nrLinii = dataInputStream.readInt();
            int nrColoane = dataInputStream.readInt();

            ArrayList<ImagineEtichetata> imaginiEtichetate = new ArrayList<>(nrElemente);

            HashMap<Integer, String> corespondentaEticheta = new HashMap<>();
            for(int i = 0; i < 10; ++i)
                corespondentaEticheta.put(i, Integer.toString(i));
            multimeImagini.setCorespondentaEticheta(corespondentaEticheta);
            //- ---------

            DataInputStream eticheteInputStream =
                    new DataInputStream(new BufferedInputStream(new FileInputStream(locatieMemorieEtichete)));
            int nrMagicEtichete = eticheteInputStream.readInt();
            int nrEtichete = eticheteInputStream.readInt();

            for(int i = 0; i < nrElemente; ++i)
            {
                int indexClasa = eticheteInputStream.readUnsignedByte();
                ImagineEtichetata imagineEtichetata =
                        new ImagineEtichetata(multimeImagini, new File("x"), indexClasa);

                Matrice matrice = new Matrice(nrLinii, nrColoane);
                for(int x = 0; x < nrLinii; ++x)
                    for(int y = 0; y < nrColoane; ++y)
                        matrice.setValoareElement(x, y,
                                dataInputStream.readUnsignedByte() / 255d);

                ArrayList<Matrice> val = new ArrayList<>();
                val.add(matrice);
                imagineEtichetata.setValori(val);

                imaginiEtichetate.add(imagineEtichetata);
            }

            dataInputStream.close();
            eticheteInputStream.close();

            return imaginiEtichetate;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<ImagineEtichetata> citesteMultimeMNISTLiniarizat
            (MultimeImagini multimeImagini, String locatieMemorieDate, String locatieMemorieEtichete)
    {
        try
        {
            DataInputStream dataInputStream =
                    new DataInputStream(new BufferedInputStream(new FileInputStream(locatieMemorieDate)));

            int nrMagic = dataInputStream.readInt();
            int nrElemente = dataInputStream.readInt();

            int nrLinii = dataInputStream.readInt();
            int nrColoane = dataInputStream.readInt();

            ArrayList<ImagineEtichetata> imaginiEtichetate = new ArrayList<>(nrElemente);

            // todo de vzt
            HashMap<Integer, String> corespondentaEticheta = new HashMap<>();
            for(int i = 0; i < 10; ++i)
                corespondentaEticheta.put(i, Integer.toString(i));
            multimeImagini.setCorespondentaEticheta(corespondentaEticheta);
            //- ---------

            DataInputStream eticheteInputStream =
                    new DataInputStream(new BufferedInputStream(new FileInputStream(locatieMemorieEtichete)));
            int nrMagicEtichete = eticheteInputStream.readInt();
            int nrEtichete = eticheteInputStream.readInt();

            for(int i = 0; i < nrElemente; ++i)
            {
                int indexClasa = eticheteInputStream.readUnsignedByte();
                // TODO are ros sa-i mai dau locatia?
                ImagineEtichetata imagineEtichetata =
                        new ImagineEtichetata(multimeImagini, new File("x"), indexClasa);

                ArrayList<Double> valoriLiniarizat = new ArrayList<>(nrLinii * nrColoane);

                for(int k = 0; k < nrLinii * nrColoane; k++)
                        valoriLiniarizat.add(
                                dataInputStream.readUnsignedByte() / 255d);

                imagineEtichetata.setValoriLiniarizat(valoriLiniarizat);

                imaginiEtichetate.add(imagineEtichetata);
            }

            dataInputStream.close();
            eticheteInputStream.close();

            return imaginiEtichetate;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
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
