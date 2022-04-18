package cosmin.functiiActivare;

import cosmin.neuron.Neuron;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.StratDeIesire;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.stratDeIesire.functieDeCost.EntropieIncrucisata;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;

/**
 * @aythor IonescuCosmin
 *   A se utiliza numai la clasificare multi-clasa
 */
public class Softmax implements FunctieActivare
{
    private double sumaFunctiiExponentiale;
    private StratDeIesire stratDeIesire;

    // pt stabilitate numerica
    private double maxVal;

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
        maxVal = getStratDeIesire().getNeuroni().get(0).getValoareIntrare();
        for(Neuron neuron: stratDeIesire.getNeuroni())
            maxVal = Math.max(maxVal, neuron.getValoareIntrare());

        for(Neuron neuron: this.stratDeIesire.getNeuroni())
            this.sumaFunctiiExponentiale += FastMath.exp(neuron.getValoareIntrare() - maxVal);
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

        return FastMath.exp(input - maxVal) / this.sumaFunctiiExponentiale;
    }

    /*
    varianta instabila numeric ------------------------------------------------
  public void calculeazaSumaFunctiiExponentiale()
  {
      for(Neuron neuron: this.stratDeIesire.getNeuroni())
          this.sumaFunctiiExponentiale += Math.exp(neuron.getValoareIntrare());
  }

  @Override
  public double valoareFunctie(Double input)
  {
      // se utilizeaza impreuna cu EntropieIncrucisata
      if(!(stratDeIesire.getFunctieDeCost() instanceof EntropieIncrucisata))
          throw new IllegalArgumentException("Functia de cost trebuie sa fie"
                  + " EntropieIncrucisata!");

      if(this.sumaFunctiiExponentiale == 0d)
          this.calculeazaSumaFunctiiExponentiale();

      return Math.exp(input) / this.sumaFunctiiExponentiale;
  }
     --------------------------------------------------------------------------*/

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

    public double getMaxVal()
    {
        return maxVal;
    }

    public void setMaxVal(double maxVal)
    {
        this.maxVal = maxVal;
    }
}
