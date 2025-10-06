package g62098.dev3.oxono.View.JavaFXComponents;

import g62098.dev3.oxono.Controller.ControllerJavaFX;
import g62098.dev3.oxono.Model.Observer;
import g62098.dev3.oxono.Model.Symbol;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class ValidTotemMoves extends VBox implements Observer {
    private ControllerJavaFX controllerJavaFX;
    private Label numberValidMoves;

    public ValidTotemMoves(ControllerJavaFX controllerJavaFX) {
        this.controllerJavaFX = controllerJavaFX;
        this.numberValidMoves = new Label("Valid Totem Moves: 0");
        this.getChildren().add(numberValidMoves);
        this.setSpacing(10);
    }

    /**
     * Met à jour le nombre de mouvements valides du totem.
     */
    @Override
    public void update() {
        // Mise à jour de l'affichage des mouvements valides

        if(controllerJavaFX.getIsTotemSelected()) {
            Symbol totemSymbol = controllerJavaFX.getSelectedTotemSymbol();
            if(totemSymbol!=null) {
                List<int[]> validMoves = controllerJavaFX.getValidTotemMoves(totemSymbol); // Exemple avec Symbol.TOTEM
                numberValidMoves.setText("Valid Totem Moves: " + validMoves.size());
            }
        }
        else{
            numberValidMoves.setText("Valid Totem Moves: " + "0");
        }
    }
}
