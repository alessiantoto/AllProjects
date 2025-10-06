package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.CompteurChiffre;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompteurChiffreTest {

    @Test
    void testCompteurChiffreIdentique() {
        CompteurChiffre compteur = new CompteurChiffre(5);
        CodeSecret codeSecret = new CodeSecret("555");
        Probleme probleme = new Probleme(codeSecret, 5, 5, Collections.singletonList(compteur));
        compteur.valider("555", probleme);
        assertTrue(compteur.validationStatus());
    }

    @Test
    void testCompteurChiffreSansOccurences() {
        CompteurChiffre compteur = new CompteurChiffre(3);
        CodeSecret codeSecret = new CodeSecret("555");
        Probleme probleme = new Probleme(codeSecret, 5, 5, Collections.singletonList(compteur));
        compteur.valider("555", probleme);
        assertTrue(compteur.validationStatus());
    }

    @Test
    void testCompteurChiffresDifferentsAvecMemesOccurences() {
        CompteurChiffre compteur = new CompteurChiffre(4);
        CodeSecret codeSecret = new CodeSecret("444");
        Probleme probleme = new Probleme(codeSecret, 4, 4, Collections.singletonList(compteur));
        compteur.valider("555", probleme);
        assertFalse(compteur.validationStatus());
    }

    @Test
    void testCompteurChiffreAvecCodeVide() {
        CompteurChiffre compteur = new CompteurChiffre(2);
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 2, 2, Collections.singletonList(compteur));
        compteur.valider("", probleme);
        assertFalse(compteur.validationStatus());
    }

    @Test
    void testCompteurChiffreAvecCodeSecretVide() {
        CompteurChiffre compteur = new CompteurChiffre(1);
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(compteur) );
        compteur.valider("123", probleme);
        assertFalse(compteur.validationStatus());
    }

}
