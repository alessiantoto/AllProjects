package g62098.model;

import java.io.Serializable;

/**
 *Cette classe représente une exception personnalisée pour le jeu Qwirkle.
 *Elle est utilisée pour signaler des erreurs spécifiques à Qwirkle, avec un message d'erreur personnalisé.
 */
public class QwirkleException extends RuntimeException implements Serializable {
    /**
     Constructeur de l'exception QwirkleException.
     @param message Le message d'erreur personnalise à afficher lors de la levée de l'exception.
     */
    public QwirkleException(String message) {
        super(message);
    }
}
