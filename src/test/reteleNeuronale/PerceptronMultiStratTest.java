package test.reteleNeuronale;

import cosmin.regulInvatare.GradientDescendentStohastic;
import cosmin.reteleNeuronale.PerceptronMultiStrat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerceptronMultiStratTest
{
    //TODO teste

    @Test
    void executaPropagare()
    {
        PerceptronMultiStrat perceptronMultiStrat =
                new PerceptronMultiStrat(3, 3, 2, 3);
        perceptronMultiStrat.setRegulaInvatare(new GradientDescendentStohastic());
        perceptronMultiStrat.antreneaza();

    }
}