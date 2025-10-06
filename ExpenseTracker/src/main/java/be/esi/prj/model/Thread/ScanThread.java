package be.esi.prj.model.Thread;

import be.esi.prj.model.Facade;
import be.esi.prj.model.Items.Serie;
import java.util.concurrent.CountDownLatch;

/**
 * Classe représentant un thread de traitement qui scanne une série d'images
 * en utilisant un OCR pour extraire le texte des images.
 */
public class ScanThread extends Thread {
  private Serie serie;
  private OcrScanner ocrScanner;
  private Facade facade;
  private final CountDownLatch latch;


  /**
   * Crée un nouveau thread de scan pour une série d'images donnée.
   *
   * @param facade l'interface pour notifier l'état du traitement
   * @param serie la série d'images à scanner
   * @param latch l'objet {@link CountDownLatch} pour synchroniser les threads
   */
  public ScanThread(Facade facade, Serie serie, CountDownLatch latch) {
    this.serie = serie;
    this.facade = facade;
    this.latch = latch;
  }


  /**
   * Exécute le scan OCR sur la série d'images.
   * Crée un {@link OcrScanner} et démarre l'analyse des images.
   */
  @Override
  public void run() {
    try {
      ocrScanner = new OcrScanner(this.serie);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // Décrémente le latch une fois le traitement terminé
      latch.countDown();
    }
  }
}
