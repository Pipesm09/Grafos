/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

/**
 *
 * @author andre
 */
public class Nodo {
    private Nodo siguiente;
    private char destino; // <-- CAMBIADO A char

    public Nodo(char destino) {
        this.destino = destino;
        this.siguiente = null;
    }

    public Nodo getSiguiente() { return siguiente; }
    public void setSiguiente(Nodo siguiente) { this.siguiente = siguiente; }
    public char getDestino() { return destino; } // <-- CAMBIADO A char
    public void setDestino(char destino) { this.destino = destino; } // <-- CAMBIADO A char
}

