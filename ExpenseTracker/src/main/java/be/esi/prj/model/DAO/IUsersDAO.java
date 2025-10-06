package be.esi.prj.model.DAO;

import be.esi.prj.model.DTO.UsersDTO;
import java.util.List;
import java.util.Optional;

/**
 * Interface représentant les opérations de gestion des utilisateurs dans la base de données.
 * Elle définit les méthodes pour ajouter, récupérer, lister et supprimer des utilisateurs.
 */
public interface IUsersDAO {

  /**
   * Ajoute un utilisateur à la base de données.
   *
   * @param userDTO L'objet `UsersDTO` représentant l'utilisateur à ajouter.
   * @return L'ID de l'utilisateur ajouté.
   */
  int addUser(UsersDTO userDTO);

  /**
   * Récupère un utilisateur spécifique en fonction de son ID.
   *
   * @param id L'ID de l'utilisateur à récupérer.
   * @return Un objet `Optional<UsersDTO>` contenant l'utilisateur si trouvé, sinon vide.
   */
  Optional<UsersDTO> getUser(int id);

  /**
   * Récupère tous les utilisateurs enregistrés dans la base de données.
   *
   * @return Une liste contenant tous les utilisateurs sous forme d'objets `UsersDTO`.
   */
  List<UsersDTO> getAllUsers();

  /**
   * Supprime un utilisateur spécifique de la base de données en fonction de son ID.
   *
   * @param id L'ID de l'utilisateur à supprimer.
   */
  void deleteUser(int id);
}
