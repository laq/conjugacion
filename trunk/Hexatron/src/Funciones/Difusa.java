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
    
    
     double membresia[]= new double[5];
   
      
    
    //Función difusa que retorna un arreglo de double con las membresias de las cinco acciones
    public double[] difuso(double concentracion)
    {
        int intervalo = 50;
        int limite =(int) concentracion/50;
        int linf = limite-1;
        int lsup = limite + 1;
      
        if(limite<0)
        {
            membresia[0] = 1;
            return membresia;
        }
            else if(limite==250)
        {
       membresia[4] = 1;
            return membresia;
      }
     
          if(limite>1)
           membresia[linf]=(((limite+1)*intervalo)-concentracion)/intervalo;
           if(limite>0)
           membresia[limite] = 1;
          if(limite<4)
           membresia[lsup] = (concentracion-(limite*intervalo))/intervalo;
        
        if(limite==0)
            membresia[limite]= (intervalo - concentracion)/intervalo;
        
        return membresia;
     
        }
    

    /**
     *
     * @param concentracion
     * @return  0: muerte, 1: Quieto, 2: girar:3, mover:4, conjugar:5
     */
    public int accion(int concentracion)
    {
        double M = 0;
        int pos = -1;
        inicializar();
        difuso(concentracion);
        for(int i =0; i<membresia.length; i++)
        {
            membresia[i] = membresia[i] * Math.random();
            if(membresia[i]>M)
            {
                M=membresia[i];
                pos = i;
            }
        } 
        System.out.println(pos);
        return pos;
    }
            
    
  //Inicializa el arreglo de membresia en cero 
    private void inicializar()
    {
        for(int i=0; i<membresia.length; i++)
            membresia[i]=0;
    }
   
    
     public static void main(String[] args) {
        // TODO code application logic here
          Difusa d = new Difusa();
          d.accion(55);  
    }
    
    
    
}
