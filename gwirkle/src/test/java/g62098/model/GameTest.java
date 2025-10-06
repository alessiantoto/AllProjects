package g62098.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void top() {
        Grid grid = new Grid(); // Créez une instance de la classe Grid
        List<String> names = new ArrayList<>(2);
        names.add("Dorian");
        names.add("Popa");

        Game game = new Game(names);
        // Ajouter les tuiles souhaitées pour le joueur 1
        List<Tile> hand1 = new ArrayList<>(game.getCurrentPlayerHand());
        hand1.add(new Tile(Color.RED, Shape.ROUND));
        hand1.add(new Tile(Color.RED, Shape.SQUARE));
        hand1.add(new Tile(Color.RED, Shape.DIAMOND));
        hand1.add(new Tile(Color.RED, Shape.CROSS));
        hand1.add(new Tile(Color.RED, Shape.PLUS));
        hand1.add(new Tile(Color.BLUE, Shape.PLUS));

        // Changer de joueur pour ajouter les tuiles souhaitées pour le joueur 2
        game.pass();

        List<Tile> hand2 = new ArrayList<>(game.getCurrentPlayerHand());
        hand2.add(new Tile(Color.YELLOW, Shape.ROUND));
        hand2.add(new Tile(Color.YELLOW, Shape.SQUARE));
        hand2.add(new Tile(Color.YELLOW, Shape.DIAMOND));
        hand2.add(new Tile(Color.YELLOW, Shape.CROSS));
        hand2.add(new Tile(Color.YELLOW, Shape.PLUS));
        hand2.add(new Tile(Color.GREEN, Shape.PLUS));

    }
}