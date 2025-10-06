package g62098.dev3.oxono.Model;

import java.util.*;

public class Board {
    private Cell[][] grid;
    private int gridSize; // Size of the grid
    private Player player1; // First player
    private Player player2; // Second player
    private int totem1X, totem1Y; // Coordinates of the first totem
    private int totem2X, totem2Y; // Coordinates of the second totem


    /**
     * Constructs a new Board with a specified grid size and initializes
     * the cells, players, and the initial positions of totems.
     */
    public Board(int gridSize) {
        this.gridSize = gridSize;
        grid = new Cell[gridSize][gridSize];
        initializeCells();
        setupPlayers(); // Initialisation des joueurs
        setupInitialPosition();
    }

    /**
     * Initializes all cells in the grid by creating new {@link Cell} objects for each position.
     */
    public void initializeCells() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = new Cell(); // Create new Cell objects for each grid position
            }
        }
    }

    /**
     * Sets up the initial positions of the totems with random symbols (X or O)
     * and places them on the grid.
     */
    public void setupInitialPosition() {
        Symbol firstSymbol = getRandomSymbolTotem(); // Randomly select X or O
        Symbol secondSymbol = (firstSymbol == Symbol.X) ? Symbol.O : Symbol.X;

        Totem totem1 = new Totem(firstSymbol);
        Totem totem2 = new Totem(secondSymbol);

        // Calculate and set initial positions for the totems
        totem1X = gridSize / 2; // Example position: (3,3)
        totem1Y = gridSize / 2;

        totem2X = (gridSize / 2) - 1; // Example position: (2,2)
        totem2Y = (gridSize / 2) - 1;

        grid[totem1X][totem1Y].occupy(totem1);
        grid[totem2X][totem2Y].occupy(totem2);
    }

    /**
     * Initializes the players with different colors.
     */
    public void setupPlayers() {
        // Initialiser le joueur 1
        player1 = new Player(OxonoColor.PINK);

        // Initialiser le joueur 2
        player2 = new Player(OxonoColor.BLACK);
    }


    /**
     * Randomly selects a symbol (either X or O) for a totem.
     *
     * @return a randomly selected {@link Symbol} (X or O).
     */
    private Symbol getRandomSymbolTotem() {
        Random random = new Random();
        return random.nextBoolean() ? Symbol.X : Symbol.O;
    }

    /**
     * Checks if a placement at the specified position is valid.
     * A placement is valid if the position is within the grid and not already occupied.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @return true if the placement is valid, false otherwise
     */
    public boolean isValidPlacement(int x, int y) {
        if (x < 0 || x >= gridSize || y < 0 || y >= gridSize) {
            return false;
        }
        if (grid[x][y].isOccupied()) {
            return false;
        }
        return true;
    }

    /**
     * Updates the position of the corresponding totem when it is moved.
     *
     * @param totemSymbol the symbol of the totem to update (X or O)
     * @param newX        the new x-coordinate of the totem
     * @param newY        the new y-coordinate of the totem
     */
    public void updateCorrespondantTotemPosition(Symbol totemSymbol, int newX, int newY) {
        if (grid[totem1X][totem1Y].getOccupiedBy().getSymbol() == totemSymbol) {
            totem1X = newX;
            totem1Y = newY;
        } else {
            totem2X = newX;
            totem2Y = newY;
        }
    }

    /**
     * Finds the current position of a totem on the board.
     *
     * @param symbol the symbol of the totem (X or O)
     * @return an array containing the x and y coordinates of the totem, or null if not found
     * @throws OxonoException if no totem with the given symbol is found on the board
     */
    public int[] findTotemPosition(Symbol symbol) throws OxonoException{
        int[] position = new int[2];    // Array to store coordinates (X, Y)

        // Check position of totem X
        if (grid[totem1X][totem1Y].getOccupiedBy().getSymbol() == symbol) {
            position[0] = totem1X;
            position[1] = totem1Y;
            return position; // Return position if found
        }

        // Check position of totem O
        if (grid[totem2X][totem2Y].getOccupiedBy().getSymbol() == symbol) {
            position[0] = totem2X;
            position[1] = totem2Y;
            return position; // Return position if found
        }

        // If no totem found, throw an exception
        throw new OxonoException("Totem with symbol " + symbol + " not found on the board.");
    }



    public Cell[][] getGrid() {
        return grid;
    }

    public int getTotem1X() {
        return totem1X;
    }

    public int getTotem1Y() {
        return totem1Y;
    }

    public int getTotem2X() {
        return totem2X;
    }

    public int getTotem2Y() {
        return totem2Y;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getGridSize(){
        return gridSize;
    }

}
