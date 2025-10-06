import model.Point;
import model.Rectangle;
import org.junit.Test;

import static org.junit.Assert.*;

public class RectangleTest {
    @Test
    public void testMove() {
        Point upperLeft = new Point(0, 0);
        Rectangle rectangle = new Rectangle(upperLeft, 2, 2, 'R');
        rectangle.move(2, 2);

        // Vérifiez que les coordonnées du coin supérieur gauche ont été mises à jour correctement
        assertEquals(2.0, rectangle.getUpperLeft().getX(), 0.001); // Utilisation de delta pour traiter les erreurs d'arrondi
        assertEquals(2.0, rectangle.getUpperLeft().getY(), 0.001); // Utilisation de delta pour traiter les erreurs d'arrondi
    }

    @Test
    public void testIsInside() {
        Point upperLeft = new Point(1, 1);
        Rectangle rectangle = new Rectangle(upperLeft, 4, 3, 'R');
        Point insidePoint = new Point(3, -2);
        Point outsidePoint = new Point(5, 5);

        // Vérifiez que le point à l'intérieur du rectangle est correctement détecté
        assertTrue(rectangle.isInside(insidePoint));

        // Vérifiez que le point à l'extérieur du rectangle est correctement détecté
        assertFalse(rectangle.isInside(outsidePoint));

        // Déplacez le rectangle
        rectangle.move(2, 2);

        // Vérifiez à nouveau que le point à l'intérieur du rectangle est correctement détecté après le déplacement
        assertTrue(rectangle.isInside(new Point(5, 4)));

        // Vérifiez à nouveau que le point à l'extérieur du rectangle est correctement détecté après le déplacement
        assertFalse(rectangle.isInside(new Point(7, 7)));
    }
}