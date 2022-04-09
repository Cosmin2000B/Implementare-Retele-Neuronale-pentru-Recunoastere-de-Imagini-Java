package cosmin.operatii_io.imagine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class ObtinereValoriImagineRGB
{
    /**
     *
     * @param locatieMemorie
     * @return
     */
    public static LinkedList<double[][]> toValoriSubunitare(String locatieMemorie)
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

        double[][] rosu = new double[randuri][coloane];
        double[][] verde = new double[randuri][coloane];
        double[][] albastru = new double[randuri][coloane];

        for(int i = 0; i < randuri; ++i)
            for(int j = 0; j < coloane; ++j)
            {
                int valoare = imagine.getRGB(j, i);
                Color culoare = new Color(valoare, true);
                rosu[i][j] = culoare.getRed() / 255d;
                verde[i][j] = culoare.getGreen() / 255d;
                albastru[i][j] = culoare.getBlue() / 255d;
            }
        LinkedList<double[][]> canaleCuloare = new LinkedList<>();
        canaleCuloare.add(rosu);
        canaleCuloare.add(verde);
        canaleCuloare.add(albastru);
        return canaleCuloare;
    }
}
