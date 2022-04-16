package cosmin.indiciPerformanta.clasificare;

/**
 * Clasa container metrici de clasificare
 */
public final class MetriciPerformantaClasificare
{
    private double adevaratPozitive;
    private double adevaratNegative;
    private double falsPozitive;
    private double falsNegative;
    ///**
     //* Coeficientul de corelatie r ( Karl Pearson )
     //*   - valori dezirabile : 1.0
     //*   - valori indezirabile: < 0.2
     //*/
    //private double coeficientCorelatie;
    private double nrTotalElemente;
    private String etichetaClasa;

    public MetriciPerformantaClasificare(int adevaratPozitive, int adevaratNegative,
                                         int falsPozitive, int falsNegative)
    {
        this.adevaratPozitive = adevaratPozitive;
        this.adevaratNegative = adevaratNegative;
        this.falsPozitive = falsPozitive;
        this.falsNegative = falsNegative;

        this.nrTotalElemente =
                adevaratPozitive + adevaratNegative + falsPozitive + falsNegative;
    }

    /**
     * Calculeaza acuratetea clasificarii.
     *   formula: (ap +an) / nrTotalElemente
     * @return acuratetea clasificarii.
     */
    public double obtineAcurateteClasificare()
    {
        return (adevaratPozitive + adevaratNegative) / nrTotalElemente;
    }

    /**
     *  Se poate calcula si drept 1 - acurateteClasificare
     *    formula: (fp + fn) / nrTotalElemente
     * @return rata erorii.
     */
    public double obtinereRataErorii()
    {
        return (falsPozitive + falsNegative) /  nrTotalElemente;
    }

    /**
     *   Calculeaza si returneaza precizia de clasificare, reprezentand
     * numarul total de exemple clasificate adevarat-pozitiv impartit la
     * numarul total de clasificari pozitive ( ap / (ap+fp) )
     * @return precizia de clasificare
     */
    public double obtinerePrecizieClasificare()
    {
        return adevaratPozitive / (adevaratPozitive + falsPozitive);
    }

    /**
     *   Calculeaza si returneaza senzitivitatea clasificatorului, reprezentand numarul
     * de exemple corect clasificate pozitiv-adevarat impartit la numarul total de ex.
     * clsificate pozitiv (!(!p)) = p => ap / (ap + fn)
     * @return  senzitivittatea clasificatorului
     */
    public double obtinereSenzitivitate()
    {
        return adevaratPozitive / (adevaratPozitive + falsNegative);
    }

    /**
     *
     */
    public static MetriciPerformantaClasificare[] genereazaMetriciClasificare
    (MatriceDeConfuzie matriceDeConfuzie)
    {
        int nrClase = matriceDeConfuzie.getNrClase();

        // clasificare binara
        if(nrClase == 2)
        {
            MetriciPerformantaClasificare[] metriciPerformantaClasificare =
                    new MetriciPerformantaClasificare[1];
            String[] eticheteleClaselor = matriceDeConfuzie.getEticheteleClaselor();

            int adevaratPozitive = matriceDeConfuzie.obtinereValoarePozitie(0, 0);
            int adevaratNegative = matriceDeConfuzie.obtinereValoarePozitie(1, 1);
            int falsPozitive = matriceDeConfuzie.obtinereValoarePozitie(1, 0);
            int falsNegative = matriceDeConfuzie.obtinereValoarePozitie(0, 1);

            metriciPerformantaClasificare[0] = new MetriciPerformantaClasificare
                    (adevaratPozitive, adevaratNegative, falsPozitive, falsNegative);
            metriciPerformantaClasificare[0].setEtichetaClasa(eticheteleClaselor[0]);

            return metriciPerformantaClasificare;
        }
        else // ============= clasificare multi-clasa =============================
        {
            MetriciPerformantaClasificare[] metriciPerformantaClasificare =
                    new MetriciPerformantaClasificare[nrClase];
            String[] eticheteClase = matriceDeConfuzie.getEticheteleClaselor();

            for(int indexClasa = 0; indexClasa < matriceDeConfuzie.getNrClase(); ++indexClasa)
            {
                int adevaratPozitive = matriceDeConfuzie.obtineAdevaratPozitive(indexClasa);
                int adevaratNegative = matriceDeConfuzie.obtinereAdevaratNegative(indexClasa);
                int falsPozitive = matriceDeConfuzie.obtinereFalsPozitive(indexClasa);
                int falsNegative = matriceDeConfuzie.obtinereFalsNegative(indexClasa);

                metriciPerformantaClasificare[indexClasa] =
                        new MetriciPerformantaClasificare(adevaratPozitive, adevaratNegative,
                                falsPozitive, falsNegative);

                metriciPerformantaClasificare[indexClasa].
                        setEtichetaClasa(eticheteClase[indexClasa]);
            }

            return metriciPerformantaClasificare;
        }
    }

    // ------------------ Setteri si Getteri ----------------------------

    public double getAdevaratPozitive()
    {
        return adevaratPozitive;
    }

    public void setAdevaratPozitive(double adevaratPozitive)
    {
        this.adevaratPozitive = adevaratPozitive;
    }

    public double getAdevaratNegative()
    {
        return adevaratNegative;
    }

    public void setAdevaratNegative(double adevaratNegative)
    {
        this.adevaratNegative = adevaratNegative;
    }

    public double getFalsPozitive()
    {
        return falsPozitive;
    }

    public void setFalsPozitive(double falsPozitive)
    {
        this.falsPozitive = falsPozitive;
    }

    public double getFalsNegative()
    {
        return falsNegative;
    }

    public void setFalsNegative(double falsNegative)
    {
        this.falsNegative = falsNegative;
    }

    /*
    public double getCoeficientCorelatie()
    {
        return coeficientCorelatie;
    }

    public void setCoeficientCorelatie(double coeficientCorelatie)
    {
        this.coeficientCorelatie = coeficientCorelatie;
    }
     */

    public double getNrTotalElemente()
    {
        return nrTotalElemente;
    }

    public void setNrTotalElemente(double nrTotalElemente)
    {
        this.nrTotalElemente = nrTotalElemente;
    }

    public String getEtichetaClasa()
    {
        return etichetaClasa;
    }

    public void setEtichetaClasa(String etichetaClasa)
    {
        this.etichetaClasa = etichetaClasa;
    }
}
