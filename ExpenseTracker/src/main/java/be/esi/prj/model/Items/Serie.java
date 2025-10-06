package be.esi.prj.model.Items;

/**
 * Représente une série d'objets {@link Item}, correspondant généralement à un groupe d’images à traiter ensemble.
 */
public class Serie {


  private int numberOfImages;

  private Item[] items;

  /**
   * Crée une nouvelle série contenant un nombre spécifié d'éléments.
   *
   * @param numberOfImages Le nombre d'éléments dans la série.
   */
  public Serie(int numberOfImages) {
    this.numberOfImages = numberOfImages;
    this.items = new Item[numberOfImages];
  }

  /**
   * Retourne le nombre total d'éléments dans cette série.
   *
   * @return Le nombre d’éléments.
   */
  public int getNumberOfImages() {
    return numberOfImages;
  }

  /**
   * Ajoute un élément {@link Item} à une position spécifique dans la série.
   *
   * @param index L’index auquel ajouter l’élément.
   * @param item  L’élément à ajouter.
   * @throws IndexOutOfBoundsException si l’index est en dehors des limites valides.
   */
  public void addItem(int index, Item item) {
    if (index >= 0 && index < numberOfImages) {
      this.items[index] = item;
    } else {
      throw new IndexOutOfBoundsException("Index out of bounds: " + index);
    }
  }

  /**
   * Récupère l’élément situé à un index donné.
   *
   * @param index L’index de l’élément souhaité.
   * @return L’élément à l’index donné.
   */
  public Item getItem(int index) {
    return this.items[index];
  }
}
