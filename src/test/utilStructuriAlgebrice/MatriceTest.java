package test.utilStructuriAlgebrice;

import cosmin.utilStructuriAlgebrice.Matrice;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MatriceTest
{

    @Test
    void liniarizare()
    {
        double[] oa1 = {1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        double[] oa2 = {1, 2, 3, 4};
        double[] oa3 = {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d};

        linizarizareTest(new double[][]{{1, 2, 4}, {5, 6, 7}, {8, 9, 10}, {11, 12, 13}},
                (ArrayList<Double>) Arrays.stream(oa1).boxed().collect(Collectors.toList()));
        linizarizareTest(new double[][]{{1,2}, {3, 4}},
                (ArrayList<Double>) Arrays.stream(oa2).boxed().collect(Collectors.toList()));
        linizarizareTest(new double[][]{{1, 2, 3, 4}, { 5, 6, 7, 8}},
                (ArrayList<Double>) Arrays.stream(oa3).boxed().collect(Collectors.toList()));
    }

    private void linizarizareTest(double[][] input, ArrayList<Double> outputAsteptat)
    {
        Matrice matrice = new Matrice(input);
        ArrayList<Double> actual = Matrice.liniarizare(matrice);

        for(int i = 0; i < outputAsteptat.size(); ++i)
            assertEquals(outputAsteptat.get(i), actual.get(i));
    }
}