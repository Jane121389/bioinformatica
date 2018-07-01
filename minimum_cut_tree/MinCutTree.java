import java.io.*;
import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class MinCutTree
{
    static int M_adyacencia[][]; //a=0,b=1,...,y=24,z=25,(=26,)=28
    static int arboles[][];
    static int mayor;
    static int peso_total;
    public static void crea_matriz(int n_elementos, int numero_arboles)
    {
        M_adyacencia=new int[n_elementos][n_elementos]; //inicializa una matriz de adyacencia
        int     i, j, k, l, s=0;
        boolean salir=false, antes=true;
        //llena la matriz de adyacencia [n_elementos]x[n_elementos] //cada fila es una letra donde i=0 es a , i=1 es b etc.
        for (i=0; i < n_elementos; i++) //recorre n_elementos
        {
            for (j=i + 1; j < n_elementos; j++)    //recorre n_elementos
                for (k=0; k < numero_arboles; k++) //recorre los árboles
                {
                    antes=true;                                                    //indica que no aparece un '(' antes, es decir si está conectado
                    salir=false;                                                   //indica que no hay que salir de la operación
                    for (l=0; l < arboles[k].length && arboles[k][l] != i; l++);   //recorro el arreglo en columnas hasta que sea igual
                                                                                   //al número de la longitud del árbol y mientras el
                                                                                   //número de la letra del árbol sea diferente a la
                                                                                   //fila del arreglo
                    for (; l < arboles[k].length && salir == false; l++)           //recorro las letras del arbol hasta que termine su longitud o
                                                                                   //encuentre que el número de letra es igual a la columna(salir==false)
                    {
                        if (arboles[k][l] == j)
                            salir=true;
                        if (arboles[k][l] == -57 || arboles[k][l] == -1) //verifica si se encuentra el caracter '('
                            antes=false;
                    }
                    if (antes == true) //si no se encuentra el caracter '(' quiere decir que esa letra, esta relacionada con la letra
                                       //que tiene el número i y le sumo uno al valo que tenía
                    {
                        M_adyacencia[i][j]= M_adyacencia[i][j] + 1;
                        M_adyacencia[j][i]= M_adyacencia[j][i] + 1;
                    }
                }
        }
    }

    public static void inicio(int numero_arboles, int longitud_arbol)
    {
        arboles=new int[numero_arboles][longitud_arbol]; //inicializa la matriz de árboles
        //inicializa la matriz de árboles con valor -1, ya que no hay algún caracter que representaremos con valor -1
        for (int i=0; i < numero_arboles; i++)
            for (int j=0; j < longitud_arbol; j++)
                arboles[i][j]=-1;
    }

    public static void crea_arboles(String arbol, int n_arbol)
    {
        int i=0, cont_l=1, letra=0;
        //recorre letra por letra la cadena del arbol
        for (i=0; i < arbol.length(); i++)
        {
            letra              =(arbol.charAt(i) - 97); //le resta el número 97, para comenzar en el número cero el abecedario (a=0,b=1,...z=25)
            arboles[n_arbol][i]=letra;                  //coloca en una matriz el número de letra
            if (letra > mayor)                          //si la letra actual es mayor que la anterior, se asigna a la variable mayor la letra, es decir,
                                                        // obtiene la mejor letra
                mayor=letra;
        }
    }

    public static String [] busca_mayor(Grafo g, String etiqueta, int peso_mayor, int num_nodos, String [] nodos, int total_nodos, String etiqueta_antes)
    {
        Vertice vert1 = g.datos.get(etiqueta);
        String  vert =etiqueta_antes, vert_antes="";
        int     peso_m=0, peso_m_a=0;
        boolean eliminado;
        peso_total=0;
        //busca el mayor peso para viajar por el grafo
        for (Arista aristilla : vert1.aristas) {
            eliminado=false;
            //verifica si un nodo ya no esta en el conjuto del grafo
            for (int i=0; i < num_nodos; i++)
                if (aristilla.etiqueta.equals(nodos[i]))
                    eliminado=true;
            //si el nodo no fue eliminado
            if (eliminado == false)
                //busca el mayor peso
                if (aristilla.peso > peso_m)
                {
                    vert_antes=vert;
                    peso_m_a  =peso_m;
                    peso_m    =aristilla.peso;
                    vert      =aristilla.etiqueta;
                }
                //busca el siguiente mayor peso
                else if (aristilla.peso > peso_m_a)
                {
                    peso_m_a  =aristilla.peso;
                    vert_antes=aristilla.etiqueta;
                }
        }
        //si el mejor peso esta en una arista del nodo anterior
        if (peso_mayor > peso_m)
        {
            vert_antes=vert;
            vert      =etiqueta_antes;
            peso_m_a  =peso_m;
        }
        peso_total      =peso_mayor;
        nodos[num_nodos]=vert; //guarda el camino de los nodos
        num_nodos++;
        //recorre todo el grafo para encontrar donde cortar los grafos
        if (num_nodos < total_nodos)
            busca_mayor(g, vert, peso_m_a, num_nodos, nodos, total_nodos, vert_antes);
        else
        {
            peso_total=peso_m + peso_m_a;
            peso_total=g.pega(nodos[total_nodos - 1], nodos[total_nodos - 2]);
            peso_total=peso_total + peso_mayor;
        }
        return nodos;
    }

    public static String [] donde_dividir(int num_nodos, Grafo g)
    {
        int    peso_cortar    =1000;
        String cortar         ="";
        String cortar_cadena[]=new String[num_nodos];
        String nodillos[];
        for (int i=num_nodos; i > 2; i--) //recorro todos los vértices
        {
            Entry<String, Vertice> pareja = (Entry<String, Vertice>)(g.datos.entrySet().toArray()[0]);
            nodillos   =new String[i];
            nodillos[0]=pareja.getValue().etiqueta; //tomo el primer vértice
            for (int j=1; j < i; j++)
                nodillos[j]="";
            //Para saber donde cortar el grafo se hace uso del metodo MTCV Most tightly Connected Vertex
            nodillos=busca_mayor(g, pareja.getValue().etiqueta, 0, 1, nodillos, i, pareja.getValue().etiqueta);
            //Asigna en un arreglo , el arreglo que tiene el minimo corte
            if (peso_total < peso_cortar)
            {
                cortar_cadena=new String[i];
                cortar_cadena=nodillos;
                peso_cortar  =peso_total;
            }
        }
        return cortar_cadena;
    }

    public static Grafo divide_grafo(String [] cortar_cadena, Grafo g)
    {
        //System.out.println("Dividira grafito: ");
        for (Entry<String, Vertice> nodo : g.datos.entrySet())
            for (int i=0; i < cortar_cadena.length - 1; i++)
                nodo.getValue().borraArista(cortar_cadena[i]);
        for (int i=0; i < cortar_cadena.length - 1; i++)
            g.datos.remove(cortar_cadena[i]);
        return g;
    }

    public static void supertree(Grafo graf)
    {
        String complemento="";
        int    s =0, i = 0;
        System.out.print("(");
        //Si el tamaño del grafo es mayor que dos se vuelve a dividir
        if (graf.tamano() > 2)
        {
            String [] cortar=donde_dividir(graf.tamano(), graf.copia()); //nos indica donde cortar el grafo y
                                                                         //asigna el subgráfo al arreglo cortar
            complemento =cortar[cortar.length - 1];                      //obtiene el segundo subgrafo en String
            String [] cortar2=new String[(complemento.length() / 2) + 2];
            //Se guarda el segundo subgrafo en el arreglo cortar2
            for (i=0; i < complemento.length(); i++)
                if (complemento.charAt(i) != ',')
                {
                    cortar2[s]=complemento.charAt(i) + "";
                    s++;
                }
            cortar2[s]=" ";
            Grafo grafi2 = graf.copia();                 //Hace una copia del grafo de entrada
            Grafo graf2  =divide_grafo(cortar2, grafi2); //divide el gráfo de entrada que contenga los
                                                         //vértices del arreglo cortar2
            supertree(graf2);                            //llamada recursiva de superTree con el subgrafo como parámetro
            Grafo grafi1 = graf.copia();                 //Hace una copia del grafo de entrada
            Grafo graf1  = divide_grafo(cortar, grafi1); //divide el gráfo de entrada que contenga los
                                                         //vértices del arreglo cortar
            supertree(graf1);                            //llamada recursiva de superTree con el subgrafo como parámetro
        }
        else
            for (Entry<String, Vertice> nodo : graf.datos.entrySet())
                System.out.print(nodo.getValue().etiqueta);    //imprime las etiquetas de los vértices

        System.out.print(")");
    }

    /*verifica si un grafo es disconexo en caso correcto lo divide en los grafos conexos posibles*/
    public static void disconexo(int total_nodos, int [][] m_adya)
    {
        int      ocupados=0, j=0, k=0, i=0, aux=0;
        int [][] m_adya2       =new int[total_nodos][total_nodos];
        int []   nodos_ocupados=new int[total_nodos];
        //inicialiación del arreglo de nodos ocupados con 0
        for (i=0; i < total_nodos; i++)
            nodos_ocupados[i]=0;
        //mientras el total de nodos no sea igual a los que tiene la matriz
        while (total_nodos - 1 > ocupados)
        {
            j=0;
            for (i=0; i < total_nodos; i++)
                for (k=0; k < total_nodos; k++)
                    m_adya2[i][k]=0;
            //buscar un nodo que no se encuentre visitado
            while (nodos_ocupados[j] == 1) {
                j++;
            }
            //mientras que un nodo no se haya ocupado
            do {
                if (nodos_ocupados[j] == 0)
                {
                    nodos_ocupados[j]=1; //colocar que el nodo ya ha sido visitado
                    // llena la matriz m_adya2 con los nodos que se encuentran en la columna j
                    for (k=0; k < total_nodos; k++)
                    {
                        m_adya2[k][j]=m_adya[k][j];
                        m_adya2[j][k]=m_adya[j][k];
                        if (m_adya[k][j] != 0 && nodos_ocupados[k] == 0) //si algún nodo de la columna j es distinto
                                                                         //de cero y no ha sido visitado antes lo tomamos
                                                                         //para visitarlo porsteriormente
                            aux=k;
                    }
                    ocupados++;
                }
                j=aux;
            } while (nodos_ocupados[j] == 0);  //realiza el codigo mientras encontremos algún nodo no visitado
            Grafo grafin = new Grafo(m_adya2); //se realiza una instancia de la clase Grafo
            supertree(grafin);                 //crea un superArbol
        }

        System.out.println("");
    }

    public static void main(String args[])
    {
        int     i             =0;
        int     numero_arboles=0;
        String  arbol_str     ="";
        Scanner sc            =new Scanner(System.in);
        System.out.println("Número de arboles=");
        //Recibe el número de árboles como entrada
        numero_arboles=sc.nextInt();
        mayor         =0;
        //inicializa el arreglo que contendra los árboles
        inicio(numero_arboles, 20);
        for (i=0; i < numero_arboles; i++)
        {
            System.out.println("Arbol " + (i + 1) + "=");
            //acepta un árbol
            arbol_str=sc.next();
            //introduce en una matriz de enteros(donde cada entero es un caracter) los árboles
            crea_arboles(arbol_str, i);
        }
        mayor++;
        crea_matriz(mayor, numero_arboles);
        disconexo(mayor, M_adyacencia);
    }
}
