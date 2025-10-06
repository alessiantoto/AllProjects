package Modele.Validateurs;

import Modele.Problemes.Probleme;

public interface Validateur{
    /**
     * Méthode pour effectuer la validation du code.
     * Si les deux codes sont dans la meme categorie -> True
     * Sinon -> False
     * @param code Le code à valider.
     * met l'etat a true si la validation réussit, false sinon.
     */


    void valider(int code, Probleme probleme);

    /**
     * Méthode pour obtenir le resultat apres avoir valide le validateur
     * avec la methode d'en haut, valider
     *
     * @return La description du validateur.
     */

    Boolean validationStatus();


    /**
     * Définit le statut de validation.
     *
     * @param valide Le nouveau statut de validation.
     */
    void setStatus(Boolean valide);


    /**
     * Obtient la description du validateur.
     *
     * @return La description du validateur.
     */
    String getDescription();


}

