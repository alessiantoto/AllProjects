package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe ComparateurChiffre est une implémentation du Validateur.
 * Elle compare le chiffre à une position spécifiée avec une valeur donnée.
 */
public class ComparateurChiffre implements Validateur {
    private Boolean validationStatus = null; // Nouvelle variable pour stocker l'état de validation

    private int valeur;
    private int numeroChiffre;

    /**
     * Compare un chiffre a une valeur
     * @param valeur  Valeur à laquelle on compare
     * @param numeroChiffre Numéro du chiffre qui va être comparé à la valeur
     */
    public ComparateurChiffre(int valeur, int numeroChiffre) {
        this.valeur = valeur;
        this.numeroChiffre = numeroChiffre;
    }

    @Override
    public void valider(int codeInt, Probleme probleme) {
        String code = Integer.toString(codeInt);
        String codeSecret = Integer.toString(probleme.getCodeSecret());
        if (code.length() != 3 || codeSecret.length() != 3) {
            // Si le code secret et le code joueur n'ont pas exactement trois chiffres, la validation échoue
            validationStatus = false;
            return; //lancer exception bon endroit pour suivre d'ou ca vien
        }

        int chiffreAComparerJoueur = Character.getNumericValue(code.charAt(numeroChiffre - 1));
        int chiffreAComparerSecret = Character.getNumericValue(codeSecret.charAt(numeroChiffre - 1));


        validationStatus = chiffreAComparerJoueur < valeur && chiffreAComparerSecret < valeur ||
                chiffreAComparerJoueur == valeur && chiffreAComparerSecret == valeur ||
                chiffreAComparerJoueur > valeur && chiffreAComparerSecret > valeur;
    }



    @Override
    public Boolean validationStatus() {
        return this.validationStatus;
    }

    @Override
    public void setStatus(Boolean status) {
        this.validationStatus = status;
    }

    @Override
    public String getDescription() {
        return String.format("Compare le chiffre à la position %d avec %d", numeroChiffre, valeur);
    }
}
