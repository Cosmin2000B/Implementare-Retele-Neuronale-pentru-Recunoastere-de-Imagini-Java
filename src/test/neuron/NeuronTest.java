package test.neuron;

import cosmin.functiiActivare.ReLU;
import cosmin.functiiActivare.sigmoide.Logistica;
import cosmin.functiiActivare.sigmoide.TangentaHiperbolica;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
class NeuronTest
{
    @Test
    void adaugaSinapsaIntrare()
    {
        Neuron n1 = new Neuron();

        Neuron n2 = new Neuron();
        Neuron n3 = new Neuron();

        // testam si exceptii specifice ------------------
        Sinapsa s23 = null;
        Exception exceptie1 =
                assertThrows(IllegalArgumentException.class,
                        () ->
                        {
                            n1.adaugaSinapsaIntrare(s23);
                        });
        String mesaj1 = exceptie1.getMessage();
        assert(mesaj1.equals("Sinapsa de intrare este nula!") );

        Sinapsa s32 = new Sinapsa(n2,n3);
        Exception exceptie2 =
                assertThrows(IllegalArgumentException.class,
                        ()->
                        {
                            n1.adaugaSinapsaIntrare(s32);
                        });
        String mesaj2 = exceptie2.getMessage();
        assert(mesaj2.equals("Nu se poate adauga aceasta sinapsa de intrare!"
                + " Neuronul destinatar nu coincide cu neuronul curent."));
        // ---------------------------------------------

        Sinapsa s21 = new Sinapsa(n2, n1);
        Sinapsa s31 = new Sinapsa(n3, n1);

        n1.adaugaSinapsaIntrare(s21);
        n1.adaugaSinapsaIntrare(s31);

        assertEquals(2,n1.getSinapseIntrare().size());

        Neuron n4 = new Neuron();
        Sinapsa s41 = new Sinapsa(n4, n1);

        n1.adaugaSinapsaIntrare(s41);

        assertEquals(3, n1.getSinapseIntrare().size());
    }

    @Test
    void adaugaSinapsaIesire()
    {
        Neuron n1 = new Neuron();

        Neuron n2 = new Neuron();
        Sinapsa s12 = new Sinapsa(n1,n2);
        Neuron n3 = new Neuron();
        Sinapsa s13 = new Sinapsa(n1,n3);

        n1.adaugaSinapsaIesire(s12);
        n1.adaugaSinapsaIesire(s13);

        assertEquals(2, n1.getSinapseIesire().size());
    }

    @Test
    void calculeazaIntrare()
    {
        Neuron n1 = new Neuron();
        n1.setPondereBias(0.21);

        Neuron n2 = new Neuron();
        n2.setValoareIesire(0.45);
        Sinapsa s21 = new Sinapsa(n2, n1, 0.76);
        n1.adaugaSinapsaIntrare(s21);

        Neuron n3 = new Neuron();
        n3.setValoareIesire(0.29);
        Sinapsa s31 = new Sinapsa(n3, n1, 0.33);
        n1.adaugaSinapsaIntrare(s31);

        // verificare la precizie de 4 zecimale
        assertEquals(0.2277,n1.getValoareIntrare(), .0001);
    }

    @Test
    void calculeazaIesire()
    {
        Neuron n1 = new Neuron();
        n1.setPondereBias(1.24);

        Neuron n2 = new Neuron();
        n2.setValoareIesire(4.321);
        Sinapsa s21 = new Sinapsa(n2, n1);
        s21.setPondere(3.29);

        Neuron n3 = new Neuron();
        n3.setValoareIesire(0.756);
        Sinapsa s31 = new Sinapsa(n3, n1);
        s31.setPondere(2.13);

        n1.adaugaSinapsaIntrare(s21);
        n1.adaugaSinapsaIntrare(s31);

        assertEquals(0.99999953,n1.getValoareIesire(), 0.00001);
    }

    @Test
    void SimulareRNA3x3x3x1() // pag 12, caiet
    {
        // Strat intrare
        Neuron n11 = new Neuron();
        n11.setValoareIesire(2);
        Neuron n12 = new Neuron();
        n12.setValoareIesire(3);
        Neuron n13 = new Neuron();
        n13.setValoareIesire(1);

        // Strat ascuns I
        Neuron n21 = new Neuron(new ReLU());
        n21.getBias().setPondere(0.45);
        Neuron n22 = new Neuron(new ReLU());
        n22.getBias().setPondere(0.25);
        Neuron n23 = new Neuron(new ReLU());
        n23.getBias().setPondere(0.15);

        // Strat ascuns II
        Neuron n31 = new Neuron(new ReLU());
        n31.getBias().setPondere(0.17);
        Neuron n32 = new Neuron(new ReLU());
        n32.getBias().setPondere(0.62);
        Neuron n33 = new Neuron(new ReLU());
        n33.getBias().setPondere(0.98);

        // Strat iesire
        Neuron n41 = new Neuron(new Logistica());
        n41.getBias().setPondere(0.59);

        // adauga sinapse strat ascuns I
        n21.adaugaSinapsaIntrare(n11, 0.24);
        n21.adaugaSinapsaIntrare(n12, 0.35);
        n21.adaugaSinapsaIntrare(n13, 0.82);

        n22.adaugaSinapsaIntrare(n11, 0.51);
        n22.adaugaSinapsaIntrare(n12, 0.61);
        n22.adaugaSinapsaIntrare(n13, 0.75);

        n23.adaugaSinapsaIntrare(n11, 0.2);
        n23.adaugaSinapsaIntrare(n12, 0.9);
        n23.adaugaSinapsaIntrare(n13, 0.79);

        // adauga ponderi strat ascuns II
        n31.adaugaSinapsaIntrare(n21, 0.13);
        n31.adaugaSinapsaIntrare(n22, 0.5);
        n31.adaugaSinapsaIntrare(n23, 0.12);

        n32.adaugaSinapsaIntrare(n21, 0.42);
        n32.adaugaSinapsaIntrare(n22, 0.3);
        n32.adaugaSinapsaIntrare(n23, 0.7);

        n33.adaugaSinapsaIntrare(n21, 0.61);
        n33.adaugaSinapsaIntrare(n22, 0.46);
        n33.adaugaSinapsaIntrare(n23, 0.143);

        // adauga sinapsa strat

        n41.adaugaSinapsaIntrare(n31, 0.5);
        n41.adaugaSinapsaIntrare(n32, 0.46);
        n41.adaugaSinapsaIntrare(n33, 0.98);

        assertEquals(1.9,n21.getValoareIesire(), 0.1);
        assertEquals(3.35,n22.getValoareIesire(), 0.01);
        assertEquals(3.74,n23.getValoareIesire(), 0.01);

        assertEquals(2.2008,n31.getValoareIesire(), 0.0001);
        assertEquals(3.801,n32.getValoareIesire(), 0.001);
        assertEquals(2.25482, n33.getValoareIesire(), 0.00001);

        assertEquals(0.98866638208, n41.getValoareIesire(), 0.000000001);
    }

    void toStringTest()
    {
        Neuron n1 = new Neuron(new TangentaHiperbolica());
        n1.setNumeIdentificare("Neuron 1");

        Neuron n2 = new Neuron(new ReLU());
        n2.setNumeIdentificare("Neuron 2");

        Neuron n3 = new Neuron();
        n3.setNumeIdentificare("Neuron 3");

        Neuron n4 = new Neuron(new TangentaHiperbolica());
        n4.setNumeIdentificare("Neuron 4");

        Neuron n5 = new Neuron(new ReLU());
        n5.setNumeIdentificare("Neuron 5");

        n3.adaugaSinapsaIntrare(n1);
        n3.adaugaSinapsaIntrare(n2);

        n3.adaugaSinapsaIesire(n4);
        n3.adaugaSinapsaIesire(n5);

        System.out.println(n3);
    }
}