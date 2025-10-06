package g62098.dev3.oxono.View.JavaFXComponents;

import g62098.dev3.oxono.Controller.ControllerJavaFX;
import g62098.dev3.oxono.Model.OxonoColor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * The {@code InfoView} class is responsible for displaying the current player's color
 * and the number of remaining X and O pieces for the player.
 */
public class InfoView extends HBox {

    private Label playerColorLabel;
    private Label remainingPlayerPiecesXLabel;
    private Label remainingPlayerPiecesOLabel;
    private ControllerJavaFX controllerJavaFX;
    private Label AIColorLabel;
    private Label remainingAIPiecesXLabel;
    private Label remainingAIPiecesOLabel;

    /**
     * Constructs the {@code InfoView} class. Initializes labels and updates the information displayed.
     *
     * @param controllerJavaFX The controller instance used to interact with the facade logic.
     */
    public InfoView(ControllerJavaFX controllerJavaFX) {
        this.controllerJavaFX = controllerJavaFX;

        playerColorLabel = new Label();
        remainingPlayerPiecesXLabel = new Label();
        remainingPlayerPiecesOLabel = new Label();

        AIColorLabel = new Label();
        remainingAIPiecesXLabel = new Label();
        remainingAIPiecesOLabel = new Label();

        this.getChildren().addAll(playerColorLabel, remainingPlayerPiecesXLabel, remainingPlayerPiecesOLabel,AIColorLabel, remainingAIPiecesXLabel, remainingAIPiecesOLabel);
        this.setSpacing(10);

        // Update the labels with the current game state
        updateInfo();
    }

    /**
     * Updates the displayed information about the current player and the number of remaining pieces.
     */
    public void updateInfo() {
        OxonoColor currentPlayerColor = controllerJavaFX.getCurrentPlayerColor();

        // Display the current player's color
        playerColorLabel.setText("Player: " + currentPlayerColor);

        // Display the number of remaining pieces for X and O
        remainingPlayerPiecesXLabel.setText("Remaining X: " + controllerJavaFX.getCurrentPlayerRamainingX());
        remainingPlayerPiecesOLabel.setText("Remaining O: " + controllerJavaFX.getCurrentPlayerRamainingO());

    }

}
