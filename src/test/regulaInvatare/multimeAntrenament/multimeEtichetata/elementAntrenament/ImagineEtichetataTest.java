package test.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament;

import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.elementAntrenament.ImagineEtichetata;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ImagineEtichetataTest
{

    @Test
    void getValori()
    {
        HashMap<Integer, String> s = new HashMap<Integer, String >();
        MultimeImagini multimeImagini = new MultimeImagini(new File(""), s, 1);
        ImagineEtichetata img =
                new ImagineEtichetata(multimeImagini,
                        new File
                                ("F:\\Mein\\Proiecte\\Java\\CititorDataset1\\src\\res\\mnist_png\\training\\1\\3.png"),
                        4);
    }

    @Test
    void getValoriLiniarizat()
    {
    }

    @Test
    void getIndexClasa()
    {
    }
}