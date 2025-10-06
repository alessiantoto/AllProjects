package be.esi.prj.model.Strategy;

import opennlp.tools.tokenize.SimpleTokenizer;

/**
 * Implémentation de la stratégie d'extraction basée sur un traitement
 * simulé à l'aide de l'outil de tokenisation OpenNLP.
 * <p>
 * Cette classe est une ébauche : les méthodes retournent actuellement
 * des valeurs simulées à des fins de démonstration.
 */
public class ExtractWithAI implements ExtractStrategy {

  /**
   * Extrait le prix à partir du texte fourni.
   * <p>
   * La méthode suppose que le texte contient une chaîne représentant
   * un prix, mais retourne actuellement une valeur fictive.
   *
   * @param text le contenu textuel du ticket
   * @return le prix extrait sous forme de {@code Double}
   */
  @Override
  public Double extractPrice(String text) {
    String price = findEntity("price");
    return Double.parseDouble(price.replace("€", "").trim());
  }

  /**
   * Recherche une entité spécifique dans le texte.
   * <p>
   * Cette version de la méthode retourne une valeur simulée
   * et ignore le contenu réel du texte.
   *
   * @param text le texte dans lequel chercher
   * @return une chaîne représentant la valeur trouvée
   */
  private String findEntity(String text) {
    SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
    String[] tokens = tokenizer.tokenize(text);
    return "extracted_value";
  }
}
