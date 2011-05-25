/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica_Hexatron.plasmids;




/**
 *
 * @author LAQ
 */
public class SimplePlasmid implements Plasmid{
    float plasmidPlus=10;

    public SimplePlasmid() {
    }

    public float calculateNewEnvState(float envAvrg, float currentenv) {
        //posible y=x+(-(x-50)^(1/3)) more smooth
        //current y=x+(-1/4)*(x-50)
        float newEnv=currentenv+(-1f/4f)*(currentenv-50);
       // newEnv=(envAvrg+2*currentenv)/3;
        
        return newEnv;
    }

}
