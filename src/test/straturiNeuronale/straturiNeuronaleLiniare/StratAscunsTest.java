package test.straturiNeuronale.straturiNeuronaleLiniare;

import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratAscuns;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratDeIntrare;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StratAscunsTest {

    @Test
    void calculeazaIesiri() {
    }

    @Test
    void reseteazaPonderi() {
    }

    @Test
    void stabilesteStratDens() {
    }

    @Test
    void setStratAnterior()
    {
        assertThrows(IllegalArgumentException.class, () ->
        {
            StratDeIesire si = new StratDeIesire(2);
            StratAscuns sa = new StratAscuns(4);

            sa.setStratAnterior(si);
        });

    }

    @Test
    void setStratAnterior2()
    {
        StratAscuns sa = new StratAscuns(2);
        StratDeIntrare si = new StratDeIntrare(2);

        sa.setStratAnterior(si);

        assert(sa == si.getStratUlterior());
    }

    @Test
    void setStratUlterior() {
    }

    // TODO vezi strat iesire
    @Test
    void gasesteSinapsaIntrare()
    {
        StratAscuns stratAscuns = new StratAscuns(2);
        stratAscuns.getNeuroni().get(0).setNumeIdentificare("S Ascuns, 0");
        stratAscuns.getNeuroni().get(1).setNumeIdentificare("S Ascuns, 1");

        StratAscuns stratAscuns1 = new StratAscuns(2);
        stratAscuns1.getNeuroni().get(0).setNumeIdentificare("S Ascuns1, 0");
        stratAscuns1.getNeuroni().get(1).setNumeIdentificare("S Ascuns1, 1");

        stratAscuns.setStratUlterior(stratAscuns1);
        stratAscuns.stabilesteStratDens();

        Sinapsa s0a_1i = stratAscuns1.gasesteSinapsaIntrare(stratAscuns.getNeuroni().get(0),
                stratAscuns1.getNeuroni().get(1));

        assertSame(s0a_1i.getNeuronEmitent(), stratAscuns.getNeuroni().get(0));
        assertSame(s0a_1i.getNeuronDestinatar(), stratAscuns1.getNeuroni().get(1));
    }
}