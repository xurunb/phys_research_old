import javax.swing.*;

/**
 * PopUpInput is a class designed by Lynn Ziegler to enable the user to easily input String's,
 * int's, double's, and boolean's using a JOptionPane popup box. Each of the methods accepts
 * as input a String which will be shown as a prompt in the box. Since each of
 * the methods is static, you only need use something like
 *              double x=PopUpInput.getDouble("prompt")
 * to read in a double value, if this file is in the directory where you are
 * compiling your program.
 */
public class PopUpInput {
  
  /**
   * getDouble(String inputdialog) pops up an input box with inputdialog displayed and,
   * then, converts whatever was typed in into a double and returns that double.
   */
  public static double getDouble(String inputDialog) {
    return(Double.parseDouble(JOptionPane.showInputDialog(inputDialog)));
  }
  
  /**
   * getInt(String inputdialog) pops up an input box with inputdialog displayed and,
   * then, converts whatever was typed in into an int and returns that int.
   */
  public static int getInt(String inputDialog) {
     return(Integer.parseInt(JOptionPane.showInputDialog(inputDialog)));
  }
  /**
   * getString(String inputdialog) pops up an input box with inputdialog displayed and,
   * then, returns that String.
   */
  public static String getString(String inputDialog) {
    return(JOptionPane.showInputDialog(inputDialog));
  }
  /**
   * getBoolean(String inputdialog) pops up an input box with inputdialog displayed and,
   * then, converts whatever was typed in into a boolean and returns that boolean.
   */
  public static boolean getBoolean(String inputDialog) {
     return(Boolean.parseBoolean(JOptionPane.showInputDialog(inputDialog)));
  }
}
  
   
