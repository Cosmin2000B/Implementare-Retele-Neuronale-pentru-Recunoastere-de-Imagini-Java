package test.neuron;

import cosmin.neuron.Bias;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BiasTest
{
    @Test
    void calculeazaValoare()
    {
        Bias b1 = new Bias();
        double rezultat1 = (-1) * b1.getPondere();

        Bias b2 = new Bias(2.38d);

        assertEquals(rezultat1,b1.calculeazaValoare());
        assertEquals(-2.38,b2.calculeazaValoare());
    }

    public void toStringTest()
    {
        Bias b1 = new Bias(0.33d);
        System.out.println(b1);
    }
}