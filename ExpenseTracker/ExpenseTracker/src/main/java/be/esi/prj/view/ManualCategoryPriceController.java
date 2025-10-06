package be.esi.prj.view;

import be.esi.prj.model.Category;
import be.esi.prj.model.Items.Item;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Contrôleur de la vue manuelle pour l’attribution de la catégorie et du prix à un item.
 */
public class ManualCategoryPriceController {

  @FXML private ImageView itemImageView;
  @FXML private TextField priceParameter;
  @FXML private ComboBox<String> categoryParameter;
  @FXML private Button validatePriceButton;
  @FXML private Label errorLabel;

  private MainView mainView;
  private Item currentItem;

  /**
   * Initialise la liste déroulante avec les catégories disponibles.
   */
  @FXML
  public void initialize() {
    Category[] categories = Category.values();
    ObservableList<String> categoryNames = FXCollections.observableArrayList();
    for (Category category : categories) {
      categoryNames.add(category.name());
    }
    categoryParameter.setItems(categoryNames);
  }

  /**
   * Gère la validation de la catégorie et du prix.
   * Affiche les erreurs éventuelles ou transmet les données à la vue principale.
   */
  @FXML
  private void handleValidatePriceButtonAction() {
    String priceText = priceParameter.getText();
    String categoryText = categoryParameter.getValue();

    if (priceText == null || priceText.trim().isEmpty()) {
      errorLabel.setText("Veuillez entrer le prix.");
      errorLabel.setVisible(true);
      errorLabel.setManaged(true);
      return;
    }

    if (categoryText == null || categoryText.isEmpty()) {
      errorLabel.setText("Veuillez sélectionner une catégorie.");
      errorLabel.setVisible(true);
      errorLabel.setManaged(true);
      return;
    }

    try {
      double price = Double.parseDouble(priceText);

      errorLabel.setVisible(false);
      errorLabel.setManaged(false);

      if (mainView != null) {
        mainView.processCategoryPrice(categoryText, price);
      } else {
        System.err.println("MainView reference is null. Cannot process price and category.");
      }

    } catch (NumberFormatException e) {
      errorLabel.setText("Veuillez entrer un nombre valide pour le prix (ex: 12.34).");
      errorLabel.setVisible(true);
      errorLabel.setManaged(true);
      System.err.println("Invalid price format: " + priceText);
    } catch (IllegalArgumentException e) {
      errorLabel.setText("Catégorie sélectionnée invalide.");
      errorLabel.setVisible(true);
      errorLabel.setManaged(true);
      System.err.println("Invalid category selected: " + categoryText);
    } catch (Exception e) {
      errorLabel.setText("Une erreur est survenue lors de la validation.");
      errorLabel.setVisible(true);
      errorLabel.setManaged(true);
      System.err.println("An error occurred during validation: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Définit les attributs nécessaires à l'affichage et à l'interaction avec la vue.
   *
   * @param item     l'élément à traiter
   * @param mainView la vue principale
   */
  public void setAttributes(Item item, MainView mainView) {
    this.mainView = mainView;
    this.currentItem = item;

    priceParameter.clear();
    categoryParameter.getSelectionModel().clearSelection();
    errorLabel.setVisible(false);
    errorLabel.setManaged(false);

    displayImageFromItem();
  }

  /**
   * Affiche l’image associée à l’item dans la vue, en essayant plusieurs méthodes de chargement.
   */
  private void displayImageFromItem() {
    itemImageView.setImage(null);

    if (currentItem != null) {
      File imageFile = currentItem.getFile();

      if (imageFile != null && imageFile.exists()) {
        Image itemImage = null;
        boolean loaded = false;

        try {
          itemImage = new Image(imageFile.toURI().toString());
          if (!itemImage.isError()) {
            itemImageView.setImage(itemImage);
            loaded = true;
          } else {
            System.err.println(
                "Image loading failed via URI for file: "
                    + imageFile.getAbsolutePath()
                    + ". Error: "
                    + itemImage.getException());
          }
        } catch (Exception e) {
          System.err.println(
              "Exception during image loading via URI for file: "
                  + imageFile.getAbsolutePath()
                  + " - "
                  + e.getMessage());
        }

        if (!loaded) {
          try (FileInputStream fis = new FileInputStream(imageFile)) {
            itemImage = new Image(fis);
            if (!itemImage.isError()) {
              itemImageView.setImage(itemImage);
              loaded = true;
            } else {
              System.err.println(
                  "Image loading failed via FileInputStream for file: "
                      + imageFile.getAbsolutePath()
                      + ". Error: "
                      + itemImage.getException());
            }
          } catch (FileNotFoundException e) {
            System.err.println(
                "File not found during FileInputStream loading: "
                    + imageFile.getAbsolutePath()
                    + " - "
                    + e.getMessage());
          } catch (Exception e) {
            System.err.println(
                "Exception during image loading via "
                    + "FileInputStream for file: "
                    + imageFile.getAbsolutePath()
                    + " - "
                    + e.getMessage());
          }
        }

        if (!loaded) {
          System.err.println("Failed to load image from file: " + imageFile.getAbsolutePath());
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
