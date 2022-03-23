package test.straturiNeuronale.straturiNeuronaleLiniare;

import cosmin.functiiActivare.sigmoide.TangentaHiperbolica;
import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratDeIntrare;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratNeuronalLiniar;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StratDeIntrareTest
{

    @Test
    public void ConstructorValoriIesire_StratDeIntrare()
    {
        ArrayList<Double> valoriIesire = new ArrayList<>(Arrays.asList(0.21d, 0.30d, 0.1d, 0.12d));
        StratDeIntrare stratDeIntrare = new StratDeIntrare(valoriIesire);

        ArrayList<Double> actualValoriIesire = new ArrayList<>();

        for(Neuron neuron: stratDeIntrare.getNeuroni())
            actualValoriIesire.add(neuron.getValoareIesire());

        assertEquals (Arrays.asList(0.21d, 0.30d, 0.1d, 0.12d),actualValoriIesire);
    }

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