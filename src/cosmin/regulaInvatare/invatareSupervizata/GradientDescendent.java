package cosmin.regulaInvatare.invatareSupervizata;

import cosmin.indiciPerformanta.clasificare.EvaluatorPerformantaClasificare;
import cosmin.neuron.Neuron;
import cosmin.regulaInvatare.RegulaInvatare;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeAntrenamentEtichetata;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament.ImagineEtichetata;
import cosmin.reteleNeuronale.PerceptronMultiStrat;
import cosmin.reteleNeuronale.ReteaNeuronalaFeedForward;

import java.util.ArrayList;

// TODO
public class GradientDescendent extends RegulaInvatare<MultimeAntrenamentEtichetata>
{
    // 1 - default -> online
    // > 1 && < dimMultime - > antrenament cu sublumtimi (nr iteratii intr - o epoca: dimMultime / dimSubmultime
    // == dimMultime -> actualizare dupa o epoca ( epoca = iteratie)
    private int dimensiuneSubmutlime = 1;
    private double rataInvatare = 0.1d;
    // initial 0 => fara inertie
    private double inertie = 0.9d;

    int nrMaximEpoci = 100;
    double eroareAdmisa = 0.005d;

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

        EvaluatorPerformantaClasificare.ClasificareMultiClasa clasificareMultiClasa
                = new EvaluatorPerformantaClasificare.ClasificareMultiClasa
                (getMultimeAntrenament().getCorespondentaEticheta().values().toArray(new String[0]));

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
                this.pregatesteInputIesiriRetea(i);

                this.getReteaNeuronala().executaPropagare();
                // inregistrat situatie ---------------------------------
                /*
                if(getReteaNeuronala() instanceof PerceptronMultiStrat)
                    clasificareMultiClasa.
                            proceseazaRezultatRna(((PerceptronMultiStrat) getReteaNeuronala()).
                                            getStratDeIesire().getValoriIesire(),
                                    ((PerceptronMultiStrat) getReteaNeuronala()).
                                    getStratDeIesire().getValoriDorite());
                 */
                // sa se imparta la dimSubmultimeAntrenament ------------------
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
            System.out.println("Epoca " + nrEpociEfectuate + ", eroare: " + eroareCurenta);
            nrEpociEfectuate++;
        }

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
    }
}
