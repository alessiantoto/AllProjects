package g62098.dev3.oxono.Model;

/**
 * The {@code Totem} class represents a totem in the Oxono game.
 * Each totem is associated with a symbol (X or O) and a player (PINK or BLACK).
 */
public class Totem implements Occupant {
    // Attributes
    private Symbol symbol; // Type of symbol (X or O)
    private OxonoColor oxonoColor; // Player associated (PINK or BLACK)
    private Boolean movable; // Indicates whether the totem can be moved

    /**
     * Constructor to initialize the totem with a symbol, a player, and its position.
     *
     * @param symbol The symbol of the totem (X or O).
     */
    public Totem(Symbol symbol) {
        this.symbol = symbol;    // Sets the type of symbol for the totem
        this.oxonoColor = OxonoColor.BLUE; // Sets the player associated with the totem (defaulted to BLUE)
        this.movable = true; // By default, the totem is movable
    }

    // Getters
    public Symbol getSymbol() {
        return symbol;    // Returns the symbol type of the totem
    }

    public OxonoColor getColor() {
        return oxonoColor;  // Returns the player associated with the totem
    }

    public Boolean getMovable() {
        return movable; // Returns whether the totem is movable
    }

    /**
     * toString method to display information about the totem in a readable format.
     *
     * @return A string representing the totem.
     */
    @Override
    public String toString() {
        return "Totem [type=" + symbol + ", player=" + oxonoColor + "]";
    }
}
