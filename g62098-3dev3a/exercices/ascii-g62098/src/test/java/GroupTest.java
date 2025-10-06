import model.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GroupTest {
    @Test
    public void testIsInside() {
        // Créez quelques formes pour les tests
        Rectangle rectangle = new Rectangle(new Point(1, 1), 2, 2, 'R');
        Circle circle = new Circle(new Point(4, 4), 2, 'C');

        // Créez un groupe avec les formes créées
        List<Shape> shapes = new ArrayList<>();
        shapes.add(rectangle);
        shapes.add(circle);
        Group group = new Group(shapes, 'G');

        // Vérifiez que le point à l'intérieur du rectangle est détecté
        assertTrue(group.isInside(new Point(2, 0)));

        // Vérifiez que le point à l'intérieur du cercle est détecté
        assertTrue(group.isInside(new Point(5, 5)));

        // Vérifiez que le point à l'extérieur du groupe est correctement détecté
        assertFalse(group.isInside(new Point(0, 0)));
    }

    @Test
    public void testMove() {
        // Créez quelques formes pour les tests
        Rectangle rectangle = new Rectangle(new Point(1, 1), 2, 2, 'R');
        Circle circle = new Circle(new Point(4, 4), 2, 'C');

        // Créez un groupe avec les formes créées
        List<Shape> shapes = new ArrayList<>();
        shapes.add(rectangle);
        shapes.add(circle);
        Group group = new Group(shapes, 'G');

        // Déplacez le groupe
        group.move(2, 2);

        // Vérifiez que les formes à l'intérieur du groupe ont été déplacées correctement
        assertEquals(4.0, rectangle.getUpperLeft().getX(), 0.001);
        assertEquals(4.0, rectangle.getUpperLeft().getY(), 0.001);

        assertEquals(6.0, circle.getCenter().getX(), 0.001);
        assertEquals(6.0, circle.getCenter().getY(), 0.001);
    }

    @Test
    public void testIsInsideEmptyGroup() {
        // Créez un groupe vide
        Group emptyGroup = new Group(new ArrayList<>(), 'E');

        // Vérifiez que le point n'est pas détecté à l'intérieur d'un groupe vide
        assertFalse(emptyGroup.isInside(new Point(0, 0)));
    }

    @Test
    public void testMoveEmptyGroup() {
        // Créez un groupe vide
        Group emptyGroup = new Group(new ArrayList<>(), 'E');

        // Déplacez le groupe vide
        emptyGroup.move(2, 2);

        // Aucune exception ne devrait être levée, et le groupe doit rester vide
        assertTrue(emptyGroup.getShapes().isEmpty());
    }




}
