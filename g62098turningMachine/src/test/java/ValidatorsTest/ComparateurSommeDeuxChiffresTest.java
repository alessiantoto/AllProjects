package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.ComparateurSommeDeuxChiffres;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparateurSommeDeuxChiffresTest {

    @Test
    void testComparateurSommeDeuxCodesIdentiques() {
        ComparateurSommeDeuxChiffres comparateur = new ComparateurSommeDeuxChiffres();
        CodeSecret codeSecret = new CodeSecret("356");
        Probleme probleme = new Probleme(codeSecret, 3, 6, Collections.singletonList(comparateur));
        comparateur.valider("356", probleme);
        assertTrue(comparateur.validationStatus());
    }

    @Test
    void testComparateurSommeDeuxChiffresEgal() {
        ComparateurSommeDeuxChiffres comparateur = new ComparateurSommeDeuxChiffres();
        CodeSecret codeSecret = new CodeSecret("999");
        Probleme probleme = new Probleme(codeSecret, 9, 9, Collections.singletonList(comparateur));
        comparateur.valider("999", probleme);
        assertTrue(comparateur.validationStatus());
    }

    @Test
    void testComparateurSommeDeuxChiffresOK() {
        ComparateurSommeDeuxChiffres comparateur = new ComparateurSommeDeuxChiffres();
        CodeSecret codeSecret = new CodeSecret("813");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(comparateur));
        comparateur.valider("543", probleme);
        assertTrue(comparateur.validationStatus());
    }

    @Test
    void testComparateurSommeDeuxChiffresCodeInvalide() {
        ComparateurSommeDeuxChiffres comparateur = new ComparateurSommeDeuxChiffres();
        CodeSecret codeSecret = new CodeSecret("12");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(comparateur));
        comparateur.valider("12", probleme);
        assertFalse(comparateur.validationStatus());
    }

    @Test
    void testComparateurSommeDeuxChiffresInverses() {
        ComparateurSommeDeuxChiffres comparateur = new ComparateurSommeDeuxChiffres();
        CodeSecret codeSecret = new CodeSecret("213");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(comparateur));
        comparateur.valider("123", probleme);
        assertTrue(comparateur.validationStatus());
    }
}
