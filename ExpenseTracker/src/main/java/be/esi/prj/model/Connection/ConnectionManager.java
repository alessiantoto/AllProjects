package be.esi.prj.model.Connection;

import be.esi.prj.errorhandling.TicketException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Cette classe gère la connexion à la base de données SQLite.
 * Elle crée une connexion à la base de données, et s'assure que les tables nécessaires sont créées si elles n'existent pas déjà.
 * Elle permet également de fermer la connexion proprement.
 */

public class ConnectionManager {
  private static final String DB_DIRECTORY = "external-data";
  private static final String DB_FILENAME = "expense.db";
  private static Connection connection;


  /**
   * Récupère la connexion à la base de données. Si la connexion n'existe pas encore,
   * elle est créée et les tables nécessaires sont créées.
   *
   * @return La connexion à la base de données.
   */
  public static Connection getConnection() {
    if (connection == null) {
      try {
        File directory = new File(DB_DIRECTORY);
        if (!directory.exists()) {
          directory.mkdir();
        }

        String dbPath = DB_DIRECTORY + File.separator + DB_FILENAME;
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        createTablesIfNeeded();

      } catch (SQLException e) {
        e.printStackTrace();
        throw new TicketException(
            "❌ Erreur de connexion à la base de données à l'emplacement: "
                + DB_DIRECTORY
                + File.separator
                + DB_FILENAME
                + " - "
                + e.getMessage(),
            e);
      }
    }
    return connection;
  }


  /**
   * Crée les tables nécessaires dans la base de données si elles n'existent pas déjà.
   * Les tables créées sont : `Users` et `Expenses`.
   */
  private static void createTablesIfNeeded() {
    try (Statement stmt = connection.createStatement()) {
      String createUserTableSQL =
          "CREATE TABLE IF NOT EXISTS Users ("
              + "userId INTEGER PRIMARY KEY AUTOINCREMENT, "
              + "name TEXT NOT NULL UNIQUE, "
              + "salary INTEGER NOT NULL);";
      stmt.executeUpdate(createUserTableSQL);

      String createExpensesTableSQL =
          "CREATE TABLE IF NOT EXISTS Expenses ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
              + "userId INTEGER NOT NULL, "
              + "date DATE NOT NULL, "
              + "category TEXT NOT NULL, "
              + "price INTEGER NOT NULL, "
              + "FOREIGN KEY (userId) REFERENCES Users(userId) ON DELETE CASCADE);";
      stmt.executeUpdate(createExpensesTableSQL);

    } catch (SQLException e) {
      e.printStackTrace();
      throw new TicketException(
          "❌ Erreur lors de la création des tables "
              + "dans la base de données : "
              + e.getMessage(),
          e);
    }
  }
}
