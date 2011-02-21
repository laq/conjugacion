/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hexatron;

import Logica_Hexatron.Hexatron;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Ztiphen
 */
public class MiDibujo extends JFrame {

    
    private Hexatron hexatron = new Hexatron();
    Lienzo lienzo = new Lienzo(getHexatron());

    public MiDibujo(){

    }

    public void addMouse(){
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                paso();
            }

            public void mousePressed(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseReleased(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseEntered(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseExited(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

        });
    }

    private static MenuBar crearMenuBar(final MiDibujo midibujo) throws HeadlessException {
        MenuBar menubar = new MenuBar();
        Menu menu = new Menu("automata");
        //Cuadrar ancho y alto
        MenuItem menuItem = new MenuItem("Dimenciones", new MenuShortcut(KeyEvent.VK_D));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String sanch = JOptionPane.showInputDialog("# numero de celulas a lo ancho, actual");
                int ancho = Integer.parseInt(sanch);
                String salto = JOptionPane.showInputDialog("# numero de celulas a lo alto");
                int alto = Integer.parseInt(salto);
                midibujo.getHexatron().setAlto(alto);
                midibujo.getHexatron().setAncho(ancho);
                midibujo.getHexatron().poblar();
                midibujo.repintar();
                //                midibujo.repaint();
            }
        });
        menu.add(menuItem);
        //Cuadrar numero de generaciones
        menuItem = new MenuItem("Generaciones", new MenuShortcut(KeyEvent.VK_G));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sgen = JOptionPane.showInputDialog("# numero de Generaciones Por Correr, actual:"+midibujo.lienzo.getGenerations());
                midibujo.lienzo.setGenerations(Integer.parseInt(sgen));
            }
        });
        menu.add(menuItem);

        //Iniciar simulacion
        menuItem = new MenuItem("Iniciar simulation", new MenuShortcut(KeyEvent.VK_I));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //midibujo.startSimulation();
                new Thread(midibujo.lienzo).start();
            }
        });
        menu.add(menuItem);

        //step
        menuItem = new MenuItem("Paso", new MenuShortcut(KeyEvent.VK_P));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                midibujo.paso();
            }
        });
        menu.add(menuItem);

        //re pobloar
        menuItem = new MenuItem("Repoblar", new MenuShortcut(KeyEvent.VK_R));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                midibujo.hexatron.poblar();
                midibujo.repaint();
            }
        });
        menu.add(menuItem);


        menubar.add(menu);
        return menubar;
    }

    public void dibujar() {
        this.add(lienzo);
    }

    public void repintar() {
        lienzo.setHexatron(hexatron);
        lienzo.repaint();
    }

    public static void main(String args[]) {
        int screenx = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screeny = Toolkit.getDefaultToolkit().getScreenSize().height;

        MiDibujo midibujo = new MiDibujo();
        MenuBar menubar = crearMenuBar(midibujo);
        midibujo.addMouse();
        midibujo.setMenuBar(menubar);
        midibujo.lienzo.setDoubleBuffered(true);
        midibujo.dibujar();
        midibujo.setBounds(0, 0, screenx, screeny - 50);
        midibujo.setVisible(true);
        midibujo.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * @return the hexatron
     */
    public Hexatron getHexatron() {
        return hexatron;
    }

    

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    private void paso() {
        lienzo.startSimulation(1);
    }
}
