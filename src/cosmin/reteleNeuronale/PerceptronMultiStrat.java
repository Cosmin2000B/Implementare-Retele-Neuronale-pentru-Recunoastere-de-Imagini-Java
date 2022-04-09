package cosmin.reteleNeuronale;

import cosmin.functiiActivare.ReLU;
import cosmin.functiiActivare.Softmax;
import cosmin.functiiActivare.sigmoide.Logistica;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratAscuns;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratDeIntrare;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratNeuronalLiniar;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.EntropieIncrucisata;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.Entropie_Incrucisata_Binara;

import java.util.LinkedList;

/**
 * @author Ionescu Cosmin
 */
public class PerceptronMultiStrat extends ReteaNeuronalaFeedForward
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

        // nu avem straturi ascunse
        if(this.straturiAscunse.isEmpty())
        {
            stratDeIntrare.setStratUlterior(stratDeIesire);
            stratDeIntrare.setStratUlterior(stratDeIesire);
        }
        else // avem straturi ascunse
        {
            straturiAscunse.getLast().setStratUlterior(stratDeIesire);
            straturiAscunse.getLast().stabilesteStratDens();
        }
    }


    // ----------------- Sfarsit Constructori -----------------
    @Override
    public void executaPropagare()
    {
        this.straturiAscunse.forEach(StratAscuns::calculeazaIesiri);
        this.stratDeIesire.calculeazaIesiri();
        this.stratDeIesire.calculeazaEroareaRetelei();
    }

    // todo in 2 etape -> actualizeaza = durere
    public void retropropagareStratIesire(int dimSubmultimeAntrenament)
    {
        for(int i = 0; i < stratDeIesire.getNumarNeuroni(); ++i)
        {
            // ------- Strat de Iesire ----------
            Neuron neuronCurent = stratDeIesire.getNeuroni().get(i);
            double eroareNeuronIteratie = 0d;
            neuronCurent.setEroareNeuron(
                            stratDeIesire.getFunctieDeCost().calculeazaDerivata(neuronCurent, i, stratDeIesire) *
                                    neuronCurent.getFunctieActivare().valoareDerivata(neuronCurent.getValoareIntrare()));
            // bias
            neuronCurent.getBias().setDeltaPondere
                    (neuronCurent.getBias().getDeltaPondere()
                            + neuronCurent.getEroareNeuron() / dimSubmultimeAntrenament);

            // randul sinapselor -> sinapse intrare
            for(Sinapsa sinapsaIntrare: neuronCurent.getSinapseIntrare())
                sinapsaIntrare.setDeltaPondere(sinapsaIntrare.getDeltaPondere() +
                        neuronCurent.getEroareNeuron() * sinapsaIntrare.getNeuronEmitent().getValoareIesire()
                                / dimSubmultimeAntrenament);
        }
    }

    public void retropropagareStratAscuns(int dimSubmultimeAntrenament, StratAscuns stratAscuns)
    {
        for(int i = 0; i < stratAscuns.getNumarNeuroni(); ++i)
        {
            Neuron neuronCurent = stratAscuns.getNeuroni().get(i);
            // strat ulterior in contextul propagarii, fiind anterior in
            // retropropagare
            double sumaEroriPonderate = 0d;
            StratNeuronalLiniar stratUlterior = stratAscuns.getStratUlterior();

            for(Neuron neuronUlterior: stratUlterior.getNeuroni())
                sumaEroriPonderate +=
                        // se respecta ordinea din stabilirea stratului dens (determinare sinapsa corespondenta)
                        neuronUlterior.getEroareNeuron() * neuronUlterior.getSinapseIntrare().get(i).getPondere();

            neuronCurent.setEroareNeuron
                    (sumaEroriPonderate * neuronCurent.getFunctieActivare().
                            valoareDerivata(neuronCurent.getValoareIntrare()) / dimSubmultimeAntrenament);

            // bias
            neuronCurent.getBias().setDeltaPondere(neuronCurent.getBias().getDeltaPondere()
                    + neuronCurent.getEroareNeuron() / dimSubmultimeAntrenament);

            // sinapse -> sinapse intrare
            for(Sinapsa sinapsaIntrare: neuronCurent.getSinapseIntrare())
                sinapsaIntrare.setDeltaPondere(sinapsaIntrare.getDeltaPondere() + neuronCurent.getEroareNeuron() *
                        sinapsaIntrare.getNeuronEmitent().getValoareIesire());
        }
    }

    @Override
    public void executaRetropropagare(int dimSubmultimeAntrenament)
    {
        retropropagareStratIesire(dimSubmultimeAntrenament);

        for(int i = straturiAscunse.size() - 1; i >= 0; --i)
            retropropagareStratAscuns(dimSubmultimeAntrenament, straturiAscunse.get(i));

        // todo de vazut actualizare
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
