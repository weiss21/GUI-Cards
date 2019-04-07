/*Team 9 - W. Robleh, M. Mariscal, T. Doan, Y. Nikulyak, C. Piwarski
* CST338 - Software Design
* Assignment 5
* Phase 2: Encapsulating Layout and Icons into
CardTable and GUICard Classes
*/
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class Assign5
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playerCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];
    
   public static void main(String[] args)
   {
      int k;
      Icon tempIcon;
      
      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // CREATE LABELS ----------------------------------------------------
       for (k = 0; k < NUM_CARDS_PER_HAND; k++ )
       {
           tempIcon = GUICard.getIcon(generateRandomCard());
           humanLabels[k] = new JLabel(tempIcon);
           computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
           myCardTable.pn1HumanHand.add(humanLabels[k]);
           myCardTable.pn1ComputerHand.add(computerLabels[k]);
       }
      
      for (k = 0; k < NUM_PLAYERS; k++)
      {
          tempIcon = GUICard.getIcon(generateRandomCard());
          playerCardLabels[k] = new JLabel(tempIcon);
          if (k > 0)
            playLabelText[k] = new JLabel("You" , JLabel.CENTER);
          else
            playLabelText[k] = new JLabel("Computer ", JLabel.CENTER);
      }
  
      // ADD LABELS TO PANELS -----------------------------------------
      for ( k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
          myCardTable.pn1ComputerHand.add(computerLabels[k]);
          myCardTable.pn1HumanHand.add(playerCardLabels[k]);
      }
     
      // add labels to main play area
      for ( k = 0; k < NUM_PLAYERS; k++)
      {
          if ( k < NUM_PLAYERS)
             myCardTable.pn1PlayerArea.add(playerCardLabels[k]);
          else
            myCardTable.pn1PlayerArea.add(playLabelText[k - NUM_PLAYERS]);
      }  
      
      //ad
        
      // show everything to the user
      myCardTable.setVisible(true);

   }

// and two random cards in the play region (simulating a computer/hum ply)
public static Card generateRandomCard()
{
  Card card = new Card();
  int cardVal = (int) (Math.random() % 14);
  int suitVal =  (int) (Math.random() % 4);
  //card.set(GUICard.turnIntIntoCardValue(cardVal), 
           //GUICard.turnIntIntoCardSuit(suitVal));     
  
  //card.value = GUICard.turnIntIntoCardValue(cardVal);
  //card.suit = Card.valueOf(suitVal);

  return card;
}
}


/*
 * One object of class CardTable represents a table for a game of playing cards
 * complete with multiple hands and main playing area.
 */
class CardTable extends JFrame
{

   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games
   private int numCardsPerHand;
   private int numPlayers;
   
   public JPanel pn1ComputerHand, pn1HumanHand, pn1PlayerArea;

   //CardTable(String title, int numCardsPerHand, int numPlayers) - 
   //The constructor filters input, 
   //adds any panels to the JFrame,  
   //establishes layouts according to the general description below.
   public CardTable(String title, int numCardsPerHand, int numPlayers)//NEED HELP ON THIS ONE
   {
      //filler for now
      title = "";
      this.numCardsPerHand = numCardsPerHand;
      this.numPlayers = numPlayers;
      this.pn1ComputerHand = new JPanel();
      this.pn1HumanHand = new JPanel();
      this.pn1PlayerArea = new JPanel(); 
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

/*
 * One object of class GUICard represents a standard playing Card with 
 * a graphical representation. 
 */
class GUICard
{
     private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
     private static Icon iconBack;  //back of the cards image
     static boolean iconsLoaded = false;    
     //accessor method return card   
     public static Icon getIcon(Card card)
   {
      if(!iconsLoaded) 
      {
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
   
   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      if (j < 0 || j > 3)
      {
         return "";// if j is out of parameters
      }
      return String.valueOf(Card.valueOf(j));
   }
   
   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      if (k < 0 || k > 13)
      {
         return ""; // if k is out of parameters
      }
      
      return String.valueOf(Card.cards[k]);
   }
   
   static void loadCardIcons()
   {
      if(!iconsLoaded) // Only load images once
      {
         final String directory = String.format("%s/%s/", System.getProperty("user.dir"), "/images");

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

  
/*
 * One object of class Card represents a playing card complete with a value
 * and suit.
 */
class Card
{ 
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
        public char value;
        public Suit suit = null;
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
        this.errorFlag = false;
     } else {
        this.errorFlag = true;
     }     
    return this.errorFlag;
  }
 
  
  /*
   * This method receives a boolean and sets the errorFlag to it.
   */
  public void setErrorFlag(boolean arg)
  {
     this.errorFlag = arg;
  }
  /*
   * This method returns the value for the card object.
   */
  public char getValue()
  {
     return this.value;
  }
  
  /*
   * This method returns the suit for the instance of the card. 
   */
  public Suit getSuit()
  {
     return suit;
  }

  /*
   * This method returns the errorFlag boolean for the Card object.
   */
  public boolean getErrorFlag()
  {
     return errorFlag;
  }

   /*
    * This method receives both a char and Suit. The method then
    * determines if they are appropriate for Card creation and 
    * returns a boolean.
    */
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

/*
 * This method receives a Card object and determines if it is equal in value to 
 * the current Card.
 */
 public boolean equals(Card card){
   if (card == null){
     return false;
   } 
   return (this.value == card.value && this.suit == card.suit && this.errorFlag == card.errorFlag);
 }
  
   /*
    * This method receives an array of Card objects and sorts them according to
    * their value and suit. WR
    */
   static void arraySort(Card[] card, int arraySize)
   {
      //arraySize = size of array
      
      for(int i = 1; i < arraySize; i++)
      {
         for(int j = 0; j < arraySize - i; j++)
         {
            //if(card[j].getValue() > card[j + 1].getValue())
           //compare cards with its modulo value in valueRanks
            if(card[j % 14].getValue() != valueRanks[j])
            {
               //swap card[j+1] and card[i]
               Card temp = card[j];
               card[j] = card[j + 1];
               card[j + 1] = temp;
            }
         }
      }
   }
}

/*
 * One object of class Hand represents a hand in a card game. It is composed of 
 * Card objects.
 */ 
class Hand
{
   public static final int MAX_CARDS = 52;
   private Card[] myCards;
   private int numCards;

   /*
    * This method is the default constructor method for class Hand.
    */
   public Hand()
   {
      this.numCards = 0;
      this.myCards = new Card[MAX_CARDS];
   }
  
   /*
    * This method moves all the card objects from the Hand object.
    */ 
   public void resetHand()
   {
      for(int i = 0; i < numCards; i++)
      {
         this.myCards[i] = null;
      }
      this.numCards = 0;
   }
   
   
  
   /*
    * This method adds a Card object to the next slot in the Hand object.
    */ 
   public boolean takeCard(Card card)
   {
      if (card == null)
         return false;

      // make a copy 
      Card cardCopy = new Card(card);
      if (this.numCards < this.myCards.length)
      {
         this.myCards[this.numCards] = cardCopy;
         this.numCards++;
         return true;
      }  
      return false;
   }

   /*
    * This method returns the top card from the Hand object and removes it.
    */ 
   public Card playCard()
   {
      Card returnCard = this.myCards[this.numCards - 1];
      this.myCards[this.numCards - 1] = null;
      this.numCards--;
      return returnCard;
   }

   /*
    * This method returns the value of numCards for the Hand object.
    */
   public int getNumCards()
   {
      return this.numCards;
   }
  
   /*
    * This method creates a string representation of the Hand object.
    */ 
   public String toString()
   {
      String returnString = "(";
      for(int i = 0; i < this.numCards; i++)
      {
         if (i == this.numCards - 1)
         {
            returnString += this.myCards[i].toString();
         }
         else
            returnString += this.myCards[i].toString() + ", ";
      }
      returnString += ")";
      return returnString;
   }

   /*
    * This method receives an integer value and locates the associated Card in
    * the Hand. If the Card is not located, a new Card is generated and 
    * returned.
    */
   public Card inspectCard(int k)
   {
      if (k >= 0 && k < numCards)
         return myCards[k];
      else
         return new Card('x', Card.Suit.SPADES);
   }


   /*
    * This method receives a index value and returns the appropriate Card
    * from the Hand.
    */
   public Card playCard(int cardIndex)
   {
      if ( numCards == 0 ) //error
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.SPADES);
      }
      //Decreases numCards.
      Card card = myCards[cardIndex];
      
      numCards--;
      for(int i = cardIndex; i < numCards; i++)
      {
         myCards[i] = myCards[i+1];
      }
      
      myCards[numCards] = null;
      
      return card;
    }

   /*
    * This method sorts the Hand object. MM WR
    */
   public void sort()
   {
     Card.arraySort(myCards, myCards.length);
   }
}

/*
 * One object of class Deck represents a standard deck of playing cards. This is 
 * modified code from assignment 3
 */
class Deck
{
   public static final int MAX_CARDS = 312;
   private static Card[] masterPack;
   private Card[] cards;
   private int topCard;
   private int numPacks;

   /*
    * Constructor method for Class Deck. This method receives an integer 
    * numPacks and generates a deck with numPacks number of card packs.
    */
   public Deck(int numPacks)
   {
      allocateMasterPack();
      init(numPacks);    
   }  
  
   /*
    * Default Constructor method for Class Deck. This method creates a Deck
    * with one pack of Card objects.
    */
   public Deck()
   {
      this(1);
   }
  
   /*
    * This method repopulates the cards in the Cards private member.
    */
   public void init(int numPacks)
   {
      int packLimit = (MAX_CARDS / 52);
      // array initialized with total number of cards
      if (numPacks > 0 && numPacks <= packLimit)
      {
         int total = numPacks * 52;
         this.numPacks = numPacks;
         cards = new Card[total];
         for (int i = 0; i < cards.length; i++)
         {
            cards[i] = new Card(masterPack[i % masterPack.length]);
         }
         this.topCard = total; // initialize topCard with the total amount
      }
   }

   /*
    * This method generates the master pack for a deck of Card objects.
    */ 
   private static void allocateMasterPack()
   {
      // check if masterPack has already been generated.
      if (masterPack != null)
         return;
    
      masterPack = new Card[52];
      int count = 0;
      char[] values = {'T', 'J', 'Q', 'K', 'A'};
   
      // make all the numbered cards
      for(char i = '2'; i <= '9'; i++)
      {
         for (Card.Suit suitType : Card.Suit.values())
         {
            Card newCard = new Card(i, suitType);
            masterPack[count] = newCard;
            count++;
         }
      }
   
      // make all the face cards
      for (char value : values)
      {
         for (Card.Suit suitType : Card.Suit.values())
         {
            Card newCard = new Card(value, suitType);
            masterPack[count] = newCard;
            count++;
         }
      }
   }

   /*
    * This method mixes up the cards contained in the Deck object.
    */
   public void shuffle()
   {
      int index1;
      int index2;
      int num = cards.length;
      while( num > 0)
      {
         index1 = (int)(Math.random()* cards.length); 
         index2 = (int)(Math.random()* cards.length);  
            
         //swapping the elements 
         Card temp = cards[index1]; 
         cards[index1] = cards[index2]; 
         cards[index2] = temp; 
         num--;
      }                  
   }

   /*
    * This method returns the Card object from the top of the deck.
    */
   public Card dealCard()
   {
      if (this.topCard <= 0)
         return null;
      Card returnCard = this.cards[this.topCard - 1];
      this.cards[this.topCard - 1] = null;
      this.topCard--;
      return returnCard;
   }
  
   /*
    * This method returns the value for the requested Card object in the Deck.
    */
   public Card inspectCard(int k)
   {
      if (k >= 0 && k < cards.length)
         return cards[k];
      else
         return new Card('x', Card.Suit.SPADES);
   }

   /*
    * This method returns the object value for topCard.
    */
   public int getTopCard()
   {
      return this.topCard;
   }
  
   /*
    * This method adds a Card object to the Deck. CDP
    */
   public boolean addCard(Card card)
   {
     if (card == null)
       return false;
     
     // check that the Card is not in the Deck already
     int matchingCardCount = 1;
     
     // search the Deck of Cards
     for (int i = 0; i < this.cards.length; i++)
       if (cards[i].equals(card))
         matchingCardCount += 1;
     
     // if adding the Card would exceed pack limitations return false
     if (matchingCardCount > this.numPacks)
        return false;
     
     return true;
   }
  
   /*
    * This method removes a Card object from the Deck. CDP
    */
   public boolean removeCard(Card card)
   {
      if (card == null)
        return false;
     
     // Search the Deck for the card
     for (int i = 0; i < this.cards.length; i++)
     {
       // if a match is found, replace it with the top card
       if (this.cards[i].equals(card))
         this.cards[i] = cards[this.getTopCard()];
         return true;
     }

     // if the card is not found return false
     return false;
   }
  
  /*
   * This method sorts the Deck of Cards and places them in order.
   * CDP and MM
   */
  public void sort()
  {
      Card.arraySort(cards, cards.length);
  }
  
  /*
   * This method returns the number of Cards in the Deck. CDP
   * 
   */
  public int getNumCards()
  {
     return this.cards.length;
  }
}



    