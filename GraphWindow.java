import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D.Double;

public class GraphWindow extends JPanel
{
    double[][] points;
    int scale;
    final int STROKE=2;
    
    public GraphWindow(double[][] points)
    {
        this.points = points;
        scale = 10;
    }
    
    /*UPDATES zoom*/
    public void setScale(int newScale)
    {
        scale = newScale;
    }
    
    /*UPDATES points that are plotted*/
    public void setPoints(double[][] points)
    {
        this.points = points;
    }
    
    /*REPAINT window*/
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.clearRect(0, 0, 500, 500); //clear window
        paintGraph(g2); //paint new graph
    }
    
    /*PAINT graph*/
    public void paintGraph(Graphics2D g2)
    {
        g2.draw(new Rectangle2D.Double(20.0, 20.0, 460.0, 460.0)); //draw box
        g2.draw(new Line2D.Double(250.0,20.0,250.0,480.0)); //draw y-axis
        g2.draw(new Line2D.Double(20.0,250.0,480.0,250.0)); //draw x-axis
        
        /*DRAW gridlines*/
        for(int i=0; i < 200; i++)
        {
            g2.draw(new Line2D.Double(i*scale+250,253.0,i*scale+250,247.0));
            g2.draw(new Line2D.Double(250-i*scale,253.0,250-i*scale,247.0));
            g2.draw(new Line2D.Double(253.0,250-i*scale,247.0,250-i*scale));
            g2.draw(new Line2D.Double(253.0,250+i*scale,247.0,250+i*scale));
        }
        
        int xStand=250; //center reference
        int yStand=250; //center reference
        double newX=0;
        double newY=0;
        double oldX=0;
        double oldY=0;
        
        /*DRAW lines from each point connecting them
         *   -gives the illusion of a curve if points are plotted with vary small x value variations*/
        for(int i=0; i< points.length; i++)
        {
            newX = xStand+(points[i][0]*scale);
            newY = yStand-(points[i][1]*scale);
            
            g2.fill(new Ellipse2D.Double(newX-1,newY-1, 2, 2)); //draw small ciricle on point
            
            if(!(i==0)) //if not first point - draw line
            {
                g2.setPaint(Color.RED);
                g2.setStroke(new BasicStroke(STROKE));
                g2.draw(new Line2D.Double(newX,newY,oldX,oldY));
                g2.setStroke(new BasicStroke(1));
                g2.setPaint(Color.BLACK);
                
            }
            
            //fix edges of graph
            g2.clearRect(0, 0, 500, 20);
            g2.clearRect(0, 0, 20, 500);
            g2.clearRect(481, 0, 500, 500);
            g2.clearRect(0, 481, 500,20);
            oldX=newX;
            oldY=newY;
        }
    }
}