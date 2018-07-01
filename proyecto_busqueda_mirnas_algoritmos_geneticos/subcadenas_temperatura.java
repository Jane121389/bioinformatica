/* Janeth De Anda Gil
   03/08/2016
   Programa que busca una subcadena con intercmbios entre dos cadenas */

import java.io.*;
class subcadenas_temperatura
{
    public static void main(String args[])
    {
        arma_matriz("ATGCGCTCCCTCCTGCTTCTCAGCGCCTTCTGCCTCCTGGAGGCGGCCCTGGCCGCCGAGGTGAAGAAACCTGCAGCCGCAGCAGCTCCTGGCACTGCGGAGAAGTTGAGCCCCAAGGCGGCCACGCTTGCCGAGCGCAGCGCCGGCCTGGCCTTCAGCTTGTACCAGGCCATGGCCAAGGACCAGGCAGTGGAGAACATCCTGGTGTCACCCGTGGTGGTGGCCTCGTCGCTAGGGCTCGTGTCGCTGGGCGGCAAGGCGACCACGGCGTCGCAGGCCAAGGCAGTGCTGAGCGCCGAGCAGCTGCGCGACGAGGAGGTGCACGCCGGCCTGGGCGAGCTGCTGCGCTCACTCAGCAACTCCACGGCGCGCAACGTGACCTGGAAGCTGGGCAGCCGACTGTACGGACCCAGCTCAGTGAGCTTCGCTGATGACTTCGTGCGCAGCAGCAAGCAGCACTACAACTGCGAGCACTCCAAGATCAACTTCCGCGACAAGCGCAGCGCGCTGCAGTCCATCAACGAGTGGGCCGCGCAGACCACCGACGGCAAGCTGCCCGAGGTCACCAAGGACGTGGAGCGCACGGACGGCGCCCTGCTAGTCAACGCCATGTTCTTCAAGCCACACTGGGATGAGAAATTCCACCACAAGATGGTGGACAACCGTGGCTTCATGGTGACTCGGTCCTATACCGTGGGTGTCATGATGATGCACCGGACAGGCCTCTACAACTACTACGACGACGAGAAGGAAAAGCTGCAAATCGTGGAGATGCCCCTGGCCCACAAGCTCTCCAGCCTCATCATCCTCATGCCCCATCACGTGGAGCCTCTCGAGCGCCTTGAAAAGCTGCTAACCAAAGAGCAGCTGAAGATCTGGATGGGGAAGATGCAGAAGAAGGCTGTTGCCATCTCCTTGCCCAAGGGTGTGGTGGAGGTGACCCATGACCTGCAGAAACACCTGGCTGGGCTGGGCCTGACTGAGGCCATTGACAAGAACAAGGCCGACTTGTCACGCATGTCAGGCAAGAAGGACCTGTACCTGGCCAGCGTGTTCCACGCCACCGCCTTTGAGTTGGACACAGATGGCAACCCCTTTGACCAGGACATCTACGGGCGCGAGGAGCTGCGCAGCCCCAAGCTGTTCTACGCCGACCACCCCTTCATCTTCCTAGTGCGGGACACCCAAAGCGGCTCCCTGCTATTCATTGGGCGCCTGGTCCGGCCTAAGGGTGACAAGATGCGAGACGAGTTATAG", "ATGACCATGGTTGACACAGAGATGCCATTCTGGCCCACCAACTTTGGGATCAGCTCCGTGGATCTCTCCGTAATGGAAGACCACTCCCACTCCTTTGATATCAAGCCCTTCACTACTGTTGACTTCTCCAGCATTTCTACTCCACATTACGAAGACATTCCATTCACAAGAACAGATCCAGTGGTTGCAGATTACAAGTATGACCTGAAACTTCAAGAGTACCAAAGTGCAATCAAAGTGGAGCCTGCATCTCCACCTTATTATTCTGAGAAGACTCAGCTCTACAATAAGCCTCATGAAGAGCCTTCCAACTCCCTCATGGCAATTGAATGTCGTGTCTGTGGAGATAAAGCTTCTGGATTTCACTATGGAGTTCATGCTTGTGAAGGATGCAAGGGTTTCTTCCGGAGAACAATCAGATTGAAGCTTATCTATGACAGATGTGATCTTAACTGTCGGATCCACAAAAAAAGTAGAAATAAATGTCAGTACTGTCGGTTTCAGAAATGCCTTGCAGTGGGGATGTCTCATAATGCCATCAGGTTTGGGCGGATGCCACAGGCCGAGAAGGAGAAGCTGTTGGCGGAGATCTCCAGTGATATCGACCAGCTGAATCCAGAGTCCGCTGACCTCCGGGCCCTGGCAAAACATTTGTATGACTCATACATAAAGTCCTTCCCGCTGACCAAAGCAAAGGCGAGGGCGATCTTGACAGGAAAGACAACAGACAAATCACCATTCGTTATCTATGACATGAATTCCTTAATGATGGGAGAAGATAAAATCAAGTTCAAACACATCACCCCCCTGCAGGAGCAGAGCAAAGAGGTGGCCATCCGCATCTTTCAGGGCTGCCAGTTTCGCTCCGTGGAGGCTGTGCAGGAGATCACAGAGTATGCCAAAAGCATTCCTGGTTTTGTAAATCTTGACTTGAACGACCAAGTAACTCTCCTCAAATATGGAGTCCACGAGATCATTTACACAATGCTGGCCTCCTTGATGAATAAAGATGGGGTTCTCATATCCGAGGGCCAAGGCTTCATGACAAGGGAGTTTCTAAAGAGCCTGCGAAAGCCTTTTGGTGACTTTATGGAGCCCAAGTTTGAGTTTGCTGTGAAGTTCAATGCACTGGAATTAGATGACAGCGACTTGGCAATATTTATTGCTGTCATTATTCTCAGTGGAGACCGCCCAGGTTTGCTGAATGTGAAGCCCATTGAAGACATTCAAGACAACCTGCTACAAGCCCTGGAGCTCCAGCTGAAGCTGAACCACCCTGAGTCCTCACAGCTGTTTGCCAAGCTGCTCCAGAAAATGACAGACCTCAGACAGATTGTCACGGAACACGTGCAGCTACTGCAGGTGATCAAGAAGACGGAGACAGACATGAGTCTTCACCCGCTCCTGCAGGAGATCTACAAGGACTTGTACTAG");
    }

    public static void arma_matriz(String cadena1, String cadena2)
    {
        int    long1 =0, long2=0;
        int    total =0;
        int    i =0, j=0;
        int    limite1=60, limite2=37;
        int    valor =0;
        int    pos_i =0, pos_j=0;
        String subcad ="";
        long1 =cadena1.length();                        //longitud de la cadena1
        long2 =cadena2.length();                        //longitud de la cadena2
        Temperaturas [][] temperatura=new Temperaturas[long2 + 1][long1 + 1];
        System.out.println("Las subcadenas comunes entre " + cadena1 + " y " + cadena2 + " son: ");
        /*inicializo la matriz en ceros*/
        for (i=0; i <= long2; i++)
            for (j=0; j <= long1; j++)
            {
                temperatura[i][j]      =new Temperaturas();
                temperatura[i][j].temp1=0;
                temperatura[i][j].temp2=0;
            }
        /*comienzo a armar la matriz a partir del elemento 1,1 porque la fila 0 y columna 0,serán ceros*/
        for (i=1; i <= long2; i++)
            for (j=1; j <= long1; j++)
            {
                if (cadena1.charAt(j - 1) == cadena2.charAt(i - 1)) //Si las letras son iguales
                {
                    if (cadena1.charAt(j - 1) == 'A' || cadena1.charAt(j - 1) == 'T')
                        total=temperatura[i - 1][j - 1].temp1 + 2;  //agrego un uno a la diagonal
                    else if (cadena1.charAt(j - 1) == 'C' || cadena1.charAt(j - 1) == 'G')
                        total=temperatura[i - 1][j - 1].temp1 + 4;  //agrego dos a la diagonal
                }
                else
                    total=temperatura[i - 1][j - 1].temp1;
                if (cadena1.charAt(j - 1) == 'A' || cadena1.charAt(j - 1) == 'T')
                    temperatura[i][j].temp2=2;  //agrego un uno a la diagonal
                else if (cadena1.charAt(j - 1) == 'C' || cadena1.charAt(j - 1) == 'G')
                    temperatura[i][j].temp2=4;  //agrego dos a la diagonal
                temperatura[i][j].temp1=total;
                //System.out.print(" temperatura 1:"+temperatura[i][j].temp1+" temperatura 2: "+temperatura[i][j].temp2+"  "+limite1+","+limite2);
                /*guardar las posiciones donde se encuentra la última letra de la mejor subcadena*/
                if (temperatura[i][j].temp1 <= limite2)
                {
                    pos_i =i;
                    pos_j =j;
                    subcad="";
                    valor =temperatura[pos_i--][pos_j--].temp1;
                    subcad=cadena2.charAt(pos_i) + subcad;
                    int tempe2    =0;
                    int num_letras=0;
                    while (valor > 2)
                    {
                        valor =temperatura[pos_i][pos_j].temp1;
                        subcad=cadena2.charAt(pos_i) + subcad;
                        tempe2=tempe2 + temperatura[pos_i][pos_j].temp2;
                        pos_i--;
                        pos_j--;
                        num_letras++;
                    }
                    if (tempe2 >= limite1 && num_letras == 27)
                        System.out.println(subcad + " temperatura 1:" + temperatura[i][j].temp1 + " temperatura 2: " + tempe2);
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
