package Modele.Commandes;

import Modele.Game;
import Modele.Problemes.Probleme;
import Modele.Validateurs.Validateur;

public class ChooseValidateurCommand implements Command {
    private Game game;
    private Validateur validateur;
    private int code;
    private Probleme probleme;

    public ChooseValidateurCommand(Game game, Validateur validateur, int code, Probleme probleme) {
        this.game = game;
        this.validateur = validateur;
        this.code = code;
        this.probleme = probleme;
    }

    @Override
    public void execute() {
        // Logic to choose a validator
        game.chooseValidateur(validateur,code,probleme);
    }

    @Override
    public void undo() {
        game.undoChooseValidateur();
    }
}
