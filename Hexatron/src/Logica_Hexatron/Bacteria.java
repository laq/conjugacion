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

    private int tipo;//1 donadora 2 receptora
    private String codigo_gen;
    private float concentracionBact;
    private int direccionCabeza;//1 hacia arriba 4 es hacia atras y el resto son los puntos intermedios
    private int tiempoDonadora = 0;
    private int tiempoReceptora = 0;
    private Plasmid plasmid;
    public static boolean conjugationOnConcentration = false;

    //BEFORE
//    public Bacteria (int tipo)
//    {
//    this.tipo = tipo;
//    direccionCabeza=(int)(Math.random()*5)+1;
//    }
    /**
     * Creacion de bacteria basada en una concentracion
     * @param tipo
     */
    public Bacteria(int tipo) {
        super(randomConcentrationOnTipe(tipo));
//    this.tipo = tipo;//BEFORE
//        concentracionBact = (float) Math.random() * Celda.concentrationMax;
        if (conjugationOnConcentration) {
            if (this.getConcentration() > Constants.minConjugationConcentration) {
                this.tipo = 1;
                plasmid = new SimplePlasmid();
            } else {
                this.tipo = 2;
            }
        } else {
            this.tipo = tipo;
            if (tipo == 1) {
                plasmid = new SimplePlasmid();
            }
        }
        direccionCabeza = (int) (Math.random() * 5) + 1;

    }
    public static float randomConcentrationOnTipe(int type){
        if(type==1)
        {
            return Celda.validRandomConcentrationRange(100, 50);
        }
        else{
            return Celda.validRandomConcentrationRange(100, 0);
        }
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    //BEFORE
//    /**
//     * @param tipo the tipo to set
//     */
//    public void setTipo(int tipo) {
//        this.tipo = tipo;
//    }
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
     * @return the direccionCabeza
     */
    public int getDireccionCabeza() {
        return direccionCabeza;
    }

    /**
     * @param direccionCabeza the direccionCabeza to set
     */
    public void setDireccionCabeza(int direccionCabeza) {
        if (direccionCabeza < 1 || direccionCabeza > 6) {
            throw new Error("valor invalido");
        }
        this.direccionCabeza = direccionCabeza;
    }

    public int getTiempo() {
        if (tipo == 1) {
            return tiempoDonadora;
        } else {
            return tiempoReceptora;
        }
    }

    public void runTime() {
        if (tipo == 1) {
            tiempoDonadora++;
        } else {
            tiempoReceptora++;
        }
    }

    /**
     * The current bacteria donates plasmid to bact2
     * @param bact2 the receptor bacteria
     */
    public void conjugar(Bacteria bact2) {
        if (this.getTipo() == 1 && this.getTiempo() >= Constants.timeToConjugate) {
            if (bact2 != null) {//&& bact2.getTipo() != 1) {
                if (conjugationOnConcentration) {
                    float cons = this.getConcentration() + bact2.getConcentration();
                    cons = cons / 2;
                    if (cons < bact2.getConcentration()) {
                        cons = bact2.getConcentration();//the donor has nothing to give, cause it has less concentration
                    }
                    bact2.setConcentration(cons);
                }else{
                    bact2.tipo=1;
                    bact2.plasmid= new SimplePlasmid();
                }
                //   bact2.setConcentracionBact(cons);
                //bact2.setTipo(1);//BEFORE
            }
        }
    }

    public void girarBacteria() {
        //Movimiento
        int i = (int) (Math.round(Math.random()));//random movement right or left
       girarBacteria(i);
    }
    /**
     *
     * @param i <1 right >1 left
     */
     public void girarBacteria(int i) {

        if (i < 1) {
            this.setDireccionCabeza((this.getDireccionCabeza() % 6) + 1);
        } else {
            this.setDireccionCabeza(((this.getDireccionCabeza() + 4) % 6) + 1);
        }
    }
    /**
     * @return the concentracionBact
     */
    public float getConcentracionBact() {
        return concentracionBact;
    }

    /**
     * @param concentracionBact the concentracionBact to set
     */
    public void setConcentracionBact(float concentracionBact) {
        this.concentracionBact = concentracionBact;
        if (concentracionBact > Constants.minConjugationConcentration) {
            tipo = 1;
        } else {
            tipo = 2;
        }
    }

    void modifyEnviroment(float envAvrg) {

        float newenv;
        float currentenv = this.getConcentration();
        newenv = calculateNewEnvState(envAvrg, currentenv);
        this.setConcentration(newenv);
    }

    private float calculateNewEnvState(float envAvrg, float currentenv) {
        if (tipo == 1) {
            return plasmid.calculateNewEnvState(envAvrg, currentenv);
        } else {
//            System.out.println((envAvrg/currentenv)*5);//LAQ
            return currentenv;//(currentenv + (envAvrg / currentenv) * 5);

        }
    }

    @Override
    public void setConcentration(float concentration) {
        super.setConcentration(concentration);
        if (conjugationOnConcentration) {
            if (this.getConcentration() > Constants.minConjugationConcentration) {
                tipo = 1;
                plasmid = new SimplePlasmid();
            } else {
                tipo = 2;
                plasmid = null;
            }
        }
    }

}
