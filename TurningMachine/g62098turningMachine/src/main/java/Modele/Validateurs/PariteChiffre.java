package Modele.Validateurs;

import Modele.Problemes.Probleme;
/**
 * La classe PariteChiffre est une implémentation du Validateur.
 * Elle vérifie la parité d'un chiffre spécifique dans le code du joueur en le comparant avec le chiffre correspondant
 * dans le code secret.
 */
public class PariteChiffre implements Validateur {
    private Boolean validationStatus = null; // Nouvelle variable pour stocker l'état de validation

    /**
     * Énumération des catégories de parité (PAIR, IMPAIR).
     */
    public enum Parite {
        PAIR,
        IMPAIR
    }
    private int numeroChiffre;

    /**
     * Constructeur de la classe PariteChiffre.
     *
     * @param numeroChiffre Le numéro du chiffre à vérifier dans le code (1, 2 ou 3).
     */
    public PariteChiffre(int numeroChiffre) {
        this.numeroChiffre = numeroChiffre;
    }

    @Override
    public void valider(int codeInt, Probleme probleme) {
        String code=Integer.toString(codeInt);
        String codeSecret=Integer.toString(probleme.getCodeSecret());
        if (code.length() != 3 || codeSecret.length() != 3 || numeroChiffre > 3 || numeroChiffre < 1) {
            // Si le code joueur ou le code secret n'a pas exactement trois chiffres, ou le numéro de chiffre est invalide, la validation échoue
            validationStatus = false;
            return;
        }

        int chiffrePlayer = Character.getNumericValue(code.charAt(numeroChiffre - 1));
        int chiffreSecret = Character.getNumericValue(codeSecret.charAt(numeroChiffre - 1));

        validationStatus = verifierParite(chiffrePlayer) == verifierParite(chiffreSecret);
    }

    /**
     * Vérifie la parité du chiffre spécifié.
     *
     * @param chiffre Le chiffre dont on veut vérifier la parité.
     * @return La parité du chiffre (PAIR si le chiffre est pair, IMPAIR sinon).
     */
    public Parite verifierParite(int chiffre){
        if(chiffre%2 ==0){
            return Parite.PAIR;
        }
        else{
            return Parite.IMPAIR;
        }
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
        switch (numeroChiffre) {
            case 1:
                return String.format("Vérifie la parité du premier chiffre du code");
            case 2:
                return String.format("Vérifie la parité du deuxième chiffre");
            case 3:
                return String.format("Vérifie la parité du troisième chiffre");
            default:
                return "Description non définie pour le numéro de chiffre spécifié";
        }
    }

}
