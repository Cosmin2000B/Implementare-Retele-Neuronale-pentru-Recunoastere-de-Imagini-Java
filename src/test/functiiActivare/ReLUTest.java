package test.functiiActivare;

import cosmin.functiiActivare.ReLU;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReLUTest {

    @Test
    void valoareFunctie1()
    {
        ReLU r1 = new ReLU();

        assertEquals(0, r1.valoareFunctie(-0.45d));
        assertEquals(2.34d, r1.valoareFunctie(2.34d));
        assertEquals(1, r1.valoareFunctie(1d));
    }

    @Test
    void valoareFunctie2()
    {
        ReLU r1 = new ReLU();

        assertEquals(0.1e-2, r1.valoareFunctie(0.1e-2));
        assertEquals(0.1e-5, r1.valoareFunctie(0.1e-5));
        assertEquals(0, r1.valoareFunctie(-0.1e-6));
        assertEquals(48.000000000000000000000000000000000002,
                r1.valoareFunctie(48.000000000000000000000000000000000002));
    }

    @Test
    void valoareDerivata()
    {
        ReLU r1 = new ReLU();

        assertEquals(0, r1.valoareDerivata(0d));
        assertEquals(0,r1.valoareDerivata(-0.234d));
        assertEquals(1,r1.valoareDerivata(0.1d));
        assertEquals(1, r1.valoareDerivata(2.43d));
    }
}