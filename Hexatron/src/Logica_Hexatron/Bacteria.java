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
public class Bacteria extends Celda {
    

    
     private int tipo;
     private String codigo_gen;
     private int estado;//1 donadora 2 receptora
     private float concentracionBact;
     private int direccionCabeza;//1 hacia arriba 4 es hacia atras y el resto son los puntos intermedios
     private int tiempoDonadora=0;
     private int tiempoReceptora=0;
    
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
    
    public int getTiempo(){
        if(tipo==1){
            return tiempoDonadora;
        }
        else{
            return tiempoReceptora;
        }
    }

    public void runTime(){
       if(tipo==1){
            tiempoDonadora++;
        }
        else{
            tiempoReceptora++;
        }
    }

    /**
     * The current bacteria donates plasmid to bact2
     * @param bact2 the receptor bacteria
     */
    public void conjugar(Bacteria bact2){
          if (this.getTipo() == 1 && this.getTiempo() >= Constants.timeToConjugate) {
            if (bact2 != null && bact2.getTipo() != 1) {
                bact2.setTipo(1);
            }
        }
    }
    public void moverBacteria() {
        //Movimiento
        int i=(int)(Math.round(Math.random()));//random movement right or left
        if(i<0){
        this.setDireccionCabeza((this.getDireccionCabeza() % 6) + 1);
        }
        else{
         this.setDireccionCabeza(((this.getDireccionCabeza()+4) % 6) + 1);
        }
    }
   
    
    

}
