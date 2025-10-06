package Modele.Validateurs;

import Modele.Problemes.Probleme;


/**
 * La classe ChiffreExtremumMax valide si la position du chiffre maximal dans le code du joueur correspond à celui du code secret.
 */
public class ChiffreExtremumMax extends ChiffreExtremum {

    @Override
    protected int trouverPositionChiffreExtremum(String code) {
        int posChiffreMax = 0;
        int max = code.charAt(0);

        for (int i = 1; i < code.length(); i++) {
            if (code.charAt(i) > max) {
                posChiffreMax = i;
                max = code.charAt(i);
            }
        }

        return posChiffreMax;
    }

    @Override
    public String getDescription() {
        return "Détermine quel chiffre est strictement le plus grand";
    }
}
