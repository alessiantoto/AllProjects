package model;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Group représente un groupe de formes géométriques.
 * Elle permet de créer un groupe en spécifiant une liste de formes et une couleur pour le groupe.
 */
public class Group implements Shape{
    private List<Shape> shapes;
    private char color;  // Ajout de la déclaration de la variable color

    /**
     * Crée un groupe avec la liste de formes spécifiée et la couleur spécifiée.
     *
     * @param shapes La liste des formes à inclure dans le groupe.
     * @param color  La couleur du groupe (caractère ASCII).
     */
    public Group(List<Shape> shapes, char color) {
        this.shapes = shapes;
        this.color = color;
    }

    /**
     * Vérifie si le point spécifié est à l'intérieur du groupe en vérifiant chaque forme.
     *
     * @param p Le point à vérifier.
     * @return true si le point est à l'intérieur du groupe, sinon false.
     */
    @Override
    public boolean isInside(Point p) {
        // Un groupe est considéré à l'intérieur si l'un de ses éléments est à l'intérieur
        for (Shape shape : shapes) {
            if (shape.isInside(p)) {
                return true;
            }
        }
        return false;
    }



    /**
     * Déplace chaque forme à l'intérieur du groupe en fonction des déplacements spécifiés.
     *
     * @param dx Le déplacement horizontal.
     * @param dy Le déplacement vertical.
     */
    @Override
    public void move(double dx, double dy) {
        // Déplace chaque forme à l'intérieur du groupe
        for (Shape shape : shapes) {
            shape.move(dx, dy);
        }
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public char getColor() {
        return color;
    }
}
