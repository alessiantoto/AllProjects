package g62098.dev3.oxono.Model;

/**
 * The {@code OxonoException} class represents an exception specific to the Oxono game.
 * It extends the {@code RuntimeException} class to signal errors during the game's execution.
 */
public class OxonoException extends RuntimeException {

    /**
     * Constructor to create a new OxonoException with a specific message.
     *
     * @param message The message explaining the cause of the exception.
     */
    public OxonoException(String message) {
        super(message); // Pass the message to the superclass constructor
    }
}
