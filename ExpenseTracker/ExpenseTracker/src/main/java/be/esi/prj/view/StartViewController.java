package be.esi.prj.view;

import be.esi.prj.model.Connection.ConnectionManager;
import be.esi.prj.model.DTO.UsersDTO;
import be.esi.prj.model.Repository.UsersRepository;
import java.io.File;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


/**
 * Contrôleur pour la vue de démarrage. Gère la sélection ou la création d'utilisateurs,
 * la sélection d’un dossier, et le démarrage du processus principal.
 */
public class StartViewController {

  @FXML private TextField salaryField;
  @FXML private Label salaryLabel;
  @FXML private HBox salaryHBox;
  @FXML private TextField pathField;
  @FXML private Button browseButton;
  @FXML private Button startButton;
  @FXML private Label messageLabel;

  @FXML private ListView<UsersDTO> userListView;
  @FXML private TextField newUserField;
  @FXML private TextField newUserSalaryField;
  @FXML private Button addNewUserButton;


  private MainView mainView;

  private ObservableList<UsersDTO> existingUsersObservableList;
  private final UsersRepository usersRepository =
      new UsersRepository();

  /**
   * Initialise les composants de la vue : liste des utilisateurs, gestion des événements,
   * et état initial des éléments de l'interface.
   */
  @FXML
  public void initialize() {
    existingUsersObservableList = FXCollections.observableArrayList();
    userListView.setItems(existingUsersObservableList);

    userListView.setCellFactory(
        lv ->
            new javafx.scene.control.ListCell<UsersDTO>() {
              private final HBox cellBox = new HBox();
              private final Label nameLabel = new Label();
              private final Button deleteButton = new Button("🗑");

              {
                nameLabel.setMinWidth(200);
                nameLabel.setPrefWidth(200);
                nameLabel.setMaxWidth(Double.MAX_VALUE);
                nameLabel.setStyle("-fx-font-size: 14px;");

                deleteButton.setMinWidth(100);
                deleteButton.setPrefWidth(100);
                deleteButton.setOnAction(
                    e -> {
                      UsersDTO item = getItem();
                      if (item != null) {
                        handleDeleteUser(item);
                      }
                    });

                cellBox.setSpacing(20);
                cellBox.setStyle("-fx-alignment: CENTER_LEFT; -fx-padding: 5 10 5 10;");
                HBox.setHgrow(nameLabel, javafx.scene.layout.Priority.ALWAYS);
                cellBox.getChildren().addAll(nameLabel, deleteButton);
              }

              @Override
              protected void updateItem(UsersDTO user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                  setText(null);
                  setGraphic(null);
                } else {
                  nameLabel.setText(user.name());
                  setGraphic(cellBox);
                }
              }
            });

    populateUserListView();
    setupAddNewUserButtonAction();

    messageLabel.setVisible(false);
    messageLabel.setManaged(false);
  }

  private void handleDeleteUser(UsersDTO user) {
    if (user != null) {
      try {
        usersRepository.deleteUser(user.user());
        existingUsersObservableList.remove(user);
        populateUserListView();

        showMessage("Utilisateur '" + user.name() + "' supprimé avec succès !", false);
      } catch (Exception e) {
        System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        e.printStackTrace();
        showMessage("Erreur lors de la suppression de l'utilisateur.", true);
      }
    }
  }

  private void populateUserListView() {
    try {
      List<UsersDTO> users = usersRepository.getAllUsers();
      existingUsersObservableList.clear();
      if (users != null) {
        existingUsersObservableList.addAll(users);
      }
    } catch (Exception e) {
      System.err.println(
          "Erreur lors du chargement des utilisateurs pour la ListView : " + e.getMessage());
      e.printStackTrace();
      showMessage("Erreur lors du chargement des utilisateurs.", true);
    }
  }

  private void setupAddNewUserButtonAction() {
    addNewUserButton.setOnAction(event -> handleAddNewUserButtonAction());
  }

  /**
   * Gère l'action du bouton "Ajouter un utilisateur".
   * Valide les champs et insère un nouvel utilisateur dans la base.
   */
  @FXML
  private void handleAddNewUserButtonAction() {
    hideMessage();
    String newUserName = newUserField.getText();
    String newUserSalaryText = newUserSalaryField.getText();

    if (newUserName == null || newUserName.trim().isEmpty()) {
      showMessage("Veuillez entrer un nom pour le nouvel utilisateur.", true);
      return;
    }

    if (newUserSalaryText == null || newUserSalaryText.trim().isEmpty()) {
      showMessage("Veuillez entrer un salaire pour le nouvel utilisateur.", true);
      return;
    }

    double newUserSalary;
    try {
      newUserSalary = Double.parseDouble(newUserSalaryText);
      if (newUserSalary < 0) {
        showMessage(
            "Veuillez entrer un salaire valide (nombre positif ou "
                + "zéro) pour le nouvel utilisateur.",
            true);
        return;
      }
    } catch (NumberFormatException e) {
      showMessage(
          "Format de salaire invalide pour le nouvel utilisateur. " + "Veuillez entrer un nombre.",
          true);
      return;
    }

    String trimmedName = newUserName.trim();

    if (isInDataBase(trimmedName)) {
      showMessage(
          "Cet utilisateur existe déjà. Veuillez en choisir un autre "
              + "ou le sélectionner dans la liste.",
          true);
      return;
    }

    try {
      UsersDTO newUser = new UsersDTO(trimmedName, newUserSalary);
      int generatedId = usersRepository.addUser(newUser);

      if (generatedId != -1) {
        showMessage("Utilisateur '" + trimmedName + "' ajouté avec succès !", false);
        newUserField.clear();
        newUserSalaryField.clear();
        populateUserListView();
        userListView
            .getSelectionModel()
            .select(
                existingUsersObservableList.stream()
                    .filter(u -> u.user() == generatedId)
                    .findFirst()
                    .orElse(null));
      } else {
        showMessage("Erreur lors de l'ajout de l'utilisateur.", true);
      }
    } catch (Exception e) {
      System.err.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
      e.printStackTrace();
      showMessage("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage(), true);
    }
  }


  /**
   * Gère le bouton "Parcourir", pour sélectionner un dossier à analyser.
   */
  @FXML
  private void browseFolder() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    Stage stage = (Stage) pathField.getScene().getWindow();
    File selectedDirectory = directoryChooser.showDialog(stage);
    if (selectedDirectory != null) {
      pathField.setText(selectedDirectory.getAbsolutePath());
    }
    hideMessage();
  }

  /**
   * Lance l'affichage de la vue des résultats pour l'utilisateur sélectionné.
   */
  @FXML
  private void handleResultButtonAction() {
    try {
      mainView.showResultView(userListView.getSelectionModel().getSelectedItem().user());
    } catch (Exception e) {
      showMessage("Veuillez sélectionner un utilisateur dans la liste.", true);
    }
  }

  /**
   * Gère le bouton "Démarrer" : vérifie les champs requis, puis démarre le processus principal.
   */
  @FXML
  private void handleStartButtonAction() {
    hideMessage();

    UsersDTO selectedUser = userListView.getSelectionModel().getSelectedItem();
    String folderPath = pathField.getText();

    if (selectedUser == null) {
      showMessage("Veuillez sélectionner un utilisateur dans la liste.", true);
      return;
    }

    if (folderPath == null || folderPath.trim().isEmpty()) {
      showMessage("Veuillez sélectionner un dossier.", true);
      return;
    }

    showMessage("Validation réussie. Démarrage du traitement...", false);

    mainView.startScan(folderPath, selectedUser.user());
  }

  private void showMessage(String message, boolean isError) {
    if (messageLabel != null) {
      messageLabel.setText(message);
      if (isError) {
        messageLabel.setStyle("-fx-text-fill: red;");
      } else {
        messageLabel.setStyle("");
      }
      messageLabel.setVisible(true);
      messageLabel.setManaged(true);
    }
  }

  private void hideMessage() {
    if (messageLabel != null) {
      messageLabel.setVisible(false);
      messageLabel.setManaged(false);
      messageLabel.setText("");
    }
  }


  public void setMainView(MainView mainView) {
    this.mainView = mainView;
  }

  private boolean isInDataBase(String name) {
    return existingUsersObservableList.stream()
        .anyMatch(user -> user.name().equalsIgnoreCase(name));
  }
}
