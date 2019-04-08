import java.awt.*;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.*;

public class Assign5 {
   
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 
   
   public static void main(String[] args) {
      int k;
      Icon tempIcon;
      
      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      //myCardTable.setVisible(true);

      // CREATE LABELS ----------------------------------------------------
      //labels for computer
      for (k = 0; k < NUM_CARDS_PER_HAND; k++) {
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
      }
      //labels for human
      for (k = 0; k < NUM_CARDS_PER_HAND; k++) {
         humanLabels[k] = new JLabel(GUICard.getIcon(generateRandomCard()));
      }
  
      // ADD LABELS TO PANELS -----------------------------------------
      //add computer labels
      for(k = 0; k < NUM_CARDS_PER_HAND; k++) {
         myCardTable.pnlComputerHand.add(computerLabels[k]);
      }
      
      //add human labels
      for(k = 0; k < NUM_CARDS_PER_HAND; k++) {
         myCardTable.pnlHumanHand.add(humanLabels[k]);
      }
      
      // and two random cards in the play region (simulating a computer/hum ply)
      for (k = 0; k < NUM_PLAYERS; k++) {
         playedCardLabels[k] = new JLabel(GUICard.getIcon(generateRandomCard()));
      }
      
      for(k = 0; k < NUM_PLAYERS; k++) {
         myCardTable.pnlPlayArea.add(playedCardLabels[k]);
      }
      
      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("You", JLabel.CENTER);
      
      for(k = 0; k < NUM_PLAYERS; k++) {
         myCardTable.pnlPlayArea.add(playLabelText[k]);
      }

      // show everything to the user
      myCardTable.setVisible(true);
   }
   
   public static Card generateRandomCard() {
      int value = (int)(Math.random()* 14); 
      int suit = (int)(Math.random() * 4);
      
      String cardSuit = GUICard.turnIntIntoCardSuit(suit);
      String cardValue = GUICard.turnIntIntoCardValue(value);
      
      return new Card(cardValue.charAt(0), GUICard.turnStringToSuit(cardSuit) );      
   }
}

class CardTable extends JFrame{
   
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games
   
   private int numCardsPerHand;
   private int numPlayers;
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
   
   public CardTable(String title, int numCardsPerHand, int numPlayers) {
      
      super(title);
      //test parameters validity
      if (numCardsPerHand < 0 || numCardsPerHand > MAX_CARDS_PER_HAND || numPlayers < 0 
            || numPlayers > MAX_PLAYERS) {
         return;
      }
      
      this.numCardsPerHand = numCardsPerHand;
      this.numPlayers = numPlayers;
      
       pnlComputerHand = new JPanel();
       pnlComputerHand.setLayout(new GridLayout(1, numCardsPerHand));
       pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
       add(pnlComputerHand, BorderLayout.NORTH);
       
       pnlHumanHand = new JPanel();
       pnlHumanHand.setLayout(new GridLayout(1, numCardsPerHand));
       pnlHumanHand.setBorder(new TitledBorder("Your Hand"));
       add(pnlHumanHand, BorderLayout.SOUTH);
       
       pnlPlayArea = new JPanel();
       pnlPlayArea.setLayout(new GridLayout(2, numPlayers));
       pnlPlayArea.setBorder(new TitledBorder("Playing Area"));
       add(pnlPlayArea, BorderLayout.CENTER);     
   }
   
   // accessor
   public int getNumCardsPerHand() {
      return this.numCardsPerHand;
   }
   
   //accessor
   public int getNumPlayers() {
      return this.numPlayers;
   }
   
}

class GUICard {
   
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
   private static Icon iconBack;
   static boolean iconsLoaded = false;
   
   static void loadCardIcons() {
      
      //check if already loaded
      if (iconsLoaded) {
         return;
      }
      
      String folder = "images/";
      String exten = ".gif";
      // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
      // in a SHORT loop. For each file name, read it in and use it to
      // instantiate each of the 57 Icons in the icon[] array.
      for (int i = 0; i < iconCards.length; i++) {
         for (int j = 0; j < iconCards[0].length; j++) {
            iconCards[i][j] = 
                  new ImageIcon(folder + turnIntIntoCardValue(i) + 
                        turnIntIntoCardSuit(j) + exten);            
         }
      }
      
      iconBack = new ImageIcon(folder + "BK" + exten);
      iconsLoaded = true;
   }
   
   public static Icon getIcon(Card card) {
      
      loadCardIcons();
      return iconCards[turnCardValueIntoInt(card)][turnCardSuitIntoInt(card)];     
   }
   
   public static Icon getBackCardIcon() {
      loadCardIcons();
      return iconBack;
   }
   
   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      //test parameter validity
      if (k < 0 || k > 13) {
         return "";
      }
      
      String str = "";
      switch (k) {
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
   
   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      //test parameter validity
      if (j < 0 || j > 3) {
         return "";
      }
      String str = "";
      switch (j) {
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
   
   // turns 'A', '2', '3', ... 'Q', 'K', 'X' into 0 - 13
   static int turnCardValueIntoInt(Card card)
   {
      //test parameter validity
      if (card == null) {
         return -1;
      }
      
      int number = -1;
      switch (card.getValue()) {
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
   
   // turns 'C', 'D', 'H', 'S' into 0 - 3
   static int turnCardSuitIntoInt(Card card)
   {
      //test parameter validity
      if (card == null) {
         return -1;
      }
      int number = -1;
      switch (card.getSuit()) {
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
   
   public static Card.Suit turnStringToSuit(String suit) {

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
}
/*
 * One object of class Card represents an individual card from a standard deck
 * of playing cards.
 */
class Card
{  
   private static char[] CARD_VALUES = {'A', '2', '3', '4', '5', '6', '7', 
         '8', '9', 'T', 'J', 'Q', 'K', 'X'};   
   
   private char value;
   private Suit suit;
   private boolean errorFlag;
   private char[] cards = {'A', '2', '3', '4', '5', '6', '7', 
      '8', '9', 'T', 'J', 'Q', 'K', 'X'};   

   public enum Suit 
   {    
      CLUBS,
      SPADES,
      HEARTS,
      DIAMONDS  
   }; 

   /*
    * Constructor method for class Card. This method receives a value and suit
    * and creates a Card object with those attributes.
    */
   public Card(char value, Suit suit) 
   {
      set(value,suit);
   }   

   /*
    * Default constructor for class Card. Creates a Card that represents the
    * ace of spades.
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
      if(card == null)
         return;
      this.value = card.value;
      this.suit = card.suit;
   }

   /*
    * This method returns a string representation of the Card object.
    */
   public String toString()
   {
      if (errorFlag) 
      {
         return "**invalid**";
      } 
      return getValue() + " of " + getSuit();
   }

   /*
    * Mutator method that sets the numerical value and suit for the Card 
    * object.
    */
   public boolean set(char value, Suit suit)
   {
      if (isValid(value,suit)) 
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
    * Accessor method that returns the Card object's error flag/
    */
   public boolean getErrorFlag()
   {
      return this.errorFlag;
   }

   /*
    * This method receives a character value and determines if it is suitable
    * for use as an attribute for the Card value. The method returns true if 
    * the character can be used.
    */
   private boolean isValid(char value, Suit suit)
   {
      char upper = Character.toUpperCase(value);
      for(int i = 0; i < cards.length; i++) 
      {
         if(upper == cards[i]) 
         {
            return true;
         }
      }
      return false; 
   }

  /*
   * This method receives a Card object and determines if it is equal in value
   * to the receiving Card object. A boolean is returned true if both objects
   * contain the same data.
   */
   public boolean equals(Card card)
   {
      if (card == null)
      {
         return false;
      } 
      return (this.value == card.value && this.suit == card.suit &&
            this.errorFlag == card.errorFlag);
   }
   
   public static char[] valueRanks() {
      return Arrays.copyOf(CARD_VALUES, CARD_VALUES.length);
   }
   
   static void arraySort(Card[] cards, int arraySize) {
      char[] values = valueRanks();
      boolean swapped = true;
      
      while (swapped) {
         swapped = false;
         for (int i = 0; i < arraySize - 1; i++) {
            char first = cards[i].getValue();
            char second = cards[i+1].getValue();
            char smaller = ' ';
            for (int j = 0; j < values.length; j++ ) {
               if (values[j] == first) {
                  smaller = first;
                  break;
               }
               if (values[j] == second) {
                  smaller = second;
                  break;
               }
            }
            if (smaller == second) {
               Card temp = cards[i];
               cards[i] = cards[i+1];
               cards[i+1] = temp;
               swapped = true;
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
      //if no more cards in Hand, return a bad card
      if (this.numCards <= 0) {
         return new Card('x', Card.Suit.SPADES);
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
   
   public void sort() {
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
    */ 
   private static void allocateMasterPack()
   {
      // check if masterPack has already been generated.
      if (masterPack != null)
         return;
    
      masterPack = new Card[56];
      int count = 0;
      char[] values = {'T', 'J', 'Q', 'K', 'A', 'X'};
   
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
   
   public void sort() {
      Card.arraySort(cards, topCard);
   }
   
   public boolean removeCard(Card card) {
      //test parameter validity
      if(card == null) {
         return false;
      }
      
      boolean found = false;
      
      for (int i = 0; i < topCard; i++) {
         if(cards[i].equals(card)) {
            cards[i] = cards[topCard-1];
            topCard--;
            found = true;
            break;
         }
      }  
      return found;
   }
   
   public int getNumCards() {
      return topCard;
   }
   
   public boolean addCard(Card card) {
      //test parameter validity
      if(card == null) {
         return false;
      }
      
      //check the space to add a new card
      if (topCard == MAX_CARDS) {
         return false;
      }
      
      int countCopies = 0;
      int packLimit = (MAX_CARDS / 56);
      //check number of copies of the card
      for (int i = 0; i < topCard; i++ ) {
         if (cards[i].equals(card)) {
            countCopies++;
         }
      }
      
      if (countCopies >= packLimit) {
         return false;
      }
      
      topCard++;
      cards[topCard -1 ] = card;
      return true;    
   }
}
