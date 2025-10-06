package g62098.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
/**
 La classe Player représente un joueur dans le jeu Qwirkle.
 */
public class Player implements Serializable {
    private static final int MAX_TILES = 6;
    private String name;
    private List<Tile> hand;
    private int score;
    /**
     Constructeur de la classe Player.
     @param name le nom du joueur
     */
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
        hand = Bag.getInstance().getRandomTiles(6); // Ajouter les tuiles générées aléatoirement à la main du joueur
        score = 5; // Initialisation du score à zéro

    }


    /**
     Récupère la main du joueur.
     @return une version non modifiable de la liste de tuiles en main du joueur
     */
    public List<Tile> getHand() {
        return Collections.unmodifiableList(hand); // retourne une version non modifiable de la liste
    }

    /**
     *
     * @return le nom du joueur
     */
    public String getName() {
        return name;
    }


    public int getScore() {
        return score;
    }

    public void addToScore(int points) {
        score += points; // Méthode pour ajouter des points au score
    }

    /**
     Ajoute des tuiles à la main du joueur jusqu'à MAX_TILES.
     */
    public void refill(){
        int numTilesToAdd = MAX_TILES - hand.size();
        //System.out.println("Size de hand "+hand.size());
        if (numTilesToAdd > 0) {
            hand.addAll(Bag.getInstance().getRandomTiles(numTilesToAdd));
        }
    }

    /**
     Retire des tuiles de la main du joueur.
     @param ts les tuiles à retirer de la main du joueur
     */
    public void remove(Tile...ts){
        for (Tile t : ts) {
            hand.remove(t);
        }
    }


}


