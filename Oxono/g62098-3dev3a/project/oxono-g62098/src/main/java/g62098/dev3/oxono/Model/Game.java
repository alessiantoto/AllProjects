package g62098.dev3.oxono.Model;

import g62098.dev3.oxono.Model.Command.Command;
import g62098.dev3.oxono.Model.Command.CommandManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The Game class represents the main logic for managing the game.
 * It controls the board, players, and rules for totem movement and placement.
 */
public class Game {

    private Player player1;
    private Player player2;
    private boolean hasWon = false;
    private Symbol lastMovedTotem = null;
    private CommandManager commandManager;
    private boolean totemMoved;

    private Board board;
    private Cell[][] grid; // The game grid, representing the board's cells.
    /**
     * Constructor for the Game class.
     *
     * @param board The game board to be used.
     */
    public Game(Board board){
        this.commandManager = new CommandManager();
        this.board = board;
        this.grid = board.getGrid();

        this.player1 = board.getPlayer1();
        this.player2 = board.getPlayer2();
    }



    /**
     * Moves a totem from its current position to a new position.
     *
     * @param totemSymbol The symbol of the totem (e.g., X or O).
     * @param newX        The target X coordinate.
     * @param newY        The target Y coordinate.
     * @throws OxonoException If the move is invalid.
     */
    public void moveTotem(Symbol totemSymbol, int newX, int newY) throws OxonoException{
        //
        // Trouver la position actuelle du totem
        int[] currentPosition = board.findTotemPosition(totemSymbol);
        int currentX = currentPosition[0];
        int currentY = currentPosition[1];


        // Si la position est invalidée (lorsque le totem n'est pas trouvé)
        if (currentX == -1 || currentY == -1) {
            throw new OxonoException("Symbole invalide. Utilisez Symbol.X ou Symbol.O.");
        }
        System.out.println("Vous voulez deplacer le totem");


        if(checkMoveValidity(totemSymbol,newX,newY)){
            grid[newX][newY].occupy(new Totem(totemSymbol));
            //System.out.println("Le totem est maintenant à: "+totem1X+" et "+totem1Y);

            board.updateCorrespondantTotemPosition(totemSymbol,newX,newY);

            // Libérer la cellule actuelle
            grid[currentX][currentY].deleteOccupant();

            lastMovedTotem = totemSymbol;
            totemMoved = true;

        }
        else{
            throw new OxonoException("The move is invalid");
        }



    }

    /**
     * Finds the first free cell in a specific direction from a given position.
     *
     * @param totemX    The starting X coordinate of the totem.
     * @param totemY    The starting Y coordinate of the totem.
     * @param direction The direction to search (e.g., "up", "down", "left", "right").
     * @return An array containing the coordinates of the free cell, or null if no free cell is found.
     */
    public int[] findFreeCellInDirection(int totemX, int totemY, String direction) {
        // Définir les changements de coordonnées pour chaque direction
        int dx = 0, dy = 0;

        switch (direction.toLowerCase()) {
            case "up":
                dx = -1; dy = 0;  // Se déplace vers le haut
                break;
            case "down":
                dx = 1; dy = 0;   // Se déplace vers le bas
                break;
            case "left":
                dx = 0; dy = -1;  // Se déplace vers la gauche
                break;
            case "right":
                dx = 0; dy = 1;   // Se déplace vers la droite
                break;
            default:
                return null;  // Direction invalide, retourner null
        }

        // Initialiser les coordonnées de départ
        int currentX = totemX;
        int currentY = totemY;

        // Chercher la première case libre dans la direction donnée
        while (currentX >= 0 && currentX < grid.length && currentY >= 0 && currentY < grid[0].length) {
            // Si la cellule est libre
            if (!grid[currentX][currentY].isOccupied()) {
                // Retourner les coordonnées de la première cellule libre trouvée
                return new int[]{currentX, currentY};
            }

            // Se déplacer à la cellule suivante dans la direction
            currentX += dx;
            currentY += dy;
        }

        // Si aucune cellule libre n'a été trouvée, retourner null
        return null;
    }


    /**
     * Checks if a totem can move to a target position based on game rules.
     *
     * @param totemSymbol The symbol of the totem.
     * @param targetX     The target X coordinate.
     * @param targetY     The target Y coordinate.
     * @return True if the move is valid, false otherwise.
     */
    public boolean checkMoveValidity(Symbol totemSymbol, int targetX, int targetY) {

        if (!board.isValidPlacement(targetX, targetY)) {
            return false;
        }

        int[] currentPosition = board.findTotemPosition(totemSymbol);
        int totemX = currentPosition[0];
        int totemY = currentPosition[1];

        // Check if the totem can jump
        if (canJump(totemSymbol, totemX, totemY, targetX, targetY)) {
            return true; // Allow the jump if valid
        }


        // Check if the path is clear for horizontal or vertical movement
        if (!isPathBlocked(totemX, totemY, targetX, targetY)) {
            return true; // The path is clear for movement
        }


        // Allow any move if the totem is completely blocked
        if (isTotemBlockedVerticalHorizontal(totemSymbol)) {
            return true; // Allow movement to any free cell
        }

        return false;   // The move is invalid
    }


    /**
     * Checks if a totem can jump to a target position.
     *
     * @param totemSymbol The symbol of the totem.
     * @param currentX    The current X coordinate.
     * @param currentY    The current Y coordinate.
     * @param targetX     The target X coordinate.
     * @param targetY     The target Y coordinate.
     * @return True if the jump is valid, false otherwise.
     */
    public boolean canJump(Symbol totemSymbol, int currentX, int currentY, int targetX, int targetY) {
        if (isTotemEnclaved(totemSymbol)) {
            List<int[]> freeCells = new ArrayList<>();

            String[] directions = {"up", "down", "left", "right"};

            for (String direction : directions) {
                int[] freeCell = findFreeCellInDirection(currentX, currentY, direction);

                if (freeCell != null && !grid[freeCell[0]][freeCell[1]].isOccupied()) {
                    freeCells.add(freeCell);
                }
            }

            for (int[] cell : freeCells) {
                if (cell[0] == targetX && cell[1] == targetY) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if a totem is blocked in all vertical and horizontal directions.
     *
     * @param totemSymbol The symbol of the totem.
     * @return True if the totem is blocked, false otherwise.
     */
    public boolean isTotemBlockedVerticalHorizontal(Symbol totemSymbol) {
        int[] currentPosition = board.findTotemPosition(totemSymbol);
        int totemX = currentPosition[0];
        int totemY = currentPosition[1];

        // Check all directions for a free cell
        String[] directions = {"up", "down", "left", "right"};

        for (String direction : directions) {
            int[] freeCell = findFreeCellInDirection(totemX, totemY, direction);

            if (freeCell != null) {
                return false; // Not blocked if a free cell exists
            }
        }
        return true;
    }

    /**
     * Checks if the path between two positions on the grid is blocked.
     *
     * @param startX the starting x-coordinate
     * @param startY the starting y-coordinate
     * @param endX   the ending x-coordinate
     * @param endY   the ending y-coordinate
     * @return true if the path is blocked, false otherwise
     */
    public boolean isPathBlocked(int startX, int startY, int endX, int endY) {
        // Check if the movement is horizontal (same row) or vertical (same column)
        if (startX != endX && startY != endY) {
            return true;
        }

        // Check horizontal movement (same row)
        if (startX == endX) {
            int minY = Math.min(startY, endY);
            int maxY = Math.max(startY, endY);
            for (int y = minY + 1; y < maxY; y++) {
                if (grid[startX][y].isOccupied()) {
                    return true; // A piece is blocking the path
                }
            }
        }

        // Check vertical movement (same column)
        if (startY == endY) {
            int minX = Math.min(startX, endX);
            int maxX = Math.max(startX, endX);
            for (int x = minX + 1; x < maxX; x++) {
                if (grid[x][startY].isOccupied()) {
                    return true; // A piece is blocking the path
                }
            }
        }

        return false; // No blockage detected
    }

    /**
     * Places a piece on the board for a specific player.
     *
     * @param playerOxonoColor the color of the player placing the piece
     * @param symbol           the symbol of the piece to place (X or O)
     * @param x                the x-coordinate for the placement
     * @param y                the y-coordinate for the placement
     * @throws OxonoException if the placement is invalid or the player has no available pieces of the specified symbol
     */
    public void placePiece(OxonoColor playerOxonoColor, Symbol symbol, int x, int y) throws OxonoException{



        // Check the validity of the placement
        if (!checkPlaceValidity(symbol, x, y)) {
            throw new OxonoException("Placement invalide à la position (" + x + ", " + y + ") pour la pièce de symbole " + symbol + ".");
        }

        // Retrieve the player based on the color
        Player player = (playerOxonoColor == player1.getColor()) ? player1 : player2;
        // Check if the player has available pieces of the selected symbol (X or O)
        Piece pieceToPlace;
        if (symbol == Symbol.X && player.hasAvailablePiecesX()) {
            pieceToPlace = player.getPiecesX().remove(0); // Remove one X piece from the player
        } else if (symbol == Symbol.O && player.hasAvailablePiecesO()) {
            pieceToPlace = player.getPiecesO().remove(0); // Remove one O piece from the player
        } else {
            throw new OxonoException("Le joueur " + playerOxonoColor + " n'a plus de pièces disponibles de type " + symbol + ".");
        }

        // Place the piece on the board
        grid[x][y].occupy(pieceToPlace);
        System.out.println("Pièce de type " + symbol + " placée à (" + x + ", " + y + ") par le joueur " + playerOxonoColor);

        totemMoved = false;

        commandManager.clearHistory();
        // Check if the player has won
        hasWon = hasPlayerWon(playerOxonoColor, symbol, x, y);
        if (hasWon) {

            System.out.println("Le joueur " + playerOxonoColor + " a gagné !");
        }
    }

    /**
     * Checks if a piece is adjacent to a matching totem.
     *
     * @param pieceSymbol the symbol of the piece to check adjacency for
     * @param x           the x-coordinate of the piece
     * @param y           the y-coordinate of the piece
     * @return true if the piece is adjacent to a matching totem, false otherwise
     */
    private boolean isAdjacentToMatchingTotem(Symbol pieceSymbol, int x, int y) {
        // Check if the piece matches the last moved totem
        if (pieceSymbol != lastMovedTotem) {
            return false;
        }
        int[] currentPosition = board.findTotemPosition(pieceSymbol);
        int totemX = currentPosition[0];
        int totemY = currentPosition[1];

        // Check adjacency using a pre-calculated list
        List<int[]> adjacentPositions = getAdjacentPositions(totemX, totemY);
        for (int[] position : adjacentPositions) {
            if (position[0] == x && position[1] == y) {
                return true;
            }
        }

        return false;
    }


    /**
     * Checks if the player has won based on the given position and symbol/color alignment.
     *
     * @param playerOxonoColor the color of the player to check
     * @param symbol           the symbol of the piece placed
     * @param x                the x-coordinate of the piece placed
     * @param y                the y-coordinate of the piece placed
     * @return true if the player has won, false otherwise
     */

    public boolean hasPlayerWon(OxonoColor playerOxonoColor, Symbol symbol, int x, int y) {
        // Check alignment based on the symbol
        if (checkAlignment(symbol, x, y)) {
            return true;
        }

        // Check alignment based on the player's color
        if (checkAlignment(playerOxonoColor, x, y)) {
            return true;
        }

        return false; // No winning alignment found
    }


    /**
     * Checks for alignment of at least 4 consecutive pieces with the same symbol.
     *
     * @param symbol the symbol to check alignment for
     * @param x      the x-coordinate of the piece
     * @param y      the y-coordinate of the piece
     * @return true if an alignment of 4 or more is found, false otherwise
     */
    public boolean checkAlignment(Symbol symbol, int x, int y) {
        int count = 1; // Start counting from the placed piece

        // Check horizontally (left and right)
        count += countInDirection(symbol, x, y, -1, 0); // Left
        count += countInDirection(symbol, x, y, 1, 0);  // Right

        // If horizontal alignment of 4 or more is found, return true
        if (count >= 4) {
            return true;
        }

        count = 1; // Reset counter for vertical check

        // Check vertically (up and down)
        count += countInDirection(symbol, x, y, 0, -1); // Up
        count += countInDirection(symbol, x, y, 0, 1);  // Down

        // If vertical alignment of 4 or more is found, return true
        if (count >= 4) {
            return true;
        }

        return false;
    }


    /**
     * Checks for alignment of at least 4 consecutive pieces with the same player's color.
     *
     * @param playerOxonoColor the color of the player to check alignment for
     * @param x                the x-coordinate of the piece
     * @param y                the y-coordinate of the piece
     * @return true if an alignment of 4 or more is found, false otherwise
     */
    public boolean checkAlignment(OxonoColor playerOxonoColor, int x, int y) {
        int count = 1; // Start counting from the placed piece

        // Check horizontally (left and right)
        count += countInDirection(playerOxonoColor, x, y, -1, 0); // Left
        count += countInDirection(playerOxonoColor, x, y, 1, 0);  // Right

        // If horizontal alignment of 4 or more is found, return true
        if (count >= 4) {
            return true;
        }

        count = 1; // Reset counter for vertical check

        // Check vertically (up and down)
        count += countInDirection(playerOxonoColor, x, y, 0, -1); // Up
        count += countInDirection(playerOxonoColor, x, y, 0, 1);  // Down

        // If vertical alignment of 4 or more is found, return true
        if (count >= 4) {
            return true;
        }

        return false;
    }


    /**
     * Counts consecutive pieces in a specific direction with the same symbol.
     *
     * @param symbol  the symbol to count
     * @param startX  the starting x-coordinate
     * @param startY  the starting y-coordinate
     * @param dx      the x-direction step
     * @param dy      the y-direction step
     * @return the count of consecutive pieces in the specified direction
     */
    private int countInDirection(Symbol symbol, int startX, int startY, int dx, int dy) {
        int count = 0;
        int x = startX + dx;
        int y = startY + dy;

        // Traverse the grid within its bounds
        while (x >= 0 && x < board.getGridSize() && y >= 0 && y < board.getGridSize()
                && grid[x][y].isOccupied()
                && grid[x][y].getOccupiedBy().getSymbol() == symbol
                && !grid[x][y].getOccupiedBy().getMovable()) {
            count++;
            x += dx;
            y += dy;
        }

        return count;
    }


    /**
     * Counts consecutive pieces in a specific direction with the same player's color.
     *
     * @param playerOxonoColor the color to count
     * @param startX           the starting x-coordinate
     * @param startY           the starting y-coordinate
     * @param dx               the x-direction step
     * @param dy               the y-direction step
     * @return the count of consecutive pieces in the specified direction
     */
    private int countInDirection(OxonoColor playerOxonoColor, int startX, int startY, int dx, int dy) {
        int count = 0;
        int x = startX + dx;
        int y = startY + dy;

        // Traverse the grid within its bounds
        while (x >= 0 && x < board.getGridSize() && y >= 0 && y < board.getGridSize()
                && grid[x][y].isOccupied()
                && grid[x][y].getOccupiedBy().getColor() == playerOxonoColor
                && !grid[x][y].getOccupiedBy().getMovable()) {
            count++;
            x += dx;
            y += dy;
        }

        return count;
    }




    /**
     * Determines if a totem is enclosed (no adjacent free cells).
     *
     * @param totemSymbol the symbol of the totem (X or O)
     * @return true if the totem is enclosed, false otherwise
     * @throws OxonoException if the totem symbol is invalid
     */
    public boolean isTotemEnclaved(Symbol totemSymbol) throws OxonoException {
        // Validate the symbol
        if (totemSymbol != Symbol.X && totemSymbol != Symbol.O) {
            throw new OxonoException("Symbole invalide. Utilisez Symbol.X ou Symbol.O.");
        }

        int[] currentPosition = board.findTotemPosition(totemSymbol);
        int totemX = currentPosition[0];
        int totemY = currentPosition[1];

        // Check adjacent cells
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newX = totemX + dir[0];
            int newY = totemY + dir[1];

            // If any adjacent cell is free, the totem is not enclaved
            if (newX >= 0 && newX < board.getGridSize() && newY >= 0 && newY < board.getGridSize()
                    && !grid[newX][newY].isOccupied()) {
                return false;
            }
        }

        // If no free adjacent cell is found, the totem is enclosed
        return true;
    }



    /**
     * Validates if a piece placement is valid on the board.
     *
     * @param pieceSymbol the symbol of the piece to place
     * @param x           the x-coordinate of the placement
     * @param y           the y-coordinate of the placement
     * @return true if the placement is valid, false otherwise
     */
    public boolean checkPlaceValidity(Symbol pieceSymbol, int x, int y) {
        // Check if the position is valid and within bounds
        if (!board.isValidPlacement(x, y)) {
            return false;
        }

        // Check if all positions adjacent to the totem are occupied
        if (allAdjacentPiecesOccupied(pieceSymbol)) {
            // If all adjacent positions are occupied, placement is allowed anywhere
            return true;
        }

        // Check adjacency to the totem of the same symbol
        if (!isAdjacentToMatchingTotem(pieceSymbol, x, y)) {
            return false;
        }

        // If no invalidating conditions are met, placement is valid
        return true;
    }

    /**
     * Checks if all adjacent positions to a totem are occupied.
     *
     * @param totemSymbol the symbol of the totem (X or O)
     * @return true if all adjacent positions are occupied, false otherwise
     */
    public boolean allAdjacentPiecesOccupied(Symbol totemSymbol){
        int[] currentPosition = board.findTotemPosition(totemSymbol);
        int totemX = currentPosition[0];
        int totemY = currentPosition[1];
        // Get adjacent positions around the totem
        List<int[]> adjacentPositions = getAdjacentPositions(totemX, totemY);

        // Check if all adjacent positions are occupied
        for (int[] position : adjacentPositions) {
            int adjX = position[0];
            int adjY = position[1];

            // If any adjacent position is free, return false
            if (!grid[adjX][adjY].isOccupied()) {
                return false;
            }
        }
        // If no free positions are found, return true
        return true;
    }



    /**
     * Retrieves a list of positions adjacent to a given coordinate on the grid.
     *
     * @param x the x-coordinate of the starting position
     * @param y the y-coordinate of the starting position
     * @return a list of integer arrays representing adjacent positions
     */

    public List<int[]> getAdjacentPositions(int x, int y) {
        List<int[]> adjacentPositions = new ArrayList<>();

        // Check adjacent directions (up, down, left, right)
        if (x > 0) { // up
            adjacentPositions.add(new int[] {x - 1, y});
        }
        if (x < grid.length - 1) { // down
            adjacentPositions.add(new int[] {x + 1, y});
        }
        if (y > 0) { // left
            adjacentPositions.add(new int[] {x, y - 1});
        }
        if (y < grid[0].length - 1) { // right
            adjacentPositions.add(new int[] {x, y + 1});
        }

        return adjacentPositions;
    }

    public List<int[]> getValidTotemMoves(Symbol totemSymbol) {
        List<int[]> validMoves = new ArrayList<>();

        for (int x = 0; x < board.getGridSize(); x++) {
            for (int y = 0; y < board.getGridSize(); y++) {
                if (checkMoveValidity(totemSymbol, x, y)) {
                    validMoves.add(new int[]{x, y});
                }
            }
        }

        return validMoves;
    }

    public List<int[]> getValidPiecesPlaces(Symbol pieceSymbol){
        List<int[]> validPlaces = new ArrayList<>();

        for (int x = 0; x < board.getGridSize(); x++) {
            for (int y = 0; y < board.getGridSize(); y++) {
                if (checkPlaceValidity(pieceSymbol, x, y)) {
                    validPlaces.add(new int[]{x, y});
                }
            }
        }

        return validPlaces;
    }


    public Player getCurrentPlayer(OxonoColor playerColor) {
        if (getBoard().getPlayer1().getColor() == playerColor) {
            return getBoard().getPlayer1();
        } else {
            return getBoard().getPlayer2();
        }
    }


    public void startNewGame() {

        board.initializeCells();
        board.setupPlayers();

        board.setupInitialPosition();
        commandManager.clearHistory();
    }

    public void executeCommand(Command command){
        commandManager.executeCommand(command);
    }
    public void undo() throws OxonoException{
        if(totemMoved){
            commandManager.undo();
            totemMoved = false;
        }
        else{
            throw new OxonoException("Tu dois deplacer un totem avant de faire undo");
        }
    }
    public void redo(){
        commandManager.redo();
    }

    public void setBoard(Board board){
        this.board = board;
    }
    public Board getBoard(){
        return board;
    }
    public boolean getTotemMoved(){
        return totemMoved;
    }

    public boolean getHasWon(){
        return hasWon;
    }

}
