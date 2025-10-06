package be.esi.prj.model.Items;

import be.esi.prj.model.Facade;
import be.esi.prj.model.ManageFile;
import be.esi.prj.model.Thread.ScanThread;
import be.esi.prj.util.State;
import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * La classe Batch gère un ensemble d'images à traiter. Elle divise les images en séries, lance des threads pour traiter chaque série,
 * et notifie un observateur lorsque tout le traitement est terminé.
 * <p>
 * Chaque batch contient un nombre défini d'images (défini par la constante {@link #NUMBER_OF_IMAGES}), qui sont réparties en plusieurs séries.
 * Chaque série est traitée indépendamment par un thread dédié.
 */
public class Batch {

  /**
   * Nombre d'images par série.
   */
  public static final int NUMBER_OF_IMAGES = 4;

  private int numberOfSeries;
  private ScanThread[] listOfScanThread;
  private Serie[] series;
  private ManageFile manageFile;
  private Facade facade;

  /**
   * Constructeur qui initialise un batch avec les images provenant du chemin spécifié.
   *
   * @param facade La façade de l'application, utilisée pour notifier les observateurs.
   * @param path Le chemin du répertoire contenant les images à traiter.
   */
  public Batch(Facade facade, String path) {
    this.manageFile = new ManageFile(path);
    this.facade = facade;
    this.setSeries();
    this.numberOfSeries = series.length;
    this.createAndStartThread();
  }

  /**
   * Retourne la série d'images à l'indice spécifié.
   *
   * @param index L'indice de la série.
   * @return La série correspondante.
   * @throws IndexOutOfBoundsException Si l'indice est invalide (en dehors des bornes).
   */
  public Serie getSeries(int index) {
    if (index < 0 || index >= numberOfSeries) {
      throw new IndexOutOfBoundsException("Index out of bounds");
    }
    return series[index];
  }

  /**
   * Divise les images en séries. Chaque série contient un nombre d'images défini par {@link #NUMBER_OF_IMAGES}.
   */
  private void setSeries() {
    File[] allImages = manageFile.getAllImages();

    if (allImages == null) {
      this.series = new Serie[0];
      return;
    }

    int numberOfSeries = (int) Math.ceil((double) allImages.length / NUMBER_OF_IMAGES);
    this.series = new Serie[numberOfSeries];

    for (int i = 0; i < numberOfSeries; i++) {
      int nbImage = Math.min(NUMBER_OF_IMAGES, allImages.length - i * NUMBER_OF_IMAGES);
      Serie serie = new Serie(nbImage);
      for (int j = 0; j < nbImage; j++) {
        int imageIndex = i * NUMBER_OF_IMAGES + j;
        if (imageIndex < allImages.length) {
          serie.addItem(j, new Item(allImages[imageIndex]));
        }
      }
      this.series[i] = serie;
    }
  }

  /**
   * Crée et lance des threads pour traiter chaque série d'images.
   * Utilise un {@link CountDownLatch} pour attendre la fin de tous les threads.
   */
  private void createAndStartThread() {
    this.listOfScanThread = new ScanThread[this.numberOfSeries];

    if (this.numberOfSeries == 0) {
      facade.notifyObservers(State.RESULT);
      return;
    }

    CountDownLatch latch = new CountDownLatch(this.numberOfSeries);

    for (int i = 0; i < this.numberOfSeries; i++) {
      this.listOfScanThread[i] = new ScanThread(this.facade, this.series[i], latch);
      this.listOfScanThread[i].start();
    }

    new Thread(
            () -> {
              try {
                latch.await();
                facade.allThreadsAreFinished();
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            })
            .start();
  }
}