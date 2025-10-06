package g62098.dev3.oxono.Model.Facade;

import g62098.dev3.oxono.Model.*;
import g62098.dev3.oxono.Model.AI.AIPlayer;
import g62098.dev3.oxono.Model.AI.AIStrategy;
import g62098.dev3.oxono.Model.AI.RandomStrategy;
import g62098.dev3.oxono.Model.Command.Command;
import g62098.dev3.oxono.Model.Command.CommandManager;
import g62098.dev3.oxono.Model.Command.MoveTotemCommand;
import g62098.dev3.oxono.Model.Command.PlacePieceCommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameFacade implements Facade, Observable {
    private Game game;
    private CommandManager commandManager;
    private AIStrategy aiStrategy;  // Stratégie de l'IA
    private OxonoColor currentPlayerColor;
    private OxonoColor AIPlayerColor;
    private final Set<Observer> observers = new HashSet<>();

    public GameFacade(Game game) {
        this.game = game;
        this.commandManager = new CommandManager();
        this.aiStrategy = new RandomStrategy();  // Initialisation de la stratégie de l'IA
    }

    @Override
    public void startNewGame() {
        game.startNewGame();
        notifyObservers();
    }


    @Override
    public Player getCurrentPlayer(OxonoColor playerColor) {
        if (game.getBoard().getPlayer1().getColor() == playerColor) {
            return game.getBoard().getPlayer1();
        } else {
            return game.getBoard().getPlayer2();
        }
    }


    @Override
    public void moveTotem(Symbol totemSymbol, int x, int y) {
        Command command = new MoveTotemCommand(game, totemSymbol, x, y);
        game.executeCommand(command); // Utilise Board pour exécuter la commande
        notifyObservers();
    }

    //Il faut une nouvelle fenetre qui ecoute
    @Override
    public List<int[]> getValidTotemMoves(Symbol totemSymbol){
        return game.getValidTotemMoves(totemSymbol);
    }

    @Override
    public List<int[]> getValidPiecesPlaces(Symbol pieceSymbol){
        return game.getValidPiecesPlaces(pieceSymbol);
    }

    @Override
    public boolean placePiece(OxonoColor playerOxonoColor, Symbol symbol, int x, int y) {
        Command command = new PlacePieceCommand(game, playerOxonoColor, symbol, x, y);
        game.executeCommand(command); // Utilise Board pour exécuter la commande
        // Vérifie si le joueur a gagné après avoir placé la pièce
        boolean hasWon = game.getHasWon();
        notifyObservers();
        return hasWon;
    }

    @Override
    public void playAITurn(OxonoColor AIOxonoColor, Game game) {
        // Crée une instance de la stratégie de l'IA (RandomStrategy dans cet exemple)
        AIStrategy strategy = new RandomStrategy();

        // Instancie le joueur IA avec la couleur donnée et la stratégie choisie
        AIPlayer aiPlayer = new AIPlayer(AIOxonoColor, game,strategy);

        // Fait jouer l'IA
        aiPlayer.playTurn(game);
        notifyObservers();  // Notifier les observateurs après le tour de l'IA
    }

    @Override
    public void setPlayerColor(OxonoColor color) {
        this.currentPlayerColor = color;

        // Assigner l'IA à la couleur opposée
        if (color == OxonoColor.PINK) {
            this.AIPlayerColor = OxonoColor.BLACK;
        } else if (color == OxonoColor.BLACK) {
            this.AIPlayerColor = OxonoColor.PINK;
        }
        System.out.println("Toi: "+currentPlayerColor+" AI: "+AIPlayerColor);
    }

    @Override
    public boolean getTotemMoved(){
        return game.getTotemMoved();
    }

    @Override
    public void undo() {
        game.undo();
        notifyObservers();  // Notifier les observateurs après le tour de l'IA
    }

    @Override
    public void redo() {
        game.redo();
        notifyObservers();  // Notifier les observateurs après le tour de l'IA
    }


    @Override
    public Board getBoard(){
        return game.getBoard();
    }
    @Override
    public Game getGame(){
        return game;
    }

    @Override
    public OxonoColor getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    @Override
    public OxonoColor getAIPlayerColor() {
        return AIPlayerColor;
    }

    @Override
    public void notifyTotemSelected(){
        notifyObservers();
        System.out.println("Totem chosed");
    }
    @Override
    public void registerObserver(Observer o) {
        observers.add(o); // Ajoute un observateur
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o); // Supprime un observateur
    }

    @Override
    public void notifyObservers() {

        for (Observer observer : observers) {
            observer.update(); // Notifie chaque observateur avec les nouvelles données
        }

    }



}
