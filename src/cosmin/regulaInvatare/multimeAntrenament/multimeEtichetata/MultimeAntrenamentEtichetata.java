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

    public MultimeAntrenamentEtichetata(File locatieMemorie, HashMap<Integer, String> corespondentaEticheta)
    {
        super(locatieMemorie);

        this.corespondentaEticheta = corespondentaEticheta;
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
        ArrayList<Double> eticheta =
                new ArrayList<>(Collections.nCopies(corespondentaEticheta.size(), 0d));
        eticheta.set(indexClasaElementului, 1d);
        return eticheta;
    }
}
