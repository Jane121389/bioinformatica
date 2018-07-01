import java.io.*;
import java.util.*;
class Arbol_s_a
{
    public static void main(String [] args)
    {
        int       s =0, j=0, coincidencias=0, mayor=0, i=0, n=0, m=0;
        String    subsubcadena="";
        String [] micro       ={
            "hsa-miR-1302", "hsa-miR-200b-5p", "hsa-miR-210-3p", "hsa-let-7a-3p", "hsa-miR-4298", "hsa-miR-5691", "hsa-miR-3159", "hsa-miR-4456", "hsa-miR-146a-5p", "hsa-miR-429", "hsa-miR-34a-5p", "hsa-miR-3675-3p", "hsa-miR-4684-3p", "hsa-miR-4684-5p"
        };
        String [] genes ={
            "YWHAZ", "ZC3H12C", "FGFRL1", "RIMKLB", "YWHAZ", "CIPC", "ULK2", "HMGXB4", "NOVA1", "HIPK3", "ZEB1", "ZEB2", "TRIM33", "FAM76A", "CELF3", "FLOT2", "MYCN", "VAMP2", "LIMA1", "MEF2C", "JADE2"
        };
        String [] subcadenas=new String[30];
        for (m=0; m < micro.length; m++)
        {
            System.out.println("\n~~~~~~~~~~~~~MicroARN:" + micro[m] + "~~~~~~~");
            String subcadena=leer(micro[m] + ".txt");
            for (n=0; n < genes.length; n++)
            {
                System.out.print("***********Gen:" + genes[n] + "**********");

                ts.construye_arbol(genes[n] + ".txt");
                //System.out.println(ts.busca_cadena(ts.complemento("AUCUUAC")));
                for (s=0; s < (subcadena.length() - 7); s++)
                {
                    mayor=-1;
                    i    =0;
                    for (j=0; j < 3; j++)
                    {
                        subsubcadena =subcadena.substring(j, (7 + j + s));
                        coincidencias=ts.busca_cadena(ts.complemento(subsubcadena));
                        if (coincidencias >= mayor)
                        {
                            mayor=coincidencias;
                            if (mayor > 0)
                            {
                                if (coincidencias > mayor)
                                    i=0;
                                subcadenas[i]=subsubcadena;
                                i++;
                            }
                        }
                    }
                    //System.out.println("i "+i);
                    for (int k=0; k < i; k++)
                        if (mayor > 0)
                            System.out.print(subcadenas[k] + "->" + ts.complemento(subcadenas[k]) + " r: " + mayor + " ");
                }
            }
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
            System.out.println("cadena:" + cadena);
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
