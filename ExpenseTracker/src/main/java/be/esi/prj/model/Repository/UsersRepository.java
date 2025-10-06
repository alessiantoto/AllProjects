package be.esi.prj.model.Repository;

import be.esi.prj.errorhandling.TicketException;
import be.esi.prj.model.DAO.UsersDAO;
import be.esi.prj.model.DTO.UsersDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gère les opérations liées aux utilisateurs à travers {@link UsersDAO}
 * avec un cache en mémoire pour améliorer les performances.
 */
public class UsersRepository {

  /**
   * DAO pour accéder aux données des utilisateurs dans la base.
   */
  private UsersDAO usersDAO;

  /**
   * Cache en mémoire des utilisateurs, avec l'ID de l'utilisateur comme clé.
   */
  private final Map<Integer, UsersDTO> usersCache;

  /**
   * Initialise le repository avec une connexion à la base de données.
   * Charge également les utilisateurs existants dans le cache.
   *
   */
  public UsersRepository() {
    this.usersDAO = new UsersDAO();
    this.usersCache = new ConcurrentHashMap<>();
    loadCache();
  }

  /**
   * Charge tous les utilisateurs de la base dans le cache.
   */
  private void loadCache() {
    usersDAO.getAllUsers().forEach(user -> usersCache.put(user.user(), user));
  }

  /**
   * Ajoute un utilisateur et le met en cache si l'insertion a réussi.
   *
   * @param userDTO Utilisateur à ajouter.
   * @return L'ID généré pour l'utilisateur, ou -1 en cas d’échec.
   */
  public int addUser(UsersDTO userDTO) {
    int generatedID = usersDAO.addUser(userDTO);

    if (generatedID != -1) {
      usersCache.put(generatedID, new UsersDTO(generatedID, userDTO.name(), userDTO.salary()));
    }
    return generatedID;
  }

  /**
   * Récupère un utilisateur par son ID depuis le cache ou la base si absent.
   *
   * @param userId ID de l’utilisateur recherché.
   * @return Un {@link Optional} contenant l'utilisateur s’il est trouvé.
   */
  public Optional<UsersDTO> getUser(int userId) {
    return Optional.ofNullable(usersCache.get(userId))
            .or(
                    () -> {
                      Optional<UsersDTO> userFromDb = usersDAO.getUser(userId);
                      userFromDb.ifPresent(user -> usersCache.put(userId, user));
                      return userFromDb;
                    });
  }

  /**
   * Retourne tous les utilisateurs actuellement en cache.
   *
   * @return Liste de tous les utilisateurs.
   */
  public List<UsersDTO> getAllUsers() {
    return new ArrayList<>(usersCache.values());
  }


  /**
   * Supprime un utilisateur de la base et du cache.
   *
   * @param userId ID de l’utilisateur à supprimer.
   * @throws TicketException si une erreur survient lors de la suppression.
   */
  public void deleteUser(int userId) {
    try {
      usersDAO.deleteUser(userId);
      usersCache.remove(userId);
    } catch (TicketException e) {
      System.err.println(
              "❌ Erreur lors de la suppression de l'utilisateur " + userId + " : " + e.getMessage());
      throw e;
    }
  }
}
