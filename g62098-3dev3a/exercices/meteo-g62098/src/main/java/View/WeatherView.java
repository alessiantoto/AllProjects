package View;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * La classe WeatherView représente la vue pour afficher les informations météorologiques,
 * y compris le nom de la ville, les températures minimales et maximales, et une icône météo.
 */
public class WeatherView extends VBox {
    private Text cityName; // Text field to display the city's name
    private Text tempMin; // Text field to display minimum temperature
    private Text tempMax; // Text field to display maximum temperature
    private ImageView weatherIcon; // ImageView to display weather icon

    /**
     * Constructeur pour initialiser les composants de WeatherView.
     */
    public WeatherView() {
        cityName = new Text(""); // Initialize with empty text
        cityName.setFont(Font.font("Arial", 30)); // Set font size

        tempMin = new Text(""); // Initialize with empty text
        tempMin.setFont(Font.font("Arial", 20)); // Set font size

        tempMax = new Text(""); // Initialize with empty text
        tempMax.setFont(Font.font("Arial", 20)); // Set font size

        weatherIcon = new ImageView(); // Initialize ImageView for weather icon
        weatherIcon.setFitWidth(100);  // Set a specific width for the icon
        weatherIcon.setFitHeight(100); // Set a specific height for the icon
        weatherIcon.setPreserveRatio(true); // Preserve aspect ratio

        // Ajoutez tous les composants au VBox et centrez-les
        setAlignment(Pos.CENTER);  // Centre les éléments du VBox
        setSpacing(20);  // Ajoute un espacement entre les éléments (20 pixels)
        // Add all components to the VBox
        getChildren().addAll(cityName, tempMin, tempMax, weatherIcon);
    }

    /**
     * Méthode pour définir le nom de la ville.
     *
     * @param name Le nom de la ville à afficher.
     */
    public void setName(String name) {
        cityName.setText(name); // Update the city name text
    }

    /**
     * Méthode pour définir la température minimale.
     *
     * @param temp La température minimale à afficher.
     */
    public void setTempMin(double temp) {
        tempMin.setText(String.format("Temp min: %.1f °C", temp)); // Update minimum temperature text
    }

    /**
     * Méthode pour définir la température maximale.
     *
     * @param temp La température maximale à afficher.
     */
    public void setTempMax(double temp) {
        tempMax.setText(String.format("Temp max: %.1f °C", temp)); // Update maximum temperature text
    }

    /**
     * Méthode pour définir l'icône météo en utilisant la classe Images.
     *
     * @param weatherCode Le code météo à utiliser pour déterminer l'icône.
     */
    public void setImage(int weatherCode) {
        Image iconImage = Images.getImage(weatherCode).getImage();
        weatherIcon.setImage(iconImage);
    }
}