package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.ComparateurDeuxChiffres;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparateurDeuxChiffresTest {

    @Test
    void testComparateurDeuxChiffresPlusPetit() {
        ComparateurDeuxChiffres comparateur = new ComparateurDeuxChiffres(1, 2);
        CodeSecret codeSecret = new CodeSecret("356");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(comparateur));
        comparateur.valider("356", probleme);
        assertTrue(comparateur.validationStatus());
    }

    @Test
    void testComparateurDeuxChiffresEgal() {
        ComparateurDeuxChiffres comparateur = new ComparateurDeuxChiffres(2, 3);
        CodeSecret codeSecret = new CodeSecret("999");
        Probleme probleme = new Probleme(codeSecret, 2, 3, Collections.singletonList(comparateur));
        comparateur.valider("999", probleme);
        assertTrue(comparateur.validationStatus());
    }

    @Test
    void testComparateurDeuxChiffresPlusGrand() {
        ComparateurDeuxChiffres comparateur = new ComparateurDeuxChiffres(1, 3);
        CodeSecret codeSecret = new CodeSecret("123");
        Probleme probleme = new Probleme(codeSecret, 1, 3, Collections.singletonList(comparateur));
        comparateur.valider("123", probleme);
        assertTrue(comparateur.validationStatus());
    }

    @Test
    void testComparateurDeuxChiffresInvalide() {
        ComparateurDeuxChiffres comparateur = new ComparateurDeuxChiffres(1, 2);
        CodeSecret codeSecret = new CodeSecret("12");
        Probleme probleme = new Probleme(codeSecret, 1, 3, Collections.singletonList(comparateur));
        comparateur.valider("123", probleme);
        assertFalse(comparateur.validationStatus());
    }
}
