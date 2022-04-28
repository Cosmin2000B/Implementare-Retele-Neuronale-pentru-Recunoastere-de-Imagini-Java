package test.reteleNeuronale;

import cosmin.functiiActivare.FunctieLiniaraIdentitate;
import cosmin.functiiActivare.ReLU;
import cosmin.functiiActivare.Softmax;
import cosmin.functiiActivare.sigmoide.Logistica;
import cosmin.neuron.Neuron;
import cosmin.operatii_io.IoDateSerializate;
import cosmin.regulaInvatare.invatareSupervizata.GradientDescendent;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament.ImagineEtichetata;
import cosmin.reteleNeuronale.PerceptronMultiStrat;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.MediaSumeiPatratelorErorilor;
import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PerceptronMultiStratTest
{
    PerceptronMultiStrat incarcaArhitectura1()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                new PerceptronMultiStrat(3, 1, 2, 3);
        perceptronMultiStrat.getStratDeIesire().setValoriDorite(new ArrayList<>(List.of(0.1d)));
        perceptronMultiStrat.setRegulaInvatare(new GradientDescendent());

        // stabilire valori
        //strat intrare
        perceptronMultiStrat.getStratDeIntrare().stabilesteInputRetea(new ArrayList<>(Arrays.asList(2d, 3d, 1d)));
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(0).getSinapseIesire().get(0).setPondere(0.24);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(0).getSinapseIesire().get(1).setPondere(0.51);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(0).getSinapseIesire().get(2).setPondere(0.2);

        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(1).getSinapseIesire().get(0).setPondere(0.35);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(1).getSinapseIesire().get(1).setPondere(0.61);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(1).getSinapseIesire().get(2).setPondere(0.9);

        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(2).getSinapseIesire().get(0).setPondere(0.82);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(2).getSinapseIesire().get(1).setPondere(0.75);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(2).getSinapseIesire().get(2).setPondere(0.79);

        // strat ascuns 1
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(0).getSinapseIesire().get(0).setPondere(0.13);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(0).getSinapseIesire().get(1).setPondere(0.42);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(0).getSinapseIesire().get(2).setPondere(0.61);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(0).getBias().setPondere(0.45);

        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(1).getSinapseIesire().get(0).setPondere(0.5);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(1).getSinapseIesire().get(1).setPondere(0.3);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(1).getSinapseIesire().get(2).setPondere(0.46);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(1).getBias().setPondere(0.25);

        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(2).getSinapseIesire().get(0).setPondere(0.12);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(2).getSinapseIesire().get(1).setPondere(0.7);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(2).getSinapseIesire().get(2).setPondere(0.143);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(2).getBias().setPondere(0.15);

        // strat ascuns 2
        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(0).getSinapseIesire().get(0).setPondere(0.5);
        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(0).getBias().setPondere(0.17);

        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(1).getSinapseIesire().get(0).setPondere(0.46);
        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(1).getBias().setPondere(0.62);

        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(2).getSinapseIesire().get(0).setPondere(0.98);
        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(2).getBias().setPondere(0.98);

        // strat de iesire
        perceptronMultiStrat.getStratDeIesire().getNeuroni().get(0).getBias().setPondere(0.59);

        return perceptronMultiStrat;
    }

    PerceptronMultiStrat incarcaArhitectura2()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                new PerceptronMultiStrat(3,3, 2, 3);

        // strat intrare
        perceptronMultiStrat.getStratDeIntrare().stabilesteInputRetea(new ArrayList<>(Arrays.
                asList(0.1, 0.2, 0.7)));
        for(Neuron neuronIntrare: perceptronMultiStrat.getStratDeIntrare().getNeuroni())
            neuronIntrare.getSinapseIesire().clear();
        // ---- strat ascuns 1, defaul: ReLU
        for(Neuron neuronIntrare: perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni())
        {
            neuronIntrare.getSinapseIntrare().clear();
            neuronIntrare.getSinapseIesire().clear();
        }
        // -- strat ascuns 2, logistica
        for(Neuron neuronIntrare: perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni())
        {
            neuronIntrare.getSinapseIesire().clear();
            neuronIntrare.getSinapseIntrare().clear();
        }

        perceptronMultiStrat.getStraturiAscunse().get(1).setFunctieActivare(new Logistica());
        // -- strat de iesire, default: Softmax, calcul eroare: Entropie incrucisata
        perceptronMultiStrat.getStratDeIesire().setValoriDorite(new ArrayList<>(Arrays.
                asList(1d, 0d, 0d)));
        for(Neuron neuron: perceptronMultiStrat.getStratDeIesire().getNeuroni())
            neuron.getSinapseIntrare().clear();

        perceptronMultiStrat.getStratDeIntrare().stabilesteStratDens(new ArrayList<>(Arrays.
                asList(0.1, 0.2, 0.3, 0.3, 0.2, 0.7, 0.4, 0.3, 0.9)));
        perceptronMultiStrat.getStraturiAscunse().get(0).stabilesteStratDens(new ArrayList<>(Arrays.
                asList(0.2, 0.3, 0.5, 0.3, 0.5, 0.7, 0.6, 0.4, 0.8)));
        perceptronMultiStrat.getStraturiAscunse().get(1).stabilesteStratDens(new ArrayList<>(Arrays.
                asList(0.1, 0.4, 0.8, 0.3, 0.7, 0.2, 0.5, 0.2, 0.9)));

        for(Neuron neuron: perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni())
            neuron.getBias().setPondere(-1.0);
        for(Neuron neuron: perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni())
            neuron.getBias().setPondere(-1.0);
        for(Neuron neuron: perceptronMultiStrat.getStratDeIesire().getNeuroni())
            neuron.getBias().setPondere(-1.0);

        return perceptronMultiStrat;
    }

    PerceptronMultiStrat incarcaArhitectura3()
    {
        PerceptronMultiStrat perceptronMultiStrat = new PerceptronMultiStrat
                (2, 2, 1,2);

        perceptronMultiStrat.getStraturiAscunse().get(0).setFunctieActivare(new Logistica());
        perceptronMultiStrat.getStratDeIesire().setFunctieActivare(new ReLU());
        perceptronMultiStrat.getStratDeIesire().setFunctieDeCost(new MediaSumeiPatratelorErorilor());

        perceptronMultiStrat.getStratDeIntrare().stabilesteInputRetea
                (new ArrayList<>(Arrays.asList(1d, 2d)));
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(0).getSinapseIesire().get(0).setPondere(0.1);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(0).getSinapseIesire().get(1).setPondere(0.2);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(1).getSinapseIesire().get(0).setPondere(0.3);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(1).getSinapseIesire().get(1).setPondere(0.4);

        perceptronMultiStrat.getStraturiAscunse().get(0).
                getNeuroni().get(0).getSinapseIesire().get(0).setPondere(0.5);
        perceptronMultiStrat.getStraturiAscunse().get(0).
                getNeuroni().get(0).getSinapseIesire().get(1).setPondere(0.6);
        perceptronMultiStrat.getStraturiAscunse().get(0).
                getNeuroni().get(0).getBias().setPondere(0d);
        perceptronMultiStrat.getStraturiAscunse().get(0).
                getNeuroni().get(1).getSinapseIesire().get(0).setPondere(0.7);
        perceptronMultiStrat.getStraturiAscunse().get(0).
                getNeuroni().get(1).getSinapseIesire().get(1).setPondere(0.8);
        perceptronMultiStrat.getStraturiAscunse().get(0).
                getNeuroni().get(1).getBias().setPondere(0d);

        perceptronMultiStrat.getStratDeIesire().getNeuroni().get(0).getBias().setPondere(0d);
        perceptronMultiStrat.getStratDeIesire().getNeuroni().get(1).getBias().setPondere(0d);
        perceptronMultiStrat.getStratDeIesire().setValoriDorite(new ArrayList<>(Arrays.asList(0d, 1d)));

        return perceptronMultiStrat;
    }

    // --------------------------------------------------

    @Test
    void executaPropagare1()
    {
        // arhitectura 1
        PerceptronMultiStrat perceptronMultiStrat = incarcaArhitectura1();
        perceptronMultiStrat.executaPropagare();

        assertEquals(3.801,perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(1).getValoareIesire(),
                Math.pow(10, -8));
        assertEquals(2.2008,perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(0).getValoareIesire(),
                Math.pow(10, -8));
        assertEquals(0.98866638208, perceptronMultiStrat.getStratDeIesire().getNeuroni().get(0).getValoareIesire(),
                Math.pow(10, -8));
    }

    @Test
    void executaPropagare2()
    {
        // arhitectura 2
        PerceptronMultiStrat perceptronMultiStrat = incarcaArhitectura2();
        perceptronMultiStrat.executaPropagare();

        assertEquals(0.19858,
                perceptronMultiStrat.getStratDeIesire().getNeuroni().get(0).getValoareIesire(),
                Math.pow(10, -3));
        assertEquals(0.28559,
                perceptronMultiStrat.getStratDeIesire().getNeuroni().get(1).getValoareIesire(),
                Math.pow(10, -3));
        assertEquals(0.51583,
                perceptronMultiStrat.getStratDeIesire().getNeuroni().get(2).getValoareIesire(),
                Math.pow(10, -3));

        assertEquals(1.61723375,
                perceptronMultiStrat.getStratDeIesire().getEroareaRetelei(), Math.pow(10,-8));
    }

    @Test
    void retropropagareStratIesire()
    {
        // arhitectura 2
        PerceptronMultiStrat perceptronMultiStrat = incarcaArhitectura2();
        perceptronMultiStrat.executaPropagare();
        // pt a imparti la 1 (sa nu modifice rezultatul)
        perceptronMultiStrat.retropropagareStratIesire(1);

        StratDeIesire stratDeIesire = perceptronMultiStrat.getStratDeIesire();
        assertEquals(-0.80155d,
                stratDeIesire.getNeuroni().get(0).getEroareNeuron(), Math.pow(10, -5));
        assertEquals(0.28559d,
                stratDeIesire.getNeuroni().get(1).getEroareNeuron(), Math.pow(10, -3));
        assertEquals(0.51583d,
                stratDeIesire.getNeuroni().get(2).getEroareNeuron(), Math.pow(10, -3));
        assertEquals(-0.7525202237d,
                stratDeIesire.getNeuroni().get(0).getSinapseIntrare().get(0).getDeltaPondere(),
                Math.pow(10, -4));
        assertEquals(-0.7525202237,
                stratDeIesire.getNeuroni().get(0).getSinapseIntrare().get(1).getDeltaPondere(),
                Math.pow(10, -2));
        assertEquals(-0.7871896707,
                stratDeIesire.getNeuroni().get(0).getSinapseIntrare().get(2).getDeltaPondere(),
                Math.pow(10, -5));
    }

    @Test
    void executaRetropropagare()
    {
    }

    @Test
    void executaPropagare3()
    {
        // arhitectura 3 - retea mai mica
        PerceptronMultiStrat perceptronMultiStrat = incarcaArhitectura3();
        perceptronMultiStrat.executaPropagare();

        assertEquals(0.668818, perceptronMultiStrat.getStraturiAscunse().
                get(0).getNeuroni().get(0).getValoareIesire(), Math.pow(10, -3));
        assertEquals(0.71094, perceptronMultiStrat.getStraturiAscunse().
                get(0).getNeuroni().get(1).getValoareIesire(), Math.pow(10, -1));

        assertEquals(0.832067,
                perceptronMultiStrat.getStratDeIesire().getNeuroni().get(0).getValoareIesire()
        , Math.pow(10, -1));
        assertEquals(0.9799428,
                perceptronMultiStrat.getStratDeIesire().getNeuroni().get(1).getValoareIesire()
                , Math.pow(10, -1));

        assertEquals(0.17077, perceptronMultiStrat.getStratDeIesire().getEroareaRetelei(),
                Math.pow(10, -2));
    }

    @Test
    void executaPropagare4() // testare atribuire valori de iesire
    {
        // arhitectura 2
        PerceptronMultiStrat perceptronMultiStrat = incarcaArhitectura2();
        perceptronMultiStrat.executaPropagare();

        ArrayList<Double> valAsteptate = new ArrayList<>(
                Arrays.asList(0.1985d, 0.28559d, 0.51583d));

        ArrayList<Double> valReale = perceptronMultiStrat.getValoriIesire();

        for(int i = 0; i < valAsteptate.size(); ++i)
            assertEquals(valAsteptate.get(i), valReale.get(i),
                    Math.pow(10, -3));
    }

    @Test
    void retropropagareStratIesire_Arhitectura3()
    {
        PerceptronMultiStrat perceptronMultiStrat = incarcaArhitectura3();
        perceptronMultiStrat.executaPropagare();

        perceptronMultiStrat.retropropagareStratIesire(1);
        assertEquals(0.42, perceptronMultiStrat.
                getStratDeIesire().getNeuroni().get(0).getEroareNeuron(),
                Math.pow(10, -1));
    }

    @Test
    void retropropagareStratAscuns()
    {
        PerceptronMultiStrat perceptronMultiStrat = incarcaArhitectura2();
        // sa nu modifice rezultatele
        perceptronMultiStrat.retropropagareStratIesire(1);
        perceptronMultiStrat.retropropagareStratAscuns(1,
                perceptronMultiStrat.getStraturiAscunse().get(1));
        assertEquals(0.034, perceptronMultiStrat.
                getStraturiAscunse().get(1).getNeuroni().get(0).getSinapseIntrare().get(0).getDeltaPondere()
        ,Math.pow(10, -3));
    }

    @Test
    void executaOptimizare()
    {
    }

    //interna, testat nominal
    private ImagineEtichetata initImagine(String locatieMemorie)
    {
        return new ImagineEtichetata(
                new MultimeImagini(new File("x"), new HashMap<>(), 1),
                new File(locatieMemorie),
                5);
    }
    /**
     * Testare nominala prin propagarea inputului si confruntarea rezultatului
     */
    @Test
    void testeazaNominal()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                (PerceptronMultiStrat) IoDateSerializate.
                        fin("F:\\Mein\\Proiecte\\Java\\rna_antrenate\\Incercare 2\\mlp_mnist_antrenat3.ser");
        ImagineEtichetata imagineEtichetata =
                initImagine("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_png\\testing\\8\\61.png");

        assert perceptronMultiStrat != null;
        perceptronMultiStrat.reseteazaStare();
        perceptronMultiStrat.getStratDeIntrare().stabilesteInputRetea(imagineEtichetata.getValoriLiniarizat());
        perceptronMultiStrat.executaPropagare();

        /*
        for(int i = 0; i < perceptronMultiStrat.getValoriIesire().size(); ++i)
            System.out.println("Clasa " + i + ": " +
                    perceptronMultiStrat.getValoriIesire().get(i) * 100 + "%");
         */

        // peste 90% sigur ca e 8
        assert(perceptronMultiStrat.getValoriIesire().get(8) > 0.9);

        // ===============================================================================

        imagineEtichetata =
                initImagine("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_png\\testing\\5\\132.png");
        perceptronMultiStrat.reseteazaStare();
        perceptronMultiStrat.getStratDeIntrare().stabilesteInputRetea(imagineEtichetata.getValoriLiniarizat());
        perceptronMultiStrat.executaPropagare();

        // peste 90% sigur ca e 5
        assert(perceptronMultiStrat.getValoriIesire().get(5) > 0.9);

        // =============================================================================

        imagineEtichetata =
                initImagine("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_png\\testing\\2\\764.png");
        perceptronMultiStrat.reseteazaStare();
        perceptronMultiStrat.getStratDeIntrare().stabilesteInputRetea(imagineEtichetata.getValoriLiniarizat());
        perceptronMultiStrat.executaPropagare();

        // peste 90% sigur ca e 2
        assert(perceptronMultiStrat.getValoriIesire().get(2) > 0.9);
    }

    @Test
    void executaPropagare()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                new PerceptronMultiStrat(2, 1, 0,0);

        perceptronMultiStrat.getStratDeIesire().setFunctieActivare(new FunctieLiniaraIdentitate());

        perceptronMultiStrat.getStratDeIntrare().
                stabilesteInputRetea(new ArrayList<>(Arrays.asList(2d, 1d)));
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(0).getSinapseIesire().get(0).setPondere(1);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(1).getSinapseIesire().get(0).setPondere(1);

        perceptronMultiStrat.executaPropagare();
        assertEquals(3, perceptronMultiStrat.getValoriIesire().get(0));

        perceptronMultiStrat.reseteazaStare();

        perceptronMultiStrat.getStratDeIntrare().
                stabilesteInputRetea(new ArrayList<>(Arrays.asList(1d, 1d)));
        perceptronMultiStrat.executaPropagare();
        assertEquals(2, perceptronMultiStrat.getValoriIesire().get(0));
    }
}