package cosmin.functiiActivare;

/**
 *  Interfata defineste comportamentul pe care orice functie de activare trebuie
 * sa-l aiba.
 *  Functia de activare (de transfer) are rolul de a determina <strong>gradul
 * de activare</strong> al unui neuron, prin intermediul ei putandu-se controla,
 * in functie de dezideratul aplicatiei, interpretarea starii neuronului.
 * @author Ionescu Cosmin
 */
public interface FunctieActivare
{
    /**
     *  Metoda calculeaza valoarea functiei de activare pentru
     * parametrul dat de utilizator.
     * @param input reprezinta valoarea obtinuta in urma modelarii
     *              efectului cumulativ al semnalelor de intrare,
     *              numita si <strong>starea neuronului artificial.
     *              </strong>
     * @return valoarea functiei de activare, numita si <strong>
     *     gradul de activare al neuronului artificial.</strong>
     */
    public double valoareFunctie(Double input);

    /**
     *  Metoda calculeaza valoarea derivatei (de ordinul I) functiei
     * de activare pentru parametrul dat de utilizator.
     * @param input valoarea numerica pentru care se doreste calculul
     *              derivatei.
     * @return valoarea derivatei functiei de activare pentru parametrul
     * dat de utilizator.
     */
    public double valoareDerivata(Double input);
}
