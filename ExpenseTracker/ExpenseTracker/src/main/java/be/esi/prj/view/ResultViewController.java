package be.esi.prj.view;


import be.esi.prj.model.DTO.TicketDTO;
import be.esi.prj.model.Repository.TicketRepository;
import be.esi.prj.model.Repository.UsersRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


/**
 * Contrôleur de la vue des résultats financiers de l'utilisateur.
 * Affiche un résumé des dépenses par catégorie et le reste du salaire.
 */
public class ResultViewController {

  @FXML private VBox salaryExpenseContainer;
  @FXML private Label totalLabel;
  @FXML private Label restant;
  @FXML private BarChart<String, Number> barChart; // <- Bind this from FXML

  private final TicketRepository ticketRepository =
      new TicketRepository();
  private final UsersRepository usersRepository =
      new UsersRepository();
  private int userId;
  private Double salary;
  private MainView mainView;

  /**
   * Initialise la vue si un identifiant utilisateur valide est déjà défini.
   */
  @FXML
  public void initialize() {
    if (userId != -1) {
      fetchData();
    }
  }

  /**
   * Définit l'identifiant de l'utilisateur actif et la vue principale.
   *
   * @param userId   identifiant de l'utilisateur connecté
   * @param mainView instance de la vue principale
   */
  public void setAttribute(int userId, MainView mainView) {
    this.userId = userId;
    this.mainView = mainView;
    fetchData();
  }

  /**
   * Gère l'action du bouton pour revenir à l'écran d’accueil.
   */
  @FXML
  public void handleStartButtonAction() {
    mainView.showStartView();
  }

  /**
   * Récupère et traite les données utilisateur (tickets, salaires) pour mettre à jour la vue.
   */
  private void fetchData() {
    List<TicketDTO> tickets = ticketRepository.getAllTicketsFromUser(userId);
    salary = 1000.0; // Valeur temporaire
    tickets = tickets.stream().filter(ticket -> ticket.userId() == userId).toList();

    Map<String, Double> groupedByCategory =
        tickets.stream()
            .collect(
                Collectors.groupingBy(
                    ticket -> ticket.category().toString(),
                    Collectors.summingDouble(TicketDTO::price)));

    createBarChart(groupedByCategory);
    createSalaryExpenseView(groupedByCategory);

    double totalExpenses = tickets.stream().mapToDouble(TicketDTO::price).sum();
    totalLabel.setText(String.format("%.2f €", totalExpenses));
    restant.setText(String.format("%.2f €", salary - totalExpenses));
  }

  /**
   * Construit le graphique en barres représentant les dépenses par catégorie.
   *
   * @param groupedByCategory map des totaux par catégorie
   */
  private void createBarChart(Map<String, Double> groupedByCategory) {
    barChart.getData().clear();
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    groupedByCategory.forEach(
        (category, total) -> {
          series.getData().add(new XYChart.Data<>(category, total));
        });
    barChart.getData().add(series);
  }


  /**
   * Crée dynamiquement les libellés de dépenses et le salaire affichés dans l'interface.
   *
   * @param groupedByCategory map des dépenses par catégorie
   */
  private void createSalaryExpenseView(Map<String, Double> groupedByCategory) {
    salaryExpenseContainer.getChildren().clear();

    Label salaryLabel = new Label("Salaire : " + String.format("%.2f €", salary));
    salaryLabel.setStyle("-fx-font-weight: bold;");

    salaryExpenseContainer.getChildren().add(salaryLabel);

    groupedByCategory.forEach(
        (category, total) -> {
          Label label = new Label(category + " : " + String.format("%.2f €", total));
          salaryExpenseContainer.getChildren().add(label);
        });
  }
}
