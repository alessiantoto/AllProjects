package Vue.JavaFxVueComposants;

import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;


/**
 * La classe représentant l'écran de démarrage de l'application JavaFX.
 * Cet écran contient deux boutons radio permettant à l'utilisateur de choisir entre sélectionner un problème existant
 * ou générer un problème aléatoire.
 */
public class StartScreen extends VBox {
    private ToggleGroup toggleGroup = new ToggleGroup();
    public RadioButton chooseProblemRadioButton = new RadioButton("Choisir un problème");
    public RadioButton randomProblemRadioButton = new RadioButton("Problème aléatoire");
    // Layout for the main menu
    VBox mainMenuLayout = new VBox(20); // 20 pixels spacing

    /**
     * Constructeur de la classe StartScreen.
     * Initialise l'interface utilisateur de l'écran de démarrage en appelant la méthode initializeUI().
     */
    public StartScreen() {
        initializeUI();
    }

    /**
     * Initialise l'interface utilisateur de l'écran de démarrage en configurant les boutons radio
     * et le groupe de bascule associé.
     */
    public void initializeUI() {
        // Set up the radio buttons and toggle group
        chooseProblemRadioButton.setToggleGroup(toggleGroup);
        randomProblemRadioButton.setToggleGroup(toggleGroup);

        // Set up VBox properties
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20); // Adjust spacing as needed
        this.getChildren().addAll(chooseProblemRadioButton, randomProblemRadioButton);


        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.getChildren().addAll(chooseProblemRadioButton, randomProblemRadioButton);
        //Ajoute les boutons radio à mainMenuLayout

    }

    /**
     * Obtient le layout principal de l'écran de démarrage.
     *
     * @return Le layout principal (VBox) contenant les boutons radio.
     */
    public VBox getMainMenuLayout(){
        return this.mainMenuLayout;
    }

}
