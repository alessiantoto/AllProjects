

import static org.junit.jupiter.api.Assertions.*;

import g62098.dev3.oxono.Model.*;
import g62098.dev3.oxono.Model.Facade.Facade;
import g62098.dev3.oxono.Model.Facade.GameFacade;
import g62098.dev3.oxono.View.ConsoleView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelTest {
    private Board board;
    private Game game;
    private Facade facade;
    private ConsoleView consoleView;
    private int gridSize = 6;

    @BeforeEach
    public void setup() {


        board = new Board(6);
        game = new Game(board);
        facade = new GameFacade(game);
        consoleView = new ConsoleView();
        consoleView.displayBoard(board);
    }

    @Test
    public void testGridSize() {
        assertTrue(board.getGrid().length > 2, "La grille devrait avoir au moins 3 lignes.");
        assertTrue(board.getGrid()[0].length > 2, "La grille devrait avoir au moins 3 colonnes.");
    }
    @Test
    public void testSetupInitialPosition() {
        // Appeler la méthode pour initialiser la position des totems
        board.setupInitialPosition();

        // Récupérer les positions initiales des totems
        int totem1X = board.getTotem1X();
        int totem1Y = board.getTotem1Y();
        int totem2X = board.getTotem2X();
        int totem2Y = board.getTotem2Y();

        // Vérifier que les positions calculées sont correctes
        assertEquals(gridSize / 2, totem1X, "Totem 1 X position should be in the center");
        assertEquals(gridSize / 2, totem1Y, "Totem 1 Y position should be in the center");
        assertEquals((gridSize / 2) - 1, totem2X, "Totem 2 X position should be next to the center");
        assertEquals((gridSize / 2) - 1, totem2Y, "Totem 2 Y position should be next to the center");

        // Vérifier que les cellules correspondantes sont occupées
        assertTrue(board.getGrid()[totem1X][totem1Y].isOccupied(), "Totem 1 cell should be occupied");
        assertTrue(board.getGrid()[totem2X][totem2Y].isOccupied(), "Totem 2 cell should be occupied");

        // Vérifier que les symboles des totems sont différents
        Symbol totem1Symbol = board.getGrid()[totem1X][totem1Y].getOccupiedBy().getSymbol();
        Symbol totem2Symbol = board.getGrid()[totem2X][totem2Y].getOccupiedBy().getSymbol();
        assertNotEquals(totem1Symbol, totem2Symbol, "The two totems should have different symbols");
    }


    @Test
    public void testPlacementHorsLimites() {
        // Tester un placement hors des limites de la grille
        assertFalse(board.isValidPlacement(-1, 0));  // -1 est hors des limites
        assertFalse(board.isValidPlacement(6, 6));   // (6,6) est hors limites si gridSize est 6
    }


    @Test
    public void testMoveTotemDynamic() {
        // Vérifier la position initiale des totems
        int initialTotem1X = board.getTotem1X();
        int initialTotem1Y = board.getTotem1Y();
        Symbol totem1Symbol = board.getGrid()[initialTotem1X][initialTotem1Y].getOccupiedBy().getSymbol();

        int initialTotem2X = board.getTotem2X();
        int initialTotem2Y = board.getTotem2Y();
        Symbol totem2Symbol = board.getGrid()[initialTotem2X][initialTotem2Y].getOccupiedBy().getSymbol();

        // Déplacer le premier totem (peu importe son symbole) à une nouvelle position
        int targetX1 = initialTotem1X;
        int targetY1 = initialTotem1Y - 2; // Exemple: déplacement vers la gauche de 2 cellules
        facade.moveTotem(totem1Symbol, targetX1, targetY1);

        // Vérifier que les positions ont été mises à jour correctement pour le premier totem
        assertEquals(targetX1, board.getTotem1X(), "Totem 1 should be moved to the new X position");
        assertEquals(targetY1, board.getTotem1Y(), "Totem 1 should be moved to the new Y position");

        // Vérifier que l'ancienne cellule du premier totem a été libérée
        assertFalse(board.getGrid()[initialTotem1X][initialTotem1Y].isOccupied(), "The initial cell of Totem 1 should be free");

        // Vérifier que la nouvelle cellule est occupée par le premier totem
        assertTrue(board.getGrid()[targetX1][targetY1].isOccupied(), "The new cell should be occupied by Totem 1");

        // Déplacer le deuxième totem (peu importe son symbole) à une nouvelle position
        int targetX2 = initialTotem2X;
        int targetY2 = initialTotem2Y + 2; // Exemple: déplacement vers la droite de 2 cellules
        facade.moveTotem(totem2Symbol, targetX2, targetY2);

        // Vérifier que les positions ont été mises à jour correctement pour le deuxième totem
        assertEquals(targetX2, board.getTotem2X(), "Totem 2 should be moved to the new X position");
        assertEquals(targetY2, board.getTotem2Y(), "Totem 2 should be moved to the new Y position");

        // Vérifier que l'ancienne cellule du deuxième totem a été libérée
        assertFalse(board.getGrid()[initialTotem2X][initialTotem2Y].isOccupied(), "The initial cell of Totem 2 should be free");

        // Vérifier que la nouvelle cellule est occupée par le deuxième totem
        assertTrue(board.getGrid()[targetX2][targetY2].isOccupied(), "The new cell should be occupied by Totem 2");

        // Afficher l'état du plateau après chaque déplacement
        consoleView.displayBoard(board);

        // Exemple de déplacements supplémentaires pour tester le comportement dynamique
        facade.moveTotem(totem2Symbol, targetX2, targetY2 - 3); // Déplacement du deuxième totem
        consoleView.displayBoard(board);

        facade.moveTotem(totem1Symbol, targetX1 + 1, targetY1); // Déplacement du premier totem
        consoleView.displayBoard(board);
    }

    @Test
    public void testMoveTotem() {

        Symbol totemSymbol = board.getGrid()[3][3].getOccupiedBy().getSymbol();


        // Déplacer le totem X de (3,3) à (3,1)
        facade.moveTotem(totemSymbol, 3, 1);

        // Vérifier que les positions ont été mises à jour correctement
        assertEquals(3, board.getTotem1X(), "Totem X should be moved to the new X position");
        assertEquals(1, board.getTotem1Y(), "Totem X should be moved to the new Y position");

        // Vérifier que l'ancienne cellule a été libérée
        assertFalse(board.getGrid()[3][3].isOccupied(), "The initial cell of Totem X should be free");

        // Vérifier que la nouvelle cellule est occupée
        assertTrue(board.getGrid()[3][1].isOccupied(), "The new cell should be occupied by Totem X");
    }

    @Test
    public void testGetPlayer1() {
        // Vérifier que getPlayer1() renvoie bien le joueur PINK
        assertNotNull(board.getPlayer1(), "Le joueur 1 ne devrait pas être nul.");
        assertEquals(OxonoColor.PINK, board.getPlayer1().getColor(), "Le joueur 1 devrait être de couleur PINK.");
    }

    @Test
    public void testGetPlayer2() {
        // Vérifier que getPlayer2() renvoie bien le joueur BLACK
        assertNotNull(board.getPlayer2(), "Le joueur 2 ne devrait pas être nul.");
        assertEquals(OxonoColor.BLACK, board.getPlayer2().getColor(), "Le joueur 2 devrait être de couleur BLACK.");
    }

    @Test
    public void testInvalidPlacement() {
        // Tester un placement invalide dans une cellule déjà occupée
        board.getGrid()[2][2].occupy(new Piece(OxonoColor.PINK, Symbol.X)); // Occuper la cellule (2, 2)
        assertThrows(OxonoException.class, () -> {
            facade.placePiece(OxonoColor.PINK, Symbol.X, 2, 2);
        }, "Un placement dans une cellule occupée devrait lancer une exception.");

        // Tester un placement non adjacent au totem
        assertThrows(OxonoException.class, () -> {
            facade.placePiece(OxonoColor.PINK, Symbol.X, 5, 5);
        }, "Un placement non adjacent au totem devrait lancer une exception.");

        // Tester un placement sans pièces disponibles
        board.getPlayer1().getPiecesX().clear(); // Supprimer toutes les pièces X de player1
        assertThrows(OxonoException.class, () -> {
            facade.placePiece(OxonoColor.PINK, Symbol.X, 3, 2); // Position valide mais sans pièce disponible
        }, "Le joueur sans pièces disponibles devrait provoquer une exception.");

        // Tester un placement avec un symbole inconnu (hypothèse : invalide)
        assertThrows(OxonoException.class, () -> {
            facade.placePiece(OxonoColor.PINK, null, 3, 2); // Symbole non défini
        }, "Un placement avec un symbole inconnu devrait lancer une exception.");
    }


    //fonctionne si X est au depart au 3,3
    @Test
    public void testValidPlacement() {
        // Tester un placement valide adjacent au totem X

        System.out.println("Cellule (3,2) occupée ? " + board.getGrid()[3][2].isOccupied());
        System.out.println("Validité de placement adjacente au totem : " + board.isValidPlacement(2, 3));
        System.out.println(board.getTotem1X());
        System.out.println(board.getTotem1Y());
        System.out.println(board.getTotem2X());
        System.out.println(board.getTotem2Y());

        facade.moveTotem(Symbol.X, 3, 2);
        facade.placePiece(OxonoColor.PINK, Symbol.X,3,1);

        System.out.println("Validité de placement adjacente au totem : " + game.checkPlaceValidity(Symbol.X,2, 3));


        assertTrue(board.getGrid()[3][1].isOccupied(), "La cellule (2,3) devrait être occupée.");
        assertEquals(Symbol.X, board.getGrid()[3][1].getOccupiedBy().getSymbol(), "La pièce placée devrait être un X.");

        facade.moveTotem(Symbol.X, 4, 2);
        facade.placePiece(OxonoColor.BLACK, Symbol.X, 4, 3); // Adjacente au totem O
        assertTrue(board.getGrid()[4][3].isOccupied(), "La cellule (3,4) devrait être occupée.");
        assertEquals(Symbol.X, board.getGrid()[4][3].getOccupiedBy().getSymbol(), "La pièce placée devrait être un O.");

        // Vérifier que le joueur a bien consommé une pièce X
        assertEquals(8, board.getPlayer1().getPiecesO().size(), "Le joueur PINK devrait avoir 8 pièces X restantes.");

        // Vérifier que le joueur a bien consommé une pièce O
        assertEquals(7, board.getPlayer2().getPiecesX().size(), "Le joueur BLACK devrait avoir 7 pièces O restantes.");
        consoleView.displayBoard(board);
    }
    @Test
    public void testValidPlacementForPlayer1() {
        // Assume that the totem has already been moved before placing the piece.
        // Let's check that the totem move has happened and that it's a valid placement.

        // Step 1: Simulate the totem move for the player.
        Symbol totemSymbol = Symbol.X; // Assume we are moving the X totem for the test
        int[] totemMove = {3, 2}; // Example move coordinates for the totem (3, 2)
        game.moveTotem(totemSymbol, totemMove[0], totemMove[1]);

        // Verify that the totem is indeed at the new position (3, 2).
        assertEquals(totemSymbol, board.getGrid()[3][2].getOccupiedBy().getSymbol(),
                "The totem should be at position (3,2) after the move.");
        assertTrue(board.getGrid()[3][2].isOccupied(), "The position (3,2) should now be occupied by the totem.");

        // Step 2: Check if the cell (3, 1) is valid for placement before trying to place a piece.
        assertTrue(game.checkPlaceValidity(Symbol.X, 3, 1), "The cell (3,1) should be valid for placement.");

        // Step 3: Now, place the piece at (3, 2) after the totem move.
        facade.placePiece(OxonoColor.PINK, Symbol.X, 3, 1);

        // Step 4: Verify that the cell (3, 2) is now occupied by a piece.
        assertTrue(board.getGrid()[3][2].isOccupied(), "The cell (3,2) should be occupied by a piece.");

        // Verify that the piece placed is of the correct type (X in this case).
        assertEquals(Symbol.X, board.getGrid()[3][2].getOccupiedBy().getSymbol(),
                "The piece placed should be an X.");

        // Verify that the player has used one piece X.
        assertEquals(7, board.getPlayer1().getPiecesX().size(), "The player should have 7 X pieces left.");

        // Verify that the player has not used any O pieces.
        assertEquals(8, board.getPlayer1().getPiecesO().size(), "The player should still have 8 O pieces left.");

        // Optionally, display the board to visualize the result
        consoleView.displayBoard(board);
    }



    @Test
    public void testCheckAlignment() {
        // Obtenez la grille
        Cell[][] grid = board.getGrid();

        // Cas 1 : Alignement horizontal de 4 pièces 'X'
        grid[2][2].occupy(new Piece(OxonoColor.PINK, Symbol.X)); // (2,2)
        grid[2][3].occupy(new Piece(OxonoColor.PINK, Symbol.X)); // (2,3)
        grid[2][4].occupy(new Piece(OxonoColor.PINK, Symbol.X)); // (2,4)
        grid[2][5].occupy(new Piece(OxonoColor.PINK, Symbol.X)); // (2,5)

        // Vérifiez si l'alignement horizontal de 4 est détecté
        assertTrue(game.checkAlignment(Symbol.X, 2, 3), "L'alignement horizontal de 4 pièces X devrait être détecté.");

        // Cas 2 : Alignement vertical de 4 pièces 'O'
        grid[2][1].occupy(new Piece(OxonoColor.BLACK, Symbol.O)); // (3,1)
        grid[3][1].occupy(new Piece(OxonoColor.BLACK, Symbol.O)); // (4,1)
        grid[4][1].occupy(new Piece(OxonoColor.BLACK, Symbol.O)); // (5,1)
        grid[5][1].occupy(new Piece(OxonoColor.BLACK, Symbol.O)); // (6,1)

        // Vérifiez si l'alignement vertical de 4 est détecté
        assertTrue(game.checkAlignment(Symbol.O, 5, 1), "L'alignement vertical de 4 pièces O devrait être détecté.");

        // Cas 3 : Pas d'alignement (moins de 4 pièces)
        grid[1][1].occupy(new Piece(OxonoColor.PINK, Symbol.X)); // (1,1)
        grid[1][2].occupy(new Piece(OxonoColor.PINK, Symbol.X)); // (1,2)
        grid[1][3].occupy(new Piece(OxonoColor.PINK, Symbol.X)); // (1,3)

        // Vérifiez que l'alignement n'est pas détecté
        assertFalse(game.checkAlignment(Symbol.X, 1, 3), "Un alignement de moins de 4 pièces ne devrait pas être détecté.");

        // Cas 4 : Mélange de symboles (pas d'alignement)
        grid[4][2].occupy(new Piece(OxonoColor.BLACK, Symbol.O)); // (4,2)
        grid[4][3].occupy(new Piece(OxonoColor.PINK, Symbol.X));  // (4,3)
        grid[4][4].occupy(new Piece(OxonoColor.BLACK, Symbol.O)); // (4,4)
        grid[4][5].occupy(new Piece(OxonoColor.PINK, Symbol.X));  // (4,5)

        // Vérifiez que l'alignement n'est pas détecté avec un mélange
        assertFalse(game.checkAlignment(Symbol.X, 4, 3), "Un mélange de symboles ne devrait pas être détecté comme un alignement.");
        consoleView.displayBoard(board);
    }


    @Test
    public void testMoveEnclavedTotem() {
        // Initialiser le plateau
        Board board = new Board(6);
        // Déterminer le symbole du totem en position (3,3)
        Symbol totemSymbol = ((Totem) board.getGrid()[3][3].getOccupiedBy()).getSymbol();

        // Étape 1 : Simuler un déplacement valide
        // Placer des pièces pour entourer le totem X
        board.getGrid()[2][3].occupy(new Piece(OxonoColor.BLACK, Symbol.O));
        board.getGrid()[4][3].occupy(new Piece(OxonoColor.PINK, Symbol.O));
        board.getGrid()[3][2].occupy(new Piece(OxonoColor.BLACK, Symbol.O));
        board.getGrid()[3][4].occupy(new Piece(OxonoColor.BLACK, Symbol.O));

        try {
            // Tester le déplacement vers le haut
            facade.moveTotem(totemSymbol, 1, 3); // Tentative de déplacement vers (1,3)

        } catch (OxonoException e) {
            fail("Le totem ne devrait pas échouer à se déplacer.");
        }

        // Réinitialiser pour un autre test
        board.getGrid()[1][3].deleteOccupant();
        board.getGrid()[3][5].deleteOccupant();
        board.getGrid()[3][3].occupy(new Totem(Symbol.X)); // Remettre le totem à (3,3)
    }

    @Test
    public void testCheckMoveValidity() {

        Symbol totemSymbol = board.getGrid()[3][3].getOccupiedBy().getSymbol();

        // Case 1: Move to a valid cell
        boolean isValidMove = game.checkMoveValidity(totemSymbol, 4, 3);
        assertTrue(isValidMove, "Moving to a valid cell should be allowed.");

        // Case 2: Move to a cell outside the board boundaries
        isValidMove = game.checkMoveValidity(totemSymbol, 6, 6);
        assertFalse(isValidMove, "Moving to a cell outside the board should be invalid.");

        // Case 3: Move blocked by an obstacle
        game.getBoard().getGrid()[4][3].occupy(new Piece(OxonoColor.BLACK, Symbol.O)); // Block the path
        isValidMove = game.checkMoveValidity(totemSymbol, 4, 3);
        assertFalse(isValidMove, "Moving to a cell blocked by an obstacle should be invalid.");

        // Case 4: Move allowed via a valid jump but outside grid
        game.getBoard().getGrid()[4][3].deleteOccupant(); // Clear the adjacent cell
        game.getBoard().getGrid()[5][3].occupy(new Piece(OxonoColor.PINK, Symbol.O)); // Add an obstacle for jumping
        isValidMove = game.checkMoveValidity(totemSymbol, 6, 3);
        assertFalse(isValidMove, "Should not be allowed cause outside of grid");

        // Clean up: remove the totem to avoid conflicts in other tests
        game.getBoard().getGrid()[3][3].deleteOccupant();
    }

    @Test
    public void testCheckPlaceValidityWithTotemMovement() {
        // Place initial totem at a known position
        board.getGrid()[3][3].occupy(new Totem(Symbol.X));

        // Dynamically determine the symbol of the totem
        Symbol totemSymbol = ((Totem) board.getGrid()[3][3].getOccupiedBy()).getSymbol();

        // Step 1: Move the totem to a valid position
        try {
            facade.moveTotem(totemSymbol, 4, 3); // Move the totem to a new position
            assertEquals(totemSymbol, ((Totem) board.getGrid()[4][3].getOccupiedBy()).getSymbol(),
                    "The totem should be successfully moved to (4,3).");
        } catch (OxonoException e) {
            fail("Totem movement should succeed.");
        }

        // Step 2: Validate placement logic after the totem is moved
        // Case 1: Place a piece in a valid cell adjacent to the new totem position
        boolean isValidPlacement = game.checkPlaceValidity(totemSymbol, 5, 3);
        assertTrue(isValidPlacement, "Placing a piece in a valid adjacent cell should be allowed.");

        // Case 2: Place a piece outside the board boundaries
        isValidPlacement = game.checkPlaceValidity(totemSymbol, 6, 6);
        assertFalse(isValidPlacement, "Placing a piece outside the board should be invalid.");

        // Case 3: Place a piece in a cell not adjacent to a matching totem
        isValidPlacement = game.checkPlaceValidity(totemSymbol, 2, 2);
        assertFalse(isValidPlacement, "Placing a piece far from the totem should be invalid if not all cells are occupied.");

        // Step 3: Simulate blockage of all adjacent cells
        blockAdjacentCells(4, 3); // Block the cells around the totem's new position
        isValidPlacement = game.checkPlaceValidity(totemSymbol, 1, 1);
        assertTrue(isValidPlacement, "Placement should be allowed anywhere if all adjacent cells are occupied.");

        // Clean up: remove the totem to avoid conflicts in other tests
        board.getGrid()[4][3].deleteOccupant();
    }

    // Helper method to block all cells around a specific position
    private void blockAdjacentCells(int x, int y) {
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            // Check if the cell is within the board boundaries
            if (newX >= 0 && newX < board.getGrid().length &&
                    newY >= 0 && newY < board.getGrid()[newX].length) {
                board.getGrid()[newX][newY].occupy(new Piece(OxonoColor.BLACK, Symbol.O));
            }
        }
    }

}
