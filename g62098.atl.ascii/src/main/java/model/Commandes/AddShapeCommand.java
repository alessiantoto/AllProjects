package model.Commandes;

import model.Drawing;
import model.Shape;

/**
 * La classe AddShapeCommand représente une commande pour ajouter une forme au dessin.
 * Elle implémente l'interface Command et définit les méthodes execute() et undo().
 */
public class AddShapeCommand implements Command {
    private Drawing drawing;
    private Shape shape;

    /**
     * Crée une nouvelle instance de la commande d'ajout de forme.
     *
     * @param drawing Le dessin auquel la forme doit être ajoutée.
     * @param shape   La forme à ajouter.
     */
    public AddShapeCommand(Drawing drawing, Shape shape) {
        this.drawing = drawing;
        this.shape = shape;
    }


    /**
     * Exécute la commande en ajoutant la forme au dessin.
     */
    @Override
    public void execute() {
        drawing.addShape(shape);
    }

    /**
     * Annule la commande en retirant la forme du dessin.
     * Si la forme n'a pas été ajoutée, cette méthode n'affectera rien.
     */
    @Override
    public void undo() {
        int index = drawing.getShapes().indexOf(shape);
        if (index >= 0) {
            drawing.removeShape(index);
        }
        drawing.removeShape(index);
    }
}
