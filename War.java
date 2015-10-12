public class War{
    private MultiDS<Card> deck; //the deck of the game
    private MultiDS<Card> p1hand; //the hand of player 1
    private MultiDS<Card> p2hand; //the hand of player 2
    private MultiDS<Card> p1discard; //the discard pile of player 1
    private MultiDS<Card> p2discard; //the discard pile of player 2
    private MultiDS<Card> cardsInPlay; //stores the cards in play
    int rounds; //number of rounds that the game will run for
    int roundCount; //a count of the number of rounds that have already passed
    
    /**creates a new War object that will run for the desired number of rounds. It will
     * automatically start the game of war, creating the deck of cards, shuffling them and dealing
     * them to the players, and then finally checking for the number of cards that each player has
     * when desired number of rounds has passed.
     * @param a integer of desired number of rounds for the game to run
     */
    public War(int a){
        System.out.println("Welcome to the Game of War!\n");
        rounds=a;
        roundCount=0;
        deck=new MultiDS<Card>(52);
        for (Card.Suits s: Card.Suits.values()){
            for (Card.Ranks r: Card.Ranks.values()){
                Card temp=new Card(s,r);
                deck.addItem(temp);
            }
        }
        deck.shuffle();
        System.out.println("Now dealing cards to the players...\n");
        p1hand=new MultiDS<Card>(52);
        p2hand=new MultiDS<Card>(52);
        for(int i=0;i<52;i++){
            if(i%2==0){
                p1hand.addItem(deck.removeItem());
            }
            else{
                p2hand.addItem(deck.removeItem()); 
            }
        }
        
        System.out.println("Here is Player 1's hand:\n"+p1hand.toString()+"\n");
        System.out.println("Here is Player 2's hand:\n"+p2hand.toString()+"\n");
        p1discard=new MultiDS<Card>(52);
        p2discard=new MultiDS<Card>(52);
        cardsInPlay=new MultiDS<Card>(52);
        
        this.Battle();
        
        if(roundCount==rounds){
            int p1cards=(p1hand.size()+p1discard.size());
            int p2cards=(p2hand.size()+p2discard.size());
            
            System.out.println("\nGame over!");
            System.out.println("After " + roundCount + " rounds, this is the status:");
            System.out.println("\tPlayer 1 has "+ p1cards+" cards");
            System.out.println("\tPlayer 2 has "+ p2cards+" cards");
            
            if(p1cards>p2cards){
                System.out.println("Player 1 Wins!");
            }
            else if(p2cards>p1cards){
                System.out.println("Player 2 Wins!");
            }
            else{
                System.out.println("It's a STALEMATE!");
            }
            System.exit(0);
        }
        
    }
    
    /**Method is automatically called when the War object is initialized. Starts the game of war,
     * and compares a card from each player's hand for the desired number of rounds. Before each
     * round, each player's hand is checked to see if it is empty. The cards in play are placed into
     * card objects p1 and p2, and then added to the cardsInPlay MultiDS. The winning player then
     * recieves all the cards in cardsInPlay into their discard pile. If a tie occurs, the method
     * Conflict is run, which starts a WAR.
     * 
     */
    public void Battle(){
        System.out.println("Starting the WAR!\n");
        for(roundCount=0;roundCount<rounds;roundCount++){
            this.p1checkEmpty();
            this.p2checkEmpty();
            Card p1=p1hand.removeItem();
            Card p2=p2hand.removeItem();
            cardsInPlay.addItem(p1);
            cardsInPlay.addItem(p2);
            int result = p1.compareTo(p2);
            if (result > 0){
                System.out.println("Player 1 wins: " + p1 + " beats " + p2);
                p1discard.addItem(cardsInPlay.removeItem());
                p1discard.addItem(cardsInPlay.removeItem());
                cardsInPlay.clear();
            }
            else if (result < 0){
                System.out.println("Player 2 wins: " + p1 + " loses to " + p2);
                p2discard.addItem(cardsInPlay.removeItem());
                p2discard.addItem(cardsInPlay.removeItem());
                cardsInPlay.clear();
            }
            else{
                System.out.println("WAR: " + p1 + " ties " + p2);
                this.Conflict();
            }
        }
        return;
    }
    
    /**Handles the case of a tie during the game, known as a WAR. One card is removed from each hand,
     * placed into the cardsInPlay, and are said to be at risk. Then an additional card from each 
     * hand is removed and compared to see who wins the WAR. If another tie occurs, Conflict is run
     * again, placing another card at risk. Before the cards are removed, the players' hands each
     * checked to make sure that no nulls are returned and that the player has not run out of cards.
     * The winner of the WAR recieves all the cards in cardsInPlay.
     */
    public void Conflict(){
        this.p1checkEmpty();
        this.p2checkEmpty();
        
        Card p1=p1hand.removeItem();
        cardsInPlay.addItem(p1);
        Card p2=p2hand.removeItem();
        cardsInPlay.addItem(p2);
        System.out.println("\tPlayer 1's " + p1 + " and Player 2's " + p2 + " are at risk!");
        
        this.p1checkEmpty();
        this.p2checkEmpty();
        p1=p1hand.removeItem();
        cardsInPlay.addItem(p1);
        p2=p2hand.removeItem();
        cardsInPlay.addItem(p2);
        
        int result = p1.compareTo(p2);
        if (result > 0){
            System.out.println("Player 1 wins: " + p1 + " beats " + p2);
            int x=cardsInPlay.size();
            for(int i=0;i<x;i++){
                p1discard.addItem(cardsInPlay.removeItem());
            }
            cardsInPlay.clear();
        }
        else if (result < 0){
            System.out.println("Player 2 wins: " + p1 + " loses to " + p2);
            int x=cardsInPlay.size();
            for(int i=0;i<x;i++){
                p2discard.addItem(cardsInPlay.removeItem());
            }
            cardsInPlay.clear();
        }
        else{
            System.out.println("WAR: " + p1 + " ties " + p2);
            this.Conflict();
        }
        return;
        
    }
    
    /**Checks if the hand of player 1 is empty. If it is, it runs checkWin to see if the discard
     * pile of player 1 is empty. If there are cards left in the discard pile, they will be moved
     * into player 1's hand, the hand will be shuffled, and the discard pile will be cleared.If the
     * hand is not empty, it returns void and the method that called it resumes.
     * 
     */
    public void p1checkEmpty(){
        if(p1hand.empty()){
            this.checkWin(p1discard);
            
            int x=p1discard.size();
            for(int i=0;i<x;i++){
                p1hand.addItem(p1discard.removeItem());
            }
            p1discard.clear();
            System.out.println("Player 1 is shuffling.");
            p1hand.shuffle();
            
           
        }
        return;
        
    }
    
    /**Checks if the hand of player 2 is empty. If it is, it runs checkWin to see if the discard
     * pile of player 2 is empty. If there are cards left in the discard pile, they will be moved
     * into player 2's hand, the hand will be shuffled, and the discard pile will be cleared. If the
     * hand is not empty, it returns void and the method that called it resumes.
     * 
     */
    public void p2checkEmpty(){
        if(p2hand.empty()){
            this.checkWin(p2discard);
            
            int x=p2discard.size();
            for(int i=0;i<x;i++){
                p2hand.addItem(p2discard.removeItem());
            }
            p2discard.clear();
            System.out.println("Player 2 is shuffling.");
            p2hand.shuffle();
            
           
        }
        return;
        
    }
    
    /**Checks to see if a discard pile is empty. It is only called when the hand is empty, and is
     * checking to see if the game has therefore been won. If the discard pile is empty, the hand and
     * discard piles of player 1 and 2 are compared, and the player with all the cards wins, at which
     * point the program is exited. Otherwise, it returns void and the method that called it resumes.
     * 
     */
    public void checkWin(MultiDS<Card> a){
        if(a.empty()){
            System.out.println("\nGame over!");
            if((p1hand.size()+p1discard.size())>(p2hand.size()+p2discard.size())){
                System.out.println("Player 2 has no cards left.\nPlayer 1 Wins after "+roundCount+" rounds!");
            }
            else{
                System.out.println("Player 1 has no cards left.\nPlayer 2 Wins after "+roundCount+" rounds!");
            }
            System.exit(0);
        }
        return;
    }
    
    /**the desired number of rounds must be entered in the command line. If nothing is entered, or a nonpositive
     * integer or noninteger is enter, an exception is thrown. This number is then used to create a War object, 
     * which automatically runs.
     * @param String that parses to a postive Integer.
     */
    public static void main(String [] args){
        int foo=0;
        if(args.length==0){
            throw new ArrayIndexOutOfBoundsException("You must enter an integer greater than 0.");
        }
        else if(!(args[0].matches("\\d+"))||Integer.parseInt(args[0])<=0){
            throw new IllegalArgumentException("You must enter an integer greater than 0.");
        }
        else{
            foo = Integer.parseInt(args[0]);
        }
        War game1=new War(foo);
    }
}
