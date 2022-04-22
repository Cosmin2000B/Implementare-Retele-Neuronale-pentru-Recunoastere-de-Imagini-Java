package cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament.utilImagine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *  Clasa cu metode statice pentru redimensionarea imaginilor. Pentru
 * obtinerea valorilor numerice din imagini se foloses metodele pt imagini
 * din cadrul operatii_io
 * @see cosmin.operatii_io.imagine.ObtinereValoriImagineAN
 * @see cosmin.operatii_io.imagine.ObtinereValoriImagineRGB
 */
public class RedimensionareImagine
{
    /**
     * 
     * @param imagine imaginea ce se doreste redimensionata
     * @param nrLinii numarul de linii pe care il va avea imaginea redimensionata
     * @param nrColoane numarul de coloane pe care il va avea imaginea redimensionata
     * @return imaginea redimensionata
     */
    public static BufferedImage redimensioneazaImagine(BufferedImage imagine, int nrLinii, int nrColoane)
    {
        BufferedImage imagRedimensionata =
                new BufferedImage(nrColoane, nrLinii, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = imagRedimensionata.createGraphics();

        graphics2D.drawImage(imagine, 0, 0  ,nrColoane, nrLinii, null);
        graphics2D.dispose();

        return imagRedimensionata;
    }

    /**
     * 
     * @param imagine imaginea ce se doreste redimensionata
     * @param nrLinii numarul de linii pe care il va avea imaginea redimensionata
     * @param nrColoane numarul de coloane pe care il va avea imaginea redimensionata
     * @return imaginea redimensionata
     */
    public static BufferedImage redimensioneazaImagine(Image imagine, int nrLinii, int nrColoane)
    {
        BufferedImage bufferedImage = (BufferedImage) imagine;
        return redimensioneazaImagine(bufferedImage, nrLinii, nrColoane);
    }
}
