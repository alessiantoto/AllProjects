package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe PariteSommeChiffres est une implémentation du Validateur.
 * Elle détermine si la somme des chiffres du code du joueur est paire ou impaire,
 * puis compare cette parité avec celle du code secret.
 */
public class PariteSommeChiffres implements Validateur {
    private Boolean validationStatus = null; // Nouvelle variable pour stocker l'état de validation

    @Override
    public void valider(int codeInt, Probleme probleme) {
        String code = Integer.toString(codeInt);
        String codeSecret = Integer.toString(probleme.getCodeSecret());
        if (code.length() != 3 || codeSecret.length() != 3) {
            // Si le code joueur ou le code secret n'a pas exactement trois chiffres, la validation échoue
            validationStatus = false;
            return;
        }

        int sommeChiffresJoueur = calculerSommeChiffres(code);
        int sommeChiffresSecret = calculerSommeChiffres(codeSecret);

        // Comparer la parité de la somme des chiffres du joueur avec celle du secret

        validationStatus = sommeChiffresJoueur % 2 == sommeChiffresSecret % 2;
    }

    /**
     * Calcule la somme des chiffres dans le code spécifié.
     *
     * @param code Le code dans lequel calculer la somme des chiffres.
     * @return La somme des chiffres dans le code.
     */
    private int calculerSommeChiffres(String code) {
        int somme = 0;

        for (int i = 0; i < code.length(); i++) {
            int chiffre = Character.getNumericValue(code.charAt(i));
            somme += chiffre;
        }

        return somme;
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
        return "Détermine si la somme des chiffres du code est paire ou impaire";
    }
}
