package test.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

import cosmin.functiiActivare.Softmax;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.EntropieIncrucisata;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

            assertEquals(0.3566749439, stratDeIesire.getEroareaRetelei(),
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

        assertEquals(0.9162907319, stratDeIesire.getEroareaRetelei(),
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

    @Test
    public void calculeazaEroarea4()
    {
        StratDeIesire stratDeIesire = new StratDeIesire(7);
        stratDeIesire.getNeuroni().get(0).setValoareIntrare(1.0);
        stratDeIesire.getNeuroni().get(1).setValoareIntrare(2.0);
        stratDeIesire.getNeuroni().get(2).setValoareIntrare(3.0);
        stratDeIesire.getNeuroni().get(3).setValoareIntrare(4.0);
        stratDeIesire.getNeuroni().get(4).setValoareIntrare(1.0);
        stratDeIesire.getNeuroni().get(5).setValoareIntrare(2.0);
        stratDeIesire.getNeuroni().get(6).setValoareIntrare(3.0);

        stratDeIesire.setFunctieActivare(new Softmax(stratDeIesire));
        stratDeIesire.setFunctieDeCost(new EntropieIncrucisata());
        stratDeIesire.calculeazaIesiri();

        ArrayList<Double> valoriAsteptate = new ArrayList<>(Arrays.
                asList(0.02364054, 0.06426166, 0.1746813, 0.474833, 0.02364054, 0.06426166, 0.1746813));

        for(int i = 0; i < 7; ++i)
            assertEquals(valoriAsteptate.get(i),
                    stratDeIesire.getNeuroni().get(i).getValoareIesire()
            , Math.pow(10, -8));
    }
}