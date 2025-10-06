package be.esi.prj.view;

import be.esi.prj.controller.Controller;
import be.esi.prj.util.Observer;
import be.esi.prj.util.State;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Vue principale de l'application. Gère les changements de vues et observe les changements d'état.
 */
public class MainView implements Observer {

  private BorderPane rootLayout;
  private Stage primaryStage;
  private Controller controller;
  private Scene mainScene;


  /**
   * Initialise la vue principale avec le contrôleur et la fenêtre principale.
   *
   * @param controller   le contrôleur de l'application
   * @param primaryStage la fenêtre principale (stage)
   */
  public MainView(Controller controller, Stage primaryStage) {
    this.controller = controller;
    controller.registerObserver(this);
    this.primaryStage = primaryStage;
    initRootLayout();
    showStartView();
  }


  /**
   * Récupère l'identifiant de l'utilisateur courant.
   *
   * @return l'identifiant de l'utilisateur
   */
  public int fetchUserId() {
    return controller.fetchUserId();
  }


  /**
   * Initialise le layout principal (BorderPane) et la scène.
   */
  private void initRootLayout() {
    rootLayout = new BorderPane();
    mainScene = new Scene(rootLayout, 800, 600);

    String cssPath = "/fxml/css/style.css";
    try {
      String css = getClass().getResource(cssPath).toExternalForm();
      mainScene.getStylesheets().add(css);
    } catch (NullPointerException e) {
      System.err.println(
          "Error loading CSS stylesheet: " + cssPath + ". File not found or path is incorrect.");
      e.printStackTrace();
    } catch (Exception e) {
      System.err.println("An unexpected error occurred while loading CSS: " + e.getMessage());
      e.printStackTrace();
    }

    primaryStage.setScene(mainScene);
    primaryStage.setTitle("Analyse de tickets");
    primaryStage.show();
  }

  /**
   * Affiche la vue de démarrage (StartView).
   */
  public void showStartView() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
      Parent startRoot = loader.load();
      StartViewController startViewController = loader.getController();
      startViewController.setMainView(this);

      rootLayout.setCenter(startRoot);

    } catch (IOException | NullPointerException e) {
      System.err.println("Erreur lors du chargement de start.fxml");
      e.printStackTrace();
    }
  }


  /**
   * Affiche la vue des résultats pour l'utilisateur courant.
   */
  private void showResultView() {
    showResultView(fetchUserId());
  }


  /**
   * Affiche la vue des résultats pour un utilisateur donné.
   *
   * @param userId identifiant de l'utilisateur
   */
  public void showResultView(int userId) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/resultView.fxml"));
      Parent resultRoot = loader.load();

      ResultViewController resultViewController = loader.getController();
      resultViewController.setAttribute(userId, this);

      rootLayout.setCenter(resultRoot);
    } catch (IOException | NullPointerException e) {
      System.err.println("Erreur lors du chargement de resultView.fxml");
      e.printStackTrace();
    }
  }

  /**
   * Affiche la vue de saisie manuelle de la catégorie.
   */
  private void showManualCategoryView() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manualCategory.fxml"));
      Parent manualCategoryRoot = loader.load();
      ManualCategoryController manualViewController = loader.getController();
      manualViewController.setAttributes(controller.getCurrentItem(), this);

      rootLayout.setCenter(manualCategoryRoot);
    } catch (IOException | NullPointerException e) {
      System.err.println("Erreur lors du chargement de manualVerification.fxml");
      e.printStackTrace();
    }
  }

  /**
   * Affiche la vue de saisie manuelle de la catégorie et du prix.
   */
  private void showManualCategoryPriceView() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manualCategoryPrice.fxml"));
      Parent manualCategoryPriceRoot = loader.load();
      rootLayout.setCenter(manualCategoryPriceRoot);
      ManualCategoryPriceController manualCategoryPriceController = loader.getController();

      manualCategoryPriceController.setAttributes(controller.getCurrentItem(), this);
    } catch (IOException | NullPointerException e) {
      System.err.println("Erreur lors du chargement de manualVerification.fxml");
      e.printStackTrace();
    }
  }

  /**
   * Affiche la vue de chargement pendant les opérations asynchrones.
   */
  private void showLoadingView() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loading.fxml"));
      Parent loadingRoot = loader.load();
      rootLayout.setCenter(loadingRoot);
    } catch (IOException | NullPointerException e) {
      System.err.println("Erreur lors du chargement de manualVerification.fxml");
      e.printStackTrace();
    }
  }

  /**
   * Démarre le processus de scan.
   *
   * @param folderPath chemin du dossier contenant les images
   * @param userId     identifiant de l'utilisateur
   */
  public void startScan(String folderPath, int userId) {
    controller.startScan(folderPath, userId);
    showLoadingView();
  }

  /**
   * Traite la catégorie sélectionnée.
   *
   * @param category la catégorie choisie
   */
  public void processCategory(String category) {
    controller.processCategory(category);
    showLoadingView();
  }

  /**
   * Traite la catégorie et le prix sélectionnés.
   *
   * @param category la catégorie choisie
   * @param price    le prix associé
   */
  public void processCategoryPrice(String category, Double price) {
    controller.processCategoryPrice(category, price);
    showLoadingView();
  }

  @Override
  public void update(State state) {
    Platform.runLater(
        () -> {
          if (state == State.START) {
            showStartView();
          } else if (state == State.MANUAL_CATEGORY) {
            showManualCategoryView();
          } else if (state == State.RESULT) {
            showResultView();
          } else if (state == State.MANUAL_CATEGORY_PRICE) {
            showManualCategoryPriceView();
          }
        });
  }
}
