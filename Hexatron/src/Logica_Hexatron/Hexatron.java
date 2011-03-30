/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica_Hexatron;

import Funciones.Difusa;
import java.util.ArrayList;
import java.util.List;
import utils.LogPrinter;

/**
 *
 * @author Ztiphen
 */
public class Hexatron {

    private Celda matriz[][];
    private Celda matrizClone[][];
    private int ancho = 50;
    private int alto = 50;
    private int generation = 0;

    public Celda[][] getMatriz() {
        return matriz;
    }

    public Hexatron(boolean neutral) {
        matriz = new Celda[alto][ancho];
        poblar(neutral);

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

    public void poblar(boolean neutral) {

        matriz = new Celda[alto + 2][ancho + 2];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                boolean border = false;
                int tipo = 0;
                if (i == 0 || j == 0 || (i == matriz.length - 1) || (j == matriz[i].length - 1)) {
                    tipo = 3;
                    border = true;
                } else {
                    tipo = (int) (Math.random() * 4) + 1;
                }
                if (tipo == 1) {
                    matriz[i][j] = new Bacteria(1);
                } else if (tipo == 2) {
                    matriz[i][j] = new Bacteria(2);
                } else {
                    matriz[i][j] = border ? new Vacio(Constants.borderDefault) : new Vacio(neutral);
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
    public Celda getCellAtFrom(int direction, int i, int j, boolean clone) {
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
        ii = ii >= matriz.length ? 0 : ii;
        jj = jj >= matriz[0].length ? 0 : jj;
        if (clone) {
            return matrizClone[ii][jj];
        } else {
            return matriz[ii][jj];
        }
    }

    /**
     * Regresa la bacteria relativa a la actual en la direccion indicada por direction
     * @param direction
     * @param i
     * @param j
     * @return
     */
    public Celda getCellAtFromRound(int direction, int i, int j, boolean clone) {
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

        ii = ii > matriz.length - 2 ? 1 : ii;
        jj = jj > matriz[0].length - 2 ? 1 : jj;
        ii = ii == 0 ? matriz.length - 2 : ii;
        jj = jj == 0 ? matriz[0].length - 2 : jj;

        LogPrinter.printConsole("i:j/ni:nj=" + i + ":" + j + "/" + ii + ":" + jj + " dir:" + direction, 4);
        if (clone) {
            return matrizClone[ii][jj];
        } else {
            return matriz[ii][jj];
        }
    }

    public void nextGen() {
        matrizClone = matriz.clone();

        for (int i = 1; i < matriz.length - 1; i++) {
            for (int j = 1; j < matriz[i].length - 1; j++) {
                if (matrizClone[i][j] instanceof Bacteria) {
                    Bacteria bact = (Bacteria) matriz[i][j];
                    Difusa difusa = new Difusa();
                    double[] neighboors = neighboorsConcentration(bact, i, j);
                    int accion = difusa.accion(neighboors, bact.getConcentration());
//                    System.out.println("accion"+accion);
                    switch (accion) {
                        case 2: {
                            bact.girarBacteria(1);
                            break;
                        }
                        case 1: {
                            bact.girarBacteria(0);
                            break;
                        }
                        case 0: {
                            Celda c = getCellAtFromRound(bact.getDireccionCabeza(), i, j, false);
                            Bacteria bact2 = c instanceof Bacteria ? (Bacteria) c : null;
                            bact.conjugar(bact2);
                            if (bact2 == null) {
                                bact.girarBacteria();
                            }
                            break;
                        }
                        default:
                            bact.girarBacteria();

                    }
                    bact.modifyEnviroment(enviromentAverage(i, j));
                    bact.runTime();
                }
                concentrationDiffussion(i, j);
            }
        }
        generation++;
    }

    private double[] neighboorsConcentration(Bacteria bact, int i, int j) {
        double lcons = 0, fcons = 0, rcons = 0;
        Celda fcell = getCellAtFromRound(bact.getDireccionCabeza(), i, j, true);
        fcons = fcell instanceof Bacteria ? fcell.getConcentration() : Double.NaN;
        Celda lcell = getCellAtFromRound(bact.getDireccionCabeza() - 1, i, j, true);
        lcons = lcell instanceof Bacteria ? lcell.getConcentration() : Double.NaN;
        Celda rcell = getCellAtFromRound(bact.getDireccionCabeza() + 1, i, j, true);
        rcons = rcell instanceof Bacteria ? rcell.getConcentration() : Double.NaN;
        double[] neighboors = {fcons, rcons, lcons};
        return neighboors;
    }

    private void concentrationDiffussion(int i, int j) {
        float concentracion = matrizClone[i][j].getConcentration();
        float concentracionAcumulada = 0;
//        int ceroCells = 0;
//        int normalCells = 0;
//        int topCells = 0;
        for (int k = 1; k <= 6; k++) {
            float tempcons = getCellAtFromRound(k, i, j, true).getConcentration();

            concentracionAcumulada += tempcons;
//            if (tempcons == 0) {
//                ceroCells++;
//            }
//            if (tempcons > 0 && tempcons < Celda.concentrationMax) {
//                normalCells++;
//            }
//            if (tempcons == Celda.concentrationMax) {
//                topCells++;
//            }
        }
        float neighborConcentration = (concentracionAcumulada) / 6;
        if (concentracion >= neighborConcentration + 15) {//TODO idea of sand pile, change limit for droping
            float nueva = (neighborConcentration + concentracion) / 2;
            matriz[i][j].setConcentration(nueva);
            float cons = concentracion - nueva;
            neighboorsChangeCons(cons / 6, i, j);
        }
        if (concentracion <= neighborConcentration - 15) {//TODO idea of sand pile, change limit for droping
            float nueva = (neighborConcentration + concentracion) / 2;
            matriz[i][j].setConcentration(nueva);
            float cons = concentracion - nueva;
            neighboorsChangeCons(cons / 6, i, j);
        }


    }
//Vieja concentracion difucion con reaccion
//    private void concentrationDiffussion(int i, int j) {
//        float concentracion = matrizClone[i][j].getConcentration();
//        float concentracionAcumulada = concentracion;
//        int ceroCells = 0;
//        int normalCells = 0;
//        int topCells = 0;
//        for (int k = 1; k <= 6; k++) {
//            float tempcons = getCellAtFromRound(k, i, j,true).getConcentration();
//
//            concentracionAcumulada += tempcons;
//            if (tempcons == 0) {
//                ceroCells++;
//            }
//            if (tempcons > 0 && tempcons < Celda.concentrationMax) {
//                normalCells++;
//            }
//            if (tempcons == Celda.concentrationMax) {
//                topCells++;
//            }
//        }
//         float nueva=concentracion;//(concentracionAcumulada / (normalCells + topCells + 1));
//       // float nueva = ceroCells == 6 ? 0 : (concentracionAcumulada / (normalCells + topCells + 1)) + 50;
////        if (concentracion == 0) {
////            LogPrinter.printConsole(concentracion + ":" + i + "|" + j + " n:" + normalCells + " top:" + topCells + " nueva:" + nueva, 4);
////        }
////
////        if (concentracion == Celda.concentrationMax) {//sick becomes healthy
////            nueva = 0;
////        }
////        if (concentracion == 0 && ((normalCells) < 1 && (topCells) < 2)) {
////            nueva = 0;
////        }
////
////        if (concentracion == 0) {
////            LogPrinter.printConsole("nuevaConcentracion:" + nueva, 4);
////        }
//        matriz[i][j].setConcentration(nueva);
//    }

    /**
     * @return the generation
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * @param generation the generation to set
     */
    public void setGeneration(int generation) {
        this.generation = generation;
    }

    private void bacteriaConcetrationDiffusion(int i, int j) {
        float concentracion = ((Bacteria) matriz[i][j]).getConcentracionBact();
//        float concentracionAcumulada=concentracion;
        int ceroCells = 0;
        int normalCells = 0;
        int topCells = 0;
        for (int k = 1; k <= 6; k++) {
            Celda c = getCellAtFromRound(k, i, j, true);
            if (c instanceof Bacteria) {
                Bacteria bact = (Bacteria) c;
                float tempcons = bact.getConcentracionBact();
//            concentracionAcumulada+=tempcons;
                if (tempcons == 0) {
                    ceroCells++;
                }
                if (tempcons > 0 && tempcons < Celda.concentrationMax - 20) {
                    normalCells++;
                }
                if (tempcons > Celda.concentrationMax - 20) {
                    topCells++;
                }
            }
        }
        if (concentracion > Celda.concentrationMax - 10) {
            ((Bacteria) matriz[i][j]).setConcentracionBact(0);
        }
        if (topCells > 5 && concentracion > Celda.concentrationMax - 20) {
            Bacteria bact = ((Bacteria) matriz[i][j]);
            bact.setConcentracionBact(bact.getConcentracionBact() + 1);
            //LogPrinter.printConsole("cat to cero" + topCells + " " + normalCells + " " + ceroCells + " :" + i + "|" + j, 4);
        }


    }

    private float enviromentAverage(int i, int j) {
        float concentracionAcumulada = 0;
//        int ceroCells = 0;
//        int normalCells = 0;
//        int topCells = 0;
        for (int k = 1; k <= 6; k++) {
            float tempcons = getCellAtFromRound(k, i, j, true).getConcentration();
            concentracionAcumulada += tempcons;
        }
        return concentracionAcumulada / 6;
    }

    private void neighboorsChangeCons(float cons, int i, int j) {
        for (int k = 1; k <= 6; k++) {
            Celda cell = getCellAtFromRound(k, i, j, true);
            cell.setConcentration(cell.getConcentration() + cons);
        }
    }

    private List findFree(int i, int j) {
        List<Integer> l = new ArrayList<Integer>();
        for (int k = 1; k <= 6; k++) {
            Celda cell = getCellAtFromRound(k, i, j, true);
            if (cell instanceof Vacio) {
                l.add(k);
            }
        }
        return l;
    }
}
