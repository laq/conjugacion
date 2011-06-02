package Logica_Hexatron.plasmids;

import Logica_Hexatron.Constants;

/**Plasmid with constant aproximation
 *
 * @author Ztiphen
 * @author LAQ
 */
public class SimplePlasmidConst implements Plasmid {

    float plasmidPlus = 0;

    public SimplePlasmidConst() {
        plasmidPlus = (float) Math.random() * 9.9f + 0.1f;
    }

    public float calculateNewEnvState(float envAvrg, float currentenv) {
        //posible y=x+(-(x-50)^(1/3)) more smooth
        //current y=x+(-1/4)*(x-50)
        float newEnv;
        if (currentenv < Constants.donorsCenter) {
            newEnv = currentenv + plasmidPlus;
        } else {
            newEnv = currentenv - plasmidPlus;
        }

        // newEnv=(envAvrg+2*currentenv)/3;
//        System.out.println(newEnv+" "+currentenv);
        return newEnv;
    }

    public void setPlasmidParameter(float parameter) {
        plasmidPlus = parameter;
    }
}
