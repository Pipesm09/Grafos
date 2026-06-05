/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package grafos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Ventana corregida para renderizar el grafo de forma limpia.
 *
 * @author ASUS
 */
public class VentanaGrafo extends javax.swing.JFrame {

    private Grafo grafo;
    private final int RADIO_NODO = 20; // Radio del círculo del vértice (diámetro 40 / 2)

    public VentanaGrafo() {
        initComponents();
    }

    public VentanaGrafo(Grafo grafo) {
        initComponents();
        this.grafo = grafo;
        setSize(950, 750);
        setLocationRelativeTo(null);
    }

    // Método optimizado para dibujar la punta de la flecha
    private void dibujarFlecha(Graphics2D g2, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double angulo = Math.atan2(dy, dx);
        int tamFlecha = 12;

        int xA = (int) (x2 - tamFlecha * Math.cos(angulo - Math.PI / 6));
        int yA = (int) (y2 - tamFlecha * Math.sin(angulo - Math.PI / 6));

        int xB = (int) (x2 - tamFlecha * Math.cos(angulo + Math.PI / 6));
        int yB = (int) (y2 - tamFlecha * Math.sin(angulo + Math.PI / 6));

        g2.drawLine(x2, y2, xA, yA);
        g2.drawLine(x2, y2, xB, yB);
    }

    @Override
    public void paint(Graphics g) {
        // Usamos Graphics2D para activar el suavizado de bordes (Antialiasing)
        // Esto hace que las líneas y círculos no se vean "pixelados" o cortados.
        Graphics2D g2 = (Graphics2D) g;
        super.paint(g2);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (grafo == null) {
            return;
        }

        char[] vertices = grafo.getVertices();
        Nodo[] vec = grafo.getVec();
        int n = grafo.getCantVertices();

        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int radioCalculado = 240; // Ajustamos un poco el radio para dar más espacio

        int[] posX = new int[n];
        int[] posY = new int[n];

        // 1. Calcular las coordenadas centrales de cada nodo
        for (int i = 0; i < n; i++) {
            double angulo = 2 * Math.PI * i / n;
            posX[i] = centroX + (int) (radioCalculado * Math.cos(angulo));
            posY[i] = centroY + (int) (radioCalculado * Math.sin(angulo));
        }

        // Saber si es dirigido
        boolean dirigido = grafo.determinarTipoGrado();

        // 2. Dibujar las conexiones (Aristas y Pesos)
        for (int i = 0; i < n; i++) {
            Nodo p = vec[i];

            while (p != null) {
                int j = grafo.buscarIndice(p.getDestino());

                if (j != -1) {
                    // CASO A: Bucle (Auto-lazo)
                    if (i == j) {
                        g2.setColor(Color.BLUE);
                        // Dibujamos un bucle elegante en la parte exterior del nodo
                        g2.drawOval(posX[i] + 15, posY[i] - 25, 30, 30);

                        g2.setColor(Color.RED);
                        g2.setFont(new Font("Arial", Font.BOLD, 12));
                        g2.drawString(String.valueOf(p.getPeso()), posX[i] + 28, posY[i] - 30);
                    } // CASO B: Aristas entre nodos diferentes
                    else {
                        // El centro geométrico de los óvalos de dibujo (que miden 40x40)
                        int x1 = posX[i] + 20;
                        int y1 = posY[i] + 20;
                        int x2 = posX[j] + 20;
                        int y2 = posY[j] + 20;

                        double dx = x2 - x1;
                        double dy = y2 - y1;
                        double distancia = Math.sqrt(dx * dx + dy * dy);

                        // Vector unitario de dirección
                        double ux = dx / distancia;
                        double uy = dy / distancia;

                        // Vector perpendicular para desplazar las líneas paralelas en doble vía
                        double px = -uy;
                        double py = ux;

                        // Desplazamiento lateral (offset) para que la ida y la vuelta no se encimen
                        int offset = 10;

                        // Aplicamos el desplazamiento a los puntos de inicio y fin de la línea
                        int inicioX = (int) (x1 + (ux * RADIO_NODO) + (px * offset));
                        int inicioY = (int) (y1 + (uy * RADIO_NODO) + (py * offset));

                        int finX = (int) (x2 - (ux * RADIO_NODO) + (px * offset));
                        int finY = (int) (y2 - (uy * RADIO_NODO) + (py * offset));

                        // Dibujar la línea de la arista
                        g2.setColor(Color.DARK_GRAY);
                        g2.drawLine(inicioX, inicioY, finX, finY);

                        // Dibujar la flecha si el grafo es dirigido
                        if (dirigido) {
                            dibujarFlecha(g2, inicioX, inicioY, finX, finY);
                        }

                        // Calcular la posición del peso en el centro de la línea desplazada
                        int pesoX = (inicioX + finX) / 2;
                        int pesoY = (inicioY + finY) / 2;

                        // Desplazar el indicador del peso un poco más hacia afuera para no tocar la línea
                        pesoX += (int) (px * 12);
                        pesoY += (int) (py * 12);

                        // Dibujar un pequeño fondo blanco para el peso para que la línea no lo cruce visualmente
                        g2.setColor(Color.WHITE);
                        g2.fillOval(pesoX - 10, pesoY - 10, 20, 20);

                        g2.setColor(Color.RED);
                        g2.drawOval(pesoX - 10, pesoY - 10, 20, 20);

                        // Escribir el peso centrado dentro del círculo
                        g2.setColor(Color.BLACK);
                        g2.setFont(new Font("Arial", Font.BOLD, 11));

                        String pesoStr = String.valueOf(p.getPeso());
                        // Ajuste visual de centrado del texto dentro del óvalo según los caracteres
                        int ajusteX = (pesoStr.length() > 1) ? -7 : -4;
                        g2.drawString(pesoStr, pesoX + ajusteX, pesoY + 4);
                    }
                }
                p = p.getSiguiente();
            }
        }

        // 3. Dibujar los Vértices por encima de las conexiones
        for (int i = 0; i < n; i++) {
            // Fondo blanco para que las aristas que entran no crucen el texto del nodo
            g2.setColor(Color.WHITE);
            g2.fillOval(posX[i], posY[i], 40, 40);

            // Borde del nodo
            g2.setColor(Color.BLACK);
            g2.drawOval(posX[i], posY[i], 40, 40);

            // Nombre del nodo centrado
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString(
                    String.valueOf(vertices[i]),
                    posX[i] + 15,
                    posY[i] + 24
            );
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaGrafo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
