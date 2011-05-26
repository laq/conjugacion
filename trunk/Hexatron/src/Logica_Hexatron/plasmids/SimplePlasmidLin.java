/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica_Hexatron.plasmids;




/**
 *
 * @author LAQ
 */
public class SimplePlasmidLin implements Plasmid{
    float plasmidPlus=10;

    public SimplePlasmidLin() {
        plasmidPlus=(float)Math.random();
    }

    public float calculateNewEnvState(float envAvrg, float currentenv) {
        //posible y=x+(-(x-50)^(1/3)) more smooth
        //current y=x+(-1/4)*(x-50)
        float newEnv=currentenv+(-plasmidPlus)*(currentenv-50);
       // newEnv=(envAvrg+2*currentenv)/3;
        
        
        return newEnv;
    }

     public void setPlasmidParameter(float parameter) {
        plasmidPlus=parameter;
    }
}
