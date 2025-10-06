package model;


/**
 * L'interface Shape définit les méthodes nécessaires pour représenter une forme géométrique dans un dessin ASCII.
 */
public interface Shape {

    /**
     * Vérifie si un point donné est à l'intérieur de la forme géométrique.
     *
     * @param p Le point à vérifier.
     * @return true si le point est à l'intérieur de la forme, sinon false.
     */
    boolean isInside(Point p);

    /**
     * Déplace la forme géométrique
     *
     * @param dx La quantité de déplacement en direction horizontale (x).
     * @param dy La quantité de déplacement en direction verticale (y).
     */
    void move(double dx, double dy);

    /**
     * Obtient la couleur de la forme géométrique.
     *
     * @return La couleur de la forme (caractère ASCII représentant la couleur).
     */
    char getColor();

    /**
     * Creates and returns a copy of this shape.
     *
     * @return A new instance that is a copy of this shape.
     */
}
