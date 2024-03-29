package Logic_Hexatron;

import utils.LogPrinter;

/**
 * A Class representing a cell on the automata, having its own concentration
 * @author Ztiphen
 * @author  LAQ
 */
public class Cell {

    final public static float concentrationMax = 250;
    final public static float concentrationMin = -250;//when this are modified the boarder default must also be modified
    private float concentration;
    private boolean antibiotic=false;
    int x;
    int y;

    public Cell(float conc) {
        LogPrinter.printConsole("concentracoin" + conc, 3);
        this.setConcentration(conc);
    }

    public Cell() {
        concentration = ((float) Math.random() * (concentrationMax - concentrationMin)) + concentrationMin;
        LogPrinter.printConsole("celdaInicio" + concentration, 4);
    }

    /**
     * @return the concentration
     */
    public float getConcentration() {
        return concentration;
    }

    /**
     * @param concentration the concentration to set
     */
    public void setConcentration(float concentration) {
        if (concentration > Cell.concentrationMax) {
            concentration = Cell.concentrationMax;
        }
        if (concentration < Cell.concentrationMin) {
            concentration = Cell.concentrationMin;
        }
        this.concentration = concentration;
    }

    public static float validRandomConcentrationUpperHalf(){
        return ((float) Math.random() * (concentrationMax - concentrationMin) / 2) + concentrationMin + (concentrationMax - concentrationMin) / 2;
    }

    /**
     * gives a random valid value in the range given
     * @param rangeSize
     * @param center
     * @return
     */
     public static int validRandomConcentrationRange(float rangeSize,float center){
         rangeSize=Math.abs(rangeSize)/2;
         float max=center+rangeSize;
         float min=center-rangeSize;
         max=max>concentrationMax?concentrationMax:max;
         min=min<concentrationMin?concentrationMin:min;
        return (int)(( Math.random() * (max - min)) + min);
    }

    /**
     * @return the antibiotic
     */
    public boolean isAntibiotic() {
        return antibiotic;
    }

    /**
     * @param antibiotic the antibiotic to set
     */
    public void setAntibiotic(boolean antibiotic) {
        this.antibiotic = antibiotic;
    }


}
