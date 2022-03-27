package ReteleNeuronale;

import cosmin.regulInvatare.RegulaInvatare;
import cosmin.regulInvatare.multimeAntrenament.MultimeAntrenament;

// TODO implementat MLP
public interface ReteaNeuronala<RI extends RegulaInvatare>
{
    public void calculeazaIesirea();
    public void retroPropagareaErorii();

    public void antreneaza(MultimeAntrenament multimeAntrenament);
    public void antreneaza(MultimeAntrenament multimeAntrenament, RegulaInvatare regulaInvatare);
}
