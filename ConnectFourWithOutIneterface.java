import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interface for a Connect Four game.
 */
public interface ConnectFour {

    // Enum for players in the game
    public enum Player {X, O}
    
    /**
     * Initializes the 6x7 game grid, clearing any existing tokens.
     */
    public void init(); 
    
    /**
     * Checks if the game can continue.
     * @return true if the game is ongoing, false if there's a winner or the grid is full.
     */
    public boolean hasNext();
    
    /**
     * Gets the player whose turn is next.
     * @return the current player.
     */
    public Player getTurn();

    /**
     * Drops the current player's token into the specified column.
     * 
     * @param col the column number (0 to 6).
     * @throws IllegalArgumentException if the column number is invalid.
     * @throws IllegalStateException if the column is already full.
     */
    public void drop(int col) throws IllegalArgumentException, IllegalStateException;

    /**
     * Displays the current state of the grid to the console.
     * Empty cells are shown as underscores, and the column numbers are shown at the bottom.
     */
    public void print();

    /**
     * Determines if there is a winner.
     * @return true if a winner exists, false otherwise.
     */
    public boolean hasWinner();
    
    /**
     * Gets the winner of the game.
     * 
     * @return the player who has won.
     * @throws IllegalStateException if the game is still ongoing or if there's no winner.
     */
    public Player getWinner() throws IllegalStateException;
    
    /**
     * Creates a new ConnectFour instance using the student's ID.
     * 
     * @param sid the student's ID.
     * @return a new instance of ConnectFour.
     */
    public static ConnectFour newInstance(String sid) {
        ConnectFour instance = null;
        try {
            Class clazz = Class.forName("a2223.hw2.ConnectFour" + sid);
            instance = (ConnectFour) clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectFour.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return instance;
        }
    }
    
    /**
     * Main method to start the Connect Four game.
     * Prompts the user for their student ID to initialize the game.
     */
    public static void main(String[] args) {
        Scanner sin = new Scanner(System.in);  
        
        System.out.print("Enter your SID: ");
        ConnectFour game = newInstance(sin.next());
        game.init();
        System.out.println("");
                
        game.print();
        while(game.hasNext()) {
            while(true) {
                try {
                    System.out.print("Drop " + game.getTurn() + " at: ");
                    game.drop(sin.nextInt());
                    break;
                } catch(IllegalArgumentException e) {
                    System.out.println("Invalid column. Please choose a column between 0 and 6.");
                } catch(InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sin.nextLine();
                }
            }
            
            System.out.println("");
            game.print();
        }
        
        if(game.hasWinner())
            System.out.println("The winner is " + game.getWinner() + " !!!");
        else
            System.out.println("DRAW game!");
    }

}
