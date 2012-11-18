package aodprogram;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import zhstructures.*;
/*
 * version 1.2 June 20, 2011
 */

public class DataInputer{
  private Scanner sc;
  //private ZHLinkedBinarySearchTree<ZHCompPair<Double, Double>> tree;
  private ArrayList<ZHCompPair<Double, Double>> list;
  
  public DataInputer(String fileName){
    //tree=new ZHLinkedBinarySearchTree<ZHCompPair<Double, Double>>();
    list=new ArrayList<ZHCompPair<Double, Double>>();
    System.out.println(fileName);
    try{
      sc=new Scanner(new File(fileName)); 
    }
    catch(Exception e){
      System.out.println("Error in scanner!"); 
    }
    double wv;
    double data;
    while(sc.hasNext()){
      wv=java.lang.Double.parseDouble(sc.next());
      data=java.lang.Double.parseDouble(sc.next());
      //tree.add(new ZHCompPair<Double, Double>(wv, data));
      list.add(new ZHCompPair<Double, Double>(wv, data));
    }
    sc.close();
  }
  
  public double calculate(double w){
    //ZHCompPair<Double, Double>temp=new ZHCompPair<Double, Double>(w, -1.0);
    boolean equal=false;
    double result;
    int i=0;
    int size=list.size();
    while(i<size && w>=list.get(i).getKey()){
      if(w==list.get(i).getKey()){
        equal=true;
      }
      i++;
    }
    if(!equal){
      double ls=list.get(i).getKey();    //larger smallest
      double sl=list.get(i-1).getKey();      //smaller largest 
      double lsD=list.get(i).getValue(); //larger smallest data
      double slD=list.get(i-1).getValue();   //smaller largest data
      result=lsD*(w-sl)/(ls-sl)+slD*(ls-w)/(ls-sl);
    }
    else{
      result=list.get(i-1).getValue(); 
    }
    return result;
  }
}