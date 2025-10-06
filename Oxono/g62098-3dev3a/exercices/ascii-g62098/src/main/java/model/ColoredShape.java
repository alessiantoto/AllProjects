package model;

/**
 * La classe abstraite ColoredShape représente une forme géométrique colorée dans un dessin ASCII.
 * Elle implémente l'interface Shape et définit la couleur de la forme.
 */
public abstract class ColoredShape implements Shape{
    private char color;

    /**
     * Crée une instance de forme géométrique colorée avec la couleur spécifiée.
     * @param color La couleur de la forme (caractère ASCII).
     */
    public ColoredShape(char color) {
        this.color = color;
    }


    /**
     * Obtient la couleur de la forme géométrique.
     * @return La couleur de la forme (caractère ASCII représentant la couleur).
     */
    public char getColor() {
        return color;
    }


    /**
     * Définit la couleur de la forme géométrique.
     * @param color La nouvelle couleur de la forme (caractère ASCII).
     */
    public void setColor(char color) {
        this.color = color;
    }

}
