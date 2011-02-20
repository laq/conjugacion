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
  private int ancho;
  private int alto;

    public int[][] getMatriz() {
        return matriz;
    }

    public Hexatron()
    {
        matriz=new int[ancho][alto];
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

    private void poblar() {
          for(int i =0; i<matriz.length; i++)
            for(int j =0; j<matriz.length; j++)
                matriz[i][j]=(int)(Math.random()*4)+1;

    }



}
