package test.reteleNeuronale;

import cosmin.functiiActivare.sigmoide.Logistica;
import cosmin.regulaInvatare.invatareSupervizata.GradientDescendent;
import cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata.MultimeImagini;
import cosmin.reteleNeuronale.PerceptronMultiStrat;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PerceptronMultiStratTest
{
    //TODO teste - de rezolvat
    @Test
    void executaPropagare()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                new PerceptronMultiStrat(3, 1, 2, 3);
        perceptronMultiStrat.getStratDeIesire().setValoriDorite(new ArrayList<>(List.of(0.1d)));
        perceptronMultiStrat.setRegulaInvatare(new GradientDescendent());

        // stabilire valori
        //strat intrare
        perceptronMultiStrat.getStratDeIntrare().stabilesteInputRetea(new ArrayList<>(Arrays.asList(2d, 3d, 1d)));
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(0).getSinapseIesire().get(0).setPondere(0.24);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(0).getSinapseIesire().get(1).setPondere(0.51);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(0).getSinapseIesire().get(2).setPondere(0.2);

        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(1).getSinapseIesire().get(0).setPondere(0.35);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(1).getSinapseIesire().get(1).setPondere(0.61);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(1).getSinapseIesire().get(2).setPondere(0.9);

        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(2).getSinapseIesire().get(0).setPondere(0.82);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(2).getSinapseIesire().get(1).setPondere(0.75);
        perceptronMultiStrat.getStratDeIntrare().getNeuroni().get(2).getSinapseIesire().get(2).setPondere(0.79);

        // strat ascuns 1
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(0).getSinapseIesire().get(0).setPondere(0.13);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(0).getSinapseIesire().get(1).setPondere(0.42);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(0).getSinapseIesire().get(2).setPondere(0.61);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(0).getBias().setPondere(0.45);

        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(1).getSinapseIesire().get(0).setPondere(0.5);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(1).getSinapseIesire().get(1).setPondere(0.3);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(1).getSinapseIesire().get(2).setPondere(0.46);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(1).getBias().setPondere(0.25);

        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(2).getSinapseIesire().get(0).setPondere(0.12);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(2).getSinapseIesire().get(1).setPondere(0.7);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(2).getSinapseIesire().get(2).setPondere(0.143);
        perceptronMultiStrat.getStraturiAscunse().get(0).getNeuroni().get(2).getBias().setPondere(0.15);

        // strat ascuns 2
        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(0).getSinapseIesire().get(0).setPondere(0.5);
        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(0).getBias().setPondere(0.17);

        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(1).getSinapseIesire().get(0).setPondere(0.46);
        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(1).getBias().setPondere(0.62);

        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(2).getSinapseIesire().get(0).setPondere(0.98);
        perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(2).getBias().setPondere(0.98);

        // strat de iesire
        perceptronMultiStrat.getStratDeIesire().getNeuroni().get(0).getBias().setPondere(0.59);

        perceptronMultiStrat.executaPropagare();

        // todo de vzt cu fct de activare

        assertEquals(3.801,perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(1).getValoareIesire(),
                Math.pow(10, -8));
        assertEquals(2.2008,perceptronMultiStrat.getStraturiAscunse().get(1).getNeuroni().get(0).getValoareIesire(),
                Math.pow(10, -8));
        assertEquals(0.98866638208, perceptronMultiStrat.getStratDeIesire().getNeuroni().get(0).getValoareIesire(),
                Math.pow(10, -8));
    }

    @Test
    void retropropagareStratIesire()
    {
    }

    @Test
    void executaRetropropagare()
    {
    }
}