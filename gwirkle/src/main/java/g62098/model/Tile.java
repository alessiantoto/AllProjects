package g62098.model;

import java.io.Serializable;

/**

 Une tuile est composé d'une couleur et d'une forme.
 @param color la couleur de la tuile
 @param shape la forme de la tuile
 */
public record Tile (Color color, Shape shape) implements Serializable {
}

