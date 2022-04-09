package cosmin.operatii_io;

import java.io.*;

/**
 *
 */
public class IoDateSerializate
{
    private IoDateSerializate(){};

    /**
     *
     * @param locatieMemorie
     * @return
     */
    public static Object fin(String locatieMemorie)
    {
        try(ObjectInputStream fin =
                    new ObjectInputStream(new FileInputStream(locatieMemorie)))
        {
            return fin.readObject();
        }
        catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param ob
     * @param locatieDorita
     */
    public static void fout(Object ob, String locatieDorita)
    {
        try (ObjectOutputStream fout =
                     new ObjectOutputStream(new FileOutputStream(locatieDorita)))
        {
            fout.writeObject(ob);
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
}
