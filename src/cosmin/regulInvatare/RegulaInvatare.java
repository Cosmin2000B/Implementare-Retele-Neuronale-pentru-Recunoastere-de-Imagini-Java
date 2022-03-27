package cosmin.regulInvatare;

// TODO

import cosmin.regulInvatare.multimeAntrenament.MultimeAntrenament;

//TODO SGD, GD
/**
 *
 */
public abstract class RegulaInvatare
{
    /*
    // pentru multi-threading
    private boolean invatareOprita = false;
    */

    private MultimeAntrenament multimeAntrenament;

    abstract public void antreneaza(MultimeAntrenament multimeAntrenament);

    public MultimeAntrenament getMultimeAntrenament()
    {
        return multimeAntrenament;
    }

    public void setMultimeAntrenament(MultimeAntrenament multimeAntrenament)
    {
        this.multimeAntrenament = multimeAntrenament;
    }
}
