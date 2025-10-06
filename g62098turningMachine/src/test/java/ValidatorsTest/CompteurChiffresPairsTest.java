package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.CompteurChiffresPairs;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompteurChiffresPairsTest {

    @Test
    void testCompteurChiffresPairsAvecChiffresPairsSuffisants() {
        CompteurChiffresPairs compteur = new CompteurChiffresPairs();
        CodeSecret codeSecret = new CodeSecret("246");
        Probleme probleme = new Probleme(codeSecret, 2, 4,  Collections.singletonList(compteur));
        compteur.valider("246", probleme);
        assertTrue(compteur.validationStatus());
    }

    @Test
    void testCompteurChiffresPairsSansChiffresPairs() {
        CompteurChiffresPairs compteur = new CompteurChiffresPairs();
        CodeSecret codeSecret = new CodeSecret("135");
        Probleme probleme = new Probleme(codeSecret, 1, 3, Collections.singletonList(compteur));
        compteur.valider("135", probleme);
        assertTrue(compteur.validationStatus());
    }

    @Test
    void testCompteurChiffresPairsAvecChiffresPairsDifferents() {
        CompteurChiffresPairs compteur = new CompteurChiffresPairs();
        CodeSecret codeSecret = new CodeSecret("246");
        Probleme probleme = new Probleme(codeSecret, 2, 4, Collections.singletonList(compteur));
        compteur.valider("135", probleme);
        assertFalse(compteur.validationStatus());
    }

    @Test
    void testCompteurChiffresPairsAvecCodeVide() {
        CompteurChiffresPairs compteur = new CompteurChiffresPairs();
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(compteur));
        compteur.valider("", probleme);
        assertFalse(compteur.validationStatus());
    }

    @Test
    void testCompteurChiffresPairsAvecCodeSecretVide() {
        CompteurChiffresPairs compteur = new CompteurChiffresPairs();
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 2, 4, Collections.singletonList(compteur));
        compteur.valider("135", probleme);
        assertFalse(compteur.validationStatus());
    }
}
