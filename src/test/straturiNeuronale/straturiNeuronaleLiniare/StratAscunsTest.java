package test.straturiNeuronale.straturiNeuronaleLiniare;

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
}