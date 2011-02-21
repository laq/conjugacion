/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica_Hexatron;

/**
 *
 * @author Ztiphen
 */
public class Hexatron {

    private Celda matriz[][];
    private int ancho = 50;
    private int alto = 50;

    public Celda[][] getMatriz() {
        return matriz;
    }

    public Hexatron() {
        matriz = new Celda[alto][ancho];
        poblar();

    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @param ancho the ancho to set
     */
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    /**
     * @return the alto
     */
    public int getAlto() {
        return alto;
    }

    /**
     * @param alto the alto to set
     */
    public void setAlto(int alto) {
        this.alto = alto;
    }

    public void poblar() {
        matriz = new Celda[alto + 2][ancho + 2];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                int tipo = 0;
                if (i == 0 || j == 0 || (i == matriz.length - 1) || (j == matriz[i].length - 1)) {
                    tipo = 3;
                } else {
                    tipo = (int) (Math.random() * 3) + 1;
                }
                if (tipo == 1) {
                    matriz[i][j] = new Bacteria(1);
                } else if (tipo == 2) {
                    matriz[i][j] = new Bacteria(2);
                } else {
                    matriz[i][j] = new Vacio();
                }
            }
        }
    }

    public void nextGen() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] instanceof Bacteria) {
                    Bacteria bact = (Bacteria) matriz[i][j];
                    bact.setDireccionCabeza((bact.getDireccionCabeza() + 1) % 6);
                }
            }
        }
    }
}
