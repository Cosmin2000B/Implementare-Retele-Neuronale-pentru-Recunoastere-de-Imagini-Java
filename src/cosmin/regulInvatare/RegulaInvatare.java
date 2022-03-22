package cosmin.regulInvatare;

// TODO

import cosmin.regulInvatare.multimeAntrenament.MultimeAntrenament;

/**
 *
 */
public abstract class RegulaInvatare
{
    private boolean invatareOprita = false;
    private MultimeAntrenament multimeAntrenament;

    abstract public void invata(MultimeAntrenament multimeAntrenament);
}
