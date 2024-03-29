package Logic_Hexatron.plasmids;




/**
 *Plasmid with cubic root aproximation
 * @author Ztiphen
 * @author LAQ
 */
public class SimplePlasmidCub implements Plasmid{
    float plasmidPlus=20;

    public SimplePlasmidCub() {
        plasmidPlus=(float)(Math.random()*19)+1;
    }

    public float calculateNewEnvState(float envAvrg, float currentenv) {
        //posible y=x+(-(x-50)^(1/3)) more smooth
        //current y=x+(-1/4)*(x-50)
        
        float newEnv=currentenv+(float)(plasmidPlus*Math.cbrt((-currentenv+50f)));
        

       // newEnv=(envAvrg+2*currentenv)/3;
        
        return newEnv;
    }

    public void setPlasmidParameter(float parameter) {
        plasmidPlus=parameter;
    }

}
