package cosmin.regulaInvatare.multimeAntrenament.multimeEtichetata;

import cosmin.regulaInvatare.multimeAntrenament.MultimeAntrenament;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

// TODO
public abstract class MultimeAntrenamentEtichetata extends MultimeAntrenament
{
    private HashMap<Integer, String> corespondentaEticheta;
    private ArrayList<ArrayList<Double>> etichete;

    public MultimeAntrenamentEtichetata(File locatieMemorie, HashMap<Integer, String> corespondentaEticheta)
    {
        super(locatieMemorie);

        this.corespondentaEticheta = corespondentaEticheta;

        int nrClase = corespondentaEticheta.size();
        this.etichete = new ArrayList<>(nrClase);
        for(int i = 0; i < nrClase; ++i)
        {
            ArrayList<Double> eticheta =
                    new ArrayList<>(Collections.nCopies(corespondentaEticheta.size(), 0d));
            eticheta.set(i, 1d);
            this.etichete.add(eticheta);
        }
    }

    // --------- Setteri si Getteri -------------

    public HashMap<Integer, String> getCorespondentaEticheta()
    {
        return corespondentaEticheta;
    }

    public void setCorespondentaEticheta(HashMap<Integer, String> corespondentaEticheta)
    {
        this.corespondentaEticheta = corespondentaEticheta;
    }

    // -------------------- Sfarsit Getteri si Setteri---------------------------

    // hardcodarea unui vector de 0
    public ArrayList<Double> getEtichetaCorespunzatoare(int indexClasaElementului)
    {
        return etichete.get(indexClasaElementului);
    }

    public ArrayList<ArrayList<Double>> getEtichete()
    {
        return etichete;
    }

    public void setEtichete(ArrayList<ArrayList<Double>> etichete)
    {
        this.etichete = etichete;
    }
}
