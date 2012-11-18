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
 
  public IrradianceCalculator(double l, double l0, double d, double alpha, double x, double mu, double beta, double m, double p, double p0, double mp){
    this.l=l;
    this.l0=l0;
    this.d=d;
    this.alpha=alpha;
    this.x=x;
    this.mu=mu;
    this.beta=beta;
    this.m=m;
    this.p=p;
    this.p0=p0;
    this.mp=mp;
    delta= 0.0-Math.log(l/l0*Math.pow(d, 2))/mp-alpha*x*mu/mp-beta*m/mp*p/p0;
  }
  
  public double getAOD(){
   return  delta;
  }
  
}