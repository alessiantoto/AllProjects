package g62098.dev3.oxono.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Player} class represents a player in the Oxono game.
 * Each player has a color (PINK or BLACK) and a set of pieces, marked with either "X" or "O".
 */
public class Player {
    private OxonoColor oxonoColor;
    private List<Piece> piecesX; // Stack of X pieces
    private List<Piece> piecesO; // Stack of O pieces

    /**
     * Constructor to initialize the player with a specific color and their pieces.
     *
     * @param oxonoColor The color of the player (PINK or BLACK).
     */
    public Player(OxonoColor oxonoColor) {
        this.oxonoColor = oxonoColor;
        this.piecesX = new ArrayList<>(); // Stack for X pieces
        this.piecesO = new ArrayList<>(); // Stack for O pieces
        initializePieces(); // Initialize the pieces
    }

    /**
     * Initializes the player's pieces. Creates 8 X pieces and 8 O pieces.
     */
    private void initializePieces() {
        piecesX.clear();
        piecesO.clear();
        // Create 8 pieces for each symbol
        for (int i = 0; i < 8; i++) {
            piecesX.add(new Piece(oxonoColor, Symbol.X)); // Add an X piece
            piecesO.add(new Piece(oxonoColor, Symbol.O)); // Add an O piece
        }
    }


    /**
     * Checks if the player has any available X pieces left.
     *
     * @return true if there are available X pieces, false otherwise.
     */
    public boolean hasAvailablePiecesX() {
        return !piecesX.isEmpty();
    }

    /**
     * Checks if the player has any available O pieces left.
     *
     * @return true if there are available O pieces, false otherwise.
     */
    public boolean hasAvailablePiecesO() {
        return !piecesO.isEmpty();
    }

    /**
     * Gets the color of the player.
     *
     * @return the color of the player (PINK or BLACK).
     */
    public OxonoColor getColor() {
        return oxonoColor; // Return the player's color
    }

    /**
     * Gets the list of X pieces for the player.
     *
     * @return the list of X pieces.
     */
    public List<Piece> getPiecesX() {
        return piecesX; // Return the list of X pieces for the player
    }

    /**
     * Gets the list of O pieces for the player.
     *
     * @return the list of O pieces.
     */
    public List<Piece> getPiecesO() {
        return piecesO; // Return the list of O pieces for the player
    }
}
