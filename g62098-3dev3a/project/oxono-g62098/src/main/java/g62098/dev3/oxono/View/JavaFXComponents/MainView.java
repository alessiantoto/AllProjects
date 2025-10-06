package g62098.dev3.oxono.View.JavaFXComponents;

import g62098.dev3.oxono.Controller.ControllerJavaFX;
import g62098.dev3.oxono.Model.Observer;
import g62098.dev3.oxono.Model.OxonoColor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The {@code MainView} class represents the main view of the game.
 * It manages the overall structure of the application, including the board view,
 * the control buttons, and the color selection dialog for the player.
 */
public class MainView extends BorderPane implements Observer {
    private BoardView boardView;
    private ControlsView controlsView;
    private InfoView infoView;  // New InfoView instance
    private OxonoColor currentPlayerColor; // Tracks the current player color (PINK or BLACK)
    private ControllerJavaFX controllerJavaFX;
    private ValidTotemMoves validTotemMovesWindow;

    /**
     * Constructor for the {@code MainView} class. Initializes the game facade,
     * board view, and controls view. Also displays a color selection dialog for the player.
     *
     * @param controllerJavaFX The controller instance used to interact with the facade logic.
     * @param primaryStage The primary stage for the application.
     */
    public MainView(ControllerJavaFX controllerJavaFX, Stage primaryStage) {
        controllerJavaFX.registerObserver(this);

        // Display a pop-up to ask the user to choose their color
        showColorSelectionDialog(primaryStage);


        // Set the player's color in the GameFacade
        controllerJavaFX.setPlayerColors(currentPlayerColor);

        this.controllerJavaFX = controllerJavaFX;
        this.boardView = new BoardView(controllerJavaFX);
        this.controlsView = new ControlsView(controllerJavaFX);

        this.infoView = new InfoView(controllerJavaFX); // Initialize InfoView

        // Initialisation de la fenêtre des mouvements valides
        this.validTotemMovesWindow = new ValidTotemMoves(controllerJavaFX);


        // Add components to the main view
        this.setCenter(boardView); // Place the board in the center
        this.setBottom(controlsView); // Place the controls at the bottom
        this.setTop(infoView); // Place the InfoView at the top

        // Update the board view
        boardView.updateView();
    }

    /**
     * Displays a pop-up dialog allowing the player to choose their color.
     *
     * @param primaryStage The primary stage of the application.
     */
    private void showColorSelectionDialog(Stage primaryStage) {
        // Create the pop-up dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Color Selection");
        alert.setHeaderText("Choose your color!");
        alert.setContentText("Are you the PINK or BLACK player?");

        // Add buttons for color selection
        ButtonType pinkButton = new ButtonType("PINK");
        ButtonType blackButton = new ButtonType("BLACK");
        alert.getButtonTypes().setAll(pinkButton, blackButton);

        // Show the alert and wait for a response
        alert.showAndWait().ifPresent(response -> {
            if (response == pinkButton) {
                currentPlayerColor = OxonoColor.PINK;
                System.out.println("You are the PINK player.");
            } else if (response == blackButton) {
                currentPlayerColor = OxonoColor.BLACK;
                System.out.println("You are the BLACK player.");
            }
        });
    }


    /**
     * Displays the main view of the game on the given primary stage.
     *
     * @param primaryStage The primary stage where the game will be displayed.
     */
    public void show(Stage primaryStage) {
        Scene scene = new Scene(this, 600, 600);
        primaryStage.setTitle("Board Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method is called when the game state is updated.
     * It refreshes the board view to reflect the current game state.
     */
    @Override
    public void update() {
        boardView.updateView();
        infoView.updateInfo();
    }
}
