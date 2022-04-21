package cosmin.operatii_io.imagine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ObtinereValoriImagineAN
{
    /**
     *
     * @param locatieMemorie
     * @return
     */
    public static double[][] toValoriSubunitare(String locatieMemorie)
    {
        BufferedImage imagine = null;
        File locatie = null;

        try
        {
            locatie = new File(locatieMemorie);
            imagine = ImageIO.read(locatie);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        int randuri = imagine.getHeight();
        int coloane = imagine.getWidth();

        double[][] valori = new double[randuri][coloane];

        for(int i = 0; i < randuri; ++i)
            for(int j = 0; j < coloane; ++j)
            {
                int valoare = imagine.getRGB(j, i);
                Color culoare = new Color(valoare, true);
                valori[i][j] =
                        (culoare.getRed() * 0.299d + culoare.getGreen() * 0.587d +
                                culoare.getBlue() * 0.114d) / 255d;
            }

        return valori;
    }

    /**
     *
     * @param imagine
     * @return
     */
    public static double[][] toValoriSubunitare(BufferedImage imagine)
    {
        int randuri = imagine.getHeight();
        int coloane = imagine.getWidth();

        double[][] valori = new double[randuri][coloane];

        for(int i = 0; i < randuri; ++i)
            for(int j = 0; j < coloane; ++j)
            {
                int valoare = imagine.getRGB(j, i);
                Color culoare = new Color(valoare, true);
                valori[i][j] =
                        (culoare.getRed() * 0.299d + culoare.getGreen() * 0.587d +
                                culoare.getBlue() * 0.114d) / 255d;
            }

        return valori;
    }
}
