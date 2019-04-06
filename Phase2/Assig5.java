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

  
class GUICard
{
	 private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
 	 private static Icon iconBack;  //back of the cards image
 	 static boolean iconsLoaded = false;	
  		//accessor method return card 	
	 public static Icon getIcon(Card card)
   {
      if(!iconsLoaded) {
         loadCardIcons();
      }
      return iconCards[new String(Card.cards).indexOf(card.getValue())][card.getSuit().intValue];
   }
   public static Icon getBackCardIcon()
   {
      if(!iconsLoaded) // no need to load more than once
      {
         loadCardIcons();
      }
      return iconBack;
}
   static void loadCardIcons()
   {
      if(!iconsLoaded) // Only load images once
      {
         final String directory = String.format("%s/%s/", System.getProperty("user.dir"), "\\images\\");

         for(int suitIndex = 0; suitIndex < Card.Suit.NUM_SUITS; suitIndex++)
         {
            for(int valueIndex = 0; valueIndex < Card.cards.length; valueIndex++)
            {
               iconCards[valueIndex][suitIndex] = new ImageIcon(String.format("%s%s%s.gif", directory, Card.cards[valueIndex], Card.Suit.valueOf(suitIndex)));
            }
         }
         iconBack = new ImageIcon(String.format("%s%s.gif", directory, "BK")); //sets cards back image
         iconsLoaded = true;
      }
   }
}

  
 //Define the Suit enum, { clubs, diamonds, hearts, spades }, inside the Card class. 
class Card
{
  
  //Include three members char value,Suit suit,boolean errorFlag.

  
 //Define the Suit enum, { clubs, diamonds, hearts, spades }, inside the Card class. 
  
     //Public enum Members:
     public enum Suit
     {
        CLUBS(0, 'C'), DIAMONDS(1, 'D'), HEARTS(2, 'H'), SPADES(3, 'S');

        public static final int NUM_SUITS = 4;
        public int intValue;
        public char charValue;

        Suit(int intValue, char charValue)
        {
           this.intValue = intValue;
           this.charValue = charValue;
        }

        public static char valueOf(int integer)
        {
           for(Suit suit : Suit.values())
           {
              if(integer == suit.intValue)
              {
                 return suit.charValue;
              }
           }
           throw new IllegalArgumentException(String.format("%s is not a valid argument"));
        }
     
     }
     //Public Static Data Members:
        public static final char[] cards = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};
        public static char[] valueRanks = cards;

        //Private Data Members:
        private char value;
        private Suit suit = null;
        private boolean errorFlag = false;

     
 
 /*Card(char value, Suit suit) - The constructor should call the proper mutator(s).  
 Overload this to cope with a client that wants to instantiate without parameters and use 'A' 
 and 'spades' as the default value and suit when not supplied.  Provide at least two constructors
  -- no parameters and all parameters -- or more if you wish.  
 Because we have the errorFlag member, the constuctor (via the mutator), can set that member 
 when it gets bad data; it does not have to assign default values upon receipt of bad data.  
 This is a new technique for us.  Again, default card (no parameters passed) is the ('A', spades).*/
 public Card(char value, Suit suit) 
 {
    set(value,suit);
 }
  
  //i would suggest using {set('A', Suit.SPADES);} to keep code consistance as our default construct
  public Card() 
  {
     this('A', Suit.SPADES);
  } 
 
 //copy constructor
 public Card(Card card){
   if(card == null){
     return;
   }
   this.value = card.value;
   this.suit = card.suit;
 }
 
  /*string toString() - a stringizer that the client can use prior to displaying the card.  
  It provides a clean representation of the card.  If errorFlag == true, 
  it should return correspondingly reasonable reflection of this fact (something like "
  [ invalid ]" rather than a suit and value). */
  public String toString()
  {
     if (errorFlag) 
     {
        return "**invalid**";
     } else
        return getValue() + " of " + getSuit();
  }
  /*a mutator that accepts the legal values established in the earlier section. 
  When bad values are passed, errorFlag is set to true and other values can be left in any state 
  (even partially set). 
  If good values are passed, they are stored and errorFlag is set to false. 
   Make use of the private helper, listed below.*/
  public boolean set(char value, Suit suit)
  {
     if (isValid(value,suit)) {
        this.value = Character.toUpperCase(value);
        this.suit = suit;
        errorFlag = false;
     } else {
        errorFlag = true;
     }     
    return errorFlag;
  } 
  public void setErrorFlag(boolean arg)
  {
     errorFlag = arg;
  }
  /*
   * This method returns the value for the card object.
   */
  public char getValue()
  {
     return value;
  }
  
  /*
   * This method returns the suit for the instance of the card. 
   */
  public Suit getSuit()
  {
     return suit;
  }

  public boolean getErrorFlag()
  {
     return errorFlag;
  }
  /*a private helper method that returns true or false, depending on the legality of the 
   * parameters.  
   Note that, although it may be impossible for suit to be illegal (due to its enum-ness), 
   we pass it, anyway, 
   in anticipation of possible changes to the type from enum to, say, char or int, someday. 
   We only need to test value, at this time.*/
   private boolean isValid(char value, Suit suit)
  {
    char upper = Character.toUpperCase(value);
    for(int i = 0; i < cards.length; i++) {
       if(upper == cards[i]) {
          return true;
       }
    }
    return false; 
  }
 //returns true if all the fields (members) are identical and false, otherwise.
 public boolean equals(Card card){
   if (card == null){
     return false;
   } 
   return (this.value == card.value && this.suit == card.suit && this.errorFlag == card.errorFlag);
 }
}


	



