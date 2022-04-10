package test.regulaInvatare.invatareSupervizata;

import cosmin.regulaInvatare.invatareSupervizata.GradientDescendent;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import cosmin.reteleNeuronale.PerceptronMultiStrat;
import cosmin.reteleNeuronale.ReteaNeuronala;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradientDescendentTest
{

    public PerceptronMultiStrat genereazaPerceptronMultiStrat()
    {
        PerceptronMultiStrat perceptronMultiStrat = new PerceptronMultiStrat(784, 10,
                2, 128);
        MultimeImagini multimeImagini = MultimeImagini.
                citesteMultimeImagini("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_png",
                        1);
        GradientDescendent gradientDescendent = new GradientDescendent();
        gradientDescendent.setMultimeAntrenament(multimeImagini);
        gradientDescendent.setNrMaximEpoci(50);


        // todo atentie la implementari
        // de modelat pt reflexie
        perceptronMultiStrat.setRegulaInvatare(gradientDescendent);
        gradientDescendent.setReteaNeuronala(perceptronMultiStrat);

        return perceptronMultiStrat;
    }

    @Test
    void pregatesteInputIesiriRetea()
    {
        PerceptronMultiStrat perceptronMultiStrat = genereazaPerceptronMultiStrat();
        if(perceptronMultiStrat.getRegulaInvatare() instanceof GradientDescendent)
        {
            ((GradientDescendent) perceptronMultiStrat.getRegulaInvatare()).pregatesteInputIesiriRetea(30000);
            assert (perceptronMultiStrat.getStratDeIesire().getValoriDorite().get(4) == 1);
        }
    }

    @Test
    void antreneaza()
    {
    }
}