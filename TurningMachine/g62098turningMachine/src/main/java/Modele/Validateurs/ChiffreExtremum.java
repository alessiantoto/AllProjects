package Modele.Validateurs;

import Modele.Problemes.Probleme;

public abstract class ChiffreExtremum implements Validateur{
    private Boolean validationStatus = null; // Variable pour stocker l'état de validation

    @Override
    public void valider(int codeInt, Probleme probleme) {
        String code = Integer.toString(codeInt);
        String codeSecret = Integer.toString(probleme.getCodeSecret());
        if (code.length() != 3 || codeSecret.length() != 3) {
            // Si le code secret et le code joueur n'ont pas exactement trois chiffres, la validation échoue
            validationStatus = false;
            return;
        }

        int playerPosChiffreExtremum = trouverPositionChiffreExtremum(code);
        int secretPosChiffreExtremum = trouverPositionChiffreExtremum(codeSecret);

        validationStatus = playerPosChiffreExtremum == secretPosChiffreExtremum;
    }

    /**
     * Trouve la position du chiffre extrême dans le code spécifié.
     * Cette méthode doit être implémentée par les sous-classes pour spécifier le type de chiffre extrême.
     *
     * @param code Le code dans lequel trouver la position du chiffre extrême.
     * @return La position du chiffre extrême dans le code.
     *
     * Héritage : Les sous-classes, qu'elles se trouvent dans le même paquet ou dans un autre paquet,
     * peuvent accéder à cette méthode. Cela permet aux sous-classes de l'implémenter
     * ou de l'utiliser tout en cachant cette méthode des autres parties du programme qui ne sont pas des sous-classes.
     */
    protected abstract int trouverPositionChiffreExtremum(String code);

    @Override
    public Boolean validationStatus() {
        return this.validationStatus;
    }

    @Override
    public void setStatus(Boolean status) {
        this.validationStatus = status;
    }
}
