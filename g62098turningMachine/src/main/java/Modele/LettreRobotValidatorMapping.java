package Modele;
/**
 * La classe LettreRobotValidatorMapping représente une association entre une lettre de robot et une description de validateur.
 * Elle est utilisée pour mapper les lettres associées aux validateurs dans le jeu.
 */
public class LettreRobotValidatorMapping {
    private String lettreRobot; //Un attribut privé qui stocke la lettre associée au validateur.
    private String descriptionValidator;  //

    /**
     * Constructeur de la classe LettreRobotValidatorMapping.
     *
     * @param lettreRobot          La lettre associée au validateur.
     * @param descriptionValidator La description du validateur.
     */
    public LettreRobotValidatorMapping(String lettreRobot, String descriptionValidator) {
        this.lettreRobot = lettreRobot;
        this.descriptionValidator = descriptionValidator;
    }

    /**
     * Obtient la lettre associée au validateur.
     *
     * @return La lettre associée au validateur.
     */
    public String getLettreRobot() {
        return lettreRobot;
    }

    /**
     * Obtient la description du validateur.
     *
     * @return La description du validateur.
     */
    public String getDescriptionValidator() {
        return descriptionValidator;
    }


}
