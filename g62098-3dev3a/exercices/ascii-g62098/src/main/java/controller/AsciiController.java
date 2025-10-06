package controller;

import model.AsciiPaint;
import model.ColoredShape;
import model.Commandes.ColorShapeCommand;
import model.Shape;
import view.Vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AsciiController {
    private AsciiPaint paint;
    private Vue vue;
    private Scanner clavier;

    // Le constructeur prend le modèle en paramètre
    public AsciiController(AsciiPaint asciiPaint, Vue vue) {
        this.paint = asciiPaint;
        this.vue = vue;
        this.clavier = new Scanner(System.in);
    }

    // Boucle d'application principale
    /**
     * Démarre la boucle de commande et gère l'application.
     */
    public void start() {
        while (true) {  // Boucle infinie jusqu'à "quit"
            // Demander à l'utilisateur de saisir une commande
            System.out.println("Entrez une commande (ex: " +
                    "add, show, list, move, color, quit) : ");
            String commande = clavier.nextLine();

            //regarder commande avant pattern
            // Expressions régulières pour analyser les différentes commandes
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

            // Matchers pour analyser la commande
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
            Matcher ungroupMatcher = ungroupPattern.matcher(commande);
            Matcher undoMatcher = undoPattern.matcher(commande);
            Matcher redoMatcher = redoPattern.matcher(commande);

            // Liste de formes
            List<Shape> formes = null;

            if (circleMatcher.matches()) {
                // Ajouter un cercle
                int x = Integer.parseInt(circleMatcher.group(1));
                int y = Integer.parseInt(circleMatcher.group(2));
                double rayon = Double.parseDouble(circleMatcher.group(3));
                char couleur = circleMatcher.group(4).charAt(0);
                paint.newCircle(x, y, rayon, couleur);

            } else if (rectangleMatcher.matches()) {
                // Ajouter un rectangle
                int x = Integer.parseInt(rectangleMatcher.group(1));
                int y = Integer.parseInt(rectangleMatcher.group(2));
                double largeur = Double.parseDouble(rectangleMatcher.group(3));
                double hauteur = Double.parseDouble(rectangleMatcher.group(4));
                char couleur = rectangleMatcher.group(5).charAt(0);
                paint.newRectangle(x, y, largeur, hauteur, couleur);

            } else if (squareMatcher.matches()) {
                // Ajouter un carré
                int x = Integer.parseInt(squareMatcher.group(1));
                int y = Integer.parseInt(squareMatcher.group(2));
                double cote = Double.parseDouble(squareMatcher.group(3));
                char couleur = squareMatcher.group(4).charAt(0);
                paint.newSquare(x, y, cote, couleur);

            } else if (showMatcher.matches()) {
                // Afficher le dessin
                String dessin = paint.asAscii();
                vue.afficherDessin(dessin);

            } else if (listMatcher.matches()) {
                // Lister les formes
                formes = paint.getDrawing().getShapes();
                vue.afficherListeFormes(formes);

            } else if (colorMatcher.matches()) {
                // Changer la couleur d'une forme
                formes = paint.getDrawing().getShapes();
                int nForme = Integer.parseInt(colorMatcher.group(1));
                char couleur = colorMatcher.group(2).charAt(0);

                if (nForme >= 0 && nForme < formes.size()) {
                    ColoredShape coloredShape = (ColoredShape) formes.get(nForme);
                    char couleurActuelle = coloredShape.getColor();

                    ColorShapeCommand colorCommand = new ColorShapeCommand(coloredShape, couleur, couleurActuelle);
                    colorCommand.execute();
                    paint.changeColorWithUndo(coloredShape, couleur, couleurActuelle);
                } else {
                    System.out.println("Numéro de forme invalide.");
                }

            } else if (moveMatcher.matches()) {
                // Déplacer une forme
                int numForme = Integer.parseInt(moveMatcher.group(1));
                int moveH = Integer.parseInt(moveMatcher.group(2));
                int moveV = Integer.parseInt(moveMatcher.group(3));

                formes = paint.getDrawing().getShapes();
                if (numForme >= 0 && numForme < formes.size()) {
                    Shape shape = formes.get(numForme);
                    paint.moveShapeWithUndo(shape, moveH, moveV);
                } else {
                    System.out.println("Numéro de forme invalide.");
                }

            } else if (deleteMatcher.matches()) {
                // Supprimer une forme
                int shapeIndex = Integer.parseInt(deleteMatcher.group(1));
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
}
