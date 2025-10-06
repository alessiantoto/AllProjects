package Modele.Problemes;

/**
 * La classe CodeSecret représente un code secret dans le jeu.
 * Elle encapsule la valeur du code et fournit des méthodes pour manipuler ce code.
 */
public class CodeSecret {
    private int code;

    /**
     * Constructeur de la classe CodeSecret.
     *
     * @param code La valeur du code secret.
     */
    public CodeSecret(int code) {
        this.code = code;
    }

    /**
     * Obtient la valeur du code secret.
     *
     * @return La valeur du code secret.
     */
    public int getCode() {
        return code;
    }

    /**
     * Définit la valeur du code secret.
     *
     * @param code La nouvelle valeur du code secret.
     */
    public void setCode(int code) {
        this.code = code;
    }

}
