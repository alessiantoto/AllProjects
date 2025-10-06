package model.Commandes;

import model.Drawing;
import model.Shape;
/**
 * La classe RemoveShapeCommand représente une commande pour supprimer une forme du dessin.
 * Elle stocke les informations nécessaires pour l'exécution et l'annulation de la commande.
 */
public class RemoveShapeCommand implements Command{
    private Drawing drawing;
    private Shape shape;
    /**
     * Crée une nouvelle instance de la commande de suppression de forme.
     *
     * @param drawing Le dessin duquel la forme est supprimée.
     * @param shape   La forme à supprimer.
     */

    public RemoveShapeCommand(Drawing drawing, Shape shape) {
        this.drawing = drawing;
        this.shape = shape;
    }

    /**
     * Exécute la commande de vbgggg
     *$wqsxxxxxxxxxxxxxxxxxxxxxxxpolen retirant la forme du dessin.
     */
    @Override
    public void execute() {
        int index = drawing.getShapes().indexOf(shape);
        if (index >= 0 && index <= drawing.getShapes().size()-1) {
            drawing.removeShape(index);
        }
    }

    /**
     * Annule la commande de suppression en réajoutant la forme au dessin.
     */
    @Override
    public void undo() {
        drawing.addShape(shape);
    }
}
