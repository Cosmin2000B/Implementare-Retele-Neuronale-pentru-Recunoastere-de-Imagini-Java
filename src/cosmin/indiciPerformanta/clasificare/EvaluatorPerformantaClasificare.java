package cosmin.indiciPerformanta.clasificare;

import cosmin.indiciPerformanta.EvaluatorPerformanta;

import java.util.ArrayList;

import static java.lang.Math.*;

/**
 *
 */
public abstract class EvaluatorPerformantaClasificare implements EvaluatorPerformanta<MatriceDeConfuzie>
{
    //todo vezi hashmap
    private final String[] eticheteClasa;
    // valoare a iesirii dupa care consideram daca predictia == 1 / == 0
    private double prag = 0.51d;
    MatriceDeConfuzie matriceDeConfuzie;

    /**
     * utilizare interna pt. super(eticheteClasa)
     * @param eticheteClasa etichetele claselor
     */
    private EvaluatorPerformantaClasificare(String[] eticheteClasa)
    {
        this.eticheteClasa = eticheteClasa;
        this.matriceDeConfuzie = new MatriceDeConfuzie(eticheteClasa);
    }

    /**
     *     Varianta standard a metodei proceseazaRezultatRna este
     *     pentru clasificare binara
     * @param valoriIesire valorile neuronilor de pe stratul de iesire
     * @param valoriDorite valorile dorite pentru exemplul respectiv
     */
    @Override
    public void proceseazaRezultatRna(ArrayList<Double> valoriIesire,
                                      ArrayList<Double> valoriDorite)
    {
        boolean clasaDorita = (valoriDorite.get(0) > 0);
        boolean clasaIesire = (valoriIesire.get(0) > prag);

        if( clasaIesire && clasaDorita )
            // adevarat pozitiv
            matriceDeConfuzie.incrementeazaElement(0, 0);
        else if( !clasaIesire && clasaDorita )
            // fals negativ
            matriceDeConfuzie.incrementeazaElement(0, 1);
        else if(clasaIesire) // daca se ajunge aici, automat !clasaDorita
            // fals pozitiva
            matriceDeConfuzie.incrementeazaElement(1, 0);
        else
            // adevarat negativ
            matriceDeConfuzie.incrementeazaElement(1, 1);
    }

    @Override
    public MatriceDeConfuzie getRezultat()
    {
        return this.matriceDeConfuzie;
    }

    @Override
    public void reseteazaEvaluator()
    {
        this.matriceDeConfuzie = new MatriceDeConfuzie(eticheteClasa);
    }

    // ----------------- Setteri si Getteri -----------------------

    public String[] getEticheteClasa()
    {
        return eticheteClasa;
    }

    public double getPrag()
    {
        return prag;
    }

    public void setPrag(double prag)
    {
        this.prag = prag;
    }

    public MatriceDeConfuzie getMatriceDeConfuzie()
    {
        return matriceDeConfuzie;
    }

    public void setMatriceDeConfuzie(MatriceDeConfuzie matriceDeConfuzie)
    {
        this.matriceDeConfuzie = matriceDeConfuzie;
    }

    // ----------- Sfarist Setteri si Getteri ---------------

    /**
     *
     */
    public static final class ClasificareBinara extends EvaluatorPerformantaClasificare
    {
        public static final String[] ETICHETE_BINARE = new String[]{"Adevarat", "Fals"};

        /**
         *
         * @param prag
         */
        public ClasificareBinara(double prag)
        {
           super(ETICHETE_BINARE);
           this.setPrag(prag);
        }
    }

    /**
     *
     */
    public static class ClasificareMultiClasa extends EvaluatorPerformantaClasificare
    {
        public ClasificareMultiClasa(String[] eticheteClase)
        {
            super(eticheteClase);
        }

        /**
         *
         * @param valoriIesire valorile neuronilor de pe stratul de iesire
         * @param valoriDorite valorile dorite pentru exemplul respectiv
         * @throws IllegalArgumentException
         */
        @Override
        public void proceseazaRezultatRna(ArrayList<Double> valoriIesire,
                                          ArrayList<Double> valoriDorite)
        {
            int clasaIesire = 0;
            int clasaDorita = 0;

            if(valoriDorite.size() != valoriIesire.size())
                throw new IllegalArgumentException("Numarul de elemente din" +
                        "vectorul de valoriDorite este diferit de cel din" +
                        "valoriIesire!");

            for(int i = 1; i < valoriDorite.size(); ++i)
            {
                //todo de vazut cu treapta

                if(valoriDorite.get(i) > valoriDorite.get(clasaDorita))
                    clasaDorita = i;
                if(valoriIesire.get(i) > valoriIesire.get(clasaIesire))
                    clasaIesire = i;
            }

            matriceDeConfuzie.
                    incrementeazaElement(clasaDorita, clasaIesire);
        }

    }
}
