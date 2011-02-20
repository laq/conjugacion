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

  private  int matriz[][];
  private int ancho=50;
  private int alto=50;

    public int[][] getMatriz() {
        return matriz;
    }

    public Hexatron()
    {
        matriz=new int[alto][ancho];
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
        matriz= new int[alto][ancho];
          for(int i =0; i<matriz.length; i++)
            for(int j =0; j<matriz[i].length; j++)
                matriz[i][j]=(int)(Math.random()*4)+1;

    }



}
