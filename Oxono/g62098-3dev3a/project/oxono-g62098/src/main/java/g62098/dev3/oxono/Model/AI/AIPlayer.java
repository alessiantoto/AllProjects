package g62098.dev3.oxono.Model.AI;


import g62098.dev3.oxono.Model.*;

import java.util.Random;

/**
 * The AIPlayer class represents an artificial intelligence (AI) player in the Oxono game.
 * It determines moves for its totems and placements for its pieces based on a specified strategy.
 */
public class AIPlayer {
    private OxonoColor playerOxonoColor; // The color of the AI player's pieces (e.g., PINK or BLACK).
    private Game game;
    private AIStrategy strategy;         // The strategy used by the AI for making decisions.
    private Random random;               // Random generator for adding non-deterministic behavior.

    /**
     * Constructs an AI player with a specific color and strategy.
     *
     * @param playerOxonoColor The color associated with the AI player.
     * @param strategy         The strategy the AI will use to determine its moves.
     */
    public AIPlayer(OxonoColor playerOxonoColor, Game game,AIStrategy strategy) {
        this.playerOxonoColor = playerOxonoColor;
        this.game = game;
        this.strategy = strategy;
        this.random = new Random();
    }

    /**
     * Executes the AI's turn by performing the following steps:
     * 1. Randomly chooses a totem (Symbol.X or Symbol.O) and moves it based on the strategy.
     * 2. Attempts to place a piece corresponding to the moved totem's symbol.
     *
     * @param game The current state of the game board.
     */
    public void playTurn(Game game) {
        Symbol totemSymbol;

        // Step 1: Retrieve the current player using their color
        Player currentPlayer = game.getCurrentPlayer(playerOxonoColor);

        // Check if pieces are available for both X and O
        int availablePiecesX = currentPlayer.getPiecesX().size();
        int availablePiecesO = currentPlayer.getPiecesO().size();

        // Determine which totem to move based on piece availability
        if (availablePiecesX > 0 && availablePiecesO > 0) {
            // If both types of pieces are available, randomly choose a totem
            totemSymbol = random.nextBoolean() ? Symbol.X : Symbol.O;
        } else if (availablePiecesX > 0) {
            // If only X pieces are available, choose X totem
            totemSymbol = Symbol.X;
        } else if (availablePiecesO > 0) {
            // If only O pieces are available, choose O totem
            totemSymbol = Symbol.O;
        } else {
            // If no pieces are available for either type, skip the turn
            System.out.println("AI cannot move any totem: No pieces available for both symbols.");
            return;
        }
        // Step 2: Determine the new position for the chosen totem
        int[] move = strategy.chooseTotemMove(totemSymbol, game);

        try {
            // Move the totem to the specified position
            game.moveTotem(totemSymbol, move[0], move[1]);
            System.out.println("AI moved the totem " + totemSymbol + " to (" + move[0] + ", " + move[1] + ").");
        } catch (Exception e) {
            // Handle any errors during totem movement
            System.err.println("Error during AI totem movement: " + e.getMessage());
        }

        // Step 3: Attempt to place a piece on the board
        Symbol pieceSymbol = totemSymbol; // The piece symbol matches the moved totem's symbol
        int[] piecePlacement = strategy.choosePlacePiece(pieceSymbol, game);

        if (piecePlacement != null) {
            try {
                // Place the piece at the determined position
                game.placePiece(playerOxonoColor, pieceSymbol, piecePlacement[0], piecePlacement[1]);
                boolean hasWon = game.getHasWon(); // Check if the placement resulted in a win
                System.out.println("AI placed a piece " + pieceSymbol + " at (" + piecePlacement[0] + ", " + piecePlacement[1] + ").");

                if (hasWon) {
                    System.out.println("AI has won the game!");
                }
            } catch (OxonoException e) {
                // Handle errors during piece placement
                System.err.println("Error during AI piece placement: " + e.getMessage());
            }
        } else {
            // No valid position found for placing a piece
            System.out.println("No valid position found for placing a piece.");
        }
    }
}
