package Modele.Validateurs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe Factory fournissant des méthodes statiques pour obtenir des validateurs en fonction d'indices spécifiés
 * et pour récupérer des validateurs à partir d'indices ou de validateurs.
 */
public class Factory {
    /**
     * Mapping entre les indices et les validateurs associés.
     */
    private enum IndiceValidatorMapping {    //PRIVATE
        INDICE_1(1, new ComparateurChiffre(1,1)),
        INDICE_2(2, new ComparateurChiffre(3,1)),
        INDICE_3(3, new ComparateurChiffre(3,2)),
        INDICE_4(4, new ComparateurChiffre(4,2)),
        INDICE_5(5, new PariteChiffre(1)),
        INDICE_6(6, new PariteChiffre(2)),
        INDICE_7(7, new PariteChiffre(3)),
        INDICE_8(8, new CompteurChiffre(1)),
        INDICE_9(9, new CompteurChiffre(3)),
        INDICE_10(10, new CompteurChiffre(4)),
        INDICE_11(11, new ComparateurDeuxChiffres(1,2)),
        INDICE_12(12, new ComparateurDeuxChiffres(1,3)),
        INDICE_13(13, new ComparateurDeuxChiffres(2,3)),
        INDICE_14(14, new ChiffreExtremumMin()),   //pour le MIN
        INDICE_15(15, new ChiffreExtremumMax()),
        INDICE_16(16, new ParitePlusFrequente()),
        INDICE_17(17, new CompteurChiffresPairs()),
        INDICE_18(18, new PariteSommeChiffres()),
        INDICE_19(19, new ComparateurSommeDeuxChiffres()),
        INDICE_20(20, new NombreRepetitions()),
        INDICE_21(21, new ChiffreJumeau()),
        INDICE_22(22, new OrdreChiffres()),

        ;


        private final int indice;
        private final Validateur validateur;


        IndiceValidatorMapping(int indice, Validateur validateur) {
            this.indice = indice;
            this.validateur = validateur;
        }

        public int getIndice() {
            return indice;
        }

        public Validateur getValidateur() {
            return validateur;
        }

        public String getDescription() {
            return validateur.getDescription(); // Assurez-vous que Validateur a une méthode getDescription()
        }
    }

    /**
     * Retourne une liste de validateurs en fonction des indices spécifiés.
     *
     * @param indices Tableau d'indices sous forme de chaînes de caractères.
     * @return Liste de validateurs correspondant aux indices spécifiés.
     */
    public List<Validateur> getValidatorsByIndices(String[] indices) {
        List<Validateur> validateurs = new ArrayList<>();   //qui stockera les validateurs correspondant aux indices spécifiés.
        for (String indice : indices) {
            int indiceInt = Integer.parseInt(indice);
            for (IndiceValidatorMapping mapping : IndiceValidatorMapping.values()) {    //Boucle qui itère sur chaque élément de l'énumération
                if (mapping.getIndice() == indiceInt) {
                    validateurs.add(mapping.getValidateur());
                    break;
                }
            }
        }
        return validateurs;
    }

    /**
     * Retourne l'indice associé au validateur spécifié.
     *
     * @param validateur Le validateur dont on souhaite obtenir l'indice.
     * @return L'indice associé au validateur spécifié, ou -1 s'il n'est pas trouvé.
     */
    public int getIndiceByValidator(Validateur validateur) {
        for (IndiceValidatorMapping mapping : IndiceValidatorMapping.values()) {
            if (mapping.getValidateur().equals(validateur)) {
                return mapping.getIndice();
            }
        }
        // Si aucun indice n'est trouvé, retourner -1 ou une valeur qui indique l'absence de l'indice
        return -1;
    }


    /**
     * Retourne le nombre total d'indices et de validateurs dans le mapping.
     *
     * @return Le nombre total d'indices et de validateurs.
     */
    public int size() {
        return IndiceValidatorMapping.values().length;
    }


    /**
     * Retourne une liste de descriptions de validateurs avec leurs indices.
     *
     * @return Liste des descriptions de validateurs avec leurs indices.
     */
    public List<String> getValidatorDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (IndiceValidatorMapping mapping : IndiceValidatorMapping.values()) {
            descriptions.add(mapping.getIndice() + ". " + mapping.getValidateur().getDescription());
        }
        return descriptions;
    }

    public String getValidatorDescriptionByIndice(int indice) {
        for (IndiceValidatorMapping mapping : IndiceValidatorMapping.values()) {
            if (mapping.getIndice() == indice) {
                return mapping.getDescription();
            }
        }
        return null; // ou une valeur par défaut si l'indice est invalide
    }
}
