package cosmin.evenimente;

import cosmin.regulaInvatare.RegulaInvatare;

/**
 *
 */
public class EvenimentRegulaInvatare extends java.util.EventObject
{
    private EvenimentRegulaInvatare.TipEveniment tipEveniment;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EvenimentRegulaInvatare(RegulaInvatare source,
                                   EvenimentRegulaInvatare.TipEveniment tipEveniment)
    {
        super(source);
        this.tipEveniment = tipEveniment;
    }

    /**
     * Tipuri de evenimente admise pentru reguli de invatare.
     */
    public enum TipEveniment
    {
        INCPERE_INVATARE,
        EPOCA_FINALIZATA,
        INVATARE_OPRITA
    }

    public static TipEveniment INCEPERE_INVATARE = TipEveniment.INCPERE_INVATARE;
    public static TipEveniment EPOCA_FINALIZATA = TipEveniment.EPOCA_FINALIZATA;
    public static TipEveniment INVATARE_OPRITA = TipEveniment.INVATARE_OPRITA;

    // ----------------- Setteri si Getteri --------------------

    public TipEveniment getTipEveniment()
    {
        return tipEveniment;
    }

}
