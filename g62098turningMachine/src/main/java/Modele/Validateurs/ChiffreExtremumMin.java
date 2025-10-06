package Modele.Validateurs;

import Modele.Problemes.Probleme;

/**
 * La classe ChiffreExtremumMin est une implémentation du Validateur.
 * Elle valide si la position du chiffre minimal dans le code du joueur correspond à celui du code secret dans le problème.
 */
public class ChiffreExtremumMin extends ChiffreExtremum {
    @Override
    protected int trouverPositionChiffreExtremum(String code) {
        int posChiffreMin = 0;
        int min = code.charAt(0);

        for (int i = 1; i < code.length(); i++) {
            if (code.charAt(i) < min) {
                posChiffreMin = i;
                min = code.charAt(i);
            }
        }

        return posChiffreMin;
    }

    @Override
    public String getDescription() {
        return "Détermine la position du chiffre minimal dans le code.";
    }
}
