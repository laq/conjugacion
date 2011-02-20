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
    
  private  int matriz[][] = new int[120][120];

    public int[][] getMatriz() {
        return matriz;
    }
    
    public Hexatron()
    {
        for(int i =0; i<matriz.length; i++)
            for(int j =0; j<matriz.length; j++)
                matriz[i][j]=(int)(Math.random()*4)+1;
        

    }
    
    

}
