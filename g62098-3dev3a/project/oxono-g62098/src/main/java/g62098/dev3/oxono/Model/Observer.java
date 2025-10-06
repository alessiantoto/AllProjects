package g62098.dev3.oxono.Model;

/**
 * Interface for objects that want to be notified of changes.
 * Implementing classes should provide an update method to handle updates.
 */
public interface Observer {
    /**
     * Method to be called when an update occurs.
     */
    void update();
}
