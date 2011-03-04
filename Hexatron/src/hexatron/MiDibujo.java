/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hexatron;

import Logica_Hexatron.Celda;
import Logica_Hexatron.Constants;
import Logica_Hexatron.Hexatron;
import java.awt.Checkbox;
import java.awt.Graphics;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.KeyStroke;

/**
 *
 * @author Ztiphen
 */
public class MiDibujo extends JFrame {

    private Hexatron hexatron = new Hexatron();
    private Lienzo lienzo = new Lienzo(getHexatron());
    private JLabel jlable;
    private JSlider jslider;

    

    

    public static void main(String args[]) {
        int screenx = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screeny = Toolkit.getDefaultToolkit().getScreenSize().height;

        MiDibujo midibujo = new MiDibujo();
        JMenuBar menubar = crearMenuBar(midibujo);
        midibujo.addMouse();
        midibujo.setJMenuBar(menubar);
//        midibujo.setMenuBar(menubar);
        midibujo.lienzo.setDoubleBuffered(true);
        Box b=new  Box(BoxLayout.Y_AXIS);
        midibujo.jlable=new JLabel("Generations : 0");
        b.add(midibujo.jlable);
        midibujo.lienzo.setJlable(midibujo.jlable);
        midibujo.jslider=new JSlider(0, 1500, 750);
        midibujo.lienzo.setjslider(midibujo.jslider);
        b.add(midibujo.jslider);
        b.add(midibujo.lienzo);
        midibujo.add(b);
      //  midibujo.dibujar();
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

    private static JMenuBar crearMenuBar(final MiDibujo midibujo) {
        JMenuBar menubar = new JMenuBar();

        JMenu menu = menuAutomata(midibujo);
        menubar.add(menu);
        menu = menuConjugation();
        menubar.add(menu);
        menu = menuView(midibujo);
        menubar.add(menu);
        menu = menuHelp(menu);
        menubar.add(menu);





        return menubar;
    }

    private static JMenu menuAutomata(final MiDibujo midibujo) {
        JMenu menu = new JMenu("Automata");
        menu.setMnemonic('a');
        JMenuItem menuItem = new JMenuItem("Dimensions");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));

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
        menuItem = new JMenuItem("Generations");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String sgen = JOptionPane.showInputDialog("# of Generations to run, actual:" + midibujo.lienzo.getGenerations());
                midibujo.lienzo.setGenerations(Integer.parseInt(sgen));
                midibujo.getJMenuBar().getMenu(0).getItem(2).setLabel("Start simulation and run for " + sgen + " generations");
            }
        });
        menu.add(menuItem);
        //Iniciar simulacion
        menuItem = new JMenuItem("Run simulation for " + midibujo.lienzo.getGenerations() + " generations");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //midibujo.startSimulation();
                new Thread(midibujo.lienzo).start();
            }
        });
        menu.add(menuItem);
        //step
        menuItem = new JMenuItem("Step");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                midibujo.step();
            }
        });
        menu.add(menuItem);
        //re pobloar
        menuItem = new JMenuItem("Clear and Restart(Random)");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                midibujo.hexatron.poblar();
                midibujo.hexatron.setGeneration(0);
                midibujo.repaint();
            }
        });
        menu.add(menuItem);
        return menu;
    }

    private static JMenu menuConjugation() {
        JMenu menu;
        menu = new JMenu("Conjugation");
         menu.setMnemonic('c');
        JMenuItem menuItem = new JMenuItem("Probabilities");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
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
        menuItem = new JMenuItem("Min Conjugation Concentration");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String sgen = JOptionPane.showInputDialog("Level for Doning(actual:" + Constants.minConjugationConcentration+" max:"+Celda.concentrationMax+")");
                Constants.minConjugationConcentration = Float.parseFloat(sgen);
            }
        });
        menu.add(menuItem);
        return menu;
    }

    private static JMenu menuView(final MiDibujo m) {
        JMenu menu;
        menu = new JMenu("View");
         menu.setMnemonic('v');
        JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem("Bacteria Layer",true);
        m.lienzo.setBacteriaLayer(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m.repintar();
            }
        });
        menu.add(menuItem);
         menuItem = new JCheckBoxMenuItem("Concentration Layer",false);
         m.lienzo.setConcentrationLayer(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m.repintar();
            }
        });
        menu.add(menuItem);
         menuItem = new JCheckBoxMenuItem("Bacteria Concentration Layer",false);
         m.lienzo.setBacteriaConcentrationLayer(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m.repintar();
            }
        });
        menu.add(menuItem);
        menuItem = new JCheckBoxMenuItem("Show bact Concentration",true);
        m.lienzo.setShowConcentrationNumber(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m.repintar();
            }
        });
        menu.add(menuItem);
        return menu;
    }
    
    private static JMenu menuHelp(JMenu menu) {
        menu = new JMenu("?");
        JMenuItem menuItem = new JMenuItem("About...");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
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
