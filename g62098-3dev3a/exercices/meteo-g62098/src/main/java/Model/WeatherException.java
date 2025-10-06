package Model;

/**
 * La classe WeatherException est une exception personnalisée qui est utilisée pour signaler des erreurs spécifiques
 * liées à la récupération des données météorologiques.
 */

public class WeatherException extends Exception {

    /**
     * Constructeur qui accepte un message d'erreur.
     *
     * @param message - le message décrivant l'erreur.
     */
    public WeatherException(String message) {
        super(message);
    }

    /**
     * Constructeur qui accepte un message d'erreur et une cause.
     *
     * @param message - le message décrivant l'erreur.
     * @param cause   - la cause de l'exception, permettant de connaître l'origine du problème.
     */
    public WeatherException(String message, Throwable cause) {
        super(message, cause);
    }
}
