/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hexatron;

import Logica_Hexatron.Hexatron;
import java.awt.Component;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import javax.swing.JFrame;

/**
 *
 * @author Ztiphen
 */
public class MiDibujo extends JFrame{
    
    Hexatron hexatron = new Hexatron();
    Lienzo lienzo = new Lienzo(hexatron.getMatriz());
    
    public void dibujar()
    {
    this.add(lienzo);
    }

  
    public static void main(String args[]) {
    int screenx =  Toolkit.getDefaultToolkit().getScreenSize().width;
    int screeny = Toolkit.getDefaultToolkit().getScreenSize().height;
    MiDibujo midibujo = new MiDibujo();
    midibujo.dibujar();
    midibujo.setBounds(0, 0,screenx,screeny-50);
    midibujo.setVisible(true);
    midibujo.setDefaultCloseOperation(EXIT_ON_CLOSE);
    MenuBar menubar= new MenuBar();
    menubar.add(new Menu(""));
    
    midibujo.setMenuBar(menubar);
    }

}
