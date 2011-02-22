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
    public Bacteria getBacteriaAtFrom(int direction, int i, int j) {
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
        System.out.println("i:j/ni:nj=" + i + ":" + j + "/" + ii + ":" + jj + " dir:" + direction);
        Bacteria bact = matriz[ii][jj] instanceof Bacteria ? (Bacteria) matriz[ii][jj] : null;
        return bact;
    }

    public void nextGen() {
        for (int i = 1; i < matriz.length; i++) {
            for (int j = 1; j < matriz[i].length; j++) {

                //conjugacion
                if (matriz[i][j] instanceof Bacteria) {
                    Bacteria bact = (Bacteria) matriz[i][j];
                    if ((bact).getTipo() == 1) {
                        Bacteria bact2 = getBacteriaAtFrom(bact.getDireccionCabeza(), i, j);
                        if (bact2 != null && bact2.getTipo() != 1) {
                            bact2.setTipo(1);
                        }

                    }
                }

                if (matriz[i][j] instanceof Bacteria) {
                    Bacteria bact = (Bacteria) matriz[i][j];
                    bact.setDireccionCabeza((bact.getDireccionCabeza() % 6)+1 );
                }
            }
        }
    }
}
