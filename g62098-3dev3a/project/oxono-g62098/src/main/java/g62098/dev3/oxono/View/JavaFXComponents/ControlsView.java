package g62098.dev3.oxono.View.JavaFXComponents;

import g62098.dev3.oxono.Controller.ControllerJavaFX;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * The {@code ControlsView} class is responsible for managing the control interface of the application,
 * including buttons for undo, redo, and exit actions. The button actions are linked to methods of the facade.
 */
public class ControlsView extends HBox {

    private Button undoButton;
    private Button redoButton;
    private Button exitButton;
    private Button newGameButton;
    private ControllerJavaFX controllerJavaFX;


    /**
     * Constructs the {@code ControlsView} class. Initializes the buttons and event listeners.
     *
     * @param controllerJavaFX The facade instance used to interact with the facade.
     */
    public ControlsView(ControllerJavaFX controllerJavaFX) {

        this.controllerJavaFX = controllerJavaFX;
        undoButton = new Button("Undo");
        redoButton = new Button("Redo");
        exitButton = new Button("Exit");
        newGameButton = new Button("New Game");

        this.getChildren().addAll(undoButton, redoButton, exitButton, newGameButton);
        this.setSpacing(10);

        // Event listeners for the buttons
        undoButton.setOnAction(event -> {
            controllerJavaFX.handleUndo();
        });

        redoButton.setOnAction(event -> {
            controllerJavaFX.handleRedo();
        });

        // Event listener for the "Exit" button
        exitButton.setOnAction(event -> {
            controllerJavaFX.handleExit();
        });

        // Event listener for the "New Game" button
        newGameButton.setOnAction(event -> {
            controllerJavaFX.handleNewGame();
        });
    }
}
