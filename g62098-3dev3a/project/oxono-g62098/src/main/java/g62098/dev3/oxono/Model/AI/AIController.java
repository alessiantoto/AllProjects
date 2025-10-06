package g62098.dev3.oxono.Model.AI;

import g62098.dev3.oxono.Model.Board;
import g62098.dev3.oxono.View.ConsoleView;

/**
 * The AIController class is responsible for managing the interactions between the AI strategy,
 * the game board, and the console view in the Oxono game. It acts as a bridge to control the AI's actions.
 */
public class AIController {

    private final ConsoleView consoleView; // The console view for displaying AI actions and feedback.
    private final Board board; // The current state of the game board.
    private final AIStrategy aiStrategy; // The strategy algorithm used by the AI.

    /**
     * Constructs an AIController instance with the given console view, game board, and AI strategy.
     *
     * @param consoleView The console view used to display the game's progress and AI actions.
     * @param board       The game board where the AI will perform actions.
     * @param aiStrategy  The strategy algorithm defining how the AI makes decisions.
     */
    public AIController(ConsoleView consoleView, Board board, AIStrategy aiStrategy) {
        this.consoleView = consoleView;
        this.board = board;
        this.aiStrategy = aiStrategy;
    }

    // Additional methods for controlling AI actions can be implemented here.
}
