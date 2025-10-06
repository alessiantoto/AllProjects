package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.ChiffreExtremumMax;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChiffreExtremumMinMaxTest {
    @Test
    void testValiderAvecChiffresIdentiques() {
        ChiffreExtremumMax chiffreExtremumMax = new ChiffreExtremumMax();
        CodeSecret codeSecret = new CodeSecret("111");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(chiffreExtremumMax));
        chiffreExtremumMax.valider("111", probleme);
        assertTrue(chiffreExtremumMax.validationStatus());
    }

    @Test
    void testValiderAvecChiffresDifferents() {
        ChiffreExtremumMax chiffreExtremumMax = new ChiffreExtremumMax();
        CodeSecret codeSecret = new CodeSecret("789");
        Probleme probleme = new Probleme(codeSecret, 7, 8, Collections.singletonList(chiffreExtremumMax));
        chiffreExtremumMax.valider("645", probleme);
        assertFalse(chiffreExtremumMax.validationStatus());
    }

    @Test
    void testValiderAvecCodeInvalide() {
        ChiffreExtremumMax chiffreExtremumMax = new ChiffreExtremumMax();
        CodeSecret codeSecret = new CodeSecret("123");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(chiffreExtremumMax));
        chiffreExtremumMax.valider("12", probleme);
        assertFalse(chiffreExtremumMax.validationStatus());
    }

    @Test
    void testValiderAvecCodeSecretInvalide() {
        ChiffreExtremumMax chiffreExtremumMax = new ChiffreExtremumMax();
        CodeSecret codeSecret = new CodeSecret("3");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(chiffreExtremumMax));
        chiffreExtremumMax.valider("345", probleme);
        assertFalse(chiffreExtremumMax.validationStatus());
    }
    @Test
    void testValiderAvecCodesOK() {
        ChiffreExtremumMax chiffreExtremumMax = new ChiffreExtremumMax();
        CodeSecret codeSecret = new CodeSecret("678");
        Probleme probleme = new Probleme(codeSecret, 1, 2, Collections.singletonList(chiffreExtremumMax));
        chiffreExtremumMax.valider("345", probleme);
        assertTrue(chiffreExtremumMax.validationStatus());
    }

}
