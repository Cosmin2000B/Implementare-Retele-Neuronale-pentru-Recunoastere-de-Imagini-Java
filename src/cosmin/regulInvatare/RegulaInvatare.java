package cosmin.regulInvatare;

import cosmin.regulInvatare.multimeAntrenament.MultimeAntrenament;
import cosmin.reteleNeuronale.ReteaNeuronala;

//TODO SGD, GD

/**
 *   Regula de antrenament stabileste modul in care ajustam RNA pentru
 *  a obtine rezultate mai apropiate de cele asteptate.
 * @param <TipMultimeAntrenament> reprezinta tipul multimii de antrenament:
 *                               in cazul antrenarii supervizate, un element
 *                               de antrenament trebuie sa contine si o
 *                               eticheta pentru compararea cu rezultatul
 *                               obtinut.
 */
public abstract class RegulaInvatare<TipMultimeAntrenament extends MultimeAntrenament>
{
    /*
    // pentru multi-threading
    private boolean invatareOprita = false;
    */

    private ReteaNeuronala<?> reteaNeuronala;
    private TipMultimeAntrenament multimeAntrenament;
    private double rataInvatare = 0.1d;
    private double inertie = 0.9d;

    abstract public void antreneaza();

    public TipMultimeAntrenament getMultimeAntrenament()
    {
        return multimeAntrenament;
    }

    public void setMultimeAntrenament(TipMultimeAntrenament multimeAntrenament)
    {
        this.multimeAntrenament = multimeAntrenament;
    }
}
