package test.regulaInvatare.invatareSupervizata;

import cosmin.functiiActivare.LeakyReLU;
import cosmin.functiiActivare.ReLU;
import cosmin.functiiActivare.sigmoide.Logistica;
import cosmin.functiiActivare.sigmoide.TangentaHiperbolica;
import cosmin.neuron.Neuron;
import cosmin.regulaInvatare.invatareSupervizata.GradientDescendent;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament.ImagineEtichetata;
import cosmin.reteleNeuronale.PerceptronMultiStrat;
import cosmin.reteleNeuronale.ReteaNeuronala;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.LoginContext;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
        gradientDescendent.setDimensiuneSubmutlime(32);
        gradientDescendent.setRataInvatare(0.001);
        gradientDescendent.setInertie(0.7d);
        gradientDescendent.setNrMaximEpoci(100);


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
            ((GradientDescendent) perceptronMultiStrat.getRegulaInvatare()).pregatesteInputIesiriRetea(0);
            assert (perceptronMultiStrat.getStratDeIesire().getValoriDorite().get(0) == 1);

            // test atribuire valori
            ImagineEtichetata imagineEtichetata = new ImagineEtichetata(new MultimeImagini(new File(""),
                    new HashMap<Integer, String>(), 1),
                    new File("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_png\\training\\0\\1.png"),
                    0);

            ArrayList<Double> valoriImagine = imagineEtichetata.getValoriLiniarizat();
            for(int i = 0; i < perceptronMultiStrat.getStratDeIntrare().getNeuroni().size(); ++i)
                assertEquals(valoriImagine.get(i), perceptronMultiStrat.
                        getStratDeIntrare().getNeuroni().get(i).getValoareIesire());
        }
    }

    //@Test
    void antreneaza()
    {
        PerceptronMultiStrat perceptronMultiStrat = genereazaPerceptronMultiStrat();
        perceptronMultiStrat.getStraturiAscunse().get(0).setFunctieActivare(new ReLU());
        //perceptronMultiStrat.getStraturiAscunse().get(1).setFunctieActivare(new ReLU());
        perceptronMultiStrat.antreneaza();
    }
}