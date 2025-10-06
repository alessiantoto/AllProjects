package g62098.dev3.oxono.Application;

import g62098.dev3.oxono.Controller.ControllerJavaFX;
import g62098.dev3.oxono.Model.Board;
import g62098.dev3.oxono.Model.Facade.GameFacade;
import g62098.dev3.oxono.Model.Game;
import g62098.dev3.oxono.View.JavaFXComponents.ControlsView;
import g62098.dev3.oxono.View.JavaFXComponents.MainView;
import g62098.dev3.oxono.View.JavaFXComponents.SizeView;
import g62098.dev3.oxono.View.JavaFXComponents.ValidTotemMoves;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The entry point for the JavaFX-based Oxono game application.
 * This class initializes the game components and sets up the graphical user interface using JavaFX.
 */
public class ApplicationJavaFX extends Application {

    /**
     * The main entry point for the JavaFX application.
     *
     * <p>This method is automatically called when the JavaFX application is launched. It performs the following steps:
     * <ol>
     *     <li>Initializes the game board and game logic using {@link Board}, {@link Game}, and {@link GameFacade}.</li>
     *     <li>Creates the {@link MainView} instance to represent the graphical user interface (GUI).</li>
     *     <li>Displays the primary stage of the JavaFX application.</li>
     * </ol>
     *
     * @param primaryStage the primary stage for this JavaFX application, representing the main window.
     */
    @Override
    public void start(Stage primaryStage) {
        // Step 1: Get the grid size selected by the user
        SizeView sizeView = new SizeView(primaryStage);
        int gridSize = sizeView.getSelectedGridSize(); // Get the selected grid size from MainView

        // Step 2: Create the Board, Game, and GameFacade using the grid size
        Board board = new Board(gridSize);  // Initialize the game board with the selected grid size
        Game game = new Game(board);  // Initialize the game instance with the board

        // Step 3: Create the GameFacade using the Game instance
        GameFacade gameFacade = new GameFacade(game);

        ControllerJavaFX controllerJavaFX = new ControllerJavaFX(gameFacade);

        // Step 4: Initialize MainView with the correct GameFacade
        MainView mainView = new MainView(controllerJavaFX, primaryStage);


        // Step 5: Show the main view
        mainView.show(primaryStage);



        Stage validTotemMovesStage = new Stage();
        ValidTotemMoves validTotemMoves = new ValidTotemMoves(controllerJavaFX);
        validTotemMovesStage.setTitle("Valid Totem Moves");
        validTotemMovesStage.setScene(new Scene(validTotemMoves, 300, 200));
        validTotemMovesStage.show();


        // Observer registration to keep ValidTotemMoves updated
        gameFacade.registerObserver(validTotemMoves);


    }

    /**
     * The main method that launches the JavaFX application.
     *
     * <p>This method invokes the {@code launch} method from the {@link javafx.application.Application} class
     * to initialize and start the JavaFX application.</p>
     *
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) {
        launch(args); // This launches the JavaFX application
    }
}
