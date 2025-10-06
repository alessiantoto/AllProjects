package g62098.model;

/**
 * Direction représente une direction dans la grille de jeu Qwirkle.
 */
public enum Direction{
    RIGHT(0, 1),
    LEFT(0,-1),
    UP(-1,0),
    DOWN(1,0);
    private int deltaRow;
    private int deltaCol;

    /**
     Crée une nouvelle direction avec le delta de lignes et le delta de colonnes donnés.
     @param deltaRow le delta de lignes
     @param deltaCol le delta de colonnes
     */
    Direction(int deltaRow, int deltaCol) {
        this.deltaRow = deltaRow;
        this.deltaCol = deltaCol;
    }

    /**
     * Retourne la direction opposée.
     * @return la direction oppose
     */
    public Direction opposite() {
        return switch (this) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
    }

    /**
     *Retourne le delta de lignes de cette direction.
     *@return le delta de lignes
     */
    public int getDeltaRow() {
        return deltaRow;
    }
    /**
     *Retourne le delta de colonnes de cette direction.
     *@return le delta de colonnes
     */
    public int getDeltaCol() {
        return deltaCol;
    }


}
