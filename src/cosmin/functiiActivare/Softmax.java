package cosmin.functiiActivare;

import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.EntropieIncrucisata;
import org.jetbrains.annotations.NotNull;

/**
 * @aythor IonescuCosmin
 *   A se utiliza numai la clasificare multi-clasa
 */
public class Softmax implements FunctieActivare
{
    private double sumaFunctiiExponentiale;
    private StratDeIesire stratDeIesire;

    public Softmax(StratDeIesire stratDeIesire)
    {
        this.stratDeIesire = stratDeIesire;
    }

    /**
     * Calculeaza suma functiilor exponentiale e^(valoareIntrare)
     * pentru toti neuronii de pe stratul de iesire transmis in
     * constructor
     */
    public void calculeazaSumaFunctiiExponentiale()
    {
        for(Neuron neuron: this.stratDeIesire.getNeuroni())
            this.sumaFunctiiExponentiale += Math.exp(neuron.getValoareIntrare());
    }

    /**
     *
     * @param input valoarea de intrare a neuronului curent
     * @return valoarea functiei Softmax pentru valoarea de
     * intrare transmisa
     */
    @Override
    public double valoareFunctie(Double input)
    {
        // se utilizeaza impreuna cu EntropieIncrucisata
        if(!(stratDeIesire.getFunctieDeCost() instanceof EntropieIncrucisata))
            throw new IllegalArgumentException("Functia de cost trebuie sa fie"
            + " EntropieIncrucisata!");

       if(this.sumaFunctiiExponentiale == 0d)
           this.calculeazaSumaFunctiiExponentiale();

       // in urma calcularii este 0, atribuim o valoare foarte mica, a.i. sa
        // evitam impartirea la 0
       if(this.sumaFunctiiExponentiale == 0d)
           this.sumaFunctiiExponentiale = 0.00000001d;

       return Math.exp(input) / this.sumaFunctiiExponentiale;
    }

    /**
     *    Derivata are formula specifica pentru cazul particular in care functia
     *  de activare la nivel de strat este Softmax, iar Functia de Cost/Eroarea
     *  de clasificare se masoara cu ajutorul Entropiei.
     * @param input irelevant in acest caz, pastreaza modelul impus de interfata
     *              functiilor de activare FunctieActivare.
     * @return valoarea neutra in cadrul operatiei de inmultie, adica 1,
     *         astfel incats a fie retropropagata valoare calculata pt.
     *         derivata functiei de cost bazata pe entropie
     *         ( valoareDorita(indexNeuron) - valoareIesire(indexNeuron)
     */
    @Override
    public double valoareDerivata(Double input)
    {
        // resetam suma pentru urmatoarea propagare
        this.sumaFunctiiExponentiale = 0d;

        return 1d;
    }

    // ------------------ Getteri si Setteri -----------------------

    public double getSumaFunctiiExponentiale()
    {
        return sumaFunctiiExponentiale;
    }

    public void setSumaFunctiiExponentiale(double sumaFunctiiExponentiale)
    {
        this.sumaFunctiiExponentiale = sumaFunctiiExponentiale;
    }

    public StratDeIesire getStratDeIesire()
    {
        return stratDeIesire;
    }

    public void setStratDeIesire(StratDeIesire stratDeIesire)
    {
        this.stratDeIesire = stratDeIesire;
    }
}
