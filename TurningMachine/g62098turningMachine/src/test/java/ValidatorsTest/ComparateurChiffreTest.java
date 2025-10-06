package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.ComparateurChiffre;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparateurChiffreTest {
    @Test
    void testComparateurChiffre() {
        // Créer une instance de ComparateurChiffre avec valeur = 5, numéro de chiffre = 2
        ComparateurChiffre comparateur = new ComparateurChiffre(5, 2);

        // Créer une liste de validateurs et y ajouter notre comparateur
        CodeSecret codeSecret = new CodeSecret("456");
        Probleme probleme = new Probleme(codeSecret, 4, 5, Collections.singletonList(comparateur));
        // Teste si le 2ème chiffre (indice 1) est égal à 5 dans le code "153"
        comparateur.valider("153", probleme);
        assertTrue(comparateur.validationStatus());

        // Teste si le 2ème chiffre (indice 1) n'est pas égal à 5 dans le code "123"
        comparateur.valider("123", probleme);
        assertFalse(comparateur.validationStatus());

        // Teste si le code est vide, la validation doit échouer
        comparateur.valider("", probleme);
        assertFalse(comparateur.validationStatus());
    }
}
