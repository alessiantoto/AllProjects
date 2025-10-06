package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe ComparateurDeuxChiffres est une implémentation du Validateur.
 * Elle compare deux chiffres à des positions spécifiées entre le code du joueur et le code secret.
 */
public class ComparateurDeuxChiffres implements Validateur{
    //avoir une classe parent pour les deux types de comparateur
    //ou fonctions en parametre ---> lambda
    private Boolean validationStatus = null; // Nouvelle variable pour stocker l'état de validation

    /**
     * enumeration de categories de comparaison
     */
    private enum Categorie {PLUS_PETIT, EGAL, PLUS_GRAND}
    private int premierChiffre;
    private int deuxiemeChiffre;

    /**
     * Constructeur de la classe CompteurChiffre.
     *
     * @param premierChiffre 1er chiffre à comparer
     * @param deuxiemeChiffre 2 eme chiffre à comparer
     */
    public ComparateurDeuxChiffres(int premierChiffre, int deuxiemeChiffre) {
        this.premierChiffre = premierChiffre;
        this.deuxiemeChiffre = deuxiemeChiffre;
    }

    @Override
    public void valider(int codeInt, Probleme probleme) {
        String code = Integer.toString(codeInt);
        String codeSecret = Integer.toString(probleme.getCodeSecret());
        if (code.length() != 3 || codeSecret.length() != 3) {
            // Si le code secret et le code joueur n'ont pas exactement trois chiffres, la validation échoue
            validationStatus = false;
            return;
        }

        int chiffreJoueur1 = Character.getNumericValue(code.charAt(premierChiffre - 1));
        int chiffreJoueur2 = Character.getNumericValue(code.charAt(deuxiemeChiffre - 1));

        int chiffreSecret1 = Character.getNumericValue(codeSecret.charAt(premierChiffre - 1));
        int chiffreSecret2 = Character.getNumericValue(codeSecret.charAt(deuxiemeChiffre - 1));

        // Compare les catégories des deux chiffres du code joueur et du code secret
        validationStatus = determinerCategorie(chiffreJoueur1, chiffreJoueur2) == determinerCategorie(chiffreSecret1, chiffreSecret2);
    }


    /**
     * Détermine la catégorie d'une comparaison entre deux chiffres.
     *
     * @param chiffre1 Le premier chiffre de la comparaison.
     * @param chiffre2 Le deuxième chiffre de la comparaison.
     * @return La catégorie de la comparaison (PLUS_PETIT, EGAL, PLUS_GRAND).
     */
    private String determinerCategorie(int chiffre1, int chiffre2) {
        // Implémentation pour déterminer la catégorie (PLUS_PETIT, EGAL, PLUS_GRAND)
        if (chiffre1 < chiffre2) {
            return Categorie.PLUS_PETIT.name();
        } else if (chiffre1 == chiffre2) {
            return Categorie.EGAL.name();
        } else {
            return Categorie.PLUS_GRAND.name();
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
        return "Compare le premier chiffre avec le deuxième chiffre";
    }

}
