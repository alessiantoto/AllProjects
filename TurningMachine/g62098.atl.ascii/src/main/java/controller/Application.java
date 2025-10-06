package controller;

import model.AsciiPaint;
import model.ColoredShape;
import model.Commandes.ColorShapeCommand;
import model.Commandes.Command;
import model.Commandes.MoveShapeCommand;
import model.Shape;
import view.Vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * La classe Application est l'application principale pour créer et manipuler des dessins ASCII avec des formes géométriques.
 * Elle permet à l'utilisateur d'interagir avec le programme en saisissant des commandes depuis la ligne de commande.
 */
public class Application {
    private AsciiPaint paint;
    private Vue vue;

    /**
     * Crée une nouvelle instance de l'application.
     */
    public Application() {
        Scanner clavier = new Scanner(System.in);
        System.out.println("Entrez la largeur de la zone de dessin : ");
        int largeur = clavier.nextInt();

        System.out.println("Entrez la hauteur de la zone de dessin : ");
        int hauteur = clavier.nextInt();
        paint = new AsciiPaint(largeur, hauteur);
        vue = new Vue();
    }


    /**
     * Lance l'application et gère les commandes de l'utilisateur.
     */
    public void start() {
        Scanner clavier = new Scanner(System.in);
        char continuer = 'O'; // Initialisation à 'O' pour permettre la première itération.

        while (true) {  //faire while true
            // Demander à l'utilisateur de saisir une commande
            System.out.println("Entrez une commande (ex: " +
                    "add, show, list, move, color, quit) : ");
            String commande = clavier.nextLine();

            // Utiliser une expression régulière pour analyser la commande
            Pattern circlePattern = Pattern.compile("^\\s*add\\s+circle\\s+(\\d{1,2})\\s+(\\d{1,2})\\s+(\\d{1,2}(?:\\.\\d+)?)\\s+([A-Za-z])\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern rectanglePattern = Pattern.compile("^\\s*add\\s+rectangle\\s+(\\d{1,2})\\s+(\\d{1,2})\\s+(\\d{1,2})\\s+(\\d{1,2}(?:\\.\\d+)?)\\s+([A-Za-z])\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern squarePattern = Pattern.compile("^\\s*add\\s+square\\s+(\\d{1,2})\\s+(\\d{1,2})\\s+(\\d{1,2}(?:\\.\\d+)?)\\s+([A-Za-z])\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern showPattern = Pattern.compile("^\\s*show\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern listPattern = Pattern.compile("^\\s*list\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern colorPattern = Pattern.compile("^\\s*color\\s+(\\d+)\\s+([A-Za-z])\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern quitPattern = Pattern.compile("^\\s*quit\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern movePattern = Pattern.compile("^\\s*move\\s+(\\d{1,2})\\s+(\\d{1,2})\\s+(\\d{1,2})\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern deletePattern = Pattern.compile("^\\s*delete\\s+(\\d{1,2})\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern groupPattern = Pattern.compile("^\\s*group\\s+(\\d{1,2}(?:\\s+\\d{1,2})*)\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern ungroupPattern = Pattern.compile("^\\s*ungroup\\s+(\\d{1,2})\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern undoPattern = Pattern.compile("^\\s*undo\\s*$", Pattern.CASE_INSENSITIVE);
            Pattern redoPattern = Pattern.compile("^\\s*redo\\s*$", Pattern.CASE_INSENSITIVE);




            Matcher colorMatcher = colorPattern.matcher(commande);
            Matcher listMatcher = listPattern.matcher(commande);
            Matcher circleMatcher = circlePattern.matcher(commande);
            Matcher rectangleMatcher = rectanglePattern.matcher(commande);
            Matcher squareMatcher = squarePattern.matcher(commande);
            Matcher showMatcher = showPattern.matcher(commande);
            Matcher quitMatcher = quitPattern.matcher(commande);
            Matcher moveMatcher = movePattern.matcher(commande);
            Matcher deleteMatcher = deletePattern.matcher(commande);
            Matcher groupMatcher = groupPattern.matcher(commande);
            Matcher ungroupMatcher =ungroupPattern.matcher(commande);
            Matcher undoMatcher = undoPattern.matcher(commande);
            Matcher redoMatcher = redoPattern.matcher(commande);

            List<Shape> formes = null;
            if (circleMatcher.matches()) {
                int x = Integer.parseInt(circleMatcher.group(1));
                int y = Integer.parseInt(circleMatcher.group(2));
                double valeur1 = Double.parseDouble(circleMatcher.group(3));
                char couleur = circleMatcher.group(4).charAt(0);
                paint.newCircle(x, y, valeur1, couleur);
            } else if (rectangleMatcher.matches()) {
                int x = Integer.parseInt(rectangleMatcher.group(1));
                int y = Integer.parseInt(rectangleMatcher.group(2));
                double valeur1 = Double.parseDouble(rectangleMatcher.group(3));
                double valeur2 = Double.parseDouble(rectangleMatcher.group(4));
                char couleur = rectangleMatcher.group(5).charAt(0);
                paint.newRectangle(x, y, valeur1, valeur2, couleur);
            } else if (squareMatcher.matches()) {
                int x = Integer.parseInt(squareMatcher.group(1));
                int y = Integer.parseInt(squareMatcher.group(2));
                double side = Double.parseDouble(squareMatcher.group(3));
                char couleur = squareMatcher.group(4).charAt(0);
                paint.newSquare(x, y, side, couleur);
            } else if (showMatcher.matches()) {
                // Afficher le dessin
                String dessin = paint.asAscii();
                vue.afficherDessin(dessin);
            } else if (listMatcher.matches()) {
                formes = paint.getDrawing().getShapes();
                vue.afficherListeFormes(formes);
            } else if (colorMatcher.matches()) {
                formes = paint.getDrawing().getShapes();
                int nForme = Integer.parseInt(colorMatcher.group(1));
                char couleur = colorMatcher.group(2).charAt(0);


                if (nForme >= 0 && nForme < formes.size()) {
                    ColoredShape coloredShape = (ColoredShape) formes.get(nForme);
                    //obtenir couleur actuelle
                    char couleurActuelle = coloredShape.getColor();
                    // Créer une instance de ColorCommand
                    ColorShapeCommand colorCommand = new ColorShapeCommand(coloredShape, couleur,couleurActuelle);

                    // Exécuter la commande
                    colorCommand.execute();

                    // Ajouter la commande à la liste des commandes pour undo/redo
                    paint.changeColorWithUndo(coloredShape, couleur, couleurActuelle);
                } else {
                    System.out.println("Numéro de forme invalide.");
                }
            }
            else if(moveMatcher.matches()){
                int numForme = Integer.parseInt(moveMatcher.group(1));
                int moveH = Integer.parseInt(moveMatcher.group(2));
                int moveV = Integer.parseInt(moveMatcher.group(3));

                // Obtenez la forme correspondante depuis votre liste de formes
                formes = paint.getDrawing().getShapes();
                if (numForme >= 0 && numForme < formes.size()) {
                    Shape shape = formes.get(numForme);
                    paint.moveShapeWithUndo(shape, moveH, moveV);
                } else {
                    System.out.println("Numéro de forme invalide.");
                }
            }
            else if(deleteMatcher.matches()){
                int shapeIndex = Integer.parseInt(deleteMatcher.group(1));
                // Maintenant, vous avez l'index de la forme à supprimer (shapeIndex)
                // Vous pouvez appeler la méthode deleteShape(shapeIndex) pour effectuer la suppression.
                paint.deleteShapeWithUndo(shapeIndex);
            }
            else if (groupMatcher.matches()) {
                String groupShapes = groupMatcher.group(1);
                String[] shapeIndices = groupShapes.split("\\s+");

                List<Integer> indicesToGroup = new ArrayList<>();
                for (String shapeIndex : shapeIndices) {
                    indicesToGroup.add(Integer.parseInt(shapeIndex));
                }
                paint.groupShapesWithUndo(indicesToGroup);
            }

            else if(ungroupMatcher.matches()){
                String groupIndexStr = ungroupMatcher.group(1);

                // Convertir le numéro du groupe en entier
                int groupIndex = Integer.parseInt(groupIndexStr);

                // Appeler la méthode ungroup avec le numéro du groupe
                paint.ungroupShapesWithUndo(groupIndex);
            }
            else if (undoMatcher.matches()) {
                paint.undo();
            }
            else if (redoMatcher.matches()) {
                paint.redo();
            }
            else if (quitMatcher.matches()) {
                // Quitter le jeu en sortant de la boucle
                break;
            }
            else {
                System.out.println("Commande invalide.");
            }
        }
    }

    /**
     * Méthode principale qui crée une instance de l'application et la démarre.
     *
     * @param args Les arguments de ligne de commande (non utilisés dans cet exemple).
     */
    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }

}
