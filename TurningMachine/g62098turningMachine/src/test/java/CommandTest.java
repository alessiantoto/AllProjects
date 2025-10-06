import Modele.Problemes.CodeSecret;
import Modele.Commandes.NextRoundCommand;
import Modele.Commandes.StartGameCommand;
import Modele.Game;
import Modele.Problemes.Probleme;
import Modele.Validateurs.ChiffreExtremumMax;
import Modele.Validateurs.ChiffreExtremumMin;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandTest {
    @Test
    void testUndo() {
        Game game = new Game();

        CodeSecret codeSecret = new CodeSecret(123);

        Probleme probleme = new Probleme(codeSecret, 1, 1, Arrays.asList(new ChiffreExtremumMax(), new ChiffreExtremumMin()));
        game.executeCommand(new StartGameCommand(game,probleme));

        // Vérifier que la pile undoStack contient la commande
        assertEquals(1, game.getUndoStack().size());
        // Créer une commande factice pour les tests

        game.executeCommand(new NextRoundCommand(game));

        // Vérifier que la pile undoStack contient la commande
        assertEquals(2, game.getUndoStack().size());

        // Appeler la méthode undo
        game.undo();

        // Vérifier que la pile undoStack contient la commande
        assertEquals(1, game.getUndoStack().size());

        // Vérifier que la commande a été déplacée vers la pile redoStack
        assertEquals(1, game.getRedoStack().size());
        // Assurez-vous que l'objet sur la redoStack est de type NextRoundCommand
        assertTrue(game.getRedoStack().peek() instanceof NextRoundCommand);
    }

    @Test
    void testRedo() {
        Game game = new Game();

        CodeSecret codeSecret = new CodeSecret(123);

        Probleme probleme = new Probleme(codeSecret, 1, 1, Arrays.asList(new ChiffreExtremumMax(), new ChiffreExtremumMin()));
        game.executeCommand(new StartGameCommand(game, probleme));

        // Vérifier que la pile undoStack contient la commande
        assertEquals(1, game.getUndoStack().size());

        // Créer une commande factice pour les tests
        ChiffreExtremumMax commandMock = new ChiffreExtremumMax();
        game.executeCommand(new NextRoundCommand(game));

        // Vérifier que la pile undoStack contient la commande
        assertEquals(2, game.getUndoStack().size());

        // Appeler la méthode undo
        game.undo();

        // Vérifier que la pile undoStack contient la commande
        assertEquals(1, game.getUndoStack().size());

        // Vérifier que la pile redoStack contient la commande annulée
        assertEquals(1, game.getRedoStack().size());

        // Appeler la méthode redo
        game.redo();

        // Vérifier que la pile redoStack est vide après redo
        assertTrue(game.getRedoStack().isEmpty());
    }

    @Test
    void testExecuteCommand() {
        Game game = new Game();

        // Créez une instance de CodeSecret au lieu de String
        CodeSecret codeSecret = new CodeSecret(123);

        Probleme probleme = new Probleme(codeSecret, 1, 1, Arrays.asList(new ChiffreExtremumMax(), new ChiffreExtremumMin()));
        game.executeCommand(new StartGameCommand(game, probleme));

        // Vérifier que la pile undoStack contient la commande de démarrage du jeu
        assertEquals(1, game.getUndoStack().size());

        // Appeler la méthode executeCommand avec la commande factice
        game.executeCommand(new NextRoundCommand(game));

        // Vérifier que la pile undoStack contient maintenant deux commandes
        assertEquals(2, game.getUndoStack().size());

        // Vérifier que la pile redoStack est vide après l'exécution d'une nouvelle commande
        assertTrue(game.getRedoStack().isEmpty());
    }

}
