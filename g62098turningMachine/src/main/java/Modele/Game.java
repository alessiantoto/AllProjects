package Modele;

import Modele.Commandes.Command;
import Modele.Problemes.Probleme;
import Modele.Validateurs.Validateur;

import java.util.*;

/**
 * La classe Jeu représente le modèle du jeu.
 * Elle étend la classe Observable pour permettre la mise en œuvre du modèle de conception Observer.
 */
public class Game{
    private Probleme problemeActuel;
    private int codeJoueur;
    private List<Validateur> validateursVerifies;
    // Liste pour stocker temporairement les validateurs choisis
    private List<Validateur> validateursChoisisTemp = new ArrayList<>();

    //boolean resultat;
    private int score;

    //private String ancienCodeJoueur;
    //private List<Validateur> anciensValidateursChoisis;
    // Initialisation de validatorStates
    private Map<Validateur, Boolean> validatorStates = new HashMap<>();
    //Map<Validateur, Boolean> previousValidatorStates;
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    /**
     * Constructeur par défaut de la classe Jeu.
     * Initialise les listes et les piles nécessaires.
     */
    public Game(){
        this.validateursVerifies = new ArrayList<>();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }


    /**
     * Méthode pour démarrer une nouvelle partie.
     * @param probleme Le problème à résoudre dans la nouvelle partie.
     */
    public void startGame(Probleme probleme){
        this.problemeActuel = probleme;
        this.codeJoueur = 0;
        this.validateursVerifies.clear();
        this.score = 0;

    }

    /**
     * Méthode pour afficher le message d'erreur.
     * @param message Le message d'erreur.
     */
    private void displayError(String message) {
        System.out.println(message);
    }

    /**
     * Méthode pour soumettre le code du joueur.
     * @param code Le code soumis par le joueur.
     */
    public void submitPlayerCode(int code) {
        if (validateursVerifies.isEmpty()) {
            if (isValidCode(code)) {
                this.codeJoueur = code;
            } else {
                displayError("Code invalide. Chaque chiffre doit être entre 1 et 5.");
            }
        } else {
            displayError("Action invalide. Vous ne pouvez pas soumettre un code après avoir sélectionné des validateurs.");
        }
    }



    /**
     * Ajoute un validateur sélectionné par le joueur.
     * Si le nombre maximum de validateurs n'est pas atteint, le validateur est ajouté à la liste des validateurs vérifiés.
     * Le validateur est également retiré de la liste des validateurs associés au problème en cours,
     * et son état précédent est sauvegardé pour une éventuelle opération d'annulation (undo).
     *
     * @param validateur Le validateur à ajouter.
     * @param code       Le code soumis par le joueur.
     * @param probleme   Le problème en cours.
     */

    public void chooseValidateur(Validateur validateur, int code, Probleme probleme){
        if (validateursVerifies.size() < 3) {
            validateursVerifies.add(validateur);

            Probleme problemeEnCours = getProblemeActuel();
            if (problemeEnCours != null) {
                List<Validateur> validateursProbleme = problemeEnCours.getValidateursAssocies();
                validateursProbleme.remove(validateur);
                // Ajouter le validateur à la liste temporaire pour undo
                validateursChoisisTemp.add(validateur);
                //valider le validateur
                validateur.valider(code, probleme); //on valide le validateur choisi
                // Mise à jour de la map validatorStates avec le nouvel état du validateur
                validatorStates.put(validateur, validateur.validationStatus()); //on actualise la map avec les etats des validateurs

            }

        }
        else {
            // Affichez un message ou effectuez une action appropriée si le nombre maximum de validateurs est atteint
            System.out.println("Maximum 3 validateurs");
        }
    }

    /**
     * Méthode pour annuler le choix du dernier validateur.
     * Annule également les modifications associées.
     */
    public void undoChooseValidateur() {
        if (!validateursChoisisTemp.isEmpty()) {
            Validateur dernierValidateurChoisi = validateursChoisisTemp.remove(validateursChoisisTemp.size() - 1);
            validateursVerifies.remove(dernierValidateurChoisi);

            dernierValidateurChoisi.setStatus(null);
            Probleme problemeEnCours = getProblemeActuel();
            List<Validateur> validateursProbleme = problemeEnCours.getValidateursAssocies();
            validateursProbleme.add(dernierValidateurChoisi);

            // Supprimer le validateur de la map validatorStates car il n'est plus choisi
            validatorStates.remove(dernierValidateurChoisi);
        }
    }


    /**
     * Sauvegarde l'état actuel des validateurs spécifiés dans une map.
     * Cette méthode est utilisée pour conserver l'état précédent des validateurs avant une opération d'annulation (undo).
     *
     * @param validateurs La liste des validateurs dont l'état doit être sauvegardé.
     * @return Une map associant chaque validateur à son état actuel (true si validé, false sinon).
     */
    public Map<Validateur, Boolean> saveValidatorStates(List<Validateur> validateurs) {
        validatorStates = new HashMap<>();
        for (Validateur validator : validateurs) {
            validatorStates.put(validator, validator.validationStatus());
        }
        return validatorStates;
    }

    /**
     * Méthode pour passer à la prochaine manche du jeu.
     * Réinitialise le code du joueur, les validateurs, et incrémente le score.
     */
    public void nextRound(){
        this.setScore(this.score + 1);
        // Réinitialise le code joueur
        this.codeJoueur = 0;

        Probleme problemeEnCours = getProblemeActuel();
        List<Validateur> validateursProbleme = problemeEnCours.getValidateursAssocies();
        validateursProbleme.addAll(validateursVerifies);

        for(Validateur validator : validateursProbleme){
            validator.setStatus(null);
        }
        // Vide la liste des validateurs vérifiés
        this.validateursVerifies.clear();
    }

    /*
    public void nextRoundUndo(){
        //stocker dans la commande pas dans game
        this.codeJoueur = ancienCodeJoueur;
        // Restaurer les validateurs vérifiés à leur état précédent
        this.validateursVerifies.clear();
        this.validateursVerifies.addAll(anciensValidateursChoisis);

        Probleme problemeEnCours = getProblemeActuel();
        List<Validateur> validateursProbleme = problemeEnCours.getValidateursAssocies();
        // Restaurer les validateurs vérifiés à leur état précédent en utilisant la map
        for (Validateur validator : validateursProbleme) {
            Boolean previousStatus = previousValidatorStates.get(validator);
            if (previousStatus != null) {
                validator.setStatus(previousStatus);
            }
        }
        validateursProbleme.removeAll(validateursVerifies);
        this.setScore(this.score - 1);
        notifyObservers();
    }


     */

    /**
     * Méthode pour vérifier le code du joueur par rapport au code secret du problème.
     * @param code     Le code soumis par le joueur.
     * @param probleme Le problème en cours.
     */
    public boolean checkCode(int code, Probleme probleme) {
        if (isValidCode(code)) {
            // Comparer les entiers avec l'opérateur '=='
            boolean resultat = (code == probleme.getCodeSecret());

            return resultat;
        }
        return false;
    }



    /**
     * Vérifie si le code spécifié est valide.
     * Un code est considéré comme valide s'il est composé de 3 chiffres, et chaque chiffre est entre 1 et 5 inclusivement.
     *
     * @param code Le code à vérifier.
     * @return true si le code est valide, false sinon.
     */
    public boolean isValidCode(int code) {
        // Convertir l'entier en chaîne de caractères
        String codeStr = Integer.toString(code);

        // Vérifier si la chaîne a exactement 3 caractères
        if (codeStr.length() != 3) {
            return false;
        }

        // Vérifier que chaque caractère est un chiffre entre '1' et '5'
        for (char c : codeStr.toCharArray()) {
            if (c < '1' || c > '5') {
                return false;
            }
        }

        return true;
    }


    /**
     * Méthode pour quitter le jeu.
     * Termine l'exécution du programme.
     */
    public void abandon() {
        System.exit(0);
        // Notifier les observateurs de l'abandon
    }


    /**
     * Exécute une commande spécifiée et l'ajoute à la pile d'annulation (undo stack).
     * Cette méthode prend une instance de la classe Command en paramètre, exécute la commande,
     * ajoute la commande à la pile d'annulation (undo stack), et vide la pile de refaire (redo stack).
     * Enfin, elle notifie les observateurs enregistrés de la modification de l'état de l'objet.
     *
     * @param command La commande à exécuter et à ajouter à la pile d'annulation.
     */
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);    //on met dans undo la commande pour pouvoir faire undo dessu
        redoStack.clear(); // car on ne peut pas faire redo sur un commande annulé
    }

    /**
     * Méthode pour annuler la dernière commande.
     * Dépile la dernière commande, annule son effet, et la place dans la pile redoStack.
     */
    public void undo() {
        if (!undoStack.isEmpty()) { //verifie que il y a de commande sur laquelle on peut faire undo
            Command lastCommand = undoStack.pop();
            lastCommand.undo();
            redoStack.push(lastCommand);
        }
        else {
            displayError("Erreur: Il n'y a rien à annuler.");
        }
    }


    /**
     * Méthode pour refaire la dernière commande annulée.
     * Dépile la dernière commande de redoStack, exécute son effet, et la place dans la pile undoStack.
     */
    public void redo() {
        if (!redoStack.isEmpty()) { //verifie qu'il y a de commande sur laquelle on peut faire redo
            Command commandToRedo = redoStack.pop();
            commandToRedo.execute();
            undoStack.push(commandToRedo);
        }else {
            displayError("Erreur: Il n'y a rien à refaire.");
        }
    }


    /**
     * Obtient le code du joueur actuel.
     *
     * @return Le code du joueur.
     */
    public int getCodeJoueur() {
        return this.codeJoueur;
    }

    /**
     * Obtient la liste des validateurs qui ont été vérifiés dans le jeu.
     *
     * @return La liste des validateurs vérifiés.
     */
    public List<Validateur> getValidateursVerifies() {
        return validateursVerifies;
    }

    /**
     * Obtient le score actuel dans le jeu.
     *
     * @return Le score actuel.
     */
    public int getScore() {
        return score;
    }

    /**
     * Obtient le problème actuellement en cours dans le jeu.
     *
     * @return Le problème actuel.
     */
    public Probleme getProblemeActuel() {
        return problemeActuel;
    }

    /**
     * Modifie le score actuel du joueur.
     *
     * @param score Le nouveau score à définir.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Obtient la pile d'annulation (undoStack) du jeu.
     *
     * @return La pile undoStack.
     */
    public Stack<Command> getUndoStack() {
        return undoStack;
    }


    /**
     * Obtient la pile de rétablissement (redoStack) du jeu.
     *
     * @return La pile redoStack.
     */
    public Stack<Command> getRedoStack() {
        return redoStack;
    }

    /**
     * Définit le code du joueur actuel.
     * Cette méthode permet de mettre à jour le code du joueur avec la valeur fournie.
     *
     * @param codeJoueur Le nouveau code du joueur à définir.
     */
    public void setCodeJoueur(int codeJoueur) {
        this.codeJoueur = codeJoueur;
    }


    /**
     * Définit la liste des validateurs vérifiés.
     * Cette méthode remplace la liste actuelle des validateurs vérifiés par la liste fournie.
     *
     * @param validateursVerifies La nouvelle liste de validateurs vérifiés.
     */
    public void setValidateursVerifies(List<Validateur> validateursVerifies) {
        this.validateursVerifies = validateursVerifies;
    }

    /**
     * Restaure les états précédents des validateurs à partir de la map fournie.
     * Cette méthode met à jour chaque validateur dans la map avec l'état précédemment sauvegardé.
     *
     * @param previousValidatorStates Une map associant chaque validateur à son état précédent
     *                                (true si validé, false sinon).
     */
    public void restoreValidatorStates(Map<Validateur, Boolean> previousValidatorStates) {
        for (Map.Entry<Validateur, Boolean> entry : previousValidatorStates.entrySet()) {
            Validateur validator = entry.getKey();
            Boolean status = entry.getValue();
            validator.setStatus(status);
        }
    }



}
