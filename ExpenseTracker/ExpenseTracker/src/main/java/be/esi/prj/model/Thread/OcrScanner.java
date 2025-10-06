package be.esi.prj.model.Thread;

import be.esi.prj.model.Items.Serie;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * Classe chargée de scanner les images d'une série avec l'OCR Tesseract
 * et d'extraire le texte de chaque image.
 */
public class OcrScanner {

  Tesseract tesseract;

  Serie serie;

  /**
   * Crée un scanner OCR et démarre automatiquement le traitement des images.
   *
   * @param serie la série d'images à traiter
   */
  public OcrScanner(Serie serie) {
    this.serie = serie;

    this.initTesseract();
    this.startScanning();
  }

  /**
   * Initialise l'instance de Tesseract avec les données linguistiques.
   */
  private void initTesseract() {
    tesseract = new Tesseract();
    try {
      URI uriConfig = ClassLoader.getSystemResource("data").toURI();
      Path path = Paths.get(uriConfig);
      String dataDirectory = path.toAbsolutePath().toString();

      tesseract.setDatapath(dataDirectory);
      tesseract.setLanguage("fra");
    } catch (Exception e) {
      throw new IllegalStateException(
              "Erreur lors de l'initialisation de Tesseract : " + e.getMessage(), e);
    }
  }

  /**
   * Scanne chaque image de la série et enregistre le texte reconnu dans l'objet {@code Item}.
   */
  private void startScanning() {
    for (int i = 0; i < serie.getNumberOfImages(); i++) {
      serie.getItem(i).setText(scan(serie.getItem(i).getFile()));
    }
  }

  /**
   * Applique l'OCR sur une image donnée.
   *
   * @param file le fichier image à scanner
   * @return le texte reconnu, ou {@code null} en cas d'erreur
   */
  private String scan(File file) {
    String result = null;
    try {
      result = tesseract.doOCR(file);
    } catch (TesseractException e) {
      e.printStackTrace();
    }
    return result;
  }
}
