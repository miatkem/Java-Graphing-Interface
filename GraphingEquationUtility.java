import java.awt.Point;
import java.util.*;

public class GraphingEquationUtility
{
    private String expression;
    private double slope;
    private double constant;
    private double quadradicCoefficient;
    private double pointDensity;
    private double[][] points;
    private ArrayList<Double> coefficients;
    private ArrayList<Double> exponents;
    
    public GraphingEquationUtility(String expression)
    {
        this.expression=expression;
        points = new double[200][2];
        coefficients = new ArrayList<Double>();
        exponents = new ArrayList<Double>();
        slope=0.0;
        constant=0.0;
        pointDensity=1.0;
        quadradicCoefficient=0.0;
        cleanExpression();
        fillTable();
    }
    
    public GraphingEquationUtility()
    {
        expression="";
        coefficients = new ArrayList<Double>();
        exponents = new ArrayList<Double>();
        points = new double[200][2];
        slope=0.0;
        constant=0.0;
        pointDensity=1.0;
        quadradicCoefficient=0.0;
    }
    
    /*EVALUATE the expression at an X value*/
    public double getPointAt(double x)
    {
        double yPoint=0.0;
        for(int i = 0; i<coefficients.size(); i++)
        {
            yPoint+=coefficients.get(i)*Math.pow(x,exponents.get(i));
        }
        
        yPoint+=(slope*x)+constant;
        return yPoint;
    }
    
    /*RETURN points - (for graph window)*/
    public double[][] getTable()
    {
        return points;
    }
    
    /*UPDATE point density - (detail slider)*/
    public void setPointDensity(double pointDensity)
    {
        this.pointDensity=pointDensity;
        fillTable();
    }
    
    /*UPDATE expression*/
    public void setExpression(String expression)
    {
        this.expression=expression;
        cleanExpression();
        fillTable();
    }
    
    /*Uses getPointAt() to obtain each of the points and fills a 2d array with the information*/
    public void fillTable()
    {
        double amountOfPoint = 100.0*pointDensity;
        points = new double[(int)(200.0*pointDensity)][2];
        for(int i =0; i < points.length; i++)
        {
            double x = (i-amountOfPoint)/pointDensity;
            points[i][0]=x;
            points[i][1]=getPointAt(x);
        }
    }
    
    /*Extracts all needed information from the String expression*/
    public void cleanExpression()
    {
        /*Declare Variavble and instantiate new lists*/
        String strSlope="";
        String strConstant="";
        coefficients = new ArrayList<Double>();
        exponents = new ArrayList<Double>();
        
        /*Remove the 'y=' out of the function if it exists*/
        if(expression.indexOf("=") < 2 && expression.indexOf("=") > -1)
            expression=expression.substring(2,expression.length());
        //2x^2.5+2x+2
        
        /*POLYNOMIAL PORTION*/
        /*while there is still x with an exponent*/
        while(expression.indexOf("x")>=0)
        {
            if(expression.indexOf("x")==0||expression.indexOf("+x")==0)/*If coefficient doesn't exist slope is 1*/
                coefficients.add(1.0);
            else if(expression.indexOf("-x")==0)/*If coefficient doesn't exist and x is - slope is -1*/
                coefficients.add(-1.0);
            else /*Double before x is the coefficient*/
                coefficients.add(Double.parseDouble(expression.substring(0,expression.indexOf("x"))));
            if(expression.indexOf("x") < expression.length()-1 && expression.charAt(expression.indexOf("x")+1) == '^') /*If the exponent is in () then take the value with in them*/
            {
                int i=1;
                while(expression.indexOf("^")+i!=expression.length() && expression.charAt(expression.indexOf("^")+i) != '-' && expression.charAt(expression.indexOf("^")+i)!='+')
                    i++;
                
                exponents.add(Double.parseDouble(expression.substring(expression.indexOf("^")+1,expression.indexOf("^")+i)));
                expression=expression.substring(expression.indexOf("^")+i);
            }
            else/*Else exponent is the first character after the ^*/
            {
                exponents.add(1.0);
                expression=expression.substring(expression.indexOf("x")+1);
            }
        }
        /*LINEAR PORTION--------Find the slope*/
        if(expression.indexOf("x")<0) /*If x doesn't exist slope is 0*/
            strSlope="0";
        else  /*If x exist find string version slope*/
            strSlope=expression.substring(0,expression.indexOf("x"));
        
        /*Convert String version of slope into Double*/
        if(strSlope.equals(""))
            slope=1;
        else if(strSlope.equals("-"))
            slope=-1;
        else
            slope= Double.parseDouble(strSlope);
        
        /*Find the b/Constant*/
        if(expression.indexOf("x")==expression.length()-1)/*If nothing is after x constant is 0*/
            strConstant="0";
        else if(expression.indexOf("x")<0)/*If x doesn't exist constant is the whole expression*/
            strConstant=expression;
        else/*If x exists take substring after x*/
            strConstant=expression.substring(expression.indexOf("x")+1,expression.length());
        /*Convert String version of constant into Double*/
        constant= Double.parseDouble(strConstant);
    }
    
    /*Creates a table of values*/
    public String toString()
    {
        String result="";
        result+= "Slope: " + slope + "    Constant: " + constant;
        for(int i = 0; i < points.length; i++)
        {
            result+="\n" + "X: " + points[i][0] + "\t" + "Y: " + points[i][1];
        }
        
        return result;
    }
    
}