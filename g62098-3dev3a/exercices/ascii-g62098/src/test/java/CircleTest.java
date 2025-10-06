import model.Circle;
import model.Point;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CircleTest {
    @Test
    public void testIsInsideInside() {
        Point center = new Point(5, 5);
        Circle circle = new Circle(center, 3, 'C');
        Point insidePoint = new Point(6, 6);

        assertTrue(circle.isInside(insidePoint));
    }
    @Test
    public void testMove() {
        Point center = new Point(5, 5);
        Circle circle = new Circle(center, 3, 'C');
        circle.move(2, 3);

        // Vérifier que le centre du cercle a été correctement déplacé
        assertTrue(center.getX() == 7 && center.getY() == 8);
    }

}
