package model;

/**
 * La classe Point représente un point avec des coordonnées x et y.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Crée un nouveau point avec les coordonnées spécifiées.
     *
     * @param x Coordonnée X du point.
     * @param y Coordonnée Y du point.
     */
    public Point(double x, double y){
        this.x=x;
        this.y=y;
    }

    /**
     * Constructeur de copie qui crée un point identique à un autre point.
     *
     * @param p Le point à copier.
     */
    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Bouge le point
     *
     * @param dx La quantité de déplacement en direction horizontale (x).
     * @param dy La quantité de déplacement en direction verticale (y).
     */
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }


    /**
    * Calcule la distance entre ce point et un autre point spécifié.
    * @param other Le point à partir duquel la distance est calculée.
    * @return La distance entre ce point et le point spécifié.
    */
    public double distanceTo(Point other){
        double dx=this.x-other.x;
        double dy=this.y-other.y;
        return Math.sqrt(dx * dx + dy * dy);
        //calcule la distance  en utilisant le théorème de Pythagore pour calculer la longueur de l'hypoténuse du triangle formé par les différences en x et y.
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
