package aodprogram;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import zhstructures.*;
import javax.swing.event.*;
import objectdraw.*;

/*
 * file: GUI
 * @author Xu,Runbo
 * @version 1.1
 * @date May 31, 2011
 */

/*
 * This is a class to create a graphical user interface to read couple .IRR files and write selected wavelengths and related values to a new file
 */
public class GUI extends JFrame implements ActionListener {
  private JPanel panel;
  private JButton startB, exitB;
  private JLabel outL, inL;
  private JTextField outF;
  private JTextArea inF;
  private JScrollPane jsp;
  private Container thisFrame;
  private ArrayList<String> fileNames;
  private PrintWriter pt;
  
  public GUI(){
    panel=new JPanel();
    panel.setLayout(new GridLayout(3,2));
    startB=new JButton("Start");
    startB.addActionListener(this);
    exitB=new JButton("Exit");
    exitB.addActionListener(this);
    outL=new JLabel("Output Name");
    inL=new JLabel("Input Files");
    outF=new JTextField();
    inF=new JTextArea();
    jsp = new JScrollPane(inF);
    
    panel.add(startB);
    panel.add(exitB);
    panel.add(outL);
    panel.add(outF);
    panel.add(inL);
    
    thisFrame=getContentPane();
    thisFrame.add(panel, BorderLayout.NORTH);
    thisFrame.add(jsp, BorderLayout.CENTER);
    thisFrame.validate();
    setVisible(true);
    setSize(900, 800);
  }
  
  public void actionPerformed(ActionEvent a){
    Object source=a.getSource();
    if(source==exitB){
      System.exit(0);
    }
    else if(source==startB){
      String outputFile=outF.getText();
      String temp=inF.getText();
      temp=temp.replaceAll("\n", " ");
      int n=0;
      fileNames=new ArrayList<String>();
      while(n<temp.length()){
        fileNames.add(temp.substring(n, n+19));
        n=n+20;
      }
      int size=fileNames.size();
      FileReader reader[] =new FileReader[size];
      int j=0;
      for(String fileN: fileNames){
        reader[j]=new FileReader(fileN);
        j++;
      }
      ZHTreeMap<String, ZHCompPair<String, String>> set =new ZHTreeMap<String, ZHCompPair<String, String>>();
      ArrayList<String> wList=new ArrayList<String>();
      wList.add("368.00");
      wList.add("412.00");
      wList.add("500.00");
      wList.add("610.00");
      wList.add("675.00");
      wList.add("778.00");
      wList.add("862.00");
      for(String w:wList){
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
      for(String w:wList){
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
  }
  
  public static void main(String args[]){
   new GUI(); 
  }
}