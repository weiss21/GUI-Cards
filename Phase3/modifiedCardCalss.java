/*
 * One object of class Card represents an individual card from a standard deck
 * of playing cards.
 */
class Card
{  
   
   
   private char value;
   private Suit suit;
   private boolean errorFlag;
   public static final char[] cards = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};
   public static char[] valueRanks = cards;

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
   
   static void arraySort(Card[] cards, int arraySize) {
      boolean swapped = true;
      
      while (swapped) {
         swapped = false;
         for (int i = 0; i < arraySize - 1; i++) {
            if (cards[i].compareTo(cards[i+1]) > 0) {
               Card temp = cards[i];
               cards[i] = cards[i + 1];
               cards[i + 1] = temp;
               swapped = true;
            }
         }
      }
   }  
   
   public int compareTo(Card card) {

      if (this.value == card.value) {
         return GUICard.turnCardSuitIntoInt(this) - GUICard.turnCardSuitIntoInt(card);
      }

      return GUICard.turnCardValueIntoInt(this) - GUICard.turnCardValueIntoInt(card);
   }
}
