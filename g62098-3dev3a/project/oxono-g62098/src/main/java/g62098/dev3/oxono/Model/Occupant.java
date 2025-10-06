package g62098.dev3.oxono.Model;

/**
 * Interface representing an occupant on the Oxono game board.
 * All occupants (Totem and Piece) must implement this interface.
 */
public interface Occupant {

    /**
     * Returns the symbol of the occupant (X or O).
     *
     * @return The symbol of the occupant.
     */
    Symbol getSymbol();

    /**
     * Returns the color to which this occupant belongs.
     *
     * @return The color of the occupant.
     */
    OxonoColor getColor(); // Changed from Player to Color

    /**
     * Indicates whether this occupant can be moved.
     *
     * @return true if the occupant can be moved, false otherwise.
     */
    Boolean getMovable();
}
