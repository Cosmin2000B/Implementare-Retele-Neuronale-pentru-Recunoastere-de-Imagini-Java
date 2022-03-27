package cosmin.straturiNeuronale;

import cosmin.functiiActivare.FunctieActivare;

public interface StratNeuronal
{
    public void calculeazaIesiri();
    public void reseteazaPonderi();
    public void actualizeazaPonderi(double rataInvatare);
    public void actualizeazaPonderi(double rataInvatare, double inertie);
}
