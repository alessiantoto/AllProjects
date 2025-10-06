package be.esi.prj.model.DTO;

/**
 * Représente un utilisateur dans le système.
 * Ce DTO contient les informations relatives à un utilisateur, telles que son ID, son nom et son salaire.
 * Il est utilisé pour transférer les données des utilisateurs entre les différentes couches de l'application.
 */
public record UsersDTO(int user, String name, Double salary) {
  public UsersDTO(String name, Double salary) {
    this(0, name, salary);
  }
}
