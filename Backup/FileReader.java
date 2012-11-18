import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import zhstructures.*;

public class FileReader {
  private Scanner sc;
  private String time;
  public ZHTreeMap<String, String> tree;
  public PrintWriter pt;
  
  public FileReader(String fileName){
    try{
      sc=new Scanner(new File(fileName));
    }
    catch (Exception e){
      System.out.println("error in reading file"); 
    }
    String line;
    line=sc.nextLine();
    time=line.substring(8,23);
    String temp= time;
    temp.replaceAll("-", "");
    String year=temp.substring(0, 4);
    int month=java.lang.Integer.parseInt(temp.substring(5,7));
    int day=java.lang.Integer.parseInt(temp.substring(8,10));
    int dayN = 0;
    if(month==5){
      dayN=120+day;
    }
    else if(month==6){
      dayN=151+day;
    }
    else if (month==7){
      dayN=181+day;
    }
    else if( month==8){
      dayN= 212+day;
    }
    int hour=java.lang.Integer.parseInt(temp.substring(11,13));
    int minute=java.lang.Integer.parseInt(temp.substring(13,15));
    int m = minute*100/60;
    double h=hour+m/100.00;
    time=year+", "+ dayN+ ", "+ h;
    sc.nextLine();
    tree=new ZHTreeMap<String, String>();
    while(sc.hasNextLine()){
      line=sc.nextLine();
      if(line.length()>5){
        String waveLength = line.substring(0, 7);
        waveLength=waveLength.replaceAll(" ", "");
        String result = line.substring(9, 20);
        tree.put(waveLength, result);
      }
    }
    sc.close();
  }
  
  public String getResult(String w){
    System.out.println(tree.containsKey(w));
    System.out.println(w);
    if(tree.containsKey(w)){
      return tree.get(w); 
    }
    return null;
  }
  
  public ZHTreeSet<String> wSet(){
    return tree.keySet(); 
  }
  
  public String getTime(){
    return time; 
  }
}