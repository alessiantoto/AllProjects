package model;
import java.util.Stack;
/**
 * La classe Circle représente un cercle coloré dans un dessin ASCII.
 * Elle hérite de la classe ColoredShape et définit les propriétés spécifiques au cercle.
 */
public class Circle extends ColoredShape{
    private Point center;
    private double radius;
    private Shape previousState;  // Ajout d'une pile pour l'historique d'état

    /**
     * Crée une instance de cercle avec le centre, le rayon et la couleur spécifiés en utilisant super.
     *
     * @param center Le point représentant le centre du cercle.
     * @param radius Le rayon du cercle.
     * @param color  La couleur du cercle (caractère ASCII).
     */
    public Circle(Point center, double radius, char color){
        super(color);
        this.center = center;
        this.radius = radius;
    }

    /**
     * Vérifie si un point donné est à l'intérieur du cercle.
     *
     * @param p Le point à vérifier.
     * @return true si le point est à l'intérieur du cercle, sinon false.
     */
    @Override
    public boolean isInside(Point p) {
        double distance = center.distanceTo(p); // Utilisation de la méthode distanceTo pour calculer la distance entre le point p et le centre du cercle.
        return distance <= radius; // Le point p est à l'intérieur du cercle si sa distance au centre est inférieure ou égale au rayon.
    }

    /**
     * Déplace le cercle en déplaçant son centre
     *
     * @param dx La quantité de déplacement en direction horizontale (x).
     * @param dy La quantité de déplacement en direction verticale (y).
     */
    @Override
    public void move(double dx, double dy) {
        // Sauvegarde de l'état avant le déplacement
        center.move(dx,dy);
    }

    /**
     * Obtient la position du centre du cercle.
     *
     * @return Le point représentant le centre du cercle.
     */
    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
}
