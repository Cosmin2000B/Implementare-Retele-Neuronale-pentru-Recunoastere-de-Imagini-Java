package cosmin.indiciPerformanta;

import java.util.ArrayList;

/**
 * Interfata generica pentru evaluatori
 * @param <TipEvaluator> tipul evaluatorului
 */
public interface EvaluatorPerformanta<TipEvaluator>
{
    /**
     * interpreteaza discrepanta / asemanarea dintre valorile obtinute de RNA si valorile
     * dorite
     * @param valoriIesire valorile neuronilor de pe stratul de iesire
     * @param valoriDorite valorile dorite pentru exemplul respectiv
     */
    public void proceseazaRezultatRna(ArrayList<Double> valoriIesire, ArrayList<Double> valoriDorite);

    /**
     *
     * @return rezultatul evaluarii
     */
    public TipEvaluator getRezultat();

    /**
     *
     */
    public void reseteazaEvaluator();
}
