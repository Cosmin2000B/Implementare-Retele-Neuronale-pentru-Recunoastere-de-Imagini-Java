package test.reteleNeuronale;

import cosmin.regulInvatare.GradientDescendentStohastic;
import cosmin.reteleNeuronale.PerceptronMultiStrat;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PerceptronMultiStratTest
{
    //TODO teste

    @Test
    void executaPropagare()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                new PerceptronMultiStrat(3, 3, 2, 3);
        perceptronMultiStrat.getStratDeIesire().setValoriDorite(new ArrayList<>(Arrays.
                asList(0.3, 0.2, 0.1)));
        perceptronMultiStrat.setRegulaInvatare(new GradientDescendentStohastic());
        perceptronMultiStrat.executaPropagare();
    }
}