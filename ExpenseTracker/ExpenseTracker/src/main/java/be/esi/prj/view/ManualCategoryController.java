package be.esi.prj.view;

import be.esi.prj.model.Category;
import be.esi.prj.model.Items.Item;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Contrôleur de la vue permettant à l'utilisateur de sélectionner manuellement une catégorie.
 */
public class ManualCategoryController {

  @FXML private ComboBox<String> parameterComboBox;
  @FXML private Button validateButton;
  @FXML private ImageView itemImageView;

  private MainView mainView;
  private Item currentItem;


  /**
   * Initialise les composants de la vue, en remplissant la liste déroulante avec les catégories disponibles.
   */
  @FXML
  public void initialize() {
    Category[] categories = Category.values();
    ObservableList<String> categoryNames = FXCollections.observableArrayList();
    for (Category category : categories) {
      categoryNames.add(category.name());
    }
    parameterComboBox.setItems(categoryNames);
  }


  /**
   * Gère le clic sur le bouton de validation.
   * Transmet la catégorie sélectionnée à la vue principale si elle est valide.
   */
  @FXML
  private void handleValidateButtonAction() {
    String categoryName = parameterComboBox.getValue();
    if (categoryName != null && !categoryName.isEmpty()) {
      mainView.processCategory(categoryName);
    } else {
    }
  }

  /**
   * Définit les attributs du contrôleur, y compris l'élément courant et la vue principale.
   *
   * @param item     l'élément en cours d'analyse
   * @param mainView la vue principale
   */
  public void setAttributes(Item item, MainView mainView) {
    this.mainView = mainView;
    this.currentItem = item;

    displayImageFromItem();
  }

  /**
   * Affiche l'image associée à l'élément courant dans la vue.
   */
  private void displayImageFromItem() {
    if (currentItem != null) {
      File imageFile = currentItem.getFile();

      if (imageFile != null && imageFile.exists()) {
        try {
          Image itemImage = new Image(imageFile.toURI().toString());
          itemImageView.setImage(itemImage);
        } catch (Exception e) {
          System.err.println(
              "Erreur lors du chargement de l'image à partir du fichier : "
                  + imageFile.getAbsolutePath()
                  + " - "
                  + e.getMessage());
          itemImageView.setImage(null);
        }
      } else {
        itemImageView.setImage(null);
      }
    } else {
      itemImageView.setImage(null);
    }
  }
}
