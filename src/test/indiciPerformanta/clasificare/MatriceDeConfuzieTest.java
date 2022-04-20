package test.indiciPerformanta.clasificare;

import cosmin.indiciPerformanta.clasificare.MatriceDeConfuzie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatriceDeConfuzieTest
{

    @Test
    void incrementeazaElement()
    {
        // 3 clase
        String[] etichete = {"Unu", "Doi", "Trei"};
        MatriceDeConfuzie matriceDeConfuzie = new MatriceDeConfuzie(etichete);

        matriceDeConfuzie.incrementeazaElement(1, 2);

        assertEquals(1, matriceDeConfuzie.obtinereValoarePozitie(1, 2));
        assertEquals(3, matriceDeConfuzie.getNrClase());
    }
}