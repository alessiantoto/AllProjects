package g62098.dev3.oxono.Model.AI;

import g62098.dev3.oxono.Model.Game;
import g62098.dev3.oxono.Model.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements AIStrategy {
    private Random random = new Random();

    @Override
    public int[] chooseTotemMove(Symbol totemSymbol, Game game) {
        int totemX, totemY;

        // Use Game to find the totem's current position
        int[] Position = game.getBoard().findTotemPosition(totemSymbol);
        totemX = Position[0];
        totemY = Position[1];

        // Liste pour les déplacements valides
        List<int[]> validMoves = new ArrayList<>();

        // Parcourir toutes les cases du plateau
        for (int i = 0; i < game.getBoard().getGrid().length; i++) {
            for (int j = 0; j < game.getBoard().getGrid()[0].length; j++) {
                // Vérifiez si le déplacement est valide avec checkMoveValidity
                if (game.checkMoveValidity(totemSymbol, i, j)) {
                    validMoves.add(new int[]{i, j});
                }
            }
        }

        // Si des déplacements valides existent, choisir un au hasard
        if (!validMoves.isEmpty()) {
            return validMoves.get(random.nextInt(validMoves.size()));
        } else {
            // Si aucun déplacement valide n'a été trouvé, retourner la position actuelle
            return new int[]{totemX, totemY};
        }
    }

    @Override
    public int[] choosePlacePiece(Symbol pieceSymbol, Game game) {
        List<int[]> validPlacements = new ArrayList<>();
        int[] totemPosition = game.getBoard().findTotemPosition(pieceSymbol);
        int totemX = totemPosition[0];
        int totemY = totemPosition[1];

        // Vérifier si toutes les pièces adjacentes au totem sont occupées
        if (game.allAdjacentPiecesOccupied(pieceSymbol)) {
            // Toutes les positions adjacentes sont occupées : parcourir tout le plateau
            for (int i = 0; i < game.getBoard().getGrid().length; i++) {
                for (int j = 0; j < game.getBoard().getGrid()[0].length; j++) {
                    // Ajouter toutes les positions vides (null) à la liste
                    if (game.getBoard().getGrid()[i][j].getOccupiedBy() == null) {
                        validPlacements.add(new int[]{i, j});
                    }
                }
            }
        } else {
            // Parcourir uniquement les positions adjacentes au totem
            List<int[]> adjacentPositions = game.getAdjacentPositions(totemX, totemY);
            for (int[] position : adjacentPositions) {
                int adjX = position[0];
                int adjY = position[1];
                if (game.checkPlaceValidity(pieceSymbol, adjX, adjY)) {
                    validPlacements.add(position);
                }
            }
        }

        // Si des positions valides existent, choisir une position au hasard
        if (!validPlacements.isEmpty()) {
            return validPlacements.get(random.nextInt(validPlacements.size()));
        }

        // Si aucune position valide n'existe, retourner null ou gérer différemment
        System.out.println("Aucun emplacement valide trouvé pour le symbole " + pieceSymbol);
        return null;
    }


}
