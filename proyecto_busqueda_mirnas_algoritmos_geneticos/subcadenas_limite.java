/* Janeth De Anda Gil
   03/08/2016
   Programa que busca una subcadena con intercmbios entre dos cadenas */

import java.io.*;
class subcadenas_limite
{
    public static void main(String args[])
    {
        arma_matriz("TATATGCGGCCCCTATAGTACTATTGACACACCCAGTCAACACCCACGATCACAGATGGACATATAA", "AATGCCGGTCCGGGGCGCTAAATATTACACAGTACAGTAACCCGCTGCCAATCCATGACGATCAGT");
    }

    public static void arma_matriz(String cadena1, String cadena2)
    {
        int    long1 =0, long2=0;
        int    total =0;
        int    i =0, j=0;
        int    limite=6;
        int    valor =0;
        int    pos_i =0, pos_j=0;
        String subcad="";
        long1=cadena1.length(); //longitud de la cadena1
        long2=cadena2.length(); //longitud de la cadena2
        int matriz[][]=new int[long2 + 1][long1 + 1];
        /*inicializo la matriz en ceros*/
        for (i=0; i <= long2; i++)
            for (j=0; j <= long1; j++)
                matriz[i][j]=0;
        /*comienzo a armar la matriz a partir del elemento 1,1 porque la fila 0 y columna 0,serán ceros*/
        for (i=1; i <= long2; i++)
            for (j=1; j <= long1; j++)
            {
                if (cadena1.charAt(j - 1) == cadena2.charAt(i - 1)) //Si las letras son iguales
                {
                    if (cadena1.charAt(j - 1) == 'A' || cadena1.charAt(j - 1) == 'T')
                        total=matriz[i - 1][j - 1] + 2;  //agrego un uno a la diagonal
                    else if (cadena1.charAt(j - 1) == 'C' || cadena1.charAt(j - 1) == 'G')
                        total=matriz[i - 1][j - 1] + 4;  //agrego dos a la diagonal
                }
                else
                    total=matriz[i - 1][j - 1];
                matriz[i][j]=total;
                /*guardar las posiciones donde se encuentra la última letra de la mejor subcadena*/
                if (matriz[i][j] == limite)
                {
                    pos_i =i;
                    pos_j =j;
                    subcad="";
                    valor =matriz[pos_i][pos_j];
                    System.out.println("valor " + valor + "Pos " + pos_i + "," + pos_j);
                    subcad=cadena2.charAt(pos_i - 1) + subcad;
                    while (valor > 2)
                    {
                        valor =matriz[pos_i--][pos_j--];
                        subcad=cadena2.charAt(pos_i - 1) + subcad;
                    }
                    System.out.println("La subcadena común entre " + cadena1 + " y " + cadena2 + " es " + subcad);
                }
            }
        /*imprime la matriz*/
        /*  for(i=0;i<=long2;i++)
           {
               for(j=0;j<=long1;j++)
                   if(matriz[i][j]<0&&matriz[i][j]>-10||matriz[i][j]>=10)
                   System.out.print(" "+matriz[i][j]);
                   else if(matriz[i][j]>=0)
                       System.out.print("  "+matriz[i][j]);
                   else
                   System.out.print(matriz[i][j]);
           System.out.println("");
           }*/
    }
}
