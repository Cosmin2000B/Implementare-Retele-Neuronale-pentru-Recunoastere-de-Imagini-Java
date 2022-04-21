package test.regulaInvatare.invatareSupervizata;

import cosmin.functiiActivare.LeakyReLU;
import cosmin.functiiActivare.ReLU;
import cosmin.functiiActivare.sigmoide.Logistica;
import cosmin.functiiActivare.sigmoide.TangentaHiperbolica;
import cosmin.neuron.Neuron;
import cosmin.operatii_io.IoDateSerializate;
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
        gradientDescendent.setRataInvatare(0.0001);
        gradientDescendent.setInertie(0.9d);
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
        perceptronMultiStrat.getStraturiAscunse().get(1).setFunctieActivare(new ReLU());
        perceptronMultiStrat.antreneaza();
    }

    //@Test
    public void citesteMNISTliniarizat()
    {
        HashMap<Integer, String> eticheteMNIST = new HashMap<>();
        for(int i = 0; i < 10; ++i)
            eticheteMNIST.put(i, Integer.toString(i));
        MultimeImagini mnist = new MultimeImagini(new File("x"), eticheteMNIST,1);
        mnist.setImaginiAntrenament(MultimeImagini.
                citesteMultimeMNISTLiniarizat(mnist, "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\train-images.idx3-ubyte",
                        "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\train-labels.idx1-ubyte"));
    }

    //@Test
    void antreneazaMNIST()
    {
        PerceptronMultiStrat perceptronMultiStrat = new PerceptronMultiStrat(784, 10,
                2, 128);

        GradientDescendent gradientDescendent = new GradientDescendent();
        gradientDescendent.setDimensiuneSubmutlime(32);
        gradientDescendent.setRataInvatare(0.0001);
        gradientDescendent.setInertie(0.9d);
        gradientDescendent.setNrMaximEpoci(20);

        // todo de setat etichete
        HashMap<Integer, String> eticheteMNIST = new HashMap<>();
        for(int i = 0; i < 10; ++i)
            eticheteMNIST.put(i, Integer.toString(i));
        MultimeImagini mnist = new MultimeImagini(new File("x"), eticheteMNIST,1);
        mnist.setImaginiAntrenament(MultimeImagini.
                citesteMultimeMNISTLiniarizat(mnist, "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\train-images.idx3-ubyte",
                        "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\train-labels.idx1-ubyte"));

        gradientDescendent.setMultimeAntrenament(mnist);
        perceptronMultiStrat.setRegulaInvatare(gradientDescendent);

        perceptronMultiStrat.antreneaza();
        IoDateSerializate.
                fout(perceptronMultiStrat, "F:\\Mein\\Proiecte\\Java\\rna_antrenate\\mlp_mnist.ser");
    }

    //@Test
    public void antreneazaReteaSalvata()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                (PerceptronMultiStrat) IoDateSerializate.fin("F:\\Mein\\Proiecte\\Java\\rna_antrenate\\mlp_mnist.ser");

        GradientDescendent gradientDescendent = new GradientDescendent();
        gradientDescendent.setDimensiuneSubmutlime(64);
        gradientDescendent.setRataInvatare(0.01);
        gradientDescendent.setInertie(0.9d);
        gradientDescendent.setNrMaximEpoci(5);

        HashMap<Integer, String> eticheteMNIST = new HashMap<>();
        for(int i = 0; i < 10; ++i)
            eticheteMNIST.put(i, Integer.toString(i));
        MultimeImagini mnist = new MultimeImagini(new File("x"), eticheteMNIST,1);
        mnist.setImaginiAntrenament(MultimeImagini.
                citesteMultimeMNISTLiniarizat(mnist, "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\train-images.idx3-ubyte",
                        "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\train-labels.idx1-ubyte"));

        gradientDescendent.setMultimeAntrenament(mnist);
        perceptronMultiStrat.setRegulaInvatare(gradientDescendent);

        perceptronMultiStrat.antreneaza();
        IoDateSerializate.
                fout(perceptronMultiStrat, "F:\\Mein\\Proiecte\\Java\\rna_antrenate\\mlp_mnist_antrenament2.ser");
    }

    //@Test
    public void testeazaRna()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                (PerceptronMultiStrat) IoDateSerializate.
                        fin("F:\\Mein\\Proiecte\\Java\\rna_antrenate\\mlp_mnist_antrenament6.ser");

        HashMap<Integer, String> eticheteMNIST = new HashMap<>();
        for(int i = 0; i < 10; ++i)
            eticheteMNIST.put(i, Integer.toString(i));
        MultimeImagini mnist = new MultimeImagini(new File("x"), eticheteMNIST,1);
        mnist.setImaginiTestare(MultimeImagini.
                citesteMultimeMNISTLiniarizat(mnist,
                        "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\t10k-images.idx3-ubyte",
                        "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\t10k-labels.idx1-ubyte"));

        GradientDescendent gradientDescendent = new GradientDescendent();
        gradientDescendent.setMultimeAntrenament(mnist);
        perceptronMultiStrat.setRegulaInvatare(gradientDescendent);
        perceptronMultiStrat.testeaza();
    }

   // @Test
    public void testeazaRnaGenerata()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                new PerceptronMultiStrat(784, 10, 2, 128);

        HashMap<Integer, String> eticheteMNIST = new HashMap<>();
        for(int i = 0; i < 10; ++i)
            eticheteMNIST.put(i, Integer.toString(i));
        MultimeImagini mnist = new MultimeImagini(new File("x"), eticheteMNIST,1);
        mnist.setImaginiTestare(MultimeImagini.
                citesteMultimeMNISTLiniarizat(mnist,
                        "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\t10k-images.idx3-ubyte",
                        "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\t10k-labels.idx1-ubyte"));

        GradientDescendent gradientDescendent = new GradientDescendent();
        gradientDescendent.setMultimeAntrenament(mnist);
        perceptronMultiStrat.setRegulaInvatare(gradientDescendent);
        perceptronMultiStrat.testeaza();
    }

    //@Test
    public void antreneazaReteaSalvata2()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                (PerceptronMultiStrat) IoDateSerializate.
                        fin("F:\\Mein\\Proiecte\\Java\\rna_antrenate\\mlp_mnist_antrenament6.ser");

        GradientDescendent gradientDescendent = new GradientDescendent();
        gradientDescendent.setDimensiuneSubmutlime(32);
        gradientDescendent.setRataInvatare(0.00005);
        gradientDescendent.setInertie(0.5d);
        gradientDescendent.setNrMaximEpoci(15);

        HashMap<Integer, String> eticheteMNIST = new HashMap<>();
        for (int i = 0; i < 10; ++i)
            eticheteMNIST.put(i, Integer.toString(i));
        MultimeImagini mnist = new MultimeImagini(new File("x"), eticheteMNIST, 1);
        mnist.setImaginiAntrenament(MultimeImagini.
                citesteMultimeMNISTLiniarizat(mnist, "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\train-images.idx3-ubyte",
                        "F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_vanilla\\train-labels.idx1-ubyte"));

        gradientDescendent.setMultimeAntrenament(mnist);
        perceptronMultiStrat.setRegulaInvatare(gradientDescendent);

        perceptronMultiStrat.antreneaza();
        IoDateSerializate.
                fout(perceptronMultiStrat, "F:\\Mein\\Proiecte\\Java\\rna_antrenate\\mlp_mnist_antrenament7.ser");
    }
}