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
     /**
      * Returns the new concentration of a cell given its current concentration and the neighbors average
      * @param envAvrg
      * @param currentenv
      * @return
      */
     float calculateNewEnvState(float envAvrg, float currentenv);

     void setPlasmidParameter(float parameter);

}
