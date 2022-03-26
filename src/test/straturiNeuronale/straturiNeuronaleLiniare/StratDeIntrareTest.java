package test.straturiNeuronale.straturiNeuronaleLiniare;

import cosmin.functiiActivare.sigmoide.TangentaHiperbolica;
import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratAscuns;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratDeIntrare;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void stabilesteStratDens()
    {
        StratDeIntrare si = new StratDeIntrare(3);
        StratDeIesire sie = new StratDeIesire(5);
        si.setStratUlterior(sie);
        si.stabilesteStratDens();

        // nr. total sinapse 3*5 = 15
        int nrTotalSinapse = 0;

        for(Neuron neuron: si.getNeuroni())
            nrTotalSinapse += neuron.getSinapseIesire().size();
            //System.out.println(neuron);

        assertEquals(15, nrTotalSinapse);
    }

    @Test
    void setStratUlterior()
    {
        StratDeIntrare si = new StratDeIntrare(4);

        assertThrows(IllegalArgumentException.class, () ->
        {
            StratDeIntrare si2 = new StratDeIntrare(3);
            si.setStratUlterior(si2);
            si.stabilesteStratDens();
        });

        StratAscuns sa = new StratAscuns(3);
        si.setStratUlterior(sa);
        si.stabilesteStratDens();

        assertEquals(si, sa.getStratAnterior());
    }
}