import Model.BmrModel;
import Model.Sexe;
import Model.StyleDeVie;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import View.LeftSide;
import View.RightSide;

public class ApplicationPrincipale extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Calcul du BMR");
        HBox root = new HBox(40);

        LeftSide leftSide = new LeftSide();
        RightSide rightSide = new RightSide();

        BmrModel bmrModel = new BmrModel();
        bmrModel.addPropertyChangeListener(rightSide);

        root.getChildren().addAll(leftSide, rightSide);

        Button calcul = new Button("Calcul du BMR");
        calcul.setPrefWidth(600);
        Button clear = new Button("Clear");
        clear.setPrefWidth(600);

        clear.setOnAction(event -> leftSide.clearFields());

        calcul.setOnAction(event -> {
            Sexe gender = leftSide.getGender();
            double weight = leftSide.getWeight();
            double height = leftSide.getUserHeight();
            int age = leftSide.getAge();
            String selectedActivityLevel = leftSide.getSelectedActivityLevel();

            // Vérifier si l'un des champs nécessaires est vide
            if (gender == null || weight <= 0 || height <= 0 || age <= 0 || selectedActivityLevel == null) {
                // Afficher un message d'erreur dans les zones de résultats
                rightSide.setBmrTextField("Veuillez remplir toutes les zones nécessaires.");
                rightSide.setCaloriesTextField("Veuillez remplir toutes les zones nécessaires.");
            } else {
                // Tous les champs sont remplis, procéder au calcul du BMR
                double calculatedBmr = bmrModel.calculateBmr(gender, weight, height, age);
                bmrModel.setBmr(calculatedBmr);
                rightSide.setBmrTextField(String.valueOf(calculatedBmr));
                double activityFactor = leftSide.getActivityFactor(selectedActivityLevel);
                double calculatedCalories = calculatedBmr * activityFactor;
                bmrModel.setCalories(calculatedCalories);
                rightSide.setCaloriesTextField(String.valueOf(calculatedCalories));
            }
        });

        VBox mainContainer = new VBox(10);
        mainContainer.getChildren().addAll(root, calcul, clear);
        mainContainer.setAlignment(Pos.TOP_CENTER);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);
        mainContainer.getChildren().add(0, menuBar);

        Scene scene = new Scene(mainContainer, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
