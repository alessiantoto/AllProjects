package Controleur;

import Modele.Facade.Facade;
import Modele.Problemes.Probleme;
import Modele.Validateurs.Factory;
import Modele.Validateurs.Validateur;
import Vue.ConsoleVue;

import java.util.*;

/**
 * Le contrôleur de l'interface console qui gère les interactions entre l'utilisateur et le modèle.
 */
public class ControleurConsole {

    private Facade facade;
    private Factory factory;
    private ConsoleVue vueConsole;

    public ControleurConsole(Facade facade, Factory factory) {
        this.facade = facade;
        this.factory = factory;
        this.vueConsole = new ConsoleVue();
    }

    public void start() {
        while (true) {
            // Choisir un problème
            String choisirProbleme = vueConsole.demanderChoixProbleme();

            switch (choisirProbleme) {
                case "OUI":
                    vueConsole.afficherListeProblemes(facade.totalProblemes());
                    int numeroProblemeChoix = vueConsole.demanderNumeroProbleme(facade.totalProblemes());
                    facade.startGame(facade.loadSpecificLine(numeroProblemeChoix));
                    break;

                case "NON":
                    int numeroProblemeAleatoire = new Random().nextInt(facade.totalProblemes()) + 1;
                    facade.startGame(facade.loadSpecificLine(numeroProblemeAleatoire));
                    break;

                default:
                    vueConsole.afficherMessage("Choix invalide, veuillez entrer 'OUI' ou 'NON'.");
                    continue;
            }

            vueConsole.afficherMenu();
            while (true) {
                // Afficher le menu et gérer les options
                Probleme problemeEnCours = facade.getProblemeActuel();
                List<Validateur> validateursProbleme = problemeEnCours.getValidateursAssocies();

                //Associer chaque validateur à son numéro en utilisant la Factory
                Map<Integer, Validateur> validateurMap = new HashMap<>();
                for (Validateur validateur : validateursProbleme) {
                    int indice = factory.getIndiceByValidator(validateur);
                    if (indice != -1) {
                        validateurMap.put(indice, validateur);
                    }
                }

                // Passer la Map de validateurs à la vue

                vueConsole.afficherValidateursProbleme(validateurMap);
                vueConsole.afficherScore(facade.getScore());

                int choix = vueConsole.demanderChoixJeu();

                switch (choix) {
                    case 1:
                        int code = vueConsole.demanderCode();
                        facade.submitPlayerCode(code);
                        break;

                    case 2:
                        gererChoixValidateur(validateurMap);
                        break;

                    case 3:
                        facade.nextRound();
                        vueConsole.afficherMessage("!! - Passage à la manche suivante - !!");
                        break;

                    case 4:
                        int essaiDeviner = vueConsole.demanderDevinerCode();
                        boolean resultat = facade.guessCode(essaiDeviner, facade.getProblemeActuel());
                        vueConsole.afficherMessage(resultat ? "Vous avez gagné!" : "Vous avez perdu!");
                        if (resultat) System.exit(0);
                        break;

                    case 5:
                        facade.undo();
                        break;

                    case 6:
                        facade.redo();
                        break;

                    case 7:
                        facade.abandon();
                        break;

                    default:
                        vueConsole.afficherMessage("Choix invalide");
                }
            }
        }
    }

    private void gererChoixValidateur(Map<Integer, Validateur> validateurMap) {
        if (facade.getPlayerCode() == 0) {
            vueConsole.afficherMessage("Veuillez entrer un code avant de choisir un validateur.");
        } else {
            int numeroIndiceValidateur = vueConsole.demanderNumeroValidateur();
            Validateur validateurChoisi = validateurMap.get(numeroIndiceValidateur);

            if (validateurChoisi != null) {
                facade.chooseValidator(validateurChoisi, facade.getPlayerCode(), facade.getProblemeActuel());

                // Recharger les validateurs pour obtenir les statuts actualisés
                Probleme problemeEnCours = facade.getProblemeActuel();
                List<Validateur> validateursProbleme = problemeEnCours.getValidateursAssocies();
                Map<Integer, Validateur> updatedValidateurMap = new HashMap<>();
                for (Validateur validateur : validateursProbleme) {
                    int indice = factory.getIndiceByValidator(validateur);
                    if (indice != -1) {
                        updatedValidateurMap.put(indice, validateur);
                    }
                }
                vueConsole.afficherValidateursProbleme(updatedValidateurMap);
                vueConsole.afficherMessage("Le validateur que vous avez choisi est : " + validateurChoisi.getDescription() + " - " +
                        (validateurChoisi.validationStatus() ? "Valide" : "Non valide"));
            } else {
                vueConsole.afficherMessage("L'indice choisi ne correspond à aucun validateur du problème.");
            }
        }
    }
}

