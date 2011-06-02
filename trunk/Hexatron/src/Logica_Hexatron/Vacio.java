/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica_Hexatron;

/**
 * Class that represents an empty cell
 * @author Ztiphen
 */
public class Vacio  extends Cell{
    public Vacio(float conc){
        super(conc);
    }
    public Vacio(boolean neutral){
        super(concentrationOnNeutral(neutral));
        if(Math.random()<Constants.antibioticPercentage){
            this.setAntibiotic(true);
        }
    }

    private static float concentrationOnNeutral(boolean neutral){
        if(neutral){
            return 0;
        }
        else{
           return Cell.validRandomConcentrationRange(200, -50);
        }
    }


}
