package g62098.dev3.oxono.Model;

/**
 * Interface for objects that can have observers.
 * Implementing classes should handle adding, removing, and notifying observers.
 */
public interface Observable {

    /**
     * Registers an observer to be notified of changes.
     *
     * @param o The observer to be registered.
     */
    void registerObserver(Observer o);

    /**
     * Removes an observer from the notification list.
     *
     * @param o The observer to be removed.
     */
    void removeObserver(Observer o);

    /**
     * Notifies all registered observers about changes.
     */
    void notifyObservers();
}
