package Vue;

import Modele.Facade.Facade;
import Modele.Validateurs.Validateur;

import java.util.*;

/**
 * La classe ConsoleVue affiche les informations du jeu dans la console et implémente l'interface Observer
 * pour être notifiée des changements d'état dans la façade du jeu.
 */
public class ConsoleVue implements Observer {
    private Scanner scanner;

    /**
     * Constructeur de la classe ConsoleVue.
     *
     */
    public ConsoleVue() {
        this.scanner = new Scanner(System.in);
    }


    /**
     * Affiche la liste des problèmes avec leurs informations associées.
     *
     * @param totalProblemes Le nombre total de problèmes disponibles.
     */
    // Affiche la liste des problèmes disponibles
    public void afficherListeProblemes(int totalProblemes) {
        afficherMessage("Total des problèmes disponibles : " + totalProblemes);
    }

    /**
     * Affiche les validateurs associés au problème en cours.
     *
     */
    // Accepter une Map pour afficher les validateurs avec leur numéro
    public void afficherValidateursProbleme(Map<Integer, Validateur> validateurMap) {
        System.out.println("Validateurs disponibles pour ce problème :");
        for (Map.Entry<Integer, Validateur> entry : validateurMap.entrySet()) {
            Integer numero = entry.getKey();
            Validateur validateur = entry.getValue();
            System.out.println(numero + ". " +  validateur.getDescription());
        }
    }



    /**
     * Affiche le score actuel du joueur.
     *
     * @param score Le score du joueur.
     */
    public void afficherScore(int score) {
        System.out.println("Votre score est de: " + score);
        System.out.println("--------------------------");
    }


    /**
     * Affiche le menu principal du jeu.
     */
    public void afficherMenu(){
        System.out.println("-------- MENU --------");
        // Afficher le menu et obtenir l'entrée de l'utilisateur
        System.out.println("Entrez votre choix: ");
        System.out.println("1. Entrer un code");
        System.out.println("2. Choisir un validateur");
        System.out.println("3. Passer à la manche suivante");
        System.out.println("4. Deviner un code");
        System.out.println("5. Undo");
        System.out.println("6. Redo");
        System.out.println("7. Abandonner");
    }

    /**
     * Affiche un message dans la console.
     *
     * @param message Le message à afficher.
     */
    public void afficherMessage(String message) {
        System.out.println(message);
    }


    /**
     * Méthode de l'interface Observer appelée lorsqu'il y a un changement d'état dans la façade du jeu.
     *
     * @param o   L'objet observable (facade) qui a changé.
     * @param arg L'argument passé lors de la notification (plus pour JavaFx).
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Facade) {
            Facade updatedFacade = (Facade) o;

            // Utilisez updatedFacadeJeu pour obtenir des informations ou mettre à jour l'interface utilisateur
            afficherScore(updatedFacade.getScore());
        }
    }

    // Capture la saisie utilisateur pour choisir un problème
    public String demanderChoixProbleme() {
        afficherMessage("Vous voulez choisir un problème?");
        return scanner.nextLine().toUpperCase();
    }

    // Capture la saisie utilisateur pour choisir un numéro de problème
    public int demanderNumeroProbleme(int totalProblemes) {
        int numeroProblemeChoix = -1;
        while (true) {
            try {
                afficherMessage("Veuillez entrer un numéro de problème valide (entre 1 et " + totalProblemes + "): ");
                String input = scanner.nextLine();
                if (!input.matches("\\d+")) {
                    afficherMessage("Veuillez entrer un numéro de problème valide.");
                    continue;
                }
                numeroProblemeChoix = Integer.parseInt(input);
                if (numeroProblemeChoix < 1 || numeroProblemeChoix > totalProblemes) {
                    afficherMessage("Numéro de problème non valide. Veuillez réessayer.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                afficherMessage("Veuillez entrer un numéro de problème valide.");
            }
        }
        return numeroProblemeChoix;
    }

    // Capture la saisie utilisateur pour choisir un numéro de validateur
    public int demanderNumeroValidateur() {
        afficherMessage("Choisissez un numéro de validateur :");
        return scanner.nextInt();
    }

    public int demanderChoixJeu() {
        afficherMessage("Faites votre choix de jeu :");
        return scanner.nextInt();
    }

    // Capture la saisie utilisateur pour soumettre un code
    public int demanderCode() {
        afficherMessage("Entrez un code de 3 chiffres (chaque chiffre entre 1 et 5): ");
        int code = scanner.nextInt();
        return code;
    }


    // Capture la saisie utilisateur pour deviner le code
    public int demanderDevinerCode() {
        afficherMessage("Devinez le code: ");
        return scanner.nextInt();
    }


}
