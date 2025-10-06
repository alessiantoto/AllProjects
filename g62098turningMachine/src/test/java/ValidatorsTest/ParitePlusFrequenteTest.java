package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.ParitePlusFrequente;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParitePlusFrequenteTest {

    @Test
    void testParitePlusFrequenteAvecParitesDifferentes() {
        ParitePlusFrequente paritePlusFrequenteValidator = new ParitePlusFrequente();
        CodeSecret codeSecret = new CodeSecret("246");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(paritePlusFrequenteValidator));
        paritePlusFrequenteValidator.valider("135", probleme);
        assertFalse(paritePlusFrequenteValidator.validationStatus());
    }

    @Test
    void testParitePlusFrequenteAvecParitePaire() {
        ParitePlusFrequente paritePlusFrequenteValidator = new ParitePlusFrequente();
        CodeSecret codeSecret = new CodeSecret("246");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(paritePlusFrequenteValidator));
        paritePlusFrequenteValidator.valider("486", probleme);
        assertTrue(paritePlusFrequenteValidator.validationStatus());
    }

    @Test
    void testParitePlusFrequenteAvecPariteImpaire() {
        ParitePlusFrequente paritePlusFrequenteValidator = new ParitePlusFrequente();
        CodeSecret codeSecret = new CodeSecret("135");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(paritePlusFrequenteValidator));
        paritePlusFrequenteValidator.valider("179", probleme);
        assertTrue(paritePlusFrequenteValidator.validationStatus());
    }

    @Test
    void testParitePlusFrequenteAvecCodesDeTaillesDifferentes() {
        ParitePlusFrequente paritePlusFrequenteValidator = new ParitePlusFrequente();
        CodeSecret codeSecret = new CodeSecret("246");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(paritePlusFrequenteValidator));
        paritePlusFrequenteValidator.valider("12", probleme);
        assertFalse(paritePlusFrequenteValidator.validationStatus());
    }

    @Test
    void testParitePlusFrequenteAvecCodeVide() {
        ParitePlusFrequente paritePlusFrequenteValidator = new ParitePlusFrequente();
        CodeSecret codeSecret = new CodeSecret("246");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(paritePlusFrequenteValidator));
        paritePlusFrequenteValidator.valider("", probleme);
        assertFalse(paritePlusFrequenteValidator.validationStatus());
    }
}
