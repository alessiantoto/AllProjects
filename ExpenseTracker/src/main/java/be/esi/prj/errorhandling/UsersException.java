package be.esi.prj.errorhandling;

/**
 * Cette classe représente une exception spécifique liée aux erreurs survenant
 * lors de la gestion des utilisateurs dans l'application.
 * Elle étend `RuntimeException` pour permettre une gestion d'exception non vérifiée.
 */
public class UsersException extends RuntimeException {

  /**
   * Constructeur pour créer une nouvelle instance de `UsersException` avec un message
   * d'erreur et une cause d'exception.
   *
   * @param message le message détaillant l'erreur
   * @param cause la cause de l'exception, généralement une autre exception
   */
  public UsersException(String message, Throwable cause) {
    super(message, cause);
  }
}
