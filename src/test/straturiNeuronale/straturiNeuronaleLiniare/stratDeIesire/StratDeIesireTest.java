package test.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire;

import cosmin.functiiActivare.ReLU;
import cosmin.functiiActivare.Softmax;
import cosmin.functiiActivare.sigmoide.Logistica;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratAscuns;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratDeIntrare;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.EntropieIncrucisata;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.SumaPatratelorErorilor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StratDeIesireTest
{
    @Test
    void calculeazaIesiri()
    {
        StratDeIesire stratDeIesire = new StratDeIesire(4);
        stratDeIesire.setFunctieActivare(new Logistica());

        stratDeIesire.getNeuroni().get(0).setValoareIntrare(0.76);
        stratDeIesire.getNeuroni().get(1).setValoareIntrare(2.35);
        stratDeIesire.getNeuroni().get(2).setValoareIntrare(4.17);
        stratDeIesire.getNeuroni().get(3).setValoareIntrare(1.29);

        stratDeIesire.calculeazaIesiri();
        ArrayList<Double> valoriAsteptate = new ArrayList<>(Arrays.
                asList(0.68135373378, 0.91293422756, 0.98478287879, 0.78414718917));
        ArrayList<Double> valoriRezultate = new ArrayList<>();

        for(Neuron neuron: stratDeIesire.getNeuroni())
            valoriRezultate.add(neuron.getValoareIesire());

        for(int i = 0; i < valoriRezultate.size(); ++i)
            assertEquals(
                    valoriAsteptate.get(i),
                    valoriRezultate.get(i),
                    Math.pow(10, -10)
            );
    }

    @Test
    void calculeazaIesiriSoftmax()
    {
        StratDeIesire stratDeIesire = new StratDeIesire(4);
        stratDeIesire.setFunctieActivare(new Softmax(stratDeIesire));

        stratDeIesire.getNeuroni().get(0).setValoareIntrare(0.76);
        stratDeIesire.getNeuroni().get(1).setValoareIntrare(2.35);
        stratDeIesire.getNeuroni().get(2).setValoareIntrare(4.17);
        stratDeIesire.getNeuroni().get(3).setValoareIntrare(1.29);

        // daca fct de activare nu e EntropieIncrucisata, primim exceptie
        assertThrows(IllegalArgumentException.class, () -> stratDeIesire.calculeazaIesiri());
        stratDeIesire.setFunctieDeCost(new EntropieIncrucisata());
        stratDeIesire.calculeazaIesiri();

        ArrayList<Double> valoriAsteptate = new ArrayList<>(Arrays.
                asList(0.02640757281, 0.1294961069, 0.7992316416, 0.04486467865));

        // suma trebuie sa fie 1 (model probabilistic)
        double suma = 0d;
        for(Double val: valoriAsteptate)
            suma += val;

        assertEquals(1, suma, Math.pow(10, -10));

        ArrayList<Double> valoriRezultate = new ArrayList<>();
        for(Neuron neuron: stratDeIesire.getNeuroni())
            valoriRezultate.add(neuron.getValoareIesire());

        // verificam valorile calculate
        for(int i = 0; i < valoriRezultate.size(); ++i)
            assertEquals(
                    valoriAsteptate.get(i),
                    valoriRezultate.get(i),
                    Math.pow(10, -10)
            );
    }

    @Test
    void adaugaNeuron() {
    }

    @Test
    void testAdaugaNeuron() {
    }

    @Test
    void testAdaugaNeuron1() {
    }

    @Test
    void eliminaNeuron() {
    }

    @Test
    void testEliminaNeuron() {
    }

    @Test
    void calculeazaEroareaRetelei() {
    }

    @Test
    void SimulareRetea3x3x3x1()
    {
        StratDeIntrare stratDeIntrare = new StratDeIntrare(new ArrayList<>(Arrays.asList(2d, 3d, 1d)));

        StratAscuns stratAscuns1 = new StratAscuns(3, new ReLU());
        stratDeIntrare.setStratUlterior(stratAscuns1);
        stratDeIntrare.
                stabilesteStratDens(new ArrayList<>(Arrays.
                        asList(0.24, 0.51, 0.2, 0.35, 0.61, 0.9, 0.82, 0.75, 0.79)));

        StratAscuns stratAscuns2 = new StratAscuns(3, new ReLU());
        stratAscuns1.setStratUlterior(stratAscuns2);
        stratAscuns1.stabilesteStratDens(new ArrayList<>(Arrays.
                asList(0.13, 0.42, 0.61, 0.5, 0.3, 0.46, 0.12, 0.7, 0.143)));

        StratDeIesire stratDeIesire = new StratDeIesire(new ArrayList<>(Arrays.asList(0.98866638208d)));
        stratDeIesire.setFunctieDeCost(new SumaPatratelorErorilor());
        stratDeIesire.getNeuroni().get(0).setFunctieActivare(new Logistica());
        stratDeIesire.setValoriDorite(new ArrayList<Double>(Arrays.asList(0.321)));
        //TODO modelat pe caz general numele (strat,nr)
        stratDeIesire.getNeuroni().get(0).setNumeIdentificare("Neuron 0, Strat Iesire");
        stratAscuns2.setStratUlterior(stratDeIesire);
        stratAscuns2.stabilesteStratDens(new ArrayList<>(Arrays.
                asList(0.5, 0.46, 0.98)));

        // setam ponderi sinapse si bias-uri
        //Strat intrare
        stratDeIntrare.getNeuroni().get(0).setNumeIdentificare("Neuron 0, Strat intrare");
        stratDeIntrare.getNeuroni().get(1).setNumeIdentificare("Neuron 1, Strat intrare");
        stratDeIntrare.getNeuroni().get(2).setNumeIdentificare("Neuron 2, Strat intrare");

        //Strat ascuns I

        // bias
        stratAscuns1.getNeuroni().get(0).getBias().setPondere(0.45);
        stratAscuns1.getNeuroni().get(0).setNumeIdentificare("Neuron 0, Strat Ascuns 1");
        // bias
        stratAscuns1.getNeuroni().get(1).getBias().setPondere(0.25);
        stratAscuns1.getNeuroni().get(1).setNumeIdentificare("Neuron 1, Strat Ascuns 1");
        // bias
        stratAscuns1.getNeuroni().get(2).getBias().setPondere(0.15);
        stratAscuns1.getNeuroni().get(2).setNumeIdentificare("Neuron 2, Strat Ascuns 1");

        //Strat ascuns II

        // bias
        stratAscuns2.getNeuroni().get(0).getBias().setPondere(0.17);
        stratAscuns2.getNeuroni().get(0).setNumeIdentificare("Neuron 0, Strat Ascuns 2");
        // bias
        stratAscuns2.getNeuroni().get(1).getBias().setPondere(0.62);
        stratAscuns2.getNeuroni().get(1).setNumeIdentificare("Neuron 1, Strat Ascuns 2");

        // bias
        stratAscuns2.getNeuroni().get(2).getBias().setPondere(0.98);
        stratAscuns2.getNeuroni().get(2).setNumeIdentificare("Neuron 2, Strat Ascuns 2");

        // Strat iesire
        stratDeIesire.getNeuroni().get(0).getBias().setPondere(0.59);

        stratAscuns1.calculeazaIesiri();
        stratAscuns2.calculeazaIesiri();
        stratDeIesire.calculeazaIesiri();

        /* --  afisare situatie neuroni
        for(Neuron neuron: stratDeIntrare.getNeuroni())
            System.out.print(neuron);
        System.out.println("----/-----------/----------");
        for(Neuron neuron: stratAscuns1.getNeuroni())
            System.out.println(neuron);
        System.out.println("----/---------/------------");
        for(Neuron neuron: stratAscuns2.getNeuroni())
            System.out.println(neuron);
        System.out.println(stratDeIesire.getNeuroni().get(0));
        */

        assertEquals(0.98866638208,
                       stratDeIesire.getNeuroni().get(0).getValoareIesire(),
                Math.pow(10, -9));
        assertEquals(0.222889198, stratDeIesire.getEroareaRetelei(), Math.pow(10, -6));
    }

    @Test
    void gasesteSinapsaIntrare1()
    {
        StratAscuns s1 = new StratAscuns(3);
        StratAscuns s2 = new StratAscuns(4);

        for(int i = 0; i < 3; i++)
            s1.getNeuroni().get(i).setNumeIdentificare("Strat Ascuns 1, Neuronul " + i);

        for(int i = 0; i < 4; i++)
            s2.getNeuroni().get(i).setNumeIdentificare("Strat Ascuns 2, Neuronul " + i);

        s1.setStratUlterior(s2);
        s1.stabilesteStratDens();

        // verificam daca sinapsa gasita corespunde cu realitatea
        assertEquals( s1.getNeuroni().get(0).getSinapseIesire().get(3).getNeuronDestinatar(),
                s2.gasesteSinapsaIntrare
                        (s1.getNeuroni().get(0),s2.getNeuroni().get(3)).getNeuronDestinatar());

    }

    @Test
    void gasesteSinapsaIntrare2()
    {
        StratAscuns stratAscuns = new StratAscuns(2);
        stratAscuns.getNeuroni().get(0).setNumeIdentificare("S Ascuns, 0");
        stratAscuns.getNeuroni().get(1).setNumeIdentificare("S Ascuns, 1");

        StratAscuns stratIesire = new StratAscuns(2);
        stratIesire.getNeuroni().get(0).setNumeIdentificare("S Iesire, 0");
        stratIesire.getNeuroni().get(1).setNumeIdentificare("S Iesire, 1");

        stratAscuns.setStratUlterior(stratIesire);
        stratAscuns.stabilesteStratDens();

        Sinapsa s0a_1i = stratIesire.gasesteSinapsaIntrare(stratAscuns.getNeuroni().get(0),
                stratIesire.getNeuroni().get(1));

        assertSame(s0a_1i.getNeuronEmitent(), stratAscuns.getNeuroni().get(0));
        assertSame(s0a_1i.getNeuronDestinatar(), stratIesire.getNeuroni().get(1));
    }

    @Test
    void SimulareRna3x3x3x3()
    {
        //Clasificare multi-clasa (3 clase)

        // Strat intrare
        StratDeIntrare stratDeIntrare = new StratDeIntrare(new ArrayList<>(Arrays.
                asList(0.1, 0.2, 0.7)));
        // Strat Ascuns 1, fct activare: ReLU
        StratAscuns stratAscuns1 = new StratAscuns(3, new ReLU());
        stratDeIntrare.setStratUlterior(stratAscuns1);
        stratDeIntrare.stabilesteStratDens(new ArrayList<>(Arrays.
                asList(0.1, 0.2, 0.3, 0.3, 0.2, 0.7, 0.4, 0.3, 0.9)));
        // Strat Ascuns 2, fct activare: logistica
        StratAscuns stratAscuns2 = new StratAscuns(3, new Logistica());
        stratAscuns2.setStratAnterior(stratAscuns1);
        stratAscuns1.stabilesteStratDens(new ArrayList<>(Arrays.
                asList(0.2, 0.3, 0.5, 0.3, 0.5, 0.7, 0.6, 0.4, 0.8)));
        // Strat de Iesire, fct activare Softmax
        // calcularea functiei de cost: entropie incrucisata
        StratDeIesire stratDeIesire = new StratDeIesire(3);
        stratDeIesire.setFunctieActivare(new Softmax(stratDeIesire));
        stratDeIesire.setFunctieDeCost(new EntropieIncrucisata());

        stratAscuns2.setStratUlterior(stratDeIesire);
        stratAscuns2.stabilesteStratDens(new ArrayList<>(Arrays.
                asList(0.1, 0.4, 0.8, 0.3, 0.7, 0.2, 0.5, 0.2, 0.9)));
        stratDeIesire.setValoriDorite(new ArrayList<>(Arrays.
                asList(1d, 0d, 0d)));

        for(Neuron neuron: stratAscuns1.getNeuroni())
            neuron.getBias().setPondere(-1.0);
        for(Neuron neuron: stratAscuns2.getNeuroni())
            neuron.getBias().setPondere(-1.0);
        for(Neuron neuron: stratDeIesire.getNeuroni())
            neuron.getBias().setPondere(-1.0);

        stratAscuns1.calculeazaIesiri();
        stratAscuns2.calculeazaIesiri();
        stratDeIesire.calculeazaIesiri();
        stratDeIesire.calculeazaEroareaRetelei();

        //for(Neuron neuron: stratDeIesire.getNeuroni())
            //System.out.print(neuron.getValoareIesire() + " ");
        //System.out.println("\n");
        //System.out.println(stratDeIesire.getEroareaRetelei());

        assertEquals(1.61723375, stratDeIesire.getEroareaRetelei(), Math.pow(10,-8));
    }

}