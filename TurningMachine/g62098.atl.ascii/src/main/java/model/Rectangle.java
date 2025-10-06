package model;

/**
 * La classe Rectangle représente un rectangle coloré dans un dessin ASCII.
 * Elle hérite de la classe ColoredShape et définit les propriétés spécifiques au rectangle.
 */
public class Rectangle extends ColoredShape {
    private Point upperLeft;
    double width;
    private double height;


    /**
     * Crée une instance de rectangle avec le coin supérieur gauche, la largeur, la hauteur et la couleur spécifiés avec super.
     *
     * @param upperLeft Le point représentant le coin supérieur gauche du rectangle.
     * @param width     La largeur du rectangle.
     * @param height    La hauteur du rectangle.
     * @param color     La couleur du rectangle (caractère ASCII).
     */

    public Rectangle(Point upperLeft, double width, double height, char color) {
        super(color);
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }


    /**
     * Vérifie si un point donné est à l'intérieur du rectangle.
     *
     * @param p Le point à vérifier.
     * @return true si le point est à l'intérieur du rectangle, sinon false.
     */
    @Override
    public boolean isInside(Point p) {
        if (upperLeft == null) {
            // Gérer le cas où upperLeft est null, par exemple en renvoyant false
            return false;
        }

        // Vérification si le point p est à l'intérieur du rectangle en fonction des coordonnées du coin supérieur gauche (upperLeft), de la largeur (width) et de la hauteur (height).
        double x1 = upperLeft.getX();
        double y1 = upperLeft.getY();
        double x2 = x1 + width;
        double y2 = y1 - height;



        double px = p.getX();
        double py = p.getY();


        // Utilisation directe des coordonnées du coin inférieur droit
        return px >= x1 && px <= x2 && py <= y1 && py >= y2;
        // Verifie si le point est entre upperLeft et bottomRight
    }

    /**
     * Deplace le rectangle
     *
     * @param dx La quantité de déplacement en direction horizontale (x).
     * @param dy La quantité de déplacement en direction verticale (y).
     */
    @Override
    public void move(double dx, double dy) {
        // Déplacer la forme (rectangle) en mettant à jour les coordonnées du coin supérieur gauche.
        upperLeft.move(upperLeft.getX() + dx, upperLeft.getY() + dy);
    }
    // Le reste du code reste inchangé

    public Point getUpperLeft() {
        return upperLeft;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
