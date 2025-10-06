package g62098.dev3.oxono.View;

import g62098.dev3.oxono.Model.*;

/**
 * The {@code ConsoleView} class is responsible for displaying the game state in the console.
 * It handles various messages, such as the welcome message, the board display, the current player,
 * and game actions like displaying a menu and error messages.
 */
public class ConsoleView {

    /**
     * Displays a welcome message to the player.
     */
    public void displayWelcomeMessage() {
        System.out.println("Welcome to the Oxono game!");
    }

    /**
     * Displays the current state of the board.
     *
     * @param board The current board to display.
     */
    public void displayBoard(Board board) {
        var grid  = board.getGrid();
        System.out.println("Current board state:");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isOccupied()) {
                    Occupant occupant = grid[i][j].getOccupiedBy();
                    if (occupant != null) {
                        String coloredSymbol = getColoredSymbol(occupant.getSymbol(), occupant.getColor());
                        System.out.print(" " + coloredSymbol + " ");
                    }
                } else {
                    System.out.print(" . "); // Empty cell
                }
            }
            System.out.println(); // New line after each row
        }
    }

    /**
     * Displays a message indicating the current player.
     *
     * @param currentPlayerOxonoColor The color of the current player (PINK or BLACK).
     */
    public void displayCurrentPlayer(OxonoColor currentPlayerOxonoColor) {
        System.out.println("It's " + currentPlayerOxonoColor + "'s turn!");
    }

    /**
     * Displays the menu options for the player.
     */
    public void displayMenu() {
        System.out.println("Choose an action:");
        System.out.println("1. Move a totem");
        System.out.println("2. Place a piece");
        System.out.println("3. Undo last command");
        System.out.println("4. Redo last command");
        System.out.println("5. Quit");
    }

    /**
     * Displays a message when the player selects an invalid choice.
     */
    public void displayInvalidChoice() {
        System.out.println("Invalid choice, please try again.");
    }


    /**
     * Returns a colored representation of a symbol based on its color.
     *
     * @param symbol the symbol to display (X or O)
     * @param oxonoColor the color of the symbol (PINK, BLACK, or BLUE)
     * @return a string representing the symbol in its associated color
     */
    private String getColoredSymbol(Symbol symbol, OxonoColor oxonoColor) {
        String ansiCode;
        switch (oxonoColor) {
            case PINK:
                ansiCode = "\u001B[35m"; // Magenta for pink
                break;
            case BLACK:
                ansiCode = "\u001B[30m"; // Black
                break;
            case BLUE:
                ansiCode = "\u001B[34m"; // Blue
                break;
            default:
                ansiCode = "\u001B[0m"; // Default reset
        }
        return ansiCode + symbol + "\u001B[0m"; // Append symbol and reset color
    }
    /**
     * Displays the positions of the totems on the board.
     *
     * @param board The current board to retrieve totem positions.
     */
    public void displayTotemPositions(Board board) {
        // Retrieve the coordinates of the totems
        int totem1X = board.getTotem1X();
        int totem1Y = board.getTotem1Y();
        int totem2X = board.getTotem2X();
        int totem2Y = board.getTotem2Y();

        // Access the cells of the totems
        Cell[][] grid = board.getGrid(); // Board grid

        // Check and retrieve the occupant of each cell
        Cell cellTotem1 = grid[totem1X][totem1Y];
        Cell cellTotem2 = grid[totem2X][totem2Y];

        // Display the positions and information of the totems
        System.out.println("Totem at (" + totem1X + ", " + totem1Y + "): " + cellTotem1.getOccupiedBy().getSymbol());
        System.out.println("Totem at (" + totem2X + ", " + totem2Y + "): " + cellTotem2.getOccupiedBy().getSymbol());
    }

    /**
     * Displays a general message in the game.
     *
     * @param message The message to display.
     */
    public void displayOxonoMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays a victory message for the winner.
     *
     * @param winner The winner of the game (PINK or BLACK).
     */
    public void displayVictory(OxonoColor winner) {
        System.out.println("Player " + winner + " has won!");
    }

    /**
     * Displays a goodbye message when the game ends.
     */
    public void displayGoodbyeMessage() {
        System.out.println("Thank you for playing!");
    }
}
