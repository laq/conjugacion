/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import utils.LogPrinter;


/**
 *
 * @author Ztiphen
 */
public class Difusa {

    double membresia[] = new double[3];//conjugar, girar der, girar iz

    //Función difusa que retorna un arreglo de double con las membresias de las  acciones
    public double[] difuso(double[] neighboorsConcentration, double ownConcentration) {
         for(int i=0;i<neighboorsConcentration.length;i++){
             membresia[i]=Math.abs((ownConcentration-neighboorsConcentration[i]));
             LogPrinter.printConsole(i+" "+ ownConcentration+" "+neighboorsConcentration[i]+" "+ membresia[i], 3);
//             if(Double.isNaN(membresia[i])){
//                 LogPrinter.printConsole("nan "+ membresia[i], 3);
//                 membresia[i]=20;
//             }
         }
        return membresia;

    }

    /**
     *
     * @param concentracion
     * @return  0: muerte, 1: Quieto, 2: girar, 3:mover, 4:conjugar
     */
    public int accion(double[] neighboorsConcentration, double ownConcentration) {
        double M = 0;
        int pos = -1;
        inicializar();
        difuso(neighboorsConcentration, ownConcentration);
        for (int i = 1; i < membresia.length; i++) {
            LogPrinter.printConsole(i+" "+ membresia[i], 5);
            membresia[i] += membresia[i-1];
            LogPrinter.printConsole(i+" "+ membresia[i], 5);
        }
        double rand=Math.random()*membresia[membresia.length-1];
        LogPrinter.printConsole("rand="+rand, 3);
         for (int i = membresia.length-1; i>=0; i--) {
             if(rand<membresia[i]){
                 pos=i;
             }
         }
        LogPrinter.printConsole("pos"+pos, 5);
        return pos;
    }

    //Inicializa el arreglo de membresia en cero
    private void inicializar() {
        for (int i = 0; i < membresia.length; i++) {
            membresia[i] = 0;
        }
    }

    public static void main(String[] args) {
       // Difusa d = new Difusa();
     //   for(int i=-250;i<=250;i++){
      //   System.out.println(i+":"+d.accion(i)+","+d.accion(i)+","+d.accion(i));
        System.out.println((73-Double.NaN));
//        }
        
    }
}
