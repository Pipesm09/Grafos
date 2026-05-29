package grafos;

import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class Grafos {

    // Instancia del Grafo global para que los métodos la puedan modificar
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
                        JOptionPane.showMessageDialog(null, "Error: Primero debe ingresar un grafo (Opción 1).");
                    }
                    break;
                case 3:
                    if (miGrafo != null) {
                        miGrafo.determinarTipoGrado();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Primero debe ingresar un grafo (Opción 1).");
                    }
                    break;
                case 0:
                    System.out.println("Saliendo del programa de Grafos...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción incorrecta. Intente de nuevo.");
            }

        } while (opc != 0);
    }

    // ==========================================
    // MENÚ PRINCIPAL CON JOPTIONPANE
    // ==========================================
    public static int Menu() {
        String input = JOptionPane.showInputDialog(null, 
                "****** Menu Principal de Grafos ******\n"
                + "1. Ingresar Datos del Grafo (Vértices y Aristas)\n"
                + "2. Mostrar Lista de Adyacencia (Ver en Consola)\n"
                + "3. Determinar Tipo de Grafo (Dirigido / No Dirigido)\n"
                + "0. Salir\n\n"
                + "Ingrese una opción:");
        
        // Evitamos error si el usuario cierra la ventana de diálogo (X o Cancelar)
        if (input == null) {
            return 0;
        }
        return Integer.parseInt(input);
    }

    // ==========================================
    // CAPTURA DE DATOS DINÁMICA
    // ==========================================
    public static void IngresarDatosGrafo() {
        // 1. Pedir el número total de vértices (para inicializar el vector con tamaño fijo)
        String inputV = JOptionPane.showInputDialog("¿Cuántos vértices tendrá el grafo?");
        if (inputV == null || inputV.isEmpty()) return;
        int numVertices = Integer.parseInt(inputV);

        // Inicializamos la instancia global con el tamaño definido
        miGrafo = new Grafo(numVertices);

        // 2. Leer los nombres de los vértices (valores numéricos como en tu tablero)
        for (int i = 0; i < numVertices; i++) {
            String verticeInput = JOptionPane.showInputDialog("Vértice " + (i + 1) + " de " + numVertices + "\nDigite el valor numérico:");
            if (verticeInput != null && !verticeInput.isEmpty()) {
                int valor = Integer.parseInt(verticeInput);
                miGrafo.insertarVertice(valor);
            }
        }

        // 3. Pedir el número de conexiones (aristas)
        String inputA = JOptionPane.showInputDialog("¿Cuántas conexiones (aristas) va a ingresar?");
        if (inputA != null && !inputA.isEmpty()) {
            int numAristas = Integer.parseInt(inputA);

            // Bucle para capturar origen y destino de cada carretera
            for (int i = 0; i < numAristas; i++) {
                String origenInput = JOptionPane.showInputDialog("Conexión " + (i + 1) + " de " + numAristas + "\nDigite el vértice de ORIGEN:");
                int origen = Integer.parseInt(origenInput);

                String destinoInput = JOptionPane.showInputDialog("Conexión " + (i + 1) + " de " + numAristas + "\nDigite el vértice de DESTINO:");
                int destino = Integer.parseInt(destinoInput);

                // Insertamos la arista tal como la escribe el usuario (una vía)
                miGrafo.insertarArista(origen, destino);
            }
        }

        JOptionPane.showMessageDialog(null, "Grafo cargado exitosamente. Puede verlo en la opción 2.");
    }
}
