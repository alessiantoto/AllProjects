package View;

import Model.Sexe;
import Model.StyleDeVie;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * Cette classe représente le côté gauche de l'interface utilisateur où les utilisateurs peuvent saisir leurs données.
 */
public class LeftSide extends GridPane {
    private final TextField tailleTextField = new TextField();
    private final TextField poidsTextField = new TextField();


    private final TextField ageTextField = new TextField();
    private final RadioButton hommeRadioButton = new RadioButton("Homme");
    private final RadioButton femmeRadioButton = new RadioButton("Femme");
    private final ChoiceBox<StyleDeVie> choiceStyle = new ChoiceBox<>();

    //gestionnaire d'événements pour les zones de texte (autoriser uniquement les chiffres)
    private EventHandler<KeyEvent> numericValidationHandler = event -> {
        String character = event.getCharacter();
        if (!character.matches("[0-9]")) {
            event.consume();
        }
    };

    /**
     * Constructeur de la classe LeftSide.
     * Initialise les composants graphiques et ajoute des gestionnaires d'événements.
     */
    public LeftSide() {


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        Label donnees = new Label("Données:");
        gridPane.add(donnees, 0, 0);

        Label lblTaille = new Label("Taille (cm):");
        gridPane.add(lblTaille, 0, 1);
        gridPane.add(tailleTextField, 1, 1, 2, 1);

        Label lblPoids = new Label("Poids (kg):");
        gridPane.add(lblPoids, 0, 2);
        gridPane.add(poidsTextField, 1, 2, 2, 1);

        Label lblAge = new Label("Âge (années):");
        gridPane.add(lblAge, 0, 3);
        gridPane.add(ageTextField, 1, 3, 2, 1);

        Label lblSexe = new Label("Sexe:");
        gridPane.add(lblSexe, 0, 4);
        ToggleGroup sexeToggleGroup = new ToggleGroup();
        hommeRadioButton.setToggleGroup(sexeToggleGroup);
        femmeRadioButton.setToggleGroup(sexeToggleGroup);
        gridPane.add(hommeRadioButton, 1, 4);
        gridPane.add(femmeRadioButton, 2, 4);

        Label lblStyle = new Label("Style de vie:");
        gridPane.add(lblStyle, 0, 5);
        choiceStyle.getItems().addAll(StyleDeVie.SEDENTAIRE, StyleDeVie.PEU_ACTIF, StyleDeVie.ACTIF, StyleDeVie.FORT_ACTIF, StyleDeVie.EXTREMEMENT_ACTIF);
        choiceStyle.getSelectionModel().selectFirst();
        gridPane.add(choiceStyle, 1, 5);

        getChildren().add(gridPane);
        setAlignment(Pos.TOP_CENTER);

        // Ajouter des gestionnaires d'événements aux zones de texte
        addNumericValidation(tailleTextField, "taille");
        addNumericValidation(poidsTextField, "poids");
        addNumericValidation(ageTextField, "age");
    }

    /**
     * Affiche une boîte de dialogue d'erreur avec le message spécifié.
     * @param message Message d'erreur à afficher.
     */

    /**
     * Ajoute un gestionnaire d'événements pour les zones de texte afin de n'autoriser que les caractères numériques.
     * @param textField Zone de texte à laquelle ajouter le gestionnaire d'événements.
     */
    private void addNumericValidation(TextField textField, String fieldName) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, numericValidationHandler);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("0")) {
                showError("Le champ " + fieldName + " ne peut pas être zéro.");
                textField.setText("");
            }
        });
    }

    /**
     * Affiche erreur avec message correspondant
     */
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    /**
     * Réinitialise les champs de texte et les boutons radio.
     */
    public void clearFields() {
        // Ajoutez le code pour réinitialiser les champs de texte et les boutons radio.
        tailleTextField.clear();
        poidsTextField.clear();
        ageTextField.clear();
        hommeRadioButton.setSelected(false);
        femmeRadioButton.setSelected(false);
        choiceStyle.getSelectionModel().clearSelection();
    }

    public Sexe getGender() {
        if (hommeRadioButton.isSelected()) {
            return Sexe.HOMME;
        } else if (femmeRadioButton.isSelected()) {
            return Sexe.FEMME;
        } else {
            // Retourne une valeur par défaut (par exemple, Sexe.HOMME ou Sexe.FEMME)
            return null;
        }
    }


    public double getWeight() {
        String weightText = poidsTextField.getText();
        if (weightText.isEmpty()) {
            return 0;
        }
        // le code pour récupérer le poids à partir du champ de texte poidsTextField.
            return Double.parseDouble(poidsTextField.getText());
    }

    public double getUserHeight() {
        String heightText = tailleTextField.getText();
        if (heightText.isEmpty()) {
            return 0;
        }
        // le code pour récupérer la taille à partir du champ de texte heightTextField.
        return Double.parseDouble(tailleTextField.getText());
    }


    public int getAge() {
        String ageText = ageTextField.getText();
        // le code pour récupérer l'âge à partir du champ de texte ageTextField.
        if (ageText.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(ageTextField.getText());
    }

    public String getSelectedActivityLevel() {
        StyleDeVie selectedLevel = choiceStyle.getValue();
        if (selectedLevel == null) {
            return null;
        }
        // le code pour récupérer le niveau d'activité sélectionné dans la ChoiceBox choiceStyle.
        return choiceStyle.getValue().toString();
    }

    public double getActivityFactor(String selectedActivityLevel) {
        // le code pour déterminer le facteur d'activité en fonction du niveau sélectionné.
        double activityFactor = 0.0;

        switch (selectedActivityLevel) {
            case "SEDENTAIRE":
                activityFactor = 1.2;
                break;
            case "PEU_ACTIF":
                activityFactor = 1.375;
                break;
            case "ACTIF":
                activityFactor = 1.55;
                break;
            case "FORT_ACTIF":
                activityFactor = 1.725;
                break;
            case "EXTREMEMENT_ACTIF":
                activityFactor = 1.9;
                break;
        }

        return activityFactor;
    }

    //getter pour les test

    public TextField getTailleTextField() {
        return tailleTextField;
    }

    public TextField getPoidsTextField() {
        return poidsTextField;
    }

    public TextField getAgeTextField() {
        return ageTextField;
    }

    public RadioButton getHommeRadioButton() {
        return hommeRadioButton;
    }

    public RadioButton getFemmeRadioButton() {
        return femmeRadioButton;
    }

    public ChoiceBox<StyleDeVie> getChoiceStyle() {
        return choiceStyle;
    }

    public EventHandler<KeyEvent> getNumericValidationHandler() {
        return numericValidationHandler;
    }
}
