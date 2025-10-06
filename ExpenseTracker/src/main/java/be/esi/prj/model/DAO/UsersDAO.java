package be.esi.prj.model.DAO;

import be.esi.prj.errorhandling.UsersException;
import be.esi.prj.model.Connection.ConnectionManager;
import be.esi.prj.model.DTO.UsersDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDAO implements IUsersDAO {
  private final Connection connection = ConnectionManager.getConnection();


  @Override
  public int addUser(UsersDTO userDTO) {
    String insertSql = "INSERT INTO Users(name, salary) VALUES (?, ?)";
    String idSql = "SELECT last_insert_rowid()";
    int generatedId = -1;

    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
      insertStmt.setString(1, userDTO.name());
      insertStmt.setDouble(2, userDTO.salary());
      insertStmt.executeUpdate();

      try (Statement idStmt = connection.createStatement();
          ResultSet rs = idStmt.executeQuery(idSql)) {
        if (rs.next()) {
          generatedId = rs.getInt(1);
        }
      }

    } catch (SQLException e) {
      throw new UsersException("❌ Erreur lors de l'ajout de l'utilisateur : " + e.getMessage(), e);
    }
    return generatedId;
  }

  @Override
  public Optional<UsersDTO> getUser(int userId) {
    String sql = "SELECT userId, name, salary FROM Users WHERE userId = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setInt(1, userId);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          UsersDTO usersDTO =
              new UsersDTO(rs.getInt("userId"), rs.getString("name"), rs.getDouble("salary"));
          return Optional.of(usersDTO);
        }
      }
    } catch (SQLException e) {
      throw new UsersException(
          "❌ Erreur lors de la récupération de l'utilisateur : " + e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public List<UsersDTO> getAllUsers() {
    List<UsersDTO> usersList = new ArrayList<>();
    String sql = "SELECT userId, name, salary FROM Users";

    try (Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        UsersDTO user =
            new UsersDTO(rs.getInt("userId"), rs.getString("name"), rs.getDouble("salary"));
        usersList.add(user);
      }

    } catch (SQLException e) {
      throw new UsersException(
          "❌ Erreur lors de la récupération de tous les utilisateurs : " + e.getMessage(), e);
    }
    return usersList;
  }

  @Override
  public void deleteUser(int userId) {
    String sql = "DELETE FROM Users WHERE userId = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setInt(1, userId);

      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new UsersException(
          "❌ Erreur lors de la suppression de l'utilisateur : " + e.getMessage(), e);
    }
  }
}
