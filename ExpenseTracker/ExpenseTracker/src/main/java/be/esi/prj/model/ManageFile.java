package be.esi.prj.model;

import java.io.File;
import java.nio.file.Path;

/**
 * La classe ManageFile est utilisée pour gérer les fichiers et les répertoires.
 * Elle permet de vérifier l'existence d'un répertoire, de récupérer le chemin du dossier
 * et de lister tous les fichiers image présents dans ce répertoire.
 */
public class ManageFile {
  private String stringPath;
  private Path pathToFolder;

  /**
   * Constructeur de la classe ManageFile.
   * Initialise l'objet avec le chemin du dossier et effectue les vérifications nécessaires.
   *
   * @param stringPath le chemin du dossier sous forme de chaîne de caractères.
   */
  public ManageFile(String stringPath) {
    this.stringPath = stringPath;
    this.verifyIfPathIsFolder();
    this.setPathToFolder();
  }


  /**
   * Vérifie si le chemin spécifié correspond à un répertoire existant.
   * Si le répertoire n'existe pas, une exception IllegalArgumentException est lancée.
   */
  private void verifyIfPathIsFolder() {
    File file = new File(this.stringPath);
    if (!file.exists()) {
      throw new IllegalArgumentException("Path does not exist");
    }
  }

  /**
   * Définit le chemin sous forme d'objet Path du répertoire spécifié.
   */
  private void setPathToFolder() {
    this.pathToFolder = Path.of(stringPath);
  }

  /**
   * Récupère tous les fichiers image présents dans le répertoire.
   * Les formats d'image pris en charge sont : .jpg, .jpeg, .png, .gif, .bmp et .webp.
   *
   * @return un tableau de fichiers représentant les images dans le répertoire.
   */
  public File[] getAllImages() {
    return pathToFolder
        .toFile()
        .listFiles(
            (dir, name) -> {
              String lowerCaseName = name.toLowerCase();
              return lowerCaseName.endsWith(".jpg")
                  || lowerCaseName.endsWith(".jpeg")
                  || lowerCaseName.endsWith(".png")
                  || lowerCaseName.endsWith(".gif")
                  || lowerCaseName.endsWith(".bmp")
                  || lowerCaseName.endsWith(".webp");
            });
  }
}
