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

    private int[] vertices; //va a guardar los vertices
    private Nodo[] vec; //va a guardar los caminos de los vertices en filas
    private int cantVertices; //sirvira para insertar y dar el tamaño

    public Grafo(int maxVertices) {
        this.vertices = new int[maxVertices];
        this.vec = new Nodo[maxVertices]; //aqui se le da el tamaño a la lista de adyacencia por ser V*V
        this.cantVertices = 0;
    }

    //metodo insertar en grafos
    public void insertarVertice(int dato) {
        // se verifica cada vez si ya se lleno el arreglo de os vectores con su cantidad
        if (cantVertices < vertices.length) {
            vertices[cantVertices] = dato; //se inserta de 0 hasta el tamaña del arreglo, por eso cantVertices=0
            vec[cantVertices] = null; //esto para evitar errores, porque despues se utilizara :V
            cantVertices++; //se avanza sin necesidad de un ciclo, ya que cada vertice le corresponde solo un espacio
        } else {
            System.out.println("No se puede insertar mas vertices, cantidad maxima alcanzada XdXDxdxddddDX");
        }
    }

    //procedo a conectar los vertices papu :V, con el indice pero este se encuentra con este metodo
    public int buscarIndice(int dato /*el vertice*/) {
        for (int i = 0; i < cantVertices; i++) { //se busca con un for cada vertice en el arreglo vertices
            if (vertices[i] == dato) {
                return i; //se retornara el indice una vez que encuentre el vertice
            }
        }
        return -1; //sino lo encontro devuelve -1
    }

    //se va conectar por aristas los nodos papu :V
    //los parametros seran el vertice donde sale o el primero y su destino que sera en orden segun el usuario y se creara nodo por cada enlace
    public void insertarArista(int origen, int destino) {
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

    private class verticeExacto {

        int dato;

        public verticeExacto(int dato) {
            this.dato = dato;
        }
    }

    //metodos para poder anaalizar los gafros y ver las conexiones de los vertices (:(((((((((((()
    private boolean existeArista(verticeExacto origen, verticeExacto destino) {
        //este metodo verifica si hay camino directo entre vertices por ende necesito el indice del vertice o los vertices
        int indiceOrigen = buscarIndice(origen.dato);
        if (indiceOrigen == -1) {//o sea que no hay vertice
            return false;
        }
        //si, si hay vertice se recorre su fila hasta null para ver is hay camino
        Nodo p = vec[indiceOrigen];
        while (p != null) {
            if (p.getDestino() == destino.dato) {
                return true; //encontre el camino WWWW
            }
            p = p.getSiguiente();
        }
        return false; //Si salio del while y no encontro nada :(
    }

    //metodo para ver si el grafo es dirigido
    public void determinarTipoGrado() {
        if (cantVertices == 0) { //por si no hay vertices
            System.out.println("El grafo esta vacio");
            return;
        }
        //se debe de ir por cada vertice y por cada fila de este vertice para ver si existe camino de ida, debe de existir camino de regreso
        //si esto paso es NO DIRIGIDO, pero si hay ida, pero no regreso en tal solo un vertice es DIRIGIDO
        for (int i = 0; i < cantVertices; i++) {
            verticeExacto p = new verticeExacto(vertices[i]); // un p tipo vertice que servira para comparar el origen con el destino 
            Nodo aristaActual = vec[i]; //nodo para recorrer mmg
            while (aristaActual != null) {
                //se recorre la lista de adyacencia en la fial de ese vertices[i]
                verticeExacto destino = new verticeExacto(aristaActual.getDestino());
                //aqui se verifica si existe aristaActual va a destino, debe de exister el nodo destino vaya a la AristaActual o algo asi xdxdxd
                if (!existeArista(destino, p)) {
                    System.out.println("El grafo es DIRIGIDO");
                    return;
                }
                //si no se avanza
                aristaActual = aristaActual.getSiguiente();

            }
        }
        //si llego hasta aca suscribite :VVVV:VV::V:V:V.v.v.v.v.V::V
        //no mentiras, si llego hasta aca signidica que hubo tanto ida-regreso como regreso-id entonces es NO DIRIGIDO
        System.out.println("\"El grafo es NO DIRIGIDO.\"");
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
}
