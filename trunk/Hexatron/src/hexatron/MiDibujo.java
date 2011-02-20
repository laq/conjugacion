/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hexatron;

import Logica_Hexatron.Hexatron;
import java.awt.Component;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Ztiphen
 */
public class MiDibujo extends JFrame{

    private Hexatron hexatron = new Hexatron();
    Lienzo lienzo = new Lienzo(getHexatron());

    public void dibujar()
    {
    this.add(lienzo);
    }
    public void repintar(){

        lienzo.setHexatron(hexatron);
        lienzo.repaint();
    }

    public static void main(String args[]) {
    int screenx =  Toolkit.getDefaultToolkit().getScreenSize().width;
    int screeny = Toolkit.getDefaultToolkit().getScreenSize().height;

    final MiDibujo midibujo = new MiDibujo();
      MenuBar menubar= new MenuBar();
    Menu menu=new Menu("automata");
    MenuItem menuItem=new MenuItem("Ancho", new MenuShortcut(KeyEvent.VK_A));
    menuItem.addActionListener(new ActionListener() {            
            public void actionPerformed(ActionEvent e) {
                String sanch=JOptionPane.showInputDialog("# numero de celulas a lo ancho");
                int ancho=Integer.parseInt(sanch);
                 String salto=JOptionPane.showInputDialog("# numero de celulas a lo alto");
                int alto=Integer.parseInt(salto);
                midibujo.getHexatron().setAlto(alto);
                midibujo.getHexatron().setAncho(ancho);
                midibujo.getHexatron().poblar();
                midibujo.repintar();
                midibujo.repaint();
            }
        });
    menu.add(menuItem);
    menubar.add(menu);
    midibujo.setMenuBar(menubar);
    midibujo.dibujar();
    midibujo.setBounds(0, 0,screenx,screeny-50);
    midibujo.setVisible(true);
    midibujo.setDefaultCloseOperation(EXIT_ON_CLOSE);
  
    }

    /**
     * @return the hexatron
     */
    public Hexatron getHexatron() {
        return hexatron;
    }

}
