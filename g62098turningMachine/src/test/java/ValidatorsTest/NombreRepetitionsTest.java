package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.NombreRepetitions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NombreRepetitionsTest {

    @Test
    void testNombreRepetitionsMemesCodes() {
        NombreRepetitions validateur = new NombreRepetitions();
        CodeSecret codeSecret = new CodeSecret("122");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(validateur));
        validateur.valider("122", probleme);
        assertTrue(validateur.validationStatus());
    }

    @Test
    void testNombreRepetitionsAvecRepetitionsAvecChiffresDifferents() {
        NombreRepetitions validateur = new NombreRepetitions();
        CodeSecret codeSecret = new CodeSecret("122");
        Probleme probleme = new Probleme(codeSecret, 1, 2,  Collections.singletonList(validateur));
        validateur.valider("133", probleme);
        assertTrue(validateur.validationStatus());
    }

    @Test
    void testNombreRepetitionsAvecCodeVide() {
        NombreRepetitions validateur = new NombreRepetitions();
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 1, 2,  Collections.singletonList(validateur));
        validateur.valider("", probleme);
        assertFalse(validateur.validationStatus());
    }

    @Test
    void testNombreRepetitionsAvecCodeSecretVide() {
        NombreRepetitions validateur = new NombreRepetitions();
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 1, 2,  Collections.singletonList(validateur));
        validateur.valider("133", probleme);
        assertFalse(validateur.validationStatus());
    }
}
