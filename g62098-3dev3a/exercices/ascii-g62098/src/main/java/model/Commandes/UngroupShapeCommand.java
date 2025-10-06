package model.Commandes;

import model.Drawing;
import model.Group;
import model.Shape;

import java.util.ArrayList;
import java.util.List;
/**
 * La classe UngroupShapeCommand représente une commande pour dégrouper une forme de type groupe.
 * Elle stocke les informations nécessaires pour l'exécution et l'annulation de la commande.
 */

public class UngroupShapeCommand implements Command {
    private Drawing drawing;
    private int groupIndex;
    private List<Shape> groupShapes; // Stocker les formes du groupe

    /**
     * Crée une nouvelle instance de la commande de dégroupement.
     *
     * @param drawing    Le dessin dans lequel la forme est dégroupée.
     * @param groupIndex L'index de la forme de groupe à dégrouper.
     */
    public UngroupShapeCommand(Drawing drawing, int groupIndex) {
        this.drawing = drawing;
        this.groupIndex = groupIndex;
        this.groupShapes = new ArrayList<>(); // Initialisation de la liste de formes
    }

    /**
     * Exécute la commande de dégroupement en retirant la forme de groupe et en ajoutant les formes individuelles au dessin.
     */
    @Override
    public void execute() {
        Shape shapeAtIndex = drawing.getShapes().get(groupIndex);

        if (shapeAtIndex instanceof Group) {
            Group group = (Group) shapeAtIndex;
            groupShapes.addAll(group.getShapes()); // Stocker les formes du groupe

            // Dégroupement avant d'ajouter les formes individuelles
            drawing.ungroup(groupIndex);
        } else {
            System.out.println("La forme à l'index " + groupIndex + " n'est pas un groupe.");
        }
    }

    /**
     * Annule la commande de dégroupement en regroupant les formes qui ont été dégroupées.
     */
    @Override
    public void undo() {
        List<Integer> groupIndices = new ArrayList<>();

        for (Shape shape : groupShapes) {
            int index = drawing.getShapes().indexOf(shape);
            groupIndices.add(index);
        }

        // Regrouper les formes qui ont été dégroupées
        drawing.groupShapes(groupIndices);
    }

}
