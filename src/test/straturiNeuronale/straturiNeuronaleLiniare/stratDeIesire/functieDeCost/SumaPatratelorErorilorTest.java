package test.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.SumaPatratelorErorilor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SumaPatratelorErorilorTest
{
    @Test
    public void calculeazaEroare1()
    {
        ArrayList<Double> valoriDorite = new ArrayList<>(Arrays.asList(0.1, 0.3, 0.24, 0.15));

        StratDeIesire stratDeIesire = new StratDeIesire(4);
        stratDeIesire.setFunctieDeCost(new SumaPatratelorErorilor());
        stratDeIesire.setValoriDorite(valoriDorite);

        double adauga = 0.1d;

        // valori: 0.3, 0.35, 0.40, 0.45
        for(Neuron neuron: stratDeIesire.getNeuroni())
        {
            neuron.setValoareIesire(0.2d + adauga);
            adauga += 0.05d;
        }

        assertEquals(0.07905, stratDeIesire.getEroareaRetelei(), 0.0001);

        // verificam daca ne este permis sa calculam functia de cost atunci cand nr de neuroni
        // difera de dimensiunea vectorului de valori dorite
        stratDeIesire.adaugaNeuron(new Neuron());
        assertThrows(IllegalArgumentException.class, ()->stratDeIesire.calculeazaEroareaRetelei());
    }
}