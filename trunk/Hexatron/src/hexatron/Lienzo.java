/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hexatron;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;

/**
 * Clase que hereda de Canvas y sirve para dibujar una linea.
 */
public class Lienzo extends Canvas
{
     int xPoints[]={5,10,10,5,0,0};
       int yPoints[]={0,5,10,15,10,5};
       int Matriz[][];
       int cont =0;
  
    /**
     * Constructor. Hace que el tamaño del canvas sea 100x100 pixels.
     */
    public Lienzo(int matriz[][])
    {
        
        this.Matriz=matriz;
        
        
    }
    
   
    
    
    /**
     * Dibuja la última línea que se le haya pasado.
     */
  
    public void paint(Graphics g)
    {
        
       
         g.setColor(Color.green);
         update(g);
          
        
       
      //g.drawLine(23, 3, 56, 5);
    }
    
    public void update(Graphics g)
    {
        
        
             
      for(int i =0; i<Matriz.length; i++)
      {
          for(int j =0; j<Matriz.length; j++)
          {
              
              if(Matriz[i][j]==1)
              {
                  g.setColor(Color.green);
              g.drawPolygon(xPoints, yPoints, 6);}
              else if(Matriz[i][j]==2)
              {   g.setColor(Color.yellow);
                  g.fillPolygon(xPoints, yPoints, 6);}
              else if(Matriz[i][j]==3)
              {    g.setColor(Color.red);
                  g.fillPolygon(xPoints, yPoints, 6);}
               else if(Matriz[i][j]==4)
              {    g.setColor(Color.blue);
                  g.fillPolygon(xPoints, yPoints, 6);}
              
              
              
              for(int k =0; k<6; k++)
              xPoints[k]=xPoints[k]+12;
              
             
             
          }
           for(int k =0; k<6; k++)
          yPoints[k]=yPoints[k]+12;
          cont++;
          if(cont%2==0)
          {
          xPoints[0]=5;
          xPoints[1]=10;
          xPoints[2]=10;
          xPoints[3]=5;
          xPoints[4]=0;
          xPoints[5]=0;
          }
 else{
        xPoints[0]=12;
          xPoints[1]=17;
          xPoints[2]=17;
          xPoints[3]=12;
          xPoints[4]=7;
          xPoints[5]=7;       
 }
                   
      }
      
          yPoints[0] = 0;
          yPoints[1]=5;
          yPoints[2]=10;
          yPoints[3]=15;
          yPoints[4]=10;
          yPoints[5]=5;
 
    }
    
    /**
     * Guarda la línea que se le pasa para dibujarla cuando se le indique
     * llamando a paint()
     */
}

