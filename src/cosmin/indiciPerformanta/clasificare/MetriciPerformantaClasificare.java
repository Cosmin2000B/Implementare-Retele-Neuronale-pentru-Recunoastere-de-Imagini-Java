package cosmin.indiciPerformanta.clasificare;

/**
 *   Clasa container metrici de clasificare
 * la nivel de clasa.
 */
public final class MetriciPerformantaClasificare
{
    /**
     * numarul de date clasificate corect ca apartinand
     * clasei de interes
     */
    private double adevaratPozitive;
    /**
     * numarul de date clasificate corect ca neapartinand
     * clasei de interes
     */
    private double adevaratNegative;
    /**
     * numarul de date clasificate incorect ca apartinand
     * clasei de interes
     */
    private double falsPozitive;
    /**
     * numarul de date clasificate incorect ca neapartinand
     * clasei de interes
     */
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
     *  Calculeaza metricii de performanta pentru fiecare clasa in parte.
     * @param matriceDeConfuzie matricea de confuzie aferenta clasificarilor
     *                          facute de RNA.
     * @return un vector primitiv de MetriciPerformantaClasificare clasificare, fiecare
     * element de tipul MetriciPerformantaClasificare corespunde prin index-ul din vector
     * unei clase.
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

            // pentru fiecare clasa
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

    // todo de vzt cum merge
    /**
     * acuratetea tuturor clasificarilor, calculata prin media sumei
     * acuratetii claselor
     */
    public static double getAcurateteMedie(MatriceDeConfuzie matriceDeConfuzie)
    {
        double acurateteMedie = 0d;

        for(int i = 0; i < matriceDeConfuzie.getNrClase(); ++i)
        {
            MetriciPerformantaClasificare metriciPerformantaClasificare =
                    new MetriciPerformantaClasificare(
                            matriceDeConfuzie.obtineAdevaratPozitive(i),
                            matriceDeConfuzie.obtinereAdevaratNegative(i),
                            matriceDeConfuzie.obtinereFalsPozitive(i),
                            matriceDeConfuzie.obtinereFalsNegative(i));
            acurateteMedie += metriciPerformantaClasificare.obtineAcurateteClasificare();
        }

        return acurateteMedie /= matriceDeConfuzie.getNrClase();
    }

    /**
     * acuratetea tuturor clasificarilor, calculata prin media sumei
     * acuratetii claselor
     */
    public static double getPrecizieMedie(MatriceDeConfuzie matriceDeConfuzie)
    {
        double acurateteMedie = 0d;

        for(int i = 0; i < matriceDeConfuzie.getNrClase(); ++i)
        {
            MetriciPerformantaClasificare metriciPerformantaClasificare =
                    new MetriciPerformantaClasificare(
                            matriceDeConfuzie.obtineAdevaratPozitive(i),
                            matriceDeConfuzie.obtinereAdevaratNegative(i),
                            matriceDeConfuzie.obtinereFalsPozitive(i),
                            matriceDeConfuzie.obtinereFalsNegative(i));
            acurateteMedie += metriciPerformantaClasificare.obtinerePrecizieClasificare();
        }

        return acurateteMedie /= matriceDeConfuzie.getNrClase();
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

    /**
     *  returneaza numarul total de clasificari efectuate
     * @return numarul de clasificari efectuate
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
