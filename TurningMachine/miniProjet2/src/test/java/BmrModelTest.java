import static org.junit.jupiter.api.Assertions.*;

import Model.BmrModel;
import Model.Sexe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * La classe {@code BmrModelTest} contient les tests unitaires pour la classe {@code BmrModel}.
 * Elle vérifie le bon fonctionnement de la méthode {@code calculateBmr} en diverses situations.
 */
 public class BmrModelTest {
    private BmrModel bmrModel;

    /**
     * Méthode exécutée avant chaque test pour initialiser les objets nécessaires.
     */
    @BeforeEach
    public void setUp() {
        bmrModel = new BmrModel();
    }

    /**
     * Teste le calcul du BMR pour un homme avec un poids, une taille et un âge donnés.
     */
    @Test
    public void testCalculateBmrForHomme() {
        double bmr = bmrModel.calculateBmr(Sexe.HOMME, 78.0, 175.0, 25);
        assertEquals(1839.6, bmr); // Adjust the expected value as needed
    }

    /**
     * Teste le calcul du BMR pour une femme avec un poids, une taille et un âge donnés.
     */
    @Test
    public void testCalculateBmrForFemme() {
        double bmr = bmrModel.calculateBmr(Sexe.FEMME, 65.0, 160.0, 30);
        assertEquals(1426.0, bmr); // Adjust the expected value as needed
    }


    /**
     * Teste le calcul du BMR avec un poids nul. Le résultat attendu dépend de votre implémentation.
     */
    @Test
    public void testCalculateBmrWithZeroWeight() {
        double bmr = bmrModel.calculateBmr(Sexe.FEMME, 0.0, 160.0, 30);
        assertEquals(0.0, bmr); // La valeur attendue dépend de votre implémentation pour un poids nul
    }


    /**
     * Teste le calcul du BMR avec un sexe non spécifié. Le résultat attendu est 0.0.
     */
    @Test
    public void testCalculateBmrWithNullGender() {
        double bmr = bmrModel.calculateBmr(null, 78.0, 175.0, 25);
        assertEquals(0.0, bmr); // Le sexe non spécifié doit renvoyer 0.0
    }

    /**
     * Teste la méthode setCalories pour s'assurer qu'elle définit correctement la valeur des calories.
     */
    @Test
    public void testSetCalories() {
        bmrModel.setCalories(2000.0);
        assertEquals(2000.0, bmrModel.getCalories()); // Assuming a getter method for calories
    }

    /**
     * Teste la méthode setBmr pour s'assurer qu'elle définit correctement la valeur du BMR.
     */
    @Test
    public void testSetBmr() {
        bmrModel.setBmr(1800.0);
        assertEquals(1800.0, bmrModel.getBmr()); // Assuming a getter method for BMR
    }

    /**
     * Teste la méthode setCalories avec une valeur négative.
     * La méthode devrait ignorer les valeurs négatives et maintenir la valeur actuelle.
     */
    @Test
    public void testSetCaloriesWithNegativeValue() {
        bmrModel.setCalories(2000.0);
        bmrModel.setCalories(-100.0);
        assertEquals(2000.0, bmrModel.getCalories()); // Assuming a getter method for calories
    }

    /**
     * Teste la méthode setBmr avec une valeur négative.
     * La méthode devrait ignorer les valeurs négatives et maintenir la valeur actuelle.
     */
    @Test
    public void testSetBmrWithNegativeValue() {
        bmrModel.setBmr(1800.0);
        bmrModel.setBmr(-200.0);
        assertEquals(1800.0, bmrModel.getBmr()); // Assuming a getter method for BMR
    }

    /**
     * Teste la méthode calculateBmr avec des valeurs nulles pour le poids, la taille et l'âge.
     * Le résultat attendu est 0.0.
     */
    @Test
    public void testCalculateBmrWithZeroValues() {
        double bmr = bmrModel.calculateBmr(Sexe.HOMME, 0.0, 0.0, 0);
        assertEquals(0.0, bmr);
    }

    /**
     * Teste la méthode calculateBmr avec un âge négatif.
     * Le résultat attendu est 0.0 car l'âge ne peut pas être négatif.
     */
    @Test
    public void testCalculateBmrWithNegativeAge() {
        double bmr = bmrModel.calculateBmr(Sexe.FEMME, 65.0, 160.0, -30);
        assertEquals(0.0, bmr);
    }



}

