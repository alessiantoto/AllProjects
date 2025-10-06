package model.Commandes;
import model.ColoredShape;
/**
 * La classe ColorCommand représente une commande pour changer la couleur d'une forme géométrique.
 */
public class ColorShapeCommand implements Command{

    private ColoredShape coloredShape;
    private char oldColor;
    private char newColor;

    /**
     * Crée une instance de ColorCommand.
     *
     * @param coloredShape    La forme géométrique à colorer.
     * @param newColor La nouvelle couleur à appliquer.
     */
    public ColorShapeCommand(ColoredShape coloredShape, char newColor, char oldColor) {
        this.coloredShape = coloredShape;
        this.newColor = newColor;
        this.oldColor = oldColor;
    }


    /**
     * Exécute la commande en changeant la couleur de la forme.
     */
    @Override
    public void execute() {
        coloredShape.setColor(newColor);
    }

    /**
     * Annule la commande en restaurant la couleur précédente de la forme.
     */
    @Override
    public void undo() {
        coloredShape.setColor(oldColor);
    }
}
