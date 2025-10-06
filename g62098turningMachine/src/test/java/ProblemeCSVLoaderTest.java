import Modele.Validateurs.Factory;
import Modele.Problemes.Probleme;
import Modele.Problemes.ProblemeCSVLoader;
import Modele.Validateurs.Validateur;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProblemeCSVLoaderTest {

    @Test
    void loadSpecificLine() {
        Factory factory = new Factory();
        ProblemeCSVLoader problemeCSVLoader = new ProblemeCSVLoader(factory);  //liste de 0 a 22
        // Assurez-vous que votre fichier CSV est accessible dans le répertoire de ressources du test
        Probleme problem = problemeCSVLoader.loadSpecificLine(1);

        assertNotNull(problem);
        assertEquals("241", problem.getCodeSecret());
        assertEquals(1, problem.getDifficulte());
        assertEquals(3, problem.getDegreHasard());
        // Vérifiez d'autres propriétés selon votre structure de problème
        List<Validateur> validateurs = problem.getValidateursAssocies();
        System.out.println(validateurs.get(0));
        System.out.println(validateurs.get(1));
        System.out.println(validateurs.get(2));
        System.out.println(validateurs.get(3));

        assertNotNull(validateurs);
        assertFalse(validateurs.isEmpty());
    }

    @Test
    void countTotalProblemes() {
        Factory factory = new Factory();
        ProblemeCSVLoader problemeCSVLoader = new ProblemeCSVLoader(factory);

        int totalProblemes = problemeCSVLoader.countTotalProblemes();

        int expectedTotalProblemes = 16; // nbr total prooblemes

        assertEquals(expectedTotalProblemes, totalProblemes);
    }
}
