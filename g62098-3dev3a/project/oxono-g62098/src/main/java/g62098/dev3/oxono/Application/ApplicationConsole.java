package g62098.dev3.oxono.Application;

import g62098.dev3.oxono.Controller.ControleurConsole;
import g62098.dev3.oxono.Model.Board;
import g62098.dev3.oxono.Model.Facade.Facade;
import g62098.dev3.oxono.Model.Facade.GameFacade;
import g62098.dev3.oxono.Model.Game;

public class ApplicationConsole {
    public static void main(String[] args) {
        // Step 1: Initialize the game board
        Board board = new Board(6);  // Create a new Board instance to represent the game grid
        Game game = new Game(board);

        // Step 2: Create a Facade (GameFacade is an implementation of Facade interface)
        Facade facade = new GameFacade(game);  // Pass the board to GameFacade, which handles the game logic

        // Step 3: Initialize the Console Controller (ControleurConsole)
        ControleurConsole controleurConsole = new ControleurConsole(facade);  // Connect the Facade to the Console Controller

        // Step 4: Start the game
        controleurConsole.startGame();  // Start the game using the controller, which drives the game logic in the console
    }
}
