package be.esi.prj.model.Thread;

import be.esi.prj.model.Items.Serie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class OcrScannerTest {

  private Serie serie;
  private OcrScanner ocrScanner;

  @BeforeEach
  void setUp() {
    serie = new Serie(5);
    ocrScanner = new OcrScanner(serie);
  }

  @Test
  void testInitTesseract() throws Exception {
    Method method = OcrScanner.class.getDeclaredMethod("initTesseract");
    method.setAccessible(true);
    assertDoesNotThrow(() -> method.invoke(ocrScanner));
  }
}