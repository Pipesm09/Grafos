package grafos;

import javax.swing.JOptionPane;
import java.util.LinkedList;
import java.util.Queue;

public class Grafos {

    static Grafo miGrafo = null;

    public static void main(String[] args) {
        int opc = 0;
        do {
            opc = Menu();
            switch (opc) {
                case 1:
                    IngresarDatosGrafo();
                    break;
                case 2:
                    if (miGrafo != null) {
                        miGrafo.mostrarListaAdyacencia();
                    } else {
                        JOptionPane.showMessageDialog(null, "Cargue el grafo en la opción 1.");
                    }
                    break;
                case 3:
                    if (miGrafo != null) {
                        miGrafo.determinarTipoGrado();
                    } else {
                        JOptionPane.showMessageDialog(null, "Cargue el grafo en la opción 1.");
                    }
                    break;
                case 4:
                    if (miGrafo != null) {
                        miGrafo.matrizIncidencia();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Primero debe ingresar un grafo (Opción 1).");
                    }
                case 5:
                    String input = JOptionPane.showInputDialog("¿Desde que vertice quiere iniciar el recorrido DFS?");
                    if (input != null && !input.isEmpty()) {
                        char inicio = input.charAt(0);
                        miGrafo.alistarDFS(inicio);
                    }
                    break;
                case 6: // INSERTAR VERTICE

                    if (miGrafo == null) {
                        JOptionPane.showMessageDialog(null, "Primero debe crear un grafo.");
                        break;
                    }

                    String nuevoVertice = JOptionPane.showInputDialog(
                            "Ingrese el vértice a insertar:");

                    if (nuevoVertice != null && !nuevoVertice.trim().isEmpty()) {

                        char vertice = nuevoVertice.trim().charAt(0);

                        miGrafo.insertarVertice(vertice);

                        JOptionPane.showMessageDialog(null,
                                "Vértice " + vertice + " insertado.");
                    }
                    break;

                case 7: // ELIMINAR VERTICE

                    if (miGrafo == null) {
                        JOptionPane.showMessageDialog(null, "Primero debe crear un grafo.");
                        break;
                    }

                    String verticeEliminar = JOptionPane.showInputDialog("Ingrese el vértice a eliminar:");

                    if (verticeEliminar != null && !verticeEliminar.trim().isEmpty()) {

                        char vertice = verticeEliminar.trim().charAt(0);

                        if (miGrafo.eliminarVertice(vertice)) {

                            JOptionPane.showMessageDialog(null,
                                    "Vértice " + vertice + " eliminado.");

                        } else {

                            JOptionPane.showMessageDialog(null,
                                    "El vértice " + vertice + " no existe.");
                        }
                    }
                    break;
                case 8: // INSERTAR ARISTA

                    if (miGrafo == null) {
                        JOptionPane.showMessageDialog(null, "Primero debe crear un grafo.");
                        break;
                    }

                    String origenInsertar = JOptionPane.showInputDialog(
                            "Ingrese el vértice ORIGEN:");

                    String destinoInsertar = JOptionPane.showInputDialog(
                            "Ingrese el vértice DESTINO:");

                    if (origenInsertar != null && destinoInsertar != null
                            && !origenInsertar.trim().isEmpty()
                            && !destinoInsertar.trim().isEmpty()) {

                        char origen = origenInsertar.trim().charAt(0);
                        char destino = destinoInsertar.trim().charAt(0);

                        miGrafo.insertarArista(origen, destino);

                        JOptionPane.showMessageDialog(null,
                                "Arista " + origen + " → " + destino + " insertada.");
                    }
                    break;

                case 9: // ELIMINAR ARISTA

                    if (miGrafo == null) {
                        JOptionPane.showMessageDialog(null, "Primero debe crear un grafo.");
                        break;
                    }

                    String origenEliminar = JOptionPane.showInputDialog(
                            "Ingrese el vértice ORIGEN:");

                    String destinoEliminar = JOptionPane.showInputDialog(
                            "Ingrese el vértice DESTINO:");

                    if (origenEliminar != null && destinoEliminar != null
                            && !origenEliminar.trim().isEmpty()
                            && !destinoEliminar.trim().isEmpty()) {

                        char origen = origenEliminar.trim().charAt(0);
                        char destino = destinoEliminar.trim().charAt(0);

                        miGrafo.eliminarArista(origen, destino);

                        JOptionPane.showMessageDialog(null,
                                "Arista " + origen + " → " + destino + " eliminada.");
                    }
                    break;
                case 10:

                    if (miGrafo == null) {
                        JOptionPane.showMessageDialog(null,
                                "Primero debe crear un grafo.");
                        break;
                    }

                    String inicio = JOptionPane.showInputDialog(
                            "Ingrese el vértice inicial para BFS:");

                    if (inicio != null && !inicio.trim().isEmpty()) {

                        char verticeInicio = inicio.trim().charAt(0);

                        miGrafo.bfs(verticeInicio);
                    }

                    break;
                case 11:
                    if (miGrafo != null) {
                        miGrafo.mostrarMatrizAdyacencia();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Primero debe ingresar un grafo (Opción 1).");
                    }
                    break;
                case 12:
                    if (miGrafo == null) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Primero debe crear un grafo.");

                        break;
                    }

                    VentanaGrafo ventana = new VentanaGrafo(miGrafo);

                    ventana.setVisible(true);
                    opc = 0;
                    break;
                case 13:
                    if (miGrafo == null) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Primero debe crear un grafo.");

                        break;
                    }

                    String origen = JOptionPane.showInputDialog(
                            "Vértice origen:");

                    String destino = JOptionPane.showInputDialog(
                            "Vértice destino:");

                    if (origen != null && destino != null
                            && !origen.isEmpty()
                            && !destino.isEmpty()) {

                        miGrafo.caminoMasCorto(
                                origen.charAt(0),
                                destino.charAt(0));
                    }
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
            }
        } while (opc != 0);
    }

    public static int Menu() {
        String input = JOptionPane.showInputDialog(null,
                "****** Menu Principal de Grafos ******\n"
                + "1. Ingresar Datos en formato de línea\n"
                + "2. Mostrar Lista de Adyacencia\n"
                + "3. Determinar Tipo (Dirigido o No Dirigido)\n"
                + "4. Mostrar Matriz de Incidencia\n"
                + "5. Recorrido DFS\n"
                + "6. Insertar Vértice\n"
                + "7. Eliminar Vértice\n"
                + "8. Insertar Arista\n"
                + "9. Eliminar Arista\n"
                + "10. Recorrido BFS \n "
                + "11. Mostrar Matriz de Adyacencia\n"
                + "12. Visualizar el grafo. \n"
                + "13. Camino mas corto. \n"
                + "0. Salir\n\n"
                + "Ingrese una opción:");

        if (input == null) {
            return 0;
        }

        return Integer.parseInt(input);
    }

    //parseo con el split.
    public static void IngresarDatosGrafo() {
        String verticesInput = JOptionPane.showInputDialog(
                "Ingrese los vértices separados por comas");

        if (verticesInput == null || verticesInput.trim().isEmpty()) {
            return;
        }

        String[] partesVertices = verticesInput.split(",");

        miGrafo = new Grafo(partesVertices.length);

        for (int i = 0; i < partesVertices.length; i++) {
            // Tomamos la primera letra del string limpio (con trim() quitamos espacios accidentales)
            char letra = partesVertices[i].trim().charAt(0);
            miGrafo.insertarVertice(letra);
        }
        //leo las aristas

        String aristasInput = JOptionPane.showInputDialog(
                "Ingrese las conexiones separadas por guiones");

        if (aristasInput != null && !aristasInput.trim().isEmpty()) {
            String[] carreteras = aristasInput.split("-");

            for (int i = 0; i < carreteras.length; i++) {
                String[] origenYdestino = carreteras[i].split(",");

                if (origenYdestino.length == 2) {

                    char origen = origenYdestino[0].trim().charAt(0);
                    char destino = origenYdestino[1].trim().charAt(0);

                    int pesoExistente
                            = miGrafo.obtenerPeso(destino, origen);

                    if (pesoExistente != -1) {

                        miGrafo.insertarArista(
                                origen,
                                destino,
                                pesoExistente
                        );

                    } else {

                        miGrafo.insertarArista(origen, destino);
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Grafo cargado y parseado exitosamente.");
    }
}
