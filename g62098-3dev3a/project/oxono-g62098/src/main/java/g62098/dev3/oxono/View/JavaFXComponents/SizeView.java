package g62098.dev3.oxono.View.JavaFXComponents;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class SizeView {
    private int gridSize;
    public SizeView(Stage primaryStage){
        showGridSizeSelectionDialog(primaryStage);
    }

    private int showGridSizeSelectionDialog(Stage primaryStage) {
        // Create the pop-up dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Grid Size Selection");
        alert.setHeaderText("Choose the size of the game grid");
        alert.setContentText("Choose a grid size (6x6, 8x8, or 10x10):");

        // Add buttons for size selection
        ButtonType sixBySixButton = new ButtonType("6x6");
        ButtonType eightByEightButton = new ButtonType("8x8");
        ButtonType tenByTenButton = new ButtonType("10x10");
        alert.getButtonTypes().setAll(sixBySixButton, eightByEightButton, tenByTenButton);

        // Show the alert and wait for a response
        alert.showAndWait().ifPresent(response -> {
            //gridSize = 6;
            if (response == sixBySixButton) {
                gridSize = 6;
            } else if (response == eightByEightButton) {
                gridSize = 8;
            } else if (response == tenByTenButton) {
                gridSize = 10;
            }
        });

        return gridSize;  // Return the selected grid size
    }

    /**
     * Get the selected grid size.
     * @return the selected grid size.
     */
    public int getSelectedGridSize() {
        return gridSize;
    }
}
