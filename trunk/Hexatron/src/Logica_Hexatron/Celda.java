/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica_Hexatron;

/**
 *
 * @author Ztiphen
 */
public class Celda {
   public static float concentrationMax=1000;
   private float concentration=(float)Math.random()*400;
   int x;
   int y;

    /**
     * @return the concentration
     */
    public float getConcentration() {
        return concentration;
    }

    /**
     * @param concentration the concentration to set
     */
    public void setConcentration(float concentration) {
        if (concentration>Celda.concentrationMax){
            concentration=Celda.concentrationMax;
        }
        this.concentration = concentration;
    }
   
    

}
