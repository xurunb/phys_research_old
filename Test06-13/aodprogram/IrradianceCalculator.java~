package aodprogram;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import zhstructures.*;
import javax.swing.event.*;
import objectdraw.*;

public class IrradianceCalculator {
  private double l, l0, d, alpha, x, mu, beta, m, p, p0, delta, mp;
  
  public IrradianceCalculator(double l, double l0, double d, double beta, double p, double p0, double mp){
    this.l=l;
    this.l0=l0;
    this.d=d;
    //this.alpha=alpha;
    //this.x=x;
    //this.mu=mu;
    this.beta=beta;
    
    this.p=p;
    this.p0=p0;
    this.mp=mp;
    m=mp-0.0018167*(mp-1)-0.002875*Math.pow((mp-1), 2)-0.0008083*Math.pow((mp-1), 3);
    System.out.println(mp);
    delta= 0.0-Math.log(l/l0*Math.pow(d, 2))/mp-beta*m/mp*p/p0;
  }
  
  public double getAOD(){
    return  delta;
  }
  
}