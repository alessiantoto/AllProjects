package model.Commandes;

import model.Shape;

/**
 * La classe MoveShapeCommand représente une commande pour déplacer une forme.
 * Elle stocke les informations nécessaires pour l'exécution et l'annulation du déplacement.
 */


public class MoveShapeCommand implements Command{
    private Shape shape;
    private double dx;
    private double dy;

    /**
     * Crée une nouvelle instance de la commande de déplacement de forme.
     *
     * @param shape La forme à déplacer.
     * @param dx    La quantité de déplacement en direction horizontale (x).
     * @param dy    La quantité de déplacement en direction verticale (y).
     */

    public MoveShapeCommand(Shape shape, double dx, double dy) {
        this.shape = shape;
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Exécute la commande de déplacement en déplaçant la forme.
     */
    @Override
    public void execute() {
        shape.move(dx, dy);
    }

    /**
     * Annule la commande de déplacement en déplaçant la forme dans la direction opposée.
     */
    @Override
    public void undo() {
        shape.move(-dx, -dy);
    }
}
