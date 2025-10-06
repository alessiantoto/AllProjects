package be.esi.prj.model.Strategy;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Stratégie d'extraction utilisant des expressions régulières pour identifier
 * des informations comme le prix dans un texte brut issu d'un ticket.
 */
public class ExtractWithRegex implements ExtractStrategy {
  private static final String regexPrice =
      "(?i)(total|à\\s*payer|À\\s*payer|net\\s*à\\s*payer|reste\\s*à\\s*"
          + "payer|montant\\s*dû).*?(\\d+\\s*[\\.,]\\s*\\d{2})\\s*€?";



  /**
   * Extrait le prix à partir du texte fourni en analysant chaque ligne
   * avec une expression régulière.
   *
   * @param text le contenu brut du ticket
   * @return le prix détecté, ou {@code null} si aucun prix valide n'a été trouvé
   */
  @Override
  public Double extractPrice(String text) {
    Pattern pattern = Pattern.compile(regexPrice);
    for (String line : text.split("\\n")) {
      Matcher matcher = pattern.matcher(line);
      if (matcher.find()) {
        String priceStr = matcher.group(2);
        if (priceStr != null) {
          priceStr = priceStr.replace(",", ".").replaceAll("\\s+", "");
          try {
            return Double.parseDouble(priceStr);
          } catch (NumberFormatException e) {
            System.err.println("Error parsing price: " + priceStr);
          }
        }
      }
    }
    // TODO: Add a call for the manual extraction method
    return null;
  }

}
