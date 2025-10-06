package Model;

/**
 * L'énumération {@code StyleDeVie} représente les différents styles de vie avec leur facteur d'activité associé.
 * Les valeurs incluses sont {@code SEDENTAIRE}, {@code PEU_ACTIF}, {@code ACTIF}, {@code FORT_ACTIF}, et {@code EXTREMEMENT_ACTIF}.
 * Chaque style de vie a un facteur associé qui est utilisé pour calculer les besoins énergétiques.
 */
 public enum StyleDeVie {
    SEDENTAIRE(1.2),
    PEU_ACTIF(1.375),
    ACTIF(1.55),
    FORT_ACTIF(1.725),
    EXTREMEMENT_ACTIF(1.9);

    private final double factor;

    /**
     * Constructeur privé pour associer un facteur à chaque style de vie.
     *
     * @param factor Le facteur d'activité associé au style de vie.
     */
    StyleDeVie(double factor) {
        this.factor = factor;
    }
}