package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe CompteurChiffresPairs est une implémentation du Validateur.
 * Elle compte le nombre de chiffres pairs dans le code du joueur et le code secret.
 */
public class CompteurChiffresPairs implements Validateur {
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

        // Compter les chiffres pairs dans le code joueur
        int compteurPairsJoueur = compterChiffresPairs(code);

        // Compter les chiffres pairs dans le code secret
        int compteurPairsSecret = compterChiffresPairs(codeSecret);

        // Retourner true si le nombre de chiffres pairs dans le code joueur et le code secret correspond

        validationStatus = compteurPairsJoueur == compteurPairsSecret;
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
        return "Compte combien de chiffres dans le code sont pairs";
    }

    /**
     * Compte le nombre de chiffres pairs dans le code spécifié.
     *
     * @param code Le code dans lequel compter les chiffres pairs.
     * @return Le nombre de chiffres pairs dans le code.
     */
    private int compterChiffresPairs(String code) {
        int compteurPairs = 0;

        // Parcourir chaque chiffre dans le code
        for (int i = 0; i < code.length(); i++) {
            int chiffre = Character.getNumericValue(code.charAt(i));

            // Vérifier si le chiffre est pair
            if (chiffre % 2 == 0) {
                compteurPairs++;
            }
        }

        return compteurPairs;
    }
}
