package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

/**
 * La classe InputView représente la vue d'entrée pour saisir le nom de la ville
 * et sélectionner la date pour laquelle les données météo doivent être récupérées.
 */
public class InputView extends HBox {
    private TextField textField; // To input city name
    private DatePicker datePicker; // To select date
    private Button fetchButton; // Button to fetch weather data

    /**
     * Constructeur de la classe InputView.
     * Initialise les composants de la vue d'entrée.
     */
    public InputView() {
        textField = new TextField();
        textField.setPromptText("Entrez le nom de la ville");

        datePicker = new DatePicker(); // Date picker for selecting a date

        fetchButton = new Button("Chercher"); // Button to fetch weather data

        setPadding(new Insets(10));
        setSpacing(10);
        setAlignment(Pos.CENTER);

        getChildren().addAll(textField, datePicker, fetchButton); // Add components to HBox
    }


    /**
     * Récupère le texte saisi dans le champ de texte.
     *
     * @return Le nom de la ville saisi par l'utilisateur.
     */
    public String getText() {
        return textField.getText();
    }

    /**
     * Récupère la date sélectionnée dans le sélecteur de date.
     *
     * @return La date sélectionnée.
     */
    public LocalDate getDate() {
        return datePicker.getValue();
    }

    /**
     * Récupère le bouton de recherche pour permettre à MainView de définir l'action.
     *
     * @return Le bouton de recherche.
     */
    public Button getFetchButton() {
        return fetchButton;
    }
}
