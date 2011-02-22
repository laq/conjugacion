/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hexatron;

import Logica_Hexatron.Bacteria;
import Logica_Hexatron.Celda;
import Logica_Hexatron.Hexatron;
import Logica_Hexatron.Vacio;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * Clase que hereda de Canvas y sirve para dibujar una linea.
 */
public class Lienzo extends JPanel implements Runnable {

    int xPoints[] = {5, 10, 10, 5, 0, 0};
    int yPoints[] = {0, 5, 10, 15, 10, 5};
    private Hexatron hexatron;
    
    Image offscreen;
    Graphics gBuff;
    private int generations=5;

    /**
     * Constructor. Hace que el tamaño del canvas sea 100x100 pixels.
     */
    public Lienzo(Hexatron hexatron) {
        this.hexatron = hexatron;
    }

    
    @Override
    public void paint(Graphics g) {        
        int pxwidth = this.getWidth();
        int pxheight = this.getHeight();
        float width = pxwidth / (hexatron.getAncho()+2);
        float height = pxheight / (hexatron.getAlto()+2);
        float val = width > height ? height : width;
        int valHex = (int) (val / 2 - (val / 10));
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
                if (getMatriz()[i][j] instanceof Bacteria) {
                    Bacteria bact = (Bacteria) (getMatriz()[i][j]);
                    if (bact.getTipo() == 1) {
                        g.setColor(Color.blue);
                        g.fillPolygon(xPoints, yPoints, 6);
                    } else {
                        g.setColor(Color.CYAN);
                        g.fillPolygon(xPoints, yPoints, 6);
                    }
                    paintHead(bact, g, valHex);
                } else if (getMatriz()[i][j] instanceof Vacio) {
                    g.setColor(Color.green);
                    g.drawPolygon(xPoints, yPoints, 6);
                }
                for (int k = 0; k < 6; k++) {
                    xPoints[k] = xPoints[k] + (int) val;
                }
            }
            for (int k = 0; k < 6; k++) {
                yPoints[k] = yPoints[k] + (int) val;
            }
            
            //Para hacer el desface horizontal y alinear los hexagonos
            if (i % 2 != 0) {
                xPoints[0] = valHex;
                xPoints[1] = 2 * valHex;
                xPoints[2] = 2 * valHex;
                xPoints[3] = valHex;
                xPoints[4] = 0;
                xPoints[5] = 0;
            } else {
                xPoints[0] = valHex + (3 * valHex) / 2;
                xPoints[1] = 2 * valHex + (3 * valHex) / 2;
                xPoints[2] = 2 * valHex + (3 * valHex) / 2;
                xPoints[3] = valHex + (3 * valHex) / 2;
                xPoints[4] = 0 + (3 * valHex) / 2;
                xPoints[5] = 0 + (3 * valHex) / 2;
            }
        }
        yPoints[0] = 0;
        yPoints[1] = valHex;
        yPoints[2] = 2 * valHex;
        yPoints[3] = 3 * valHex;
        yPoints[4] = 2 * valHex;
        yPoints[5] = valHex;
//        this.printAll(gBuff);
        
    }

    @Override
    public void update(Graphics g) {
       paint(g);
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

    private void paintHead(Bacteria bact, Graphics g, int val) {
        g.setColor(Color.red);
        Graphics2D g2d = ((Graphics2D) g);
        Stroke s = g2d.getStroke();
        g2d.setStroke(new BasicStroke(val / 2));
        int i = 0, j = 1;

        i = (bact.getDireccionCabeza() - 1);
        i = i < 0 ? 5 : i;
        j = (bact.getDireccionCabeza()) % 6;

        g.drawLine(xPoints[i], yPoints[i], xPoints[j], yPoints[j]);
        g2d.setStroke(s);

    }

    void startSimulation(int generations) {
        int i = 0;
        while (i < generations) {
            hexatron.nextGen();
            Graphics gr=this.getGraphics();
            this.repaint();
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.err.println("ERROR");
                ex.printStackTrace();
            }
            i++;
        }
    }

    public void run() {
        startSimulation(getGenerations());
    }

    /**
     * @return the generations
     */
    public int getGenerations() {
        return generations;
    }

    /**
     * @param generations the generations to set
     */
    public void setGenerations(int generations) {
        this.generations = generations;
    }
    /**
     * Guarda la línea que se le pasa para dibujarla cuando se le indique
     * llamando a paint()
     */


}
