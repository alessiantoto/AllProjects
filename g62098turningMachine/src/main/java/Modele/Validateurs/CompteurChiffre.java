package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe CompteurChiffre est une implémentation du Validateur.
 * Elle compte le nombre d'occurrences d'une valeur spécifiée dans le code du joueur et le code secret.
 */
public class CompteurChiffre implements Validateur {
    private Boolean validationStatus = null; // Nouvelle variable pour stocker l'état de validation

    private int valeur;

    /**
     * Constructeur de la classe CompteurChiffre.
     *
     * @param valeur La valeur à compter dans le code.
     */
    public CompteurChiffre(int valeur) {
        this.valeur = valeur;
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
        int occurrencesPlayer = compterNbrIterations(code);
        int occurrencesSecret = compterNbrIterations(codeSecret);

        // Retourner true si le nombre d'occurrences est le même dans les deux codes
        validationStatus = occurrencesPlayer == occurrencesSecret;
    }

    /**
     * Compte le nombre d'occurrences du chiffre spécifié dans le code.
     *
     * @param code Le code dans lequel compter les occurrences du chiffre.
     * @return Le nombre d'occurrences du chiffre spécifié dans le code.
     */
    private int compterNbrIterations(String code) {
        // Initialiser le compteur de la valeur spécifiée
        int compteur = 0;

        // Parcourir chaque chiffre dans le code
        for (int i = 0; i < code.length(); i++) {
            int chiffre = Character.getNumericValue(code.charAt(i));

            // Vérifier si le chiffre est égal à la valeur spécifiée
            if (chiffre == valeur) {
                compteur++;
            }
        }

        return compteur;
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
        return "Compte combien de fois la valeur " + valeur + " apparaît dans le code ()";
    }
}
