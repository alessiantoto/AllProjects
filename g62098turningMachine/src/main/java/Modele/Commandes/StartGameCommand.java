package Modele.Commandes;

import Modele.Game;
import Modele.Problemes.Probleme;

public class StartGameCommand implements Command {
    private Game game;
    private Probleme probleme;

    public StartGameCommand(Game game, Probleme probleme) {
        this.game = game;
        this.probleme = probleme;
    }

    @Override
    public void execute() {
        // Logic to start a new game with the specified problem
        game.startGame(probleme);
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("On peut pas faire undo sur start game");
    }
}
