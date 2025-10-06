package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
public class BmrModel {
    private double bmr;
    private double calories;
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    /**
     * Ajoute un auditeur pour les modifications de propriété.
     *
     * @param listener L'auditeur à ajouter.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Calcule le taux métabolique de base (BMR) en fonction du sexe, du poids, de la taille et de l'âge.
     *
     * @param gender Le sexe de la personne (HOMME ou FEMME).
     * @param weight Le poids en kilogrammes.
     * @param height La taille en centimètres.
     * @param age L'âge en années.
     * @return Le BMR calculé.
     */
    public double calculateBmr(Sexe gender, double weight, double height, int age) {
        // Vérifie si l'un des attributs est null, et renvoie 0.0 si c'est le cas
        if (weight < 1 || height < 1 || age < 1) {
            return 0.0;
        }
        if (gender == Sexe.HOMME) {
            bmr = 13.7 * weight + 5 * height - 6.8 * age + 66;
        } else if (gender == Sexe.FEMME) {
            bmr = 9.6 * weight + 1.8 * height - 4.7 * age + 655;
        } else {
            bmr = 0.0; // Sexe non spécifié
        }
        return bmr;
    }

    /**
     * Définit la valeur des calories brûlées.
     * @param calories Les calories brûlées à définir.
     */
    public void setCalories(double calories) {
        if (calories>=0) {
            double oldValue = this.calories; // Déclarez et initialisez oldValue
            this.calories = calories;
            changeSupport.firePropertyChange("Calories", oldValue, calories);
        }
    }

    /**
     * Définit la valeur du taux métabolique de base (BMR).
     * @param bmr La nouvelle valeur du BMR à définir.
     */
    public void setBmr(double bmr) {
        if(bmr>0) {
            double oldValue = this.bmr;
            this.bmr = bmr;
            changeSupport.firePropertyChange("BMR", oldValue, bmr);
        }
    }

    public double getBmr() {
        return bmr;
    }

    public double getCalories() {
        return calories;
    }
}

