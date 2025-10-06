package g62098.dev3.oxono.Model;

/**
 * The {@code OxonoColor} enumeration represents the two players of the Oxono game.
 * Each player is associated with a specific color:
 * <ul>
 *   <li>PINK - Player with pink pieces</li>
 *   <li>BLACK - Player with black pieces</li>
 * </ul>
 * This enumeration clearly identifies which player owns which color of pieces.
 */
public enum OxonoColor {
    /**
     * Player with pink pieces.
     */
    PINK,

    /**
     * Player with black pieces.
     */
    BLACK,

    /**
     * Player with blue pieces (used for the totem).
     */
    BLUE;

    /**
     * Returns a textual representation of the player's color.
     * This method is useful for displaying the color name in a readable format.
     *
     * @return a string representing the player's color:
     *         "Pink" for PINK, "Black" for BLACK, or "Blue" for BLUE.
     */
    @Override
    public String toString() {
        if (this == PINK) {
            return "Pink";
        } else if (this == BLUE) {
            return "Blue";
        } else {
            return "Black";
        }
    }
}
