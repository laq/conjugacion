/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica_Hexatron;

/**
 *
 * @author Ztiphen
 */
public class Vacio  extends Celda{
    public Vacio(float conc){
        super(conc);
    }
    public Vacio(boolean neutral){
        super(concentrationOnNeutral(neutral));
    }

    private static float concentrationOnNeutral(boolean neutral){
        if(neutral){
            return 0;
        }
        else{
           return Celda.validRandomConcentrationRange(200, -50);
        }

    }


}
