package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe NombreRepetitions est une implémentation du Validateur.
 * Elle détermine si un chiffre du code se répète et, le cas échéant, combien de fois, en comparant le code du joueur
 * avec le code secret.
 */
public class NombreRepetitions implements Validateur {
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

        // Compter les répétitions dans le code joueur
        int repetitionsJoueur = compterRepetitions(code);

        // Compter les répétitions dans le code secret
        int repetitionsSecret = compterRepetitions(codeSecret);

        // Retourner true si le nombre de répétitions dans le code joueur et le code secret correspond

        validationStatus = repetitionsJoueur == repetitionsSecret;

    }

    /**
     * Compte le nombre maximum de répétitions d'un même chiffre dans le code spécifié.
     *
     * @param code Le code dans lequel compter les répétitions.
     * @return Le nombre maximum de répétitions d'un même chiffre dans le code.
     */
    private int compterRepetitions(String code) {
        int[] occurrences = new int[10]; // Tableau pour compter les occurrences de chaque chiffre (0 à 9)

        // Parcourir chaque chiffre dans le code et incrémenter l'occurrence correspondante
        for (int i = 0; i < code.length(); i++) {
            int chiffre = Character.getNumericValue(code.charAt(i));
            occurrences[chiffre]++;
        }

        // Parcourir les occurrences pour trouver le nombre maximum
        int maxOccurrences = 0;
        for (int occurrence : occurrences) {
            if (occurrence > maxOccurrences) {
                maxOccurrences = occurrence;
            }
        }

        return maxOccurrences;
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
        return String.format("Détermine si un chiffre du code se répète, et si oui, combien de fois");
    }

}
