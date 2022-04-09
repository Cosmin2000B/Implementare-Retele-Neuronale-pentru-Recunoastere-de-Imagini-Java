package cosmin.regulaInvatare;

import cosmin.regulaInvatare.multimeAntrenament.MultimeAntrenament;
import cosmin.reteleNeuronale.ReteaNeuronala;

import java.io.Serializable;

/**
 *   Regula de antrenament stabileste modul in care ajustam RNA pentru
 *  a obtine rezultate mai apropiate de cele asteptate.
 * @param <TipMultimeAntrenament> reprezinta tipul multimii de antrenament:
 *                               in cazul antrenarii supervizate, un element
 *                               de antrenament trebuie sa contine si o
 *                               eticheta pentru compararea cu rezultatul
 *                               obtinut.
 */
public abstract class RegulaInvatare<TipMultimeAntrenament extends MultimeAntrenament> implements Serializable
{
    /*
    // pentru multi-threading
    private boolean invatareOprita = false;
    */

    private ReteaNeuronala<?> reteaNeuronala;
    private transient TipMultimeAntrenament multimeAntrenament;

    public RegulaInvatare() {}

    abstract public void antreneaza();

    // ------------ Getteri si Setteri ---------------

    public ReteaNeuronala<?> getReteaNeuronala()
    {
        return reteaNeuronala;
    }

    public void setReteaNeuronala(ReteaNeuronala<?> reteaNeuronala)
    {
        this.reteaNeuronala = reteaNeuronala;
    }

    public TipMultimeAntrenament getMultimeAntrenament()
    {
        return multimeAntrenament;
    }

    public void setMultimeAntrenament(TipMultimeAntrenament multimeAntrenament)
    {
        this.multimeAntrenament = multimeAntrenament;
    }

}
