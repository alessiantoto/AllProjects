package g62098.dev3.oxono.Model.Command;

import g62098.dev3.oxono.Model.Game;
import g62098.dev3.oxono.Model.Symbol;

public class MoveTotemCommand implements Command {
    private Game game;
    private Symbol totemSymbol;        // The symbol of the totem (X or O)
    private int oldX, oldY, newX, newY; // Old and new coordinates for the totem

    // Constructor to initialize the move command with the board, totem's symbol, and new coordinates
    public MoveTotemCommand(Game game, Symbol totemSymbol, int newX, int newY) {
        this.game = game;
        this.totemSymbol = totemSymbol;

        // Use Game to find the totem's current position
        int[] oldPosition = game.getBoard().findTotemPosition(totemSymbol);
        this.oldX = oldPosition[0];
        this.oldY = oldPosition[1];

        this.newX = newX;
        this.newY = newY;
    }

    // Executes the move operation by calling the board's move method
    @Override
    public void execute() {
        game.moveTotem(totemSymbol, newX, newY);
    }

    // Undoes the move operation by restoring the totem to its original coordinates
    @Override
    public void undo() {
        game.moveTotem(totemSymbol, oldX, oldY);
    }
}
