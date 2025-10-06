package be.esi.prj.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ManageFileTest {

  @TempDir
  Path tempDir;

  private String validPath;
  private String invalidPath;

  private Path ticketDir;
  private Path image1;
  private Path image2;
  private Path nonImageFile;

  @BeforeEach
  void setUp() throws IOException {
    ticketDir = tempDir.resolve("images/ticket");
    Files.createDirectories(ticketDir);

    image1 = ticketDir.resolve("image1.jpg");
    image2 = ticketDir.resolve("image2.png");
    Files.createFile(image1);
    Files.createFile(image2);
    nonImageFile = ticketDir.resolve("text.txt");
    Files.createFile(nonImageFile);

    validPath = ticketDir.toString();
    invalidPath = tempDir.resolve("nonexistent").toString();
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(nonImageFile);
    Files.deleteIfExists(image2);
    Files.deleteIfExists(image1);
    Files.deleteIfExists(ticketDir);
    Files.deleteIfExists(tempDir);
  }

  @Test
  void testConstructorWithInvalidPath() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new ManageFile(invalidPath);
    });
    assertEquals("Path does not exist", exception.getMessage());
  }

  @Test
  void testVerifyIfPathIsFolderWithInvalidPath() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new ManageFile(invalidPath);
    });
    assertEquals("Path does not exist", exception.getMessage());
  }

  @Test
  void testSetPathToFolder() throws NoSuchFieldException, IllegalAccessException {
    ManageFile manageFile = new ManageFile(validPath);
    java.lang.reflect.Field pathField = ManageFile.class.getDeclaredField("pathToFolder");
    pathField.setAccessible(true);
    Path path = (Path) pathField.get(manageFile);
    assertEquals(ticketDir, path);
  }

  @Test
  void testGetAllImages() {
    ManageFile manageFile = new ManageFile(validPath);
    File[] images = manageFile.getAllImages();
    assertNotNull(images);
    assertEquals(2, images.length);
    for (File file : images) {
      String name = file.getName().toLowerCase();
      assertTrue(name.endsWith(".jpg") || name.endsWith(".png"));
    }
  }

  @Test
  void testGetAllImagesWithEmptyFolder() throws IOException {
    Path emptyDir = tempDir.resolve("empty");
    Files.createDirectory(emptyDir);
    ManageFile manageFile = new ManageFile(emptyDir.toString());
    File[] images = manageFile.getAllImages();
    assertNotNull(images);
    assertEquals(0, images.length);
  }
}