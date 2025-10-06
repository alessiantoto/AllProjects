package g62098.dev3.oxono.View.JavaFXComponents;

import g62098.dev3.oxono.Controller.ControllerJavaFX;
import g62098.dev3.oxono.Model.OxonoColor;
import g62098.dev3.oxono.Model.Occupant;
import g62098.dev3.oxono.Model.Symbol;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Objects;

/**
 * The {@code BoardView} class is responsible for displaying the game board in the JavaFX application.
 * It manages the display of the game grid, the movement of totems, and the placement of pieces,
 * as well as displaying a pop-up when the game ends with a win.
 */
public class BoardView extends GridPane {

    private ControllerJavaFX controllerJavaFX;

    private final Image totemX = new Image(Objects.requireNonNull(getClass()
            .getResource("/assets/TotemX.png")).toExternalForm());
    private final Image totemO = new Image(Objects.requireNonNull(getClass()
            .getResource("/assets/TotemO.png")).toExternalForm());
    private final Image blackX = new Image(Objects.requireNonNull(getClass()
            .getResource("/assets/BlackX.png")).toExternalForm());
    private final Image pinkX = new Image(Objects.requireNonNull(getClass()
            .getResource("/assets/PinkX.png")).toExternalForm());
    private final Image blackO = new Image(Objects.requireNonNull(getClass()
            .getResource("/assets/BlackO.png")).toExternalForm());
    private final Image pinkO = new Image(Objects.requireNonNull(getClass()
            .getResource("/assets/PinkO.png")).toExternalForm());

    /**
     * Constructs the {@code BoardView} class.
     * Initializes the game board view by retrieving game model information from the facade.
     *
     * @param controllerJavaFX The controller instance used to interact with the facade model.
     */
    public BoardView(ControllerJavaFX controllerJavaFX) {
        this.controllerJavaFX = controllerJavaFX;
        setupBoard();
    }

    /**
     * Sets up the game board by initializing the grid, the cells, and defining click event handlers.
     */
    private void setupBoard() {
        javafx.scene.paint.Color cellBackground = javafx.scene.paint.Color.DARKMAGENTA;
        javafx.scene.paint.Color cellBorderColor = javafx.scene.paint.Color.MEDIUMPURPLE;

        // Apply a global background to the board
        this.setStyle("-fx-background-color: #8B008B;");
        this.setHgap(0); // No spacing between cells
        this.setVgap(0);

        int gridSize = controllerJavaFX.getBoard().getGridSize();
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                // Create a cell
                StackPane cellPane = new StackPane();
                Rectangle border = new Rectangle(50, 50);
                border.setFill(cellBackground);
                border.setStroke(cellBorderColor);
                border.setStrokeWidth(2);

                ImageView cellImage = new ImageView();
                cellImage.setFitWidth(50);
                cellImage.setFitHeight(50);

                cellPane.getChildren().addAll(border, cellImage);
                this.add(cellPane, x, y);

                // Check if the cell is occupied and update the image
                if (controllerJavaFX.getBoard().getGrid()[x][y].isOccupied()) {
                    Occupant occupant = controllerJavaFX.getBoard().getGrid()[x][y].getOccupiedBy();
                    if (occupant != null) {
                        updateCellImage(cellImage, occupant);
                    }
                }

                // Add an event handler for clicking on a cell
                final int finalX = x;
                final int finalY = y;
                cellPane.setOnMouseClicked(event -> controllerJavaFX.handleCellClick(finalX, finalY));

                // Add an event handler for mouse entering a cell
                cellPane.setOnMouseEntered(event -> {
                    if (controllerJavaFX.getIsTotemSelected()) {
                        // Check if the totem can be moved (logic for totem movement already in place)
                        Symbol totemSymbol = controllerJavaFX.getBoard().getGrid()[controllerJavaFX.getSelectedTotemX()][controllerJavaFX.getSelectedTotemY()].getOccupiedBy().getSymbol();
                        List<int[]> validMoves = controllerJavaFX.getValidTotemMoves(totemSymbol);

                        // Check if the cell (finalX, finalY) is a valid move for the totem
                        boolean isValidMove = false;
                        for (int[] validMove : validMoves) {
                            if (validMove[0] == finalX && validMove[1] == finalY) {
                                isValidMove = true;
                                break;
                            }
                        }

                        // Change the color of the cell based on the validity of the totem move
                        if (isValidMove) {
                            border.setFill(javafx.scene.paint.Color.GREEN);  // Valid cell for moving a totem
                        } else {
                            border.setFill(javafx.scene.paint.Color.RED);  // Invalid cell for moving a totem
                        }
                    } else {
                        // No totem selected, all cells become red
                        border.setFill(javafx.scene.paint.Color.RED);

                        // Check if the cell is valid for placing a token
                        if(controllerJavaFX.getSelectedTotemSymbol() != null) {
                            Symbol currentPlayerSymbol = controllerJavaFX.getSelectedTotemSymbol();
                            List<int[]> validPlaces = controllerJavaFX.getValidPiecesPlaces(currentPlayerSymbol);

                            // Check if the cell (finalX, finalY) is a valid cell for placing a token
                            boolean canPlaceToken = false;
                            for (int[] validPlace : validPlaces) {
                                if (validPlace[0] == finalX && validPlace[1] == finalY) {
                                    canPlaceToken = true;
                                    break;
                                }
                            }

                            // Change the color of the cell based on the validity of placing a token
                            if (canPlaceToken) {
                                border.setFill(javafx.scene.paint.Color.GREEN);  // Valid cell for placing a token
                            } else {
                                border.setFill(javafx.scene.paint.Color.RED);  // Invalid cell for placing a token
                            }
                        }
                    }
                });

                // Reset the color of the cell when the mouse leaves the cell
                cellPane.setOnMouseExited(event -> {
                    border.setFill(cellBackground);  // Reset the original color
                });
            }
        }
    }




    /**
     * Updates the image in a cell based on the occupant (totem or piece).
     *
     * @param cell     The image view of the cell to update.
     * @param occupant The occupant of the cell (totem or piece).
     */
    private void updateCellImage(ImageView cell, Occupant occupant) {
        if (occupant.getMovable()) {
            cell.setImage(occupant.getSymbol() == Symbol.X ? totemX : totemO);
        } else {
            if (occupant.getColor() == OxonoColor.BLACK) {
                cell.setImage(occupant.getSymbol() == Symbol.X ? blackX : blackO);
            } else if (occupant.getColor() == OxonoColor.PINK) {
                cell.setImage(occupant.getSymbol() == Symbol.X ? pinkX : pinkO);
            }
        }
    }

    /**
     * Updates the board view based on the current game state.
     */
    public void updateView() {
        int gridSize = controllerJavaFX.getBoard().getGridSize();
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                StackPane cellPane = (StackPane) this.getChildren().get(x + y * gridSize);
                ImageView cellImage = (ImageView) cellPane.getChildren().get(1);

                if (controllerJavaFX.getBoard().getGrid()[x][y].isOccupied()) {
                    Occupant occupant = controllerJavaFX.getBoard().getGrid()[x][y].getOccupiedBy();
                    if (occupant != null) {
                        updateCellImage(cellImage, occupant);
                    }
                } else {
                    cellImage.setImage(null);
                }
            }
        }
    }
}
