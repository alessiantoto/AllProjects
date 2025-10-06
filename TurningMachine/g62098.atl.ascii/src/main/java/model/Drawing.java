package model;

import model.Commandes.AddShapeCommand;
import model.Commandes.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * La classe Drawing représente un dessin composé de différentes formes géométriques.
 * Elle permet de créer un dessin, d'ajouter des formes et de rechercher une forme à une position donnée.
 */

public class Drawing {
    private List<Shape> shapes;
    private int height;
    private int width;

    /**
     * Crée un dessin avec la largeur et la hauteur spécifiées.
     *
     * @param width  La largeur du dessin en caractères.
     * @param height La hauteur du dessin en caractères.
     */

    public Drawing(int width, int height) {
        this.width = width;
        this.height = height;
        this.shapes = new ArrayList<>();
    }
    /**
     * Ajoute une forme au dessin.
     *
     * @param shape La forme à ajouter.
     */
    public void addShape(Shape shape) {
        // Save the current state before making changes
        shapes.add(shape);
    }

    /**
     * Groupe des formes spécifiées dans un nouveau groupe et ajoute le groupe au dessin.
     *
     * @param shapeIndices Liste d'indices des formes à grouper.
     */
    public void groupShapes(List<Integer> shapeIndices) {

        if (shapeIndices.size() < 2) {
          System.out.println("Vous devez spécifier au moins deux formes à grouper.");
          return;
        }

        List<Shape> shapesToGroup = new ArrayList<>();
        char groupColor = ' ';  // Couleur initiale du groupe

        for (int shapeIndex : shapeIndices) {
            if (shapeIndex >= 0 && shapeIndex < shapes.size()) {
                shapesToGroup.add(shapes.get(shapeIndex));
                // La couleur du groupe est la couleur de la première forme ajoutée
                if (shapesToGroup.size() == 1) {
                    groupColor = shapes.get(shapeIndex).getColor();
                }
            } else {
                System.out.println("L'index de forme " + shapeIndex + " est invalide.");
            }
        }

        // Créer le groupe
        Group group = new Group(shapesToGroup, groupColor);

        // Supprimer les formes individuelles du dessin
        for (int i = shapeIndices.size() - 1; i >= 0; i--) {
            int shapeIndex = shapeIndices.get(i);
            if (shapeIndex >= 0 && shapeIndex < shapes.size()) {
                removeShape(shapeIndex);
            }
        }

        // Ajouter le groupe à la liste des formes
        addShape(group);
    }

    /**
     * Dégroupe le groupe spécifié et réintègre les formes individuelles dans le dessin.
     *
     * @param groupIndex L'index du groupe à dégrouper.
     */
    public void ungroup(int groupIndex) {
        if (groupIndex >= 0 && groupIndex < shapes.size()) {

            // Récupérer le groupe à dégrouper
            Group group = (Group) shapes.get(groupIndex);

            // Récupérer les formes du groupe
            List<Shape> groupShapes = group.getShapes();

            // Supprimer le groupe de la liste des formes
            removeShape(groupIndex);

            // Réintégrer les formes individuelles dans la liste des formes
            for (Shape shape : groupShapes) {
                addShape(shape);
            }
        }
    }

    /**
     * Recherche et renvoie la première forme à la position spécifiée.
     *
     * @param p Le point à rechercher.
     * @return La forme trouvée à cette position, ou null si aucune forme n'a été trouvée.
     */
    public Shape getShapeAt(Point p) {
        for (Shape shape : shapes) {
            if (shape.isInside(p)) {
                return shape;
            }
        }
        return null; // Aucune forme trouvée à cette position.
    }
    /**
     * Supprime la forme à l'index spécifié dans la liste des formes.
     *
     * @param shapeIndex L'index de la forme à supprimer.
     */
    public void removeShape(int shapeIndex) {
        if (shapeIndex >= 0 && shapeIndex < shapes.size()) {
            shapes.remove(shapeIndex);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Shape> getShapes() {
        return shapes;
    }


}
