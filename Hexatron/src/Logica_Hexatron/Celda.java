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
   public static float concentrationMax=250;
   public static float concentrationMin=-250;
   private float concentration;
   int x;
   int y;

   public Celda(float conc){
       concentration=conc;
   }

   public Celda(){
       concentration=((float)Math.random()*(concentrationMax-concentrationMin))-concentrationMin;
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
        if (concentration<Celda.concentrationMin){
            concentration=Celda.concentrationMin;
        }
        this.concentration = concentration;
    }
   
    

}
