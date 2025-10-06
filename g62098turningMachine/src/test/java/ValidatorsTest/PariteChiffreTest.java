package ValidatorsTest;

import Modele.Problemes.CodeSecret;
import Modele.Problemes.Probleme;
import Modele.Validateurs.PariteChiffre;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PariteChiffreTest {

    @Test
    void testPariteChiffreAvecPariteDifferente() {
        PariteChiffre pariteChiffreValidator = new PariteChiffre(1);
        CodeSecret codeSecret = new CodeSecret("246");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteChiffreValidator));
        pariteChiffreValidator.valider("135", probleme);
        assertFalse(pariteChiffreValidator.validationStatus());
    }

    @Test
    void testPariteChiffreAvecPariteLaMeme() {
        PariteChiffre pariteChiffreValidator = new PariteChiffre(2);
        CodeSecret codeSecret = new CodeSecret("585");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteChiffreValidator));
        pariteChiffreValidator.valider("246", probleme);
        assertTrue(pariteChiffreValidator.validationStatus());
    }

    @Test
    void testPariteChiffreAvecPariteIdentiqueCodeVide() {
        PariteChiffre pariteChiffreValidator = new PariteChiffre(3);
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteChiffreValidator));
        pariteChiffreValidator.valider("", probleme);
        assertFalse(pariteChiffreValidator.validationStatus());
    }

    @Test
    void testPariteChiffreAvecProblemeVide() {
        PariteChiffre pariteChiffreValidator = new PariteChiffre(3);
        CodeSecret codeSecret = new CodeSecret("");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteChiffreValidator));
        pariteChiffreValidator.valider("345", probleme);
        assertFalse(pariteChiffreValidator.validationStatus());
    }

    @Test
    void testPariteChiffreAvecChiffre0() {
        PariteChiffre pariteChiffreValidator = new PariteChiffre(0);
        CodeSecret codeSecret = new CodeSecret("345");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteChiffreValidator));
        pariteChiffreValidator.valider("345", probleme);
        assertFalse(pariteChiffreValidator.validationStatus());
    }

    @Test
    void testPariteChiffreAvecChiffreNegatif() {
        PariteChiffre pariteChiffreValidator = new PariteChiffre(-1);
        CodeSecret codeSecret = new CodeSecret("345");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteChiffreValidator));
        pariteChiffreValidator.valider("345", probleme);
        assertFalse(pariteChiffreValidator.validationStatus());
    }

    @Test
    void testPariteChiffreTropGrand() {
        PariteChiffre pariteChiffreValidator = new PariteChiffre(4);
        CodeSecret codeSecret = new CodeSecret("345");
        Probleme probleme = new Probleme(codeSecret, 1, 1, Collections.singletonList(pariteChiffreValidator));
        pariteChiffreValidator.valider("345", probleme);
        assertFalse(pariteChiffreValidator.validationStatus());
    }
}
