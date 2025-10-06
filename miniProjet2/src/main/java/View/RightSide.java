package View;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Cette classe représente le côté droit de l'interface utilisateur où les résultats de calcul sont affichés.
 */
public class RightSide extends GridPane implements PropertyChangeListener {
    private Label bmrLabel;
    private TextField bmrTextField;
    private Label caloriesLabel;
    private TextField caloriesTextField;

    /**
     * Constructeur de la classe RightSide.
     * Initialise les composants graphiques pour afficher les résultats de calcul.
     */
    public RightSide() {
        // Initialisez les composants
        GridPane rightGridPane = new GridPane();
        bmrLabel = new Label("BMR ");
        bmrTextField = new TextField();
        bmrTextField.setDisable(true); // Désactivez le champ de texte pour empêcher la modification

        caloriesLabel = new Label("Calories ");
        caloriesTextField = new TextField();
        caloriesTextField.setDisable(true); // Désactivez le champ de texte pour empêcher la modification

        // Ajoutez les composants à la GridPane
        rightGridPane.add(new Label("Résultats"), 0, 0);
        rightGridPane.add(bmrLabel, 0, 1);
        rightGridPane.add(bmrTextField, 1, 1);
        rightGridPane.add(caloriesLabel, 0, 2);
        rightGridPane.add(caloriesTextField, 1, 2);
        // Ajoutez la GridPane à la HBox
        getChildren().add(rightGridPane);
    }

    /**
     * Méthode invoquée lorsqu'une propriété change.
     *
     * @param evt Événement de changement de propriété.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("BMR".equals(evt.getPropertyName())) {
            // Mettez à jour le champ de texte BMR
            setBmrTextField(String.valueOf(evt.getNewValue()));
        } else if ("Calories".equals(evt.getPropertyName())) {
            // Mettez à jour le champ de texte Calories
            setCaloriesTextField(String.valueOf(evt.getNewValue()));
        }
    }

    public void setBmrTextField(String text) {
        this.bmrTextField.setText(text);
    }

    public void setCaloriesTextField(String caloriesTextField) {
        this.caloriesTextField.setText(caloriesTextField);
    }

}
