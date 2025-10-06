package be.esi.prj.model.DAO;

import be.esi.prj.model.DTO.UsersDTO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsersDAOTest {
  private static Connection connection;
  private UsersDAO usersDAO;

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
    }
  }

  @BeforeEach
  void setup() throws SQLException {
    usersDAO = new UsersDAO();
    try (Statement stmt = connection.createStatement()) {
      stmt.execute("INSERT INTO Users(name, salary) VALUES ('Alice', 3000), ('Bob', 4000);");
    }
  }

  @AfterEach
  void cleanDatabase() throws SQLException {
    try (Statement stmt = connection.createStatement()) {
      stmt.execute("DELETE FROM Users;");
    }
  }

  @AfterAll
  static void closeDatabase() throws SQLException {
    connection.close();
  }

  @Test
  void testAddAndRemoveUser() {
    UsersDTO user = new UsersDTO(0, "temp777", 3500.0);
    usersDAO.deleteUser(user.user());
    int id = usersDAO.addUser(user);
    assertTrue(id > 0);
    usersDAO.deleteUser(id);
  }

  @Test
  void testGetUserNotExists() {
    Optional<UsersDTO> result = usersDAO.getUser(999);
    assertFalse(result.isPresent());
  }
}