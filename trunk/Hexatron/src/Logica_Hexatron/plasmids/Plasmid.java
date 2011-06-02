package Logica_Hexatron.plasmids;

/**
 * Plasmid interface different plasmids should extend this interface
 * @author Ztiphen
 * @author LAQ
 */
public interface Plasmid {

    /**
     * Returns the new concentration of a cell given its current concentration and the neighbors average
     * @param envAvrg
     * @param currentenv
     * @return
     */
    float calculateNewEnvState(float envAvrg, float currentenv);

    void setPlasmidParameter(float parameter);
}
