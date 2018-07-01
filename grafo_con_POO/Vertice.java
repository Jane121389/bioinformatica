import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa a un vertice, su etiqueta y la lista de sus adyacencias.
 */
public class Vertice implements Serializable
{
    private static final long serialVersionUID = -8742995850115033239L;

    /**
     * Su etiqueta o valor.
     */
    String etiqueta;
    /**
     * La lista de adyacencias, o mas bien la lista de aristas que salen de el.
     */
    List<Arista> aristas;

    /**
     * Para construir un vertice solo necesitamos su etiqueta.
     */
    public Vertice(String etiqueta)
    {
        this.etiqueta = etiqueta;
    }

    /**
     * Este metodo nos permite agregar un arista al vertice
     * sin tocar la lista desde otra clase afuera.
     */
    public void agregaArista(Arista arista)
    {
        if (aristas == null)
            aristas = new ArrayList<Arista>();
        aristas.add(arista);
    }

    /**
     * Nos indica si el vertice tiene conexion con
     * otro vertice de la etiqueta dada.
     */
    public boolean contieneConexionCon(String nombre)
    {
        for (Arista elemento : aristas)
            if (elemento.etiqueta.equals(nombre))
                return true;
        return false;
    }

    /**
     * Nos devuelve la arista que conecta al vertice que le damos.
     */
    public Arista buscaArista(String nombre)
    {
        for (Arista elemento : aristas)
            if (elemento.etiqueta.equals(nombre))
                return elemento;
        return null;
    }

    /**
     * Borra una arista que conecta con el vertice dado.
     */
    public Arista borraArista(String nombre)
    {
        Arista seVaABorrar     = null;
        int    indicePorBorrar = 0;
        for (int i = 0; i < aristas.size(); i++)
            if (aristas.get(i).etiqueta.equals(nombre))
            {
                seVaABorrar     = aristas.get(i);
                indicePorBorrar = i;
            }
        aristas.remove(indicePorBorrar);
        return seVaABorrar;
    }

    /**
     * Imprime todas las aristas conectadas a este vertice.
     */
    public void imprimeAristas()
    {
        System.out.println("Tiene aristas");
        for (Arista elemento : aristas)
            System.out.println("Conectado a -> " + elemento.etiqueta + " , con peso -> " + elemento.peso);
    }
}
