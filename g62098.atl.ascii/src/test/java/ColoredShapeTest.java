import model.ColoredShape;
import model.Point;
import model.Rectangle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColoredShapeTest {
    @Test
    public void testGetColor() {
        Point p = new Point(0,0);
        // Arrange
        ColoredShape coloredShape = new Rectangle(p,2,2,'R');

        // Act
        char color = coloredShape.getColor();

        // Assert
        assertEquals('R', color);
    }

    @Test
    public void testSetColor() {
        Point p = new Point(0,0);
        // Arrange
        ColoredShape coloredShape = new Rectangle(p,2,2,'R');

        coloredShape.setColor('X');

        // Act
        char color = coloredShape.getColor();

        // Assert
        assertEquals('X', color);
    }



}
