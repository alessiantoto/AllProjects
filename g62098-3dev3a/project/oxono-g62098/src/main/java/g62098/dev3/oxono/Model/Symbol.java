package g62098.dev3.oxono.Model;

/**
 * The {@code Symbol} enumeration represents the two possible symbols for the pieces in the Oxono game.
 * Each piece is marked with a symbol, either "X" or "O", to differentiate between the types of pieces.
 */
public enum Symbol {
    /**
     * "X" symbol for the pieces.
     */
    X,

    /**
     * "O" symbol for the pieces.
     */
    O;

    /**
     * Returns a textual representation of the piece's symbol.
     * This method is useful for displaying the symbol in a readable format.
     *
     * @return a string representing the piece's symbol:
     *         "X" for the X symbol and "O" for the O symbol.
     */
    @Override
    public String toString() {
        return this == X ? "X" : "O";
    }
}
