package Controller;
import Model.Model;
import Model.WeatherException;
import View.MainView;
import javafx.scene.control.Alert;
import java.time.LocalDate;

/**
 * La classe Controller sert d'intermédiaire entre le Modèle et la Vue.
 * Elle gère les interactions de l'utilisateur depuis la vue, les traite à l'aide du modèle
 * et met à jour la vue en conséquence.
 */
public class Controller {
    private final Model model;
    private final MainView view;


    /**
     * Construit un Controller avec le Modèle et la MainView spécifiés.
     *
     * @param model Le modèle à utiliser pour récupérer les données météorologiques.
     * @param view  La vue principale pour afficher les informations météorologiques.
     */
    public Controller(Model model, MainView view) {
        this.model = model;
        this.view = view;
        this.model.registerObserver(view);
    }

    /**
     * Récupère les données météorologiques en fonction de l'adresse et de la date fournies.
     * Met à jour la vue avec les données météorologiques récupérées ou affiche une alerte en cas d'erreur.
     *
     * @param address L'adresse (nom de la ville) pour laquelle récupérer la météo.
     * @param date    La date pour laquelle récupérer la météo.
     */
    public void actionFetch(String address, LocalDate date) {
        try {
            // Fetch the weather data using the model
            var weatherObject = model.fetch(address, date);
            // Update the view with the weather data (you need to implement this)
            //view.update(weatherObject); car observer mtn
        } catch (WeatherException e) {
            showAlert("Erreur", e.getMessage());
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur inattendue s'est produite : " + e.getMessage());
        }
    }

    /**
     * Affiche une boîte de dialogue d'alerte avec le titre et le message spécifiés.
     * Cette méthode est utilisée pour informer l'utilisateur des erreurs survenues lors du processus de récupération.
     *
     * @param title   Le titre de la boîte de dialogue d'alerte.
     * @param message Le message à afficher dans la boîte de dialogue d'alerte.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
