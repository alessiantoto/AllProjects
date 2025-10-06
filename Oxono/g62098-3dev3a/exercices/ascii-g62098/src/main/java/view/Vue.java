package view;

import model.Group;
import model.Shape;

import java.util.List;

/**
 * La classe Vue gère l'affichage du dessin ASCII et de la liste des formes géométriques.
 */
public class Vue {

    /**
     * Affiche le dessin ASCII à l'écran.
     * @param dessin Le dessin ASCII à afficher.
     */
    public void afficherDessin(String dessin) {
        System.out.println("Dessin ASCII :");
        System.out.println(dessin);
    }

    /**
     * Affiche la liste des formes géométriques avec leurs informations.
     * @param formes La liste des formes géométriques à afficher.
     */
    public void afficherListeFormes(List<Shape> formes) {
        System.out.println("Liste des formes :");
        int index = 1;  // Utilisez un index séparé pour numéroter les formes affichées
        for (Shape forme : formes) {
            if (forme instanceof Group) {
                // Si la forme est un groupe, afficher "Groupe" suivi de ses formes internes et de la couleur du groupe
                Group groupe = (Group) forme;
                List<Shape> formesInternes = groupe.getShapes();
                System.out.println("Groupe " + index + ":");
                System.out.println("   Couleur du groupe : " + groupe.getColor());
                for (Shape formeInterne : formesInternes) {
                    System.out.println("      Forme " + index + ": " + formeInterne.getClass().getSimpleName());
                    System.out.println("      Couleur : " + formeInterne.getColor());
                    index++;
                }
            } else {
                // Si la forme n'est pas un groupe, l'afficher normalement
                System.out.println("Forme " + index + ": " + forme.getClass().getSimpleName());
                System.out.println("Couleur : " + forme.getColor());
                index++;
            }
        }
    }
}
