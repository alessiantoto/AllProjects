package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe ChiffreJumeau est une implémentation du Validateur.
 * Elle valide si un chiffre du code du joueur se trouve en exactement deux exemplaires,
 * de la même manière que dans le code secret du problème.
 */
public class ChiffreJumeau implements Validateur {
    private Boolean validationStatus = null; // Nouvelle variable pour stocker l'état de validation

    @Override
    public void valider(int codeInt, Probleme probleme) {
        String code = Integer.toString(codeInt);
        String codeSecret = Integer.toString(probleme.getCodeSecret());
        // Vérifier que la taille des codes est égale à 3
        if (code.length() != 3 || codeSecret.length() != 3) {
            validationStatus = false;
            return;
        }

        // Initialiser un tableau pour compter le nombre d'occurrences de chaque chiffre (0 à 9)
        int[] occurrencesCodeJoueur = new int[10];
        int[] occurrencesCodeSecret = new int[10];

        // Parcourir chaque chiffre dans le code joueur et mettre à jour le tableau d'occurrences
        for (int i = 0; i < code.length(); i++) {
            int chiffre = Character.getNumericValue(code.charAt(i));
            occurrencesCodeJoueur[chiffre]++;
        }

        // Parcourir chaque chiffre dans le code secret et mettre à jour le tableau d'occurrences
        for (int i = 0; i < codeSecret.length(); i++) {
            int chiffre = Character.getNumericValue(codeSecret.charAt(i));
            occurrencesCodeSecret[chiffre]++;
        }

        // Vérifier s'il y a un chiffre jumeau qui apparaît exactement deux fois (mais pas trois) dans les deux codes
        boolean chiffreJumeauTrouveCodeJoueur = false;
        boolean chiffreJumeauTrouveCodeSecret = false;

        for (int i = 0; i < occurrencesCodeJoueur.length; i++) {
            if (occurrencesCodeJoueur[i] == 2) {
                if (chiffreJumeauTrouveCodeJoueur) {
                    validationStatus = false; // Plus d'un chiffre jumeau trouvé dans le code joueur, retourner faux
                }
                chiffreJumeauTrouveCodeJoueur = true; // Premier chiffre jumeau trouvé dans le code joueur
            }
        }

        for (int i = 0; i < occurrencesCodeSecret.length; i++) {
            if (occurrencesCodeSecret[i] == 2) {
                if (chiffreJumeauTrouveCodeSecret) {
                    validationStatus = false; // Plus d'un chiffre jumeau trouvé dans le code secret, retourner faux
                }
                chiffreJumeauTrouveCodeSecret = true; // Premier chiffre jumeau trouvé dans le code secret
            }
        }

        validationStatus = chiffreJumeauTrouveCodeJoueur == chiffreJumeauTrouveCodeSecret;
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
        return "Détermine si un chiffre du code se trouve en exactement deux exemplaires";
    }
}

