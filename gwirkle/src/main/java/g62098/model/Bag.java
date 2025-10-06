package g62098.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;import java.io.Serializable;


/**
 * Bag représente un sac de tuiles pour le jeu Qwirkle.
 * Le sac contient 108 tuiles, avec 6 couleurs différentes et 6 formes différentes.
 * Il y a 3 copies de chaque tuile dans le sac.
 */
public class Bag implements Serializable{
    /**
     Instance singleton de la classe Bag
     */
    private final static Bag instance=new Bag();

    /**
     Liste de tuiles contenues dans le sac
     */
    private List<Tile> tiles;

    /**
     Constructeur privé pour initialiser le sac avec les 108 tuiles.
     Le sac contient 3 copies de chaque tuile, 6 couleurs et 6 formes différentes.
     */

    private Bag() {
        tiles = new ArrayList<>();
        for (Color color : Color.values()) {
            for (Shape shape : Shape.values()) {
                for (int i = 0; i < 3; i++) {
                    tiles.add(new Tile(color, shape));
                }
            }
        }
        Collections.shuffle(tiles);
    }

    /**
     *
     * @return l'instance singleton de la classe Bag
     */
    public static Bag getInstance() {
        return instance;
    }

    /**
     * Retourne une liste de tuiles prises aléatoirement dans le sac.
     * Si le sac est vide, la méthode retourne null.
     * Si le nombre de tuiles demandé est supérieur au nombre de tuiles restantes dans le sac, la méthode retourne toutes les tuiles restantes.
     * @param n nombre des tuiles que le joueur doit prendre du sac
     * @return la/les nouvelle/s tuile/s que le joueur a pris du sac
     */
    public List<Tile> getRandomTiles(int n) {
        if (tiles.isEmpty()) {
            return null;
        }
        if (tiles.size()<n){
            List<Tile> foo;
            foo = new ArrayList<Tile>();
            while (!tiles.isEmpty()) {
                foo.add(tiles.remove(0));
            }
            return foo;
        }
        int i=0;
        List<Tile> foo;
        foo = new ArrayList<Tile>();
         while(i<n) {
            foo.add(tiles.remove(0));
            i++;
        }
        return foo;
    }
    /**
     Retourne le nombre de tuiles
     @return le nombre de tuiles
     */
    public int size() {
        return tiles.size();
    }

}

