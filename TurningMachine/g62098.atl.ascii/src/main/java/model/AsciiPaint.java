package model;

import model.Commandes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * La classe AsciiPaint permet de créer et de manipuler des dessins ASCII.
 * Elle permet d'ajouter des cercles, des rectangles et des carrés au dessin,
 * puis de générer une représentation ASCII du dessin.
 */
public class AsciiPaint {
    Drawing drawing;
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    /**
     * Crée une instance de AsciiPaint
     *
     * @param width  La largeur du dessin en caracteres
     * @param height La hauteur du dessin en caracteres
     */
    public AsciiPaint(int width, int height) {
        drawing = new Drawing(width, height);
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Ajoute une nouvelle forme au dessin en utilisant une commande spécifiée, puis la stocke
     * dans la pile undo pour permettre une éventuelle annulation. Cette méthode exécute la
     * commande fournie, laquelle effectue l'ajout de la forme au dessin. Ensuite, la commande
     * est ajoutée à la pile undo, et la pile redo est effacée, car l'ajout d'une nouvelle forme
     * invalide les commandes de refonte.
     *
     * @param command La commande encapsulant l'ajout de la forme au dessin.
     */
    public void addShapeWithUndo(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Si une nouvelle commande est ajoutée, efface la pile redo
    }

    /**
     * efface la shape, l'index commence à 0
     * @param shapeIndex
     */
    // Méthode pour supprimer une forme avec Undo
    public void deleteShapeWithUndo(int shapeIndex) {
        Shape shapeToRemove = drawing.getShapes().get(shapeIndex);
        Command removeCommand = new RemoveShapeCommand(drawing, shapeToRemove);
        removeCommand.execute();
        undoStack.push(removeCommand);
        redoStack.clear(); // Si une nouvelle commande est ajoutée, efface la pile redo
    }


    /**
     * Groupe des formes sélectionnées avec la possibilité d'annuler l'opération.
     *
     * @param shapeIndices La liste des indices des formes à grouper.
     */
    public void groupShapesWithUndo(List<Integer> shapeIndices) {
        GroupShapeCommand command = new GroupShapeCommand(drawing, shapeIndices);
        command.execute();
        undoStack.push(command);
    }

    /**
     * Dégroupe une forme de type groupe avec la possibilité d'annuler l'opération.
     *
     * @param groupIndex L'indice du groupe à dégrouper dans la liste des formes du dessin.
     */
    public void ungroupShapesWithUndo(int groupIndex) {
        UngroupShapeCommand command = new UngroupShapeCommand(drawing, groupIndex);
        command.execute();
        undoStack.push(command);
    }

    /**
     * Déplace une forme spécifiée avec la possibilité d'annuler l'opération.
     *
     * @param shape La forme à déplacer.
     * @param dx    La quantité de déplacement en direction horizontale (x).
     * @param dy    La quantité de déplacement en direction verticale (y).
     */
    public void moveShapeWithUndo(Shape shape, double dx, double dy) {
        MoveShapeCommand moveCommand = new MoveShapeCommand(shape, dx, dy);
        addShapeWithUndo(moveCommand);
    }

    /**
     * Change couleur d'une forme spécifiée avec la possibilité d'annuler l'opération.
     *
     * @param coloredShape La forme à déplacer.
     * @param newColor    La quantité de déplacement en direction horizontale (x).
     * @param oldColor    La quantité de déplacement en direction verticale (y).
     */

    public void changeColorWithUndo(ColoredShape coloredShape, char newColor, char oldColor) {
        ColorShapeCommand colorCommand = new ColorShapeCommand(coloredShape, newColor,oldColor);
        colorCommand.execute();
        addShapeWithUndo(colorCommand);
    }


    /**
     * Ajoute un cercle au dessin avec les coordonnees spécifiées, le rayon et la couleur.
     *
     * @param x      La coordonnée X du centre du cercle.
     * @param y      La coordonnée Y du centre du cercle.
     * @param radius Le rayon du cercle.
     * @param color  La couleur du cercle (caractere ASCII).
     */


    public void newCircle(int x, int y, double radius, char color){
        Point center = new Point(x, y);
        Circle circle = new Circle(center,radius,color);
        Command addCommand = new AddShapeCommand(drawing, circle);
        addShapeWithUndo(addCommand);
    }

    /**
     * Ajoute un rectangle au dessin avec les coordonnées spécifiées, la largeur, la hauteur et la couleur.
     *
     * @param x      La coordonnée X du coin supérieur gauche du rectangle.
     * @param y      La coordonnée Y du coin supérieur gauche du rectangle.
     * @param width  La largeur du rectangle.
     * @param height La hauteur du rectangle.
     * @param color  La couleur du rectangle (caractère ASCII).
     */

    public void newRectangle(int x, int y, double width, double height, char color){
        Point upperLeft = new Point(x,y);
        Rectangle rectangle = new Rectangle(upperLeft,width,height,color);
        Command addCommand = new AddShapeCommand(drawing, rectangle);
        addShapeWithUndo(addCommand);

    }

    /**
     * Ajoute un carré au dessin avec les coordonnées spécifiées, la longueur du côté et la couleur.
     *
     * @param x     La coordonnée X du coin supérieur gauche du carré.
     * @param y     La coordonnée Y du coin supérieur gauche du carré.
     * @param side  La longueur du côté du carré.
     * @param color La couleur du carré (caractère ASCII).
     */
    public void newSquare(int x, int y, double side, char color){
        Point upperLeft = new Point(x, y);
        Square square = new Square(upperLeft, side, color);
        Command addCommand = new AddShapeCommand(drawing, square);
        addShapeWithUndo(addCommand);
    }

    /**
     * Génère une représentation ASCII du dessin.
     *
     * @return Une chaîne de caractères représentant le dessin en ASCII.
     */
    public String asAscii(){
        int width = drawing.getWidth();
        int height = drawing.getHeight();
        char[][] canvas = new char[height][width];

        // Remplir le tableau canvas avec des espaces
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = ' ';
            }
        }

        // Dessiner chaque forme sur le canvas
        List<Shape> shapes = drawing.getShapes();
        for (Shape shape : shapes) {
            char color = shape.getColor();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Point p = new Point(j, i);
                    if (shape.isInside(p)) {
                        canvas[i][j] = color;
                    }
                }
            }
        }
        StringBuilder asciiArt = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                asciiArt.append(canvas[i][j]);
            }
            asciiArt.append('\n'); // Nouvelle ligne pour chaque ligne d'ASCII art
        }
        return asciiArt.toString();

    }
    /**
     * Obtient l'objet Drawing associé à cette instance de AsciiPaint.
     *
     * @return L'objet Drawing contenant les formes du dessin.
     */
    public Drawing getDrawing() {
        return drawing;
    }


    /**
     * Annule la dernière opération effectuée sur le dessin. Cette méthode dépile la dernière
     * commande effectuée, puis appelle la méthode undo() de cette commande pour annuler
     * l'effet de cette opération. La commande annulée est ensuite ajoutée à la pile redo pour
     * permettre une éventuelle refonte.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command lastCommand = undoStack.pop();
            lastCommand.undo();
            redoStack.push(lastCommand);
        }
    }

    /**
     * Refait la dernière opération annulée sur le dessin. Cette méthode dépile la dernière
     * commande annulée, puis appelle la méthode execute() de cette commande pour refaire
     * l'opération. La commande refaite est ensuite ajoutée à la pile undo pour permettre
     * une éventuelle annulation.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command lastUndoneCommand = redoStack.pop();
            lastUndoneCommand.execute();
            undoStack.push(lastUndoneCommand);
        }
    }

    public Stack<Command> getUndoStack() {
        return undoStack;
    }

    public Stack<Command> getRedoStack() {
        return redoStack;
    }
}
