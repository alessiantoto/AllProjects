package model.Commandes;

import model.Drawing;

import java.util.List;

/**
 * La classe GroupShapeCommand représente une commande pour regrouper des formes dans un dessin.
 * Elle stocke les informations nécessaires pour l'exécution et l'annulation du regroupement.
 */
public class GroupShapeCommand implements Command{
    private Drawing drawing;
    private List<Integer> shapeIndices;

    /**
     * Crée une nouvelle instance de la commande de regroupement de formes.
     *
     * @param drawing      Le dessin dans lequel les formes seront regroupées.
     * @param shapeIndices Les indices des formes à regrouper.
     */
    public GroupShapeCommand(Drawing drawing, List<Integer> shapeIndices) {
        this.drawing = drawing;
        this.shapeIndices = shapeIndices;
    }

    /**
     * Exécute la commande de regroupement en créant un groupe avec les formes spécifiées.
     */
    @Override
    public void execute() {
        drawing.groupShapes(shapeIndices);
    }

    /**
     * Annule la commande de regroupement en dégroupant le dernier groupe ajouté.
     */
    @Override
    public void undo() {
        int groupIndex = drawing.getShapes().size() - 1; // Index du dernier groupe ajouté
        drawing.ungroup(groupIndex);
    }
}
