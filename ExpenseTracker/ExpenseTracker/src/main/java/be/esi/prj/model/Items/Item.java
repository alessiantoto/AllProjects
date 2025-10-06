package be.esi.prj.model.Items;

import java.io.File;

/**
 * Représente un élément pouvant être un fichier ou un texte dans un processus de traitement de lot.
 * Cette classe permet de manipuler un fichier et/ou un contenu textuel associé à un élément.
 */

public class Item {
  private File file;
  private String text;

  /**
   * Crée un élément avec un fichier et un contenu textuel spécifiés.
   *
   * @param file Le fichier associé à l'élément.
   * @param content Le contenu textuel de l'élément.
   */
  public Item(File file, String content) {
    this.file = file;
    this.text = content;
  }

  /**
   * Crée un élément avec un fichier spécifié et sans contenu textuel.
   *
   * @param file Le fichier associé à l'élément.
   */
  public Item(File file) {
    this(file, null);
  }


  public File getFile() {
    return file;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
