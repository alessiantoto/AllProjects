package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.OrdreChiffres;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrdreChiffresTest {

    @Test
    void testOrdreChiffresAvecOrdreCroissant() {
        OrdreChiffres validateur = new OrdreChiffres();
        CodeSecret codeSecret = new CodeSecret("123");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(validateur));

        validateur.valider("123", probleme);
        assertTrue(validateur.validationStatus());
    }

    @Test
    void testOrdreChiffresAvecOrdreDecroissant() {
        OrdreChiffres validateur = new OrdreChiffres();
        CodeSecret codeSecret = new CodeSecret("321");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(validateur));

        validateur.valider("321", probleme);
        assertTrue(validateur.validationStatus());
    }

    @Test
    void testOrdreChiffresAvecOrdreConstant() {
        OrdreChiffres validateur = new OrdreChiffres();
        CodeSecret codeSecret = new CodeSecret("222");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(validateur));
        validateur.valider("222", probleme);
        assertTrue(validateur.validationStatus());
    }

    @Test
    void testOrdreChiffresAvecUnConstant() {
        OrdreChiffres validateur = new OrdreChiffres();
        CodeSecret codeSecret = new CodeSecret("123");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(validateur));
        validateur.valider("222", probleme);
        assertFalse(validateur.validationStatus());
    }

    @Test
    void testOrdreChiffresAvecProblemeConstant() {
        OrdreChiffres validateur = new OrdreChiffres();
        CodeSecret codeSecret = new CodeSecret("666");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(validateur));
        validateur.valider("653", probleme);
        assertFalse(validateur.validationStatus());
    }

    @Test
    void testOrdreChiffresAvecProblemeConstantEtFauxOrdre() {
        OrdreChiffres validateur = new OrdreChiffres();
        CodeSecret codeSecret = new CodeSecret("666");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(validateur));
        validateur.valider("656", probleme);
        assertFalse(validateur.validationStatus());
    }

    @Test
    void testOrdreChiffresAvecCodeVide() {
        OrdreChiffres validateur = new OrdreChiffres();
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(validateur));
        validateur.valider("", probleme);
        assertFalse(validateur.validationStatus());
    }

    @Test
    void testOrdreChiffresAvecCodeSecretVide() {
        OrdreChiffres validateur = new OrdreChiffres();
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(validateur));
        validateur.valider("456", probleme);
        assertFalse(validateur.validationStatus());
    }
}
