package be.esi.prj.model.DAO;

import be.esi.prj.errorhandling.TicketException;
import be.esi.prj.model.Category;
import be.esi.prj.model.Connection.ConnectionManager;
import be.esi.prj.model.DTO.TicketDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TicketDAO implements ITicketDAO {

  private final Connection connection = ConnectionManager.getConnection();
  @Override
  public int addTicket(TicketDTO ticket) {
    String insertSql =
        "INSERT INTO Expenses(userId, date, category, price) " + "VALUES (?, ?, ?, ?)";
    String idSql = "SELECT last_insert_rowid()";
    int generatedId = -1;

    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
      insertStmt.setInt(1, ticket.userId());
      insertStmt.setDate(2, java.sql.Date.valueOf(ticket.date()));
      insertStmt.setString(3, ticket.category().toString());
      insertStmt.setDouble(4, ticket.price());
      insertStmt.executeUpdate();

      try (Statement idStmt = connection.createStatement();
          ResultSet rs = idStmt.executeQuery(idSql)) {
        if (rs.next()) {
          generatedId = rs.getInt(1);
        }
      }

    } catch (SQLException e) {
      throw new TicketException("❌ Erreur lors de l'ajout du ticket", e);
    }

    return generatedId;
  }


  @Override
  public List<TicketDTO> getAllTickets() {
    List<TicketDTO> tickets = new ArrayList<>();
    String sql = "SELECT id, userId, date, category, price FROM Expenses";

    try (Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        try {
          Date sqlDate = rs.getDate("date");
          String categoryStr = rs.getString("category");

          if (sqlDate == null || categoryStr == null) {
            System.err.println("⚠️ Ligne ignorée (date ou catégorie null)");
            continue;
          }

          Category category = Category.valueOf(categoryStr.toUpperCase());

          TicketDTO ticket =
              new TicketDTO(
                  rs.getInt("id"),
                  rs.getInt("userId"),
                  sqlDate.toLocalDate(),
                  category,
                  rs.getDouble("price"));

          tickets.add(ticket);

        } catch (IllegalArgumentException e) {
          System.err.println("⚠️ Catégorie inconnue ignorée : " + rs.getString("category"));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("❌ Erreur lors de la récupération des tickets", e);
    }

    return tickets;
  }

  @Override
  public List<TicketDTO> getTicketsByUser(int userId) {
    List<TicketDTO> tickets = new ArrayList<>();
    String sql = "SELECT id, userId, date, category, price FROM Expenses " + "WHERE userId = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setInt(1, userId);

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          tickets.add(
              new TicketDTO(
                  rs.getInt("id"),
                  rs.getInt("userId"),
                  rs.getDate("date").toLocalDate(),
                  Category.valueOf(rs.getString("category").toUpperCase()),
                  rs.getDouble("price")));
        }
      }

    } catch (SQLException e) {
      throw new TicketException("❌ Erreur lors de la récupération des tickets par utilisateur", e);
    }

    return tickets;
  }
}
