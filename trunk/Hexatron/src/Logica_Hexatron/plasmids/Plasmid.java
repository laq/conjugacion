/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica_Hexatron.plasmids;

/**
 *
 * @author LAQ
 */
public interface Plasmid{
     float calculateNewEnvState(float envAvrg, float currentenv);
     void setPlasmidParameter(float parameter);

}
