/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica_Hexatron;

/**
 *
 * @author LAQ
 */
class SimplePlasmid implements Plasmid{

    public SimplePlasmid() {
    }

    public float calculateNewEnvState(float envAvrg, float currentenv) {
        float newEnv=currentenv;
        newEnv=(envAvrg+2*currentenv)/3;
        
        return newEnv;
    }

}
