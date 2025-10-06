package Application;

import Controleur.ControleurJavaFx;
import Modele.Facade.FacadeImpl;
import Modele.LettreRobotValidatorMapping;
import Modele.Problemes.Probleme;
import Modele.Validateurs.Factory;
import Modele.Validateurs.Validateur;
import Vue.JavaFxVueComposants.RobotWithValidator;
import Vue.JavaFxVueComposants.StartScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Modele.Facade.Facade;
import javafx.util.Duration;

import java.util.*;

public class ApplicationJavaFx extends Application implements Observer {    //utiliser javabeans
    private Facade facade = new FacadeImpl(new Factory());
    private int selectedProblemNumber;
    private Label scoreLabel;
    private Label problemLabel;
    private Label nbrValidateursUtilises;
    private VBox gameBoardLayout;
    private HBox buttonsLayout;
    private HBox robotHeadersLayout;
    private List<VBox> robotLayouts = new ArrayList<>();
    private ControleurJavaFx controleur;
    private Map<String, Boolean> validateurEtatMap = new HashMap<>();

    // Créez la Map pour associer la lettre du robot au validateur
    private List<LettreRobotValidatorMapping> lettreRobotValidatorMappings = new ArrayList<>();

    private RobotWithValidator robotWithValidator;
    /**
     * Méthode principale de l'application JavaFX qui démarre l'interface utilisateur.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans cette application).
     */

    /**
     * Initialise et configure l'interface utilisateur JavaFX.
     *
     * @param primaryStage Le stage principal de l'application.
     */

    @Override
    public void start(Stage primaryStage) {
        this.controleur = new ControleurJavaFx(this);  // Instantiate your ControleurJavaFx here

        primaryStage.setTitle("Game Title");
        StartScreen startScreen = new StartScreen();
        startScreen.initializeUI();


        // Create JavaFX components for the game board
        problemLabel = new Label("Problème:" + selectedProblemNumber);
        scoreLabel = new Label("Manches:  "+ controleur.getScore());
        nbrValidateursUtilises = new Label("Validateurs utilises: ");


        // Créez une nouvelle instance de HBox pour robotHeadersLayout
        TextField codeTextField = new TextField();
        codeTextField.setMaxWidth(90);
        Button enterCodeButton = new Button("Entrer le code");

        Button chooseValidatorButton = new Button("Choisir un validateur");

        Button nextRoundButton = new Button("Passer à la manche suivante");

        Button guessCodeButton = new Button("Deviner le code");

        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");

        Button abandonButton = new Button("Abandonner");

        // Créer un HBox pour les boutons horizontaux
        buttonsLayout = new HBox(10);
        buttonsLayout.getChildren().addAll(
                chooseValidatorButton,
                nextRoundButton,
                guessCodeButton,
                undoButton,
                redoButton,
                abandonButton
        );


        // Layout for the game board
        gameBoardLayout = new VBox(10);
        gameBoardLayout.getChildren().addAll(
                problemLabel,
                scoreLabel,
                nbrValidateursUtilises,
                codeTextField,
                enterCodeButton,
                buttonsLayout
        );


        // StackPane to overlay main menu and game board layouts
        StackPane root = new StackPane();   //empile ses éléments graphiques les uns sur les autres, et le dernier élément ajouté est affiché au premier plan.
        root.getChildren().add(startScreen.getMainMenuLayout());
        facade.addObserver(this);
        robotHeadersLayout = new HBox();
        robotWithValidator = new RobotWithValidator(controleur, facade, robotHeadersLayout, robotLayouts, lettreRobotValidatorMappings,validateurEtatMap);

        // Set action handlers for buttons in the main menu
        startScreen.chooseProblemRadioButton.setOnAction(e -> {
            chooseSpecificProblem(primaryStage);

            // After making the choice, show the game board layout
            root.getChildren().clear();
            root.getChildren().add(gameBoardLayout); //pas necessaire je pense
        });

        startScreen.randomProblemRadioButton.setOnAction(e -> {
            chooseRandomProblem(primaryStage);

            root.getChildren().clear();
            root.getChildren().add(gameBoardLayout);

            // Create robot headers based on the selected problem
            Probleme selectedProblem = controleur.getProblemeActuel();
            if (selectedProblem != null) {
                // Update the contents of the existing robotWithValidator instance
                robotHeadersLayout = robotWithValidator.createRobotHeaders("/TuringMachine-assets/robot/", selectedProblem.getValidateursAssocies());
                gameBoardLayout.getChildren().add(1, robotHeadersLayout);
            }
        });

        // Set action handlers for buttons
        enterCodeButton.setOnAction(e -> {
            // Handle entering code
            String code = codeTextField.getText();

            // Vérifier la validité du code
            if (isValidCode(code)) {
                controleur.submitPlayerCode(Integer.parseInt(code));
                // Effacer le texte après avoir soumis le code
                codeTextField.clear();
            } else {
                // Display an error message in JavaFX instead of printing to the console
                controleur.showAlert("Code invalide", "Assurez-vous d'entrer un code de 3 chiffres.");
            }
        });

        chooseValidatorButton.setOnAction(e -> {
            // Récupérez la liste des validateurs disponibles (vous devez définir cette liste)
            List<Validateur> availableValidators = controleur.availableValidators();
            // Appelez la méthode pour afficher la fenêtre de dialogue des validateurs
            showAvailableValidators(availableValidators);
        });


        nextRoundButton.setOnAction(e -> {
            controleur.nextRound();
        });

        guessCodeButton.setOnAction(e -> {
            // Créer un dialogue de saisie de texte
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Devinez le code");
            dialog.setHeaderText("Entrez le code que vous souhaitez deviner :");
            dialog.setContentText("Code:");

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            Optional<String> result = dialog.showAndWait();

            // Traiter la réponse de l'utilisateur
            result.ifPresent(code -> {
                // Vérifier la validité du code
                if (isValidCode(code)) {
                    boolean resultat = controleur.guessCode(Integer.parseInt(code), controleur.getProblemeActuel());
                    // Afficher une popup en fonction du résultat
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Résultat de la tentative");
                    alert.setHeaderText(null);
                    if (resultat) {
                        alert.setContentText("Félicitations, vous avez gagné !");
                    }
                    else {
                        alert.setContentText("Désolé, vous avez perdu. Mieux chance la prochaine fois !");
                    }

                    alert.show();
                    // Fermer l'application après 3 secondes
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                        alert.close();// Fermer la fenêtre d'alerte après 1 secondes
                        Platform.exit();        //pour faire taches nettoyage et pas brutal
                    }));
                    timeline.setCycleCount(1);  //execute une seule fois
                    timeline.play();

                } else {
                    // Afficher un message d'erreur si le code n'est pas valide
                    controleur.showAlert("Code invalide", "Assurez-vous d'entrer un code de 3 chiffres entre 1 et 5 compris");
                }
            });
        });

        undoButton.setOnAction(e -> {
            controleur.undo();
        });

        redoButton.setOnAction(e -> {
            controleur.redo();
        });

        abandonButton.setOnAction(e -> {
            controleur.abandon();
        });

        // Set up the scene
        Scene scene = new Scene(root, 1000, 600);
        // Définir le style CSS pour la scène
        scene.getRoot().setStyle("-fx-background-color: darkgreen;");
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    /**
     * Vérifie si le code est composé de trois chiffres, chacun étant dans la plage de 1 à 5 inclus.
     *
     * @param code Le code à vérifier.
     * @return true si le code est valide, false sinon.
     */
    private boolean isValidCode(String code) {
        return code.matches("[1-5]{3}");
    }

    /**
     * Choisi un problème de manière aléatoire parmi les problèmes disponibles.
     * Génère un nombre aléatoire entre 1 et le nombre total de problèmes inclus,
     * puis charge le problème correspondant et démarre le jeu avec ce problème.
     *
     * @param primaryStage Le stage principal de l'application JavaFX.
     */
    private void chooseRandomProblem(Stage primaryStage) {
        // Générer un nombre aléatoire entre 1 et 22 inclus
        int randomProblemNumber = new Random().nextInt(controleur.totalProblemes()) + 1;

        selectedProblemNumber = randomProblemNumber;

        // Charger le problème correspondant
        Probleme randomProblem = controleur.loadSpecificLine(randomProblemNumber);

        // Démarrer le jeu avec le problème choisi
        controleur.startGame(randomProblem);

    }

    /**
     * Permet à l'utilisateur de choisir un problème spécifique à partir d'une liste déroulante.
     * Cette méthode crée une nouvelle fenêtre (Stage) contenant une liste déroulante de problèmes,
     * une étiquette affichant les informations du problème sélectionné et un bouton pour démarrer le jeu.
     * L'utilisateur peut sélectionner un problème dans la liste, affichant ainsi les informations
     * correspondantes dans l'étiquette. En cliquant sur le bouton "Démarrer le jeu", le jeu est lancé
     * avec le problème sélectionné.
     *
     * @param primaryStage Le stage principal de l'application JavaFX.
     */
    private void chooseSpecificProblem(Stage primaryStage) {
        // Créer une nouvelle fenêtre (Stage) pour afficher la liste des problèmes
        Stage problemsStage = new Stage();
        problemsStage.setTitle("Choisir un problème");

        // Créer et configurer un conteneur VBox pour organiser les éléments
        VBox vbox = new VBox(10); // 10 pixels de séparation entre les éléments
        vbox.setAlignment(Pos.CENTER);

        // Créer une étiquette pour afficher les informations du problème sélectionné
        Label selectedProblemLabel = new Label("Aucun problème sélectionné");

        // Créer une liste déroulante (ComboBox) pour afficher les problèmes
        ComboBox<String> problemsComboBox = new ComboBox<>();

        // Ajouter les problèmes à la liste déroulante

        controleur.loadProblemsIntoComboBox(problemsComboBox);


        // Ajouter un gestionnaire d'événements pour la sélection dans la liste déroulante
        problemsComboBox.setOnAction(event -> {
            String selectedProblemInfo = problemsComboBox.getValue();
            selectedProblemLabel.setText("Problème sélectionné:\n" + selectedProblemInfo);
        });

        // Créer un bouton pour démarrer le jeu avec le problème sélectionné
        Button startGameButton = new Button("Démarrer le jeu");
        startGameButton.setOnAction(event -> {

            String selectedProblemInfo = problemsComboBox.getValue();
            // Extraire le numéro du problème à partir de l'information du problème
            int problemNumber = Integer.parseInt(selectedProblemInfo.split(":")[0].replaceAll("[^0-9]", ""));
            selectedProblemNumber = problemNumber;
            Probleme selectedProblem = controleur.getProblemOfLine(problemNumber);
            controleur.startGame(selectedProblem);

            if (selectedProblem != null) {
                // Create robot headers based on the selected problem
                robotHeadersLayout = robotWithValidator.createRobotHeaders("/TuringMachine-assets/robot/", selectedProblem.getValidateursAssocies());
                // Add robot headers to the game board layout
                gameBoardLayout.getChildren().add(1, robotHeadersLayout); // Add at index 1 to position it after the problemLabel
            }
            else {
                controleur.showAlert("Probleme null", "Il y a une erreur lors du lancement du jeu avec le numero du probleme specifie");
            }
            // Fermer la fenêtre des problèmes après avoir choisi un problème
            problemsStage.close();
        });

        // Ajouter les éléments au conteneur VBox
        vbox.getChildren().addAll(problemsComboBox, selectedProblemLabel, startGameButton);

        // Créer une nouvelle scène avec le conteneur VBox
        Scene problemsScene = new Scene(vbox, 400, 300);

        // Ajouter la scène à la fenêtre des problèmes
        problemsStage.setScene(problemsScene);

        // Afficher la fenêtre des problèmes
        problemsStage.show();

    }


    /**
     * Affiche une fenêtre permettant à l'utilisateur de choisir un validateur parmi une liste de validateurs disponibles.
     * Cette méthode crée une nouvelle fenêtre (Stage) contenant une liste déroulante de validateurs et un bouton
     * pour confirmer la sélection. L'utilisateur peut choisir un validateur dans la liste et cliquer sur le bouton
     * "Confirmer" pour ajouter le validateur sélectionné. Le validateur ajouté est ensuite validé en appelant les
     * méthodes appropriées du contrôleur. Le processus de validation peut être répété jusqu'à ce que trois validateurs
     * soient validés.
     *
     * @param availableValidators La liste des validateurs disponibles parmi lesquels l'utilisateur peut choisir.
     */

    private void showAvailableValidators(List<Validateur> availableValidators) {
        // Créez une nouvelle fenêtre (Stage) pour afficher les validateurs disponibles
        Stage validatorsStage = new Stage();
        validatorsStage.setTitle("Choisir un validateur");

        // Créez et configurez un conteneur VBox pour organiser les éléments
        VBox vbox = new VBox(10); // 10 pixels de séparation entre les éléments
        vbox.setAlignment(Pos.CENTER);

        // Créez une liste déroulante (ComboBox) pour afficher les validateurs
        ComboBox<String> validatorsComboBox = new ComboBox<>();

        controleur.createValidators(validatorsComboBox, availableValidators);


        // Créez un bouton pour ajouter le validateur sélectionné
        Button addValidatorButton = new Button("Confirmer");
        addValidatorButton.setOnAction(event -> {
            if(controleur.getValidateursVerifies().size()<3) {
                String selectedValidatorDescription = validatorsComboBox.getValue();
                if (selectedValidatorDescription != null) {
                    // Trouver le validateur correspondant à la description sélectionnée
                    Validateur selectedValidator = controleur.findValidatorByDescription(selectedValidatorDescription, availableValidators);

                    // Appel à la méthode du contrôleur pour valider le validateur
                    controleur.chooseValidator(selectedValidatorDescription, availableValidators, controleur.getPlayerCode(), controleur.getProblemeActuel());

                    // Appel à la méthode du contrôleur pour valider le validateur

                    boolean validationStatus = selectedValidator.validationStatus();
                    // Ajouter la description et l'état initial (par exemple, false) dans la Map
                    validateurEtatMap.put(selectedValidatorDescription, validationStatus);

                    // Mettez à jour le label correspondant en bas du validateur
                    updateValidatorLabel(selectedValidator, validationStatus);

                }

            }
            else{
                controleur.showAlert("Max de validateurs choisis", "tu peux choisir max 3 validateurs par manche");
            }

            // Fermez la fenêtre des validateurs après avoir choisi un validateur
            validatorsStage.close();
        });

        // Ajoutez les éléments au conteneur VBox
        vbox.getChildren().addAll(validatorsComboBox, addValidatorButton);

        // Créez une nouvelle scène avec le conteneur VBox
        Scene validatorsScene = new Scene(vbox, 400, 300);

        // Ajoutez la scène à la fenêtre des validateurs
        validatorsStage.setScene(validatorsScene);

        // Affichez la fenêtre des validateurs
        validatorsStage.show();
    }

    @Override
    public void update(Observable o, Object arg) {

        // Mettre à jour l'interface utilisateur en fonction des changements dans la façade
        int newScore = facade.getScore();
        // Actualiser les composants d'interface utilisateur, par exemple les labels
        scoreLabel.setText("Score: " + newScore);
        problemLabel.setText("Problème: " + selectedProblemNumber);

        // Mettez à jour le label pour afficher le nombre de validateurs vérifiés
        int nbValidateursVerifies = controleur.getValidateursVerifies().size();
        nbrValidateursUtilises.setText("Validateurs utilisés: " + nbValidateursVerifies);

        // Mise à jour de l'état des validateurs dans l'interface utilisateur
        updateAllValidatorsLabel();
    }

    /**
     * Met à jour tous les labels des validateurs dans l'interface utilisateur pour refléter l'état actuel de chaque validateur
     * associé au problème en cours. Cette méthode parcourt la liste des validateurs, vérifie leur état, et met à jour les labels
     * correspondants dans l'interface utilisateur.
     */
    private void updateAllValidatorsLabel() {
        //souci encapsulation
        Probleme selectedProblem = controleur.getProblemeActuel();

        if (selectedProblem != null) {
            List<Validateur> validateurs = selectedProblem.getValidateursAssocies();

            for (Validateur validator : validateurs) {
                // Obtenez la valeur renvoyée par validationStatus()
                Boolean validationStatus = validator.validationStatus();
                // Appelez updateValidatorLabel() en passant la valeur actuelle (peut être null)
                updateValidatorLabel(validator, validationStatus);
            }
        }
    }

    /**
     * Met à jour le label d'un validateur dans l'interface utilisateur avec le nouvel état spécifié.
     * Cette méthode met à jour le label associé à un validateur dans l'interface utilisateur, en fonction de son nouvel état.
     *
     * @param validator Le validateur dont le label doit être mis à jour.
     * @param newState  Le nouvel état du validateur.
     */
    private void updateValidatorLabel(Validateur validator, Boolean newState) {
        String descriptionValidateur = validator.getDescription();
        // Trouvez la lettre du robot associée au validateur dans la Map
        String selectedRobotLetter =controleur.findRobotLetterForValidator(descriptionValidateur, lettreRobotValidatorMappings);
        validateurEtatMap.put(descriptionValidateur, newState);

        // Recherchez le VBox correspondant à la lettre du robot dans la Map
        VBox robotLayout = findRobotLayoutForLetter(selectedRobotLetter);

        // Mettez à jour le Label correspondant dans le VBox
        if (robotLayout != null) {
            Label resultLabel = (Label) robotLayout.getChildren().stream()  //Utilise un flux (stream) pour parcourir les nœuds enfants du VBox
                    .filter(node -> node instanceof Label && node.getStyle().contains("bold"))
                    .findFirst() //Il filtre les nœuds pour récupérer le premier qui est une instance de Label et qui a le style "bold" (gras).
                    .orElse(null);

            if (resultLabel != null) {
                if (newState == null) {
                    resultLabel.setText("État : non vérifié");
                } else {
                    resultLabel.setText("État : " + (newState ? "TRUE" : "FALSE"));
                }
            }
        }
    }

    /**
     * Recherche et renvoie le VBox correspondant à la lettre du robot dans la liste des layouts de robot.
     * Cette méthode parcourt la liste des layouts de robot pour trouver le VBox associé à la lettre du robot.
     * @param robotLetter La lettre du robot dont on cherche le VBox associé.
     * @return Le VBox correspondant à la lettre du robot, ou null si aucun n'est trouvé.
     */
    private VBox findRobotLayoutForLetter(String robotLetter) {
        return robotLayouts.stream()
                .filter(layout -> layout.getChildren().stream() //Pour chaque layout, crée un flux à partir de la liste de ses nœuds enfants.
                        .anyMatch(node -> node instanceof Label && ((Label) node).getText().equals(robotLetter)))
                // Vérifie si au moins un nœud enfant est une instance de Label et si le texte de ce label correspond à la lettre de robot spécifiée
                .findFirst()
                .orElse(null);
    }

    public Facade getFacadeJeu() {
        return facade;
    }
}
