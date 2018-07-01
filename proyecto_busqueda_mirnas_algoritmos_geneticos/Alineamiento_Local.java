import java.io.*;
import java.util.*;
class Alineamiento_Local
{
    static MatrizAlinear[][] matriz;        //Objeto que tendra la matriz de alineamiento y los posibles caminos
    static int fila;
    static int columna;
    static int num_ant=0, s=0;
    static String cadena1;
    static String cadena2;
    static String cadena="";
    public static void inicializa(String cadena_uno, String cadena_dos)
    {
        int i=0, j=0; //indice de la matriz
        int k=0;      //Indice del vector
        cadena1=cadena_uno;
        cadena2=cadena_dos;
        fila   =cadena2.length() + 1;             //numero de filas de la matriz
        columna=cadena1.length() + 1;             //numero de columnas de la matriz
        matriz =new MatrizAlinear[fila][columna]; //matriz de alineacion
        MatrizAlinear m=new MatrizAlinear();
        for (i=0; i < fila; i++)
            for (j=0; j < columna; j++)
            {
                matriz[i][j]=new MatrizAlinear();
                matriz[i][j].set_numero(0); //inicializacion de la matriz en ceros
                for (k=0; k < 3; k++)
                    matriz[i][j].set_mueve(k, 0);  //inicializacion de movimientos en cero (0=no existe movimiento;1=existe movimiento)
            }
    }

    public static void llena_matriz()
    {
        int i =0, j=0;         //indice de la matriz
        int k         =0;      //Indice del vector
        int peso      =0;      //peso del elemento de la matriz
        int mayor_peso=0;      //toma el peso mayor
        int gap       =-2;     //valor del gap
        int sustituye =-1;     //valor de la sustitucion
        int igual     =1;
        int mover     =0; //Toma valores de 0=mueve en diagonal;1=mueve en vertical;2=mueve en horizontal
        for (i=1; i < fila; i++)
            for (j=1; j < columna; j++)
            {
                peso=matriz[i - 1][j - 1].get_numero();             //Se toma el peso de la diagonal anterior
                if (cadena1.charAt(j - 1) != cadena2.charAt(i - 1)) //Si las letras de las cadenas son diferentes
                    peso=peso + sustituye;                          //al peso se le aumenta el valor del score de una sustitucion
                else
                    peso=peso + igual;              //al peso se le aumenta el valor del score de una igualdad
                mayor_peso=peso;                    //a la variable mayor_peso, se le asigna el valor del peso actual
                matriz[i][j].set_mueve(0, 1);       //para indicar que existe movimiento 0=mueve en diagonal;1=mueve en                 vertical;2=mueve en horizontal
                peso=matriz[i - 1][j].get_numero(); //Se toma el peso de la fila anterior
                if ((peso + gap) >= mayor_peso)     //Si el peso mas el gap es mayor o igual al mayor peso actual
                {
                    mayor_peso=peso + gap;        //se le asigna a la variable mayor_peso el peso actual mas el gap
                    matriz[i][j].set_mueve(1, 1); //para indicar que existe movimiento 0=mueve en diagonal;1=mueve en                   vertical;2=mueve en horizontal
                    if ((peso + gap) > mayor_peso)
                        matriz[i][j].set_mueve(0, 0);  //para indicar que no existe movimiento
                }
                peso=matriz[i][j - 1].get_numero(); //Se toma el peso de la columna anterior
                if ((peso + gap) >= mayor_peso)     //Si el peso mas el gap es mayor o igual al mayor peso actual
                {
                    mayor_peso=peso + gap;        //se le asigna a la variable mayor_peso el peso actual mas el gap
                    matriz[i][j].set_mueve(2, 1); //para indicar que existe movimiento 0=mueve en diagonal;1=mueve en                   vertical;2=mueve en horizontal
                    if ((peso + gap) > mayor_peso)
                    {
                        matriz[i][j].set_mueve(0, 0); //para indicar que no existe movimiento
                        matriz[i][j].set_mueve(1, 0); //para indicar que no existe movimiento
                    }
                }
                if (mayor_peso >= 0)                      //Si el mayor_peso actual es mayor o igual a 0
                    matriz[i][j].set_numero(mayor_peso);  //al atributo numero de la matriz se le asigna el valor de                        la variable mayor_peso
                else                                      //de lo contrario
                    matriz[i][j].set_numero(0);           //al atributo numero de la matriz se le asigna 0
            }
    }

    public static void imprime_pesos()
    {
        int i=0, j=0;
        for (i=0; i < fila; i++) //recorre la matriz
        {
            for (j=0; j < columna; j++)
                System.out.print(matriz[i][j].get_numero());  //imprime el elemento numero de la matriz
            System.out.println("");
        }
    }

    public static int busca_mayor()
    {
        int mayor=0, i=0, j=0;
        /*busca el elemento mayor de la matriz*/
        for (i=0; i < fila; i++) //recorre la matriz
            for (j=0; j < columna; j++)
                if (matriz[i][j].get_numero() > mayor) //Si el numero de la matriz es mayor a la variable mayor
                    mayor=matriz[i][j].get_numero();
        //Se le asigna a la variable mayor el numero de la matriz
        return mayor;
    }

    public static void imprime_camino()
    {
        int mayor=0, i=0, j=0;
        mayor=busca_mayor();     //Busca el elemento mas grande
        for (i=0; i < fila; i++) //recorre la matriz
            for (j=0; j < columna; j++)
                if (matriz[i][j].get_numero() == mayor) //Si el numero de la matriz es igual a la variable mayor
                {
                    camino(i, j);
                    System.out.println("\n\n" + cadena); //Imprime la cadena optima 1
                    System.out.println(" ");
                    cadena="";
                }
    }

    public static void camino(int i, int j)
    {
        String pinta_camino="";
        if (matriz[i][j].get_numero() > 0)
        {
            if (matriz[i][j].get_mueve(0) == 1) //existe movimiento diagonal
                camino(i - 1, j - 1);
            if (matriz[i][j].get_mueve(1) == 1) //existe movimiento vertical
                camino(i - 1, j);
            if (matriz[i][j].get_mueve(2) == 1) //existe movimiento horizontal
                camino(i, j - 1);
            //pinta_camino=pinta_camino+matriz[i][j].get_numero();//conactena el camino en una
            if (matriz[i][j].get_numero() == (num_ant + 1))      //si la letra se mantiene igual
                System.out.print(cadena2.charAt(i - 1));         //imprime el caracter
            else if (matriz[i][j].get_numero() == (num_ant - 1)) //si la letra se sustituye
                System.out.print("S");                           //imprime una S que indica sustitución
            else if (matriz[i][j].get_numero() == (num_ant - 2)) //si existe un gap
                System.out.print("_");                           //imprime un _ que indica gap
            cadena =cadena + cadena1.charAt(j - 1);              //concatena letras de la cadena 1
            num_ant=matriz[i][j].get_numero();
        }
        else
            num_ant=0;
    }

    public static void main(String args[])
    {
        String  cadena1, cadena2;
        Scanner sc=new Scanner(System.in);
        System.out.println("Escriba la cadena 1:");
        cadena1=sc.next();
        System.out.println("Escriba la cadena 2:");
        cadena2=sc.next();
        inicializa(cadena1, cadena2); //inicializa los atributos de la matriz de objetos en cero
        llena_matriz();               //llena la matriz de alineación
        System.out.println("\nImprime la matriz final:");
        imprime_pesos(); //imprime la matriz de alineación
        System.out.println("\nImprime la alineacion local (donde 'S' es sustitución y '_' es un gap)");
        imprime_camino(); //imprime las alineaciones optimas
        System.out.println("");
    }
}

