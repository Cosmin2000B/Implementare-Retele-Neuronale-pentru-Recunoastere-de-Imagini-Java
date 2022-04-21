package cosmin.utilStructuriNeuronale.initializarePonderi;

import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;
import cosmin.reteleNeuronale.PerceptronMultiStrat;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratAscuns;
import cosmin.straturiNeuronale.straturiNeuronaleLiniare.StratNeuronalLiniar;

public class InitializareNguyenWidrow extends InitializareInterval
{
    /**
     * @param limitaInferioara limita inferioara a intervaului din care
     *                         dorim sa obtinem valori.
     * @param limitaSuperioara limita superioara a intervalului din care
     */
    public InitializareNguyenWidrow(double limitaInferioara, double limitaSuperioara)
    {
        super(limitaInferioara, limitaSuperioara);
    }

    @Override
    public void initializeazaPonderi(PerceptronMultiStrat perceptronMultiStrat)
    {
        super.initializeazaPonderi(perceptronMultiStrat);

        int dimensiuneStratIntrare =
                perceptronMultiStrat.getStratDeIntrare().getNumarNeuroni();

        int nrNeuroniStraturiAscunse = 0;
        for(StratAscuns stratAscuns: perceptronMultiStrat.getStraturiAscunse())
            nrNeuroniStraturiAscunse += stratAscuns.getNumarNeuroni();

        double beta = 0.7 * Math.pow(nrNeuroniStraturiAscunse, 1.0 / dimensiuneStratIntrare);

        for(StratAscuns stratAscuns: perceptronMultiStrat.getStraturiAscunse())
            initializeazaStrat(stratAscuns, beta);
        initializeazaStrat(perceptronMultiStrat.getStratDeIesire(), beta);
    }

    /**
     *   Initializeaza ponderile sinapselor unui strat neuronal.
     * functie cu uz intern. Evitarea redundantei de cod in initializeazaPonderi
     */
    private void initializeazaStrat(StratNeuronalLiniar stratNeuronalLiniar, double beta)
    {
        double norm = 0d;

        for(Neuron neuron:stratNeuronalLiniar.getNeuroni())
        {
            for(Sinapsa sinapsa: neuron.getSinapseIntrare())
            {
                double pondere = sinapsa.getPondere();
                norm += pondere * pondere;
            }
        }
        norm = Math.sqrt(norm);
        // beta *valPondere / norm
        for(Neuron neuron: stratNeuronalLiniar.getNeuroni())
        {
            for(Sinapsa sinapsa: neuron.getSinapseIntrare())
            {
                double pondere = sinapsa.getPondere();
                sinapsa.setPondere(beta * pondere / norm);
            }
        }
    }
}
