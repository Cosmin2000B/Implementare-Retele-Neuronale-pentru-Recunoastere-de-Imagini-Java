package cosmin.regulaInvatare.invatareSupervizata;

import cosmin.indiciPerformanta.clasificare.EvaluatorPerformantaClasificare;
import cosmin.indiciPerformanta.clasificare.MetriciPerformantaClasificare;
import cosmin.regulaInvatare.RegulaInvatare;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeAntrenamentEtichetata;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament.ImagineEtichetata;
import cosmin.reteleNeuronale.PerceptronMultiStrat;
import cosmin.reteleNeuronale.ReteaNeuronalaFeedForward;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class GradientDescendent extends RegulaInvatare<MultimeAntrenamentEtichetata>
{
    /**
     * 1 - default -> online
     * > 1 && < dimMultime - > antrenament cu sublumtimi (nr iteratii intr - o epoca: dimMultime / dimSubmultime
     * == dimMultime -> actualizare dupa o epoca ( epoca = iteratie)
     */
    private int dimensiuneSubmutlime = 1;
    /**
     *  Considerat cel mai important hiperparametru al unei retele neuronale, rata de invatare
     * determina dimensiunea pasilor pe care ii facem catre o solutie optima. O valoare inadecvata
     * a ratei de invatare poate duce catre o convergenta lenta sau chiar poate impiedica total
     * convergenta.
     */
    private double rataInvatare = 0.1d;
    // initial 0 => fara inertie
    private double inertie = 0.9d;
    // pentru oprire fortata a procesului de antrenare
    boolean opresteAntrenament = false;

    int nrMaximEpoci = 100;
    double eroareAdmisa = 0.005d;

    // format pt afisarea valorilor
    private final static DecimalFormat df = new DecimalFormat(".##");

    public GradientDescendent()
    {}

    public void pregatesteInputIesiriRetea(int indexElement)
    {
        ImagineEtichetata elemetCurent = ((MultimeImagini) this.getMultimeAntrenament()).
                getImaginiAntrenament().get(indexElement);

        ArrayList<Double> valoriDorite =
                this.getMultimeAntrenament().getEtichetaCorespunzatoare(elemetCurent.getIndexClasa());

        // stabilire input si valori de iesire asteptate
        if(this.getReteaNeuronala() instanceof PerceptronMultiStrat)
        {
            ((PerceptronMultiStrat) this.getReteaNeuronala()).
                    getStratDeIntrare().stabilesteInputRetea(elemetCurent.getValoriLiniarizat());
            ((PerceptronMultiStrat) this.getReteaNeuronala()).getStratDeIesire().setValoriDorite(valoriDorite);
        }// < ----- la if
        // todo pt convolutionala
    }

    public void pregatesteInputIesiriReteaTestare(int indexElement)
    {
        ImagineEtichetata elemetCurent = ((MultimeImagini) this.getMultimeAntrenament()).
                getImaginiTestare().get(indexElement);

        ArrayList<Double> valoriDorite =
                this.getMultimeAntrenament().getEtichetaCorespunzatoare(elemetCurent.getIndexClasa());

        // stabilire input si valori de iesire asteptate
        if(this.getReteaNeuronala() instanceof PerceptronMultiStrat)
        {
            ((PerceptronMultiStrat) this.getReteaNeuronala()).
                    getStratDeIntrare().stabilesteInputRetea(elemetCurent.getValoriLiniarizat());
            ((PerceptronMultiStrat) this.getReteaNeuronala()).getStratDeIesire().setValoriDorite(valoriDorite);
        }// < ----- la if
        // todo pt convolutionala
    }

    @Override
    public void antreneaza()
    {
        if(this.getReteaNeuronala() == null)
            throw new IllegalArgumentException("Nicio retea neuronala nu este ascoiata" +
                    "acestei reguli de invatare!");

        if(this.getMultimeAntrenament() == null)
            throw new IllegalStateException("Nu este incarcata nicio multime de antrenament!");

        if(!(this.getMultimeAntrenament() instanceof MultimeImagini))
            throw new IllegalStateException("Momentant, nu suporta decat MultimeImagini!");

        // nr epoci efectuate
        int nrEpociEfectuate = 0;

        // eroarea curenta a sistemului
        double eroareCurenta = 1;

        EvaluatorPerformantaClasificare clasificare = null;
        if(getMultimeAntrenament().getCorespondentaEticheta().size() > 2)
        {
            // ================= Clasificare multi-clasa ===========================
            clasificare
                    = new EvaluatorPerformantaClasificare.ClasificareMultiClasa
                    (getMultimeAntrenament().getCorespondentaEticheta().values().toArray(new String[0]));
        }
        else // ===================== Clasificare binara ===========================
        {
            clasificare =
                    new EvaluatorPerformantaClasificare.ClasificareBinara(0.51d);
        }


        while(nrEpociEfectuate < nrMaximEpoci && eroareCurenta > eroareAdmisa)
        {
            // la fiecare epoca, rearanjam aleator elementele
            MultimeImagini.amestecaAleator(((MultimeImagini) this.getMultimeAntrenament())
                    .getImaginiAntrenament());
            // resetam eroarea la fiecare epoca
            eroareCurenta = 0;

            for(int i = 0;
                i < ((MultimeImagini) this.getMultimeAntrenament()).getImaginiAntrenament().size(); ++i)
            {
                // ===== oprire fortata ======
                if(opresteAntrenament)
                {
                    // resetare oprire
                    opresteAntrenament = false;
                    return;
                }
                // ===========================

                this.pregatesteInputIesiriRetea(i);

                this.getReteaNeuronala().executaPropagare();
                clasificare.proceseazaRezultatRna(getReteaNeuronala().getValoriIesire(),
                        this.getMultimeAntrenament().
                                getEtichetaCorespunzatoare(((MultimeImagini) getMultimeAntrenament())
                                        .getImaginiAntrenament().get(i).getIndexClasa()));

                eroareCurenta +=  this.getReteaNeuronala().getEroareaRetelei();
                 this.getReteaNeuronala().
                        executaRetropropagare(this.dimensiuneSubmutlime);

                // am terminat o iteratie
                if(i % dimensiuneSubmutlime == 0)
                {
                    // actualizam ponderile
                     this.getReteaNeuronala().executaOptimizare(rataInvatare, inertie);

                    // eroareCurenta /= dimensiuneSubmutlime; // eroare la nivel de submultime
                    // todo de sters ---- elminat acolada if --------------------------------
                    /*
                    System.out.println("Epoca " + nrEpociEfectuate + ", Iteratia " + i / dimensiuneSubmutlime +
                            ", Eroarea retelei:" + eroareCurenta);
                    System.out.print("Erori neuroni: ");
                    for (Neuron neuron: ((PerceptronMultiStrat) this.getReteaNeuronala()).getStratDeIesire().getNeuroni())
                        System.out.print(neuron.getEroareNeuron() + " ");
                    System.out.println();
                    System.out.println("Valori de iesire strat de iesire:");
                    for (Neuron neuron: ((PerceptronMultiStrat) this.getReteaNeuronala()).getStratDeIesire().getNeuroni())
                        System.out.print(neuron.getValoareIesire() + " ");
                    System.out.println();
                    System.out.println("Valori de intrare strat de iesire:");
                    for (Neuron neuron: ((PerceptronMultiStrat) this.getReteaNeuronala()).getStratDeIesire().getNeuroni())
                        System.out.print(neuron.getValoareIntrare() + " ");
                    System.out.println();
                    System.out.println("Valori intrare strat Ascuns 0:");
                    for (Neuron neuron: ((PerceptronMultiStrat) this.getReteaNeuronala()).getStraturiAscunse().
                            get(0).getNeuroni())
                        System.out.print(neuron.getValoareIntrare() + " ");
                    System.out.println();
                    System.out.println("Valori iesire strat Intrare:");
                    for (Neuron neuron: ((PerceptronMultiStrat) this.getReteaNeuronala()).getStratDeIntrare().
                            getNeuroni())
                        System.out.print(neuron.getValoareIesire() + " ");
                    System.out.println();
                    System.out.println();
                     */
                    // todo sterge intervalul asta ----------------------------------
                }
            } // s-a terminat o epoca

            if(getMultimeAntrenament() instanceof MultimeImagini)
                // eroare la nivel de epoca
                eroareCurenta /= ((MultimeImagini) getMultimeAntrenament()).getImaginiAntrenament().size();

            // todo de sters
            System.out.println("Epoca " + nrEpociEfectuate + ", eroare: " + eroareCurenta
            + ", acuratete: " + df.format(MetriciPerformantaClasificare.getAcurateteMedie(clasificare.getMatriceDeConfuzie()))
            + ", precizie: " +  df.format(MetriciPerformantaClasificare.getPrecizieMedie(clasificare.getMatriceDeConfuzie())));
            // -----------------------
            nrEpociEfectuate++;

            // resetam dupa fiecare epoca
            clasificare.reseteazaEvaluator();
        }

    }

    @Override
    public void testeaza()
    {
        // todo eventual calculat si metrici specifici

        if(this.getReteaNeuronala() == null)
            throw new IllegalArgumentException("Nicio retea neuronala nu este ascoiata" +
                    "acestei reguli de invatare!");

        if(this.getMultimeAntrenament() == null)
            throw new IllegalStateException("Nu este incarcata nicio multime de antrenament!");

        if(!(this.getMultimeAntrenament() instanceof MultimeImagini))
            throw new IllegalStateException("Momentant, nu suporta decat MultimeImagini!");

        EvaluatorPerformantaClasificare clasificare = null;
        if(getMultimeAntrenament().getCorespondentaEticheta().size() > 2)
        {
            // ================= Clasificare multi-clasa ===========================
            clasificare
                    = new EvaluatorPerformantaClasificare.ClasificareMultiClasa
                    (getMultimeAntrenament().getCorespondentaEticheta().values().toArray(new String[0]));
        }
        else // ===================== Clasificare binara ===========================
        {
            clasificare =
                    new EvaluatorPerformantaClasificare.ClasificareBinara(0.51d);
        }

        // procentul de exemple corect clasificate
        double corectClasificate = 0d;

        int dimensiuneMultimeTestare = ((MultimeImagini) getMultimeAntrenament()).getImaginiTestare().size();

        for(int i = 0; i < dimensiuneMultimeTestare; ++i)
        {
            pregatesteInputIesiriReteaTestare(i);
            this.getReteaNeuronala().executaPropagare();

            clasificare.proceseazaRezultatRna(getReteaNeuronala().getValoriIesire(),
                    this.getMultimeAntrenament().
                            getEtichetaCorespunzatoare(((MultimeImagini) getMultimeAntrenament())
                                    .getImaginiTestare().get(i).getIndexClasa()));

            int indiceClasaDorita = 0;
            double valMaxClsObtinuta = 0d;
            int indiceClasaObtinuta = 0;

            ArrayList<Double> valoriDorite = this.getMultimeAntrenament().
                    getEtichetaCorespunzatoare(((MultimeImagini) getMultimeAntrenament())
                            .getImaginiTestare().get(i).getIndexClasa());

            for(int j = 0; j < getMultimeAntrenament().getCorespondentaEticheta().size(); ++j)
            {
                if(getReteaNeuronala().getValoriIesire().get(j) > valMaxClsObtinuta)
                {
                    valMaxClsObtinuta = getReteaNeuronala().getValoriIesire().get(j);
                    indiceClasaObtinuta = j;
                }

                if(valoriDorite.get(j) == 1)
                    indiceClasaDorita = j;
            }

            if(indiceClasaDorita == indiceClasaObtinuta)
                corectClasificate ++;

            this.getReteaNeuronala().reseteazaStare();
        }

        corectClasificate /= dimensiuneMultimeTestare;

        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Procent clasificari corecte: " + df.format(corectClasificate * 100) +"%");

        System.out.println(clasificare.getMatriceDeConfuzie());
    }

    // ---------------- Setteri si Getteri --------------------

    public int getDimensiuneSubmutlime()
    {
        return dimensiuneSubmutlime;
    }

    public void setDimensiuneSubmutlime(int dimensiuneSubmutlime)
    {
        this.dimensiuneSubmutlime = dimensiuneSubmutlime;
    }

    public double getRataInvatare()
    {
        return rataInvatare;
    }

    public void setRataInvatare(double rataInvatare)
    {
        this.rataInvatare = rataInvatare;
    }

    public double getInertie()
    {
        return inertie;
    }

    public void setInertie(double inertie)
    {
        this.inertie = inertie;
    }

    public int getNrMaximEpoci()
    {
        return nrMaximEpoci;
    }

    public void setNrMaximEpoci(int nrMaximEpoci)
    {
        this.nrMaximEpoci = nrMaximEpoci;
    }

    public boolean isOpresteAntrenament()
    {
        return opresteAntrenament;
    }

    public void setOpresteAntrenament(boolean opresteAntrenament)
    {
        this.opresteAntrenament = opresteAntrenament;
    }

    public double getEroareAdmisa()
    {
        return eroareAdmisa;
    }

    public void setEroareAdmisa(double eroareAdmisa)
    {
        this.eroareAdmisa = eroareAdmisa;
    }

    @Override
    public ReteaNeuronalaFeedForward getReteaNeuronala()
    {
        return (ReteaNeuronalaFeedForward) super.getReteaNeuronala();
    }

    public void setReteaNeuronala(ReteaNeuronalaFeedForward reteaNeuronala)
    {
        super.setReteaNeuronala(reteaNeuronala);
        reteaNeuronala.setRegulaInvatare(this);
    }
}
