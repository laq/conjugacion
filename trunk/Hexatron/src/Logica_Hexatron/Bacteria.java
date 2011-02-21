/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica_Hexatron;

/**
 *
 * @author Ztiphen
 */
public class Bacteria extends Celda {
    

    
     private int tipo;
     private String codigo_gen;
     private int estado;
     private int direccionCabeza;//1 hacia arriba 4 es hacia atras y el resto son los puntos intermedios
    
    
    public Bacteria (int tipo)
    {
    this.tipo = tipo;
    direccionCabeza=(int)(Math.random()*5)+1;
    }

  
     /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the codigo_gen
     */
    public String getCodigo_gen() {
        return codigo_gen;
    }

    /**
     * @param codigo_gen the codigo_gen to set
     */
    public void setCodigo_gen(String codigo_gen) {
        this.codigo_gen = codigo_gen;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return the direccionCabeza
     */
    public int getDireccionCabeza() {
        return direccionCabeza;
    }

    /**
     * @param direccionCabeza the direccionCabeza to set
     */
    public void setDireccionCabeza(int direccionCabeza) {
        this.direccionCabeza = direccionCabeza;
    }
    
    
    
   
    
    

}
