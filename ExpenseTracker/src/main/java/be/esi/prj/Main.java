package be.esi.prj;

import be.esi.prj.controller.Controller;
import be.esi.prj.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Point d'entrée principal de l'application JavaFX.
 * Initialise le contrôleur et la vue principale.
 */

public class Main extends Application {

  private Controller controller;



  /**
   * Méthode appelée au démarrage de l'application JavaFX.
   * Initialise le contrôleur et la vue principale.
   *
   * @param primaryStage la fenêtre principale de l'application
   */
  @Override
  public void start(Stage primaryStage) {
    controller = new Controller();
    MainView view = new MainView(controller, primaryStage);
  }

  /**
   * Méthode main standard.
   * Lance l'application JavaFX.
   *
   * @param args les arguments de la ligne de commande
   */
  public static void main(String[] args) {
    launch(args);
  }
}
