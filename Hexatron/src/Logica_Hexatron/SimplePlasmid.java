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
        envAvrg=envAvrg-Celda.concentrationMax/2;
        currentenv=currentenv-Celda.concentrationMax/2;
        float newState=currentenv;
        float diff=Math.abs(envAvrg)-Math.abs(currentenv);
        if(diff>1){//the enviroment is stronger
//            System.out.println(diff);
            newState=(envAvrg*2+currentenv)/3;
        }//bacteria is stronger
        else if(diff<-1){
          //  newState=(envAvrg+currentenv*-(1+diff/Celda.concentrationMax))/2;
        }
        else{
            //newState=(envAvrg+currentenv)/2;
        }
        newState=(newState+Celda.concentrationMax/2);
      //  return newState<0?0:newState;
        return currentenv;
    }

}
