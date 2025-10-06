package g62098.dev3.oxono.Model.Command;

import g62098.dev3.oxono.Model.Game;
import g62098.dev3.oxono.Model.OxonoColor;
import g62098.dev3.oxono.Model.Symbol;

public class PlacePieceCommand implements Command {
    private Game game;
    private OxonoColor playerOxonoColor; // The color of the player placing the piece (PINK or BLACK)
    private Symbol symbol;             // The symbol of the piece (X or O)
    private int x, y;                  // Coordinates where the piece will be placed

    // Constructor to initialize the command with the board, player color, symbol, and position
    public PlacePieceCommand(Game game, OxonoColor playerOxonoColor, Symbol symbol, int x, int y) {
        this.game = game;
        this.playerOxonoColor = playerOxonoColor;
        this.symbol = symbol;
        this.x = x;
        this.y = y;
    }

    // Executes the command by placing the piece on the board at the specified position
    @Override
    public void execute() {
        game.placePiece(playerOxonoColor, symbol, x, y);
    }

    // Undoes the command by removing the piece from the board at the specified position
    @Override
    public void undo() {
        game.getBoard().getGrid()[x][y].deleteOccupant(); // Removes the piece from the cell at (x, y)
    }
}
