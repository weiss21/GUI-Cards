/*Team 9 - W. Robleh, M. Mariscal, T. Doan, Y. Nikulyak, C. Piwarski
* CST338 - Software Design
* Assignment 5
* Phase 1: Reading and Displaying the .gif Files
* Use what you learned about how to instantiate an Icon object to represent any 
* .gif, .jpg or other image file on your disk and then place that Icon on 
* a JLabel. In Phase 1, we simply create an array of 57 JLabels, attach the 
* 57 .gif files to them, and display the labels, unstructured, in 
* a single JFrame. 
*/

import javax.swing.*;
import java.awt.*;

public class Assig5
{
   // static for the 57 icons and their corresponding labels
   // normally we would not have a separate label for each card, but
   // if we want to display all at once using labels, we need to.

   // 52 + 4 jokers + 1 back-of-card image
   static final int NUM_CARD_IMAGES = 57;
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   private static final String directory = String.format("%s%s", 
      System.getProperty("user.dir"), "/images/");
   private static final String[] value =
   { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X" };
   private static final String[] suit =
   { "C", "D", "H", "S" };

   /*
    * This method builds the file names of all the card .gif images and 
    * instantiates ImageIcon objects for them. The icons are stored in
    * an array.
    */
   static void loadCardIcons()
   {
      int i = 0;
      for (int s = 0; s < suit.length; s++)
      {
         for (int v = 0; v < value.length; v++)
         {
            if (i < NUM_CARD_IMAGES)
               icon[i++] = new ImageIcon(directory + turnIntIntoCardValue(v) + 
                  turnIntIntoCardSuit(s) + ".gif");
            // i iterates the icon array every time card value is added.
         }
      }
      if (i < NUM_CARD_IMAGES)
         //BK is unique, so it is appended last.
         icon[i] = new ImageIcon(directory + "BK.gif");
   }

   /*
    * This method receives an integer and converts it to a corresponding
    * Card value. The value is returned as a String.
    */
   static String turnIntIntoCardValue(int k)
   {
      //check that input is within parameters
      if (k < 0 || k > 13)
      {
         return "";
      }
      return value[k];
   }

   /*
    * This method receives an integer can converts it to a corresponding
    * Card suit. The value is returned as a String.
    */
   static String turnIntIntoCardSuit(int j)
   {
      if (j < 0 || j > 3)
      {
         return "";// if j is out of parameters
      }
      return suit[j];
   }

   /*
    * This main creates a JFrame and fills it with the caerd ImageIcons.
    */
   public static void main(String[] args)
   {
      int k;
      // print out the directory of the image folder
      System.out.println(directory);

      // prepare the image icon array
      loadCardIcons();

      // establish main frame in which program will run
      JFrame frmMyWindow = new JFrame("Card Room");
      frmMyWindow.setSize(1150, 650);
      frmMyWindow.setLocationRelativeTo(null);
      frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // set up layout which will control placement of buttons, etc.
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      frmMyWindow.setLayout(layout);

      // prepare the image label array
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         labels[k] = new JLabel(icon[k]);

      // place your 3 controls into frame
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         frmMyWindow.add(labels[k]);

      // show everything to the user
      frmMyWindow.setVisible(true);
   }
}
