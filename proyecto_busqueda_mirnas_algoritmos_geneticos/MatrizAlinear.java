/* clase que contiene un número y sus posibles movimientos, con esta se forma la matriz de alineamiento*/

class MatrizAlinear
{
    int numero;   //Peso de la letra
    int [] mueve; //mueve[0]=movimiento diagonal;mueve[1]= movimiento vertical;mueve[2]=movimiento horizontal.Toman valores de 0 o 1 , donde 0 es no existe movimiento y 1 es que existe movimiento
    public MatrizAlinear()
    {
        numero=0;          //inicializamos el número en cero
        mueve =new int[3]; //creamos tres elementos del vector mueve
    }

    public void set_numero(int numero)
    {
        this.numero=numero; //Asigna a la variable numero el valor del parámetro de entrada
    }

    public int get_numero()
    {
        return numero; //regresa el valor de la variable numero cuando se llama a la función
    }

    public void set_mueve(int k, int numero)
    {
        this.mueve[k]=numero; //Asigna al indice 'k' del vector 'mueve' el parámetro de entrada 'numero'
    }

    public int get_mueve(int k)
    {
        return mueve[k]; //regresa el valor del índice 'k' del vector mueve
    }
}

