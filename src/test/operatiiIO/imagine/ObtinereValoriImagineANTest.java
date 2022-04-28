package test.operatiiIO.imagine;

import cosmin.operatii_io.imagine.ObtinereValoriImagineAN;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament.utilImagine.RedimensionareImagine;
import cosmin.utilStructuriAlgebrice.Matrice;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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

    //@Test // test imagine desenata
    public void testImagineIncarcata()
    {
        try
        {
            BufferedImage imagine = ImageIO.
                    read(new File("F:\\Mein\\Proiecte\\Java\\Imagini Desenate\\test_3.bmp"));
            BufferedImage imRedim  =
                    RedimensionareImagine.redimensioneazaImagine(imagine, 28, 28);
            ImageIO.write(imRedim, "bmp",
                    new File("F:\\Mein\\Proiecte\\Java\\Imagini Desenate\\3_redimensionat.bmp"));
            Matrice matrice = new Matrice(ObtinereValoriImagineAN.toValoriSubunitare(imRedim));

            System.out.println(matrice);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}