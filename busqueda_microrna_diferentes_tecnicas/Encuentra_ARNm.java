import java.io.*;
import java.util.*;
class Encuentra_ARNm
{
    public static void main(String [] args)
    {
        int       s =0, j=0, coincidencias=0, mayor=0, i=0, n=0, m=0, mayor_t=0, mayor_r=0, temp=0, mr=0, mt=0;
        String    subsubcadena="";
        String [] micro       ={
            "hsa-miR-1302", "hsa-miR-200b-5p", "hsa-miR-210-3p", "hsa-let-7a-3p", "hsa-miR-4298", "hsa-miR-5691", "hsa-miR-3159", "hsa-miR-4456", "hsa-miR-146a-5p", "hsa-miR-429", "hsa-miR-34a-5p", "hsa-miR-3675-3p", "hsa-miR-4684-3p", "hsa-miR-4684-5p"
        };                                                                                                                                                                                                                                                     //MicroARN ocupados
        String [] genes ={
            "YWHAZ", "ZC3H12C", "FGFRL1", "RIMKLB", "YWHAZ", "CIPC", "ULK2", "HMGXB4", "NOVA1", "HIPK3", "ZEB1", "ZEB2", "TRIM33", "FAM76A", "CELF3", "FLOT2", "MYCN", "VAMP2", "LIMA1", "MEF2C", "JADE2"
        };                                      //ARNm ocupados
        String [] subcadenas  =new String[300]; //mejores semillas de MicroARN
        String [] subcadenas_r=new String[300]; // semillas con mayores repeticiones
        String [] subcadenas_t=new String[300]; //semillas con mayor termodicamica
        int       mayores[]   =new int[300];    //repeticiones
        int       temp_r[]    =new int[300];    //termodinámica de las cadenas con mayor repeticion
        int       temp_t[]    =new int[300];
        int       micro_r[]   =new int[300]; //microARN de las cadenas con mayor repeticion
        int       micro_t[]   =new int[300]; //microARN de las cadenas con mayor temperatura
        for (n=0; n < genes.length; n++)     //Recorre el arreglo de 3'UTR de ARNm de los genes
        {
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~GEN:" + genes[n] + "~~~~~~~~~~~~~~~~~~");
            Tree_suf_archivo ts=new Tree_suf_archivo(); //instancia de la clase TRee_suf_archivo
            ts.construye_arbol(genes[n] + ".txt");      //construye un árbol de sufijos del 3' UTR del ARNm
            for (m=0; m < micro.length; m++)            //Recorre el arreglo de los microARN
            {
                String subcadena=leer(micro[m] + ".txt");  //lee la secuencia del microARN

                for (s=0; s < subcadena.length() - (7 + s); s++) //s indica el número de elementos que se agregarán a la subcadena que será la semilla

                    for (j=0; j < 3; j++)  //genera las posibles semillas de la posicion 0 a la posición 3 de la secuencia del microRNA
                    {
                        subsubcadena =subcadena.substring(j, (7 + j + s));           //genera las semillas, cuya secuencia es una subcadena de la posicion j a la posición (7+j+s), es decir nuestra semilla será de tamaño 7 en adelante
                        coincidencias=ts.busca_cadena(ts.complemento(subsubcadena)); //busca cuantas repeticiones de la semilla existen
                        if (coincidencias >= mayor)                                  //si las repeticiones son las mayores hasta el momento
                        {
                            mayor=coincidencias;
                            if (mayor > 0)
                            {
                                if (coincidencias > mayor)  //si las repeticiones son mayores hasta el momento
                                    i=0;                    //inicializa i en cero
                                subcadenas[i]=subsubcadena; //guardo en el arreglo la semilla
                                mayores[i]   =mayor;        //guardo el número de repeticiones en el arreglo
                                i++;
                            }
                        }
                    }


                for (int k=0; k < i; k++)       //verifico todas las posibles semillas con mayores repeticiones guardadas
                {
                    temp=0;                                                                   //guarda el valor termodinamico
                    for (int l=0; l < subcadenas[k].length(); l++)                            //recorro cada letra de la posible semilla
                        if (subcadenas[k].charAt(l) == 'G' || subcadenas[k].charAt(l) == 'C') //si la letra es 'G' o 'C
                            temp=temp + 4;                                                    //sumar 4 a temp
                        else
                            temp=temp + 2;
                    //sumar 2 a temp
                    if (mayores[k] > 1) //si hay más de una repetición
                    {
                        if (mayores[k] >= mayor_r)             //si el número de repeticiones es mayor o igual al mayor número de repeticiones del microARN actual
                        {
                            if (mayores[k] > mayor_r) //si el número de repeticiones es mayor al mayor número de repeticiones del microARN actual
                                mr=0;                 //inicializar mr
                            mayor_r         =mayores[k];
                            subcadenas_r[mr]=subcadenas[k]; //agregar la semilla al arreglo de subcadenas_r
                            micro_r[mr]     =m;             //agrega al arreglo el indice del microARN
                            temp_r[mr]      =temp;          //agrega al arreglo el valor termodinámico
                            mr++;                           //incrementa mr
                        }
                        if (temp >= mayor_t)             //si el valor termodinámico es mayor o igual al mayor valor termodinámico del microARN actual
                        {
                            if (temp > mayor_t)     //si el valor termodinámico es mayor al mayor valor termodinámico del microARN actual
                                mt=0;               //inicializar mt
                            mayor_t         =temp;
                            subcadenas_t[mt]=subcadenas[k]; //agregar la semilla al arreglo de subcadenas_t
                            micro_t[mt]     =m;             //agrega al arreglo el indice del microARN
                            temp_t[mr]      =mayores[k];
                            mt++;            //incrementa mt
                        }
                    }
                }
                mayor=-1;
                i    =0;
            }
            for (int k=0; k < mr; k++) //Imprime los mejores resultados basado en el número de repeticiones de las semillas, con su valor termodinámico
            {
                System.out.print("***********microARN:" + micro[micro_r[k]] + "**********");
                System.out.print(subcadenas_r[k] + "->" + ts.complemento(subcadenas_r[k]) + " r: " + mayor_r + " t: " + temp_r[k]);
            }
            /* for(int k=0;k<mt;k++)
               {
                   System.out.print("***********microARN:"+micro[micro_t[k]]+"**********");
                  System.out.print(subcadenas_t[k]+"->"+ts.complemento(subcadenas_t[k])+" t: "+mayor_t+" r:"+temp_t[k]);
               }*/
            mayor_r=0;
            mr     =0;
            mayor_t=0;
            mt     =0;
            temp   =0;
        }
    }

    static String leer(String nombre_archivo)
    {
        char       letra =' ';
        String     cadena="";
        FileReader fr    = null;
        try {
            //Abrir el fichero indicado por el usuario
            fr = new FileReader(nombre_archivo);
            //Leer el primer carácter
            int caract = fr.read();         //leer el caracter
            //Se recorre el fichero hasta encontrar el carácter -1
            //   que marca el final del fichero
            while (caract != -1) {
                letra=(char)caract;     //letra de la cadena de ADN leída
                if (letra == 'A' || letra == 'C' || letra == 'G' || letra == 'U')
                    cadena=cadena + letra;
                caract = fr.read();    //leer el caracter
            }
            //System.out.println("cadena:"+cadena);
        }
        catch (FileNotFoundException e) {
            //Operaciones en caso de no encontrar el fichero
            System.out.println("Error: Fichero no encontrado");
            //Mostrar el error producido por la excepción
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            //Operaciones en caso de error general
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            //Operaciones que se harán en cualquier caso. Si hay error o no.
            try {
                //Cerrar el fichero si se ha abierto
                if (fr != null)
                    fr.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
        return cadena;
    }
}
