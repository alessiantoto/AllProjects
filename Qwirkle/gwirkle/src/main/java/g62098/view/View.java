package g62098.view;
import g62098.model.*;

import java.io.Serializable;


/**
 * La classe View gère l'affichage du jeu Qwirkle.
         */
public class View implements Serializable {
    /**
     * Couleurs de texte prédéfinies pour l'affichage.
     */
    public static final String defaultColor = "\u001B[0m";
    public static final String blackColor = "\u001B[30m";
    public static final String redColor = "\u001B[31m";
    public static final String greenColor = "\u001B[32m";
    public static final String yellowColor = "\u001B[33m";
    public static final String blueColor = "\u001B[34m";
    public static final String purpleColor = "\u001B[35m";
    public static final String cyanColor = "\u001B[36m";

    /**
     * Affiche la grille de jeu.
     *
     * @param grid La grille de jeu à afficher.
     */
    public static void display(GridView grid) {

        // determine the minimum and maximum row and column indices
        int minRow = 91;
        int minCol = 91;
        int maxRow = 0;
        int maxCol = 0;
        for (int row = 0; row < 91; row++) {
            for (int col = 0; col < 91; col++) {
                if (grid.get(row, col) != null) {
                    minRow = Math.min(minRow, row);
                    minCol = Math.min(minCol, col);
                    maxRow = Math.max(maxRow, row);
                    maxCol = Math.max(maxCol, col);
                }
            }
        }

        // print the grid
        for (int row = minRow; row <= maxRow; row++) {
            System.out.print(row);
            for (int col = minCol; col <= maxCol; col++) {
                Tile tile = grid.get(row, col);
                if (tile != null) {
                    String color = getColor(tile);
                    System.out.print(color + defaultColor + " ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        for (int col = minCol; col <= maxCol; col++) {
            System.out.print(" "+col);
        }
        System.out.println();
    }

    /**
     * Retourne la couleur formatée d'une tuile spécifique.
     *
     * @param tile La tuile pour laquelle obtenir la couleur.
     * @return La couleur formatée de la tuile.
     */

    private static String getColor(Tile tile) {
        String couleur = defaultColor;
        switch (tile.color()) {
            case GREEN -> {
                couleur = greenColor;
            }
            case YELLOW -> {
                couleur = yellowColor;
            }
            case PURPLE -> {
                couleur = purpleColor;
            }
            case ORANGE -> {
                couleur = cyanColor;
            }
            case BLUE -> {
                couleur = blueColor;
            }
            case RED -> {
                couleur = redColor;
            }
        }
        return couleur + getShape(tile) + defaultColor;
    }

    /**
     * Retourne la forme formatée d'une tuile spécifique.
     *
     * @param tileContenantForme La tuile pour laquelle obtenir la forme.
     * @return La forme formatée de la tuile.
     */
    private static String getShape(Tile tileContenantForme) {
        switch (tileContenantForme.shape()) {
            case PLUS -> {
                return " +";
            }
            case ROUND -> {
                return " O";
            }
            case SQUARE -> {
                return "[]";
            }
            case STAR -> {
                return " *";
            }
            case CROSS -> {
                return " X";
            }
            case DIAMOND -> {
                return "<>";
            }
        }
        return " ";
    }

    /**
     * Affiche les informations du joueur actuel, y compris son nom, les tuiles de sa main et son score.
     *
     * @param currentPlayer Le joueur actuel.
     */
    public static void display(Player currentPlayer){
        System.out.println();
        System.out.println("Nom du player: "+currentPlayer.getName());
        for (int pos = 0; pos < currentPlayer.getHand().size(); pos++) {
            Tile tile = currentPlayer.getHand().get(pos);
            System.out.print(getColor(tile)+" ");
        }
        System.out.print("Les index vont de 0 a 5"+" SCORE: "+currentPlayer.getScore());
        System.out.println();
    }

    /**
     * Affiche un message d'aide expliquant les commandes du jeu.
     */
    public static void displayHelp(){
        System.out.println(
                "Q W I R K L E\n" +
                        "Qwirkle command:\n" +
                        "- play 1 tile : o <row> <col> <i>\n" +
                        "- play line: l <row> <col> <direction> <i1> [<i2>]\n" +
                        "- play plic-ploc : m <row1> <col1> <i1> [<row2> <col2> <i2>]\n" +
                        "- play first : f <i1> [<i2>]\n" +
                        "- pass : p\n" +
                        "- quit : q\n" +
                        "i : index in list of tiles\n" +
                        "d : direction in l (left), r (right), u (up), d(down)\n" +
                        "s : Sauvegarder la partie en cours\n");
    }

    /**
     * Affiche un message d'erreur.
     *
     * @param message Le message d'erreur à afficher.
     */
    public static void displayError(String message){
        System.out.println(message);
    }

    public static void displayWinner(Player[] players, int winnerIndex) {
        System.out.println("Le gagnant est : " + players[winnerIndex].getName());
        System.out.println("Score : " + players[winnerIndex].getScore());
    }


}



