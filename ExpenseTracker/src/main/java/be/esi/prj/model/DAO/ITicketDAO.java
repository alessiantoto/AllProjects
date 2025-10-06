package be.esi.prj.model.DAO;

import be.esi.prj.model.DTO.TicketDTO;
import java.util.List;
import java.util.Optional;

/**
 * Interface représentant les opérations de gestion des tickets dans la base de données.
 * Elle définit les méthodes pour ajouter, récupérer, lister et supprimer des tickets.
 */
public interface ITicketDAO {

  /**
   * Ajoute un ticket à la base de données.
   *
   * @param ticket L'objet `TicketDTO` à ajouter.
   * @return L'ID du ticket ajouté.
   */
  int addTicket(TicketDTO ticket);


  /**
   * Récupère tous les tickets enregistrés dans la base de données.
   *
   * @return Une liste contenant tous les tickets sous forme d'objets `TicketDTO`.
   */
  List<TicketDTO> getAllTickets();
  List<TicketDTO> getTicketsByUser(int userId);
}
