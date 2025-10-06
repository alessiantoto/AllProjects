package be.esi.prj.model.Items;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class SerieTest {
  @Test
  void testConstructorPositiveNumberOfImages() {
    int numImages = 5;

    Serie serie = new Serie(numImages);

    assertNotNull(serie, "L'objet Serie ne devrait pas être null");
    assertEquals(numImages, serie.getNumberOfImages(), "getNumberOfImages() devrait retourner le nombre d'images spécifié");
    assertNotNull(serie.getNumberOfImages(), "Le tableau d'items ne devrait pas être null");
    assertEquals(numImages, serie.getNumberOfImages(), "La taille du tableau d'items devrait correspondre au nombre d'images");
    for (int i = 0; i < numImages; i++) {
      assertNull(serie.getItem(i), "Chaque élément du tableau devrait être initialement null à l'index " + i);
    }
  }

  @Test
  void testConstructorZeroNumberOfImages() {
    int numImages = 0;

    Serie serie = new Serie(numImages);

    assertNotNull(serie, "L'objet Serie ne devrait pas être null");
    assertEquals(numImages, serie.getNumberOfImages(), "getNumberOfImages() devrait retourner 0 pour une série vide");
  }

  @Test
  void testGetNumberOfImages() {
    int numImages = 7;
    Serie serie = new Serie(numImages);

    int retrievedNumber = serie.getNumberOfImages();

    assertEquals(numImages, retrievedNumber, "getNumberOfImages() devrait retourner le nombre correct d'images");
  }

  @Test
  void testAddItemValidIndex() {
    int numImages = 3;
    Serie serie = new Serie(numImages);
    Item testItem = new Item(new File("item1.jpg"), "Item 1 content");
    int validIndex = 1;

    serie.addItem(validIndex, testItem);
    Item retrievedItem = serie.getItem(validIndex);

    assertEquals(testItem, retrievedItem, "L'élément ajouté devrait être récupérable à l'index valide");
  }

  @Test
  void testAddItemAtIndexZero() {
    int numImages = 2;
    Serie serie = new Serie(numImages);
    Item testItem = new Item(new File("item0.png"));
    int zeroIndex = 0;

    serie.addItem(zeroIndex, testItem);
    Item retrievedItem = serie.getItem(zeroIndex);

    assertEquals(testItem, retrievedItem, "L'élément ajouté devrait être récupérable à l'index 0");
  }

  @Test
  void testAddItemAtLastValidIndex() {
    int numImages = 4;
    Serie serie = new Serie(numImages);
    Item testItem = new Item(new File("last_item.gif"), "Last item content");
    int lastValidIndex = numImages - 1;

    serie.addItem(lastValidIndex, testItem);
    Item retrievedItem = serie.getItem(lastValidIndex);

    assertEquals(testItem, retrievedItem, "L'élément ajouté devrait être récupérable au dernier index valide");
  }

  @Test
  void testAddItemNegativeIndexThrowsException() {
    // Arrange
    int numImages = 5;
    Serie serie = new Serie(numImages);
    Item testItem = new Item(new File("invalid.txt"));
    int negativeIndex = -1;

    assertThrows(IndexOutOfBoundsException.class, () -> serie.addItem(negativeIndex, testItem),
            "addItem() devrait lancer IndexOutOfBoundsException pour un index négatif");
  }

  @Test
  void testAddItemIndexOutOfUpperBoundsThrowsException() {
    // Arrange
    int numImages = 5;
    Serie serie = new Serie(numImages);
    Item testItem = new Item(new File("invalid2.txt"));
    int outOfBoundsIndex = numImages;
    assertThrows(IndexOutOfBoundsException.class, () -> serie.addItem(outOfBoundsIndex, testItem),
            "addItem() devrait lancer IndexOutOfBoundsException pour un index >= nombre d'images");

    int outOfBoundsIndexPlusOne = numImages + 1;

    assertThrows(IndexOutOfBoundsException.class, () -> serie.addItem(outOfBoundsIndexPlusOne, testItem),
            "addItem() devrait lancer IndexOutOfBoundsException pour un index > nombre d'images");
  }

  @Test
  void testGetItemValidIndexAfterAdd() {
    // Arrange
    int numImages = 3;
    Serie serie = new Serie(numImages);
    Item testItem = new Item(new File("added.item"));
    int validIndex = 2;
    serie.addItem(validIndex, testItem);

    Item retrievedItem = serie.getItem(validIndex);

    assertEquals(testItem, retrievedItem, "getItem() devrait retourner l'élément précédemment ajouté");
  }

  @Test
  void testGetItemValidIndexNoAdd() {
    int numImages = 3;
    Serie serie = new Serie(numImages);
    int validIndex = 1;

    Item retrievedItem = serie.getItem(validIndex);

    assertNull(retrievedItem, "getItem() devrait retourner null pour un index où aucun élément n'a été ajouté");
  }

  @Test
  void testGetItemNegativeIndexThrowsException() {
    int numImages = 5;
    Serie serie = new Serie(numImages);
    int negativeIndex = -1;

    assertThrows(IndexOutOfBoundsException.class, () -> serie.getItem(negativeIndex),
            "getItem() devrait lancer IndexOutOfBoundsException pour un index négatif");
  }

  @Test
  void testGetItemIndexOutOfUpperBoundsThrowsException() {
    int numImages = 5;
    Serie serie = new Serie(numImages);
    int outOfBoundsIndex = numImages;

    assertThrows(IndexOutOfBoundsException.class, () -> serie.getItem(outOfBoundsIndex),
            "getItem() devrait lancer IndexOutOfBoundsException pour un index >= nombre d'images");

    int outOfBoundsIndexPlusOne = numImages + 1;

    assertThrows(IndexOutOfBoundsException.class, () -> serie.getItem(outOfBoundsIndexPlusOne),
            "getItem() devrait lancer IndexOutOfBoundsException pour un index > nombre d'images");
  }
}
