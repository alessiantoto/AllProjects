package be.esi.prj.model.Strategy;


/**
 * Interface définissant les méthodes nécessaires pour extraire des informations
 * depuis un texte (comme celui d’un ticket de caisse scanné).
 *
 * Utilisée pour appliquer différentes stratégies d'extraction selon le format ou la langue du texte.
 */
public interface ExtractStrategy {

  /**
   * Extrait le prix total depuis le texte fourni.
   *
   * @param text le texte brut d’un ticket
   * @return le montant total extrait ou {@code null} si non trouvé
   */
  Double extractPrice(String text);

}
