package be.esi.prj.model.DAO;

import be.esi.prj.model.Category;
import be.esi.prj.model.DTO.TicketDTO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketDAOTest {
  private static Connection connection;
  private TicketDAO ticketDAO;

  @BeforeAll
  static void setupDatabase() throws SQLException {
    connection = DriverManager.getConnection("jdbc:sqlite::memory:");
    try (Statement stmt = connection.createStatement()) {
      stmt.execute("""
                    CREATE TABLE Users (
                        userId INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        salary INTEGER NOT NULL
                    );
                """);

      stmt.execute("""
                    CREATE TABLE Expenses (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        userId INTEGER NOT NULL,
                        date DATE NOT NULL,
                        category TEXT NOT NULL,
                        price INTEGER NOT NULL,
                        FOREIGN KEY (userId) REFERENCES Users(userId) ON DELETE CASCADE
                    );
                """);
    }
  }

  @BeforeEach
  void setup() throws SQLException {
    ticketDAO = new TicketDAO();
    try (Statement stmt = connection.createStatement()) {
      stmt.execute("INSERT INTO Users(name, salary) VALUES ('TestUser', 3000);");
      stmt.execute("INSERT INTO Expenses(userId, date, category, price) VALUES (1, '2024-06-01', 'FOOD', 20);");
    }
  }

  @AfterEach
  void cleanDatabase() throws SQLException {
    try (Statement stmt = connection.createStatement()) {
      stmt.execute("DELETE FROM Expenses;");
      stmt.execute("DELETE FROM Users;");
    }
  }

  @AfterAll
  static void closeDatabase() throws SQLException {
    connection.close();
  }

  @Test
  void testAddTicket() {
    TicketDTO newTicket = new TicketDTO(0, 1, LocalDate.now(), Category.HEALTH, 50.0);
    int id = ticketDAO.addTicket(newTicket);
    assertTrue(id > 0);
  }

  @Test
  void testGetTicketsByUser() {
    List<TicketDTO> tickets = ticketDAO.getTicketsByUser(1);
    assertFalse(tickets.isEmpty());
    assertEquals(1, tickets.get(0).userId());
  }
}