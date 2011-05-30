/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hexatron;

import Logica_Hexatron.Bacteria;
import Logica_Hexatron.Celda;
import Logica_Hexatron.Constants;
import Logica_Hexatron.Hexatron;

import java.awt.Graphics;
import java.awt.Point;
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
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.KeyStroke;

/**
 *
 * @author Ztiphen
 */
public class MiDibujo extends JFrame {

    private Hexatron hexatron = new Hexatron(true);
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
        Box b = new Box(BoxLayout.Y_AXIS);
        midibujo.jlable = new JLabel("Generations : 0");
        b.add(midibujo.jlable);
        midibujo.lienzo.setJlable(midibujo.jlable);
        midibujo.jslider = new JSlider(0, 1500, 750);
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
        final MiDibujo midibujo = this;
        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                int button = e.getButton();
                if (button == MouseEvent.BUTTON1) {
                    step();
                }
                if (button == MouseEvent.BUTTON3) {
                    addAntibiotic(e.getPoint());
                    midibujo.repintar();
                }
                if (button == MouseEvent.BUTTON2) {
                    addConstantAntibiotic(e.getPoint());


                }
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

            private void addAntibiotic(Point point) {
                int j = (int) (point.x / lienzo.val) - 1;
                int i = (int) (point.y / lienzo.val) - 1;
                j = j < 0 ? 0 : j;
                i = i < 0 ? 0 : i;
//                System.out.println(i+" "+j);
                Celda cell = hexatron.getMatriz()[i][j];
//                cell.setConcentration(cell.getConcentration() + (-1f / 8f) * (cell.getConcentration() + 50));
                  cell.setConcentration(cell.getConcentration() - 50);
            }

            private void addConstantAntibiotic(Point point) {
                int j = (int) (point.x / lienzo.val) - 1;
                int i = (int) (point.y / lienzo.val) - 1;
                j = j < 0 ? 0 : j;
                i = i < 0 ? 0 : i;
//                System.out.println(i+" "+j);
                int opt = JOptionPane.showConfirmDialog(midibujo, "Antibiotic source added on:" + i + "," + j);
                boolean conf=opt==JOptionPane.OK_OPTION;
                Celda cell = hexatron.getMatriz()[i][j];
                cell.setAntibiotic(conf);
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
        menu = menuAmbiente(midibujo);
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
                midibujo.getHexatron().poblar(midibujo.lienzo.isNeutralEnviroment());
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

        //bacteria percentage
        menuItem = new JMenuItem("Change bacteria start percentage");
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String sbper = JOptionPane.showInputDialog("Percentage of Bacteria in the environment: (actual " + Constants.bacteriaPercentage+")");
                Constants.bacteriaPercentage = Float.parseFloat(sbper);
                String sdper = JOptionPane.showInputDialog("Percentage of Bacteria who donate: (actual " + Constants.donorsPercentage+")");
                Constants.donorsPercentage = Float.parseFloat(sdper);
                String saper = JOptionPane.showInputDialog("Percentage of Empty cell wich have antibiotic: (actual " + Constants.antibioticPercentage+")");
                Constants.antibioticPercentage = Float.parseFloat(saper);
            }
        });
        menu.add(menuItem);
        //re pobloar
        menuItem = new JMenuItem("Clear and Restart(Random)");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                midibujo.hexatron.poblar(midibujo.lienzo.isNeutralEnviroment());
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
//                float movement = Constants.movementProbability - Constants.conjugationProbability;
//                String sgen = JOptionPane.showInputDialog("Probability of conjugation, actual:" + Constants.conjugationProbability);
//                Constants.conjugationProbability = Float.parseFloat(sgen);
//                sgen = JOptionPane.showInputDialog("Probability of movement, actual:" + movement);
//                Constants.movementProbability = Float.parseFloat(sgen) + Constants.conjugationProbability;
                //No se hacen las siguientes por ser obvias
                // sgen = JOptionPane.showInputDialog("Probability of doing nothing, actual:" + Constants.nothingProbability);
                // Constants.nothingProbability = Integer.parseInt(sgen) + Constants.movementProbability;
            }
        });
//        menu.add(menuItem);
        menuItem = new JMenuItem("Plasmid Type");
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                 String stype=JOptionPane.showInputDialog(null,
                        "Choose plasmid type: 1. for constant 2. for linear 3. for cubic"
                        );
                Bacteria.typePlasmid= Integer.parseInt(stype);
            }
        }
        );
        menu.add(menuItem);
        menuItem = new JMenuItem("Conjugation on Concentration");
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int choice=JOptionPane.showConfirmDialog(null,
                        "Activate conjugation on concentration? (current state:"+Bacteria.conjugationOnConcentration+")",
                        "Conjugation On concentration",
                        JOptionPane.YES_NO_OPTION);
                Bacteria.conjugationOnConcentration=choice==JOptionPane.YES_OPTION;
            }
        }
        );
        menu.add(menuItem);
        menuItem = new JMenuItem("Min Conjugation Concentration");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(Bacteria.conjugationOnConcentration){
                 String sgen = JOptionPane.showInputDialog("Level for Doning(actual:" + Constants.minConjugationConcentration + " max:" + Celda.concentrationMax + ")");
                 Constants.minConjugationConcentration = Float.parseFloat(sgen);
                }
                else{
                  JOptionPane.showMessageDialog(null, "The conjugation on concentration is not active", "Conjugation On Concentration", JOptionPane.INFORMATION_MESSAGE);
                }
                }
            }
        );
        menu.add(menuItem);
        return menu;
    }

    private static JMenu menuAmbiente(final MiDibujo m) {
        JMenu menu;
        menu = new JMenu("Enviroment");
        menu.setMnemonic('e');
        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem("Neutral", true);
        m.lienzo.setAmbienteNeutral(menuItem);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                m.lienzo.getAmbienteRandom().setSelected(false);
            }
        });
        menu.add(menuItem);
        menuItem = new JRadioButtonMenuItem("Random", false);
        m.lienzo.setAmbienteRandom(menuItem);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                m.lienzo.getAmbienteNeutral().setSelected(false);
            }
        });
        menu.add(menuItem);

        return menu;
    }

    private static JMenu menuView(final MiDibujo m) {
        JMenu menu;
        menu = new JMenu("View");
        menu.setMnemonic('v');
        JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem("Bacteria Layer", true);
        m.lienzo.setBacteriaLayer(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                m.repintar();
            }
        });
        menu.add(menuItem);
        menuItem = new JCheckBoxMenuItem("Concentration Layer", false);
        m.lienzo.setConcentrationLayer(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                m.repintar();
            }
        });
        menu.add(menuItem);
        menuItem = new JCheckBoxMenuItem("Bacteria Concentration Layer", false);
        m.lienzo.setBacteriaConcentrationLayer(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                m.repintar();
            }
        });
//        menu.add(menuItem);
        menuItem = new JCheckBoxMenuItem("Show bact Concentration", false);
        m.lienzo.setShowConcentrationNumber(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
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
                JOptionPane.showMessageDialog(null, "Development version, By: \n Stifen Panche \n y \n Leonardo Quiñonez", "About..", JOptionPane.INFORMATION_MESSAGE);
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
