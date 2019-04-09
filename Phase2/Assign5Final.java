/* Team 9 - W. Robleh, M. Mariscal, T. Doan, Y. Nikulyak, C. Piwarski
 * CST338 - Software Design
 * Assignment 5
 * Phase 2: Encapsulating Layount and Icons into
 * CardTable and GUICard Classes
 * This class is the main class for Phase 2 of the assignment
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;

public class Assign5
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   public static void main(String[] args)
   {
      int k;
      Icon tempIcon;

      // establish main frame in which program will run
      CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, 
         NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // CREATE LABELS for Computer and Human
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         tempIcon = GUICard.getIcon(generateRandomCard());
         humanLabels[k] = new JLabel(tempIcon);
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
      }

      for (k = 0; k < NUM_PLAYERS; k++)
      {
         tempIcon = GUICard.getIcon(generateRandomCard());
         playedCardLabels[k] = new JLabel(tempIcon);
      }

      // ADD LABELS TO PANELS - Human and Computer

      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         myCardTable.pn1ComputerHand.add(computerLabels[k]);
         myCardTable.pn1HumanHand.add(humanLabels[k]);
      }

      // create play area text labels
      playLabelText[0] = new JLabel("Computer", SwingConstants.CENTER);
      playLabelText[1] = new JLabel("You", SwingConstants.CENTER);

      // add labels to main play area
      for (k = 0; k < NUM_PLAYERS; k++)
      {
         tempIcon = GUICard.getIcon(generateRandomCard());
         myCardTable.pn1PlayerArea.add(new JLabel(tempIcon));
      }

      for (k = 0; k < NUM_PLAYERS; k++)
         myCardTable.pn1PlayerArea.add(playLabelText[k]);

      // show everything to the user
      myCardTable.setVisible(true);
   }

   // and two random cards in the play region (simulating a computer/hum ply)
   public static Card generateRandomCard()
   {
      Deck deck = new Deck();
      deck.shuffle();
      return deck.dealCard();
   }
}

/*
 * One object of class CardTable represents a table for a game of playing cards
 * complete with multiple hands and main playing area.
 */
class CardTable extends JFrame
{

   static final int MAX_CARDS_PER_HAND = 57;
   static int MAX_PLAYERS = 2; // for now, we only allow 2 person games
   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pn1ComputerHand, pn1HumanHand, pn1PlayerArea;

   /*
    * Constructor class for CardTable that initializes the graphical user
    * interface.
    */
   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      super(title);
      // test parameters validity
      if (numCardsPerHand < 0 || numCardsPerHand > MAX_CARDS_PER_HAND 
         || numPlayers < 0 || numPlayers > MAX_PLAYERS)
      {
         return;
      }
      this.numCardsPerHand = numCardsPerHand;
      this.numPlayers = numPlayers;
      
      // layout computer player hands
      pn1ComputerHand = new JPanel();
      pn1ComputerHand.setLayout(new GridLayout(1, numCardsPerHand));
      pn1ComputerHand.setBorder(new TitledBorder("Computer Hand"));
      add(pn1ComputerHand, BorderLayout.NORTH);
      
      // layout human player hands
      pn1HumanHand = new JPanel();
      pn1HumanHand.setLayout(new GridLayout(1, numCardsPerHand));
      pn1HumanHand.setBorder(new TitledBorder("Your Hand"));
      add(pn1HumanHand, BorderLayout.SOUTH);
      
      // layout center playing area
      pn1PlayerArea = new JPanel();
      pn1PlayerArea.setLayout(new GridLayout(2, numPlayers));
      pn1PlayerArea.setBorder(new TitledBorder("Playing Area"));
      add(pn1PlayerArea, BorderLayout.CENTER);
   }

   /*
    *  This method returns the number of cards per hand.
    */
   public int getnumCardPerHand()
   {
      return numCardsPerHand;
   }

   /*
    * This method returns the number of players in the CardTable.
    */
   public int getnumPlayers()
   {
      return numPlayers;
   }
}

/*
 * One object of class GUICard represents a standard playing Card with a
 * graphical representation.
 */
class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4];
   private static Icon iconBack;
   private static boolean iconsLoaded = false;

   /*
    * This method returns an icon for the given Card object.
    */
   public static Icon getIcon(Card card)
   {
      loadCardIcons();
      return iconCards[turnCardValueIntoInt(card)][turnCardSuitIntoInt(card)];
   }

   /*
    * This method returns an icon for the back of a playing card.
    */
   public static Icon getBackCardIcon()
   {
      loadCardIcons();
      return iconBack;
   }

   /*
    * This method receives an integer and converts it to a string that
    * represents the value of a Card.
    */
   static String turnIntIntoCardValue(int k)
   {
      // test parameter validity
      if (k < 0 || k > 13)
      {
         return "";
      }

      String str = "";
      switch (k)
      {
      case 0:
         str = "A";
         break;
      case 1:
         str = "2";
         break;
      case 2:
         str = "3";
         break;
      case 3:
         str = "4";
         break;
      case 4:
         str = "5";
         break;
      case 5:
         str = "6";
         break;
      case 6:
         str = "7";
         break;
      case 7:
         str = "8";
         break;
      case 8:
         str = "9";
         break;
      case 9:
         str = "T";
         break;
      case 10:
         str = "J";
         break;
      case 11:
         str = "Q";
         break;
      case 12:
         str = "K";
         break;
      case 13:
         str = "X";
         break;
      }
      return str;
   }

   /*
    * This method receives an integer and converts it to the appropriate
    * Suit value for a Card.
    */
   static String turnIntIntoCardSuit(int j)
   {
      // test parameter validity
      if (j < 0 || j > 3)
      {
         return "";
      }
      String str = "";
      switch (j)
      {
      case 0:
         str = "C";
         break;
      case 1:
         str = "D";
         break;
      case 2:
         str = "H";
         break;
      case 3:
         str = "S";
         break;
      }
      return str;

   }

   /*
    * This method receives a Card object and returns it's value as an
    * integer.
    */
   static int turnCardValueIntoInt(Card card)
   {
      // test parameter validity
      if (card == null)
      {
         return -1;
      }

      int number = -1;
      switch (card.getValue())
      {
      case 'A':
         number = 0;
         break;
      case '2':
         number = 1;
         break;
      case '3':
         number = 2;
         break;
      case '4':
         number = 3;
         break;
      case '5':
         number = 4;
         break;
      case '6':
         number = 5;
         break;
      case '7':
         number = 6;
         break;
      case '8':
         number = 7;
         break;
      case '9':
         number = 8;
         break;
      case 'T':
         number = 9;
         break;
      case 'J':
         number = 10;
         break;
      case 'Q':
         number = 11;
         break;
      case 'K':
         number = 12;
         break;
      case 'X':
         number = 13;
         break;
      }
      return number;
   }

   /*
    * This method receives a Card object and returns an integer representing
    * the Card's Suit.
    */
   static int turnCardSuitIntoInt(Card card)
   {
      // test parameter validity
      if (card == null)
      {
         return -1;
      }
      int number = -1;
      switch (card.getSuit())
      {
      case SPADES:
         number = 0;
         break;
      case HEARTS:
         number = 1;
         break;
      case DIAMONDS:
         number = 2;
         break;
      case CLUBS:
         number = 3;
         break;
      }
      return number;
   }

   /*
    * This method receives a String and returns the corresponding Suit
    * value.
    */
   public static Card.Suit turnStringToSuit(String suit)
   {

      switch (suit.charAt(0))
      {
      case 'C':
         return Card.Suit.CLUBS;
      case 'D':
         return Card.Suit.DIAMONDS;
      case 'H':
         return Card.Suit.HEARTS;
      default:
         return Card.Suit.SPADES;
      }
   }

   /*
    * This method loads the Card Object images if they have not been
    * done so already.
    */
   static void loadCardIcons()
   {

      // check if already loaded
      if (iconsLoaded)
      {
         return;
      }

      String folder = "images/";
      String exten = ".gif";

      // generate card names and load icon
      for (int i = 0; i < iconCards.length; i++)
      {
         for (int j = 0; j < iconCards[0].length; j++)
         {
            iconCards[i][j] = new ImageIcon(folder + turnIntIntoCardValue(i) 
               + turnIntIntoCardSuit(j) + exten);
         }
      }

      iconBack = new ImageIcon(folder + "BK" + exten);
      iconsLoaded = true;
   }
}

/*
 * One object of class Card represents an individual card from a standard deck
 * of playing cards.
 */
class Card
{

   private char value;
   private Suit suit;
   private boolean errorFlag;
   
   public static char[] valueRanks = new char[]
       { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };

   public enum Suit
   {
      CLUBS, SPADES, HEARTS, DIAMONDS
   };

   /*
    * Constructor method for class Card. This method receives a value 
    * and suit and creates a Card object with those attributes.
    */
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   /*
    * Default constructor for class Card. Creates a Card that represents 
    * the ace of spades.
    */
   public Card()
   {
      this('A', Suit.SPADES);
   }

   /*
    * Copy Constructor for class Card. This method receives a Card object, and
    * generates another one containing identical data.
    */
   public Card(Card card)
   {
      if (card == null)
         return;
      this.value = card.value;
      this.suit = card.suit;
   }

   /*
    * This method returns a string representation of the Card object.
    */
   @Override
   public String toString()
   {
      if (errorFlag)
      {
         return "**invalid**";
      }
      return getValue() + " of " + getSuit();
   }

   /*
    * Mutator method that sets the numerical value and suit for the Card object.
    */
   public boolean set(char value, Suit suit)
   {
      if (isValid(value, suit))
      {
         this.value = Character.toUpperCase(value);
         this.suit = suit;
         errorFlag = false;
      } else
      {
         errorFlag = true;
      }
      return errorFlag;
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
      return this.suit;
   }

   /*
    * This method returns the error flag for the Card object.
    */
   public boolean getErrorFlag()
   {
      return this.errorFlag;
   }

   /*
    * This method receives a character value and determines if 
    * it is suitable for use as an attribute for the Card value. 
    * The method returns true if the character can be used.
    */
   private boolean isValid(char value, Suit suit)
   {
      char upper = Character.toUpperCase(value);
      for (int i = 0; i < valueRanks.length; i++)
      {
         if (upper == valueRanks[i])
         {
            return true;
         }
      }
      return false;
   }

   /*
    * This method receives a Card object and determines if it is equal 
    * in value to the receiving Card object. A boolean is returned 
    * true if both objects contain the same data.
    */
   public boolean equals(Card card)
   {
      if (card == null)
      {
         return false;
      }
      return (this.value == card.value && this.suit == card.suit 
         && this.errorFlag == card.errorFlag);
   }

   /*
    * This methods receives Card object and returns a suit value to help
    * with cardvalue
    */
   public static int getSuitValue(Card cards)
   {
      if (cards == null)
      {
         return -1;
      }
      int number = -1;
      switch (cards.getSuit())
      {
      case SPADES:
         number = 0;
         break;
      case HEARTS:
         number = 14;
         break;
      case DIAMONDS:
         number = 28;
         break;
      case CLUBS:
         number = 42;
         break;
      }
      return number;
   }
   

   /*
    * This method receives a Card and returns an integer in comparison
    * to the current Card.
    */
   public static int cardValue(Card card)
   {

      return GUICard.turnCardValueIntoInt(card) + Card.getSuitValue(card);
      
   }

   /*
    * This method receives an array of Card objects and sorts them
    * based on their Suit and value.
    */
    static void arraySort(final Card[] cards, int arraySize)
   {
      Card tempCard;

      for(int i = 0; i < arraySize; i++){
         for(int j = 1; j < arraySize - i; j++){
            if(cardValue(cards[j-1]) > cardValue(cards[j])){
               tempCard = cards[j-1];
                cards[j-1] = cards[j];
                cards[j] = tempCard;
            }
        }

      }
   }
}

/*
 * One object of class Hand represents a hand in a card game. 
 * It is composed of Card objects.
 */
class Hand
{
   public static final int MAX_CARDS = 56;
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
      for (int i = 0; i < numCards; i++)
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
      // if no more cards in Hand, return a bad card
      if (this.numCards <= 0)
      {
         return new Card('Z', Card.Suit.SPADES);
      }

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
   @Override
   public String toString()
   {
      String returnString = "(";
      for (int i = 0; i < this.numCards; i++)
      {
         if (i == this.numCards - 1)
         {
            returnString += this.myCards[i].toString();
         } else
            returnString += this.myCards[i].toString() + ", ";
      }
      returnString += ")";
      return returnString;
   }

   /*
    * This method receives an integer value and locates the associated 
    * Card in the Hand. If the Card is not located, 
    * a new Card is generated and returned.
    */
   public Card inspectCard(int k)
   {
      if (k >= 0 && k < numCards)
         return myCards[k];
      else
         return new Card('Z', Card.Suit.SPADES);
   }

   public void sort()
   {
      Card.arraySort(myCards, numCards);
   }
}

/*
 * One object of class Deck represents a standard deck of playing cards.
 */
class Deck
{
   public static final int MAX_CARDS = 336;
   private static Card[] masterPack;
   private Card[] cards;
   private int topCard;

   /*
    * Constructor method for Class Deck. This method receives 
    * an integer numPacks and generates a deck with numPacks 
    * number of card packs.
    */
   public Deck(int numPacks)
   {
      allocateMasterPack();
      init(numPacks);
   }

   /*
    * Default Constructor method for Class Deck. This method creates 
    * a Deck with one pack of Card objects.
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
      int packLimit = (MAX_CARDS / 56);
      // array initialized with total number of cards
      if (numPacks > 0 && numPacks <= packLimit)
      {
         int total = numPacks * 56;
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
    * Updated to include Joker,
    */
   private static void allocateMasterPack()
   {
      // check if masterPack has already been generated.
      if (masterPack != null)
         return;

      masterPack = new Card[56];
      int count = 0;
      char[] values =
      { 'T', 'J', 'Q', 'K', 'A', 'X' };

      // make all the numbered cards
      for (char i = '2'; i <= '9'; i++)
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
      while (num > 0)
      {
         index1 = (int) (Math.random() * cards.length);
         index2 = (int) (Math.random() * cards.length);

         // swapping the elements
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
    * This method returns the value for the requested Card 
    * object in the Deck.
    */
   public Card inspectCard(int k)
   {
      if (k >= 0 && k < cards.length)
         return cards[k];
      else
         return new Card('Z', Card.Suit.SPADES);
   }

   /*
    * This method returns the object value for topCard.
    */
   public int getTopCard()
   {
      return this.topCard;
   }

   /*
    * This method sorts the Deck of Cards.
    */
   public void sort()
   {
      Card.arraySort(cards, topCard);
   }

   /*
    * This method receives a Card and removes the matching Card
    * from the Deck.
    */
   public boolean removeCard(Card card)
   {
      // test parameter validity
      if (card == null)
      {
         return false;
      }

      boolean found = false;

      for (int i = 0; i < topCard; i++)
      {
         if (cards[i].equals(card))
         {
            cards[i] = cards[topCard - 1];
            topCard--;
            found = true;
            break;
         }
      }
      return found;
   }

   /*
    * This method returns an integer containing the number of Cards
    * in the Deck.
    */
   public int getNumCards()
   {
      return topCard;
   }

   /*
    * This method adds a Card object to the Deck. A boolean is returned
    * describing the status of completion.
    */
   public boolean addCard(Card card)
   {
      // test parameter validity
      if (card == null)
      {
         return false;
      }

      // check the space to add a new card
      if (topCard == MAX_CARDS)
      {
         return false;
      }

      int countCopies = 0;
      int packLimit = (MAX_CARDS / 56);
      // check number of copies of the card
      for (int i = 0; i < topCard; i++)
      {
         if (cards[i].equals(card))
         {
            countCopies++;
         }
      }

      if (countCopies >= packLimit)
      {
         return false;
      }

      topCard++;
      cards[topCard - 1] = card;
      return true;
   }
}
