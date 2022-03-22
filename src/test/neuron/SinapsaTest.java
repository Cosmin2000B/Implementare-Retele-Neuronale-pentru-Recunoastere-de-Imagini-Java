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
}