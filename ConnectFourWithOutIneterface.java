package a2223.hw2;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public interface ConnectFour {

    // The mark for two game players
    public enum Player {X, O}
    
    /**
     * This method initialize the 6x7 grid. When it is called, the grid 
     * is re-initialized and all tokens currently on the grid are erased. 
     */
    public void init(); 
    
    /**
     * This method returns true if the game can continue. It returns false if a winner has
     * been determined or all grid positions have been filled.
     */
    public boolean hasNext();
    
    /**
     * This method returns the player of the current turn. 
     */
    public Player getTurn();

    /**
     * This method drops a token of the current player in the specified column.
     * 
     * @param col                           the column number ranging from 0 to 6 
     * @throws IllegalArgumentException     if the input column is out of the range
     * @throw IllegalStateException         if the input column is full
     */
    public void drop(int col) throws IllegalArgumentException, IllegalStateException;

    /**
     * This method prints the current grid state to the console (i.e. System.out).
     * The grid cells are either printed with a player's token or an underscore when
     * the position is empty. The column numbers are printed at the bottom of the grid.
     * 
     * For example, an initial look of a 6x7 grid with underscores in all cells:
     * |_|_|_|_|_|_|_|
     * |_|_|_|_|_|_|_|
     * |_|_|_|_|_|_|_|
     * |_|_|_|_|_|_|_|
     * |_|_|_|_|_|_|_|
     * |_|_|_|_|_|_|_|
     * |0|1|2|3|4|5|6|
     * 
     * Another example with 3 Xs and 2 Os.
     * |_|_|_|_|_|_|_|
     * |_|_|_|_|_|_|_|
     * |_|_|_|_|_|_|_|
     * |_|_|_|_|_|_|_|
     * |_|_|_|X|O|_|_|
     * |X|_|_|X|O|_|_|
     * |0|1|2|3|4|5|6|
     */
    public void print();

    /**
     * This method returns true if the game has a winner. It returns false when the game
     * is still in progress or there is no winner.
     */
    public boolean hasWinner();
    
    /**
     * This method returns the winner of the game.
     * 
     * @return                              the winner of the game
     * @throws IllegalStateException        if the game is in progress or has no winner
     */
    public Player getWinner() throws IllegalStateException;
    

public static ConnectFour newInstance() {
    return new ConnectFour() {
        private char[][] grid = new char[6][7];
        private Player currentPlayer = Player.X;

        @Override
        public void init() {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    grid[i][j] = '_';
                }
            }
            currentPlayer = Player.X;
        }

        @Override
        public boolean hasNext() {
            for (int i = 0; i < 7; i++) {
                if (grid[0][i] == '_') {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Player getTurn() {
            return currentPlayer;
        }

        @Override
        public void drop(int col) throws IllegalArgumentException, IllegalStateException {
            if (col < 0 || col >= 7) {
                throw new IllegalArgumentException("Invalid column");
            }

            for (int i = 5; i >= 0; i--) {
                if (grid[i][col] == '_') {
                    grid[i][col] = getTurn() == Player.X ? 'X' : 'O';
                    currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
                    return;
                }
            }

            throw new IllegalStateException("Column is full");
        }

        @Override
        public void print() {
            for (int i = 0; i < 6; i++) {
                System.out.print("|");
                for (int j = 0; j < 7; j++) {
                    System.out.print(grid[i][j] + "|");
                }
                System.out.println();
            }
            System.out.println("|0|1|2|3|4|5|6|");
        }

        @Override
        public boolean hasWinner() {
            // Check for horizontal wins
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    if (grid[i][j] != '_' && grid[i][j] == grid[i][j + 1] && grid[i][j] == grid[i][j + 2] && grid[i][j] == grid[i][j + 3]) {
                        return true;
                    }
                }
            }

            // Check for vertical wins
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 7; j++) {
                    if (grid[i][j] != '_' && grid[i][j] == grid[i + 1][j] && grid[i][j] == grid[i + 2][j] && grid[i][j] == grid[i + 3][j]) {
                        return true;
                    }
                }
            }

            // Check for diagonal wins (top-left to bottom-right)
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (grid[i][j] != '_' && grid[i][j] == grid[i + 1][j + 1] && grid[i][j] == grid[i + 2][j + 2] && grid[i][j] == grid[i + 3][j + 3]) {
                        return true;
                    }
                }
            }

            // Check for diagonal wins (top-right to bottom-left)
            for (int i = 0; i < 3; i++) {
                for (int j = 6; j >= 3; j--) {
                    if (grid[i][j] != '_' && grid[i][j] == grid[i + 1][j - 1] && grid[i][j] == grid[i + 2][j - 2] && grid[i][j] == grid[i + 3][j - 3]) {
                        return true;
                    }
                }
            }

            return false;
        }

        @Override
        public Player getWinner() throws IllegalStateException {
            if (hasWinner()) {
                return (currentPlayer == Player.X) ? Player.O : Player.X;
            } else {
                throw new IllegalStateException("Game is in progress or has no winner");
            }
        }
    };
}
    

   
public static void main(String[] args) {
    Scanner sin = new Scanner(System.in);

    ConnectFour game = newInstance();
    game.init();
    System.out.println("");

    game.print();
    while (game.hasNext()) {
        while (true) {
            try {
                System.out.print("Drop " + game.getTurn() + " at: ");
                game.drop(sin.nextInt());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong column.");
            } catch (InputMismatchException e) {
                System.out.println("Wrong input.");
                sin.nextLine();
            }
        }

        System.out.println("");
        game.print();

        if (game.hasWinner()) {
            System.out.println("The winner is " + game.getWinner() + " !!!");
            return; // Exit the main method after declaring the winner
        }
    }

    System.out.println("DRAW game!");
}
}
