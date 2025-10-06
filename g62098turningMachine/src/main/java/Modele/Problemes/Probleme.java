package Modele.Problemes;

import Modele.Validateurs.Validateur;

import java.util.List;

/**
 * La classe Probleme représente un problème à résoudre dans le jeu.
 */
public class Probleme {
    private CodeSecret codeSecret;
    private int difficulte;
    private int degreHasard;
    private List<Validateur> validateursAssocies;

    /**
     * Constructeur de la classe Probleme.
     *
     * @param codeSecret          Le code secret à deviner dans le problème.
     * @param difficulte          La difficulté du problème.
     * @param degreHasard         Le degré de hasard associé au problème.
     * @param validateursAssocies La liste des validateurs associés au problème.
     */
    public Probleme(CodeSecret codeSecret, int difficulte, int degreHasard, List<Validateur> validateursAssocies) {
        this.codeSecret = codeSecret;
        this.difficulte = difficulte;
        this.degreHasard = degreHasard;
        this.validateursAssocies = validateursAssocies;
    }

    /**
     * Obtient le code secret du problème.
     *
     * @return Le code secret du problème.
     */
    public int getCodeSecret() {
        return this.codeSecret.getCode();
    }

    /**
     * Obtient la difficulté du problème.
     *
     * @return La difficulté du problème.
     */
    public int getDifficulte() {
        return difficulte;
    }


    /**
     * Obtient le degré de hasard associé au problème.
     *
     * @return Le degré de hasard associé au problème.
     */
    public int getDegreHasard() {
        return degreHasard;
    }


    /**
     * Obtient la liste des validateurs associés au problème.
     *
     * @return La liste des validateurs associés au problème.
     */
    public List<Validateur> getValidateursAssocies() {
        return validateursAssocies;
    }
}

