package g62098.App;
import g62098.model.*;
import g62098.view.View;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

import java.io.Serializable;
import java.util.regex.Pattern;

import static g62098.view.View.*;

/**
 * La classe App est la classe principale qui lance le jeu Qwirkle.
 */
public class App implements Serializable{
    private static List<String> names;
    private static Game game;
    private static Bag bag;
    /**
     * Point d'entrée principal du programme.
     */
    public static void main(String[] args) {

        try {
            //the initialization
            bag = Bag.getInstance();
            Scanner clavier = new Scanner(System.in);
            // Demande à l'utilisateur s'il souhaite restaurer une partie
            System.out.println("Voulez-vous restaurer une partie ? (o/n)");
            String restoreChoice = clavier.next();

            if (restoreChoice.equalsIgnoreCase("o")) {
                // Demande le nom du fichier de sauvegarde
                System.out.println("Entrez le nom du fichier de sauvegarde :");
                String fileName = clavier.next();

                // Restaure la partie à partir du fichier
                game = Game.getFromFile(fileName);
            } else {

                // Création du jeu en demandant le nombre de joueurs
                System.out.println("Combien de joueurs vont jouer?");
                if (!clavier.hasNextInt()) {
                    throw new QwirkleException("Veuillez saisir un nombre entier.");
                }
                int nbrJoueurs = clavier.nextInt();
                if(nbrJoueurs!=2){
                    throw new QwirkleException("Il peut avoir que 2 joueurs");
                }
                names = new ArrayList<>(nbrJoueurs);
                for (int i = 0; i < nbrJoueurs; i++) {
                    System.out.println("Entre le nom du joueur");
                    String nomJoueur = clavier.next();
                    names.add(nomJoueur);
                }
                game = new Game(names);
            }
                // Affiche le menu d'aide
                View.displayHelp();
                boolean continueGame = true;
                while (continueGame) {
                    display(game.getGrid());    // Affiche la grille de jeu
                    display(game.getCurrentPlayer());   //affiche la main du joueur
       //             if(game.getCurrentPlayerScore()<=0 || game.getCurrentPlayerScore()>=10) {
       //                 break;
       //             }

                    // Demande le choix du joueur
                    System.out.println("Faites votre choix : separateur (,) : ");

                    String choixPlayer = clavier.next();

                    // Separe les mots ou il y a "," entre
                    String[] choixPlayerDivise = choixPlayer.split(",");

                    // Prend la premiere lettre de la commande
                    String choixChar = choixPlayerDivise[0];

                    switch (choixChar) {
                        case "o":
                            //cree pattern pour verifier si choixPlayerIntO, a premier numero entre 0 et 90, le deuxieme aussi, et le troisieme entre 0 et 5

                            String pattern = "^o,(?:[0-9]|[1-8][0-9]|90),(?:[0-9]|[1-8][0-9]|90),(?:[0-5])$";
                            if (Pattern.matches(pattern, choixPlayer)) {

                                int[] choixPlayerIntO = new int[choixPlayerDivise.length - 1];
                                for (int i = 1; i <= choixPlayerIntO.length; i++) {
                                    choixPlayerIntO[i - 1] = Integer.parseInt(choixPlayerDivise[i]);
                                }

                                int rowO = choixPlayerIntO[0];
                                int colO = choixPlayerIntO[1];
                                int indexTuile = choixPlayerIntO[2];
                                game.play(rowO, colO, indexTuile);
                            }
                            else{
                                throw new QwirkleException("Le choix ne respecte pas le pattern");
                            }
                            break;
                        case "l":

                            pattern = "^l,(?:[0-9]|[1-8][0-9]|90),(?:[0-9]|[1-8][0-9]|90),(?:[udrl])(?:,[0-5])*$";
                            if (Pattern.matches(pattern, choixPlayer)) {
                                int rowL = Integer.parseInt(choixPlayerDivise[1]);
                                int colL = Integer.parseInt(choixPlayerDivise[2]);
                                char charDirection = choixPlayerDivise[3].charAt(0);
                                Direction directionL = getDirection(charDirection);
                                int[] indexes = new int[choixPlayerDivise.length - 4];

                                for (int i = 4; i < choixPlayerDivise.length; i++) {
                                    indexes[i - 4] = Integer.parseInt(choixPlayerDivise[i]);
                                }

                                // play the tiles
                                game.play(rowL, colL, directionL, indexes);
                                break;
                            }
                            else {
                                throw new QwirkleException("Le choix ne respecte pas le pattern");
                            }
                        case "m":
                            pattern = "^m,(?:[0-9]|[1-8][0-9]|90),(?:[0-9]|[1-8][0-9]|90),(?:[0-5])(?:,(?:[0-9]|[1-8][0-9]|90),(?:[0-9]|[1-8][0-9]|90),(?:[0-5]))*$";
                            if (Pattern.matches(pattern, choixPlayer)) {
                                int[] is = new int[choixPlayerDivise.length - 1];
                                for (int i = 1; i < choixPlayerDivise.length; i++) {
                                    is[i - 1] = Integer.parseInt(choixPlayerDivise[i]);
                                }
                                game.play(is);
                                break;
                            }
                            else{
                                throw new QwirkleException("Le choix ne respecte pas le pattern");
                            }
                        case "f":
                            pattern = "^f(?:,[0-5])*$";
                            if (Pattern.matches(pattern, choixPlayer)) {
                                int[] choixPlayerIntF = new int[choixPlayerDivise.length - 1];
                                for (int i = 1; i <= choixPlayerIntF.length; i++) {
                                    choixPlayerIntF[i - 1] = Integer.parseInt(choixPlayerDivise[i]);
                                    System.out.println(choixPlayerIntF[i - 1]); //verifie choix index
                                }
                                System.out.println("Dans quelle direction?");
                                char charDirectionF = clavier.next().charAt(0);
                                if (charDirectionF == 'u' || charDirectionF == 'd' || charDirectionF == 'l' || charDirectionF == 'r') {
                                    Direction direction = getDirection(charDirectionF);
                                    game.first(direction, choixPlayerIntF);
                                    break;
                                }
                                else {
                                    throw new QwirkleException("Cette direction n'existe pas");
                                }
                            }
                            else {
                                throw new QwirkleException("Le choix ne respecte pas le pattern");
                            }
                        case "p":
                            game.getCurrentPlayer().addToScore(-1);
                            game.pass();
                            game.getCurrentPlayer().addToScore(1);
                            break;
                        case "q":
                            continueGame = false;
                            break;
                        case "s":
                            // Prompt for the filename to save the game to
                            System.out.println("Entrez le nom du fichier de sauvegarde :");
                            String fileName = clavier.next();

                            // Save the game to the file
                            game.write(fileName);

                            System.out.println("La partie a été sauvegardée avec succès.");
                            break;
                        default:
                            System.out.println("Choix invalide, reesayez !");
                            break;
                    }
                    System.out.println();

                    try {

                        if(game.getCurrentPlayerScore()<=0 || game.getCurrentPlayerScore()>=10) {
                            break;
                        }
                        Game tempGame = game; // Copie temporaire de l'état du jeu

                        // Reste du code du jeu
                        if (tempGame.isOver()) {
                            continueGame = false;
                        }

                    } catch (QwirkleException e) {
                        // Ne rien faire en cas d'exception
                    }
                }
            int winnerIndex = 0;
            for (int i = 1; i < game.getPlayers().length; i++) {
                if (game.getPlayers()[i].getScore() > game.getPlayers()[winnerIndex].getScore()) {
                    winnerIndex = i;
                }
            }
            displayWinner(game.getPlayers(), winnerIndex);

            System.out.println();
            }catch(QwirkleException e){
                displayError(e.getMessage());
            }

    }
    


/**
 * Retourne la direction correspondant au caractère de direction spécifié.
 */
    public static Direction getDirection(char directionChar) {
        switch (Character.toLowerCase(directionChar)) {
            case 'u':
                return Direction.UP;
            case 'd':
                return Direction.DOWN;
            case 'r':
                return Direction.RIGHT;
            case 'l':
                return Direction.LEFT;
            default:
                throw new IllegalArgumentException("Invalid direction: " + directionChar);
        }
    }

}
