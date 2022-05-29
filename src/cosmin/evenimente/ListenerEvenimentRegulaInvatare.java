package cosmin.evenimente;

/**
 *  Interfata implementata de clasele ce asculta evenimente legate de procesul
 * de invatare al retelelor neuronale.
 */
@FunctionalInterface
public interface ListenerEvenimentRegulaInvatare extends java.util.EventListener
{
    /**
     *  Metoda executata in momentul cand are loc un eveniment legat de procesul de in-
     * vatare.
     * @param evenimentRegulaInvatare eveniment in care se regasesc informatiile legate
     *                                de evenimentul ce a avut loc.
     */
    public void handleEvenimentRegulaInvatare(EvenimentRegulaInvatare evenimentRegulaInvatare);
}
