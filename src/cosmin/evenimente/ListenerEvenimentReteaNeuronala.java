package cosmin.evenimente;

/**
 *  Interfata implementata de clasele ce asculta evenimente legate de retelele
 * neuronale.
 */
@FunctionalInterface
public interface ListenerEvenimentReteaNeuronala extends java.util.EventListener
{
    /**
     *  Metoda executata in momentul cand are loc un eveniment legat o retea neuronala.
     * @param evenimentReteaNeuronala eveniment in care se regasesc informatiile legate
     *                                de evenimentul ce a avut loc.
     */
    public void handleEvenimentReteaNeuronala(EvenimentReteaNeuronala evenimentReteaNeuronala);
}
