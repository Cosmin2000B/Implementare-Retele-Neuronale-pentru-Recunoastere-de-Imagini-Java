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

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Ionescu Cosmin
 */
public class PerceptronMultiStrat
                                  extends ReteaNeuronalaFeedForward
                                  implements Serializable
{
    /**
     * pentru identificarea cu compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private StratDeIntrare stratDeIntrare;
    private LinkedList<StratAscuns> straturiAscunse;
    private StratDeIesire stratDeIesire;


    // ----------------- Constructori -------------------------

    /**
     *
     * @param dimensiuneSetIntrare numarul de neuroni de pe stratul de intrare. Trebuie
     *                             sa coincida cu dimensiunea setului de intrare in RNA.
     * @param dimensiuneIesire numarul de neuroni de pe stratul de iesire. Trebuie sa
     *                         coincida cu dimensiunea unei etichete hardcodate sub forma
     *                         unui vector.
     * @param nrStraturiAscunse numarul de neuroni pe care dorim sa-l aiba reteaua neuronala.
     * @param nrNeuroniPerStratAscuns numarul de neuroni de pe fiecare strat ascuns.
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
            stratDeIntrare.stabilesteStratDens();
        }
        else // avem straturi ascunse
        {
            straturiAscunse.getLast().setStratUlterior(stratDeIesire);
            straturiAscunse.getLast().stabilesteStratDens();
        }
    }


    // ----------------- Sfarsit Constructori -----------------

    private void stabilesteIesiriRetea()
    {
        this.setValoriIesire(new ArrayList<>(stratDeIesire.getNumarNeuroni()));
        for(Neuron neuron: stratDeIesire.getNeuroni())
            this.getValoriIesire().add(neuron.getValoareIesire());
    }

    @Override
    public void executaPropagare()
    {
        this.straturiAscunse.forEach(StratAscuns::calculeazaIesiri);
        this.stratDeIesire.calculeazaIesiri();
        stabilesteIesiriRetea();

        // In cazul propagarii nominale pt obtinerea unui rezultat nu avem valori dorite
        if(this.stratDeIesire.getValoriDorite() != null && !this.getStratDeIesire().getValoriDorite().isEmpty())
        {
            this.stratDeIesire.calculeazaEroareaRetelei();
            this.setEroareaRetelei(this.stratDeIesire.getEroareaRetelei());
        }
    }

    public void retropropagareStratIesire(int dimSubmultimeAntrenament)
    {
        for(int i = 0; i < stratDeIesire.getNumarNeuroni(); ++i)
        {
            // ------- Strat de Iesire ----------
            Neuron neuronCurent = stratDeIesire.getNeuroni().get(i);
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

        // resetam starea neuronilor pt. urmatoarea propagare
        for(Neuron neuron: stratDeIesire.getNeuroni())
            neuron.reseteazaStare();
        // resetam starea neuronilor pt. urmatoarea propagare
        for(Neuron neuronUlterior: stratAscuns.getStratUlterior().getNeuroni())
            neuronUlterior.reseteazaStare();

        if(stratAscuns == straturiAscunse.get(0))
        {
            for(Neuron neuron: stratAscuns.getNeuroni())
                neuron.reseteazaStare();
        }

    }

    @Override
    public void executaOptimizare(double rataInvatare, double inertie)
    {
        this.stratDeIesire.actualizeazaPonderi(rataInvatare, inertie);

        if(!this.getStraturiAscunse().isEmpty())
        {
            Iterator<StratAscuns> it = straturiAscunse.descendingIterator();
            while(it.hasNext())
                it.next().actualizeazaPonderi(rataInvatare, inertie);
        }
    }

    @Override
    public void executaRetropropagare(int dimSubmultimeAntrenament)
    {
        retropropagareStratIesire(dimSubmultimeAntrenament);

        Iterator<StratAscuns> it = straturiAscunse.descendingIterator();
        while(it.hasNext())
            retropropagareStratAscuns(dimSubmultimeAntrenament, it.next());

        // todo de vazut calcul erori strat intrare
    }

    /**
     *   Reseteaza starea tuturor neuronilor din stratul de iesire si
     *  straturile ascunse.
     */
    @Override
    public void reseteazaStare()
    {
        for(Neuron neuron: stratDeIesire.getNeuroni())
            neuron.reseteazaStare();

        for(StratAscuns stratAscuns: straturiAscunse)
            for(Neuron neuron: stratAscuns.getNeuroni())
                neuron.reseteazaStare();
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
