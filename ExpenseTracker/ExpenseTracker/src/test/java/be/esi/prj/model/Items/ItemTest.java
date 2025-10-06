package be.esi.prj.model.Items;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class ItemTest {

  @Test
  void testConstructorWithFileAndContent() {
    File testFile = new File("testfile.txt");
    String testContent = "Contenu du fichier de test";

    Item item = new Item(testFile, testContent);

    assertNotNull(item, "L'objet Item ne devrait pas être null");
    assertEquals(testFile, item.getFile(), "Le fichier de l'Item devrait correspondre à celui fourni");
    assertEquals(testContent, item.getText(), "Le texte de l'Item devrait correspondre à celui fourni");
  }

  @Test
  void testConstructorWithFileOnly() {
    File testFile = new File("anotherfile.log");

    Item item = new Item(testFile);

    assertNotNull(item, "L'objet Item ne devrait pas être null");
    assertEquals(testFile, item.getFile(), "Le fichier de l'Item devrait correspondre à celui fourni");
    assertNull(item.getText(), "Le texte de l'Item devrait être null lorsque seul le fichier est fourni");
  }

  @Test
  void testGetFile() {
    File testFile = new File("somefile.data");
    Item item = new Item(testFile, "Some data");

    File retrievedFile = item.getFile();

    assertEquals(testFile, retrievedFile, "getFile() devrait retourner le fichier correct");
  }

  @Test
  void testGetTextAfterConstructionWithContent() {
    String testContent = "Initial content";
    Item item = new Item(new File("file.txt"), testContent);

    String retrievedText = item.getText();

    assertEquals(testContent, retrievedText, "getText() devrait retourner le contenu initial");
  }

  @Test
  void testGetTextAfterConstructionWithoutContent() {
    Item item = new Item(new File("file_only.txt"));

    String retrievedText = item.getText();

    assertNull(retrievedText, "getText() devrait retourner null après construction sans contenu");
  }

  @Test
  void testSetText() {
    Item item = new Item(new File("file.txt"), "Old content");
    String newContent = "New and improved content";

    item.setText(newContent);
    String retrievedText = item.getText();

    assertEquals(newContent, retrievedText, "setText() devrait mettre à jour le contenu textuel");
  }

  @Test
  void testSetTextWithNull() {
    Item item = new Item(new File("file.txt"), "Some content");

    item.setText(null);
    String retrievedText = item.getText();

    assertNull(retrievedText, "setText(null) devrait définir le contenu textuel à null");
  }
}
