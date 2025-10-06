import model.Point;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointTest {
    @Test
    public void testDistanceTo() {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(3, 4);
        double expectedDistance = 5.0; // La distance entre (0,0) et (3,4) est de 5.0

        double actualDistance = point1.distanceTo(point2);

        assertEquals(expectedDistance, actualDistance, 0.001); // Utilisation d'une tolérance pour les comparaisons de nombres flottants.
    }

    @Test
    public void testMove() {
        Point point = new Point(1, 1);
        point.move(2, 3);

        // Vérifiez que les coordonnées ont été mises à jour correctement après le déplacement
        assertEquals(3.0, point.getX(), 0.001); // Utilisation de delta pour traiter les erreurs d'arrondi
        assertEquals(4.0, point.getY(), 0.001); // Utilisation de delta pour traiter les erreurs d'arrondi
    }
}
