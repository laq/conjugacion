/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica_Hexatron;

import utils.LogPrinter;

/**
 *
 * @author Ztiphen
 */
public class Hexatron {

    private Celda matriz[][];
    private int ancho = 50;
    private int alto = 50;
    private int generation=0;


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
                boolean border=false;
                int tipo = 0;
                if (i == 0 || j == 0 || (i == matriz.length - 1) || (j == matriz[i].length - 1)) {
                    tipo = 3;
                    border=true;
                } else {
                    tipo = (int) (Math.random() * 3) + 1;
                }
                if (tipo == 1) {
                    matriz[i][j] = new Bacteria(1);
                } else if (tipo == 2) {
                    matriz[i][j] = new Bacteria(2);
                } else {
                    matriz[i][j] = border?new Vacio():new Vacio();
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
        ii=ii>=matriz.length?0:ii;
        jj=jj>=matriz[0].length?0:jj;
        return matriz[ii][jj];
    }
     /**
     * Regresa la bacteria relativa a la actual en la direccion indicada por direction
     * @param direction
     * @param i
     * @param j
     * @return
     */
    public Celda getCellAtFromRound(int direction, int i, int j) {
        i=i==1?matriz.length-2:i;
        i=i==matriz.length-2?1:i;
        j=j==1?matriz[0].length-2:j;
        j=j==matriz[0].length-2?1:j;
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
        ii=ii>=matriz.length?0:ii;
        jj=jj>=matriz[0].length?0:jj;
        return matriz[ii][jj];
    }

    public void nextGen() {


        for (int i = 1; i < matriz.length-1; i++) {
            for (int j = 1; j < matriz[i].length-1; j++) {
                if (matriz[i][j] instanceof Bacteria) {
                    Bacteria bact = (Bacteria) matriz[i][j];
                    float choice = (float) Math.random();
                    if (choice < Constants.conjugationProbability) {
                        Celda c=getCellAtFromRound(bact.getDireccionCabeza(), i, j);
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
        generation++;
    }

    private void concentrationDiffussion(int i, int j) {
        float concentracion=matriz[i][j].getConcentration();
        float concentracionAcumulada=concentracion;
        int ceroCells=0;
        int normalCells=0;
        int topCells=0;
        for(int k=0;k<6;k++){
            float tempcons=getCellAtFromRound(k, i, j).getConcentration();
            concentracionAcumulada+=tempcons;
            if(tempcons==0){
                ceroCells++;
            }
            if(tempcons>0){
                normalCells++;
            }
            if(tempcons==Celda.concentrationMax){
                topCells++;               
            }
        }
        float nueva=concentracionAcumulada/(normalCells+topCells)+1;
        if (concentracion==Celda.concentrationMax){//sick becomes healthy
            nueva=0;
        }
        if(concentracion==0 && (normalCells)<4 ){
            nueva=0;
        }
        if(concentracion==0 && (topCells)<4 ){
            nueva=0;
        }
        LogPrinter.printConsole("nuevaConcentracion:"+nueva,4 );
        matriz[i][j].setConcentration(nueva);
    }

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

    

   
}
