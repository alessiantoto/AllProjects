package Modele.Commandes;

import Modele.Game;

public class SubmitPlayerCodeCommand implements Command {
    private Game game;
    private int code;
    private int previousCode; // Store the previous state of the player code for undo

    public SubmitPlayerCodeCommand(Game game, int code) {
        this.game = game;
        this.code = code;
        this.previousCode = game.getCodeJoueur(); // Store the previous state for undo
    }

    @Override
    public void execute() {
        game.submitPlayerCode(code);
    }

    @Override
    public void undo() {
        game.submitPlayerCode(previousCode);
    }
}
