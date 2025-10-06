package model;

/**
 * La classe Square représente un carré coloré dans un dessin ASCII.
 * Elle hérite de la classe Rectangle et crée un carré en spécifiant le même côté pour la largeur et la hauteur.
 */
public class Square extends Rectangle{

    /**
     * Crée une instance de carré avec le coin supérieur gauche, la longueur du côté et la couleur spécifiés.
     *
     * @param upperLeft Le point représentant le coin supérieur gauche du carré.
     * @param side      La longueur du côté du carré.
     * @param color     La couleur du carré (caractère ASCII).
     */
    public Square(Point upperLeft, double side, char color) {
        super(upperLeft,side,side,color);
    }
}
