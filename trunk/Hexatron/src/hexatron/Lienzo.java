/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hexatron;

import Logica_Hexatron.Bacteria;
import Logica_Hexatron.Celda;
import Logica_Hexatron.Hexatron;
import Logica_Hexatron.Quimico;
import Logica_Hexatron.Vacio;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import utils.LogPrinter;

/**
 * Clase que hereda de Canvas y sirve para dibujar una linea.
 */
public class Lienzo extends JPanel implements Runnable {

    private int xPoints[] = {5, 10, 10, 5, 0, 0};
    private int yPoints[] = {0, 5, 10, 15, 10, 5};
    private JCheckBoxMenuItem bacteriaLayer;
    private JCheckBoxMenuItem concentrationLayer;
    private JCheckBoxMenuItem bacteriaConcentrationLayer;
    private JCheckBoxMenuItem showConcentrationNumber;
    private Hexatron hexatron;
    private Image offscreen;
    private Graphics gBuff;
    private int generations = 5;
    private JLabel jlable;
    private JSlider jslider;

    /**
     * Constructor. Hace que el tamaño del canvas sea 100x100 pixels.
     */
    public Lienzo(Hexatron hexatron) {
        this.hexatron = hexatron;
    }

    @Override
    public void paint(Graphics g) {
        jlable.setText("Current Generation:" + hexatron.getGeneration());
        int pxwidth = this.getWidth();
        int pxheight = this.getHeight();
        float width = pxwidth / (hexatron.getAncho() + 2);
        float height = pxheight / (hexatron.getAlto() + 2);
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
                if (isBacteriaLayer()) {
                    paintBacteriaLayer(g, i, j, valHex);
                }
                if (isConcentrationLayer()) {
                    paintConcentrationLayer(g, i, j, valHex);
                }
                if (isBacteriaConcentrationLayer()) {
                    paintBacteriaConcentrationLayer(g, i, j, valHex);
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

    public void paintBacteriaLayer(Graphics g, int i, int j, int valHex) {
        if (getMatriz()[i][j] instanceof Bacteria) {
            Bacteria bact = (Bacteria) (getMatriz()[i][j]);
            if (bact.getTipo() == 1) {
                g.setColor(Color.blue);
                g.fillPolygon(xPoints, yPoints, 6);
                paintHead(bact, g, valHex);
            } else {
                g.setColor(Color.CYAN);
                g.fillPolygon(xPoints, yPoints, 6);
            }

        } else if (getMatriz()[i][j] instanceof Vacio) {
            g.setColor(Color.green);
            g.drawPolygon(xPoints, yPoints, 6);
        } else if (getMatriz()[i][j] instanceof Quimico) {
            g.setColor(Color.PINK);
            g.fillPolygon(xPoints, yPoints, 6);
        }
    }

    public void paintConcentrationLayer(Graphics g, int i, int j, int valHex) {
        if (i == 0 || i == getMatriz().length - 1 || j == 0 || j == getMatriz()[0].length - 1) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillPolygon(xPoints, yPoints, 6);
            return;
        }
        if (getMatriz()[i][j] instanceof Bacteria) {
            Bacteria bact = (Bacteria) (getMatriz()[i][j]);
            g.setColor(colorRanger(bact));
            g.fillPolygon(xPoints, yPoints, 6);
            Graphics2D g2d = ((Graphics2D) g);
            Stroke s = g2d.getStroke();
            g2d.setStroke(new BasicStroke(valHex / 4));//TODO change bacteria differentation
            g.setColor(Color.GREEN);
            g.drawPolygon(xPoints, yPoints, 6);
            g2d.setStroke(s);


        } else if (getMatriz()[i][j] instanceof Vacio) {
            g.setColor(colorRanger(getMatriz()[i][j]));
            g.fillPolygon(xPoints, yPoints, 6);
            g.setColor(Color.green);
//            g.drawPolygon(xPoints, yPoints, 6);
        } else if (getMatriz()[i][j] instanceof Quimico) {
            g.setColor(Color.PINK);
            g.fillPolygon(xPoints, yPoints, 6);
        }
        if (isShowConcentrationNumber()) {
            Font f = g.getFont();
            g.setFont(new Font("Arial", Font.PLAIN, valHex - 1));
            g.drawString(" " + (int) getMatriz()[i][j].getConcentration(), xPoints[5], yPoints[5] + valHex);
            g.setFont(f);
        }
    }

    private void paintBacteriaConcentrationLayer(Graphics g, int i, int j, int valHex) {
        if (getMatriz()[i][j] instanceof Bacteria) {
            Bacteria bact = (Bacteria) (getMatriz()[i][j]);
            g.setColor(colorRangeg(bact));
            g.fillPolygon(xPoints, yPoints, 6);
            g.setColor(Color.white);
            if (isShowConcentrationNumber()) {
                Font f = g.getFont();
                g.setFont(new Font("Arial", Font.PLAIN, valHex - 1));
                g.drawString(" " + (int) bact.getConcentracionBact(), xPoints[5], yPoints[5] + valHex);
                g.setFont(f);
            }


        } else if (getMatriz()[i][j] instanceof Vacio) {
            //   g.setColor(Color.white);
            // g.fillPolygon(xPoints, yPoints, 6);
            g.setColor(Color.green);
            g.drawPolygon(xPoints, yPoints, 6);
        } else if (getMatriz()[i][j] instanceof Quimico) {
            g.setColor(Color.PINK);
            g.fillPolygon(xPoints, yPoints, 6);
        }
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
            Graphics gr = this.getGraphics();

            this.repaint();

            try {
                // Thread.sleep(500);
                Thread.sleep(jslider.getMaximum() - jslider.getValue());
            } catch (InterruptedException ex) {
                System.err.println("ERROR");
                ex.printStackTrace();
            }
            i++;


        }

    }

    private Color colorRanger(Celda cell) {
        Color c;
        int cons = (int) cell.getConcentration();
        //to exchange the range, so the more concentration is darker.
        cons = (-1) * cons + (int) (Celda.concentrationMax + Celda.concentrationMin);
        //set the concentration so its only positive values
        cons = cons + (int) (0 - Celda.concentrationMin);
        float concentrationPerUnit =  (675 / (Celda.concentrationMax - Celda.concentrationMin));
        int concentrationValue = ((int)(concentrationPerUnit * cons))+25;
        //int col=(int)(cell.getConcentration() / Celda.concentrationMax);
        int r = concentrationValue < 255 ? concentrationValue : 255;
        int g = concentrationValue < 510 && concentrationValue >= 255 ? concentrationValue - 255 : 255;
        if (concentrationValue < 255) {
            g = 0;
        }
        int b = concentrationValue >= 510 ? concentrationValue - 510 : 0;
        c = new Color(r, g, b);
        return c;
    }

    private Color colorRangeg(Bacteria bact) {
        Color c;
        int cons = (int) bact.getConcentracionBact();
        //to exchange the range, so the more concentration is darker.
        cons = (-1) * cons + (int) (Celda.concentrationMax + Celda.concentrationMin);
        //set the concentration so its only positive values
        cons = cons + (int) (0 - Celda.concentrationMin);
        float concentrationPerUnit =  (510 / (Celda.concentrationMax - Celda.concentrationMin));
        int concentrationValue = (int)(concentrationPerUnit * cons);
        //int col=(int)(cell.getConcentration() / Celda.concentrationMax);
        int g = concentrationValue < 255 ? concentrationValue : 255;
        int b = concentrationValue < 510 && concentrationValue >= 255 ? concentrationValue - 255 : 255;
        if (concentrationValue < 255) {
            b = 0;
        }
        int r = concentrationValue >= 510 ? concentrationValue - 510 : 0;
        c = new Color(r, g, b);
        return c;
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
     * @return the bacteriaLayer
     */
    public boolean isBacteriaLayer() {
        return bacteriaLayer.isSelected();
    }

    /**
     * @param bacteriaLayer the bacteriaLayer to set
     */
    public void setBacteriaLayer(JCheckBoxMenuItem bacteriaLayer) {
        this.bacteriaLayer = bacteriaLayer;
    }

    /**
     * @return the concentrationLayer
     */
    public boolean isConcentrationLayer() {
        return concentrationLayer.isSelected();
    }

    /**
     * @param concentrationLayer the concentrationLayer to set
     */
    public void setConcentrationLayer(JCheckBoxMenuItem concentrationLayer) {
        this.concentrationLayer = concentrationLayer;
    }

    private boolean isBacteriaConcentrationLayer() {
        return bacteriaConcentrationLayer.isSelected();
    }

    public void setBacteriaConcentrationLayer(JCheckBoxMenuItem bactConcentrationLayer) {
        this.bacteriaConcentrationLayer = bactConcentrationLayer;
    }

    /**
     * @return the showConcentrationNumber
     */
    public boolean isShowConcentrationNumber() {
        return showConcentrationNumber.isSelected();
    }

    /**
     * @param showConcentrationNumber the showConcentrationNumber to set
     */
    public void setShowConcentrationNumber(JCheckBoxMenuItem showConcentrationNumber) {
        this.showConcentrationNumber = showConcentrationNumber;
    }

    /**
     * @return the jlable
     */
    public JLabel getJlable() {
        return jlable;
    }

    /**
     * @param jlable the jlable to set
     */
    public void setJlable(JLabel jlable) {
        this.jlable = jlable;
    }

    void setJslider(JSlider jslider) {
        this.jslider = jslider;
    }

    void setjslider(JSlider jslider) {
        this.jslider = jslider;
    }
    /**
     * Guarda la línea que se le pasa para dibujarla cuando se le indique
     * llamando a paint()
     */
}
