package test.operatiiIO.imagine;

import cosmin.operatii_io.imagine.ObtinereValoriImagineAN;
import cosmin.utilStructuriAlgebrice.Matrice;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ObtinereValoriImagineANTest
{

    // private final static DecimalFormat df = new DecimalFormat(".##");

    @Test
    void toValoriSubunitare()
    {
        toValoriSubunitareTest("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_png\\training\\8\\146.png",
                28, 28);
    }

    public static void toValoriSubunitareTest(String locatieMemorie, int nrLinii, int nrColoane)
    {
        double[][] actual = ObtinereValoriImagineAN.toValoriSubunitare(locatieMemorie);
        assertEquals(nrLinii, actual.length);
        assertEquals(nrColoane, actual[0].length);
    }

    /*
    @Test
    public void testPerformanta()
    {
        File[] fisiere =
                new File("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\cifar10\\train").listFiles();
        ArrayList<File> fisiereElem = new ArrayList<>();
        for (File fisierClasa: fisiere)
            fisiereElem.addAll(Arrays.asList(fisierClasa.listFiles()));

        fisiereElem.forEach(drumElem ->
        {
            ObtinereValoriImagineAN.toValoriSubunitare(drumElem.getPath());
        });
    }
     */
}