package cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire;

import cosmin.functiiActivare.FunctieActivare;
import cosmin.functiiActivare.Softmax;
import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.straturiNeuronale.StratNeuronal;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratAscuns;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratDeIntrare;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratNeuronalLiniar;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.FunctieDeCost;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *   Stratul de iesire este ultimul strat din structura unei retele neuronale.
 *   In cadrul acestuia se calculeaza valorile de iesire ale retelei (prin
 *  intermediul neuronilor de pe acest strat), functia de cost a retelei
 *  (prin confruntarea valorilor obtinute cu cele dorite) si, de obicei,
 *  declansarea algoritmului de optimizare.
 * @author Ionescu Cosmin
 */
public class StratDeIesire
                           extends StratNeuronalLiniar
                           implements StratNeuronal, Serializable
{
    /**
     * pentru identificarea cu compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    // setul de valori pe care vrem sa le
    // obtinem
    private transient ArrayList<Double> valoriDorite;

    private StratNeuronalLiniar stratAnterior;
    private transient double eroareaRetelei;

    // la nivel de strat
    private FunctieActivare functieActivare;

    // metoda preferata de calcul a erorii
    private FunctieDeCost functieDeCost;

    // --------- Constructori -------------------

    public StratDeIesire(int numarNeuroni)
    {
        this.setNumarNeuroni(numarNeuroni);
        this.setNeuroni(new ArrayList<>(this.getNumarNeuroni()));

        for(int i = 0; i < this.getNumarNeuroni(); ++i)
        {
            Neuron neuron = new Neuron(this.functieActivare);
            this.getNeuroni().add(neuron);
        }
    }

    public StratDeIesire(@NotNull List<Double> valoriDorite)
    {
        this.setNumarNeuroni(valoriDorite.size());
        this.setNeuroni(new ArrayList<>(this.getNumarNeuroni()));

        for(int i = 0; i < this.getNumarNeuroni(); ++i)
        {
            Neuron neuron = new Neuron(this.functieActivare);
            this.getNeuroni().add(neuron);
        }

        this.valoriDorite = new ArrayList<>(valoriDorite.size());
        this.valoriDorite.addAll(valoriDorite);
    }

    public StratDeIesire(StratNeuronalLiniar stratAnterior)
    {
        this.stratAnterior = stratAnterior;
        this.setNeuroni(new ArrayList<>());
    }

    public StratDeIesire(int numarNeuroni, StratNeuronalLiniar stratAnterior, FunctieActivare functieActivare)
    {
        this.setNumarNeuroni(numarNeuroni);
        this.setNeuroni(new ArrayList<>(this.getNumarNeuroni()));

        for(int i = 0; i < this.getNumarNeuroni(); ++i)
        {
            Neuron neuron = new Neuron(functieActivare);
            this.getNeuroni().add(neuron);
        }

        this.stratAnterior = stratAnterior;
        this.functieActivare = functieActivare;
    }

    // ------------- Sfarsit Constructori --------------------------

    @Override
    public void calculeazaIesiri()
    {
        if(this.functieActivare instanceof Softmax)
        {
            for(Neuron neuron: this.getNeuroni())
                neuron.
                        setValoareIesire(this.
                        functieActivare.valoareFunctie(neuron.getValoareIntrare()));

            /*
            resetare suma valori exponentiale --> mare grija la alte implementari
            ( daca nu se executa si derivare in cadrul retropropagarii nu se actua-
            lizeaza singura) <=> problema la propagare pt obinere rezultate la mai
            mult de o rulare
             */
            ((Softmax) this.functieActivare).setSumaFunctiiExponentiale(0d);
        }
        else
        {
            for (Neuron neuron : this.getNeuroni())
                neuron.calculeazaIesire();
        }
    }

    @Override
    public void reseteazaPonderi()
    {
        for(Neuron neuron: this.getNeuroni())
        {
            ArrayList<Sinapsa> sinapse = neuron.getSinapseIntrare();
            for(Sinapsa sinapsa: sinapse)
                sinapsa.setPondere(ThreadLocalRandom.current().nextDouble());
        }
    }

    @Override
    public void actualizeazaPonderi(double rataInvatare)
    {
        for(Neuron neuron: this.getNeuroni())
        {
            neuron.getBias().actualizeazaPondere(rataInvatare);

            for(Sinapsa sinapsa: neuron.getSinapseIntrare())
                sinapsa.actualizeazaPondere(rataInvatare);
        }
    }

    @Override
    public void actualizeazaPonderi(double rataInvatare, double inertie)
    {
        for(Neuron neuron: this.getNeuroni())
        {
            neuron.getBias().actualizeazaPondere(rataInvatare, inertie);

            for(Sinapsa sinapsa: neuron.getSinapseIntrare())
                sinapsa.actualizeazaPondere(rataInvatare, inertie);
        }
    }

    // TODO testeaza metoda
    public Sinapsa gasesteSinapsaIntrare(Neuron neuronEmitent, @NotNull Neuron neuronDestinatar)
    {
        for(Sinapsa sinapsa: neuronDestinatar.getSinapseIntrare())
        {
            if(sinapsa.getNeuronEmitent() == neuronEmitent)
                return sinapsa;
        }
        return null;
    }

    /**
     *
     */
    public void calculeazaEroareaRetelei()
    {
        this.eroareaRetelei = this.functieDeCost.calculeazaEroarea(this);
    }

    // ----------------- Setteri si Getteri -------------------------

    public ArrayList<Double> getValoriDorite() {
        return valoriDorite;
    }

    public void setValoriDorite(ArrayList<Double> valoriDorite) {
        this.valoriDorite = valoriDorite;
    }

    public StratNeuronalLiniar getStratAnterior() {
        return stratAnterior;
    }

    public void setStratAnterior(StratNeuronalLiniar stratAnterior)
    {
        if(stratAnterior instanceof StratDeIesire)
            throw new IllegalArgumentException("Un strat de iesire nu poate avea drept" +
                    " strat anterior un strat de iesire!");

        if(stratAnterior instanceof StratAscuns)
        {
            this.stratAnterior = stratAnterior;

            if(((StratAscuns) stratAnterior).getStratUlterior() == this)
                return;

            ((StratAscuns) stratAnterior).setStratUlterior(this);
        }

        if(stratAnterior instanceof StratDeIntrare)
        {
            this.stratAnterior = stratAnterior;

            if(((StratDeIntrare) stratAnterior).getStratUlterior() == this)
                return;

            ((StratDeIntrare) stratAnterior).setStratUlterior(this);
        }
    }

    public double getEroareaRetelei()
    {
        if(this.eroareaRetelei == 0d)
            this.calculeazaEroareaRetelei();

        return eroareaRetelei;
    }

    public void setEroareaRetelei(double eroareaRetelei) {
        this.eroareaRetelei = eroareaRetelei;
    }

    public FunctieActivare getFunctieActivare() {
        return functieActivare;
    }

    public void setFunctieActivare(FunctieActivare functieActivare)
    {
        this.functieActivare = functieActivare;

        /*
        setam functia de activare decisa la nivel de strat pentru
        fiecare neuron
        */
        for(Neuron neuron: this.getNeuroni())
            neuron.setFunctieActivare(this.functieActivare);
    }

    public FunctieDeCost getFunctieDeCost() {
        return functieDeCost;
    }

    public void setFunctieDeCost(FunctieDeCost functieDeCost)
    {
        this.functieDeCost = functieDeCost;

        for(Neuron neuron: this.getNeuroni())
            neuron.setFunctieActivare(this.functieActivare);
    }

}
