package be.esi.prj.model.DTO;

import be.esi.prj.model.Category;
import java.time.LocalDate;

/**
 * Représente un ticket dans le système.
 * Il contient les informations relatives à un ticket, telles que l'ID de l'utilisateur, la date, la catégorie, et le prix.
 * Ce DTO est utilisé pour transférer des informations liées à un ticket entre les différentes couches de l'application.
 */
public record TicketDTO(int ticket, int userId, LocalDate date, Category category, double price) {
  public TicketDTO(int userId, LocalDate date, Category category, double price) {
    this(0, userId, date, category, price);
  }
}
