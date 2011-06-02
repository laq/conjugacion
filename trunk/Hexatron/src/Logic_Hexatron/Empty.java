package Logic_Hexatron;

/**
 * Class that represents an empty cell
 * @author Ztiphen
 * @author LAQ
 */
public class Empty  extends Cell{
    public Empty(float conc){
        super(conc);
    }
    public Empty(boolean neutral){
        super(concentrationOnNeutral(neutral));
        if(Math.random()<Constants.antibioticPercentage){
            this.setAntibiotic(true);
        }
    }

    private static float concentrationOnNeutral(boolean neutral){
        if(neutral){
            return 0;
        }
        else{
           return Cell.validRandomConcentrationRange(200, -50);
        }
    }


}
