import java.io.*;
import java.util.*;
class Rearreglo_DCJ
{
    static int [][] rea1;
    static int [][] rea2;
    static int bloques=0;
    static int m_a    =0;
    static int adyacencias[][];
    static int rea1_or[][];
    static public void inicio()
    {
        Scanner sc =new Scanner(System.in);
        int     gen=0;
        int     i =0, s=0, i_r=0;
        int     c_l=1; //0=lineal, 1=circular
        System.out.println("\n                                CDJ                                           \n Un genoma puede estar compuesto de cromosomas lineales (telomero ) y/o circulares, el cromosoma lineal  comienza con un cero y finaliza con un cero ya que no hay forma de conectar el primer elemento del cromosoma con el último como sería el caso de un cromosoma circular \n Número de genes del genoma1(si es lineal o telomero agrega uno más por cada par de ceros):");
        bloques=sc.nextInt();        //número de genes del genoma 1
        rea1   =new int[bloques][2]; //coloca elementos de la matriz que contiene al genoma1
        System.out.println("Escribe el genoma 1 (Si es un telomero o linear ,comience con un 0 y finalicelo con un 0):");
        for (; i < bloques;)
        {
            System.out.println("Escribe el gen " + (i + 1) + " :");
            gen=sc.nextInt();         //entrada del gen
            if (gen == 0 && c_l == 0) //si el gen es cero (telomero) y la bandera c_l=0, es decir finaliza el telomero
            {
                rea1[i][s]=0; //se coloca en la matriz un 0
                c_l       =1; //se cambia la bandera c_l=1, que indica que la siguiente parte del gen puede ser circular
            }
            else if (gen == 0 && c_l == 2) //con el gen =0 y la badera c_l=2 quiere decir que finaliza una parte del genoma que es circular y comienza un telómero
            {
                rea1[i_r][0]=rea1[i - 1][1] * (-1); //el gen esta representado como [-g,+g] o [+g,-g],empezando con el gen con su signo contrario, este ultimo gen lo conectaremos con el primero ya que es un cromosoma circular
                c_l         =0;                     //bandera que indica un gen circular
                s           =0;
                rea1[i][s]  =0;
            }
            else if (gen == 0 && c_l == 1 && i > 2) //gen=0 y c_l=1 indica que acaba de finalizar un cromosoma, pero tambien acaba de empezarun cromosoma lineal, i>2 asegura que no es el primer cromosoma lineal
            {
                rea1[i][0]    =rea1[i - 1][0]; //se coloca el gen anterior
                rea1[i - 1][0]=0;              //se coloca un cero
                rea1[i - 1][1]=rea1[i - 2][1]; //al gen anterior se lo camia por el gen que se encuentra dos posiciones anteriores
                rea1[i - 2][1]=0;
                c_l           =0;
            }
            else if (gen == 0) //inicia un cromosoma lineal o telómero
            {
                rea1[i][s]=0;
                c_l       =0;
            }
            else
            {
                if (c_l == 1) //si es circular
                {
                    c_l=2; //cambiamos la bandera
                    i_r=i; //guardamos el indice de linear
                    s++;   //aumentamos el indice
                }
                rea1[i][s]=gen * (-1); //se asigna en la matriz el gen con el signo contrario
                s++;                   //se incrementa s que representa el número de columnas
                if (s > 1)             //si s>1 , es decir hay más de dos columnas
                {
                    s=0; //se inicializa s
                    i++; //Se incrementa i, que representa el número de filas
                }
                if (i < bloques) //Si las filas son menor al bloque de genes que inserto el usuario, colocamos el gen en la matriz
                    rea1[i][s]=gen;
            }
            s++;
            if (s > 1) //si s>1 , es decir hay más de dos columnas
            {
                s=0; //inicializar s, que representa las columnas
                i++; //incrementar i
            }
        }
        if (c_l == 2) //si la bandera c_l  es igual a 2 , significa que termino mi genoma circular

            rea1[i_r][0]=rea1[i - 1][1] * (-1);  //conecto mi último gen con el primero
        m_a=bloques;                             //asignamos a la variable m_a el número de bloques
        System.out.println("Número de bloques de los genoma2:");
        bloques=sc.nextInt(); //se asigna el número de bloques del genoma2
        rea2   =new int[bloques][2];
        System.out.println("Escribe el genoma 2 (Si es un telomero ,comience con un 0 y finalicelo con un 0):");
        /*inicializa variables*/
        s  =0; //inicializa s
        i  =0; //inicializa i
        c_l=1; //se coloca la bandera en 1 que indica que es un cromosoma circular
        for (; i < bloques;)
        {
            System.out.println("Escribe el gen " + (i + 1) + " :");
            gen=sc.nextInt();         //se asigna gen
            if (gen == 0 && c_l == 0) //si el gen es cero (telomero) y la bandera c_l=0, es decir finaliza el telomero
            {
                rea2[i][s]=0; //se coloca en la matriz un 0
                c_l       =1; //se cambia la bandera c_l=1, que indica que la siguiente parte del gen puede ser circular
            }
            else if (gen == 0 && c_l == 2) //con el gen =0 y la badera c_l=2 quiere decir que finaliza una parte del genoma que es circular y comienza un telómero
            {
                rea2[i_r][0]=rea1[i - 1][1] * (-1); //el gen esta representado como [-g,+g] o [+g,-g],empezando con el gen con su signo contrario, este ultimo gen lo conectaremos con el primero ya que es un cromosoma circular
                c_l         =0;                     //bandera que indica un gen circular
                s           =0;
                rea2[i][s]  =0;
            }
            else if (gen == 0 && c_l == 1 && i > 2) //gen=0 y c_l=1 indica que acaba de finalizar un cromosoma, pero tambien acaba de empezarun cromosoma lineal, i>2 asegura que no es el primer cromosoma lineal
            {
                rea2[i][0]    =rea2[i - 1][0]; //se coloca el gen anterior
                rea2[i - 1][0]=0;              //se coloca un cero
                rea2[i - 1][1]=rea2[i - 2][1]; //al gen anterior se lo camia por el gen que se encuentra dos posiciones anteriores
                rea2[i - 2][1]=0;
                c_l           =0;
            }
            else if (gen == 0) //(char)48=0
            {
                rea2[i][s]=0;
                c_l       =0;
            }
            else
            {
                if (c_l == 1) //si es circular
                {
                    c_l=2; //cambiamos la bandera
                    i_r=i; //guardamos el indice de linear
                    s++;   //aumentamos el indice
                }
                rea2[i][s]=gen * (-1); //inicia un cromosoma lineal o telómero
                s++;
                if (s > 1) //si s>1 , es decir hay más de dos columnas
                {
                    s=0; //se inicializa s
                    i++; //Se incrementa i, que representa el número de filas
                }
                if (i < bloques) //Si las filas son menor al bloque de genes que inserto el usuario, colocamos el gen en la matriz
                    rea2[i][s]=gen;
            }
            s++;
            s++;
            if (s > 1) //si s>1 , es decir hay más de dos columnas
            {
                s=0; //inicializar s, que representa las columnas
                i++; //incrementar i
            }
        }
        if (c_l == 2) //si la bandera c_l  es igual a 2 , significa que termino mi genoma circular

            rea2[i_r][0]=rea2[i - 1][1] * (-1); //conecto mi último gen con el primero
        /*inicializo la matriz de adyacencias con el tamaño maximo de los bloques de los genes y la matriz rea1_or*/
        if (bloques > m_a)
        {
            adyacencias=new int[bloques][2];
            rea1_or    =new int[bloques][2];
        }
        else
        {
            adyacencias=new int[m_a][2];
            rea1_or    =new int[m_a][2];
        }
    }

    /* Método para obtener la matriz de adyacencias*/
    static void adyacente(int [][] rea1, int [][] rea2)
    {
        int     i=0, j=0, k=0, l=0, s=0;
        boolean salir;
        /*inicializo la matriz de adyacencias con -1*/
        for (i=0; i < adyacencias.length; i++)
            for (j=0; j < 2; j++)
                adyacencias[i][j]=-1;
        /*busco en cada elemento del genoma2 ,con quien esta conectado del genoma 1*/
        for (i=0; i < adyacencias.length; i++) //recorre las filas

            for (j=0; j < 2; j++)
            {
                salir=false;
                if (rea2[i][j] != 0) //si el elemento es un gen, es decir, diferente de cero

                    for (k=0; salir == false && k < rea1.length; k++) //filas del genoma 1
                    {
                        for (l=0; l < 2; l++)
                            if (rea1[k][l] == rea2[i][j]) //cuando un elemento del genoma2 es igual a un elemento del genoma1
                            {
                                salir            =true;
                                adyacencias[i][s]=k; //se coloca en la matriz de adyacencia la fila de la matriz del elemento del genoma1 que es igual al elemento genoma2
                                s++;
                                if (s > 1) //si s>1 , es decir hay más de dos columnas
                                    s=0;
                            }
                    }
            }
        /*for(i=0;i<adyacencias.length;i++){
            for(j=0;j<2;j++)
                System.out.print(adyacencias[i][j]+" ");
            System.out.println("");
            }*/
    }

    /* Método que implementa el algoritmos de DCJ1*/
    static void DCJ1()
    {
        int intercambia=0;    //0,1 -> no intercambia;2->intercambia
        int v1 =0, v2=0, aux=0, i=0, p1=0, p2=0;
        for (i=0; i < adyacencias.length; i++) { //Se obtienen los elementos de la matriz de adyacencia,es decir, las filas de la matriz del genoma 1 que tienen un elemento común con 2
            v1=adyacencias[i][0];
            v2=adyacencias[i][1];
            if (v1 != -1 && v2 != -1 && v1 != v2) //Si los elementos v1 y v2 son diferentes de -1, quiere decir que existen un elemento igual en el genoma 2 y si los elementos son diferentes entre si
            {
                if (rea1[v1][0] != rea2[i][0] && rea1[v1][0] != rea2[i][1])      //si el elemento de la columna 0 y la fila v1 de la matriz del genoma 1 no es ni el elemento  de la columna 0 y 1 de la matriz del genoma 2.
                    p1=0;                                                        //se asigna a p1=0
                else if (rea1[v1][1] != rea2[i][1] && rea1[v1][1] != rea2[i][1]) //si el elemento de la columna 0 y la fila v1 de la matriz del genoma 1 no es ni el elemento de la columna 0 y 1 de la matriz del genoma 2.
                    p1=1;                                                        //se asigna a la variable p1=1
                if (rea1[v2][0] != rea2[i][0] && rea1[v2][0] != rea2[i][1])      //si el elemento de la columna 0 y la fila v2 de la matriz del genoma 1 no es ni el elemento de la columna 0 y 1 de la matriz del genoma 2.
                    p2=0;                                                        //se asigna a la variable p2=0
                else if (rea1[v2][1] != rea2[i][1] && rea1[v2][1] != rea2[i][1]) //si el elemento de la columna 1 y la fila v2 de la matriz del genoma 1 no es ni el elemento de la columna 0 y 1 de la matriz del genoma 2.
                    //el elememto p2  representa la columna que no es igual
                    p2=1;    //se asigna a la variable p2=1
                aux=    rea1[v2][p2];
                if (p1 == 0) //si p1 es igual a cero entonces la columna 0 de la matriz del genoma 1 no es igual al elemento del genoma 2
                {
                    //se intercambian el elemento p2 que es un elemento del genoma 1 que no se encuentra en el genoma 2, por el elemento 1 de la fila v1 que si se encuentra en el genoma 2
                    rea1[v2][p2]=   rea1[v1][1];
                    rea1[v1][1] =aux;
                }
                else
                {
                    //se intercambian el elemento p2 que es un elemento del genoma 1 que no se encuentra en el genoma 2, por el elemento 0 de la fila v1 que si se encuentra en el genoma 2
                    rea1[v2][p2]=   rea1[v1][0];
                    rea1[v1][0] =aux;
                }
                System.out.println("DCJ1:");
                imprime_genomas(rea1, rea2); //Imprime los genomas 1 y 2
                adyacente(rea1, rea2);       //genera la matriz adyacente
            }
        }
    }

    /* Método que implementa el DCJ3*/
    static void DCJ3()
    {
        int i=0, v1=0, v2=0, s=0;
        for (i=0; i < adyacencias.length; i++) {
            if (i < m_a)  //Se copia la matriz del genoma 1 en una nueva matriz, mientras el valor de 1 sea menor a los bloques indicados del genoma 1
            {
                rea1_or[s][0]=rea1[i][0];
                rea1_or[s][1]=rea1[i][1];
            }
            if (rea2[i][0] != 0 && rea2[i][1] == 0) //si encuentra un cero en la columna 1 de la matriz del genoma 2
            {
                v2=adyacencias[i][1];    //busco la fila de la adyacencia con el genoma 1
                // ya que se debe dejar un espacio para el nuevo elemento que se va a agregar , se coloca en el último elemento de la nueva matriz del genoma 1, la fila que voy a remplazar de la matriz del genoma 1
                rea1_or[m_a - 1][0]=rea1[v2 + 1][0];
                rea1_or[m_a - 1][1]=rea1[v2 + 1][1];
                if (rea1[v2][0] != 0 && rea1[v2][1] != 0) //si los dos elementos de la fila v2 a los que se les agregara 0 son diferentes de 0
                {
                    rea1_or[s][0]=rea1[v2][0]; //coloco en la nueva matriz el elemento de la columna 0 de la fila v2 de la matriz del genoma1
                    rea1_or[s][1]=0;           //en la columna 1 de la nueva matriz coloco un cero
                    s++;
                    rea1_or[s][0]=0;
                    rea1_or[s][1]=rea1[v2][1];    //coloco en la nueva matriz el elemento de la columna 1 de la fila v2 de la matriz del genoma 1
                }
            }
            s++;
        }


        System.out.println("DCJ3:");
        imprime_genomas(rea1_or, rea2);
        adyacente(rea1_or, rea2); //construye la matriz de adyacencias
        /*ordena los genes del genoma1*/
        for (i=0; i < adyacencias.length; i++) {
            v1=adyacencias[i][0]; //toma la fila de la matriz del genoma 1 con la que está relacionado
            v2=adyacencias[i][1]; //toma la fila de la matriz del genoma 2 con la que está relacionado
            if (v1 == v2)         //debe de estar relacionado con ambas v1 y v2 entonces deben de ser iguales
                if (i != v1)      //si la fila de la matriz del genoma 2 es diferente a la fila de la matriz del genoma1
                {
                    rea1_or[v1][0]=rea1_or[i][0];    //asignamos a esa fila el elemento
                    rea1_or[v1][1]=rea1_or[i][1];
                    rea1_or[i][0] =rea2[i][0];  //asignamos a la fila el elemento que es igual al de la columna 0 de la matriz del genoma 2
                    rea1_or[i][1] =rea2[i][1];  //asignamos a la fila el elemento que es igual al de la columna 1 de la matriz del genoma 2
                    adyacente(rea1_or, rea2);   //construimos la matriz adyacente,porque habremos modificado el orden
                }
        }
        System.out.println("Resultado:");
        imprime_genomas(rea1_or, rea2); //imprimer los genomas 1 y 2
    }

    /*Imprime los genomas*/
    static void imprime_genomas(int [][] rea1, int [][] rea2)
    {
        int i=0;
        /*recorre la matriz del genoma y lo imprime como la representación {gj,gi}*/
        for (i=0; i < rea1.length; i++)
            System.out.print(" (" + rea1[i][0] + "," + rea1[i][1] + ") ");
        System.out.println("");
        /*recorre la matriz del genoma y lo imprime como la representación {gj,gi}*/
        for (i=0; i < rea2.length; i++)
            System.out.print(" (" + rea2[i][0] + "," + rea2[i][1] + ") ");
        System.out.println("");
    }

    public static void main(String args[])
    {
        inicio();
        System.out.println("Genomas:");
        imprime_genomas(rea1, rea2);
        adyacente(rea1, rea2);
        DCJ1();
        DCJ3();
    }
}
