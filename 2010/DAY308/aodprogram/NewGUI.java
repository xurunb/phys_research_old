package aodprogram;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import zhstructures.*;
import javax.swing.event.*;
import objectdraw.*;

public class NewGUI extends JFrame implements ActionListener {
  private JButton selectWB, generateWB, calculateB, calculatePLUSB;
  private JPanel panel1, panel2, panel3, panel;
  private JTextField outF;
  private JTextArea inF, waveF;
  private JScrollPane jsp1, jsp2;
  private JCheckBox wmoW, otherW;
  private JLabel outL, wL, inL;
  private Container thisFrame;
  private ArrayList<String> fileNames;
  private ArrayList<String> wmoList;
  private PrintWriter pt;
  
  public NewGUI(){
    panel=new JPanel();
    panel1=new JPanel();
    panel2=new JPanel();
    panel3=new JPanel();
    panel.setLayout(new GridLayout(3,1));
    panel1.setLayout(new GridLayout(1,4));
    panel2.setLayout(new GridLayout(3,2));
    panel3.setLayout(new GridLayout(1,2));
    
    selectWB=new JButton("Separate Wavelength");
    selectWB.addActionListener(this);
    generateWB=new JButton("Generate Wavelength");
    generateWB.addActionListener(this);
    calculateB=new JButton("Calculate AOD");
    calculateB.addActionListener(this);
    calculatePLUSB=new JButton("Calculate AOD PLUS");
    calculatePLUSB.addActionListener(this);
    
    panel1.add(selectWB);
    panel1.add(generateWB);
    panel1.add(calculateB);
    panel1.add(calculatePLUSB);
    
    outL=new JLabel("Output File Name:");
    wL=new JLabel("Select Wavelength:");
    outF=new JTextField();
    wmoW=new JCheckBox("WMO Wavelength");
    wmoW.addActionListener(this);
    wmoW.setSelected(true);
    inL=new JLabel("Input File Name:");
    otherW=new JCheckBox("Other Wavelength");
    otherW.addActionListener(this);
    
    panel2.add(outL);
    panel2.add(wL);
    panel2.add(outF);
    panel2.add(wmoW);
    panel2.add(inL);
    panel2.add(otherW);
    
    panel.add(panel1);
    panel.add(panel2);
    
    inF=new JTextArea();
    waveF=new JTextArea();
    jsp1=new JScrollPane(inF);
    jsp2=new JScrollPane(waveF);
    
    panel3.add(jsp1);
    panel3.add(jsp2);
    
    thisFrame=getContentPane();
    thisFrame.add(panel, BorderLayout.NORTH);
    thisFrame.add(panel3, BorderLayout.CENTER);
    thisFrame.validate();
    setVisible(true);
    setSize(1000, 800);
    
    wmoList=new ArrayList<String>();
    wmoList.add("368.00");
    wmoList.add("412.00");
    wmoList.add("500.00");
    wmoList.add("610.00");
    wmoList.add("675.00");
    wmoList.add("778.00");
    wmoList.add("862.00");
  }
  
  public void actionPerformed(ActionEvent evt){
    Object source=evt.getSource();
    if(source==selectWB){
      String temp=inF.getText();
      temp=temp.replaceAll("\n", " ");
      int n=0;
      fileNames=new ArrayList<String>();
      while(n<temp.length()){
        fileNames.add(temp.substring(n, n+17));
        n=n+18;
      }
      int size=fileNames.size();
      FileReader reader[] =new FileReader[size];
      int j=0;
      for(String fileN: fileNames){
        reader[j]=new FileReader(fileN);
        j++;
      }
      ZHLinkedBinarySearchTree<String> waveList=new ZHLinkedBinarySearchTree<String>();
      if(!wmoW.isSelected() && !otherW.isSelected()){
        wmoW.setSelected(true); 
      }
      if(wmoW.isSelected()){
        for(String w: wmoList){
          waveList.add(w);
        }
      }
      if(otherW.isSelected()){
        String t=waveF.getText();
        t=t.replaceAll("\n", " ");
        Scanner sc=new Scanner(t);
        while(sc.hasNext()){
          waveList.add(sc.next()); 
        }
        sc.close();
      }
      String add=outF.getText();
      for(int i=0; i< size; i++){
        try{
          pt=new PrintWriter(add+"S"+reader[i].getName());
        }
        catch(Exception e){
          System.out.println("Error in printer!"); 
        }
        pt.printf("%s\n", reader[i].getFirstLine());
        pt.printf("%s\n", reader[i].getSecondLine());
        for(String s: waveList){
          pt.printf("%7s  %s\n", s, reader[i].getResult(s));
        }
        pt.close();
      }
    }
    
    else if(source==generateWB){
      String outputFile=outF.getText();
      String temp=inF.getText();
      temp=temp.replaceAll("\n", " ");
      int n=0;
      fileNames=new ArrayList<String>();
      while(n<temp.length()){
        fileNames.add(temp.substring(n, n+17));
        n=n+18;
      }
      int size=fileNames.size();
      FileReader reader[] =new FileReader[size];
      int j=0;
      for(String fileN: fileNames){
        reader[j]=new FileReader(fileN);
        j++;
      }
      ZHTreeMap<String, ZHCompPair<String, String>> set =new ZHTreeMap<String, ZHCompPair<String, String>>();
      ZHLinkedBinarySearchTree<String> waveList=new ZHLinkedBinarySearchTree<String>();
      if(!wmoW.isSelected() && !otherW.isSelected()){
        wmoW.setSelected(true); 
      }
      if(wmoW.isSelected()){
        for(String w: wmoList){
          waveList.add(w);
        }
      }
      if(otherW.isSelected()){
        String t=waveF.getText();
        t=t.replaceAll("\n", " ");
        Scanner sc=new Scanner(t);
        while(sc.hasNext()){
          waveList.add(sc.next()); 
        }
        sc.close();
      }
      for(String w:waveList){
        for(int i=0; i< size; i++){
          set.put(w, new ZHCompPair<String, String>(reader[i].getTime(), reader[i].getResult(w)));
        }
      }
      try{
        pt=new PrintWriter (outputFile);
      }
      catch(Exception e){
        System.out.println("Error in printer");
      }
      for(String w:waveList){
        pt.printf("%s \n", w);
        for(int i=0; i<size;i++){
          pt.printf("   %s \n", reader[i].getTime());
        }
        for(int i=0; i<size;i++){
          pt.printf("   %s \n", reader[i].getResult(w));
        }
      }
      pt.close(); 
    }
    else if(source==calculateB){
      double d=1.016; //PopUpInput.getDouble("Please give the earth to sun distance in AU.");
      //double alpha=PopUpInput.getDouble("Please give the absorption coefficient of the absorbing species.");
      //double x=PopUpInput.getDouble("Please give the total amount of absorber in vertical column at STP");
      //double mu=PopUpInput.getDouble("Please give the ratio of the actual and vertical paths through the absorbing layer");
      
      //double m=PopUpInput.getDouble("Please give the optical path length alowing for refraction");
      double p=PopUpInput.getDouble("Please give the station pressure(in atm)");
      double p0= 1;
      String outputFile=outF.getText();
      String temp=inF.getText();
      temp=temp.replaceAll("\n", " ");
      int n=0;
      fileNames=new ArrayList<String>();
      while(n<temp.length()){
        fileNames.add(temp.substring(n, n+17));
        n=n+18;
      }
      int size=fileNames.size();
      FileReader reader[] =new FileReader[size];
      int j=0;
      for(String fileN: fileNames){
        reader[j]=new FileReader(fileN);
        j++;
      }
      ZHLinkedBinarySearchTree<String> waveList=new ZHLinkedBinarySearchTree<String>();
      if(!wmoW.isSelected() && !otherW.isSelected()){
        wmoW.setSelected(true); 
      }
      if(wmoW.isSelected()){
        for(String w: wmoList){
          waveList.add(w);
        }
      }
      if(otherW.isSelected()){
        String t=waveF.getText();
        t=t.replaceAll("\n", " ");
        Scanner sc=new Scanner(t);
        while(sc.hasNext()){
          waveList.add(sc.next()); 
        }
        sc.close();
      }
      String add=outF.getText();
      ZHTreeMap<String, ZHCompPair<Double, Double>> set=new ZHTreeMap<String, ZHCompPair<Double, Double>>();
      for(String w:waveList){
        set.put(w, new ZHCompPair<Double, Double>(PopUpInput.getDouble("Please give the AM0 for "+w+"."), 
                                                  PopUpInput.getDouble("Please give the molecular scattering coefficient for the atmosphere for "+w+".")));
      }
      for(int i=0; i<size;i++){
        try{
          pt=new PrintWriter(add+"C"+reader[i].getName());
        }
        catch(Exception e){
          System.out.println("Error in printer!"); 
        }
        reader[i].getTime();
        double a=(reader[i].getDayN()+10.0)/365.0;
        System.out.println(reader[i].getH());
        double b=-23.4*Math.cos(360.0*a/180.0*Math.PI);
        System.out.println("b: "+b);
        System.out.println("a: "+a);
        double mp=1.0/(Math.sin(45.58/180.0*Math.PI)*Math.sin(b/180.0*Math.PI)+Math.cos(45.58/180.0*Math.PI)*
                       Math.cos(b/180.0*Math.PI)*Math.cos(15*(12-reader[i].getH())/180.0*Math.PI));
        System.out.println(mp);
        for(String w: waveList){
          double l=java.lang.Double.parseDouble(reader[i].getResult(w));
          double l0=set.get(w).getKey();
          double beta=set.get(w).getValue();
          IrradianceCalculator c=new IrradianceCalculator(l, l0, d, beta, p, p0, mp);
          double delta=c.getAOD();
          pt.printf("%7s  %s\n", w, ""+delta);
        }
        pt.close();
      }
    }
    else if(source==calculatePLUSB){
      double p=PopUpInput.getDouble("Please give the station pressure(in atm)");
      String temp=inF.getText();
      temp=temp.replaceAll("\n", " ");
      int n=0;
      fileNames=new ArrayList<String>();
      while(n<temp.length()){
        fileNames.add(temp.substring(n, n+17));
        n=n+18;
      }
      String t=waveF.getText();
      t=t.replaceAll("\n", " ");
      t=t.replaceAll(",", " ");
      Scanner sc;
      sc=new Scanner(t); 
      ArrayList<Double> rangeList=new ArrayList<Double>();
      while(sc.hasNext()){
        String b=sc.next();
        double a=java.lang.Double.parseDouble(b);
        rangeList.add(a);
      }
      sc.close();
      int size=rangeList.size();
      for(String f:fileNames){
        int i=0;
        while(i<size){
         CalculatorController cc=new CalculatorController("/net/home/f10/r1xu/JavaPackages/aodprogram/Beta", "/net/home/f10/r1xu/JavaPackages/aodprogram/AM0", f, p, rangeList.get(i), rangeList.get(i+1));
         i=i+2;
        }
      }
    }
  }
  
  public static void main(String args[]){
    new NewGUI(); 
  }
  
}