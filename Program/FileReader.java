import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import zhstructures.*;

/*
 * File Name; FileReader
 * 
 * @author Xu,Runbo
 * @version 1.1
 * @Date May, 31, 2011
 */

/*
 * This FileReader uses scanner to read .IRR files and store all the wavelength and related values in ZHTreeMap.
 */
public class FileReader {
  /*
   * A scanner used to read a .IRR file
   */
  private Scanner sc;
  /*
   * A variable store the time when this data taken
   */
  private String time;
  /*
   * A map to hold all the wavelength and related valuse as pairs
   */
  public ZHTreeMap<String, String> tree;
  
  /*
   * The constructor creates a new FileReader by creating the scanner and use it to read a file
   * @param fileName the name of the file this FileReader going to read
   */
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
  
  /*
   * Will return the related value of the given wavelength
   * @param w the given wavelength
   * @return String return the value of given wavelength if w is contained in set of wavelength, else return null
   */
  public String getResult(String w){
    System.out.println(tree.containsKey(w));
    System.out.println(w);
    if(tree.containsKey(w)){
      return tree.get(w); 
    }
    return null;
  }
  
  /*
   * Will return the set of wavelength contains in this file
   * @return ZHTreeSet the set of all the wavelengths
   */
  public ZHTreeSet<String> wSet(){
    return tree.keySet(); 
  }
  /*
   * Will return time when this data taken
   * @return String time stored in the file
   */
  public String getTime(){
    return time; 
  }
}