package cosmin.indiciPerformanta.clasificare;

public class MatriceDeConfuzie
{
    /**
     * numarul total de elemente inregistrate in matrice
     * (numarul de clasificari efectuate)
     */
    private int nrTotalElemente = 0;

    /**
     * numele claselor ( corespondenta cu valorile numerice)
     */
    private String[] eticheteleClaselor;

    /**
     * valorile matricei de confuzie
     */
    private int[][] valori;

    /**
     * numarul de clase
     */
    private int nrClase;

    /**
     * pt. formatare  default in cadrul toString
     */
    private static final int DIMENSIUNE_STRING_COLOANA = 9;

    /**
     *
     * @param eticheteleClaselor numele etichetelor, vector primitiv de String, matricea
     *                           va fi generata in cu dimensiunea acestui vector.
     */
    public MatriceDeConfuzie(String[] eticheteleClaselor)
    {
        this.eticheteleClaselor = eticheteleClaselor;
        this.nrClase = eticheteleClaselor.length;

        valori = new int[eticheteleClaselor.length][eticheteleClaselor.length];
    }

    /**
     *
     * @param valoareIesire
     * @param valoareDorita
     * @return
     */
    public int obtineValoareLaPozitia(int valoareDorita, int valoareIesire)
    {
        return valori[valoareDorita][valoareIesire];
    }

    /**
     * @param valoareDorita index-ul real al clasei dorite
     * @param valoareIesire index-ul clasei prezise de rna
     */
    public void incrementeazaElement(int valoareDorita, int valoareIesire)
    {
        valori[valoareDorita][valoareIesire]++;
        nrTotalElemente++;
    }

    /**
     *  Returneaza nr de clasificari Adevarat-Pozitive, acesta aflandu-se in
     * pozitia elementului de pe diagonala principala cu indicele indexClasa.
     * @param indexClasa index-ul clasei pentru care dorim sa aflam numarul de
     *                   clasificari Adevarat-Pozitive
     * @return numarul de clasificari Adevarat-Pozitive pentru clasa indexClasa
     */
    public int obtineAdevaratPozitive(int indexClasa)
    {
        return valori[indexClasa][indexClasa];
    }

    /**
     *  Returneaza nr de clasificari Adevarat-Negative, acesta fiind obtinut
     * prin adunarea tuturor elementelor din matrice, inafara de cele aflate pe
     * linia sau coloana indexClasa.
     * @param indexClasa index-ul clasei pentru care dorim sa aflam numarul de
     *                   clasificari Adevarat-Negative
     * @return numarul de clasificari Adevarat-Negative pentru clasa indexClasa
     */
    public int obtinereAdevaratNegative(int indexClasa)
    {
        int adevaratNegative = 0;

        for(int i = 0; i < nrClase; ++i)
        {
            // nu are cum sa fie adevarat-negativ pt el insusi
            if (i == indexClasa)
                continue;

            for(int j = 0; j < nrClase; ++j)
            {
                if(j == indexClasa)
                    continue;
                adevaratNegative += valori[i][j];
            }
        }

        return adevaratNegative;
    }

    /**
     *  Returneaza numarul de clasificari Fals-Pozitive, acesta fiind obtinut
     * prin adunarea tuturor elementelor de pe coloana cu indicele indexClasa,
     * inafara de cel aflat pe linia cu indicele indexClasa.
     * @param indexClasa index-ul clasei pentru care dorim sa aflam numarul de
     *                   clasificari Fals-Pozitive.
     * @return numarul de clasificari Fals-Pozitive pentru clasa indexClasa.
     */
    public int obtinereFalsPozitive(int indexClasa)
    {
       int falsPozitive = 0;

       for(int i = 0; i < nrClase; ++i)
       {
           if (i == indexClasa)
               continue;
           falsPozitive += valori[i][indexClasa];
       }

       return falsPozitive;
    }

    /**
     *  Returneaza numarul de clasificari Fals-Negative, acesta fiind obtinut
     * prin adunarea valorilor de pe linia cu indicele indexClasa, inafara de
     * elementul de pe coloana index clasa.
     * @param indexClasa index-ul clasei pentru care dorim sa aflam numarul de
     *                   clasificari Fals-Negative.
     * @return numarul de clasificari Fals-Negative.
     */
    public int obtinereFalsNegative(int indexClasa)
    {
        int falsNegative = 0;

        for(int i = 0; i < nrClase; ++i)
        {
            if(i == indexClasa)
                continue;
            falsNegative += valori[indexClasa][i];
        }

        return falsNegative;
    }

    /**
     *
     * @param linie
     * @param coloana
     * @return
     */
    public int obtinereValoarePozitie(int linie, int coloana)
    {
        return valori[linie][coloana];
    }

    // ------------------ Setteri si Getteri ----------------------------

    public int getNrTotalElemente()
    {
        return nrTotalElemente;
    }

    public void setNrTotalElemente(int nrTotalElemente)
    {
        this.nrTotalElemente = nrTotalElemente;
    }

    public String[] getEticheteleClaselor()
    {
        return eticheteleClaselor;
    }

    public void setEticheteleClaselor(String[] eticheteleClaselor)
    {
        this.eticheteleClaselor = eticheteleClaselor;
    }

    public int[][] getValori()
    {
        return valori;
    }

    public void setValori(int[][] valori)
    {
        this.valori = valori;
    }

    public int getNrClase()
    {
        return nrClase;
    }

    public void setNrClase(int nrClase)
    {
        this.nrClase = nrClase;
    }

    // ----- Sfarsit Setteri si Getteri -----------------

    @Override
    public String toString()
    {
        StringBuilder output = new StringBuilder();
        int dimensiuneColoana = DIMENSIUNE_STRING_COLOANA;

        // ajustare la dimensiunea reala a etichetelor
        for(String eticheta: this.eticheteleClaselor)
            dimensiuneColoana = Math.max(dimensiuneColoana, eticheta.length());

        output.append(String.format("%1$" + dimensiuneColoana +"s", ""));
        for(String eticheta: eticheteleClaselor)
            output.append(String.format("%1$" + dimensiuneColoana +"s", ""));
        output.append("\n");

        for(int i = 0; i < valori.length; ++i)
        {
            output.append(String.
                    format("%1$" + dimensiuneColoana +"s", eticheteleClaselor[i]));

            for(int j = 0; j < valori[0].length; ++j)
                output.append(String.
                        format("%1$" + dimensiuneColoana +"s", valori[i][j]));
            output.append("\n");
        }

        return output.toString();
    }

}
