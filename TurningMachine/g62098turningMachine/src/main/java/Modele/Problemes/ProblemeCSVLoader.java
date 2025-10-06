package Modele.Problemes;

import Modele.Validateurs.Factory;
import Modele.Validateurs.Validateur;

import java.io.*;
import java.util.Arrays;
import java.util.List;


/**
 * La classe ProblemeCSVLoader permet de charger des problèmes à partir d'un fichier CSV.
 */
public class ProblemeCSVLoader {
    private Factory factory;

    /**
     * Constructeur de la classe ProblemeCSVLoader.
     *
     * @param factory L'objet Factory utilisé pour créer des validateurs à partir d'indices.
     */
    public ProblemeCSVLoader(Factory factory) {
        this.factory = factory;
    }


    /**
     * Charge un problème spécifique à partir d'une ligne donnée du fichier CSV.
     *
     * @param numProbleme Le numéro de la ligne correspondant au problème à charger.
     * @return Le problème chargé.
     * @throws IllegalArgumentException Si le numéro de ligne est invalide (hors des limites acceptées).
     */
    public Probleme loadSpecificLine(int numProbleme) {
        if (numProbleme <= 0 || numProbleme > 16) {
            throw new IllegalArgumentException("La ligne doit être entre 1 et 16 inclue");
        }

        Probleme problem = null;

        try (InputStream in = ProblemeCSVLoader.class.getResourceAsStream("/TuringMachine-assets/known_problems.csv")) {    //code tente d'ouvrir un flux d'entrée pour charger un fichier CSV
            if (in != null) {   //si le fichier est trouve donc source pas vide
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                    //un BufferedReader est utilisé pour lire les lignes du flux d'entrée.
                    String line;
                    int currentLine = 0;

                    while ((line = reader.readLine()) != null) {
                        //Le code itère ensuite sur les lignes, et lorsqu'il trouve la ligne du nr qu'il faut
                        if (currentLine == numProbleme) {
                            problem = createProblemFromCSVLine(line);
                            return problem;
                        }
                        currentLine++;
                    }

                    System.out.println("La ligne cible n'existe pas dans le fichier.");
                }
            } else {
                System.err.println("Le fichier de ressources n'a pas été trouvé.");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return problem;
    }


    /**
     * Crée un objet Probleme à partir d'une ligne du fichier CSV.
     *
     * @param line La ligne CSV contenant les informations du problème.
     * @return Le problème créé.
     */
    private Probleme createProblemFromCSVLine(String line) {
        Probleme problem = null;

        // Séparer les valeurs de la ligne
        String[] values = line.split(",");

        // Assumer que les colonnes sont dans l'ordre : num, difficulty, luck, code, validatorNos
        if (values.length >= 5) {
            try {
                // Attribuer les valeurs aux propriétés de Probleme
                int num = Integer.parseInt(values[0].trim());
                int difficulte = Integer.parseInt(values[1].trim());
                int degreHasard = Integer.parseInt(values[2].trim());
                CodeSecret codeSecret = new CodeSecret(Integer.parseInt(values[3].trim())); // Utiliser CodeSecret   //int pour voir si erreur est du fichier

                String[] validatorNosArray = Arrays.copyOfRange(values, 4, values.length);  //Les numéros de validateur (à partir de la quatrième colonne) sont extraits dans un tableau.
                List<Validateur> validateursAssocies = factory.getValidatorsByIndices(validatorNosArray);   //transforme les numeros en validateurs

                problem = new Probleme(codeSecret, difficulte, degreHasard, validateursAssocies);
            } catch (NumberFormatException e) {
                // Gérer les erreurs de conversion numérique si nécessaire
                e.printStackTrace();
            }
        }

        return problem;
    }


    /**
     * Compte le nombre total de problèmes dans le fichier CSV.
     *
     * @return Le nombre total de problèmes.
     */
    public int countTotalProblemes() {
        int totalProblemes = 0;

        try (InputStream in = ProblemeCSVLoader.class.getResourceAsStream("/TuringMachine-assets/known_problems.csv")) {
            if (in != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                    // Ignorer la première ligne (en-têtes)
                    reader.readLine();

                    while (reader.readLine() != null) {
                        totalProblemes++;
                    }
                }
            } else {
                System.err.println("Le fichier de ressources n'a pas été trouvé.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalProblemes;
    }

}
