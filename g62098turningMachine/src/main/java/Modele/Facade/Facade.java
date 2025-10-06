package Modele.Facade;

import Modele.Commandes.*;
import Modele.Problemes.Probleme;
import Modele.Problemes.ProblemeCSVLoader;
import Modele.Validateurs.Factory;
import Modele.Validateurs.Validateur;

import java.util.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
/**
 * La classe FacadeJeu est une interface simplifiée pour interagir avec le jeu de la Machine de Turing.
 */
public interface Facade {
    void startGame(Probleme probleme);
    void submitPlayerCode(int code);
    void chooseValidator(Validateur validateur, int code, Probleme probleme);
    void nextRound();
    boolean guessCode(int code, Probleme probleme);
    void abandon();
    void addObserver(Observer observer);
    Probleme loadSpecificLine(int numProbleme);
    int totalProblemes();
    void undo();
    void redo();
    Probleme getProblemeActuel();
    int getScore();
    Factory getListeValidateurs();
    List<Validateur> getValidateursVerifies();
    int getPlayerCode();
}

