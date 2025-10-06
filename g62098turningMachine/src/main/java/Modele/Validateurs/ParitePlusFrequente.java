package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe ParitePlusFrequente est une implémentation du Validateur.
 * Elle détermine la parité du code du joueur en comparant le nombre de chiffres pairs et impairs,
 * puis compare la parité résultante avec celle du code secret.
 */
public class ParitePlusFrequente implements Validateur {
    private Boolean validationStatus = null; // Nouvelle variable pour stocker l'état de validation

    /**
     * Énumération des catégories de parité (PAIR, IMPAIR).
     */
    private enum Parite {
        PAIR,
        IMPAIR
    }

    @Override
    public void valider(int code, Probleme probleme) {
        // Convertir les entiers en chaînes pour vérifier la longueur
        String codeStr = String.valueOf(code);
        String codeSecretStr = String.valueOf(probleme.getCodeSecret());

        if (codeStr.length() != 3 || codeSecretStr.length() != 3) {
            // Si le code joueur ou le code secret n'a pas exactement trois chiffres, la validation échoue
            validationStatus = false;
            return;
        }

        Parite pariteJoueur = determinerParite(codeStr);
        Parite pariteSecret = determinerParite(codeSecretStr);

        // Comparer la catégorie commune du code joueur avec celle du code secret
        validationStatus = pariteJoueur == pariteSecret;
    }


    /**
     * Détermine la parité du code spécifié en comptant le nombre de chiffres pairs et impairs.
     *
     * @param code Le code dont on veut déterminer la parité.
     * @return La parité du code (PAIR si le nombre de chiffres pairs est supérieur ou égal au nombre de chiffres impairs, IMPAIR sinon).
     */
    private Parite determinerParite(String code) {
        int nombrePairs = compterChiffresPairs(code);
        int nombreImpairs = code.length() - nombrePairs;

        // Comparer le nombre de chiffres pairs et impairs pour déterminer la parité
        return (nombrePairs >= nombreImpairs) ? Parite.PAIR : Parite.IMPAIR;
    }


    /**
     * Compte le nombre de chiffres pairs dans le code spécifié.
     *
     * @param code Le code dans lequel compter les chiffres pairs.
     * @return Le nombre de chiffres pairs dans le code.
     */
    private int compterChiffresPairs(String code) {
        int nombrePairs = 0;

        for (int i = 0; i < code.length(); i++) {
            int chiffre = Character.getNumericValue(code.charAt(i));

            if (chiffre % 2 == 0) {
                nombrePairs++;
            }
        }
        return nombrePairs;
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
}
