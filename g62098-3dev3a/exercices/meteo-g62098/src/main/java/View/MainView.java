package View;

import Model.Observer;
import Controller.Controller;
import Model.WeatherApi;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * La classe MainView représente la vue principale de l'application météo.
 * Elle est responsable de l'affichage des informations météo et de la
 * prise d'entrées utilisateur.
 */
public class MainView implements Observer {
    private InputView inputView; // To take user input
    private WeatherView weatherView; // To display weather information
    private Controller controller; // Reference to the controller

    /**
     * Constructeur de la classe MainView.
     * Initialise les sous-vues et configure la scène principale.
     *
     * @param stage La fenêtre principale de l'application.
     */
    public MainView(Stage stage) {
        // Initialize the views
        inputView = new InputView(); // Create an instance of InputView
        weatherView = new WeatherView(); // Create an instance of WeatherView

        // Create a BorderPane layout
        BorderPane layout = new BorderPane();

        // Set the WeatherView in the center of the BorderPane
        layout.setCenter(weatherView);

        // Set the InputView at the bottom of the BorderPane
        layout.setBottom(inputView);

        // Create the scene with the BorderPane layout
        Scene scene = new Scene(layout, 800, 600);
        stage.setTitle("Meteo");
        stage.setScene(scene);
        stage.show();

        // Utiliser le bouton de recherche pour définir l'action
        inputView.getFetchButton().setOnMouseClicked(e -> {
            String address = inputView.getText();
            LocalDate date = inputView.getDate();
            if (address != null && date != null) {
                controller.actionFetch(address, date);
            }
        });


    }

    /**
     * Définit le contrôleur pour la vue principale.
     *
     * @param controller Le contrôleur à associer à cette vue.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Méthode pour mettre à jour l'affichage avec les données météorologiques récupérées.
     *
     * @param weatherObject L'objet contenant les données météo à afficher.
     */
    public void update(WeatherApi.WeatherObject weatherObject) {
        System.out.println("Updating weather for: " + weatherObject.locality());
        System.out.println("Min Temp: " + weatherObject.tempMin());
        System.out.println("Max Temp: " + weatherObject.tempMax());
        weatherView.setName(weatherObject.locality()); // Update city name
        weatherView.setTempMin(weatherObject.tempMin()); // Update min temperature
        weatherView.setTempMax(weatherObject.tempMax()); // Update max temperature
        weatherView.setImage(weatherObject.weatherCode()); // Update weather image
    }
}
