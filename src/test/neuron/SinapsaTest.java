package test.neuron;

import cosmin.functiiActivare.ReLU;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinapsaTest
{

    @Test
    void getValoareIesire()
    {
        // 2 neuroni, vom acorda primului neuron
        // o valoare de iesire
        Neuron n1 = new Neuron();
        Neuron n2 = new Neuron();
        n1.setValoareIesire(0.9644d);

        Sinapsa s1 = new Sinapsa(n1, n2);
        n2.setValoareIntrare(s1.getValoareIesire());
        assertEquals(n1.getValoareIesire() * s1.getPondere(),n2.getValoareIntrare());

        // --------------------------

        Neuron n3 = new Neuron();
        Neuron n4 = new Neuron();
        n3.setValoareIesire(0.432);

        Sinapsa s2 = new Sinapsa(n3, n4);
        s2.setPondere(0.88);
        n4.setValoareIntrare(s2.getValoareIesire());

        assertEquals(0.38016, n4.getValoareIntrare());
    }

    public void toStringTest()
    {
        Neuron n1 = new Neuron();
        n1.setNumeIdentificare("Neuronul 1");

        Neuron n2 = new Neuron(new ReLU());
        n2.setNumeIdentificare("Neuronul 2");

        Sinapsa s1 = new Sinapsa(n1, n2, 0.21d);

        System.out.println(s1);
    }

    @Test
    void actualizeazaPondere()
    {
        Sinapsa sinapsa = new Sinapsa(new Neuron(), new Neuron(), 0d);
        sinapsa.setDeltaPondere(-0.9);
        sinapsa.setPondere(0d);
        sinapsa.actualizeazaPondere(1, 0.9);
        assertEquals(0.09d, sinapsa.getPondere(), Math.pow(10, -3));

        assertEquals(-0.09, sinapsa.getPenultimaDeltaPondere(), Math.pow(10, -3));
        sinapsa.setDeltaPondere(0.2d);
        sinapsa.actualizeazaPondere(1, 0.9);
        assertEquals(-0.061d, sinapsa.getPenultimaDeltaPondere(), Math.pow(10, -3));
        assertEquals(0.151d, sinapsa.getPondere(), Math.pow(10, -2));
        sinapsa.setDeltaPondere(0.8);
        sinapsa.actualizeazaPondere(1, 0.9);
        assertEquals(0.0251d, sinapsa.getPenultimaDeltaPondere(),
                Math.pow(10, -3));
        assertEquals(0.12596d, sinapsa.getPondere(),
                Math.pow(10, -3));

    }

    @Test
    void testActualizeazaPondere()
    {
    }
}