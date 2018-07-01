import java.io.Serializable;

/**
 * Esta clase representa una arista junto con
 * la etiqueta del vertice al que esta pegado.
 */
public class Arista implements Serializable
{
    private static final long serialVersionUID = -2920756101510847292L;

    int peso;
    String etiqueta;

    public Arista(String etiqueta, int peso)
    {
        this.peso     = peso;
        this.etiqueta = etiqueta;
    }
}
