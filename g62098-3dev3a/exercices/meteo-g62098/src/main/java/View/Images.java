package View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * La classe Images est responsable de la gestion des icônes météo.
 */
public class Images {

    /**
     * Récupère l'ImageView correspondant au code météo fourni.
     *
     * @param weatherCode Le code météo utilisé pour déterminer l'icône à afficher.
     * @return ImageView L'ImageView contenant l'icône météo correspondante.
     */
    public static ImageView getImage(int weatherCode) {
        String iconPath; // Chemin vers l'image de l'icône basé sur le code météo
        ImageView weatherIcon = new ImageView(); // Crée un nouvel ImageView pour l'icône

        // Déterminer le chemin de l'icône en fonction du code météo
        if (weatherCode == 0) {
            iconPath = "/images/sunny.png"; // Icône pour le temps ensoleillé
        } else if (weatherCode >= 1 && weatherCode <= 53) {
            iconPath = "/images/cloudy.png"; // Icône pour le temps nuageux
        } else if (weatherCode >= 61 && weatherCode <= 82) {
            iconPath = "/images/rain.png"; // Icône pour le temps pluvieux
        } else {
            iconPath = "/images/thunderstorm.png"; // Icône pour les orages
        }

        // Charger l'image et la définir dans l'ImageView weatherIcon
        try {
            Image icon = new Image(Objects.requireNonNull(Images.class.getResourceAsStream(iconPath)));
            weatherIcon.setImage(icon); // Définir l'image de l'icône
            weatherIcon.setFitWidth(100);  // Définir une largeur spécifique pour l'icône
            weatherIcon.setFitHeight(100); // Définir une hauteur spécifique pour l'icône
            weatherIcon.setPreserveRatio(true); // Préserver le rapport d'aspect
        } catch (Exception e) {
            System.out.println("Erreur de chargement de l'icône pour le code météo " + weatherCode + ": " + e.getMessage());
        }

        return weatherIcon; // Retourner l'ImageView avec l'icône chargée
    }
}
