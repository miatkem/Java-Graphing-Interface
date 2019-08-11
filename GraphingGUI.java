import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.BorderFactory;
import java.awt.geom.Ellipse2D;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GraphingGUI extends JPanel implements ActionListener, ChangeListener
{
    /*BUTTONS AND LABELS*/
    private JButton graph;
    private JTextField equation;
    private JLabel equationLabel;
    private JLabel scale;
    private JLabel detail;
    private JSlider scaleSlider;
    private JSlider detailSlider;
    /*OBJECTS*/
    private GraphWindow gWin;
    private GraphingEquationUtility graphingUtil;
    
    public static void main(String[] args)
    {
        new GraphingGUI();
    }
    
    public GraphingGUI()
    {
        /*INITIALIZE WINDOW (JFRAME)*/
        JFrame window = new JFrame("Graphing Interface");
        window.setBackground(new Color(255,0,0));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Graphing Interface");
        window.setSize(500,600);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        
        /*INITIALIZE GRAPHING UTILITY OBJECT*/
        graphingUtil=new GraphingEquationUtility();
        
        /*INITIALIZE JPANELS*/
        JPanel userInterface = new JPanel();
        userInterface.setLayout(new BorderLayout(20,10));
        
        /*VIEWING PANEL*/
        JPanel graphDisplay = new JPanel();
        graphDisplay.setLayout(new GridLayout(1,1));
        gWin = new GraphWindow(graphingUtil.getTable());
        graphDisplay.add(gWin);
        
        /*BOTTOM BAR*/
        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new BorderLayout(20,10));
        JPanel sliderBar = new JPanel();
        JLabel scale = new JLabel("Zoom:");
        sliderBar.add(scale);
        scaleSlider = new JSlider(JSlider.HORIZONTAL,3, 200, 10);
        scaleSlider.setName("scaleSlider");
        
        scaleSlider.addChangeListener(this); //zoom slider
        scaleSlider.setValue(100);
        sliderBar.add(scaleSlider);
        JLabel detail = new JLabel("Detail:");
        sliderBar.add(detail);
        detailSlider = new JSlider(JSlider.HORIZONTAL,1, 100, 1);
        detailSlider.setName("detailSlider");
        
        detailSlider.addChangeListener(this); //detail slider
        detailSlider.setValue(50);
        sliderBar.add(detailSlider);
        bottomBar.add(sliderBar, BorderLayout.SOUTH);
        JPanel equationBar = new JPanel();
        equationLabel = new JLabel("Polynomial Equation:");
        equationBar.add(equationLabel);
        equation = new JTextField(10); //equation textfield
        equation.setText(".5x^3-x+.23");
        equationBar.add(equation);
        graph = new JButton("Graph!"); //graph button
        graph.addActionListener(this);
        graph.doClick();
        equationBar.add(graph);
        bottomBar.add(equationBar, BorderLayout.CENTER);
        
        userInterface.add(graphDisplay, BorderLayout.CENTER);
        userInterface.add(bottomBar, BorderLayout.SOUTH);
        
        /*SET WINDOW VISIBLE*/
        window.add(userInterface);
        window.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        /*GRAPH BUTTON LISTENING*/
        if(e.getSource()==graph)
        {
            graphingUtil.setExpression(equation.getText()); //create new equation
            gWin.setPoints(graphingUtil.getTable()); //update points used by the graph window
            gWin.repaint(); //repaint the window
        }
    }
    
    public void stateChanged(ChangeEvent e)
    {
        /*SLIDERS*/
        JSlider source = (JSlider)e.getSource();
        if(!source.getValueIsAdjusting())
        {
            if(source.getName().equals("scaleSlider")) //Changes the zoom of the graph window
            {
                gWin.setScale((int)source.getValue());
                gWin.repaint();
            }
            
            if(source.getName().equals("detailSlider")) //Changes the amount of points plotted between each x value
            {
                graphingUtil.setPointDensity(source.getValue()/4.0);
                gWin.setPoints(graphingUtil.getTable());
                gWin.repaint();
            }
        }
    }
}