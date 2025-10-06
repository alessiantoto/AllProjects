package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.ChiffreJumeau;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChiffreJumeauTest {

    @Test
    void testChiffreJumeauAvecChiffreJumeauUnique() {
        ChiffreJumeau chiffreJumeauValidator = new ChiffreJumeau();
        CodeSecret codeSecret = new CodeSecret("112");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(chiffreJumeauValidator));
        chiffreJumeauValidator.valider("112", probleme);
        assertTrue(chiffreJumeauValidator.validationStatus());
    }

    @Test
    void testChiffreJumeauAvecChiffresJumeauxDifferents() {
        ChiffreJumeau chiffreJumeauValidator = new ChiffreJumeau();
        CodeSecret codeSecret = new CodeSecret("122");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(chiffreJumeauValidator));
        chiffreJumeauValidator.valider("122", probleme);
        assertTrue(chiffreJumeauValidator.validationStatus());
    }

    @Test
    void testChiffreJumeauAvecChiffreJumeauDansLeCodeSecretSeulement() {
        ChiffreJumeau chiffreJumeauValidator = new ChiffreJumeau();
        CodeSecret codeSecret = new CodeSecret("323");
        Probleme probleme = new Probleme(codeSecret, 1, 1,Collections.singletonList(chiffreJumeauValidator));
        chiffreJumeauValidator.valider("123", probleme);
        assertFalse(chiffreJumeauValidator.validationStatus());
    }

    @Test
    void testChiffreJumeauAvecAucunChiffreJumeau() {
        ChiffreJumeau chiffreJumeauValidator = new ChiffreJumeau();
        CodeSecret codeSecret = new CodeSecret("456");
        Probleme probleme = new Probleme(codeSecret, 1, 1,Collections.singletonList(chiffreJumeauValidator));
        chiffreJumeauValidator.valider("456", probleme);
        assertTrue(chiffreJumeauValidator.validationStatus());
    }

    @Test
    void testChiffreJumeauAvecPlusieursChiffresJumeaux() {
        ChiffreJumeau chiffreJumeauValidator = new ChiffreJumeau();
        CodeSecret codeSecret = new CodeSecret("112233");
        Probleme probleme = new Probleme(codeSecret, 1, 1,Collections.singletonList(chiffreJumeauValidator));
        chiffreJumeauValidator.valider("112233", probleme);
        assertFalse(chiffreJumeauValidator.validationStatus());
    }

    @Test
    void testChiffreJumeauAvecChiffreJumeauEtCodeVide() {
        ChiffreJumeau chiffreJumeauValidator = new ChiffreJumeau();
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 1, 1,Collections.singletonList(chiffreJumeauValidator));
        chiffreJumeauValidator.valider("", probleme);
        assertFalse(chiffreJumeauValidator.validationStatus());
    }
}
