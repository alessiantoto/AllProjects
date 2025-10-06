package g62098.dev3.oxono.Controller;

import g62098.dev3.oxono.Model.*;
import g62098.dev3.oxono.Model.Facade.Facade;
import javafx.scene.control.Alert;

import java.util.List;

/**
 * The {@code ControllerJavaFX} class handles the logic of user interactions for the JavaFX application.
 * It connects the user interface components with the game logic via the facade.
 */
public class ControllerJavaFX {

    private Facade facade;

    private boolean isTotemSelected = false; // Indicates if a totem is selected
    private Symbol selectedTotemSymbol = null;
    private int selectedTotemX = -1; // X position of the selected totem
    private int selectedTotemY = -1; // Y position of the selected totem

    /**
     * Constructs the {@code ControllerJavaFX} with the given facade.
     *
     * @param facade The facade instance used to interact with the game logic.
     */
    public ControllerJavaFX(Facade facade) {
        this.facade = facade;
    }

    /**
     * Handles the undo action triggered by the user.
     */
    public void handleUndo() {
        try {
            facade.undo();
            System.out.println("Undo action performed.");
        } catch (OxonoException e) {
            showAlert(e.getMessage());
        }
    }

    /**
     * Handles the redo action triggered by the user.
     */
    public void handleRedo() {
        try {
            facade.redo();
            System.out.println("Redo action performed.");
        } catch (OxonoException e) {
            showAlert(e.getMessage());
        }
    }

    /**
     * Handles the start new game action triggered by the user.
     */
    public void handleNewGame() {
        try {
            facade.startNewGame();

            isTotemSelected = false; // Indicates if a totem is selected
            selectedTotemSymbol = null;
            selectedTotemX = -1; // X position of the selected totem
            selectedTotemY = -1; // Y position of the selected totem
            System.out.println("New game started.");
        } catch (OxonoException e) {
            showAlert(e.getMessage());
        }
    }

    /**
     * Handles the exit action triggered by the user.
     */
    public void handleExit() {
        System.exit(0);
    }



    /**
     * Handles a click on a cell in the game board. This method allows for either moving a totem or placing a piece,
     * depending on the current game state.
     *
     * @param x The X coordinate of the clicked cell.
     * @param y The Y coordinate of the clicked cell.
     */
    public void handleCellClick(int x, int y) {
        if (!facade.getTotemMoved()) {
            // Step 1 : move the totem
            if (!isTotemSelected) {
                // Select the totem
                if (facade.getBoard().getGrid()[x][y].isOccupied() &&
                        facade.getBoard().getGrid()[x][y].getOccupiedBy().getMovable()) {

                    Symbol symbol = facade.getBoard().getGrid()[x][y].getOccupiedBy().getSymbol();
                    // Check if there are remaining pieces for the symbol
                    if (!hasRemainingPieces(symbol)) {
                        showAlert("No remaining pieces available for " + symbol + "!");
                        return; // Prevent selection
                    }

                    isTotemSelected = true;
                    selectedTotemX = x;
                    selectedTotemY = y;
                    // Totem move
                    selectedTotemSymbol = facade.getBoard().getGrid()[selectedTotemX][selectedTotemY].getOccupiedBy().getSymbol();
                    facade.notifyTotemSelected();

                } else {
                    showAlert("Select an totem first.");
                }
            } else {

                try {
                    facade.moveTotem(
                            selectedTotemSymbol,
                            x, y
                    );
                    isTotemSelected = false;
                    selectedTotemX = -1;
                    selectedTotemY = -1;
                } catch (Exception e) {
                    showAlert("Totem move error : " + e.getMessage());
                }
            }
        } else {
            // Step 2 : Piece placement
            try {
                boolean hasWon = facade.placePiece(facade.getCurrentPlayerColor(), selectedTotemSymbol, x, y);
                selectedTotemSymbol = null;

                if (hasWon) {
                    showWinnerPopup(facade.getCurrentPlayerColor());
                } else {
                    facade.playAITurn(facade.getAIPlayerColor(), facade.getGame());
                }
            } catch (Exception e) {
                showAlert("Piece placement error : " + e.getMessage());
            }
        }

    }

    // Method to check if there are remaining pieces for a given symbol
    private boolean hasRemainingPieces(Symbol totemSymbol) {
        if (totemSymbol == Symbol.X) {
            return getCurrentPlayerRamainingX() > 0;
        } else if (totemSymbol == Symbol.O) {
            return getCurrentPlayerRamainingO() > 0;
        }
        return false;
    }

    public List<int[]> getValidTotemMoves(Symbol totemSymbol){
        return facade.getValidTotemMoves(totemSymbol);
    }

    public List<int[]> getValidPiecesPlaces(Symbol pieceSymbol){
        return facade.getValidPiecesPlaces(pieceSymbol);
    }

    public OxonoColor getCurrentPlayerColor(){
        return facade.getCurrentPlayerColor();
    }
    public int getCurrentPlayerRamainingX(){
        return facade.getCurrentPlayer(getCurrentPlayerColor()).getPiecesX().size();
    }

    public int getCurrentPlayerRamainingO(){
        return facade.getCurrentPlayer(getCurrentPlayerColor()).getPiecesO().size();
    }


    /**
     * Displays an error pop-up with the given message.
     *
     * @param message The error message to display in the pop-up.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a pop-up message indicating the winner.
     *
     * @param winnerColor The color of the winning player (PINK or BLACK).
     */
    private void showWinnerPopup(OxonoColor winnerColor) {
        String winnerText = (winnerColor == OxonoColor.PINK) ? "The player PINK won !" : "The player BLACK won !";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations !");
        alert.setHeaderText("A player won !");
        alert.setContentText(winnerText);

        alert.showAndWait();
    }

    public void setPlayerColors(OxonoColor currentPlayerColor){
        facade.setPlayerColor(currentPlayerColor);
    }

    public void registerObserver(Observer o){
        facade.registerObserver(o);
    }

    public Board getBoard(){
        return facade.getBoard();
    }

    public boolean getIsTotemSelected(){
        return isTotemSelected;
    }

    public int getSelectedTotemX() {
        return selectedTotemX;
    }

    public int getSelectedTotemY() {
        return selectedTotemY;
    }

    public Symbol getSelectedTotemSymbol() {
        return selectedTotemSymbol;
    }
}
