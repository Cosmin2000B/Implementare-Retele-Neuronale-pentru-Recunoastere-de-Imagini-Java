package cosmin.neuron;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Ionescu Cosmin
 *
 * @see Neuron
 */
public class Sinapsa implements Serializable
{
    /**
     * pentru identificarea cu compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Neuron neuronEmitent;
    /**
     *
     */
    private Neuron neuronDestinatar;

    private double pondere;
    private transient double penultimaDeltaPondere = 0d;
    private transient double deltaPondere;

    private transient double valoareIesire;

    /**
     *  Constructorul cu doi parametrii de tip Neuron creaza o sinapsa care va
     * transmite valoarea de iesire/gradul de activare a/al neuronului emitent
     * catre neuronul destinatar, aceasta valoare fiind ponderata de ponderea
     * sinapsei.
     *  In acest caz, ponderea sinapsei este initializata aleator cu valori
     * rationale intre [-0.5, 0.5) / {0}.
     * @param neuronEmitent reprezinta neuronul care isi transmite gradul de
     *                      activare prin intermediul sinapsei catre neuronul
     *                      destinatar.
     * @param neuronDestinatar reprezinta neuronul care va primi prin intermeiul
     *                         sinapsei gradul de activare al neuronului emitent,
     *                         inmultit cu ponderea sinapsei.
     */
    public Sinapsa(Neuron neuronEmitent, Neuron neuronDestinatar)
    {
        this.neuronEmitent = neuronEmitent;
        this.neuronDestinatar = neuronDestinatar;

        // formula mai promitatoare decat [0,1)
        // todo de vazut euristici specifice
        this.pondere = ThreadLocalRandom.current().nextDouble() - 0.5d;
        // evitarea initializarii cu 0
        if(this.pondere == 0)
            this.pondere = 0.1d;
    }

    /**
     *  Constructorul cu trei parametrii: primii doi sunt de tip Neuron si repre-
     * zinta neuronul emitent, respectiv neuronul destinatar, iar al treilea para-
     * metru este ponderea pe care utilizatorul o atribuie sinapsei.
     *  Sinapsa creata transmite valoarea de iesire/gradul de activare a/al neuro-
     * nului emitent catre neuronul destinatar. Valoarea transimisa este inmultita
     * cu ponderea sinapsei.
     * @param neuronEmitent reprezinta neuronul care isi transmite gradul de acti-
     *                      vare prin intermediul sinapsei catre neuronul destinatar.
     * @param neuronDestinatar reprezinta neuronul care va primi prin intermediul
     *                         sinapsei gradul de activare al neuronului emitent,
     *                         inmultit cu ponderea sinapsei.
     * @param pondere este o valoare rationala stabilita de utilizator si reprezin-
     *                ta factorul cu care este inmultit gradul de activare al ne-
     *                uronului emitent pana sa fie transmis la neuronul destinatar.
     */
    public Sinapsa(Neuron neuronEmitent, Neuron neuronDestinatar, double pondere)
    {
        this.neuronEmitent = neuronEmitent;
        this.neuronDestinatar = neuronDestinatar;
        this.pondere = pondere;
    }

    /**
     *  In functie de regula de invatare atasata retelei neuronale, ponderile sinapselor
     * sunt ajustate cu scopul obtinerii unui rezultat mai apropiat de cel dorit. Scopul
     * acestui proces poate fi cuantificat si prin dorinta de a avea o functie de cost/
     * eroare de clasificare cat mai mica.
     * @param rataInvatare un hiperparametru ales in cadrul structurii retelei neuronale.
     *                     Acesta ia, in general, valori rationale din intervalul (0,1)
     *                     si reprezinta o modalitate de a controla cat de mari vor fi
     *                     ajustarile ponderilor in raport cu erorile de clasificare
     *                     obtinute.
     */
    public void actualizeazaPondere(double rataInvatare)
    {
        this.pondere -= rataInvatare * this.deltaPondere;
        // cand actualizam, resetam eroarea acumulata
        this.deltaPondere = 0;
    }

    /**
     *  In functie de regula de invatare atasata retelei neuronale, ponderile sinapselor
     * sunt ajustate cu scopul obtinerii unui rezultat mai apropiat de cel dorit. Scopul
     * acestui proces poate fi cuantificat si prin dorinta de a avea o functie de cost/
     * eroare de clasificare cat mai mica.
     * @param rataInvatare un hiperparametru ales in cadrul structurii retelei neuronale.
     *                     Acesta ia, in general, valori rationale din intervalul (0,1)
     *                     si reprezinta o modalitate de a controla cat de mari vor fi
     *                     ajustarile ponderilor in raport cu erorile de clasificare
     *                     obtinute.
     * @param inertie un hiperparametru ales in cadrul structurii retelei neuronale.
     *                Acesta ia, in general, valori rationale din intervalul (0,1)
     *                si reprezinta o modalitate de a controla cat de mare vo fi
     *                aportul ponderii precedente in ajustarea actuala.
     */
    public void actualizeazaPondere(double rataInvatare, double inertie)
    {
        // formula utilizata cu (1 - inertie) pentru evitarea scalarii ratei de invatare
        this.pondere -=
                rataInvatare * (inertie * this.penultimaDeltaPondere + (1 - inertie) * this.deltaPondere);

        // adaugam deltaPondere actuala la penultimaDeltaPondere
        this.penultimaDeltaPondere = inertie * this.penultimaDeltaPondere
                + (1 - inertie) * this.deltaPondere;
        // cand actualizam, resetam eroarea acumulata
        this.deltaPondere = 0;
    }

    // ------------ Setter si Getteri -------------------------------------//


    public Neuron getNeuronEmitent()
    {
        return neuronEmitent;
    }

    public void setNeuronEmitent(Neuron neuronEmitent)
    {
        this.neuronEmitent = neuronEmitent;
    }

    public Neuron getNeuronDestinatar()
    {
        return neuronDestinatar;
    }

    public void setNeuronDestinatar(Neuron neuronDestinatar)
    {
        this.neuronDestinatar = neuronDestinatar;
    }

    public double getPondere()
    {
        return pondere;
    }

    public void setPondere(double pondere)
    {
        this.pondere = pondere;
    }

    public double getDeltaPondere() {
        return deltaPondere;
    }

    public void setDeltaPondere(double deltaPondere) {
        this.deltaPondere = deltaPondere;
    }

    public double getPenultimaDeltaPondere()
    {
        return penultimaDeltaPondere;
    }

    public void setPenultimaDeltaPondere(double penultimaDeltaPondere)
    {
        this.penultimaDeltaPondere = penultimaDeltaPondere;
    }

    /**
     *  Aceasta metoda calculeaza valoarea transmisa catre neuronul destinatar prin
     * inmultirea valorii de iesire/gradului de activare a/al neuronului emitent cu
     * ponderea sinapsei.
     *  Prin adunarea tuturor valori de iesire ale sinapselor de intrare dintr-un
     * neuron, la care se adauga un termen liber negativ propriu neuronului (bias),
     * se obtine valoarea de intrare/starea neuronului.
     * @return valoarea returnata de aceasta metoda este un numar rational, reprezen-
     * tand produsul dintre valoarea de iesire/gradul de activare a/al neuronului
     * emitent si ponderea sinapsei.
     */
    public double getValoareIesire()
    {
        this.valoareIesire = this.neuronEmitent.getValoareIesire() * this.pondere;
        return this.valoareIesire;
    }

    // ----------------------------------------------------------------------------

    @Override
    public String toString()
    {
        return " * Sinapsa:\n" + "  - neuron emitent: " + this.neuronEmitent.getNumeIdentificare()
                + ";\n" + "  - neuron destinatar: " + this.neuronDestinatar.getNumeIdentificare()
                + ";\n" + "  - pondere sinapsa: " + this.pondere + ";\n  - ajustare pondere " +
                "sinapsa (delta pondere): " + this.deltaPondere + ";\n  - ultima valoare de " +
                "iesire: " + this.getValoareIesire() + "\n";
    }
}
