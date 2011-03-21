/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica_Hexatron;

import utils.LogPrinter;

/**
 *
 * @author Ztiphen
 */
public class Celda {

    final public static float concentrationMax = 250;
    final public static float concentrationMin = -250;//when this are modified the boarder default must also be modified
    private float concentration;
    int x;
    int y;

    public Celda(float conc) {
        this.setConcentration(conc);
    }

    public Celda() {
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
        if (concentration > Celda.concentrationMax) {
            concentration = Celda.concentrationMax;
        }
        if (concentration < Celda.concentrationMin) {
            concentration = Celda.concentrationMin;
        }
        this.concentration = concentration;
    }
}
