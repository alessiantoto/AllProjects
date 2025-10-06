package Vue.JavaFxVueComposants;

import Controleur.ControleurJavaFx;
import Modele.Facade.Facade;
import Modele.LettreRobotValidatorMapping;
import Modele.Validateurs.Factory;
import Modele.Validateurs.Validateur;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

/**
 * Composant personnalisé JavaFX représentant une mise en page avec des en-têtes de robots et des validateurs associés.
 * Chaque robot est représenté par une image et une lettre, et en dessous, son validateur associé avec l'état de validation.
 */
public class RobotWithValidator extends HBox {  //observateur aussi, observer un validateur du modele
    private ControleurJavaFx controleur;
    private HBox robotHeadersLayout;
    private List<VBox> robotLayouts;
    private List<LettreRobotValidatorMapping> lettreRobotValidatorMappings;
    private Map<String, Boolean> validateurEtatMap;
    private Facade facade;

    /**
     * Construit une nouvelle instance de RobotWithValidator.
     *
     * @param controleur                    Le contrôleur JavaFX fournissant l'interaction avec l'interface utilisateur.
     * @param facade                        L'instance de façade permettant l'accès à la logique de l'application.
     * @param robotHeadersLayout            La mise en page contenant les en-têtes de robots.
     * @param robotLayouts                  La liste des mises en page pour les robots individuels.
     * @param lettreRobotValidatorMappings  La correspondance entre les lettres des robots et leurs validateurs associés.
     * @param validateurEtatMap             La carte suivant l'état de validation de chaque validateur.
     */
    public RobotWithValidator(ControleurJavaFx controleur, Facade facade, HBox robotHeadersLayout, List<VBox> robotLayouts, List<LettreRobotValidatorMapping> lettreRobotValidatorMappings, Map<String, Boolean> validateurEtatMap) {
        this.controleur = controleur;
        this.facade = facade;  // Set the facade instance
        this.robotHeadersLayout = robotHeadersLayout;
        this.robotLayouts = robotLayouts;
        this.lettreRobotValidatorMappings = lettreRobotValidatorMappings;
        this.validateurEtatMap = validateurEtatMap;
    }

    /**
     * Crée et renvoie une mise en page HBox contenant les en-têtes de robots avec leurs images et les validateurs associés.
     * Chaque robot est représenté par une image et une lettre, et en dessous, son validateur associé avec l'état de validation.
     *
     * @param imagePath   Le chemin de base pour les images des robots.
     * @param validateurs La liste des validateurs associés aux robots.
     * @return La mise en page HBox construite contenant les en-têtes de robots.
     */
    public HBox createRobotHeaders(String imagePath, List<Validateur> validateurs) {
        HBox newRobotHeadersLayout = new HBox();    //contien les en tetes
        for (int i = 0; i < validateurs.size(); i++) {
            Validateur validateur = validateurs.get(i);
            char robotChar = (char) ('A' + i);  //en fonction de l indice
            String robotImagePath = imagePath + "robot" + robotChar + ".png";

            try {
                ImageView robotImageView = createRobotImageView(robotImagePath);
                Label robotLetterLabel = new Label(String.valueOf(robotChar)); // Créer un Label avec la lettre du robot
                VBox robotLayout = new VBox(10);
                robotLayout.setAlignment(Pos.CENTER);
                robotLayout.getChildren().add(robotImageView);  //photo robot

                // Ajoutez le validateur au bas du robot
                ImageView validatorImageView = createValidatorImageView(validateur);
                robotLayout.getChildren().addAll(robotLetterLabel, validatorImageView); //lettre et validateur

                // Ajoutez un label en bas de la VBox pour afficher l'état du validateur
                addLettreRobotValidatorMapping(String.valueOf(robotChar), validateur.getDescription());
                validateurEtatMap.put(validateur.getDescription(), null);
                Label resultLabel = new Label("État : non vérifié");
                resultLabel.setStyle("-fx-font-weight: bold;");
                robotLayout.getChildren().add(resultLabel);

                robotLayouts.add(robotLayout);
                this.getChildren().add(robotLayout);
                //System.out.println("pac");
            } catch (Exception e) {
                e.printStackTrace();
                controleur.showAlert("Erreur de chargement d'image", "Impossible de charger l'image du robot : " + robotImagePath);
            }
        }
        newRobotHeadersLayout.getChildren().addAll(robotLayouts);
        return newRobotHeadersLayout;
    }

    /**
     * Crée et renvoie une ImageView pour l'image du robot.
     *
     * @param imagePath Le chemin de l'image du robot.
     * @return L'ImageView pour l'image du robot.
     */
    private ImageView createRobotImageView(String imagePath) {
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            return imageView;
        } catch (Exception e) {
            e.printStackTrace();
            controleur.showAlert("Erreur de chargement d'image", "Impossible de charger l'image du robot : " + imagePath);
            return null;
        }
    }


    /**
     * Crée et renvoie une ImageView pour l'image du validateur.
     *
     * @param validator Le validateur pour lequel l'image est créée.
     * @return L'ImageView pour l'image du validateur.
     */
    private ImageView createValidatorImageView(Validateur validator) {
        try {
            String validatorImagePath = getValidatorImagePath(validator);

            if (validatorImagePath == null) {
                controleur.showAlert("Chemin incorect vers image", "Chemin de l'image du validateur est null pour le validateur : " + validator);
            }

            Image image = new Image(getClass().getResourceAsStream(validatorImagePath));
            ImageView imageView = new ImageView(image);
            // Ajuster la taille de l'image du validateur
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);

            imageView.setPreserveRatio(true);
            return imageView;
        } catch (Exception e) {
            e.printStackTrace();
            controleur.showAlert("Erreur de chargement d'image", "Impossible de charger l'image du validateur.");
            return null;
        }
    }

    /**
     * Obtient le chemin de l'image du validateur.
     *
     * @param validator Le validateur pour lequel le chemin est obtenu.
     * @return Le chemin de l'image du validateur.
     */
    public String getValidatorImagePath(Validateur validator) {
        Factory factory = facade.getListeValidateurs();

        if (factory != null) {
            int validatorIndex = factory.getIndiceByValidator(validator);
            return "/TuringMachine-assets/images/card" + validatorIndex + ".png";
        } else {
            controleur.showAlert("Validateurs nuls", "Votre liste de validateurs est vide");
            return null;
        }
    }

    /**
     * Ajoute une correspondance entre la lettre d'un robot et son validateur associé à la liste.
     *
     * @param lettreRobot           La lettre représentant le robot.
     * @param descriptionValidator La description du validateur associé.
     */
    public void addLettreRobotValidatorMapping(String lettreRobot, String descriptionValidator) {
        LettreRobotValidatorMapping mapping = new LettreRobotValidatorMapping(lettreRobot, descriptionValidator);
        lettreRobotValidatorMappings.add(mapping);  //associe lettre robot a description validateur
    }
}
