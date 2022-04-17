package cosmin.utilStructuriAlgebrice;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Matrice implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    private int nrLinii;
    private int nrColoane;
    // format pt afisarea valorilor
    private final static DecimalFormat df = new DecimalFormat(".##");

    private double[][] valori;

    public Matrice(int nrLinii, int nrColoane)
    {
        this.nrLinii = nrLinii;
        this.nrColoane = nrColoane;

        this.valori = new double[nrLinii][nrColoane];
    }

    /**
     * @param matricePrimitiva
     * @throws IllegalArgumentException
     */
    public Matrice(double[][] matricePrimitiva)
    {
        if (matricePrimitiva == null || matricePrimitiva.length == 0 || matricePrimitiva[0].length == 0)
            throw new IllegalArgumentException("Matricea nu poate fi nula sau goala!");

        this.nrLinii = matricePrimitiva.length;
        this.nrColoane = matricePrimitiva[0].length;
        this.valori = new double[this.nrLinii][this.nrColoane];

        for (int i = 0; i < matricePrimitiva.length; ++i)
            System.arraycopy
                    (matricePrimitiva[i], 0, this.valori[i],
                            0, matricePrimitiva[0].length);
    }

    /**
     *
     * @param matrice
     */
    public Matrice(@NotNull Matrice matrice)
    {
        this.nrLinii = matrice.nrLinii;
        this.nrColoane = matrice.nrColoane;
        this.valori = matrice.valori;
    }

    public static ArrayList<Double> liniarizare(Matrice matrice)
    {
        if (matrice == null || matrice.getValori() == null || matrice.getValori()[0] == null)
            throw new IllegalArgumentException("Matricea nu poate fi nula sau goala!");

        ArrayList<Double> liniarizat = new ArrayList<>(matrice.getNrLinii() * matrice.getNrColoane());
        // index vector
        int k = 0;

        for (int i = 0; i < matrice.getNrLinii(); ++i)
            for (int j = 0; j < matrice.getNrColoane(); ++j)
                liniarizat.add(matrice.getValori()[i][j]);

        return liniarizat;
    }

    public int getNrLinii()
    {
        return nrLinii;
    }

    public void setNrLinii(int nrLinii)
    {
        this.nrLinii = nrLinii;
    }

    public int getNrColoane()
    {
        return nrColoane;
    }

    public void setNrColoane(int nrColoane)
    {
        this.nrColoane = nrColoane;
    }

    public double[][] getValori()
    {
        return valori;
    }

    public void setValori(double[][] valori)
    {
        this.valori = new double[valori.length][valori[0].length];
        for (int i = 0; i < valori.length; ++i)
            System.arraycopy
                    (valori[i], 0, this.valori[i], 0, valori[0].length);
    }

    /**
     *
     * @param indexLinie
     * @param indexColoana
     * @param valoare
     * @throws IllegalArgumentException
     */
    public void setValoareElement(int indexLinie, int indexColoana, double valoare)
    {
        if(indexLinie > this.nrLinii || indexColoana > this.nrColoane)
            throw new IllegalArgumentException("Nu exista aceasta pozitie in matrice!");

        this.valori[indexLinie][indexColoana] = valoare;
    }

    // ----------------- Sfarsit Getteri si Setteri ------------

    @Override
    public String toString()
    {
        StringBuilder outputBuilder = new StringBuilder();
        for (int i = 0; i < nrLinii; ++i)
        {
            for (int j = 0; j < nrColoane; ++j)
                outputBuilder.append(df.format(this.valori[i][j])).append('\t');
            outputBuilder.append('\n');
        }
        String output = outputBuilder.toString();
        output += '\n';

        return output;
    }
}
