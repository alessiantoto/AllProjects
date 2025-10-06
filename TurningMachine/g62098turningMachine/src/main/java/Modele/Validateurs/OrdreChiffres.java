package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe OrdreChiffres est une implémentation du Validateur.
 * Elle détermine si les trois chiffres du code sont en ordre croissant, décroissant ou aucun, en comparant le code du joueur
 * avec le code secret.
 */
public class OrdreChiffres implements Validateur {
    private Boolean validationStatus = null; // Nouvelle variable pour stocker l'état de validation

    /**
     * Énumération des catégories de ordres.
     */
    private enum CategorieOrdre {
        CROISSANT,
        DECROISSANT,
        AUCUN
    }

    @Override
    public void valider(int codeInt, Probleme probleme) {

        String code = Integer.toString(codeInt);
        String codeSecret = Integer.toString(probleme.getCodeSecret());
        if (code.length() != 3 || codeSecret.length() != 3) {
            // Si le code joueur ou le code secret n'a pas exactement trois chiffres, la validation échoue
            validationStatus = false;
            return;
        }// Comparer l'ordre des chiffres dans le code joueur
        CategorieOrdre ordreJoueur = comparerOrdre(code);

        // Comparer l'ordre des chiffres dans le code secret
        CategorieOrdre ordreSecret = comparerOrdre(codeSecret);

        // Retourner true si l'ordre dans le code joueur correspond à l'ordre dans le code secret
        validationStatus = ordreJoueur == ordreSecret;
    }


    /**
     * Compare l'ordre des chiffres dans le code spécifié.
     *
     * @param code Le code dans lequel comparer l'ordre des chiffres.
     * @return La catégorie d'ordre des chiffres dans le code (CROISSANT, DECROISSANT, AUCUN).
     */
    private CategorieOrdre comparerOrdre(String code) {
        // Parcourir chaque chiffre dans le code et comparer avec le chiffre suivant
        for (int i = 0; i < code.length() - 1; i++) {int chiffreActuel = Character.getNumericValue(code.charAt(i));
            int chiffreSuivant = Character.getNumericValue(code.charAt(i + 1));

            if (chiffreActuel > chiffreSuivant) {
                return CategorieOrdre.DECROISSANT; // L'ordre n'est pas croissant
            } else if (chiffreActuel < chiffreSuivant) {
                return CategorieOrdre.CROISSANT; // L'ordre est croissant
            }
        }
        return CategorieOrdre.AUCUN; // L'ordre est décroissant (ou aucun, s'il s'agit d'une séquence constante)   //pas sur de ca
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
        return "Détermine si les trois chiffres du code sont en ordre croissant ou décroissant";
    }
}