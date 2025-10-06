package g62098.model;

import g62098.view.View;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * La classe Game représente le jeu de Qwirkle. Il garde une trace du tableau, des joueurs et de l'index des joueurs actuels.*------------
 */
public class Game implements Serializable{
    private Player[] players;
    private int currentPlayerIndex;
    private Grid grid;
    private int score;

    /**
     Initialise le jeu avec le tableau et les joueurs, et initialise l'index du joueur actuel.
     @param names une liste de chaînes représentant les noms des joueurs
     @throws QwirkleException si le nombre de joueurs est inférieur à 2 ou supérieur à 4
     */
    public Game(List<String> names) {
        // Initialize board
        grid = new Grid();
        // Initialize players
        players = new Player[names.size()];
        if (names.size() < 2 || names.size() > 4) {
            throw new QwirkleException("minimum 2 players, maximum 4");
        }
        for (int i = 0; i < names.size(); i++) {
            players[i] = new Player(names.get(i));
        }

        // Initialize current player index
        currentPlayerIndex = 0;
        score = 0; // Initialisation du score à zéro
    }

    /**
     * place les tuiles dans la direction spécifiée.
     * @param d la direction
     * @param is les index des tuiles à jouer dans la main du joueur actuel
     * @throws QwirkleException si les tuiles ne peuvent pas être jouées ou si l'index des tuiles n'est pas valide
     */
    public void first(Direction d, int... is) throws QwirkleException{
        Player currentPlayer = players[currentPlayerIndex];
        // Get current player and their tiles
        Tile[] played = new Tile[is.length];

        for (int pos = 0; pos < is.length; pos++) {
            played[pos] = currentPlayer.getHand().get(is[pos]);
        }
        // Verify if the tiles can be played, and execute move if they can be
        int points=grid.firstAdd(d, played); // Points obtenus lors du tour de jeu
        // Mise à jour du score du joueur actuel
        currentPlayer.addToScore(points);          //met -points
        // Remove played tiles from player's hand
        currentPlayer.remove(played);
        // Refill player's hand
        currentPlayer.refill();
        // Update current player index
        pass();
    }

    /**
     * Place une tuile à une position donnée sur le plateau.
     * @param row ligne où on veut placer la tuile
     * @param col colonne où on veut placer la tuiler
     * @param index index de la tuile
     * @throws QwirkleException si la tuile ne peut pas être jouée ou si l'index de la tuile n'est pas valide
     */
    public void play(int row, int col, int index) throws QwirkleException{
        Player currentPlayer = players[currentPlayerIndex];
        Tile tile = currentPlayer.getHand().get(index);
        // Verify if the tile can be played
        int points=grid.add(row, col, tile);
        // Mise à jour du score du joueur actuel
        currentPlayer.addToScore(points);
        // Remove played tiles from player's hand
        players[currentPlayerIndex].remove(tile);; //ou remove tilesToPlay!!!!!
        // Refill player's hand
        players[this.currentPlayerIndex].refill();
        // Update current player index
        pass();
    }

    /**
     * Place les tuiles dans une direction
     * @param row ligne ou on veut placer la premiere tuile
     * @param col colonne ou on veut placer la premiere tuile
     * @param d direction dans laquelle on veut placer le reste des tuiles
     * @param indexes les index des tuiles à jouer dans la main du joueur actuel
     */

    public void play(int row, int col, Direction d, int... indexes) throws QwirkleException{
        // Get current player and their tiles
        Player currentPlayer = players[currentPlayerIndex];
        List<Tile> tilesInHand = currentPlayer.getHand();
        List<Tile> tilesToPlay = new ArrayList<>();
        for (int index : indexes) {
            // Verify that the tile in position index is null
            if (tilesInHand.get(index) == null) {
                throw new QwirkleException("tuile nulle");
            }
            tilesToPlay.add(tilesInHand.get(index));
        }

        // Verify if the tile can be played and play them if they can be played

        int points = grid.add(row, col, d, tilesToPlay.toArray(new Tile[tilesToPlay.size()]));

        // Mise à jour du score du joueur actuel
        currentPlayer.addToScore(points);


        // Remove played tiles from player's hand
        for (Tile tile : tilesToPlay) {
            currentPlayer.remove(tile);
        }
        // Refill player's hand
        currentPlayer.refill();
        // Update current player index
        pass();
    }

    /**
     * place plusieurs tuiles aux endroits differents
     * @param is les index des tuiles a placer
     * @throws QwirkleException si le placement des tuiles est invalide
     */
    public void play(int... is) {

        // Get current player and their tiles
        Player currentPlayer = players[currentPlayerIndex];
        List<TileAtPosition> tiles = new ArrayList<>();
        for (int i = 0; i < is.length; i = i + 3) {
            tiles.add(new TileAtPosition(is[i], is[i + 1], getCurrentPlayerHand().get(is[i + 2])));
            players[currentPlayerIndex].remove(getCurrentPlayerHand().get(is[i + 2]));
        }
        // Check if the move is valid
        TileAtPosition[] line = tiles.toArray(new TileAtPosition[tiles.size()]);
        // Verify if the tile can be played and play them if they can be played
        int points = grid.add(tiles.stream().toArray(TileAtPosition[]::new));
        // Mise à jour du score du joueur actuel
        currentPlayer.addToScore(points);
        // Refill player's hand
        players[currentPlayerIndex].refill();
        // Update current player index
        pass();
    }

    /**
     * passe le tour joueur courant
     */
    public void pass() {
        if (this.currentPlayerIndex == this.players.length - 1) {
            this.currentPlayerIndex = 0;
        } else {
            this.currentPlayerIndex = currentPlayerIndex + 1;
        }
    }

    /**
     * @return la vue sur la grille de jeu actuelle
     */
    public GridView getGrid() {
        return new GridView(grid);
    }

    /**
     * @return le nom du joueur courant
     */
    public String getCurrentPlayerName() {
        return this.players[currentPlayerIndex].getName();
    }

    /**
     * @return la main du joueur courant
     */
    public List<Tile> getCurrentPlayerHand() {
        return this.players[currentPlayerIndex].getHand();
    }

    /**
     * Renvoie le score du joueur actuel.
     *
     * @return Le score du joueur actuel.
     */
    public int getCurrentPlayerScore() {
        return getCurrentPlayer().getScore();
    }


    /**
     * Renvoie le joueur actuel.
     *
     * @return Le joueur actuel.
     */
    public Player getCurrentPlayer(){
        return this.players[currentPlayerIndex];
    }

    /**
     * Vérifie si la partie est terminée.
     *
     * @return Vrai si la partie est terminée, sinon faux.
     */
    public boolean isOver() {
        List<Tile> hand = getCurrentPlayerHand();
        // Vérifier si le joueur a au moins une tuile en main
        if (hand.isEmpty()) {
            score=score+6;
            View.displayWinner(players,currentPlayerIndex);
            return false;
        }

        // Vérifier si le joueur peut placer une tuile sur la grille
        for (int row = 0; row < 90; row++) {
            for (int col = 0; col < 90; col++) {
                for (Tile tile : hand) {
                    if (grid.add(row, col, tile)>0) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Écrit l'état actuel de la partie dans un fichier.
     * @param fileName Le nom du fichier dans lequel écrire la partie.
     * @throws QwirkleException Si une erreur se produit lors de l'écriture de la partie dans le fichier.
     */
    public void write(String fileName) throws QwirkleException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(this);
        } catch (IOException e) {
            throw new QwirkleException("Erreur lors de l'écriture de la partie dans le fichier.");
        }
    }

    /**
     * Récupère une instance de la classe Game à partir d'un fichier.
     * @param fileName Le nom du fichier à partir duquel lire la partie.
     * @return Une instance de la classe Game récupérée à partir du fichier.
     * @throws QwirkleException Si une erreur se produit lors de la lecture de la partie à partir du fichier.
     */

    public static Game getFromFile(String fileName) throws QwirkleException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Game) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new QwirkleException("Erreur lors de la lecture de la partie à partir du fichier.");
        }
    }


    public Player[] getPlayers() {
        return players;
    }


}
