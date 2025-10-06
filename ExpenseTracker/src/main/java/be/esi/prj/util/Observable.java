package be.esi.prj.util;

/**
 * An Observable object in the Observer/Observable patter. Maintains a list of observer and notifies
 * them whenever an internal state changed.
 */
public interface Observable {
  /**
   * Adds an observer to the list of observers.
   *
   * @param observer The observer to be added.
   */
  void addObserver(Observer observer);

  /**
   * Removes the observer from the list.
   *
   * @param observer observer The observer to be removed.
   */
  void removeObserver(Observer observer);

  /** Notifies all observer by calling their 'update' method. */
  void notifyObservers(State state);
}
