package cosmin.regulaInvatare.multimeAntrenament;

import java.io.File;

public abstract class MultimeAntrenament
{
    private String denumire;
    private File locatieMemorie;

    public MultimeAntrenament(File locatieMemorie)
    {
        this.locatieMemorie = locatieMemorie;
        this.denumire = locatieMemorie.getName();
    }

    // --------- Setteri si Getteri ---------------

    public String getDenumire()
    {
        return denumire;
    }

    public void setDenumire(String denumire)
    {
        this.denumire = denumire;
    }

    public File getLocatieMemorie()
    {
        return locatieMemorie;
    }

    public void setLocatieMemorie(File locatieMemorie)
    {
        this.locatieMemorie = locatieMemorie;
    }
}
