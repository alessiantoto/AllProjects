package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.PariteSommeChiffres;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PariteSommeChiffresTest {

    @Test
    void testPariteSommeChiffresAvecSommePaireCommune() {
        PariteSommeChiffres pariteSommeChiffresValidator = new PariteSommeChiffres();
        CodeSecret codeSecret = new CodeSecret("246");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteSommeChiffresValidator));
        pariteSommeChiffresValidator.valider("835", probleme);
        assertTrue(pariteSommeChiffresValidator.validationStatus());
    }

    @Test
    void testPariteSommeChiffresAvecSommeImpaireCommune() {
        PariteSommeChiffres pariteSommeChiffresValidator = new PariteSommeChiffres();
        CodeSecret codeSecret = new CodeSecret("247");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteSommeChiffresValidator));
        pariteSommeChiffresValidator.valider("623", probleme);
        assertTrue(pariteSommeChiffresValidator.validationStatus());
    }

    @Test
    void testPariteSommeChiffresAvecCodesIdentiques() {
        PariteSommeChiffres pariteSommeChiffresValidator = new PariteSommeChiffres();
        CodeSecret codeSecret = new CodeSecret("247");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteSommeChiffresValidator));
        pariteSommeChiffresValidator.valider("247", probleme);
        assertTrue(pariteSommeChiffresValidator.validationStatus());
    }

    @Test
    void testPariteSommeChiffresAvecCodesDeTaillesDifferentes() {
        PariteSommeChiffres pariteSommeChiffresValidator = new PariteSommeChiffres();
        CodeSecret codeSecret = new CodeSecret("246");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteSommeChiffresValidator));
        pariteSommeChiffresValidator.valider("12", probleme);
        assertFalse(pariteSommeChiffresValidator.validationStatus());
    }

    @Test
    void testPariteSommeChiffresAvecCodeVide() {
        PariteSommeChiffres pariteSommeChiffresValidator = new PariteSommeChiffres();
        CodeSecret codeSecret = new CodeSecret("246");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteSommeChiffresValidator));
        pariteSommeChiffresValidator.valider("", probleme);
        assertFalse(pariteSommeChiffresValidator.validationStatus());
    }
}
