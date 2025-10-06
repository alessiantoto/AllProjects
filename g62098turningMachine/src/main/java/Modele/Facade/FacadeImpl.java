package Modele.Facade;

import Modele.Commandes.*;
import Modele.Facade.Facade;
import Modele.Game;
import Modele.Problemes.Probleme;
import Modele.Problemes.ProblemeCSVLoader;
import Modele.Validateurs.Factory;
import Modele.Validateurs.Validateur;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * La classe {@code FacadeImpl} implémente l'interface {@code Facade} et sert de façade pour la gestion du jeu.
 * Elle agit comme un intermédiaire entre la couche de présentation et le modèle du jeu, encapsulant les interactions avec
 * la logique du jeu et fournissant des méthodes pour les opérations du jeu. Elle est également observable, ce qui permet
 * de notifier les observateurs (comme les vues) des changements dans le jeu.
 */

public class FacadeImpl extends Observable implements Facade {
    private Game game;
    private ProblemeCSVLoader problemeCSVLoader;
    private Factory factory;
    private List<Validateur> validateursVerifies;

    /**
     * Constructeur de la classe {@code FacadeImpl}.
     * Initialise les composants nécessaires pour la gestion du jeu, y compris la création d'une instance de {@code Game},
     * {@code ProblemeCSVLoader} et la liste des validateurs vérifiés.
     *
     * @param factory La fabrique de validateurs utilisée pour créer des instances de validateurs.
     */

    public FacadeImpl(Factory factory) {
        this.game = new Game();
        this.problemeCSVLoader = new ProblemeCSVLoader(factory);
        this.factory = factory;
        this.validateursVerifies = new ArrayList<>();
    }

    /**
     * Démarre une nouvelle partie avec le problème spécifié.
     *
     * @param probleme Le problème à résoudre dans la nouvelle partie.
     */
    @Override
    public void startGame(Probleme probleme) {
        game.executeCommand(new StartGameCommand(game, probleme));
        notifyObservers(); // Notifie les observateurs que le jeu a commencé
    }

    /**
     * Soumet un code du joueur pour vérification.
     *
     * @param code Le code soumis par le joueur.
     */
    @Override
    public void submitPlayerCode(int code) {
        game.executeCommand(new SubmitPlayerCodeCommand(game, code));
        notifyObservers(); // Notifie les observateurs que le code a été soumis
    }

    /**
     * Choisit un validateur pour le problème actuel.
     *
     * @param validateur Le validateur à ajouter.
     * @param code Le code associé au validateur.
     * @param probleme Le problème en cours.
     */
    @Override
    public void chooseValidator(Validateur validateur, int code, Probleme probleme) {
        ChooseValidateurCommand command = new ChooseValidateurCommand(game, validateur, code, probleme);
        game.executeCommand(command);
        notifyObservers(); // Notifie les observateurs que le validateur a été choisi
    }

    /**
     * Passe à la prochaine manche du jeu.
     */
    @Override
    public void nextRound() {
        game.executeCommand(new NextRoundCommand(game));
        notifyObservers(); // Notifie les observateurs que le jeu passe à la prochaine manche
    }

    /**
     * Devine le code pour le problème actuel et vérifie la validité.
     *
     * @param code Le code à vérifier.
     * @param probleme Le problème en cours.
     * @return true si le code est correct, false sinon.
     */
    @Override
    public boolean guessCode(int code, Probleme probleme) {
        boolean result = game.checkCode(code, probleme);
        game.executeCommand(new CheckCodeCommand(game, probleme, code));
        notifyObservers(); // Notifie les observateurs du résultat de la vérification du code
        return result;
    }

    /**
     * Abandonne la partie en cours.
     */
    @Override
    public void abandon() {
        game.executeCommand(new AbandonCommand(game));
        notifyObservers(); // Notifie les observateurs que le joueur a abandonné
    }


    /**
     * Charge un problème spécifique depuis le fichier CSV.
     *
     * @param numProbleme Le numéro du problème à charger.
     * @return Le problème chargé.
     */
    @Override
    public Probleme loadSpecificLine(int numProbleme) {
        return problemeCSVLoader.loadSpecificLine(numProbleme);
    }

    /**
     * Obtient le nombre total de problèmes disponibles.
     *
     * @return Le nombre total de problèmes.
     */
    @Override
    public int totalProblemes() {
        return problemeCSVLoader.countTotalProblemes();
    }

    /**
     * Annule la dernière action effectuée.
     */
    @Override
    public void undo() {
        game.undo();
        notifyObservers(); // Notifie les observateurs que l'action a été annulée
    }

    /**
     * Refait la dernière action annulée.
     */
    @Override
    public void redo() {
        game.redo();
        notifyObservers(); // Notifie les observateurs que l'action a été rétablie
    }

    /**
     * Obtient le problème actuellement en cours.
     *
     * @return Le problème actuel.
     */
    @Override
    public Probleme getProblemeActuel() {
        return game.getProblemeActuel();
    }

    @Override
    public int getScore() {
        return game.getScore();
    }

    @Override
    public Factory getListeValidateurs() {
        return factory;
    }

    @Override
    public List<Validateur> getValidateursVerifies() {
        return game.getValidateursVerifies();
    }

    @Override
    public int getPlayerCode() {
        return game.getCodeJoueur();
    }
}
