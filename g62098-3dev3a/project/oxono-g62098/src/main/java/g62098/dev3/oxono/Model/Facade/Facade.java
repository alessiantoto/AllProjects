package g62098.dev3.oxono.Model.Facade;


import g62098.dev3.oxono.Model.*;

import java.util.List;

public interface Facade {

    void startNewGame();
    Player getCurrentPlayer(OxonoColor playerColor);

    // Method to move a totem
    void moveTotem(Symbol totemSymbol, int x, int y);

    // Method to get valid totem moves
    List<int[]> getValidTotemMoves(Symbol totemSymbol);

    // Method to get valid pieces places

    List<int[]> getValidPiecesPlaces(Symbol pieceSymbol);

    // Method to place a piece on the board
    boolean placePiece(OxonoColor playerOxonoColor, Symbol symbol, int x, int y);

    // Method for the AI to play its turn
    void playAITurn(OxonoColor AIOxonoColor, Game game);

    // Method to set the color of the player (PINK or BLACK)
    void setPlayerColor(OxonoColor playerColor);

    // Method to undo the last action
    void undo();

    // Method to redo the last undone action
    void redo();

    // Get the color of the player
    OxonoColor getCurrentPlayerColor();

    // Get the color of the AI player
    OxonoColor getAIPlayerColor();

    // Get the current game board
    Board getBoard();
    Game getGame();

    // Method to register an observer (used for notifying about game updates)
    void registerObserver(Observer o);
    boolean getTotemMoved();
    void notifyTotemSelected();
}
