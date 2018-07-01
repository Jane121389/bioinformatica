import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Representa un grafo como un mapa que asocia una etiqueta con su vertice.
 */
public class Grafo implements Serializable
{
    private static final long serialVersionUID = 6408167788703100175L;

    public static final boolean LOGUEO = true;
    Map<String, Vertice> datos;

    /**
     * Se puede construir un grafo a partir de su matriz de adyacencia.
     */
    public Grafo(int[][] matriz)
    {
        int largo = matriz.length;
        datos = new HashMap<String, Vertice>();
        for (int i = 0; i < largo; i++) {
            for (int j = 0; j < largo; j++)
                if (matriz[i][j] > 0)   //Si existe conexion entre i e j
                {
                    Vertice vertx = datos.get(i + ""); //Obtiene el vertice de la etiqueta i
                    //Si es nulo lo crea y lo mete al mapa de vertices
                    if (vertx == null)
                    {
                        vertx = new Vertice(i + "");
                        datos.put(i + "", vertx);
                    }
                    //Crea una arista nueva con la etiqueta de a quien conecta y su peso
                    Arista aristaNueva = new Arista(j + "", matriz[i][j]);
                    vertx.agregaArista(aristaNueva);
                }
        }
    }

    public void pega(String etiqueta1, String etiqueta2)
    {
        //Obtiene los vertices a partir de sus etiquetas
        Vertice vert1 = datos.get(etiqueta1);
        Vertice vert2 = datos.get(etiqueta2);
        //Crea la nueva etiqueta (une las dos con coma)
        String nuevaEtiqueta = etiqueta1 + "," + etiqueta2;
        //Crea el nuevo vertice
        Vertice union = new Vertice(nuevaEtiqueta);
        //Introduce el vertice al mapa
        datos.put(nuevaEtiqueta, union);
        //Itera sobre las aristas del vertice 1 para saber quien esta conectado a el
        for (Arista aristilla : vert1.aristas)
            //Descarta al arista del vertice 2 por que ya de nada sirve actualizar esa relacion (se van a pegar)
            if (!aristilla.etiqueta.equals(etiqueta2))
            {
                //Obtiene del mapa uno de los vertices que contiene al arista 1
                Vertice verticeQueMeTieneAMi = datos.get(aristilla.etiqueta);
                //Busca en ese vertice el arista que lo conecta con el vertice 1
                Arista miConexion = verticeQueMeTieneAMi.buscaArista(etiqueta1);
                //Ya que tiene el arista solo le cambia la etiqueta por la nueva
                miConexion.etiqueta = nuevaEtiqueta;
                //Crea la arista inversa (del vertice nuevo hacia este vertice)
                Arista nuevaArista = new Arista(verticeQueMeTieneAMi.etiqueta, miConexion.peso);
                //Agrega la arista nueva al vertice nuevo
                union.agregaArista(nuevaArista);
                //Si este vertice ademas de conectar al vertice 1 tambien conecta al 2 hay que sumar los pesos
                if (verticeQueMeTieneAMi.contieneConexionCon(etiqueta2))
                {
                    //Borra la conexion con 2 (la conexion con 1 fue la que le cambio el nombre y el peso)
                    Arista seBorro = verticeQueMeTieneAMi.borraArista(etiqueta2);
                    //Del vertice 2 también borra la conexion para no buscar y ahorrarnos eso
                    vert2.borraArista(verticeQueMeTieneAMi.etiqueta);
                    //Actualiza el peso del vertice encontrado hacia el nuevo vertice sumandole el de la conexion con 2
                    miConexion.peso += seBorro.peso;
                    //Actualiza el peso del vertice nuevo hacia el vertice encontrado.
                    nuevaArista.peso += seBorro.peso;
                }
            }
        //Ahora busca los del 2, solo que como ya se elimino las del 1 es mas sencillo
        for (Arista aristilla : vert2.aristas)
            if (!aristilla.etiqueta.equals(etiqueta1))
            {
                Vertice verticeQueMeTieneAMi = datos.get(aristilla.etiqueta);
                Arista  miConexion           = verticeQueMeTieneAMi.buscaArista(etiqueta2);
                miConexion.etiqueta = nuevaEtiqueta;
                Arista nuevaArista = new Arista(verticeQueMeTieneAMi.etiqueta, miConexion.peso);
                union.agregaArista(nuevaArista);
            }
        //Borra del mapa vertice 1 y 2
        datos.remove(etiqueta1);
        datos.remove(etiqueta2);
    }

    /**
     * Imprime el mapa de vertices, utiliza la impresion de los aristas de cada vertice.
     */
    public void imprime()
    {
        //Esta es la forma de iterar en mapas (no es bonita como la de listas)
        for (Entry<String, Vertice> nodo : datos.entrySet()) {
            System.out.println("Vertice con etiqueta -> " + nodo.getValue().etiqueta);
            nodo.getValue().imprimeAristas();
        }
    }

    //Este metodo copia un grafo por completo, utiliza algo raro que creo que no vale la pena aprender que hace cada cosa.
    public Grafo copia()
    {
        Grafo obj = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream    out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            out.close();
            ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
            obj = (Grafo) in.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }

    /**
     * Sirvve para imprimir cualquier cosa, se ahorra todo el
     * System.out.println y ademas se puede apagar, para no
     * tener que agregar y borrar lineas.
     */
    public void log(String texto)
    {
        if (LOGUEO)
            System.out.println(texto);
    }

    /**
     * Solo lo puse para probar cosillas
     */
    public static void main(String args[])
    {
        int[][] matriz = {{0, 1, 0, 2}, {1, 0, 0, 4}, {0, 0, 0, 7}, {2, 4, 7, 0}};
        Grafo   grafin = new Grafo(matriz);
        System.out.println("-------");
        Grafo grafin2 = grafin.copia();
        grafin2.pega("1", "3");
        grafin2.imprime();
    }
}
