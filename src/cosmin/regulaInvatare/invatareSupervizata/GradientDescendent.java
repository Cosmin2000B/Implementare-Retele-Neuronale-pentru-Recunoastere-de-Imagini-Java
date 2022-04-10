package cosmin.regulaInvatare.invatareSupervizata;

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
    private double inertie = 0.9d;

    int nrMaximEpoci = 10;
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
        if(!(this.getReteaNeuronala() instanceof ReteaNeuronalaFeedForward))
            throw new IllegalArgumentException("Nu este permisa decat antrenarea cu GradientDescendent"
            + " a RNA de tip FeedForward!");

        if(this.getMultimeAntrenament() == null)
            throw new IllegalStateException("Nu este incarcata nicio multime de antrenament!");

        if(!(this.getMultimeAntrenament() instanceof MultimeImagini))
            throw new IllegalStateException("Momentant, nu suporta decat MultimeImagini!");

        // nr epoci efectuate
        int nrEpociEfectuate = 0;

        // eroarea curenta a sistemului
        double eroareCurenta = 0;

        while(nrEpociEfectuate < nrMaximEpoci && eroareCurenta > eroareAdmisa)
        {
            // la fiecare epoca, rearanjam aleator elementele
            MultimeImagini.amestecaAleator(((MultimeImagini) this.getMultimeAntrenament())
                    .getImaginiAntrenament());

            for(int i = 0;
                i < ((MultimeImagini) this.getMultimeAntrenament()).getImaginiAntrenament().size(); ++i)
            {
                this.pregatesteInputIesiriRetea(i);

                ((ReteaNeuronalaFeedForward) this.getReteaNeuronala()).executaPropagare();
                ((ReteaNeuronalaFeedForward) this.getReteaNeuronala()).
                        executaRetropropagare(this.dimensiuneSubmutlime);

            }
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
}
