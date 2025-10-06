import javafx.application.Application;
import javafx.stage.Stage;
import  Model.Model;
import View.MainView;
import Controller.Controller;


/**
 * La classe Main est le point d'entrée de l'application JavaFX.
 */

public class MeteoApp extends Application {
    /**
     * Méthode principale pour lancer l'application.
     *
     * @param args Les arguments de ligne de commande.
     */
    public static void main(String[] args) {
        launch(args); // Lance l'application JavaFX
    }

    /**
     * Méthode pour initialiser et démarrer la scène principale.
     *
     * @param stage La scène principale de l'application.
     */
    @Override
    public void start(Stage stage) {
        // Création des instances de modèle et de vue
        Model model = new Model(); // Instance de Model pour gérer les données
        MainView view = new MainView(stage); // Instance de MainView pour l'interface utilisateur

        // Création du contrôleur avec le modèle et la vue
        Controller controller = new Controller(model, view); // Instance de Controller pour gérer les actions
        view.setController(controller); // Liaison du contrôleur avec la vue
    }

}

