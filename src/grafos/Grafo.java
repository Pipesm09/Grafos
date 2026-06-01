/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

/**
 *
 * @author andre
 */
public class Grafo {

    private char[] vertices; //va a guardar los vertices
    private Nodo[] vec; //va a guardar los caminos de los vertices en filas
    private int cantVertices; //sirvira para insertar y dar el tamaño

    public Grafo(int maxVertices) {
        this.vertices = new char[maxVertices];
        this.vec = new Nodo[maxVertices]; //aqui se le da el tamaño a la lista de adyacencia por ser V*V
        this.cantVertices = 0;
    }

    //metodo insertar en grafos
    public void insertarVertice(int dato) {
        // se verifica cada vez si ya se lleno el arreglo de os vectores con su cantidad
        if (cantVertices < vertices.length) {
            vertices[cantVertices] = (char) dato; //se inserta de 0 hasta el tamaña del arreglo, por eso cantVertices=0
            vec[cantVertices] = null; //esto para evitar errores, porque despues se utilizara :V
            cantVertices++; //se avanza sin necesidad de un ciclo, ya que cada vertice le corresponde solo un espacio
        } else {
            System.out.println("No se puede insertar mas vertices, cantidad maxima alcanzada XdXDxdxddddDX");
        }
    }

    //procedo a conectar los vertices papu :V, con el indice pero este se encuentra con este metodo
    public int buscarIndice(char dato /*el vertice*/) {
        for (int i = 0; i < cantVertices; i++) { //se busca con un for cada vertice en el arreglo vertices
            if (vertices[i] == dato) {
                return i; //se retornara el indice una vez que encuentre el vertice
            }
        }
        return -1; //sino lo encontro devuelve -1
    }

    //se va conectar por aristas los nodos papu :V
    //los parametros seran el vertice donde sale o el primero y su destino que sera en orden segun el usuario y se creara nodo por cada enlace
    public void insertarArista(char origen, char destino) {
        int indiceOrigen = buscarIndice(origen);
        int indiceDestino = buscarIndice(destino);
        //se verifica entonces si estos vertices o indices estan en el grafo
        if (indiceOrigen == -1 || indiceDestino == -1) {
            System.out.println("Uno o ambos vertices NO existen en el grafo");
            return;
        }
        //si, si estan entonces crear lista de adyacencia con lista de nodos
        Nodo nuevo = new Nodo(destino);
        //el nuevo nodo apunta a destino
        //ahora si es la primera conexion, o sea que la lista de nodos=null en la posicion 0
        if (vec[indiceOrigen] == null) {
            //quiere decir que en la posicion del origen no hay ningun nodo, por ende el nodo destino tomara esa posicion
            vec[indiceOrigen] = nuevo;
        } else {
            //ya hay camino
            Nodo p = vec[indiceOrigen]; //p se para en el que tiene nodo, ya sea el primero, seguno, tercero, etc..... xxdxdXDXDXdxdxd
            while (p.getSiguiente() != null) {
                if (p.getDestino() == destino)//quiere decir que si en la posicion origen se quiere insertar el mismo nodo, no se hace
                {
                    return;
                }
                p = p.getSiguiente();
            }
            if (p.getDestino() != destino) { //si no encontro que sea el mismo, va a insertarlo antes del null
                p.setSiguiente(nuevo);
            }
        }
    }

    //metodos para poder anaalizar los gafros y ver las conexiones de los vertices (:(((((((((((()
    private boolean existeArista(char origen, char destino) {
        //este metodo verifica si hay camino directo entre vertices por ende necesito el indice del vertice o los vertices
        int indiceOrigen = buscarIndice(origen);
        if (indiceOrigen == -1) {//o sea que no hay vertice
            return false;
        }
        //si, si hay vertice se recorre su fila hasta null para ver is hay camino
        Nodo p = vec[indiceOrigen];
        while (p != null) {
            if (p.getDestino() == destino) {
                return true; //encontre el camino WWWW
            }
            p = p.getSiguiente();
        }
        return false; //Si salio del while y no encontro nada :(
    }

    //metodo para ver si el grafo es dirigido
    public boolean determinarTipoGrado() {
        if (cantVertices == 0) { //por si no hay vertices
            System.out.println("El grafo esta vacio");
            return false;
        }
        //se debe de ir por cada vertice y por cada fila de este vertice para ver si existe camino de ida, debe de existir camino de regreso
        //si esto paso es NO DIRIGIDO, pero si hay ida, pero no regreso en tal solo un vertice es DIRIGIDO
        for (int i = 0; i < cantVertices; i++) {
            char p = vertices[i]; // un p tipo vertice que servira para comparar el origen con el destino 
            Nodo aristaActual = vec[i]; //nodo para recorrer mmg
            while (aristaActual != null) {
                //se recorre la lista de adyacencia en la fial de ese vertices[i]
                char destino = aristaActual.getDestino(); //vertice al cual se quiere ir
                //aqui se verifica si existe aristaActual va a destino, debe de exister el nodo destino vaya a la AristaActual o algo asi xdxdxd
                if (!existeArista(destino, p)) {
                    System.out.println("El grafo es DIRIGIDO");
                    return true;
                }
                //si no se avanza
                aristaActual = aristaActual.getSiguiente();
            }
        }
        //si llego hasta aca suscribete :VVVV:VV::V:V:V.v.v.v.v.V::V
        //no mentiras, si llego hasta aca signidica que hubo tanto ida-regreso como regreso-id entonces es NO DIRIGIDO
        System.out.println("\"El grafo es NO DIRIGIDO.\"");
        return false;
    }

    //mostrar lista de adyacencia
    public void mostrarListaAdyacencia() {
        System.out.println("\n===== LISTA DE ADYACENCIA DEL GRAFO =====");

        //basicamente se para en el arreglo del vertice y luego recorre la lsita de nodos por fila de cada vertice
        for (int i = 0; i < cantVertices; i++) {
            System.out.print("Fila [" + i + "] (Vértice " + vertices[i] + ") ──► ");
            Nodo p = vec[i];
            if (p == null) {
                System.out.println("//");//para saber que es null y se termino la fila
            } else {
                while (p != null) {
                    System.out.print(p.getDestino() + " ──► ");
                    p = p.getSiguiente();
                }
                System.out.print("/"); //final de la lista
            }
            System.out.println();
        }
        System.out.println("=========================================\n");
    }

    //matriz de incidencia V*A
    public int contarAristas() {
        int total = 0;
        //la lista se recorre con un for y un while dentro
        //porque el for se para en el arreglo de los vertices
        for (int i = 0; i < cantVertices; i++) {
            Nodo p = vec[i]; //lista de nodos
            while (p != null) {
                total++;
                p = p.getSiguiente(); //avanza contando cada camino=arista
            }
        }
        return total;
    }

    public void matrizIncidencia() {
        int totalAristas = contarAristas();
        //se valida si no hay aristas o vertices
        if (cantVertices == 0 || totalAristas == 0) {
            System.out.println("El grafo NO tiene aristas o vertices suficientes");
            return;
        }
        //creacion de matriz
        int[][] Matriz = new int[cantVertices][totalAristas];
        int columnaActual = 0;
        //esto porque se inserta mas sencillo en columna a columna que fila a fila, porque puede suceder que dos nodos compartan ida y regreso
        for (int i = 0; i < cantVertices; i++) {
            //por la manera en que se recorre necesito vertice destino, origen y su camino (si es el primero, segundo o tercero)
            //esto para ver si se repetien o no, para poner 1, 2 o 3
            Nodo p = vec[i];
            while (p != null) {
                int j = buscarIndice(p.getDestino());
                if (i == j) {
                    Matriz[i][columnaActual] = 3;//bucle
                } else {
                    //o sea que o uno va (i) y el otro regresa o le entre :v (j)
                    Matriz[i][columnaActual] = 1;
                    Matriz[j][columnaActual] = 2;
                }
                columnaActual++; //se avanza a la siguente arista
                p = p.getSiguiente(); // avanza el camino
            }
        }
        System.out.println("\n===== MATRIZ DE INCIDENCIA =====");

        // Imprimir aristas de columnas (E0, E1, E2...)
        System.out.print("      ");
        for (int col = 0; col < totalAristas; col++) {
            System.out.print("E" + col + "  ");
        }
        System.out.println();

        // Imprimir las filas con su respectiva información
        for (int fila = 0; fila < cantVertices; fila++) {
            System.out.print("[" + vertices[fila] + "]   ");
            for (int col = 0; col < totalAristas; col++) {
                System.out.print(Matriz[fila][col] + "   ");
            }
            System.out.println();
        }
        System.out.println("=================================\n");
    }

    //DFS (como el dragon ball xdxXDDXxDxdXd
    //este funciona como el metodo que recorre el grafo sin mirar mas opciones que la mas corta y sin repetir caminos
    //aca hago un metodo para no tener que meter todo en el main
    public void alistarDFS(char verticeInicial) {
        int indiceInicio = buscarIndice(verticeInicial);
        if (indiceInicio == -1) {
            System.out.println("El vertice de inicio NO EXISTE");
        }
        //se crea el vector visitado que es lo mas CLAVE con tamaña total de el total de vertices
        int[] visitado = new int[cantVertices];
        System.out.print("Recorrido DFS desde '" + verticeInicial + "': ");
        //llamado recursivo que es el que recorre
        dfsRecursivo(indiceInicio, visitado);
    }

    private void dfsRecursivo(int v, int[] visitado) {
        visitado[v] = 1; // se marca de una al vertice que me mandaron o el del incio
        System.out.print(vertices[v] + " ");
        Nodo p=vec[v];
        //se recorre la lista de nodos buscando que haya mas camino, o sea W==0 para que se siga contando el camino, sino se cambia
        while (p!=null){
            char datoVecinoEnLaFila = p.getDestino(); //solo sirve para que me de el indice del siguiente dato
            int w= buscarIndice(datoVecinoEnLaFila); //una vez con el indice se verifica si ya pasamos por este
            if(visitado[w]==0){
                //si no hemos pasado por aca se vuelve hallar recursivamente para poner el 1
                dfsRecursivo (w, visitado);
            }
            p=p.getSiguiente(); //en el caso que ya pasamos por este dato simplemente avanza
            
        }
    }

}
