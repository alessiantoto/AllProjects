package be.esi.prj.model.Strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExtractWithRegexTest {
  @Test
  void testExtractPriceWithTotalAndComma() {
    // Arrange
    ExtractWithRegex extractor = new ExtractWithRegex();
    String text = "Articles: 10\nTotal: 123,45 €\nMerci de votre visite!";

    Double price = extractor.extractPrice(text);

    assertNotNull(price, "Le prix ne devrait pas être null");
    assertEquals(123.45, price, 0.001, "Le prix extrait devrait être correct");
  }

  @Test
  void testExtractPriceWithDifferentCase() {
    ExtractWithRegex extractor = new ExtractWithRegex();
    String text = "NET à Payer 99.99€";

    Double price = extractor.extractPrice(text);

    // Assert
    assertNotNull(price, "Le prix ne devrait pas être null");
    assertEquals(99.99, price, 0.001, "Le prix extrait devrait être correct avec différentes casses");
  }

  @Test
  void testExtractPriceMultiLine() {
    ExtractWithRegex extractor = new ExtractWithRegex();
    String text = "Ligne 1\nLigne 2\nMontant dû 75,00 €\nLigne 4";

    Double price = extractor.extractPrice(text);

    assertNotNull(price, "Le prix ne devrait pas être null dans un texte multiligne");
    assertEquals(75.00, price, 0.001, "Le prix extrait devrait être correct dans un texte multiligne");
  }

  @Test
  void testExtractPriceNoPriceFound() {
    ExtractWithRegex extractor = new ExtractWithRegex();
    String text = "Ceci est un texte sans prix.";

    Double price = extractor.extractPrice(text);

    assertNull(price, "Le prix devrait être null si aucun prix n'est trouvé");
  }

  @Test
  void testExtractPriceWithSpacesAroundDecimal() {
    ExtractWithRegex extractor = new ExtractWithRegex();
    String text = "reste à payer: 100 . 50";

    Double price = extractor.extractPrice(text);

    assertNotNull(price, "Le prix ne devrait pas être null avec des espaces autour du décimal");
    assertEquals(100.50, price, 0.001, "Le prix extrait devrait être correct avec des espaces autour du décimal");
  }

  @Test
  void testExtractPriceWithInvalidNumberFormat() {
    ExtractWithRegex extractor = new ExtractWithRegex();
    String text = "Total: 123.XX €";

    Double price = extractor.extractPrice(text);

    assertNull(price, "Le prix devrait être null si le format du nombre est invalide");
  }

  @Test
  void testExtractPriceEmptyText() {
    ExtractWithRegex extractor = new ExtractWithRegex();
    String text = "";

    Double price = extractor.extractPrice(text);

    assertNull(price, "Le prix devrait être null pour un texte vide");
  }

  @Test
  void testExtractPriceKeywordWithoutDecimalPart() {
    ExtractWithRegex extractor = new ExtractWithRegex();
    String text = "Total: 123 €";

    Double price = extractor.extractPrice(text);

    assertNull(price, "Le prix devrait être null si la partie décimale est manquante");
  }

  @Test
  void testExtractPriceMultipleMatches() {
    ExtractWithRegex extractor = new ExtractWithRegex();
    String text = "Premier total: 10.50 €\nDeuxième total: 25.75 €";

    Double price = extractor.extractPrice(text);

    assertNotNull(price, "Le prix ne devrait pas être null avec plusieurs correspondances");
    assertEquals(10.50, price, 0.001, "Le prix extrait devrait être la première correspondance trouvée");
  }
}
