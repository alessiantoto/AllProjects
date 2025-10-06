package app;

import controller.AsciiController;
import model.AsciiPaint;
import view.Vue;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // Crée un modèle de dessin (AsciiPaint) avec des dimensions spécifiées
        Scanner clavier = new Scanner(System.in);
        System.out.println("Entrez la largeur de la zone de dessin : ");
        int largeur = clavier.nextInt();

        System.out.println("Entrez la hauteur de la zone de dessin : ");
        int hauteur = clavier.nextInt();

        AsciiPaint asciiPaint = new AsciiPaint(largeur, hauteur);
        Vue vue = new Vue();

        // Instancier le contrôleur avec le modèle
        AsciiController controller = new AsciiController(asciiPaint, vue);

        // Démarrer la boucle applicative
        controller.start();  // Lance la boucle de contrôle
    }
}
