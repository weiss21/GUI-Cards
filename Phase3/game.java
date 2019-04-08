import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

public class Assign5 {

   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   static int[] playerScores = new int[NUM_PLAYERS];
   static int[] totalScores = new int[NUM_PLAYERS];
   static JLabel[] playerScoresLabels = new JLabel[NUM_PLAYERS];

   static boolean playerWinner = false;
   static boolean compWinner = false;

   private static Card lastPlayedCard = null;

   public static void main(String[] args) {

      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      int k;

      CardGameFramework highCardGame = new CardGameFramework(numPacksPerDeck, numJokersPerPack, numUnusedCardsPerPack,
            unusedCardsPerPack, NUM_PLAYERS, NUM_CARDS_PER_HAND);

      highCardGame.deal();

      // establish main frame in which program will run
      CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // CREATE LABELS ----------------------------------------------------
      // labels for computer
      for (k = 0; k < NUM_CARDS_PER_HAND; k++) {
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
      }
      // labels for human
      for (k = 0; k < NUM_CARDS_PER_HAND; k++) {
         humanLabels[k] = new JLabel(GUICard.getIcon(highCardGame.getHand(1).inspectCard(k)));
      }

      // ADD LABELS TO PANELS -----------------------------------------
      // add computer labels
      for (k = 0; k < NUM_CARDS_PER_HAND; k++) {
         myCardTable.pnlComputerHand.add(computerLabels[k]);
      }

      // add human labels
      for (k = 0; k < NUM_CARDS_PER_HAND; k++) {
         myCardTable.pnlHumanHand.add(humanLabels[k]);
         HandCardMouseListener listener = new HandCardMouseListener(k, highCardGame);
         humanLabels[k].addMouseListener(listener);
      }

      // initial state for the game
      for (k = 0; k < NUM_PLAYERS; k++) {
         playedCardLabels[k] = new JLabel(GUICard.getBackCardIcon());
         playerScoresLabels[k] = new JLabel("0", JLabel.CENTER);
         playLabelText[k] = new JLabel(k == 0 ? "Computer" : "You", JLabel.CENTER);
      }

      for (k = 0; k < NUM_PLAYERS; k++) {
         myCardTable.pnlPlayArea.add(playedCardLabels[k]);
      }

      for (k = 0; k < NUM_PLAYERS; k++) {
         myCardTable.pnlPlayArea.add(playLabelText[k]);
      }
      for (k = 0; k < NUM_PLAYERS; k++) {
         myCardTable.pnlPlayArea.add(playerScoresLabels[k]);
      }

      // show everything to the user
      myCardTable.setVisible(true);
   }

   public static Card generateRandomCard() {
      int value = (int) (Math.random() * 14);
      int suit = (int) (Math.random() * 4);

      String cardSuit = GUICard.turnIntIntoCardSuit(suit);
      String cardValue = GUICard.turnIntIntoCardValue(value);

      return new Card(cardValue.charAt(0), GUICard.turnStringToSuit(cardSuit));
   }

   /*
    * An action listener for delayed code to execute checking logic after a nice
    * delay to a player is able to see game logic and cards change (also helper 
    * to understand who wins).
    */
   static class DelayedGameCheckListener implements ActionListener {

      private final HandCardMouseListener handCardListener;

      public DelayedGameCheckListener(HandCardMouseListener handCardListener) {
         this.handCardListener = handCardListener;
      }

      public void actionPerformed(ActionEvent e) {
         handCardListener.afterAllMoved();
      }
   }

   /*
    * An action listener for delayed code to show computer's played card with 
    * a nice delay.
    */
   static class RefreshCompCardListener implements ActionListener {

      private final Card computerCard;

      public RefreshCompCardListener(Card computerCard) {
         this.computerCard = computerCard;
      }

      public void actionPerformed(ActionEvent e) {
         playedCardLabels[0].setIcon(GUICard.getIcon(computerCard));
      }
   }

   /*
    * A mouse listener for each card on a players hand. Contains index of the
    * card and a reference to CardGameFramework.
    */
   static class HandCardMouseListener implements MouseListener {

      private final int cardIndex;
      private final CardGameFramework game;

      public HandCardMouseListener(int cardIndex, CardGameFramework game) {
         this.cardIndex = cardIndex;
         this.game = game;
      }

      /*
       * Makes human move and tests the game logic. Method processes two states: when
       * a human is to make the first move (lastPlayedCard is null)
       * and when a computer has previously made its move
       * (lastPlayedCard} is not null).
       */
      private void makeHumanMove() {
         if (cardIndex >= game.getHand(1).getNumCards()) {
            return;
         }

         Card cardToPlay = game.getHand(1).playCard(cardIndex);
         System.out.println("Player is playing: " + cardToPlay);
         playedCardLabels[1].setIcon(GUICard.getIcon(cardToPlay));

         Card computerCard;
         if (lastPlayedCard != null) {
            computerCard = lastPlayedCard;
            lastPlayedCard = null;
         } else {
            computerCard = makeComputerMove(this.game);
         }

         // check mini winner for every 2 cards
         if (cardToPlay.compareTo(computerCard) < 0) {
            playerScores[0]++;
            compWinner = true;
            playerWinner = false;
         } else {
            playerScores[1]++;
            playerWinner = true;
            compWinner = false;
         }

         redrawPlayerHand();
         redrawScore();

         Timer timer = new Timer(2000, new DelayedGameCheckListener(this));
         timer.setRepeats(false);
         timer.start();
      }

      /*
       * Checks round and game completion after timeout in
       * (DelayedGameCheckListener), sets card places with the back of the
       * cards, makes computer move if computer won last round and remembers the
       * result. The method is called every time 2 cards are played.
       */
      private void afterAllMoved() {
         checkRoundFinished();
         checkGameFinished();

         playedCardLabels[0].setIcon(GUICard.getBackCardIcon());
         playedCardLabels[1].setIcon(GUICard.getBackCardIcon());

         if (compWinner) {
            lastPlayedCard = makeComputerMove(this.game);
         }
      }

      /*
       * Makes computer move, updates card place of computer and returns card played
       * by computer.
       */
      public static Card makeComputerMove(CardGameFramework game) {
         int computerCardIndex = chooseCompHighestCard(game);

         Card computerCard = game.getHand(0).playCard(computerCardIndex);

         // delay quick change of computers cards so human can have a time to see results
         Timer timer = new Timer(300, new RefreshCompCardListener(computerCard));
         timer.setRepeats(false);
         timer.start();
         System.out.println("Computer is playing: " + computerCard);
         return computerCard;
      }

      /*
       * return Highest card index in computer's hand or -1 if no cards left.
       */
      private static int chooseCompHighestCard(CardGameFramework game) {
         int highestCardIndex = -1;
         Card currentCard = null;
         for (int i = 0; i < game.getHand(0).getNumCards(); i++) {
            if (currentCard == null) {
               currentCard = game.getHand(0).inspectCard(i);
               highestCardIndex = i;
            }
            if (game.getHand(0).inspectCard(i).compareTo(currentCard) > 0) {
               currentCard = game.getHand(0).inspectCard(i);
               highestCardIndex = i;
            }
         }
         return highestCardIndex;
      }
      /* if no more cards in hands then round finished and we deal, reset scores and redraw human hand*/
      public void checkRoundFinished() {
         if (game.getHand(0).getNumCards() <= 0 && game.getHand(1).getNumCards() <= 0) {
            if (playerScores[0] > playerScores[1]) {
               playerScoresLabels[0].setText(playerScores[0] + ": Computer wins a round");
               totalScores[0]++;
            } else {
               playerScoresLabels[1].setText(playerScores[1] + ": Player wins a round");
               totalScores[1]++;
            }

            game.deal();
            resetScores();
            redrawPlayerHand();
         }
      }
      /* if no more cards in deck the game is over, check who wins */
      public void checkGameFinished() {
         if (game.getHand(1).getNumCards() <= 0 && game.getNumCardsRemainingInDeck() <= 0) {
            if (totalScores[0] > totalScores[1]) {
               playerScoresLabels[0].setFont(new Font("Serif", Font.BOLD, 18));
               playerScoresLabels[0].setText("Computer won a game");
               playerScoresLabels[1].setText("");
            } else if (totalScores[0] < totalScores[1]) {
               playerScoresLabels[1].setFont(new Font("Serif", Font.BOLD, 18));
               playerScoresLabels[1].setText("You won a game");
               playerScoresLabels[0].setText("");
            } else {
               playerScoresLabels[0].setFont(new Font("Serif", Font.BOLD, 18));
               playerScoresLabels[1].setFont(new Font("Serif", Font.BOLD, 18));
               playerScoresLabels[0].setText("Tie");
               playerScoresLabels[1].setText("Game");
            }
            playerWinner = false;
            compWinner = false;
         }
      }
      
      /* redraw human hand every time 2 cards played to show how many cards left in a hand, or after a round ended to show again
      * full hand of cards */
      private void redrawPlayerHand() {
         Hand hand = game.getHand(1);
         for (int k = 0; k < NUM_CARDS_PER_HAND; k++) {
            if (k >= hand.getNumCards()) {
               humanLabels[k].setIcon(GUICard.getBackCardIcon());
            } else {
               humanLabels[k].setIcon(GUICard.getIcon(hand.inspectCard(k)));
            }
         }
      }
      /* show the new score after every 2 cards play */
      private void redrawScore() {
         for (int i = 0; i < NUM_PLAYERS; i++) {
            playerScoresLabels[i].setText(Integer.toString(playerScores[i]));
         }
      }
      /* refresh score after each round to start count againt to see who will win */
      private void resetScores() {
         for (int i = 0; i < NUM_PLAYERS; i++) {
            playerScores[i] = 0;
         }
      }
      
      /* reset winner/loser total scores after game ends */
      private void resetTotalScores() {
         for (int i = 0; i < NUM_PLAYERS; i++) {
            totalScores[i] = 0;
         }
      }
      
      /* interface MouseListener method implementation */
      public void mouseClicked(MouseEvent e) {
         makeHumanMove();
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }
   }
}

class CardTable extends JFrame {

   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2; // for now, we only allow 2 person games

   private int numCardsPerHand;
   private int numPlayers;
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

   public CardTable(String title, int numCardsPerHand, int numPlayers) {

      // test parameters validity
      if (title == null || numCardsPerHand < 0 || numCardsPerHand > MAX_CARDS_PER_HAND || numPlayers < 0
            || numPlayers > MAX_PLAYERS) {
         return;
      }

      setTitle(title);
      this.numCardsPerHand = numCardsPerHand;
      this.numPlayers = numPlayers;

      setLayout(new BorderLayout());

      pnlComputerHand = new JPanel();
      pnlComputerHand.setLayout(new GridLayout(1, numCardsPerHand));
      pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
      add(pnlComputerHand, BorderLayout.NORTH);

      pnlHumanHand = new JPanel();
      pnlHumanHand.setLayout(new GridLayout(1, numCardsPerHand));
      pnlHumanHand.setBorder(new TitledBorder("Your Hand"));
      add(pnlHumanHand, BorderLayout.SOUTH);

      pnlPlayArea = new JPanel();
      pnlPlayArea.setLayout(new GridLayout(3, numPlayers));
      pnlPlayArea.setBorder(new TitledBorder("Playing Area"));
      add(pnlPlayArea, BorderLayout.CENTER);

   }

   // accessor
   public int getNumCardsPerHand() {
      return this.numCardsPerHand;
   }

   // accessor
   public int getNumPlayers() {
      return this.numPlayers;
   }
}
