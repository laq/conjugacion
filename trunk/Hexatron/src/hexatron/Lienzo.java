/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hexatron;

import Logica_Hexatron.Bacteria;
import Logica_Hexatron.Celda;
import Logica_Hexatron.Hexatron;
import Logica_Hexatron.Vacio;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;

/**
 * Clase que hereda de Canvas y sirve para dibujar una linea.
 */
public class Lienzo extends Canvas {

    int xPoints[] = {5, 10, 10, 5, 0, 0};
    int yPoints[] = {0, 5, 10, 15, 10, 5};
    private Hexatron hexatron;
    int cont = 0;

    /**
     * Constructor. Hace que el tamaño del canvas sea 100x100 pixels.
     */
    public Lienzo(Hexatron hexatron) {

        this.hexatron = hexatron;


    }

    /**
     * Dibuja la última línea que se le haya pasado.
     */
    public void paint(Graphics g) {


        g.setColor(Color.green);
        update(g);



        //g.drawLine(23, 3, 56, 5);
    }

    public void update(Graphics g) {

        int pxwidth = this.getWidth();
        int pxheight = this.getHeight();
        float width = pxwidth / hexatron.getAncho();
        float height = pxheight / hexatron.getAlto();
        float val = width > height ? height : width;
        int valHex = (int)(val/2 - (val / 10));
        xPoints[0] = valHex;
        xPoints[1] = 2 * valHex;
        xPoints[2] = 2 * valHex;
        xPoints[3] = valHex;
        xPoints[4] = 0;
        xPoints[5] = 0;

        yPoints[0] = 0;
        yPoints[1] = valHex;
        yPoints[2] = 2 * valHex;
        yPoints[3] = 3 * valHex;
        yPoints[4] = 2 * valHex;
        yPoints[5] = valHex;
        // int val=12;

        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (int i = 0; i < getMatriz().length; i++) {
            for (int j = 0; j < getMatriz()[i].length; j++) {

                if(getMatriz()[i][j] instanceof Bacteria)
            {
                if(((Bacteria)(getMatriz()[i][j])).getTipo()==1)
                {
                g.setColor(Color.blue);
              g.fillPolygon(xPoints, yPoints, 6);
                }
 else
                {
                    g.setColor(Color.red);
              g.fillPolygon(xPoints, yPoints, 6);}
                
                
              }
     else if(getMatriz()[i][j] instanceof Vacio)
            {
                g.setColor(Color.green);
              g.drawPolygon(xPoints, yPoints, 6);  }


                for (int k = 0; k < 6; k++) {
                    xPoints[k] = xPoints[k] + (int)val;
                }



            }
            for (int k = 0; k < 6; k++) {
                yPoints[k] = yPoints[k] + (int)val;
            }
            cont++;
            if (cont % 2 == 0) {
//                xPoints[0] = 5;
//                xPoints[1] = 10;
//                xPoints[2] = 10;
//                xPoints[3] = 5;
//                xPoints[4] = 0;
//                xPoints[5] = 0;
                xPoints[0] = valHex;
                xPoints[1] = 2 * valHex;
                xPoints[2] = 2 * valHex;
                xPoints[3] = valHex;
                xPoints[4] = 0;
                xPoints[5] = 0;
            } else {
//                xPoints[0] = 12;
//                xPoints[1] = 17;
//                xPoints[2] = 17;
//                xPoints[3] = 12;
//                xPoints[4] = 7;
//                xPoints[5] = 7;
                xPoints[0] = valHex + (3 * valHex) / 2;
                xPoints[1] = 2 * valHex + (3 * valHex) / 2;
                xPoints[2] = 2 * valHex + (3 * valHex) / 2;
                xPoints[3] = valHex + (3 * valHex) / 2;
                xPoints[4] = 0 + (3 * valHex) / 2;
                xPoints[5] = 0 + (3 * valHex) / 2;
            }

        }

//        yPoints[0] = 0;
//        yPoints[1] = 5;
//        yPoints[2] = 10;
//        yPoints[3] = 15;
//        yPoints[4] = 10;
//        yPoints[5] = 5;
        yPoints[0] = 0;
        yPoints[1] = valHex;
        yPoints[2] = 2 * valHex;
        yPoints[3] = 3 * valHex;
        yPoints[4] = 2 * valHex;
        yPoints[5] = valHex;

    }

    /**
     * @return the Matriz
     */
    public Celda[][] getMatriz() {
        return getHexatron().getMatriz();
    }

    /**
     * @return the hexatron
     */
    public Hexatron getHexatron() {
        return hexatron;
    }

    /**
     * @param hexatron the hexatron to set
     */
    public void setHexatron(Hexatron hexatron) {
        this.hexatron = hexatron;
    }
    /**
     * Guarda la línea que se le pasa para dibujarla cuando se le indique
     * llamando a paint()
     */
}