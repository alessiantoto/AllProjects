package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.ChiffreExtremumMin;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChiffreExtremumMinTest {

    @Test
    void testValiderAvecChiffresIdentiques() {
        ChiffreExtremumMin chiffreExtremumMin = new ChiffreExtremumMin();
        CodeSecret codeSecret = new CodeSecret("111");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(chiffreExtremumMin));
        chiffreExtremumMin.valider("111", probleme);
        assertTrue(chiffreExtremumMin.validationStatus());
    }

    @Test
    void testValiderAvecChiffresDifferents() {
        ChiffreExtremumMin chiffreExtremumMin = new ChiffreExtremumMin();
        CodeSecret codeSecret = new CodeSecret("789");
        Probleme probleme = new Probleme(codeSecret, 7, 8, Collections.singletonList(chiffreExtremumMin));
        chiffreExtremumMin.valider("645", probleme);
        assertFalse(chiffreExtremumMin.validationStatus());
    }

    @Test
    void testValiderAvecCodeInvalide() {
        ChiffreExtremumMin chiffreExtremumMin = new ChiffreExtremumMin();
        CodeSecret codeSecret = new CodeSecret("123");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(chiffreExtremumMin));
        chiffreExtremumMin.valider("12", probleme);
        assertFalse(chiffreExtremumMin.validationStatus());
    }

    @Test
    void testValiderAvecCodeSecretInvalide() {
        ChiffreExtremumMin chiffreExtremumMin = new ChiffreExtremumMin();
        CodeSecret codeSecret = new CodeSecret("3");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(chiffreExtremumMin));
        chiffreExtremumMin.valider("345", probleme);
        assertFalse(chiffreExtremumMin.validationStatus());
    }
}
