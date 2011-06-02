
package Logic_Hexatron.plasmids;




/**
 * A Plasmid that does nothing
 * @author Ztiphen
 * @author LAQ
 */
public class SimplePlasmid implements Plasmid{
    float plasmidPlus=10;

    public SimplePlasmid() {
    }

    public float calculateNewEnvState(float envAvrg, float currentenv) {
        //posible y=x+(-(x-50)^(1/3)) more smooth
        //current y=x+(-1/4)*(x-50)
       // float newEnv=currentenv+(-1f/4f)*(currentenv-50);
       // newEnv=(envAvrg+2*currentenv)/3;
        
        return currentenv;
    }

     public void setPlasmidParameter(float parameter) {
        plasmidPlus=parameter;
    }
}
