package Application;

import Controleur.ControleurConsole;
import Modele.Facade.Facade;
import Modele.Facade.FacadeImpl;
import Modele.Validateurs.Factory;

public class ApplicationConsole {
    public static void main(String[] args) {
        Factory factory = new Factory();
        Facade facade = new FacadeImpl(factory);
        ControleurConsole controleurConsole = new ControleurConsole(facade, factory);
        controleurConsole.start();
    }
}
