package Modele.Commandes;

import Modele.Game;

public class AbandonCommand implements Command {
    private Game game;

    public AbandonCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.abandon();
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("L'abandon du jeu n'a pas d'effet undo, car c'est une opération finale");
    }
}
