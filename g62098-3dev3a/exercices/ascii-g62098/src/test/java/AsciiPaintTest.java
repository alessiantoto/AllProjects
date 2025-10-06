import model.*;
import model.Commandes.*;

import java.util.Arrays;
import java.util.List;
import model.Circle;
import model.Point;
import org.junit.Test;

import static org.junit.Assert.*;

public class AsciiPaintTest {

    @Test
    public void testAddShapeWithUndo() {
        AsciiPaint asciiPaint = new AsciiPaint(10, 10);
        Circle circle = new Circle(new Point(5, 5), 3, 'O');
        AddShapeCommand addCommand = new AddShapeCommand(asciiPaint.getDrawing(), circle);
        asciiPaint.addShapeWithUndo(addCommand);

        List<Shape> shapes = asciiPaint.getDrawing().getShapes();
        assertEquals(1, shapes.size());
        assertTrue(shapes.get(0) instanceof Circle);
        assertEquals(circle, shapes.get(0));
    }

    @Test
    public void testDeleteShapeWithUndo() {
        AsciiPaint asciiPaint = new AsciiPaint(10, 10);
        Circle circle = new Circle(new Point(5, 5), 3, 'O');
        AddShapeCommand addCommand = new AddShapeCommand(asciiPaint.getDrawing(), circle);
        asciiPaint.addShapeWithUndo(addCommand);

        List<Shape> shapesBeforeDelete = asciiPaint.getDrawing().getShapes();
        assertEquals(1, shapesBeforeDelete.size());

        asciiPaint.deleteShapeWithUndo(0);

        List<Shape> shapesAfterDelete = asciiPaint.getDrawing().getShapes();
        assertEquals(0, shapesAfterDelete.size());
    }

    @Test
    public void testGroupShapesWithUndo() {
        AsciiPaint asciiPaint = new AsciiPaint(10, 10);
        Circle circle = new Circle(new Point(5, 5), 3, 'O');
        Square square = new Square(new Point(2, 2), 4, 'X');

        AddShapeCommand addCircleCommand = new AddShapeCommand(asciiPaint.getDrawing(), circle);
        AddShapeCommand addSquareCommand = new AddShapeCommand(asciiPaint.getDrawing(), square);

        asciiPaint.addShapeWithUndo(addCircleCommand);
        asciiPaint.addShapeWithUndo(addSquareCommand);

        List<Shape> shapesBeforeGroup = asciiPaint.getDrawing().getShapes();
        assertEquals(2, shapesBeforeGroup.size());

        asciiPaint.groupShapesWithUndo(Arrays.asList(0, 1));

        List<Shape> shapesAfterGroup = asciiPaint.getDrawing().getShapes();
        assertEquals(1, shapesAfterGroup.size());
        assertTrue(shapesAfterGroup.get(0) instanceof Group);
    }

    @Test
    public void testUngroupShapesWithUndo() {
        AsciiPaint asciiPaint = new AsciiPaint(10, 10);
        Circle circle = new Circle(new Point(5, 5), 3, 'O');
        Square square = new Square(new Point(2, 2), 4, 'X');

        AddShapeCommand addCircleCommand = new AddShapeCommand(asciiPaint.getDrawing(), circle);
        AddShapeCommand addSquareCommand = new AddShapeCommand(asciiPaint.getDrawing(), square);

        asciiPaint.addShapeWithUndo(addCircleCommand);
        asciiPaint.addShapeWithUndo(addSquareCommand);

        List<Shape> shapesBeforeUngroup = asciiPaint.getDrawing().getShapes();
        assertEquals(2, shapesBeforeUngroup.size());

        asciiPaint.groupShapesWithUndo(Arrays.asList(0, 1));

        Group group = (Group) asciiPaint.getDrawing().getShapes().get(0);
        asciiPaint.ungroupShapesWithUndo(0);

        List<Shape> shapesAfterUngroup = asciiPaint.getDrawing().getShapes();
        assertEquals(2, shapesAfterUngroup.size());
        assertTrue(shapesAfterUngroup.contains(circle));
        assertTrue(shapesAfterUngroup.contains(square));
        assertFalse(shapesAfterUngroup.contains(group));
    }

    @Test
    public void testMoveCircle() {
        // Créez le cercle
        Point center = new Point(1, 2);
        double radius = 5;
        char color = 'C';
        Circle circle = new Circle(center, radius, color);

        // Déplacez le cercle
        double dx = 2;
        double dy = -3;
        circle.move(dx, dy);

        // Verifie si le deplacement est bon
        Point expectedCenter = new Point(3, -1);
        Point actualCenter = circle.getCenter();
        assertEquals(expectedCenter.getX(), actualCenter.getX(), 0.001); //  petite tolerance pour les comparaisons de nombres à virgule flottante
        assertEquals(expectedCenter.getY(), actualCenter.getY(), 0.001);

        // Vérifiez si le rayon et la couleur restent inchangés
        assertEquals(radius, circle.getRadius(), 0.001);
        assertEquals(color, circle.getColor());
    }

    @Test
    public void testNewSquare() {
        AsciiPaint asciiPaint = new AsciiPaint(10, 10);
        int x = 3;
        int y = 4;
        double side = 2.5;
        char color = 'S';

        // Ajoutez un carré avec la méthode newSquare
        asciiPaint.newSquare(x, y, side, color);

        // Vérifiez que le carré a bien été ajouté
        List<Shape> shapes = asciiPaint.getDrawing().getShapes();
        assertEquals(1, shapes.size());
        assertTrue(shapes.get(0) instanceof Square);

        // Vérifiez les propriétés du carré ajouté
        Square addedSquare = (Square) shapes.get(0);
        assertEquals(x, addedSquare.getUpperLeft().getX(), 0.001);
        assertEquals(y, addedSquare.getUpperLeft().getY(), 0.001);
        assertEquals(side, addedSquare.getWidth(), 0.001);
        assertEquals(color, addedSquare.getColor());
    }

    @Test
    public void testMoveShapeWithUndo() {
        AsciiPaint asciiPaint = new AsciiPaint(10, 10);
        Square square = new Square(new Point(2, 2), 4, 'X');

        // Ajoutez le carré au dessin
        asciiPaint.newSquare(2, 2, 4, 'X');

        // Déplacez le carré avec la méthode moveShapeWithUndo
        double dx = 3;
        double dy = -1;
        asciiPaint.moveShapeWithUndo(square, dx, dy);

        // Vérifiez que le carré a bien été déplacé
        assertEquals(7.0, square.getUpperLeft().getX(), 0.001);
        assertEquals(3.0, square.getUpperLeft().getY(), 0.001);
    }
        @Test
        public void testUndoAndRedo() {
            AsciiPaint asciiPaint = new AsciiPaint(10, 10);

            // Ajoutez une forme
            asciiPaint.newCircle(5, 5, 3, 'O');

            // Vérifiez que la pile undo n'est pas vide après l'ajout
            assertFalse(asciiPaint.getUndoStack().isEmpty());

            // Annulez l'ajout
            asciiPaint.undo();

            // Vérifiez que la pile undo est vide après l'annulation
            assertTrue(asciiPaint.getUndoStack().isEmpty());

            // Vérifiez que la pile redo n'est pas vide après l'annulation
            assertFalse(asciiPaint.getRedoStack().isEmpty());

            // Refaites l'ajout
            asciiPaint.redo();

            // Vérifiez que la pile redo est vide après le refait
            assertTrue(asciiPaint.getRedoStack().isEmpty());
        }
    /*
    @Test
    public void testAsAscii() {
        // Créez une instance de AsciiPaint
        AsciiPaint asciiPaint = new AsciiPaint(10, 10);

        // Ajoutez quelques formes au dessin
        asciiPaint.newCircle(3, 3, 2, 'O');
        asciiPaint.newRectangle(1, 1, 4, 3, 'R');

        // Obtenez la représentation ASCII attendue
        // Définir la représentation ASCII attendue
        String expectedAscii =
                " RRRRR    \n" +
                " RRRRR    \n" +
                "  OOO     \n" +
                " OOOOO    \n" +
                "  OOO     \n" +
                "   O      \n";

        // Obtenez la sortie réelle de asAscii
        String actualAscii = asciiPaint.asAscii();

        // Imprimer les chaînes ASCII pour examen
        System.out.println("Expected ASCII:");
        System.out.println(expectedAscii);
        System.out.println("Actual ASCII:");
        System.out.println(actualAscii);

        assertEquals("Representation ASCII incorrecte", expectedAscii, actualAscii);

    }

     */




}
