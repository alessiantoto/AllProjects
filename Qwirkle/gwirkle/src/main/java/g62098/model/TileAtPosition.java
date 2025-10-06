package g62098.model;

import java.io.Serializable;

/**
 *Cette classe représente une tuile positionnée sur la grille de jeu à une position spécifique.
 *Elle contient les informations de la tuile et de sa position sur la grille.
 */
public record TileAtPosition(int row, int col, Tile tile) implements Serializable {
}
