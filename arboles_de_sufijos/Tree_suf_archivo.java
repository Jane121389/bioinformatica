import java.io.*;
import java.util.*;
class Tree_suf_archivo
{
    Nodo_tree_s [] nodos;
    int coincidencias=1;
    public void construye_arbol(String nombre_archivo)
    {
        String      aum_c ="$";
        char        letra =' ';
        int         num =0, j=0, s=0;
        int         indice=4, i=0;
        Nodo_tree_s nodillo;
        String      cad_nodo    ="";
        String      novo_cad    ="";
        String      cadena_aux  ="";
        String      cadena      ="";
        boolean     entra       =false;
        int         lon         =0;
        int         numerillo   =0;
        boolean     quiza_enlace=false;
        FileReader  fr          = null;
        try {
            //Abrir el fichero indicado por el usuario
            fr = new FileReader(nombre_archivo);
            //Leer el primer carácter
            int caract = fr.read();         //leer el caracter
            //Se recorre el fichero hasta encontrar el carácter -1
            //   que marca el final del fichero
            while (caract != -1) {
                letra=(char)caract;     //letra de la cadena de ADN leída
                if (letra == 'A' || letra == 'C' || letra == 'G' || letra == 'T')
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

        nodos=new Nodo_tree_s[4 * cadena.length()];
        for (i=cadena.length() - 1; i >= 0; i--)
        {
            letra=cadena.charAt(i);
            aum_c=letra + aum_c;
            num  =convierte_num_letra(letra);
            s    =0;
            if (nodos[num] == null)
            {
                List<Integer> conexion=new ArrayList<Integer>();
                Nodo_tree_s   nodin   =new Nodo_tree_s(aum_c, conexion, aum_c.length());
                nodos[num]=nodin;
            }
            else
            {
                cad_nodo=nodos[num].etiqueta;
                j       =0;
                for (j=0; j < aum_c.length(); j++)
                {
                    quiza_enlace=false;
                    if (s == cad_nodo.length() && aum_c.length() - j > cad_nodo.length())
                        quiza_enlace=true;


                    if (s < cad_nodo.length() && cad_nodo.charAt(s) == aum_c.charAt(j))
                    {
                        novo_cad=novo_cad + aum_c.charAt(j);
                        s++;
                        quiza_enlace=false;
                    }
                    else
                    {
                        quiza_enlace=true;
                        if (s < cad_nodo.length() && cad_nodo.charAt(s) != aum_c.charAt(j) && cad_nodo.charAt(s) != '$')
                        {
                            quiza_enlace=false;
                            List<Integer> conexion5=new ArrayList<Integer>();
                            conexion5=nodos[num].conexion;
                            indice++;
                            nodos[num].etiqueta=cad_nodo.substring(0, s);
                            cadena_aux         =cad_nodo.substring(s);
                            Nodo_tree_s nodin4=new Nodo_tree_s(cadena_aux, conexion5, cadena_aux.length());
                            nodos[indice]=nodin4;
                            List<Integer> conexion4=new ArrayList<Integer>();
                            nodos[num].conexion=conexion4;
                            nodos[num].conexion.add(indice);
                            indice++;
                            nodos[num].conexion.add(indice);
                            cadena_aux=aum_c.substring(j);
                            List<Integer> conexion6=new ArrayList<Integer>();
                            Nodo_tree_s   nodin3   =new Nodo_tree_s(cadena_aux, conexion6, cadena_aux.length());
                            nodos[indice]=nodin3;
                            s            =cad_nodo.length();
                            j            =aum_c.length();
                        }
                    }
                    if (quiza_enlace == true)
                    {
                        entra=false;

                        if (nodos[num].conexion.isEmpty())
                        {
                            //System.out.println(" go null"+ nodos[num].tamaño+ " "+s);
                            if (nodos[num].tamaño >= s)
                            {
                                indice++;
                                nodos[num].conexion.add(indice);
                                cadena_aux=aum_c.substring(j);
                                List<Integer> conexion2=new ArrayList<Integer>();
                                Nodo_tree_s   nodin1   =new Nodo_tree_s(cadena_aux, conexion2, cadena_aux.length());
                                cadena_aux   =cad_nodo.substring(s);
                                nodos[indice]=nodin1;
                                indice++;
                                nodos[num].conexion.add(indice);
                                List<Integer> conexion3=new ArrayList<Integer>();
                                Nodo_tree_s   nodin2   =new Nodo_tree_s(cadena_aux, conexion3, cadena_aux.length());
                                nodos[indice]=nodin2;
                                if (s > 0)
                                    nodos[num].etiqueta=nodos[num].etiqueta.substring(0, s);
                                j=aum_c.length();
                            }
                        }
                        else
                        {
                            Iterator<Integer> it= nodos[num].conexion.iterator();
                            while (it.hasNext())
                            {
                                numerillo=it.next();
                                if (nodos[numerillo].etiqueta.charAt(0) == aum_c.charAt(j))
                                {
                                    cad_nodo=nodos[numerillo].etiqueta;
                                    num     =numerillo;
                                    s       =1;
                                    entra   =true;
                                }
                            }
                            if (entra == false)
                            {
                                indice++;
                                nodos[num].conexion.add(indice);
                                cadena_aux=aum_c.substring(j);
                                List<Integer> conexion4=new ArrayList<Integer>();
                                Nodo_tree_s   nodin3   =new Nodo_tree_s(cadena_aux, conexion4, cadena_aux.length());
                                nodos[indice]=nodin3;
                                s            =cad_nodo.length();
                                j            =aum_c.length();
                            }
                        }
                    }
                }
            }
        }
        /*for(int l=0;l<indice+1;l++)
            if(nodos[l]!=null)
                System.out.print(" "+nodos[l].etiqueta+ " l:"+ l+ " "+nodos[l].conexion+"\n");*/
    }

    public int busca_cadena(String subcadena)
    {
        coincidencias=1;
        int     i =0, num=0, s=1, numerillo=0;
        char    letra=' ';
        boolean salir=false, entra=false;
        letra=subcadena.charAt(0);
        num  =convierte_num_letra(letra);
        for (i=1; i < subcadena.length() && salir == false; i++)
        {
            letra=subcadena.charAt(i);
            if (s < nodos[num].etiqueta.length())
            {
                if (nodos[num].etiqueta.charAt(s) != letra)
                    salir=true;
                else
                    salir=false;
                s++;
            }
            else
            {
                Iterator<Integer> it= nodos[num].conexion.iterator();
                entra=false;
                while (it.hasNext())
                {
                    numerillo=it.next();
                    if (nodos[numerillo].etiqueta.charAt(0) == letra)
                    {
                        num  =numerillo;
                        s    =1;
                        entra=true;
                    }
                }
                if (entra == false)
                    salir=true;
            }
        }
        if (nodos[num].conexion.isEmpty())
            coincidencias=1;
        else
            coincidencias=coincidencia(num);
        if (salir == true)
            coincidencias=-2;
        return coincidencias;
    }

    public int coincidencia(int num)
    {
        int numerillo;
        if (nodos[num].conexion.isEmpty() == false)
        {
            coincidencias--;
            Iterator<Integer> it= nodos[num].conexion.iterator();
            while (it.hasNext())
            {
                numerillo=it.next();
                coincidencias++;
                //  System.out.println("numerillo "+numerillo+" coincidencias;"+coincidencias);
                coincidencia(numerillo);
            }
        }
        return coincidencias;
    }

    /* coloca un valor numerico a las letras  (0=A, 1=C, 2=G, 3=T) */
    public int convierte_num_letra(char letra)
    {
        int num=-1;
        switch (letra)
        {
            case 'A':    //la letra A ocupara la fila 0 de la matriz
                num=0;
                break;
            case 'C':    //la letra C ocupara la fila 1 de la matriz
                num=1;
                break;
            case 'G':    //la letra G ocupara la fila 2 de la matriz
                num=2;
                break;
            case 'T':    //la letra T ocupara la fila 3 de la matriz
                num=3;
                break;
        }
        return num;
    }

    public String complemento(String subcadena)
    {
        int    i    =0;
        String nueva="";
        char   letra=' ';
        for (i=0; i < subcadena.length(); i++)
        {
            letra=subcadena.charAt(i);
            switch (letra)
            {
                case 'A': //la letra A cambia por U
                    nueva='T' + nueva;
                    break;
                case 'C': //la letra C cambia por G
                    nueva='G' + nueva;
                    break;
                case 'G': //la letra G cambia por C
                    nueva='C' + nueva;
                    break;
                case 'U': //la letra T cambia por A
                    nueva='A' + nueva;
                    break;
            }
        }
        return nueva;
    }
}
