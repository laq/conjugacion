/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hexatron;

import Logica_Hexatron.Constants;
import Logica_Hexatron.Hexatron;
import com.sun.corba.se.impl.orbutil.closure.Constant;
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
    private Lienzo lienzo = new Lienzo(getHexatron());
    static private String msj=null;

    public MiDibujo() {
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


    public void addMouse() {
        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                step();
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
     public void dibujar() {
        this.add(lienzo);
    }

    public void repintar() {
        lienzo.setHexatron(hexatron);
        lienzo.repaint();
    }

    private static MenuBar crearMenuBar(final MiDibujo midibujo) throws HeadlessException {
        MenuBar menubar = new MenuBar();

        Menu menu= menuAutomata(midibujo);
        menubar.add(menu);
        menu = menuConjugation();
        menubar.add(menu);
        menu = menuHelp(menu);
        menubar.setHelpMenu(menu);

        
        
       
        
        return menubar;
    }
      private static Menu menuAutomata(final MiDibujo midibujo)  {
        Menu menu = new Menu("Automata");
        MenuItem menuItem = new MenuItem("Dimensions", new MenuShortcut(KeyEvent.VK_D));
        //Cuadrar ancho y alto
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String sanch = JOptionPane.showInputDialog("grill width, actual:" + midibujo.hexatron.getAncho());
                int ancho = Integer.parseInt(sanch);
                String salto = JOptionPane.showInputDialog("grill heigth, actual:" + midibujo.hexatron.getAlto());
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
        menuItem = new MenuItem("Generations", new MenuShortcut(KeyEvent.VK_G));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String sgen = JOptionPane.showInputDialog("# of Generations to run, actual:" + midibujo.lienzo.getGenerations());
                midibujo.lienzo.setGenerations(Integer.parseInt(sgen));
                midibujo.getMenuBar().getMenu(0).getItem(2).setLabel("Start simulation and run for " + sgen + " generations");
            }
        });
        menu.add(menuItem);
        //Iniciar simulacion
        menuItem = new MenuItem("Run simulation for " + midibujo.lienzo.getGenerations() + " generations", new MenuShortcut(KeyEvent.VK_R));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //midibujo.startSimulation();
                new Thread(midibujo.lienzo).start();
            }
        });
        menu.add(menuItem);
        //step
        menuItem = new MenuItem("Step", new MenuShortcut(KeyEvent.VK_S));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                midibujo.step();
            }
        });
        menu.add(menuItem);
        //re pobloar
        menuItem = new MenuItem("Clear and Restart(Random)", new MenuShortcut(KeyEvent.VK_C));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                midibujo.hexatron.poblar();
                midibujo.repaint();
            }
        });
        menu.add(menuItem);
        return menu;
    }

    private static Menu menuConjugation()  {
        Menu menu;
        menu = new Menu("Conjugation");
        MenuItem menuItem = new MenuItem("Probabilities", new MenuShortcut(KeyEvent.VK_P));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                float movement = Constants.movementProbability - Constants.conjugationProbability;
                String sgen = JOptionPane.showInputDialog("Probability of conjugation, actual:" + Constants.conjugationProbability);
                Constants.conjugationProbability = Float.parseFloat(sgen);
                sgen = JOptionPane.showInputDialog("Probability of movement, actual:" + movement);
                Constants.movementProbability = Float.parseFloat(sgen) + Constants.conjugationProbability;
                //No se hacen las siguientes por ser obvias
                // sgen = JOptionPane.showInputDialog("Probability of doing nothing, actual:" + Constants.nothingProbability);
                // Constants.nothingProbability = Integer.parseInt(sgen) + Constants.movementProbability;
            }
        });
        menu.add(menuItem);
        return menu;
    }

    private static Menu menuHelp(Menu menu) throws HeadlessException {
        menu = new Menu("?");
        MenuItem menuItem = new MenuItem("About...", new MenuShortcut(KeyEvent.VK_H));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Development verion, By: \n Stifen Panche \n y \n Leonardo Quiñonez", "About..", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menu.add(menuItem);
        return menu;
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

    private void step() {
        lienzo.startSimulation(1);
    }
}
