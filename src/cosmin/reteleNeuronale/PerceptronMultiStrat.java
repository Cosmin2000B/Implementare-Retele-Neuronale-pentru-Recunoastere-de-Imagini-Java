package cosmin.reteleNeuronale;

import cosmin.functiiActivare.ReLU;
import cosmin.functiiActivare.Softmax;
import cosmin.functiiActivare.sigmoide.Logistica;
import cosmin.regulInvatare.RegulaInvatare;
import cosmin.regulInvatare.multimeAntrenament.MultimeAntrenamentEtichetata;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratAscuns;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratDeIntrare;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.EntropieIncrucisata;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.Entropie_Incrucisata_Binara;

import java.util.LinkedList;

/**
 * @author Ionescu Cosmin
 */
public class PerceptronMultiStrat extends ReteaNeuronala<RegulaInvatare<MultimeAntrenamentEtichetata>>
{
    private StratDeIntrare stratDeIntrare;
    private LinkedList<StratAscuns> straturiAscunse;
    private StratDeIesire stratDeIesire;


    // ----------------- Constructori -------------------------

    // TODO explicatii constructor
    /**
     *
     * @param dimensiuneSetIntrare
     * @param dimensiuneIesire
     * @param nrStraturiAscunse
     * @param nrNeuroniPerStratAscuns
     * @see StratDeIntrare
     * @see StratAscuns
     * @see StratDeIesire
     */
    public PerceptronMultiStrat(int dimensiuneSetIntrare, int dimensiuneIesire, int nrStraturiAscunse,
                                int nrNeuroniPerStratAscuns)
    {
        super();

        this.stratDeIntrare = new StratDeIntrare(dimensiuneSetIntrare);
        this.stratDeIntrare.setNumeIdentificare("Strat de Intrare");

        this.stratDeIesire = new StratDeIesire(dimensiuneIesire);
        this.stratDeIesire.setNumeIdentificare("Strat de Iesire");

        /*
        // in cazul in care avem un singur neuron pe stratul de iesire, consideram problema
        // o clasificare binara si atribuim stratului de iesire functia de cost
        // Entropie_Incrucisata_Binara si fct. de activare logistica, altfel o consideram o
        // problema de clasificare multi-clasa si atribuim functia de cost Enropie incruci-
        // sata si fct. de activare softmax
         */
        if(dimensiuneIesire == 1)
        {
            stratDeIesire.setFunctieActivare(new Logistica());
            stratDeIesire.setFunctieDeCost(new Entropie_Incrucisata_Binara());
        }
        else
        {
            stratDeIesire.setFunctieActivare(new Softmax(stratDeIesire));
            stratDeIesire.setFunctieDeCost(new EntropieIncrucisata());
        }

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
