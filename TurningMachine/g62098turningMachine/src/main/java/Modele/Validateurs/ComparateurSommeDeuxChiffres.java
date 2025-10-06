package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe ComparateurSommeDeuxChiffres est une implémentation du Validateur.
 * Elle compare la somme de deux chiffres à une valeur spécifiée entre le code du joueur et le code secret.
 */
public class ComparateurSommeDeuxChiffres implements Validateur {
    private Boolean validationStatus = null; // Nouvelle variable pour stocker l'état de validation

    private final int valeur = 6;

    /**
     * enmeration de categories de comparaison
     */
    private enum Categorie {PLUS_PETIT, EGAL, PLUS_GRAND}

    @Override
    public void valider(int codeInt, Probleme probleme) {
        String code = Integer.toString(codeInt);
        String codeSecret = Integer.toString(probleme.getCodeSecret());
        if (code.length() != 3 || codeSecret.length() != 3) {
            // Si le code secret et le code joueur n'ont pas exactement trois chiffres, la validation échoue
            validationStatus = false;
            return;
        }

        // Récupérer les chiffres du code joueur
        int chiffre1Joueur = Character.getNumericValue(code.charAt(0));
        int chiffre2Joueur = Character.getNumericValue(code.charAt(1));

        // Récupérer les chiffres du code secret
        int chiffre1Secret = Character.getNumericValue(codeSecret.charAt(0));
        int chiffre2Secret = Character.getNumericValue(codeSecret.charAt(1));

        // Calculer la somme des deux chiffres pour le joueur et le secret
        int sommeJoueur = chiffre1Joueur + chiffre2Joueur;
        int sommeSecret = chiffre1Secret + chiffre2Secret;

        // Comparer la somme avec la valeur spécifiée pour le joueur et le secret
        validationStatus = comparerSomme(sommeJoueur) && comparerSomme(sommeSecret);

    }


    /**
     * Compare la somme avec la valeur spécifiée et retourne true si elle est dans la même catégorie.
     *
     * @param somme La somme à comparer.
     * @return true si la somme est dans la même catégorie que la valeur, false sinon.
     */
    private boolean comparerSomme(int somme) {
        // Comparer la somme avec la valeur spécifiée et retourner true si dans la même catégorie
        if (somme < valeur) {
            return determinerCategorie(somme) == Categorie.PLUS_PETIT;
        } else if (somme == valeur) {
            return determinerCategorie(somme) == Categorie.EGAL;
        } else {
            return determinerCategorie(somme) == Categorie.PLUS_GRAND;
        }
    }


    /**
     * Détermine la catégorie d'une somme par rapport à la valeur spécifiée.
     *
     * @param somme La somme à évaluer.
     * @return La catégorie de la somme par rapport à la valeur (PLUS_PETIT, EGAL, PLUS_GRAND).
     */
    private Categorie determinerCategorie(int somme) {
        // Déterminer la catégorie (PLUS_PETIT, EGAL, PLUS_GRAND)
        if (somme < valeur) {
            return Categorie.PLUS_PETIT;
        } else if (somme == valeur) {
            return Categorie.EGAL;
        } else {
            return Categorie.PLUS_GRAND;
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
        return String.format("Compare la somme de deux chiffres avec la valeur %d", valeur);
    }
}
