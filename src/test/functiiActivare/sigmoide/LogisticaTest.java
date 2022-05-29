package test.functiiActivare.sigmoide;

import cosmin.functiiActivare.FunctieActivare;
import cosmin.functiiActivare.sigmoide.Logistica;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogisticaTest
{

    @Test
    void valoareFunctie1()
    {
        FunctieActivare testLogisitica =
                new Logistica();
        assertEquals(0.442752d,
                testLogisitica.valoareFunctie(-0.23),
                Math.pow(10, -4));
    }

    @Test
    void valoareFunctie2()
    {
        FunctieActivare testLogisitica =
                new Logistica();
        assertEquals(0.5d,
                testLogisitica.valoareFunctie(0d),
                Math.pow(10, -4));
    }

    @Test
    void valoareDerivata1()
    {
        FunctieActivare testLogisitica =
                new Logistica();
        assertEquals(-0.2829d,
                testLogisitica.valoareDerivata(-0.23d),
                Math.pow(10, -4));
    }

}