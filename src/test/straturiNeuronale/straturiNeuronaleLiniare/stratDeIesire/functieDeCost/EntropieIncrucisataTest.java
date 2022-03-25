package test.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

import cosmin.functiiActivare.Softmax;
import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.EntropieIncrucisata;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EntropieIncrucisataTest
{
    @Test
    public void calculeazaEroarea1()
    {
        StratDeIesire stratDeIesire = new StratDeIesire(new ArrayList<Double>(Arrays.
                asList(1d, 0d, 0d)));
        stratDeIesire.setFunctieActivare(new Softmax(stratDeIesire));
        stratDeIesire.setFunctieDeCost(new EntropieIncrucisata());
        ArrayList<Double> valoriObtinute = new ArrayList<>(Arrays.
                asList(0.7d, 0.2d, 0.1d));

        for(int i = 0; i < stratDeIesire.getNumarNeuroni(); ++i)
            stratDeIesire.getNeuroni().get(i).setValoareIesire(valoriObtinute.get(i));

            assertEquals(0.5145731728297583, stratDeIesire.getEroareaRetelei(),
                    Math.pow(10, -8));
    }

    @Test
    public void calculeazaEroarea2()
    {
        StratDeIesire stratDeIesire = new StratDeIesire(new ArrayList<Double>(Arrays.
                asList(0d, 1d, 0d)));
        stratDeIesire.setFunctieActivare(new Softmax(stratDeIesire));
        stratDeIesire.setFunctieDeCost(new EntropieIncrucisata());
        ArrayList<Double> valoriObtinute = new ArrayList<>(Arrays.
                asList(0.4d, 0.4d, 0.02d));

        for(int i = 0; i < stratDeIesire.getNumarNeuroni(); ++i)
            stratDeIesire.getNeuroni().get(i).setValoareIesire(valoriObtinute.get(i));

        assertEquals(1.32192809488, stratDeIesire.getEroareaRetelei(),
                Math.pow(10, -8));
    }

    @Test
    public void calculeazaEroarea3()
    {
        StratDeIesire stratDeIesire = new StratDeIesire(new ArrayList<Double>(Arrays.
                asList(0d, 1d, 0d)));
        stratDeIesire.setFunctieActivare(new Softmax(stratDeIesire));
        stratDeIesire.setFunctieDeCost(new EntropieIncrucisata());
        ArrayList<Double> valoriObtinute = new ArrayList<>(Arrays.
                asList(0d, 1d, 0d));

        for(int i = 0; i < stratDeIesire.getNumarNeuroni(); ++i)
            stratDeIesire.getNeuroni().get(i).setValoareIesire(valoriObtinute.get(i));

        // clasificare perfecta => entropie = 0
        assertEquals(0d, stratDeIesire.getEroareaRetelei(),
                Math.pow(10, -8));
    }
}