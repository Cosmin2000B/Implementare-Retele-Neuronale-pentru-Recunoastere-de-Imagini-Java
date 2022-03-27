package cosmin.reteleNeuronale;

import cosmin.functiiActivare.ReLU;
import cosmin.regulInvatare.RegulaInvatare;
import cosmin.regulInvatare.multimeAntrenament.MultimeAntrenamentEtichetata;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratAscuns;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratDeIntrare;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;

import java.util.LinkedList;

/**
 *
 */
public class PerceptronMultiStrat extends ReteaNeuronala<RegulaInvatare<MultimeAntrenamentEtichetata>>
{
    private StratDeIntrare stratDeIntrare;
    private LinkedList<StratAscuns> straturiAscunse;
    private StratDeIesire stratDeIesire;


    // ----------------- Constructori -------------------------

    public PerceptronMultiStrat(int dimensiuneSetIntrare, int dimensiuneIesire, int nrStraturiAscunse,
                                int nrNeuroniPerStratAscuns)
    {
        super();

        this.stratDeIntrare = new StratDeIntrare(dimensiuneSetIntrare);
        this.stratDeIntrare.setNumeIdentificare("Strat de Intrare");

        this.stratDeIesire = new StratDeIesire(dimensiuneIesire);
        this.stratDeIesire.setNumeIdentificare("Strat de Iesire");

        this.straturiAscunse = new LinkedList<>();

        for(int i = 0; i < nrStraturiAscunse; ++i)
        {
            StratAscuns stratAscuns = new StratAscuns(nrNeuroniPerStratAscuns);
            stratAscuns.setNumeIdentificare("Strat Ascuns Nr. " + i);
            stratAscuns.setFunctieActivare(new ReLU());

            if(this.stratDeIntrare.getStratUlterior() == null)
            {
                stratDeIntrare.setStratUlterior(stratAscuns);
                stratDeIntrare.stabilesteStratDens();
            }
            else
            {
                this.straturiAscunse.getLast().setStratUlterior(stratAscuns);
                this.straturiAscunse.getLast().stabilesteStratDens();
            }

            this.straturiAscunse.add(stratAscuns);
        }
    }


    // ----------------- Sfarsit Constructori -----------------
    @Override
    public void executaPropagare()
    {
        this.straturiAscunse.forEach(stratAscuns -> stratAscuns.calculeazaIesiri());
        this.stratDeIesire.calculeazaIesiri();
        this.stratDeIesire.calculeazaEroareaRetelei();
    }

    // ----- Setteri si Getteri --------


    public StratDeIntrare getStratDeIntrare() {
        return stratDeIntrare;
    }

    public void setStratDeIntrare(StratDeIntrare stratDeIntrare) {
        this.stratDeIntrare = stratDeIntrare;
    }

    public LinkedList<StratAscuns> getStraturiAscunse() {
        return this.straturiAscunse;
    }

    public void setStraturiAscunse(LinkedList<StratAscuns> straturiAscunse) {
        this.straturiAscunse = straturiAscunse;
    }

    public StratDeIesire getStratDeIesire() {
        return stratDeIesire;
    }

    public void setStratDeIesire(StratDeIesire stratDeIesire) {
        this.stratDeIesire = stratDeIesire;
    }

    //TODO configuratii standard
    // ------------- Configuratii standard --------------------

    public static PerceptronMultiStrat getClasificatorBinar()
    {
        return null;
    }

    public static PerceptronMultiStrat getClasificatorMultiClasa()
    {
        return null;
    }
}
