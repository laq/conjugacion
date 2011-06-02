package Functions;

import utils.LogPrinter;


/**
 *Class to decide the action to chose based on the concentration differences
 * @author Ztiphen
 * @author LAQ
 */
public class Fuzzy {

    double membership[] = new double[3];//Conjugate , Turn right, Turn Left


    /**
     * Fuzzy function that returns an array with the membership of each action
     * @param neighboorsConcentration
     * @param ownConcentration
     * @return
     */
    public double[] fuzzy(double[] neighboorsConcentration, double ownConcentration) {
         for(int i=0;i<neighboorsConcentration.length;i++){
             membership[i]=Math.abs((ownConcentration-neighboorsConcentration[i]));
             LogPrinter.printConsole(i+" "+ ownConcentration+" "+neighboorsConcentration[i]+" "+ membership[i], 3);
             //OLD code to dont count the empty cells, finally removed.
//             if(Double.isNaN(membership[i])){
//                 LogPrinter.printConsole("nan "+ membership[i], 3);
//                 membership[i]=20;
//             }
         }
        return membership;

    }

    /**
     * Returns the chosen action given the concentration of the bacteria and of the neighbor cells
     * @param neighborsConcentration
     * @param ownConcentration
     * @return
     */
    public int action(double[] neighborsConcentration, double ownConcentration) {
        double M = 0;
        int pos = -1;
        initialize();
        fuzzy(neighborsConcentration, ownConcentration);
        membershipChange();
        for (int i = 1; i < membership.length; i++) {
            LogPrinter.printConsole(i+" "+ membership[i], 4);
            membership[i] += membership[i-1];
            LogPrinter.printConsole(i+" "+ membership[i], 4);
        }
        double rand=Math.random()*membership[membership.length-1];
        LogPrinter.printConsole("rand="+rand, 4);
         for (int i = membership.length-1; i>=0; i--) {
             if(rand<membership[i]){
                 pos=i;
             }
         }
        LogPrinter.printConsole("pos"+pos, 4);
        return pos;
    }

     /**
     *
     * @param concentracion
     * @return  0: muerte, 1: Quieto, 2: girar, 3:mover, 4:conjugar
     */
    public int accionNoRand(double[] neighboorsConcentration, double ownConcentration) {
        double M = Double.NEGATIVE_INFINITY;
        int pos = -1;
        initialize();
        fuzzy(neighboorsConcentration, ownConcentration);
        for (int i = 0; i < membership.length; i++) {
           if(membership[i]>M){
               M=membership[i];
               pos=i;
           }
        }
        LogPrinter.printConsole("pos"+pos, 4);
        return pos;
    }

    //Inicializa el arreglo de membership en cero
    private void initialize() {
        for (int i = 0; i < membership.length; i++) {
            membership[i] = 0;
        }
    }
/*
    public static void main(String[] args) {
       // Fuzzy d = new Fuzzy();
     //   for(int i=-250;i<=250;i++){
      //   System.out.println(i+":"+d.action(i)+","+d.action(i)+","+d.action(i));
        double a=Math.pow((-8d), (1d/3d));
        System.out.println(a+""+Math.cbrt(-8));
//        }
        
    }*/

    private void membershipChange() {
         double min=Double.POSITIVE_INFINITY;
         for (int i = 0; i < membership.length; i++) {
             if(membership[i]<min){
                 min=membership[i];
             }
         }
          for (int i = 0; i < membership.length; i++) {
             membership[i]=Math.pow(membership[i]-min+1,2);
          }
    }
}
