package test.functiiActivare;

import cosmin.functiiActivare.Softmax;
import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.EntropieIncrucisata;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SoftmaxTest
{

    @Test
    void calculeazaSumaFunctiiExponentiale()
    {
    }

    @Test
    void valoareFunctie()
    {
        // stabilire iesiri dorite
        StratDeIesire stratDeIesire = new StratDeIesire(new ArrayList<>(Arrays.asList(1d, 0d, 0d)));
        // intrarile neuronilor
        stratDeIesire.getNeuroni().get(0).setValoareIntrare(33000);
        stratDeIesire.getNeuroni().get(1).setValoareIntrare(32999);
        stratDeIesire.getNeuroni().get(2).setValoareIntrare(33001);
        // stabilire functii
        stratDeIesire.setFunctieActivare(new Softmax(stratDeIesire));
        stratDeIesire.setFunctieDeCost(new EntropieIncrucisata());

        stratDeIesire.calculeazaIesiri();
        for(Neuron neuron: stratDeIesire.getNeuroni())
            System.out.print(neuron.getValoareIesire() + " ");
    }

    @Test
    void valoareDerivata()
    {
    }
}