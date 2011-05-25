/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica_Hexatron.plasmids;

import Logica_Hexatron.Constants;




/**
 *
 * @author LAQ
 */
public class SimplePlasmidConst implements Plasmid{
    float plasmidPlus=10;

    public SimplePlasmidConst() {
        plasmidPlus=(float)Math.random()*9.9f+0.1f;
    }

    public float calculateNewEnvState(float envAvrg, float currentenv) {
        //posible y=x+(-(x-50)^(1/3)) more smooth
        //current y=x+(-1/4)*(x-50)
        float newEnv;
        if(currentenv<Constants.donorsCenter){
            newEnv=currentenv+plasmidPlus;
        }
        else{
            newEnv=currentenv-plasmidPlus;
        }
        
       // newEnv=(envAvrg+2*currentenv)/3;
        
        return newEnv;
    }

}
