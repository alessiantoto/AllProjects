package be.esi.prj.model.Repository;

import be.esi.prj.model.DAO.TicketDAO;
import be.esi.prj.model.DTO.TicketDTO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gère les opérations liées aux tickets en s'appuyant sur {@link TicketDAO}
 * et en utilisant un cache pour améliorer les performances.
 */
public class TicketRepository {

  /**
   * DAO pour accéder aux données des tickets dans la base.
   */
  private TicketDAO ticketDAO;

  /**
   * Cache en mémoire des tickets, avec l'ID du ticket comme clé.
   */
  private final Map<Integer, TicketDTO> ticketCache;

  /**
   * Initialise le repository avec une connexion à la base de données.
   * Charge également les tickets existants dans le cache.
   *
   */
  public TicketRepository() {
    this.ticketDAO = new TicketDAO();
    this.ticketCache = new ConcurrentHashMap<>();
    loadCache();
  }

  /**
   * Charge tous les tickets de la base dans le cache.
   */
  private void loadCache() {
    ticketDAO.getAllTickets().forEach(ticket -> ticketCache.put(ticket.ticket(), ticket));
  }

  /**
   * Ajoute un ticket et le met en cache s’il a bien été inséré.
   *
   * @param ticketDTO Ticket à ajouter.
   * @return L'ID généré pour ce ticket, ou -1 en cas d’échec.
   */
  public int addTicket(TicketDTO ticketDTO) {
    int generatedID = ticketDAO.addTicket(ticketDTO);

    if (generatedID != -1) {
      ticketCache.put(
              generatedID,
              new TicketDTO(
                      generatedID,
                      ticketDTO.userId(),
                      ticketDTO.date(),
                      ticketDTO.category(),
                      ticketDTO.price()));
    }
    return generatedID;
  }

  /**
   * Récupère tous les tickets liés à un utilisateur donné.
   * Met aussi à jour le cache avec ces tickets.
   *
   * @param userId ID de l’utilisateur.
   * @return Liste des tickets de l'utilisateur.
   */
  public List<TicketDTO> getAllTicketsFromUser(int userId) {
    List<TicketDTO> tickets = ticketDAO.getTicketsByUser(userId);
    tickets.forEach(ticket -> ticketCache.put(ticket.ticket(), ticket));

    return tickets;
  }
}
