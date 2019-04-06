/*Team 9 - W. Robleh, M. Mariscal, T. Doan, Y. Nikulyak, C. Piwarski
* CST338 - Software Design
* Assignment 5
* Phase 2: Encapsulating Layount and Icons into
CardTable and GUICard Classes
*/


import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class Assig5
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLable[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playerCardLables = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = newJLabel[num_players];
	
   public static void main(String[] args)
   {
      
   }
}

class CardTable extends JFrame
{

   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games
   private int numCardsPerHand;
   private int numPlayers;
   
   public JPanel pn1ComputerHand, pn1HumandHand, pn1PlayerAre;

   //CardTable(String title, int numCardsPerHand, int numPlayers) - 
   //The constructor filters input, 
   //adds any panels to the JFrame,  
   //establishes layouts according to the general description below.
   public CardTable(String title, int numCardsPerHand, int numPlayers)//NEED HELP ON THIS ONE
   {
      int input;
         do {
         input = sc.nextInt();
      } while (input < 0 || input > 3);
   }
   //Accessors for the one instance members.
   public int getnumCardPerHand()
   {
      return numCardsPerHand;
   }
   //Accessors for the one instance members.
   public int getnumPlayers()
   {
      return numPlayers;
   }

}

  
class GUICARD
{
	 private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
 	 private static Icon iconBack;
 	 static boolean iconsLoaded = false;	
  
	 static void loadCardIcon()
   {
     
   }  
}

	





