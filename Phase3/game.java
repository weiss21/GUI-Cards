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
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 
   
   static int[] playerScores = new int[NUM_PLAYERS];
   static int[] totalScores = new int[NUM_PLAYERS];
   static JLabel[] playerScoresLabels = new JLabel[NUM_PLAYERS];
   
   static int round = 0;
   static boolean playerWinner = false;
   static boolean compWinner = false;
   
   private static Card lastPlayedCard = null;

   public static void main(String[] args) {
      
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;
      
      int k;
      
      CardGameFramework highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
      
      highCardGame.deal();
      
      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // CREATE LABELS ----------------------------------------------------
      //labels for computer
      for (k = 0; k < NUM_CARDS_PER_HAND; k++) {
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
      }
      //labels for human
      for (k = 0; k < NUM_CARDS_PER_HAND; k++) {
         humanLabels[k] = new JLabel(GUICard.getIcon(highCardGame.getHand(1).inspectCard(k)));
      }
  
      // ADD LABELS TO PANELS -----------------------------------------
      //add computer labels
      for(k = 0; k < NUM_CARDS_PER_HAND; k++) {
         myCardTable.pnlComputerHand.add(computerLabels[k]);
      }
      
      //add human labels
      for(k = 0; k < NUM_CARDS_PER_HAND; k++) {
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
      
      for(k = 0; k < NUM_PLAYERS; k++) {
         myCardTable.pnlPlayArea.add(playedCardLabels[k]);
      }
      
      for(k = 0; k < NUM_PLAYERS; k++) {
         myCardTable.pnlPlayArea.add(playLabelText[k]);
      }
      for(k = 0; k < NUM_PLAYERS; k++) {
         myCardTable.pnlPlayArea.add(playerScoresLabels[k]);
      }

      // show everything to the user
      myCardTable.setVisible(true);
   }
   
   public static Card generateRandomCard() {
      int value = (int)(Math.random()* 14); 
      int suit = (int)(Math.random() * 4);
      
      String cardSuit = GUICard.turnIntIntoCardSuit(suit);
      String cardValue = GUICard.turnIntIntoCardValue(value);
      
      return new Card(cardValue.charAt(0), GUICard.turnStringToSuit(cardSuit));      
   }
   
   //inner class 
   static class DelayedGameCheckListener implements ActionListener {
      
      private final HandCardMouseListener handCardListener; 
      
      public DelayedGameCheckListener(HandCardMouseListener handCardListener) {
         this.handCardListener = handCardListener;
      }
      
      public void actionPerformed(ActionEvent e) {
         handCardListener.afterAllMoved();
      }
   }
   
   //inner class
   static class RefreshCompCardListener implements ActionListener {
      
      private final Card computerCard; 
      
      public RefreshCompCardListener(Card computerCard) {
         this.computerCard = computerCard;
      }
      
      public void actionPerformed(ActionEvent e) {
         playedCardLabels[0].setIcon(GUICard.getIcon(computerCard));
      }
   }
   
   //inner class
   static class HandCardMouseListener implements MouseListener {
      
      private final int cardIndex;
      private final CardGameFramework game;
      
      public HandCardMouseListener(int cardIndex, CardGameFramework game) {
         this.cardIndex = cardIndex;
         this.game = game;
      }

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

         //check mini winner for every 2 cards
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
      
      //is called every time 2 cards played
      private void afterAllMoved(){
         checkRoundFinished();
         checkGameFinished();
         
         playedCardLabels[0].setIcon(GUICard.getBackCardIcon());
         playedCardLabels[1].setIcon(GUICard.getBackCardIcon());
         
         if (compWinner) {
            lastPlayedCard = makeComputerMove(this.game);
         }
      }

      /*
       * Makes computer move, updates card place of computer and returns card 
       * played by computer. 
       */
      public static Card makeComputerMove(CardGameFramework game) {
         int computerCardIndex = chooseCompHighestCard(game);
        
         Card computerCard = game.getHand(0).playCard(computerCardIndex);
         
         //delay quick change of computers cards so human can have a time to see results
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
         for (int i = 0; i < game.getHand(0).getNumCards() ; i++) {
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

      public void checkRoundFinished() {
         if (game.getHand(0).getNumCards() <= 0 && game.getHand(1).getNumCards() <= 0) {
            if (playerScores[0] > playerScores[1]) {
               // playerScoresLabels[0].setFont(new Font("Serif", Font.BOLD, 18));
               playerScoresLabels[0].setText(playerScores[0] + ": Computer wins a round");
               totalScores[0]++;
               // playerScoresLabels[0].setFont(new Font("Times New Roman", Font.PLAIN, 14));
            } else {
               // playerScoresLabels[1].setFont(new Font("Serif", Font.BOLD, 18));
               playerScoresLabels[1].setText(playerScores[1] + ": Player wins a round");
               // playerScoresLabels[1].setFont(new Font("Times New Roman", Font.PLAIN, 14));
               totalScores[1]++;
            }

            game.deal();
            round++;
            resetScores();
            redrawPlayerHand();
            // resetFont();
         }
      }

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
      
      
      
      
      private void resetFont() {
         for (int i = 0; i < NUM_PLAYERS; i++) {
            playerScoresLabels[i].setFont(new Font("Times New Roman", Font.PLAIN, 14));
         }
      }

      private void redrawPlayerHand() {
         Hand hand = game.getHand(1);
         for(int k = 0; k < NUM_CARDS_PER_HAND; k++) {
            if (k >= hand.getNumCards()) {
               humanLabels[k].setIcon(GUICard.getBackCardIcon());
            } else {
               humanLabels[k].setIcon(GUICard.getIcon(hand.inspectCard(k)));
            }
         }
      }
      
      private void redrawScore() {
         for (int i = 0; i < NUM_PLAYERS; i++) {
           playerScoresLabels[i].setText(Integer.toString(playerScores[i]));
         }
      }
      
      private void resetScores() {
         for (int i = 0; i < NUM_PLAYERS; i++) {
           playerScores[i] = 0;
         }
      }
      
      private void resetTotalScores() {
         for (int i = 0; i < NUM_PLAYERS; i++) {
            totalScores[i] = 0;
         }
      }
      
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
