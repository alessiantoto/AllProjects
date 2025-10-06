package g62098.dev3.oxono.Model.AI;

import g62098.dev3.oxono.Model.Game;
import g62098.dev3.oxono.Model.Symbol;

/**
 * The AIStrategy interface defines the contract for AI decision-making in the Oxono game.
 * It provides methods for choosing a totem's move and deciding where to place a piece.
 */
public interface AIStrategy {

    /**
     * Determines the next move for a specific totem based on the current state of the board.
     *
     * @param totemSymbol The symbol of the totem (X or O) to be moved.
     * @param game       The current state of the game board.
     * @return An array of two integers representing the target coordinates [x, y] for the totem's move.
     */
    int[] chooseTotemMove(Symbol totemSymbol, Game game);

    /**
     * Determines the position to place a piece of a specific symbol based on the current state of the board.
     *
     * @param pieceSymbol The symbol of the piece (X or O) to be placed.
     * @param game       The current state of the game board.
     * @return An array of two integers representing the target coordinates [x, y] for placing the piece,
     *         or null if no valid placement is available.
     */
    int[] choosePlacePiece(Symbol pieceSymbol, Game game);
}
