package test.regulaInvatare.multimeAntrenament.multimeEtichetata;

import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import org.junit.jupiter.api.Test;

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
}