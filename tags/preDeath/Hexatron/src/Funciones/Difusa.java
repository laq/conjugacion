/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;


/**
 *
 * @author Ztiphen
 */
public class Difusa {

    double membresia[] = new double[3];//conjugar, girar der, girar iz

    //Función difusa que retorna un arreglo de double con las membresias de las  acciones
    public double[] difuso(double[] neighboorsConcentration, double ownConcentration) {
         for(int i=0;i<neighboorsConcentration.length;i++){
             membresia[i]=Math.abs((int)(ownConcentration-neighboorsConcentration[i]));
             if(membresia[i]==Double.NaN){
                 membresia[i]=50;
             }
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
        for (int i = 0; i < membresia.length; i++) {
            membresia[i] = membresia[i] * Math.random();
            if (membresia[i] > M) {
                M = membresia[i];
                pos = i;
            }
        }
        return pos;
    }

    //Inicializa el arreglo de membresia en cero
    private void inicializar() {
        for (int i = 0; i < membresia.length; i++) {
            membresia[i] = 0;
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
       // Difusa d = new Difusa();
     //   for(int i=-250;i<=250;i++){
      //   System.out.println(i+":"+d.accion(i)+","+d.accion(i)+","+d.accion(i));
        System.out.println(Double.NaN==10);
//        }
        
    }
}
