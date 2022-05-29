package cosmin.neuron;

import cosmin.functiiActivare.FunctieActivare;
import cosmin.functiiActivare.sigmoide.Logistica;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ionescu Cosmin
 *
 * @see Bias
 * @see Sinapsa
 * @see FunctieActivare
 */
public class Neuron implements Serializable
{
    /**
     * pentru identificarea compatibilitatii cu
     * versiuni anterioare ale clasei
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String numeIdentificare;

    private transient double valoareIntrare;
    private transient double valoareIesire;
    private transient double eroareNeuron;

    private ArrayList<Sinapsa> sinapseIntrare;
    private ArrayList<Sinapsa> sinapseIesire;

    private FunctieActivare functieActivare ;
    private Bias bias;

    // ----------- Constructori ------------------------

    public Neuron()
    {
        this.sinapseIntrare = new ArrayList<>();
        this.sinapseIesire = new ArrayList<>();

        this.functieActivare = new Logistica();
        this.bias = new Bias();
    }

    public Neuron(FunctieActivare functieActivare)
    {
        this.sinapseIntrare = new ArrayList<>();
        this.sinapseIesire = new ArrayList<>();

        this.functieActivare = functieActivare;
        this.bias = new Bias();
    }

    public Neuron(String numeIdentificare,
                  @NotNull ArrayList<Sinapsa> sinapseIntrare,
                  @NotNull ArrayList<Sinapsa> sinapseIesire,
                  FunctieActivare functieActivare, double pondereBias)
    {
        this.numeIdentificare = numeIdentificare;

        this.sinapseIntrare = new ArrayList<>();
        for(Sinapsa sinapsa: sinapseIntrare)
            this.adaugaSinapsaIntrare(sinapsa);

        this.sinapseIesire = new ArrayList<>();
        for(Sinapsa sinapsa: sinapseIesire)
            this.adaugaSinapsaIesire(sinapsa);

        this.functieActivare = functieActivare;
        this.bias = new Bias(pondereBias);
    }


    //------------- Sfarsit Constructori -----------------------

    /**
     *
     * @param neuronEmitent
     * @return
     */
    public boolean existaSinapsaIntrare(Neuron neuronEmitent)
    {
        for(Sinapsa sinapsa: this.sinapseIntrare)
            if(sinapsa.getNeuronEmitent() == neuronEmitent)
                return true;

        return false;
    }

    /**
     *
     * @param neuronDestinatar
     * @return
     */
    public boolean existaSinapsaIesire(Neuron neuronDestinatar)
    {
        for(Sinapsa sinapsa: this.sinapseIesire)
            if(sinapsa.getNeuronDestinatar() == neuronDestinatar)
                return true;

        return false;
    }

    /**
     *  Metoda adauga la lista de sinapse de intrare a neuronului
     * o sinapsa furnizata de utilizator.
     *  In contextul adaugarii unei sinapsa de intrare unui neuron,
     * neuronul actual trebuie sa fie neuronul destinatar, iar
     * celalalt cel emitent
     * @param sinapsa este sinapsa ce trebuie adaugata la lista de
     *                sinapse de intrare ale neuronului curent
     *                (neuronul destinatar trebuie sa coincida
     *                cu cel curent)
     */
    public void adaugaSinapsaIntrare(Sinapsa sinapsa)
    {
        // verificam daca sinapsa este nula
        if(sinapsa == null)
            throw new IllegalArgumentException("Sinapsa de intrare este nula!");

        /*
        verificam daca sinapsa poate fi adaugata la acest neuron.
          In contextul unei sinapse de intrare, neuronul destinatar
        trebuie sa fie cel curent.
         */
        if(sinapsa.getNeuronDestinatar() != this)
            throw new IllegalArgumentException("Nu se poate adauga aceasta sinapsa de intrare!"
            + " Neuronul destinatar nu coincide cu neuronul curent.");

        /*
          In cazul in care exista deja o sinapsa de intrare cu acest neuron emitent,
         iesim din metoda fara sa mai cream una.
         */
        if(existaSinapsaIntrare(sinapsa.getNeuronEmitent()))
            return;

        this.sinapseIntrare.add(sinapsa);

        /*
        // asigurarea simetriei:
        // o sinapsa intre un neuron emitent si un neuron
        // destinatar este o sinapsa de iesire pentru neuronul emitent
        // si o sinapsa de intrare pentru neuronul destinatar.
        */
        sinapsa.
                getNeuronEmitent().
                adaugaSinapsaIesire(sinapsa);
    }

    /**
     * @param neuronEmitent neuronul care transmite prin cadrul noii sinapse
     *                      catre neuronul pentru care e invocata metoda
     */
    public void adaugaSinapsaIntrare(Neuron neuronEmitent)
    {
        Sinapsa sinapsa = new Sinapsa(neuronEmitent, this);
        this.adaugaSinapsaIntrare(sinapsa);
    }

    /**
     *
     * @param neuronEmitent neuronul care transmite prin cadrul noii sinapse
     *                      catre neuronul pentru care e invocata metoda
     * @param pondereSinapsa ponderea pe care utilizatorul vrea sa o dea
     *                       sinapsei
     */
    public void adaugaSinapsaIntrare(Neuron neuronEmitent, double pondereSinapsa)
    {
        Sinapsa sinapsa = new Sinapsa(neuronEmitent, this, pondereSinapsa);
        this.adaugaSinapsaIntrare(sinapsa);
    }

    /**
     * @param sinapsa
     */
    public void adaugaSinapsaIesire(Sinapsa sinapsa)
    {
        // verificam daca sinapsa este nula
        if(sinapsa == null)
            throw new IllegalArgumentException("Sinapsa de iesire este nula!");

        /*
        verificam daca sinapsa poate fi adaugata la acest neuron.
          In contextul unei sinapse de iesire, neuronul emitent
        trebuie sa fie cel curent.
         */
        if(sinapsa.getNeuronEmitent() != this)
            throw new IllegalArgumentException("Nu se poate adauga aceasta sinapsa de iesire!"
                    + " Neuronul emitent nu coincide cu neuronul curent.");

        /*
          In cazul in care exista deja o sinapsa de iesire cu acest neuron destinatar,
         iesim din metoda fara sa mai cream una.
         */
        if(existaSinapsaIesire(sinapsa.getNeuronDestinatar()))
            return;

        this.sinapseIesire.add(sinapsa);

        /*
        // asigurarea simetriei:
        // o sinapsa intre un neuron emitent si un neuron
        // destinatar este o sinapsa de intrare pentru neuronul destinatar
        // si o sinapsa de iesire pentru neuronul emitent.
        */
        sinapsa.
                getNeuronDestinatar().
                adaugaSinapsaIntrare(sinapsa);
    }

    /**
     *
     * @param neuronDestinatar
     */
    public void adaugaSinapsaIesire(Neuron neuronDestinatar)
    {
        Sinapsa sinapsa = new Sinapsa(this, neuronDestinatar);
        this.adaugaSinapsaIesire(sinapsa);
    }

    /**
     *
     * @param neuronDestinatar
     * @param pondereSinapsa
     */
    public void adaugaSinapsaIesire(Neuron neuronDestinatar, double pondereSinapsa)
    {
        Sinapsa sinapsa = new Sinapsa(this, neuronDestinatar, pondereSinapsa);
        this.adaugaSinapsaIesire(sinapsa);
    }

    /**
     *  Intrarea sau starea unui neuron reprezinta suma intrarilor ponderate. Atat intrarile
     * cat si ponderile sunt date de sinapsele de intrare (sinapse al caror neuron destinatar
     * este neuronul pentru care se calculeaza intrarea/starea).
     */
    public void calculeazaIntrare()
    {
        this.valoareIntrare = 0d;
        for(Sinapsa sinapsa: this.sinapseIntrare)
            this.valoareIntrare += sinapsa.getValoareIesire();

        this.valoareIntrare += this.bias.calculeazaValoare();
    }
    /**
     *  Iesirea sau gradul de activare al unui neuron reprezinta calcularea  valorii functiei
     * de activare f(x) atribuite neuronului pentru valoarea x reprezentata de intrarea/starea
     * neuronului.
     */
    public void calculeazaIesire()
    {
        if(this.valoareIntrare == 0d)
            calculeazaIntrare();

        this.valoareIesire = this.
                                  functieActivare.
                                  valoareFunctie(this.valoareIntrare);
    }

    /**
     *  Reseteaza atat valoarea de intrare a neuronului, cat si pe cea de iesire
     * cu 0.
     */
    public void reseteazaStare()
    {
        this.valoareIntrare = 0d;
        this.valoareIesire = 0d;
    }

    // ---------------- Setteri si Getteri --------------------------


    public String getNumeIdentificare() {
        return numeIdentificare;
    }

    public void setNumeIdentificare(String numeIdentificare) {
        this.numeIdentificare = numeIdentificare;
    }

    public double getValoareIntrare()
    {
        if(this.valoareIntrare == 0d)
            calculeazaIntrare();

        return valoareIntrare;
    }

    public void setValoareIntrare(double valoareIntrare)
    {
        this.valoareIntrare = valoareIntrare;
    }

    public double getValoareIesire()
    {
        if(this.valoareIesire == 0d)
            calculeazaIesire();

        return valoareIesire;
    }

    public void setValoareIesire(double valoareIesire)
    {
        this.valoareIesire = valoareIesire;
    }

    public final ArrayList<Sinapsa> getSinapseIntrare()
    {
        return sinapseIntrare;
    }

    public void setSinapseIntrare(ArrayList<Sinapsa> sinapseIntrare)
    {
        if(!this.sinapseIntrare.isEmpty())
            sinapseIntrare.clear();

        for(Sinapsa sinapsa: sinapseIntrare)
            this.adaugaSinapsaIntrare(sinapsa);
    }

    public double getEroareNeuron() {
        return eroareNeuron;
    }

    public void setEroareNeuron(double eroareNeuron) {
        this.eroareNeuron = eroareNeuron;
    }

    public final ArrayList<Sinapsa> getSinapseIesire()
    {
        return sinapseIesire;
    }

    public void setSinapseIesire(ArrayList<Sinapsa> sinapseIesire)
    {
        if(!this.sinapseIesire.isEmpty())
            sinapseIesire.clear();

        for(Sinapsa sinapsa: sinapseIesire)
            this.adaugaSinapsaIesire(sinapsa);
    }

    public FunctieActivare getFunctieActivare()
    {
        return functieActivare;
    }

    public void setFunctieActivare(FunctieActivare functieActivare)
    {
        this.functieActivare = functieActivare;
    }

    public void setPondereBias(double pondere)
    {
        this.bias.setPondere(pondere);
    }

    public Bias getBias() {
        return bias;
    }

    public void setBias(Bias bias) {
        this.bias = bias;
    }

    // -----------------------------------------------------------

    @Override
    public String toString()
    {
        StringBuilder output = new StringBuilder("Neuronul " + this.getNumeIdentificare() + ":\n" +
                " A. valoare intrare (suma intrarilor ponderata + bias): "
                + this.valoareIntrare + ";\n B. valoare iesire (grad"
                + " de activare): " + this.valoareIesire + ";\n"
                + " C. eroare neuron (raportata la functia de cost): "
                + this.eroareNeuron+ ";\n" +
                " D. Sinapse intrare:\n");

        for(Sinapsa sinapsa: sinapseIntrare)
            output.append(sinapsa);

        output.append(" E. Sinapse iesire:\n");
        for(Sinapsa sinapsa: this.sinapseIesire)
            output.append(sinapsa);

        output.append(" F. Functie Activare: ").append(this.functieActivare.getClass().getSimpleName()).
                append(";\n").append(" F. Bias: \n").append(this.bias).append("\n");

        return output.toString();
    }

}
