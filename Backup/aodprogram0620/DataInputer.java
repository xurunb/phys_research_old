package aodprogram;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import zhstructures.*;

public class DataInputer{
  private Scanner sc;
  private ZHLinkedBinarySearchTree<ZHCompPair<Double, Double>> tree;
  
  public DataInputer(String fileName){
    tree=new ZHLinkedBinarySearchTree<ZHCompPair<Double, Double>>();
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
      tree.add(new ZHCompPair<Double, Double>(wv, data));
    }
    sc.close();
  }
  
  public double calculate(double w){
    ZHCompPair<Double, Double>temp=new ZHCompPair<Double, Double>(w, -1.0);
    boolean b=tree.add(temp);
    double result;
    System.out.println(b);
    if(b){
      double ls=tree.rightSmallest(temp).getKey();    //larger smallest
      double sl=tree.leftLargest(temp).getKey();      //smaller largest 
      double lsD=tree.rightSmallest(temp).getValue(); //larger smallest data
      double slD=tree.leftLargest(temp).getValue();   //smaller largest data
      result=lsD*(w-sl)/(ls-sl)+slD*(ls-w)/(ls-sl);
      tree.remove(temp);
    }
    else{
      result=tree.get(temp).getValue(); 
    }
    return result;
  }
}