package grafos;

import javax.swing.JOptionPane;

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
                    if (miGrafo != null) miGrafo.mostrarListaAdyacencia();
                    else JOptionPane.showMessageDialog(null, "Cargue el grafo en la opción 1.");
                    break;
                case 3:
                    if (miGrafo != null) miGrafo.determinarTipoGrado();
                    else JOptionPane.showMessageDialog(null, "Cargue el grafo en la opción 1.");
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
                + "3. Determinar Tipo (Dirigido/No Dirigido)\n"
                + "0. Salir\n\n"
                + "Ingrese una opción:");
        if (input == null) return 0;
        return Integer.parseInt(input);
    }

    // ===================================================
    // AQUÍ OCURRE EL PARSEO CON .split()
    // ===================================================
    public static void IngresarDatosGrafo() {
        // Paso 1: Leer todos los vértices en una sola línea (Ej: A,B,C,D)
        String verticesInput = JOptionPane.showInputDialog(
                "Ingrese los vértices separados por comas");
        
        if (verticesInput == null || verticesInput.trim().isEmpty()) return;

        // Rompemos la cadena usando la coma como separador
        String[] partesVertices = verticesInput.split(",");
        
        // Inicializamos el grafo con la cantidad exacta de partes que salieron
        miGrafo = new Grafo(partesVertices.length);

        // Insertamos cada letra en el grafo
        for (int i = 0; i < partesVertices.length; i++) {
            // Tomamos la primera letra del string limpio (con trim() quitamos espacios accidentales)
            char letra = partesVertices[i].trim().charAt(0);
            miGrafo.insertarVertice(letra);
        }

        // Paso 2: Leer todas las aristas en una sola línea (Ej: A,B-B,C-A,C)
        String aristasInput = JOptionPane.showInputDialog(
                "Ingrese las conexiones separadas por guiones");

        if (aristasInput != null && !aristasInput.trim().isEmpty()) {
            // Rompemos primero por el guion (-) para separar cada "carretera"
            String[] carreteras = aristasInput.split("-");

            for (int i = 0; i < carreteras.length; i++) {
                // Cada "carretera" es algo como "A,B". La rompemos por la coma (,)
                String[] origenYdestino = carreteras[i].split(",");
                
                if (origenYdestino.length == 2) {
                    char origen = origenYdestino[0].trim().charAt(0);
                    char destino = origenYdestino[1].trim().charAt(0);
                    
                    // Insertamos en el grafo
                    miGrafo.insertarArista(origen, destino);
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Grafo cargado y parseado exitosamente.");
    }
}
