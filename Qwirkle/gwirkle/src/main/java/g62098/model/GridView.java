package g62098.model;

import java.io.Serializable;

/**
 GridView represente la vue de la grille de jeu Qwirkle. Elle contient des méthodes pour obtenir des informations sur la grille.
 */
public class GridView implements Serializable {
    private Grid grid;
    /**
     Constructeur de la classe GridView.
     @param grid La grille de jeu qu'on doit afficher.
     */
    public GridView(Grid grid) {
        this.grid = grid;
    }
    /**
     Retourne la tuile à la position spécifiée dans la grille.
     @param row La ligne de la grille où se trouve la tuile.
     @param col La colonne de la grille où se trouve la tuile.
     @return La tuile en cette position dans la grille.
     */
    public Tile get(int row, int col){
        return this.grid.get(row,col);
    }

    /**
     *
     * @return True si la grille est vide, False sinon.
     *
     */
    public boolean isEmpty() {
        return this.grid.isEmpty();
    }
}
