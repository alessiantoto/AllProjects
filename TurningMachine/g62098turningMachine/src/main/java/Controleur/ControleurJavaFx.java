package Controleur;

import Modele.Facade.Facade;
import Modele.LettreRobotValidatorMapping;
import Modele.Problemes.Probleme;
import Modele.Validateurs.Validateur;
import Application.ApplicationJavaFx;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import java.util.List;


/**
 * Contrôleur de l'interface utilisateur JavaFX qui gère les interactions entre l'interface utilisateur et le modèle.
 */
public class ControleurJavaFx {
    private final Facade facade;
    private final ApplicationJavaFx vue;

    /**
     * Constructeur de la classe ControleurJavaFx.
     *
     * @param vue L'objet ApplicationJavaFx utilisé pour interagir avec l'interface utilisateur JavaFX.
     */
    public ControleurJavaFx(ApplicationJavaFx vue) {
        this.vue = vue;
        this.facade = vue.getFacadeJeu();
    }


    /**
     * Charge les problèmes dans une liste déroulante (ComboBox) pour que l'utilisateur puisse les sélectionner.
     *
     * @param problemsComboBox La ComboBox dans laquelle les problèmes seront chargés.
     */
    public void loadProblemsIntoComboBox(ComboBox<String> problemsComboBox) {
        // Ajouter les problèmes à la liste déroulante
        for (int i = 1; i <= facade.totalProblemes(); i++) {
            Probleme probleme = facade.loadSpecificLine(i);
            String problemInfo = "Problème " + i + ": Difficulté - " + probleme.getDifficulte() + ", Facteur de chance - " + probleme.getDegreHasard();
            problemsComboBox.getItems().add(problemInfo);
        }
    }


    /**
     * Récupère la liste des validateurs disponibles pour le problème actuel.
     *
     * @return La liste des validateurs disponibles.
     */
    public List<Validateur> availableValidators(){
        return facade.getProblemeActuel().getValidateursAssocies();
    }

    /**
     * Récupère le problème correspondant au numéro spécifié pour être observé par un observateur.
     *
     * @param problemNumber Le numéro du problème à observer.
     * @return Le problème correspondant au numéro spécifié.
     */
    public Probleme getProblemOfLine(int problemNumber){
        return facade.loadSpecificLine(problemNumber);
    }


    /**
     * Démarre une nouvelle partie avec le problème spécifié.
     *
     * @param selectedProblem Le problème avec lequel la partie doit être démarrée.
     */
    public void startGame(Probleme selectedProblem){
        facade.startGame(selectedProblem);
    }


    /**
     * Crée les validateurs dans une ComboBox pour l'affichage.
     *
     * @param validatorsComboBox   La ComboBox dans laquelle les validateurs seront affichés.
     * @param availableValidators  La liste des validateurs disponibles.
     */
    public void createValidators(ComboBox<String> validatorsComboBox, List<Validateur> availableValidators) {
        // Logique métier associée à l'affichage des validateurs

        for (Validateur validateur : availableValidators) {
            validatorsComboBox.getItems().add(validateur.getDescription());
        }
    }


    /**
     * Permet au joueur de choisir un validateur pour le code spécifié dans le problème.
     *
     * @param selectedValidatorDescription La description du validateur choisi.
     * @param availableValidators         La liste des validateurs disponibles.
     * @param code                        Le code associé à la validation.
     * @param probleme                    Le problème pour lequel la validation est effectuée.
     */
    public void chooseValidator(String selectedValidatorDescription, List<Validateur> availableValidators, int code, Probleme probleme) {
        Validateur selectedValidator = findValidatorByDescription(selectedValidatorDescription, availableValidators);

        if (selectedValidator != null) {
            if(facade.getPlayerCode()!=0) {
                facade.chooseValidator(selectedValidator, code, probleme);
            }
            else {
                showAlert("Validation sans code", "Tu dois entrer un code pour utiliser les validateurs");
            }
        } else {
            showAlert("Validateur inexistant", "Aucun validateur correspondant trouvé.");
        }
    }

    /**
     * Recherche un validateur par sa description dans la liste des validateurs disponibles.
     *
     * @param description         La description du validateur à rechercher.
     * @param availableValidators La liste des validateurs disponibles.
     * @return Le validateur trouvé, ou null s'il n'y a aucune correspondance.
     */
    public Validateur findValidatorByDescription(String description, List<Validateur> availableValidators) {
        for (Validateur validateur : availableValidators) {
            if (validateur.getDescription().equals(description)) {
                return validateur;
            }
        }
        return null;
    }

    /**
     * Recherche la lettre associée à un validateur donné dans la liste de mappages entre les descriptions des validateurs
     * et les lettres correspondantes des robots.
     *
     * @param description                  La description du validateur dont on veut obtenir la lettre du robot associé.
     * @param lettreRobotValidatorMappings La liste des mappages entre les descriptions des validateurs et les lettres des robots.
     * @return La lettre du robot associée au validateur spécifié, ou null si aucune correspondance n'est trouvée.
     */
    public String findRobotLetterForValidator(String description, List<LettreRobotValidatorMapping> lettreRobotValidatorMappings) {
        for (LettreRobotValidatorMapping mapping : lettreRobotValidatorMappings) {
            if (mapping.getDescriptionValidator().equals(description)) {
                return mapping.getLettreRobot();
            }
        }
        return null;
    }

    /**
     * Affiche une boîte de dialogue d'alerte avec le titre et le message spécifiés.
     *
     * @param title   Le titre de l'alerte.
     * @param message Le message de l'alerte.
     */


    //dans vue, pas tous au bon endroit
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Obtient le score actuel du joueur.
     *
     * @return Le score actuel.
     */
    public int getScore(){
        return facade.getScore();
    }

    /**
     * Obtient le problème actuellement en cours dans le jeu.
     *
     * @return Le problème actuel.
     */
    public Probleme getProblemeActuel(){
        return facade.getProblemeActuel();
    }

    /**
     * Soumet le code du joueur pour évaluation.
     *
     * @param code Le code soumis par le joueur.
     */
    public void submitPlayerCode(int code){
        facade.submitPlayerCode(code);
    }

    /**
     * Passe au tour suivant dans le jeu.
     */
    public void nextRound(){
        facade.nextRound();
    }

    /**
     * Annule la dernière action effectuée dans le jeu.
     */
    public void undo(){
        facade.undo();
    }

    /**
     * Refait la dernière action annulée dans le jeu.
     */
    public void redo(){
        facade.redo();
    }

    /**
     * Abandonne le jeu en cours.
     */
    public void abandon(){
        facade.abandon();
    }


    /**
     * Obtient le nombre total de problèmes disponibles dans le jeu.
     *
     * @return Le nombre total de problèmes.
     */
    public int totalProblemes(){
        return facade.totalProblemes();
    }

    /**
     * Charge un problème spécifique en fonction de son numéro.
     *
     * @param numProbleme Le numéro du problème à charger.
     * @return Le problème chargé.
     */
    public Probleme loadSpecificLine(int numProbleme){
        return facade.loadSpecificLine(numProbleme);
    }

    /**
     * Obtient la liste des validateurs qui ont été vérifiés dans le jeu.
     *
     * @return La liste des validateurs vérifiés.
     */
    public List<Validateur> getValidateursVerifies(){
        return facade.getValidateursVerifies();
    }


    /**
     * Tente de deviner le code associé à un problème donné.
     *
     * @param code Le code proposé.
     * @param probleme Le problème auquel le code est associé.
     * @return True si la supposition est correcte, sinon False.
     */
    public boolean guessCode(int code, Probleme probleme){
        return facade.guessCode(code,probleme);
    }

    /**
     * Obtient le code du joueur actuel.
     *
     * @return Le code du joueur.
     */
    public int getPlayerCode(){
        return facade.getPlayerCode();
    }

}
