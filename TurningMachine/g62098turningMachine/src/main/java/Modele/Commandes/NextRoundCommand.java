package Modele.Commandes;

import Modele.Game;
import Modele.Validateurs.Validateur;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NextRoundCommand implements Command {
    private Game game;

    // Variables pour stocker l'état avant l'exécution de la commande
    private int ancienCodeJoueur;
    private List<Validateur> anciensValidateursChoisis;
    private int ancienScore;
    private Map<Validateur, Boolean> previousValidatorStates;

    public NextRoundCommand(Game game) {
        this.game = game;
    }
    @Override
    public void execute() {
        // Sauvegarder l'état actuel avant d'exécuter la commande
        this.ancienCodeJoueur = game.getCodeJoueur();
        this.anciensValidateursChoisis = new ArrayList<>(game.getValidateursVerifies());
        this.ancienScore = game.getScore();
        this.previousValidatorStates = game.saveValidatorStates(anciensValidateursChoisis);

        // Exécuter la méthode nextRound pour avancer à la prochaine manche
        game.nextRound();
    }

    @Override
    public void undo() {
        // Restaurer l'état précédent du jeu lors de l'annulation (undo)
        game.setCodeJoueur(ancienCodeJoueur);
        game.setValidateursVerifies(anciensValidateursChoisis);
        game.setScore(ancienScore);

        // Restaurer l'état des validateurs
        game.restoreValidatorStates(previousValidatorStates);
    }
}
