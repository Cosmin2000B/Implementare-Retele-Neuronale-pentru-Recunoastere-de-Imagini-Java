package cosmin.evenimente;

import cosmin.reteleNeuronale.ReteaNeuronala;

public class EvenimentReteaNeuronala extends java.util.EventObject
{
    private EvenimentReteaNeuronala.TipEveniment tipEveniment;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EvenimentReteaNeuronala(ReteaNeuronala source,
                                   EvenimentReteaNeuronala.TipEveniment tipEveniment)
    {
        super(source);
        this.tipEveniment = tipEveniment;
    }

    /**
     * Tipuri de evenimente admise pentru retele neuronale
     */
    public enum TipEveniment
    {
        RNA_CREATA,
        REGULA_INVATARE_ATASATA,
        MULTIME_DATE_ATASATA
    }

    public static TipEveniment RNA_CREATA = TipEveniment.RNA_CREATA;
    public static TipEveniment REGULA_INVATARE_ATASATA = TipEveniment.REGULA_INVATARE_ATASATA;
    public static  TipEveniment MULTIME_DATE_ATASATA = TipEveniment.MULTIME_DATE_ATASATA;

    // ----------------- Setteri si Getteri --------------------

    public TipEveniment getTipEveniment()
    {
        return tipEveniment;
    }

}
