package g62098.dev3.oxono.Controller;

import g62098.dev3.oxono.Model.Facade.Facade;
import g62098.dev3.oxono.Model.OxonoColor;
import g62098.dev3.oxono.Model.Symbol;
import g62098.dev3.oxono.View.ConsoleView;
import g62098.dev3.oxono.Model.Board;
import g62098.dev3.oxono.Model.OxonoException;

import java.util.Scanner;

/**
 * The {@code ControleurConsole} class is the console-based controller for the Oxono game.
 * It manages user input, delegates tasks to the {@code Facade}, and interacts with the {@code ConsoleView}
 * to display the game state and messages.
 */
public class ControleurConsole {

    private Board board;  // The game board
    private Facade facade;  // The facade layer for model interaction
    private Scanner scanner;  // Scanner for reading user input
    private OxonoColor currentPlayerOxonoColor;  // The current player's color (alternates between PINK and BLACK)
    private ConsoleView consoleView;  // The console-based view

    /**
     * Initializes the controller with a given facade and sets up the game state.
     *
     * @param facade the facade to interact with the model
     */
    public ControleurConsole(Facade facade) {
        this.scanner = new Scanner(System.in);
        this.facade = facade;
        this.board = facade.getBoard();
        this.currentPlayerOxonoColor = OxonoColor.PINK;
        this.consoleView = new ConsoleView();
    }

    //nouveau stage, nouvelle scene, nouvelles intance,
    /**
     * Starts the game loop, alternating between players and processing user input or AI moves.
     */

    public void startGame() {
        consoleView.displayWelcomeMessage();
        boolean gameRunning = true;

        while (gameRunning) {
            consoleView.displayBoard(board);
            consoleView.displayCurrentPlayer(currentPlayerOxonoColor);
            consoleView.displayTotemPositions(board);
            consoleView.displayMenu();

            boolean hasMovedTotem = false; // Le joueur doit d'abord déplacer un totem
            boolean hasPlacedPiece = false; // Ensuite, il doit placer une pièce

            if (currentPlayerOxonoColor == OxonoColor.PINK) {
                while (!hasMovedTotem || !hasPlacedPiece) { // Assurez-vous que les deux actions sont effectuées
                    consoleView.displayBoard(board);
                    consoleView.displayCurrentPlayer(currentPlayerOxonoColor);
                    consoleView.displayTotemPositions(board);
                    consoleView.displayMenu();
                    int choice = getPlayerChoice();
                    switch (choice) {
                        case 1:
                            if (!hasMovedTotem) {
                                handleMoveTotem();
                                hasMovedTotem = true; // Indique que le joueur a déplacé un totem
                            } else {
                                System.out.println("You already moved a totem");
                            }
                            break;

                        case 2:
                            if (!hasMovedTotem) {
                                consoleView.displayOxonoMessage("You must move a totem before placing a piece.");
                            } else {
                                boolean hasWon = handlePlacePiece();
                                hasPlacedPiece = true; // Indique que le joueur a placé une pièce
                                if (hasWon) {
                                    consoleView.displayVictory(currentPlayerOxonoColor);
                                    gameRunning = false; // Terminer le jeu si le joueur a gagné
                                }
                            }
                            break;

                        case 3:
                            try {
                                consoleView.displayOxonoMessage("Undoing the last command");
                                facade.undo();
                                hasMovedTotem = false;
                            } catch (OxonoException e) {
                                consoleView.displayOxonoMessage(e.getMessage());
                            }
                            break;

                        case 4:
                            try {
                                consoleView.displayOxonoMessage("Redoing the last command");
                                facade.redo();
                            } catch (OxonoException e) {
                                consoleView.displayOxonoMessage(e.getMessage());
                            }
                            break;

                        case 5:
                            consoleView.displayGoodbyeMessage();
                            gameRunning = false;
                            hasMovedTotem = true; // Sortir de la boucle (même si incomplet)
                            hasPlacedPiece = true;
                            break;

                        default:
                            consoleView.displayInvalidChoice();
                    }
                }
            } else {
                // Tour de l'IA
                aiTurn();
                // Après que l'IA a joué, on change de tour
                currentPlayerOxonoColor = (currentPlayerOxonoColor == OxonoColor.PINK) ? OxonoColor.BLACK : OxonoColor.PINK;
            }

            // Alterner entre les joueurs après que le joueur humain a terminé ses deux actions
            if (hasMovedTotem && hasPlacedPiece) {
                currentPlayerOxonoColor = (currentPlayerOxonoColor == OxonoColor.PINK) ? OxonoColor.BLACK : OxonoColor.PINK;
            }
        }
    }


    /**
     * Handles the AI's turn by delegating its actions to the facade.
     */
    private void aiTurn() {
        consoleView.displayOxonoMessage("AI's turn...");
        facade.playAITurn(currentPlayerOxonoColor, facade.getGame());
    }

    /**
     * Gets the player's choice from the menu.
     *
     * @return the player's choice as an integer
     */
    private int getPlayerChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 5) {
                    return choice;
                } else {
                    consoleView.displayInvalidChoice();
                }
            } catch (NumberFormatException e) {
                consoleView.displayOxonoMessage("Please enter a valid number.");
            }
        }
    }

    /**
     * Handles placing a piece on the board. Prompts the user for input and validates it.
     *
     * @return {@code true} if the player wins after placing the piece, otherwise {@code false}
     */
    private boolean handlePlacePiece() {
        if (facade.getTotemMoved()) {
            consoleView.displayOxonoMessage("Enter coordinates to place the piece (e.g., 'x 5 3'). Coordinates must be between 0 and 5.");
            while (true) {
                String input = scanner.nextLine();
                String[] parts = input.split(" ");

                if (parts.length != 3) {
                    consoleView.displayOxonoMessage("Invalid format. Use: <symbol> <row> <column> (e.g., 'x 5 3').");
                    continue;
                }

                try {
                    String symbolInput = parts[0].toLowerCase();
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);

                    if (!(symbolInput.equals("x") || symbolInput.equals("o"))) {
                        consoleView.displayOxonoMessage("Invalid symbol. Use 'x' or 'o'.");
                        continue;
                    }

                    if (x < 0 || x > 5 || y < 0 || y > 5) {
                        consoleView.displayOxonoMessage("Coordinates must be between 0 and 5.");
                        continue;
                    }

                    Symbol symbol = (symbolInput.equals("x")) ? Symbol.X : Symbol.O;
                    boolean hasWon = facade.placePiece(currentPlayerOxonoColor, symbol, x, y);

                    return hasWon;

                } catch (NumberFormatException e) {
                    consoleView.displayOxonoMessage("Coordinates must be valid numbers.");
                } catch (OxonoException e) {
                    consoleView.displayOxonoMessage(e.getMessage());
                }
            }
        } else {
            consoleView.displayOxonoMessage("You must move a totem before placing a piece.");
            return false;
        }
    }

    /**
     * Handles moving a totem on the board. Prompts the user for input and validates it.
     */
    private void handleMoveTotem() {
        consoleView.displayOxonoMessage("Move a totem.");
        consoleView.displayOxonoMessage("Enter the symbol and coordinates for the totem to move (e.g., 'x 3 4').");

        while (true) {
            String input = scanner.nextLine().toLowerCase();
            String[] parts = input.split(" ");

            if (parts.length != 3) {
                consoleView.displayOxonoMessage("Invalid format. Use: <symbol> <row> <column> (e.g., 'x 3 4').");
                continue;
            }

            try {
                String symbolInput = parts[0];
                int newX = Integer.parseInt(parts[1]);
                int newY = Integer.parseInt(parts[2]);

                if (!symbolInput.equals("x") && !symbolInput.equals("o")) {
                    consoleView.displayOxonoMessage("Invalid symbol. Use 'x' or 'o'.");
                    continue;
                }

                if (newX < 0 || newX > 5 || newY < 0 || newY > 5) {
                    consoleView.displayOxonoMessage("Coordinates must be between 0 and 5.");
                    continue;
                }

                Symbol totemSymbol = (symbolInput.equals("x")) ? Symbol.X : Symbol.O;

                // Attempt to move the totem
                facade.moveTotem(totemSymbol, newX, newY);
                consoleView.displayOxonoMessage("Totem moved to position (" + newX + ", " + newY + ").");
                break;

            } catch (NumberFormatException e) {
                consoleView.displayOxonoMessage("Coordinates must be valid numbers.");
            } catch (OxonoException e) {
                consoleView.displayOxonoMessage(e.getMessage());
            }
        }
    }
}
