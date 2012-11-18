package aodprogram;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import zhstructures.*;
import javax.swing.event.*;

public class CalculatorController{
  private String betaFN, am0FN;
  private Scanner sc;
  private ZHTreeMap<Double, Double> map;
  private PrintWriter pt;
  private DataInputer diBeta, diAM0;
  private ArrayList<Double> set;
  private String outputF;
  
  public CalculatorController(String betaFN, String am0FN, String inputF, double p, double start, double end){
    set=new ArrayList<Double>();
    diBeta=new DataInputer(betaFN);
    diAM0=new DataInputer(am0FN);
    double p0= 1;
    double d=1.016;
    String temp= inputF;
    String year=temp.substring(0, 4);
    int month=java.lang.Integer.parseInt(temp.substring(5,7));
    int day=java.lang.Integer.parseInt(temp.substring(8,10));
    int dayN = 0;
    if(month==5){// to calculate the day number of the day in May
      dayN=120+day;
    }
    else if(month==6){// to calculate the day number of the day in June
      dayN=151+day;
    }
    else if (month==7){// to calculate the day number of the day in July
      dayN=181+day;
    }
    else if( month==8){// to calculate the day number of the day in August
      dayN= 212+day;
    }
    int hour=java.lang.Integer.parseInt(temp.substring(11,13));
    int minute=java.lang.Integer.parseInt(temp.substring(13,15));
    int m = minute*100/60;
    double h=hour+m/100.00;
    double a=(dayN+10.0)/365.0;
    double b=-23.4*Math.cos(360.0*a/180.0*Math.PI);
    double mp=1.0/(Math.sin(45.58/180.0*Math.PI)*Math.sin(b/180.0*Math.PI)+Math.cos(45.58/180.0*Math.PI)*
                   Math.cos(b/180.0*Math.PI)*Math.cos(15*(12-h)/180.0*Math.PI));
    try{
      sc=new Scanner(new File(inputF));
    }
    catch(Exception e){
      System.out.println("Error in scanner"); 
    }
    sc.nextLine();
    sc.nextLine();
    while(sc.hasNext()){
      String temp1=sc.next();
      if(temp1.length()>3){
        String temp2=sc.next();
        double wv=java.lang.Double.parseDouble(temp1);
        double l=java.lang.Double.parseDouble(temp2);
        if(wv>= start && wv<=end){
          double beta=diBeta.calculate(wv);
          double l0=diAM0.calculate(wv);
          IrradianceCalculator c=new IrradianceCalculator(l, l0, d, beta, p, p0, mp);
          double delta=c.getAOD();
          map.put(wv, delta);
          set.add(wv);
        }
      }
    }
    sc.close();
    outputF="C"+inputF.substring(0, 15)+start+end;
    try{
      pt=new PrintWriter(outputF); 
    }
    catch(Exception e){
      System.out.println("Error in printer!"); 
    }
    for (Double w: set){
      pt.printf("%7s,  %s\n", ""+w, ""+map.get(w));
    }
    pt.close();
  }
  
  
}