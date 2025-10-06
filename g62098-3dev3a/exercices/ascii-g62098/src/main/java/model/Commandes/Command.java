package model.Commandes;
/**
 * L'interface Command définit les méthodes nécessaires pour représenter une commande dans le modèle de commande.
 * Une commande est une opération qui peut être exécutée et annulée.
 */
public interface Command {

    /**
     * Exécute la commande.
     */
    void execute();

    /**
     * Annule la commande, revenant à l'état précédent avant l'exécution de la commande.
     */
    void undo();
}
