package cosmin.functiiActivare;

/**
 *  Interfața definește comportamentul pe care orice funcție de activare trebuie
 * să-l aibă.
 *  Funcția de activare (de transfer) are rolul de a determina <strong>gradul
 * de activare</strong> al unui neuron, prin intermediul ei putându-se controla,
 * în funcție de dezideratul aplicației, interpretarea stării neuronului.
 * @author Ionescu Cosmin
 */
public interface FunctieActivare
{
    /**
     *  Metoda calculează valoarea funcției de activare pentru
     * parametrul dat de utilizator.
     * @param input reprezintă valoarea obținută în urma modelării
     *              efectului cumulativ al semnalelor de intrare,
     *              numită și <strong>starea neuronului artificial.
     *              </strong>
     * @return valoarea funcției de activare, numită și <strong>
     *     gradul de activare al neuronului artificial.</strong>
     */
    public double valoareFunctie(Double input);

    /**
     *  Metoda calculează valoarea derivatei (de ordinul I) funcției
     * de activare pentru parametrul dat de utilizator.
     * @param input valoarea numerică pentru care se dorește calculul
     *              derivatei.
     * @return valoarea derivatei funcției de activare pentru parametrul
     * dat de utilizator.
     */
    public double valoareDerivata(Double input);
}
