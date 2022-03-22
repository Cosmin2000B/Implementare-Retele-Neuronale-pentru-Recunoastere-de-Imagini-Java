package test.straturiNeuronale.straturiNeuronaleLiniare;

import cosmin.functiiActivare.sigmoide.TangentaHiperbolica;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratDeIntrare;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StratDeIntrareTest
{

    @Test
    void stabilireSinapseIesireNeuron() {
    }

    @Test
    void reseteazaPonderi() {
    }

    @Test
    void actualizeazaPonderi() {
    }

    @Test
    void adaugaNeuron()
    {
        Double[] a = {2.0, 1.2, 0.43, 1.32};
        ArrayList<Double> input = new ArrayList<>(Arrays.asList(a));
        StratDeIntrare intrare = new StratDeIntrare(input);
        intrare.adaugaNeuron();

        assertEquals(5,intrare.getNumarNeuroni());
        // ---------------------------

        intrare.adaugaNeuron(new TangentaHiperbolica());

        assert
                (intrare.
                        getNeuroni().
                        get(intrare.getNumarNeuroni() - 1).
                        getFunctieActivare() instanceof TangentaHiperbolica);
    }


    @Test
    void eliminaNeuron() {
    }

    @Test
    void testEliminaNeuron() {
    }
}