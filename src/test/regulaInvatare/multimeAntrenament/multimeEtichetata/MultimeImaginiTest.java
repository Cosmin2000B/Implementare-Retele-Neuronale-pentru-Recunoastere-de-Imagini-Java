package test.regulaInvatare.multimeAntrenament.multimeEtichetata;

import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MultimeImaginiTest
{

    @Test
    static void citesteMultimeImagini()
    {
        citsteMultimeImaginiTest
                ("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\cifar10",
                        3);
        citsteMultimeImaginiTest
                ("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_png",
                        1);

        citsteMultimeImaginiTest
                ("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_fashion",
                        1);
    }

    static MultimeImagini citsteMultimeImaginiTest(String locatieMemorie, int nrCanaleCulori)
    {
        return MultimeImagini.citesteMultimeImagini(locatieMemorie, nrCanaleCulori);
    }

    @Test
    void amestecaAleator()
    {
        MultimeImagini multimeImagini =
                citsteMultimeImaginiTest("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_png",
                1);

        MultimeImagini.amestecaAleator(multimeImagini.getImaginiAntrenament());
    }

    @Test
    void testAmestecaAleator()
    {
    }

    @Test
    void etichete()
    {
        HashMap<Integer, String> corespEtichete = new HashMap<>();
        for(int i = 0; i < 6; ++i)
            corespEtichete.put(i, Integer.toString(i));

        MultimeImagini multimeImagini =
                new MultimeImagini(new File(""), corespEtichete, 1);

        for(int i = 0; i < 6; ++i)
            assertEquals(1, multimeImagini.getEtichete().get(i).get(i));
    }
}