/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica_Hexatron;

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import sun.reflect.generics.tree.Tree;
import utils.LogPrinter;

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

//    public void nextGen() {
//        for (int i = 0; i < matriz.length; i++) {
//            for (int j = 0; j < matriz[i].length; j++) {
//                if (matriz[i][j] instanceof Bacteria) {
//                    Bacteria bact = (Bacteria) matriz[i][j];
//                    bact.setDireccionCabeza((bact.getDireccionCabeza() + 1) % 6);
//                }
//            }
//        }
//    }
    /**
     * Regresa la bacteria relativa a la actual en la direccion indicada por direction
     * @param direction
     * @param i
     * @param j
     * @return
     */
    public Celda getCellAtFrom(int direction, int i, int j) {
        int ii = i;
        int jj = j;
        int tempj = j;
        switch (direction) {
            case 1:
            case 6:
                ii = i - 1;
                break;
            case 3:
            case 4:
                ii = i + 1;
                break;
            case 2:
                tempj++;
                break;
            case 5:
                tempj--;
                break;
        }

        if (i % 2 == 0) {
            jj = direction > 3 ? j - 1 : tempj;
        } else {
            jj = direction < 4 ? j + 1 : tempj;
        }
        LogPrinter.printConsole("i:j/ni:nj=" + i + ":" + j + "/" + ii + ":" + jj + " dir:" + direction, 4);
        
        return matriz[i][j];
    }

    public void nextGen() {


        for (int i = 1; i < matriz.length; i++) {
            for (int j = 1; j < matriz[i].length; j++) {
                if (matriz[i][j] instanceof Bacteria) {
                    Bacteria bact = (Bacteria) matriz[i][j];
                    float choice = (float) Math.random();

                    if (choice < Constants.conjugationProbability) {
                        Celda c=getCellAtFrom(bact.getDireccionCabeza(), i, j);
                        Bacteria bact2 = c instanceof Bacteria ? (Bacteria) c : null;
                        bact.conjugar(bact2);
                    } else if (choice < Constants.movementProbability) {                //Movimiento
                        bact.moverBacteria();
                    }
                    //else HACER NADA
                    bact.runTime();
                }
                concentrationDiffussion(i,j);
            }
        }
    }

    private void concentrationDiffussion(int i, int j) {
        float concentracionAcumulada=matriz[i][j].getConcentration();
        for(int k=0;k<6;k++){
            concentracionAcumulada+=getCellAtFrom(k, i, j).getConcentration();
        }
        float nueva=concentracionAcumulada/7+30;
        System.out.println("Nueva:"+nueva);
        if (nueva==0){
            nueva=100f;
        }
        if (nueva>Celda.concentrationMax){
            nueva=0;
        }
        System.out.println("Nueva:"+nueva);

        matriz[i][j].setConcentration(nueva);
    }

    

   
}
