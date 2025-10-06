package g62098.dev3.oxono.Model;

/**
 * The {@code Piece} class represents a piece in the Oxono game.
 * Each piece has a color (PINK or BLACK) and a symbol (X or O).
 */
public class Piece implements Occupant {
    private final OxonoColor oxonoColor; // The color of the piece (PINK or BLACK)
    private final Symbol symbol; // The symbol of the piece (X or O)
    private Boolean movable;

    /**
     * Constructor to initialize the piece with a color and a symbol.
     *
     * @param oxonoColor The color of the piece (PINK or BLACK).
     * @param symbol The symbol of the piece (X or O).
     */
    public Piece(OxonoColor oxonoColor, Symbol symbol) {
        this.oxonoColor = oxonoColor;
        this.symbol = symbol;
        this.movable = false; // The piece is initially not movable
    }

    @Override
    /**
     * Getter for the color of the piece.
     *
     * @return the color of the piece (PINK or BLACK).
     */
    public OxonoColor getColor() {
        return oxonoColor;
    }

    @Override
    /**
     * Getter for the symbol of the piece.
     *
     * @return the symbol of the piece (X or O).
     */
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    /**
     * Getter for the movable status of the piece.
     *
     * @return true if the piece is movable, false otherwise.
     */
    public Boolean getMovable() {
        return movable;
    }

    /**
     * Provides a string representation of the piece.
     *
     * @return a string representing the piece, including its color and symbol.
     */
    @Override
    public String toString() {
        return "Piece{" +
                "color=" + oxonoColor +
                ", symbol=" + symbol +
                '}';
    }
}
