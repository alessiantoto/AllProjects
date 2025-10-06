import Modele.Problemes.CodeSecret;
import Modele.Game;
import Modele.Problemes.Probleme;
import Modele.Validateurs.ChiffreExtremumMin;
import Modele.Validateurs.ChiffreExtremumMax;
import Modele.Validateurs.Validateur;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class GameTest {

    @Test
    void testStartGame() {
        Game game = new Game();
        ChiffreExtremumMax chiffreExtremumMax = new ChiffreExtremumMax();
        // Créez une instance de CodeSecret au lieu de String
        CodeSecret codeSecret = new CodeSecret(111);
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(chiffreExtremumMax));
        game.startGame(probleme);
        assertEquals(probleme, game.getProblemeActuel());
        assertNull(game.getCodeJoueur());
        assertTrue(game.getValidateursVerifies().isEmpty());
        assertEquals(0, game.getScore());
    }

    @Test
    void testSubmitPlayerCode() {
        Game game = new Game();
        ChiffreExtremumMax chiffreExtremumMax = new ChiffreExtremumMax();
        // Créez une instance de CodeSecret au lieu de String
        CodeSecret codeSecret = new CodeSecret(111);
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(chiffreExtremumMax));
        game.startGame(probleme);
        game.submitPlayerCode(123);
        assertEquals(123, game.getCodeJoueur());
    }

    @Test
    void testChooseValidateur() {
        Game game = new Game();
        ChiffreExtremumMax chiffreExtremumMax = new ChiffreExtremumMax();
        // Créez une instance de CodeSecret au lieu de String
        CodeSecret codeSecret = new CodeSecret(111);
        Probleme probleme = new Probleme(codeSecret, 1, 1, new ArrayList<>(Collections.singletonList(chiffreExtremumMax)));
        game.startGame(probleme);
        Validateur validateur = probleme.getValidateursAssocies().get(0); // Créez un validateur factice pour les tests
        game.chooseValidateur(validateur, 123, probleme);
        assertEquals(1, game.getValidateursVerifies().size());
        assertTrue(game.getValidateursVerifies().contains(validateur));
    }

    // Ajoutez d'autres tests en fonction des méthodes de la classe Game

    @Test
    void testSubmitPlayerCodeValid() {
        Game game = new Game();
        CodeSecret codeSecret = new CodeSecret(111);
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.emptyList());
        game.startGame(probleme);

        int validCode = 123;
        game.submitPlayerCode(validCode);

        assertEquals(validCode, game.getCodeJoueur());
        assertTrue(game.getValidateursVerifies().isEmpty());
        assertEquals(0, game.getScore());
    }

    @Test
    void testSubmitPlayerCodeInvalid() {
        Game game = new Game();
        CodeSecret codeSecret = new CodeSecret(111);
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.emptyList());
        game.startGame(probleme);

        int invalidCode = 0; // VERIFIER
        game.submitPlayerCode(invalidCode);

        assertNull(game.getCodeJoueur());
        assertTrue(game.getValidateursVerifies().isEmpty());
        assertEquals(0, game.getScore());
    }

    @Test
    void testUndoChooseValidateur() {
        Game game = new Game();
        CodeSecret codeSecret = new CodeSecret(111);
        Probleme probleme = new Probleme(codeSecret, 1, 1, new ArrayList<>(Collections.emptyList()));
        game.startGame(probleme);

        Validateur validateur1 = new ChiffreExtremumMax();
        Validateur validateur2 = new ChiffreExtremumMax();
        Validateur validateur3 = new ChiffreExtremumMax();

        // Ajouter les validateurs
        game.chooseValidateur(validateur1, 123, probleme);
        game.chooseValidateur(validateur2, 123, probleme);
        game.chooseValidateur(validateur3, 123, probleme);

        // Vérifier que les validateurs ont été ajoutés
        assertEquals(3, game.getValidateursVerifies().size());

        // Appeler la méthode undoChooseValidateur
        game.undoChooseValidateur();

        // Vérifier que le dernier validateur a été retiré
        assertEquals(2, game.getValidateursVerifies().size());
        assertFalse(game.getValidateursVerifies().contains(validateur3));

        // Vérifier que le validateur a été restauré dans le problème
        assertTrue(probleme.getValidateursAssocies().contains(validateur3));

    }

    @Test
    void testSaveValidatorStates() {
        Game game = new Game();
        CodeSecret codeSecret = new CodeSecret(123);
        Probleme probleme = new Probleme(codeSecret, 1, 1, Arrays.asList(new ChiffreExtremumMax(), new ChiffreExtremumMin()));

        Validateur validateur1 = new ChiffreExtremumMax();
        Validateur validateur2 = new ChiffreExtremumMin();

        // Ajouter les validateurs
        game.chooseValidateur(validateur1, 123, probleme);
        game.chooseValidateur(validateur2, 123, probleme);

        // Appeler la méthode saveValidatorStates
        Map<Validateur, Boolean> savedStates = game.saveValidatorStates(game.getValidateursVerifies());

        // Vérifier que la map des états sauvegardés n'est pas vide
        assertFalse(savedStates.isEmpty());

        // Vérifier que les états sauvegardés correspondent aux états actuels des validateurs
        for (Validateur validator : game.getValidateursVerifies()) {
            assertTrue(savedStates.containsKey(validator));
            assertEquals(validator.validationStatus(), savedStates.get(validator));
        }

        // Ajouter d'autres validateurs sans les sauvegarder
        Validateur validateur3 = new ChiffreExtremumMin();
        Validateur validateur4 = new ChiffreExtremumMax();
        game.chooseValidateur(validateur3, 123, probleme);

        // Vérifier que les états des nouveaux validateurs ne sont pas dans la map sauvegardée
        assertFalse(savedStates.containsKey(validateur3));
        assertFalse(savedStates.containsKey(validateur4));
    }

    @Test
    void testChooseValidateurMaximumTrois() {
        Game game = new Game();
        CodeSecret codeSecret = new CodeSecret(123);
        Probleme probleme = new Probleme(codeSecret, 1, 1, Arrays.asList(new ChiffreExtremumMax(), new ChiffreExtremumMin()));

        Validateur validateur1 = new ChiffreExtremumMax();
        Validateur validateur2 = new ChiffreExtremumMin();
        Validateur validateur3 = new ChiffreExtremumMin();
        Validateur validateur4 = new ChiffreExtremumMin();

        // Ajouter les trois premiers validateurs
        game.chooseValidateur(validateur1, 123, probleme);
        game.chooseValidateur(validateur2, 123, probleme);
        game.chooseValidateur(validateur3, 123, probleme);

        // Essayer d'ajouter le quatrième validateur
        game.chooseValidateur(validateur4, 123, probleme);

        // Vérifier que le quatrième validateur n'a pas été ajouté
        assertEquals(3, game.getValidateursVerifies().size());
    }

    @Test
    void testNextRound() {
        Game game = new Game();
        CodeSecret codeSecret = new CodeSecret(123);
        Probleme probleme = new Probleme(codeSecret, 1, 1, Arrays.asList(new ChiffreExtremumMax(), new ChiffreExtremumMin()));

        Validateur validateur1 = new ChiffreExtremumMax();
        Validateur validateur2 = new ChiffreExtremumMin();

        // Ajouter les validateurs
        game.chooseValidateur(validateur1, 123, probleme);
        game.chooseValidateur(validateur2, 123, probleme);



        // Appeler la méthode startGame avec le problème
        game.startGame(probleme);

        // Assurez-vous que le problème n'est pas null avant d'appeler nextRound
        assertNotNull(game.getProblemeActuel());

        // Appeler la méthode nextRound
        game.nextRound();

        // Assurez-vous que le code joueur, les validateurs et le score ont été réinitialisés
        assertEquals("",game.getCodeJoueur());
        assertTrue(game.getValidateursVerifies().isEmpty());
        assertEquals(1, game.getScore());

        // Assurez-vous que les validateurs associés au problème ont été réinitialisés
        for (Validateur validateur : probleme.getValidateursAssocies()) {
            assertNull(validateur.validationStatus());
        }
    }

    @Test
    void testIsValidCode() {
        Game game = new Game();

        // Tester avec un code valide
        assertTrue(game.isValidCode(123));

        // Tester avec un code invalide (chiffre supérieur à 5)
        assertFalse(game.isValidCode(678));

        // Tester avec un code invalide (chiffre inférieur à 1)
        assertFalse(game.isValidCode(045));

        // Tester avec un code invalide (longueur différente de 3 chiffres)
        assertFalse(game.isValidCode(12));

        // Tester avec un code invalide (caractères non numériques)
        //assertFalse(game.isValidCode(1A3));
    }


}
