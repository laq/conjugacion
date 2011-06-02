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

    private Cell matriz[][];
    private Cell matrizClone[][];
    private int width = 50;
    private int height = 50;
    private int generation = 0;
    private int donadoras=0;
    private int receptoras=0;
    private int antibiotico=0;
    private int antibioticType=1;

    public Cell[][] getMatriz() {
        return matriz;
    }

    public Hexatron(boolean neutral) {
        matriz = new Cell[height][width];
        poblar(neutral);

    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    public void printData() {
//        generation;
        int totalCells = getHeight() * getWidth();
//        getDonadoras();
//        getReceptoras();
        int emptyCells = totalCells - getDonadoras() - getReceptoras();
//        getAntibiotico();
        float percentage = (float) getDonadoras() / (float) (totalCells-emptyCells);
        //variables: tipo de plasmido
        //rango

        LogPrinter.printData(Integer.toString(generation),Integer.toString(totalCells),Integer.toString(Bacteria.typePlasmid)
                ,Integer.toString(getDonadoras()),Integer.toString(getReceptoras())
                ,Integer.toString(emptyCells),Integer.toString(getAntibiotico())
                ,Float.toString(percentage));
    }

    public void printNames(){
        LogPrinter.printData("Generation","Total Cells","Plasmid","Donors","Recipients","Empty","Antibiotic","Percentage");
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int ancho) {
        this.width = ancho;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int alto) {
        this.height = alto;
    }

    /**
     * Populate the automata, where the empty cells have a neutral concentration or random
     * @param neutral
     */
    public void poblar(boolean neutral) {
        donadoras=0;
        receptoras=0;
        antibiotico=0;
        matriz = new Cell[height + 2][width + 2];
        Bacteria.restartBacteriaCenter();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {              
                boolean border = false;
                int tipo = 0;
                if (i == 0 || j == 0 || (i == matriz.length - 1) || (j == matriz[i].length - 1)) {
                    tipo = 3;
                    border = true;
                } else {
                     tipo = (int) Math.ceil(Constants.bacteriaPercentage-Math.random());
                }
                if(tipo==1){
                    tipo =(int) Math.ceil(Math.random()-Constants.donorsPercentage)+1;
                }
                if (tipo == 1) {
                    matriz[i][j] = new Bacteria(1);
                    donadoras++;
                } else if (tipo == 2) {
                    matriz[i][j] = new Bacteria(2);
                    receptoras++;
                } else {
                    matriz[i][j] = border ? new Vacio(Constants.borderDefault) : new Vacio(neutral);
                     if(matriz[i][j].isAntibiotic()){
                         antibiotico++;
                     }
                }
            }
        }
        printNames();
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
     * Returns a Cell relative to the current one(i,j) in the direction given
     *
     * @param direction
     * @param i
     * @param j
     * @param clone says whether the returned Cell should be taken from the normal matrix or the clone matrix(without changes)
     * @return
     */
    public Cell getCellAtFrom(int direction, int i, int j, boolean clone) {
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
     * Returns a Cell relative to the current one(i,j) in the direction given, taking in account a toroidal form.
     * @param direction
     * @param i
     * @param j
     * @param clone says whether the returned Cell should be taken from the normal matrix or the clone matrix(without changes)
     * @return
     */
    public Cell getCellAtFromRound(int direction, int i, int j, boolean clone) {
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

    /**Main function of the Hexatron, calculates the next generation.
     *
     */
    public void nextGen() {
        matrizClone = matriz.clone();
        donadoras=0;
        receptoras=0;
        antibiotico=0;
        for (int i = 1; i < matriz.length - 1; i++) {
            for (int j = 1; j < matriz[i].length - 1; j++) {
                if (matrizClone[i][j] instanceof Bacteria) {
                    Bacteria bact = (Bacteria) matriz[i][j];
                    Difusa difusa = new Difusa();
                    double[] neighboors = neighboorsConcentration(bact, i, j);                  
                    LogPrinter.printConsole("******i:j "+i+":"+j+" "+bact.getConcentration(),3);
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
                            Cell c = getCellAtFromRound(bact.getDireccionCabeza(), i, j, false);
                            Bacteria bact2 = c instanceof Bacteria ? (Bacteria) c : null;
                            bact.conjugar(bact2);
                            if (bact2 == null) {
//                                bact.girarBacteria();//TODO cheq giro cuando conjugacion no fructifera
                            }
                            break;
                        }
                        default:
                            bact.girarBacteria();

                    }
                    bact.modifyEnviroment(enviromentAverage(i, j));
                    bact.runTime();
                }
                if(matrizClone[i][j] instanceof Vacio && matrizClone[i][j].isAntibiotic()){
//                    System.out.println("antibiotic source");
                     Cell cell=matriz[i][j];
                     cell.setConcentration(cell.getConcentration()+(-1)*(cell.getConcentration()+50));
                     antibiotico++;
                }
//                concentrationDiffussion(i, j);
            }
        }
      // for(int k=0;k<5;k++){
        for (int i = 1; i < matriz.length - 1; i++) {
            for (int j = 1; j < matriz[i].length - 1; j++) {
                if (matriz[i][j] instanceof Bacteria){
                    Bacteria bact=(Bacteria)matriz[i][j];
                    if(bact.getTipo()==1){//donor
                        donadoras++;
                    }
                    else{
                        receptoras++;
                    }
                }
                 concentrationDiffussion(i, j);
            }
        }
       //}
        generation++;
        printData();
    }

    /**
     * Returns an array with the concentration of the 3 visible neighbors of a bacteria in a given position
     * @param bact Bacteria
     * @param i
     * @param j
     * @return
     */
    private double[] neighboorsConcentration(Bacteria bact, int i, int j) {
        double lcons = 0, fcons = 0, rcons = 0;
        Cell fcell = getCellAtFromRound(bact.getDireccionCabeza(), i, j, true);
         fcons = fcell.getConcentration();
//        fcons = fcell instanceof Bacteria ? fcell.getConcentration() : Double.NaN;
        Cell lcell = getCellAtFromRound(bact.getDireccionCabeza() - 1, i, j, true);
        lcons= lcell.getConcentration();
//        lcons = lcell instanceof Bacteria ? lcell.getConcentration() : Double.NaN;
        Cell rcell = getCellAtFromRound(bact.getDireccionCabeza() + 1, i, j, true);
        rcons = rcell.getConcentration();
//        rcons = rcell instanceof Bacteria ? rcell.getConcentration() : Double.NaN;
        double[] neighboors = {fcons, rcons, lcons};
        return neighboors;
    }

    /**
     * For the cell in i,j calculates the difference of concentration and if bigger than 15 redistributes the concentration
     * as in a sand pile model.
     * @param i
     * @param j
     */
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
//            if (tempcons > 0 && tempcons < Cell.concentrationMax) {
//                normalCells++;
//            }
//            if (tempcons == Cell.concentrationMax) {
//                topCells++;
//            }
        }
        float neighborConcentration = (concentracionAcumulada) / 6;
        if (concentracion >= neighborConcentration + 15) {
            float nueva = (neighborConcentration + concentracion) / 2;
            matriz[i][j].setConcentration(nueva);
            float cons = concentracion - nueva;
            neighboorsChangeCons(cons / 6, i, j);
        }
        if (concentracion <= neighborConcentration - 15) {
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
//            if (tempcons > 0 && tempcons < Cell.concentrationMax) {
//                normalCells++;
//            }
//            if (tempcons == Cell.concentrationMax) {
//                topCells++;
//            }
//        }
//         float nueva=concentracion;//(concentracionAcumulada / (normalCells + topCells + 1));
//       // float nueva = ceroCells == 6 ? 0 : (concentracionAcumulada / (normalCells + topCells + 1)) + 50;
////        if (concentracion == 0) {
////            LogPrinter.printConsole(concentracion + ":" + i + "|" + j + " n:" + normalCells + " top:" + topCells + " nueva:" + nueva, 4);
////        }
////
////        if (concentracion == Cell.concentrationMax) {//sick becomes healthy
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
            Cell c = getCellAtFromRound(k, i, j, true);
            if (c instanceof Bacteria) {
                Bacteria bact = (Bacteria) c;
                float tempcons = bact.getConcentracionBact();
//            concentracionAcumulada+=tempcons;
                if (tempcons == 0) {
                    ceroCells++;
                }
                if (tempcons > 0 && tempcons < Cell.concentrationMax - 20) {
                    normalCells++;
                }
                if (tempcons > Cell.concentrationMax - 20) {
                    topCells++;
                }
            }
        }
        if (concentracion > Cell.concentrationMax - 10) {
            ((Bacteria) matriz[i][j]).setConcentracionBact(0);
        }
        if (topCells > 5 && concentracion > Cell.concentrationMax - 20) {
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

    /**
     * Changes the concentration of all neighbors of i,j in a value(cons)
     * @param cons
     * @param i
     * @param j
     */
    private void neighboorsChangeCons(float cons, int i, int j) {
        for (int k = 1; k <= 6; k++) {
            Cell cell = getCellAtFromRound(k, i, j, true);
            cell.setConcentration(cell.getConcentration() + cons);
        }
    }

    private List findFree(int i, int j) {
        List<Integer> l = new ArrayList<Integer>();
        for (int k = 1; k <= 6; k++) {
            Cell cell = getCellAtFromRound(k, i, j, true);
            if (cell instanceof Vacio) {
                l.add(k);
            }
        }
        return l;
    }

    /**
     * @return the donadoras
     */
    public int getDonadoras() {
        return donadoras;
    }

    /**
     * @return the receptoras
     */
    public int getReceptoras() {
        return receptoras;
    }

    /**
     * @return the antibiotico
     */
    public int getAntibiotico() {
        return antibiotico;
    }
}
