package test.functiiActivare;

import cosmin.functiiActivare.ReLU;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReLUTest {

    @Test
    void valoareFunctie()
    {
        ReLU r1 = new ReLU();

        assertEquals(0, r1.valoareFunctie(-0.45d));
        assertEquals(2.34d, r1.valoareFunctie(2.34d));
        assertEquals(1, r1.valoareFunctie(1d));
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