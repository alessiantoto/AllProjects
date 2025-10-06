package Modele.Commandes;

import Modele.Game;
import Modele.Problemes.Probleme;

public class CheckCodeCommand implements Command {
    private Game game;
    private Probleme probleme;
    private int code;

    public CheckCodeCommand(Game game, Probleme probleme, int code) {
        this.game = game;
        this.probleme = probleme;
        this.code = code;
    }

    @Override
    public void execute() {
        // Enregistrer l'état actuel avant de modifier dans checkCode
        game.checkCode(code, probleme);
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("La vérification du code n'a pas d'effet undo, car c'est une opération finale");
    }
}
