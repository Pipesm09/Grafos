/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package grafos;

import java.awt.Graphics;

/**
 *
 * @author ASUS
 */
public class VentanaGrafo extends javax.swing.JFrame {

    /**
     * Creates new form VentanaGrafo
     */
    private Grafo grafo;

    public VentanaGrafo() {
        initComponents();
    }

    public VentanaGrafo(Grafo grafo) {
        initComponents();
        this.grafo = grafo;
        setSize(900, 700);      // tamaño más grande
        setLocationRelativeTo(null); // centra la ventana
    }

    private void dibujarFlecha(Graphics g,
            int x1, int y1,
            int x2, int y2) {

        g.drawLine(x1, y1, x2, y2);

        double dx = x2 - x1;
        double dy = y2 - y1;

        double angulo = Math.atan2(dy, dx);

        int tamFlecha = 12;

        int xA = (int) (x2 - tamFlecha * Math.cos(angulo - Math.PI / 6));
        int yA = (int) (y2 - tamFlecha * Math.sin(angulo - Math.PI / 6));

        int xB = (int) (x2 - tamFlecha * Math.cos(angulo + Math.PI / 6));
        int yB = (int) (y2 - tamFlecha * Math.sin(angulo + Math.PI / 6));

        g.drawLine(x2, y2, xA, yA);
        g.drawLine(x2, y2, xB, yB);
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        if (grafo == null) {
            return;
        }

        char[] vertices = grafo.getVertices();
        Nodo[] vec = grafo.getVec();

        int n = grafo.getCantVertices();

        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int radio = 220;

        int[] posX = new int[n];
        int[] posY = new int[n];

        // Calcular posiciones
        for (int i = 0; i < n; i++) {

            double angulo = 2 * Math.PI * i / n;

            posX[i] = centroX + (int) (radio * Math.cos(angulo));
            posY[i] = centroY + (int) (radio * Math.sin(angulo));
        }

        // Saber si es dirigido
        boolean dirigido = grafo.determinarTipoGrado();

        // Dibujar aristas
        for (int i = 0; i < n; i++) {

            Nodo p = vec[i];

            while (p != null) {

                int j = grafo.buscarIndice(p.getDestino());

                if (j != -1) {

                    // ==========================
                    // CASO 1: BUCLE
                    // ==========================
                    if (i == j) {

                        g.drawOval(
                                posX[i] + 25,
                                posY[i] - 20,
                                30,
                                30
                        );

                        g.drawString(
                                String.valueOf(p.getPeso()),
                                posX[i] + 35,
                                posY[i] - 25
                        );
                    } // ==========================
                    // CASO 2: ARISTA NORMAL
                    // ==========================
                    else {

                        int x1 = posX[i] + 20;
                        int y1 = posY[i] + 20;

                        int x2 = posX[j] + 20;
                        int y2 = posY[j] + 20;

                        double dx = x2 - x1;
                        double dy = y2 - y1;

                        double distancia = Math.sqrt(dx * dx + dy * dy);

                        int radioNodo = 20;

                        int inicioX = (int) (x1 + dx * radioNodo / distancia);
                        int inicioY = (int) (y1 + dy * radioNodo / distancia);

                        int finX = (int) (x2 - dx * radioNodo / distancia);
                        int finY = (int) (y2 - dy * radioNodo / distancia);

                        g.drawLine(
                                inicioX,
                                inicioY,
                                finX,
                                finY
                        );

                        // ==========================
                        // PESO
                        // ==========================
                        int pesoX = (inicioX + finX) / 2;
                        int pesoY = (inicioY + finY) / 2;

                        pesoX += (int) (-dy / distancia * 15);
                        pesoY += (int) (dx / distancia * 15);

                        g.drawOval(
                                pesoX - 10,
                                pesoY - 10,
                                20,
                                20
                        );

                        g.drawString(
                                String.valueOf(p.getPeso()),
                                pesoX - 4,
                                pesoY + 5
                        );

                        // ==========================
                        // FLECHAS
                        // ==========================
                        dirigido = grafo.determinarTipoGrado();

                        if (dirigido) {

                            dibujarFlecha(
                                    g,
                                    inicioX,
                                    inicioY,
                                    finX,
                                    finY
                            );
                        }
                    }
                }

                p = p.getSiguiente();
            }
        }

        // Dibujar vértices
        for (int i = 0; i < n; i++) {

            g.drawOval(posX[i], posY[i], 40, 40);

            g.drawString(
                    String.valueOf(vertices[i]),
                    posX[i] + 15,
                    posY[i] + 25
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
