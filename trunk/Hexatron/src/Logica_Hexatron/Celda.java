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
   private float concentration;
   int x;
   int y;

   public Celda(float conc){
       concentration=conc;
   }

   public Celda(){
       concentration=(float)Math.random()*concentrationMax;
   }

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
