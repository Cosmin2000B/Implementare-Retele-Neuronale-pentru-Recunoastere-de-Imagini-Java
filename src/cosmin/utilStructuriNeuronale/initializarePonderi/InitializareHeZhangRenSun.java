package cosmin.utilStructuriNeuronale.initializarePonderi;

import cosmin.neuron.Neuron;
import cosmin.neuron.Sinapsa;

public class InitializareHeZhangRenSun extends InitializareSimpla
{
    /**
     *
     * @param neuron
     */
    @Override
    public void initializeazaPonderi(Neuron neuron)
    {
        final int nrSinapseIntrare = neuron.getSinapseIntrare().size();

        if(nrSinapseIntrare == 0) // nu avem sinapse
            return;

        final double deviatieStandard = Math.sqrt(2d / nrSinapseIntrare);
        final double limita = 3 * deviatieStandard;

        for(Sinapsa sinapsa: neuron.getSinapseIntrare())
        {
            final double valAleatorie = getGeneratorAleatoriu().nextDouble();
            final double pondere = (2 * limita * valAleatorie) - limita;
            sinapsa.setPondere(pondere);
        }
    }
}
