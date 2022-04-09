package test.regulaInvatare.multimeAntrenament.multimeEtichetata;

import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultimeImaginiTest
{

    @Test
    void citesteMultimeImagini()
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

    // todo de sters
    static void  citsteMultimeImaginiTest(String locatieMemorie, int nrCanaleCulori)
    {
        MultimeImagini multimeImagini =
                MultimeImagini.citesteMultimeImagini(locatieMemorie, nrCanaleCulori);
    }
}