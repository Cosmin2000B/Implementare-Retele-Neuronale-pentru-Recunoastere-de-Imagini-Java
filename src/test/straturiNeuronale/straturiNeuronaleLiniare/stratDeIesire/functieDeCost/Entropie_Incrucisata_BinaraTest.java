package test.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost;

import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.Entropie_Incrucisata_Binara;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Entropie_Incrucisata_BinaraTest
{

    @Test
    void calculeazaEroarea1()
    {
        StratDeIesire stratDeIesire = new StratDeIesire(1);
        stratDeIesire.setFunctieDeCost(new Entropie_Incrucisata_Binara());
        stratDeIesire.getNeuroni().get(0).setValoareIesire(0.2d);
        stratDeIesire.setValoriDorite(new ArrayList<>(Arrays.asList(1d)));
        stratDeIesire.calculeazaEroareaRetelei();

        assertEquals(1.6094379124, stratDeIesire.getEroareaRetelei(),
                Math.pow(10, -10));
    }

    @Test
    void calculeazaEroarea2()
    {
        StratDeIesire stratDeIesire = new StratDeIesire(1);
        stratDeIesire.setFunctieDeCost(new Entropie_Incrucisata_Binara());
        stratDeIesire.getNeuroni().get(0).setValoareIesire(0.05d);
        stratDeIesire.setValoriDorite(new ArrayList<>(Arrays.asList(0d)));
        stratDeIesire.calculeazaEroareaRetelei();

        // predictie corecta -> entropie 0
        assertEquals(0.051293294, Math.abs(stratDeIesire.getEroareaRetelei()), Math.pow(10, -8));
    }

    @Test
    void calculeazaDerivata()
    {

    }
}