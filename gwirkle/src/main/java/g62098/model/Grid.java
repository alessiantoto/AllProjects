    package g62098.model;

    import java.util.*;
    import java.io.Serializable;
    /**
     *La classe Grid modélise la grille de jeu Qwirkle.
     */
    public class Grid implements Serializable{
        private final int nombreLignes=90;
        private final int nombreColonnes=90;
        private Tile[][] tiles;
        private boolean isEmpty;
        /**
         *Constructeur de la classe Grid.
         */
        public Grid() {
            tiles = new Tile[nombreLignes][nombreColonnes];
            isEmpty = true;
        }

        /**
         *@return true si la grille est vide, sinon false.
         */
        public boolean isEmpty() {
            return isEmpty;
        }

        /**
         * Retourne la tuile à la position spécifiée.
         * @param row ligne de la tuile
         * @param col colonne de la tuile
         * @return la tuile a la position de la ligne et la colonne, ou null si la position est hors de la grille.
         */
        public Tile get(int row, int col) {
            if (row >= 0 && row < nombreLignes && col >= 0 && col < nombreColonnes) {
                return tiles[row][col];
            }
            return null;
        }

        /**
         *Vérifie si toutes les tuiles ont la même couleur ou la même forme.
         *@param
         *@return true si toutes les tuiles ont la même couleur ou la même forme, sinon false.
         */
        private boolean verifyColorForm(Tile... tiles) {
            Set<Color> colors = new HashSet<>();
            Set<Shape> shapes = new HashSet<>();

            Set<Tile> uniqueTiles = new HashSet<>();
            for (Tile tile : tiles) {
                colors.add(tile.color());      //Cette ligne ajoute la couleur de la tuile actuelle à l'ensemble des couleurs.
                shapes.add(tile.shape());       //Cette ligne ajoute la forme de la tuile actuelle à l'ensemble des formes.

                if (!uniqueTiles.add(tile)) {   //Cette condition vérifie si la tuile actuelle a déjà été ajoutée à l'ensemble
                    return false; // Une tuile identique a déjà été rencontrée
                }
            }
            return colors.size() == 1 || shapes.size() == 1;

            /*
            Après avoir parcouru toutes les tuiles, cette ligne vérifie si toutes les tuiles ont la même couleur ou la même forme.
             Si c'est le cas, la méthode renvoie true, sinon elle renvoie false.
             */
        }

        /**
         * Vérifie si deux tuiles ont la meme couleur ou forme.
         *
         * @param tile1 La première tuile à comparer.
         * @param tile2 La deuxième tuile à comparer.
         * @return true si les tuiles ont une correspondance de couleur ou de forme, false sinon.
         */
        private boolean verifyColorForm(Tile tile1, Tile tile2) {
            if (tile1 == null || tile2 == null) {
                return false;
            }

            Color color1 = tile1.color();
            Color color2 = tile2.color();
            Shape shape1 = tile1.shape();
            Shape shape2 = tile2.shape();

            return color1.equals(color2) || shape1.equals(shape2);
        }


        /**
         * Vérifie si une tuile identique à celle donnée existe dans la même ligne ou colonne à une position donnée.
         *
         * @param row  La position de la ligne.
         * @param col  La position de la colonne.
         * @param tile La tuile à comparer.
         * @return true si une tuile identique à celle donnée existe dans la même ligne ou colonne, false sinon.
         */
        private boolean hasIdenticalTileRowCol(int row, int col, Tile tile) {
            // Check for duplicate traits in the row (left and right)
            for (int c = col - 1; c >= 0; c--) {
                if (tiles[row][c] == null) {
                    break; // Stop checking in the left direction
                }
                if (tiles[row][c].color().equals(tile.color()) && tiles[row][c].shape().equals(tile.shape())) {
                    return true;
                }
            }
            for (int c = col + 1; c < nombreColonnes; c++) {
                if (tiles[row][c] == null) {
                    break; // Stop checking in the right direction
                }
                if (tiles[row][c].color().equals(tile.color()) && tiles[row][c].shape().equals(tile.shape())) {
                    return true;
                }
            }

            // Check for duplicate traits in the column (up and down)
            for (int r = row - 1; r >= 0; r--) {
                if (tiles[r][col] == null) {
                    break; // Stop checking in the up direction
                }
                if (tiles[r][col].color().equals(tile.color()) && tiles[r][col].shape().equals(tile.shape())) {
                    return true;
                }
            }
            for (int r = row + 1; r < nombreLignes; r++) {
                if (tiles[r][col] == null) {
                    break; // Stop checking in the down direction
                }
                if (tiles[r][col].color().equals(tile.color()) && tiles[r][col].shape().equals(tile.shape())) {
                    return true;
                }
            }

            return false;
        }

        public boolean cantPlaceBetweenIdentical(int row, int col) {
            Tile above = tiles[row - 1][col];
            Tile below = tiles[row + 1][col];
            Tile left = tiles[row][col - 1];
            Tile right = tiles[row][col + 1];

            if ((above == null && below == null) && (left == null && right == null)) {
                return false;
            }

            if (above != null && below != null && above.color() != null && below.color() != null && above.shape()!=null && below.shape()!=null &&
                    (above.color().equals(below.color()) && above.shape().equals(below.shape())) ) {
                return true;
            }

            if (left != null && right != null && left.color() != null && right.color() != null && left.shape()!=null && right.shape()!=null &&
                    (left.color().equals(right.color()) && left.shape().equals(right.shape())) ) {
                return true;
            }
            return false;
        }

        /**
         * Vérifie s'il est impossible de placer une tuile entre deux tuiles identiques dans les directions verticales et horizontales à une position donnée.
         *
         * @param row La position de la ligne.
         * @param col La position de la colonne.
         * @return true s'il est impossible de placer une tuile entre deux tuiles identiques dans les directions verticales et horizontales, false sinon.
         */

        public boolean cantPlaceBetweenIdenticalDirection(int row, int col) {
            Tile above = tiles[row - 1][col];
            Tile below = tiles[row + 1][col];
            Tile left = tiles[row][col - 1];
            Tile right = tiles[row][col + 1];

            if ((above == null && below == null) && (left == null && right == null)) {
                return false;
            }

            if (above != null && below != null && above.color() != null && below.color() != null && above.shape()!=null && below.shape()!=null &&
                    (above.color().equals(below.color()) && above.shape().equals(below.shape())) ) {
                return true;
            }

            if (left != null && right != null && left.color() != null && right.color() != null && left.shape()!=null && right.shape()!=null &&
                    (left.color().equals(right.color()) && left.shape().equals(right.shape())) ) {
                return true;
            }
            return false;
        }

        public boolean cantPlaceBetweenNoCommonTraits(int row, int col) {
            Tile above = tiles[row - 1][col];
            Tile below = tiles[row + 1][col];
            Tile left = tiles[row][col - 1];
            Tile right = tiles[row][col + 1];

            if ((above == null || below == null) && (left == null || right == null)) {
                return false;
            }

            if (above != null && below != null && above.color() != null && !verifyColorForm(above, below)) {
                return true;
            }

            if (left != null && right != null && left.color() != null && !verifyColorForm(left,right) ) {
                return true;
            }

            return false;
        }

        public boolean cantPlaceBetweenNoCommonTraitsTileAtPositon(int row, int col) {
            Tile above = tiles[row - 1][col];
            Tile below = tiles[row + 1][col];
            Tile left = tiles[row][col - 1];
            Tile right = tiles[row][col + 1];

            if ((above == null || below == null) && (left == null || right == null)) {
                return false;
            }

            if (above != null && below != null && above.color() != null && !verifyColorForm(above, below)) {
                return true;
            }

            if (left != null && right != null && left.color() != null && !verifyColorForm(left,right) ) {
                return true;
            }

            return false;
        }

        /**
         * Vérifie s'il est impossible de placer une tuile entre deux tuiles n'ayant aucun trait commun dans les directions verticales et horizontales à une position donnée.
         *
         * @param row La position de la ligne.
         * @param col La position de la colonne.
         * @return true s'il est impossible de placer une tuile entre deux tuiles n'ayant aucun trait commun dans les directions verticales et horizontales, false sinon.
         */
        public boolean cantPlaceBetweenIdenticalTileAtPosition(int row, int col) {
            Tile above = tiles[row - 1][col];
            Tile below = tiles[row + 1][col];
            Tile left = tiles[row][col - 1];
            Tile right = tiles[row][col + 1];

            if ((above == null || below == null) && (left == null || right == null)) {
                return false;
            }

            if (above != null && below != null &&
                    (above.color().equals(below.color()) && above.shape().equals(below.shape())) ) {
                return true;
            }

            if (left != null && right != null &&
                    (left.color().equals(right.color()) && left.shape().equals(right.shape())) ) {
                return true;
            }
            return false;
        }


        /**
         * Vérifie si les tuiles spécifiées sont adjacentes les unes aux autres.
         *
         * @param line Un ensemble de tuiles avec leur position.
         * @return true si toutes les tuiles sont adjacentes avec au moins un trait commun, false sinon.
         */
        private boolean areTilesAdjacent(TileAtPosition... line) {

            for (int i = 0; i < line.length - 1; i++) {
                TileAtPosition currentTile = line[i];
                TileAtPosition nextTile = line[i + 1];

                Tile current = currentTile.tile();
                Tile next = nextTile.tile();

                // Vérifier si les tuiles sont adjacentes horizontalement ou verticalement
                if (currentTile.row() == nextTile.row() && Math.abs(currentTile.col() - nextTile.col()) == 1) {
                    // Les tuiles sont adjacentes horizontalement, vérifier si elles ont un trait commun
                    if (!current.color().equals(next.color()) && !current.shape().equals(next.shape())) {
                        return false; // Les tuiles n'ont pas de trait commun
                    }
                } else if (currentTile.col() == nextTile.col() && Math.abs(currentTile.row() - nextTile.row()) == 1) {
                    // Les tuiles sont adjacentes verticalement, vérifier si elles ont un trait commun
                    if (!current.color().equals(next.color()) && !current.shape().equals(next.shape())) {
                        return false; // Les tuiles n'ont pas de trait commun
                    }
                } else {
                    return false; // Les tuiles ne sont pas adjacentes horizontalement ou verticalement
                }
            }

            return true; // Toutes les tuiles sont adjacentes avec un trait minimum en commun
        }

        /**
         * Calcule le nombre de points associés aux tuiles spécifiées.
         *
         * @param tiles Un ensemble de tuiles avec leur position.
         * @return Le nombre de points calculé pour les tuiles.
         */
        private int calculatePoints(TileAtPosition... tiles) {

            int points = 0;
            for (TileAtPosition tileAtPosition : tiles) {
                int row = tileAtPosition.row();
                int col = tileAtPosition.col();
                Tile tile = tileAtPosition.tile();

                // Calculer la longueur des adjacents horizontaux
                int horizontalLength = countHorizontalAdjacentPosition(col, row);

                // Calculer la longueur des adjacents verticaux
                int verticalLength = countVerticalAdjacentPosition(col, row);

                // Mettre à jour les points
                points +=  horizontalLength + verticalLength;

            }
            return points + tiles.length;
        }

        private int countHorizontalAdjacentPosition(int col, int row) {
            int count = 0;

            // Compter vers la gauche
            for (int i = col - 1; i >= 0; i--) {
                Tile adjacentTile = get(row, i);    //une méthode externe qui renvoie la tuile à la position donnée.
                if (adjacentTile != null) {
                    count++;
                } else {
                    break;
                }
            }

            // Compter vers la droite
            for (int i = col + 1; i < nombreColonnes; i++) {
                Tile adjacentTile = get(row, i);        //une méthode externe qui renvoie la tuile à la position donnée.
                if (adjacentTile != null){
                    count++;
                } else {
                    break;
                }
            }

            return count;
        }

        /**
         * Compte le nombre de tuiles adjacentes verticales à partir d'une position donnée.
         *
         * @param col L'indice de la colonne de départ.
         * @param row L'indice de la ligne de départ.
         * @return Le nombre de tuiles adjacentes verticales.
         */
        private int countVerticalAdjacentPosition(int col, int row) {
            int count = 0;

            // Compter vers le haut
            for (int i = row - 1; i >= 0; i--) {
                Tile adjacentTile = get(i, col);    //une méthode externe qui renvoie la tuile à la position donnée.
                if (adjacentTile != null) {
                    count++;
                } else {
                    break;
                }
            }

            // Compter vers le bas
            for (int i = row + 1; i < nombreLignes; i++) {
                Tile adjacentTile = get(i, col);    //une méthode externe qui renvoie la tuile à la position donnée.
                if (adjacentTile != null) {
                    count++;
                } else {
                    break;
                }
            }

            return count;
        }


        /**
         * Calcule le nombre de points associés à une tuile spécifique et sa position.
         *
         * @param row  L'indice de la ligne de la tuile.
         * @param col  L'indice de la colonne de la tuile.
         * @param tile La tuile pour laquelle calculer les points.
         * @return Le nombre de points calculé pour la tuile.
         */
        private int calculatePoints(int row, int col, Tile tile) {
            int points = 0;

            // Calculer la longueur des adjacents horizontaux
            int horizontalLength = countHorizontalAdjacent(col, row);

            // Calculer la longueur des adjacents verticaux
            int verticalLength = countVerticalAdjacent(col, row);

            // Mettre à jour les points
            points = 1+ horizontalLength + verticalLength;

            return points;
        }

        /**
         * Calcule le nombre de points associés à une direction donnée et une ligne de tuiles.
         *
         * @param row  L'indice de la ligne de départ.
         * @param col  L'indice de la colonne de départ.
         * @param d    La direction dans laquelle calculer les points.
         * @param line La ligne de tuiles pour laquelle calculer les points.
         * @return Le nombre de points calculé pour la direction et la ligne de tuiles.
         */
        private int calculatePointsDirection(int row, int col, Direction d,Tile...line){
            int points = 0;
            int count = 0;
            if(d==Direction.RIGHT || d==Direction.LEFT){
                count = countHorizontalAdjacent(col,row);
                for (Tile tile : line) {
                    int verticalLength = countVerticalAdjacent(col, row);
                    count += verticalLength;
                    row += d.getDeltaRow();
                    col += d.getDeltaCol();
                }
            }
            else if (d==Direction.UP || d==Direction.DOWN){
                count=countVerticalAdjacent(col,row);
                for (Tile tile: line){
                    int horizontalLength = countHorizontalAdjacent(col,row);
                    count += horizontalLength;
                    row += d.getDeltaRow();
                    col += d.getDeltaCol();
                }
            }

            // Ajouter la longueur des adjacents dans la direction spécifiée au nombre total de points
            points = 1 + count;

            return points;
        }

        /**
         * Compte le nombre de tuiles adjacentes horizontales à partir d'une position donnée.
         *
         * @param col L'indice de la colonne de départ.
         * @param row L'indice de la ligne de départ.
         * @return Le nombre de tuiles adjacentes horizontales.
         */
        private int countHorizontalAdjacent(int col, int row) {
            int count = 0;

            // Compter vers la gauche
            for (int i = col - 1; i >= 0; i--) {
                Tile adjacentTile = get(row, i);
                if (adjacentTile != null) {
                    count++;
                } else {
                    break;
                }
            }

            // Compter vers la droite
            for (int i = col + 1; i < nombreColonnes; i++) {
                Tile adjacentTile = get(row, i);
                if (adjacentTile != null) {
                    count++;
                } else {
                    break;
                }
            }

            return count;
        }

        /**
         * Compte le nombre de tuiles adjacentes verticales à partir d'une position donnée.
         *
         * @param col L'indice de la colonne de départ.
         * @param row L'indice de la ligne de départ.
         * @return Le nombre de tuiles adjacentes verticales.
         */
        private int countVerticalAdjacent(int col, int row) {
            int count = 0;

            // Compter vers le haut
            for (int i = row - 1; i >= 0; i--) {
                Tile adjacentTile = get(i, col);
                if (adjacentTile != null) {
                    count++;
                } else {
                    break;
                }
            }

            // Compter vers le bas
            for (int i = row + 1; i < nombreLignes; i++) {
                Tile adjacentTile = get(i, col);
                if (adjacentTile != null) {
                    count++;
                } else {
                    break;
                }
            }

            return count;
        }

        /**
         * Calcule les points pour une première addition de tuiles à une ligne.
         * @param line Les tuiles ajoutées à la ligne.
         * @return Le nombre de points calculé pour l'ajout initial de tuiles.
         */
        private int calculPointsFirstAdd(Tile...line){
            return line.length;
        }



        /**
         *Place les tuiles spécifiées sur la grille, dans la direction et à partir de la ligne spécifiées.
         *Cette méthode doit être utilisée uniquement une fois au début du jeu.
         *@param d la direction dans laquelle placer les tuiles.
         *@param line les tuiles à placer.
         *@return le nombre de points obtenus
         */
        public int firstAdd(Direction d, Tile... line) {
            if (line.length > 6) {
                throw new QwirkleException("Vous ne pouvez pas ajouter plus de 6 tuiles à la fois");
            }
            int row = 45;
            int col = 45;
            // Verify that all tiles are placed on empty squares
            if (!isEmpty) {
                throw new QwirkleException("Vous pouvez utiliser cette methode que en debut de partie");
            }

            // Verify that all tiles are in the same row or column
            row = 45;
            col = 45;
            int row2 = row - d.getDeltaRow();
            int col2 = col - d.getDeltaCol();
            boolean sameRow = row == row2;
            boolean sameCol = col == col2;
            for (int i = 1; i < line.length; i++) {
                row2 += d.getDeltaRow();
                col2 += d.getDeltaCol();
                if ((sameRow && row2 != row) || (sameCol && col2 != col)) {
                    throw new QwirkleException("Toutes les tuiles doivent être sur la même ligne ou colonne.");
                }
            }

            // Verify that all tiles have the same color or the same shape
            if (line.length > 1) {
                if (!verifyColorForm(line)) {
                    throw new QwirkleException("Tuile identique ou pas meme shape ou color");
                }
            }

            // Add the tiles to the board
            row = 45;
            col = 45;
            for (Tile x : line) {
                tiles[row][col] = x;
                row += d.getDeltaRow();
                col += d.getDeltaCol();
            }
        isEmpty=false;
            return calculPointsFirstAdd(line);
        }



        /**
         *
         * @param row la ligne ou on veut placer la tuile
         * @param col la colonne ou on veut placer la tuile
         * @param tile la tuile qui doit etre place
         * @return le nombre de points obtenus
         * @throws QwirkleException si la case spécifiée n'est pas vide, si la tuile n'est pas adjacente à une autre tuile ou si la tuile n'a pas la même couleur ou la même forme qu'une autre tuile adjacente.
         */
        public int add(int row, int col,  Tile tile) {
            // Verify that the tile is placed on an empty square
            if (tiles[row][col] != null) {
                throw new QwirkleException("La case spécifiée contient déjà une tile.");
            }
            // Verify that the tile is adjacent to at least one other tile
            boolean adjacent = false;
            Tile left = get(row, col - 1);
            Tile right = get(row, col + 1);
            Tile up = get(row - 1, col);
            Tile down = get(row + 1, col);

            if (left != null || right != null || up != null || down != null) {
                adjacent = true;
            }
            if (!adjacent) {
                throw new QwirkleException("La tile doit être placée à côté d'au moins une autre tile.");
            }

            // Verify that the tile has the same color or the same shape as at least one adjacent tile
            boolean sameColorOrShape=false;

            if(left!=null && verifyColorForm(left,tile)){
                sameColorOrShape = true;
            }

            if(right!=null && verifyColorForm(right,tile)){
                sameColorOrShape = true;
            }

            if(up!=null && verifyColorForm(up,tile)){
                sameColorOrShape = true;
            }

            if(down!=null && verifyColorForm(down,tile)){
                sameColorOrShape = true;
            }

            if (!sameColorOrShape) {
                throw new QwirkleException("La tuile doit avoir la même couleur ou la même forme qu'une tuile adjacente.");
            }

            if (hasIdenticalTileRowCol(row, col, tile)) {
                throw new QwirkleException("Il y a des doublons dans la ligne/colonne ou vous voulez placer");
            }

            if(cantPlaceBetweenIdentical(row,col)){
                throw new QwirkleException("Vous pouvez rien placer entre 2 tuiles identiques");
            }

            if(cantPlaceBetweenNoCommonTraits(row,col)){
                throw new QwirkleException("Vous ne pouvez rien placer entre 2 tuiles qui ne partagent aucune caracteristique");
            }


            // Add the tile to the board
            if (sameColorOrShape) {
                tiles[row][col] = tile;
            } else {
                throw new QwirkleException("Il y a une tuile adjacente à côté mais ce n'est pas la même couleur ou forme, ou il y a une tuile identique dans la ligne ou colonne des tuiles collées.");
            }
            isEmpty=false;

            //returne le nombre de points obtenus
            return calculatePoints(row, col, tile);
        }

        /**
         * Vérifie si une tuile a un voisin avec la même couleur ou la même forme.
         *
         * @param tile La tuile à vérifier.
         * @param row  L'indice de ligne de la tuile.
         * @param col  L'indice de colonne de la tuile.
         * @return true si la tuile a au moins deux voisins avec la même couleur ou la même forme, sinon false.
         */
        private boolean hasNeighborWithSameColorOrShape(Tile tile, int row, int col) {
            Tile left = (col > 0) ? get(row, col - 1) : null;
            Tile right = (col < nombreColonnes - 1) ? get(row, col + 1) : null;
            Tile up = (row > 0) ? get(row - 1, col) : null;
            Tile down = (row < nombreLignes - 1) ? get(row + 1, col) : null;

            int sameColorCount = 0;
            int sameShapeCount = 0;

            if (left != null) {
                if (verifyColorForm(left, tile)) {
                    sameColorCount++;
                    sameShapeCount++;
                }
            }
            if (right != null) {
                if (verifyColorForm(right, tile)) {
                    sameColorCount++;
                    sameShapeCount++;
                }
            }
            if (up != null) {
                if (verifyColorForm(up, tile)) {
                    sameColorCount++;
                    sameShapeCount++;
                }
            }
            if (down != null) {
                if (verifyColorForm(down, tile)) {
                    sameColorCount++;
                    sameShapeCount++;
                }
            }

            return (sameColorCount > 1) || (sameShapeCount > 1);
        }




        /**
         *
         * @param row la ligne ou on veut placer la tuile
         * @param col la colonne ou on veut placer la tuile
         * @param d la direction dans laquelle on veut placer a partir de la position de la premiere tuile
         * @param line les tuiles qui doivent etre places(autant qu'il y en a)
         * @return le nombre de points obtenus
         */

        public int add(int row, int col, Direction d, Tile... line) {
            if(isEmpty){
                throw new QwirkleException("firstAdd doit etre appele avant");
            }
            int curRow = row;
            int curCol = col;
            Map<Tile, Integer> tileCounts = new HashMap<>(); // Utilisation d'un HashMap pour compter le nombre de fois qu'une tuile est utilisée
            for (Tile x : line) {
                if (tiles[curRow][curCol] != null) {
                    throw new QwirkleException("La place ou vous voulez placer la tuile est deja prise");
                }
                if (tileCounts.containsKey(x) && tileCounts.get(x) >= 3) { // Vérification si une tuile a été utilisée plus de 3 fois
                    throw new QwirkleException("La tuile " + x + " a été utilisée plus de 3 fois.");
                }
                tileCounts.put(x, tileCounts.getOrDefault(x, 0) + 1); // Incrémentation du nombre d'occurrences de la tuile dans le HashMap
                curRow += d.getDeltaRow();
                curCol += d.getDeltaCol();
            }
            // Verify that all tiles are placed on empty squares
            int checkRow = row;
            int checkCol = col;
            for (Tile x : line) {
                if (get(checkRow, checkCol) != null) {
                    throw new QwirkleException("Toutes les tuiles doivent être placées sur des cases vides.");
                }
                checkRow += d.getDeltaRow();
                checkCol += d.getDeltaCol();
            }

            // Verify that at least one tile is adjacent to another tile
            boolean adjacentTileFound = false;
            curRow = row;
            curCol = col;

            for (Tile tile : line) {
                Tile left = (curCol > 0) ? get(curRow, curCol - 1) : null;
                Tile right = (curCol < nombreColonnes - 1) ? get(curRow, curCol + 1) : null;
                Tile up = (curRow > 0) ? get(curRow - 1, curCol) : null;
                Tile down = (curRow < nombreLignes - 1) ? get(curRow + 1, curCol) : null;

                if (left != null || right != null || up != null || down != null) {
                    adjacentTileFound = true;
                    break; // Sortir de la boucle dès qu'une tuile adjacente est trouvée
                }

                // Move to the next position based on the direction
                curRow += d.getDeltaRow();
                curCol += d.getDeltaCol();
            }
            if (!adjacentTileFound) {
                throw new QwirkleException("Au moins l'une des tuiles doit être placée à côté d'une autre tuile.");
            }


            // Verify that at least one tile has a common trait with adjacent tiles
            boolean atLeastOneTileHasCommonTrait = false;
            curRow = row;
            curCol = col;

            for (Tile x : line) {
                boolean tileHasCommonTrait = false;

                Tile left = (curCol > 0) ? get(curRow, curCol - 1) : null;
                Tile right = (curCol < nombreColonnes - 1) ? get(curRow, curCol + 1) : null;
                Tile up = (curRow > 0) ? get(curRow - 1, curCol) : null;
                Tile down = (curRow < nombreLignes - 1) ? get(curRow + 1, curCol) : null;

                if ((left != null && verifyColorForm(left, x)) ||
                        (right != null && verifyColorForm(right, x)) ||
                        (up != null && verifyColorForm(up, x)) ||
                        (down != null && verifyColorForm(down, x))) {
                    tileHasCommonTrait = true;
                }

                if (tileHasCommonTrait) {
                    atLeastOneTileHasCommonTrait = true;
                    break; // At least one tile has a common trait, no need to check further
                }

                // Mise à jour des coordonnées de la tuile pour l'itération suivante
                curRow += d.getDeltaRow();
                curCol += d.getDeltaCol();
            }

            if (!atLeastOneTileHasCommonTrait) {
                throw new QwirkleException("Les tuiles que vous voulez ajouter n'ont pas de trait commun avec les tuiles adjacentes.");
            }

            // Verify that all tiles are in the same row or column
            checkRow = row;
            checkCol = col;
            int checkRow2 = row - d.getDeltaRow();
            int checkCol2 = col - d.getDeltaCol();
            boolean sameRow = checkRow == checkRow2;
            boolean sameCol = checkCol == checkCol2;
            for (int i = 1; i < line.length; i++) {
                checkRow2 += d.getDeltaRow();
                checkCol2 += d.getDeltaCol();
                if ((sameRow && checkRow2 != checkRow) || (sameCol && checkCol2 != checkCol)) {
                    throw new QwirkleException("Toutes les tuiles doivent être sur la même rangée ou la même colonne.");
                }
            }

            // Vérification que toutes les tuiles de la ligne ont les mêmes couleurs ou les mêmes formes
            Tile firstTile = line[0];
            boolean sameColorOrShape = true;

            for (int i = 1; i < line.length; i++) {
                if (!verifyColorForm(firstTile, line[i])) {
                    sameColorOrShape = false;
                    break;
                }
            }

            if (!sameColorOrShape) {
                throw new QwirkleException("Toutes les tuiles de la ligne doivent avoir les mêmes couleurs ou les mêmes formes.");
            }

            //verifie si dans la line est une tuile identique que les tuiles du grid adjacentes
            curRow=row;
            curCol=col;
            boolean estEntre2identiques=false;
            for(Tile tile:line) {
                if (cantPlaceBetweenIdenticalDirection(curRow,curCol)) {
                    estEntre2identiques=true;
                    break;
                }
                // Mise à jour des coordonnées de la tuile pour l'itération suivante
                curRow += d.getDeltaRow();
                curCol += d.getDeltaCol();
            }
            if(estEntre2identiques){
                throw new QwirkleException("Une des tuiles que vous voulez placer est entre 2 tuiles identiques");
            }

            // Vérification supplémentaire : si toutes les tuiles ont un voisin avec la même couleur ou la même forme, elles ne peuvent pas avoir cette couleur ou cette forme
            boolean hasNeighborWithSameTrait = false;

            if (d == Direction.UP || d == Direction.DOWN) {
                Tile left = (col > 0) ? get(row, col - 1) : null;
                Tile right = (col < nombreColonnes - 1) ? get(row, col + 1) : null;

                if (hasNeighborWithSameColorOrShape(left, row, col) || hasNeighborWithSameColorOrShape(right, row, col)) {
                    hasNeighborWithSameTrait = true;
                }
            } else if (d == Direction.LEFT || d == Direction.RIGHT) {
                Tile up = (row > 0) ? get(row - 1, col) : null;
                Tile down = (row < nombreLignes - 1) ? get(row + 1, col) : null;

                if (hasNeighborWithSameColorOrShape(up, row, col) || hasNeighborWithSameColorOrShape(down, row, col)) {
                    hasNeighborWithSameTrait = true;
                }
            }

            if (hasNeighborWithSameTrait) {
                for (Tile tile : line) {
                    if (hasNeighborWithSameColorOrShape(tile, row, col)) {
                        throw new QwirkleException("Les tuiles ne peuvent pas avoir la même couleur ou la même forme que leurs voisins.");
                    }
                }
            }
            curRow = row;
            curCol = col;
            boolean estEntre2sansCommun =false;
            for (Tile tile : line) {
                if (cantPlaceBetweenNoCommonTraits(row, col)) {
                    estEntre2sansCommun=true;
                    curRow += d.getDeltaRow();
                    curCol += d.getDeltaCol();
                }
            }
            if(estEntre2sansCommun){
                throw new QwirkleException("Vous essayez de placer une tuile entre 2 tuiles qui ne partagent aucun trait");
            }


            // Placement des tuiles
            curRow = row;
            curCol = col;

            for (Tile x : line) {
                tiles[curRow][curCol] = x;
                tileCounts.put(x, tileCounts.getOrDefault(x, 0) + 1); // Incrémentation du nombre d'occurrences de la tuile dans le HashMap

                curRow += d.getDeltaRow();
                curCol += d.getDeltaCol();
            }
            return calculatePointsDirection(row, col, d, line);

        }

        /**
         * Vérifie s'il y a un trou entre les tuiles dans une ligne ou une colonne.
         * @param line Les tuiles à vérifier.
         * @return true s'il y a un trou entre les tuiles dans la ligne ou la colonne, sinon false.
         */
        private boolean hasNullBetween(TileAtPosition... line) {
            if (line.length == 1) {
                return false; // Une ligne ou une colonne d'une seule tuile peut contenir un trou
            }

            int row = line[0].row();
            int col = line[0].col();

            // Place les tuiles sur le plateau de jeu
            for (TileAtPosition x : line) {
                int curRow = x.row();
                int curCol = x.col();
                if (curRow >= 0 && curRow < tiles.length && curCol >= 0 && curCol < tiles[0].length) {
                    tiles[curRow][curCol] = x.tile();
                }
            }

            // Vérification de la présence de trous dans la ligne ou la colonne
            boolean hasNullInRow = false;
            boolean hasNullInCol = false;

            if (line[0].row() == line[1].row()) {
                // Les tuiles sont dans la même ligne, vérifier les trous entre les colonnes
                int minCol = line[0].col();
                int maxCol = line[0].col();
                for (int i = 1; i < line.length; i++) {
                    if (line[i].col() < minCol) {
                        minCol = line[i].col();
                    } else if (line[i].col() > maxCol) {
                        maxCol = line[i].col();
                    }
                }
                for (int c = minCol + 1; c < maxCol; c++) {
                    if (tiles[row][c] == null) {
                        hasNullInRow = true;
                        break;
                    }
                }
            }
            if (line[0].col() == line[1].col()) {
                // Les tuiles sont dans la même colonne, vérifier les trous entre les lignes
                int minRow = line[0].row();
                int maxRow = line[0].row();
                for (int i = 1; i < line.length; i++) {
                    if (line[i].row() < minRow) {
                        minRow = line[i].row();
                    } else if (line[i].row() > maxRow) {
                        maxRow = line[i].row();
                    }
                }
                for (int r = minRow + 1; r < maxRow; r++) {
                    if (tiles[r][col] == null) {
                        hasNullInCol = true;
                        break;
                    }
                }
            }

            // Remet les positions correspondantes à null
            for (TileAtPosition x : line) {
                int curRow = x.row();
                int curCol = x.col();
                if (curRow >= 0 && curRow < tiles.length && curCol >= 0 && curCol < tiles[0].length) {
                    tiles[curRow][curCol] = null;
                }
            }

            if (hasNullInRow || hasNullInCol) {
                return true; // Il y a un trou dans la ligne ou la colonne
            }

            return false;
        }

        /**
         *Ajoute une ligne de tuiles à la grille de jeu, si les cases ou on veut placer sont vides.
         @param line la ligne de tuiles à ajouter
         @throws QwirkleException si la ligne de tuiles n'est pas valide
         @return le nombre de points obtenus
         */
        public int add(TileAtPosition... line) {
            // Verify that all tiles are placed on empty squares
            for (TileAtPosition tile : line) {
                int row = tile.row();
                int col = tile.col();
                if (tiles[row][col] != null) {
                    throw new QwirkleException("Toutes les tuiles doivent être placées sur des cases vides.");
                }
            }
            /*
            if (!areTilesAdjacent(line)) {
                throw new QwirkleException("Vous ne pouvez pas placer car les tuiles à côté ne partagent aucun trait");
            }


             */
            // Verify si le placement laisse un vide

            if (hasNullBetween(line)) {
                throw new QwirkleException("Cela crée un trou dans la ligne ou la colonne.");
            }

            // Verify that all tiles are adjacent to at least one tile with the same shape or color
            boolean foundAdjacent = false;
            for (TileAtPosition tile : line) {
                int row = tile.row();
                int col = tile.col();
                Tile left = get(row, col - 1);
                Tile right = get(row, col + 1);
                Tile up = get(row - 1, col);
                Tile down = get(row + 1, col);
                if ((left != null && verifyColorForm(left, tile.tile()))
                        || (right != null && verifyColorForm(right, tile.tile()))
                        || (up != null && verifyColorForm(up, tile.tile()))
                        || (down != null && verifyColorForm(down, tile.tile()))) {
                    foundAdjacent = true;
                    break;
                    }
                }

                if (!foundAdjacent) {
                    throw new QwirkleException("Toutes les tuiles doivent être adjacentes à au moins une autre tuile de même forme ou de même couleur.");
                }




                /////////fixe ca en bas
                /*
                // Verify that all tiles are in the same row or column
                boolean sameRow = true;
                boolean sameCol = true;
                int row1 = line[0].row();
                int col1 = line[0].col();
                for (int i = 1; i < line.length; i++) {
                    int row2 = line[i].row();
                    int col2 = line[i].col();
                    if (row1 != row2) {
                        sameRow = false;
                    }
                    if (col1 != col2) {
                        sameCol = false;
                    }
                }
                if (!sameRow && !sameCol) {
                    throw new QwirkleException("Toutes les tuiles doivent être placées sur la même ligne ou la même colonne.");
                }

                // Verify that all tiles have the same color or the same shape
                boolean sameShapeOrColor = false;
                if (verifyColorFormFinal(line)) {
                    sameShapeOrColor = true;
                }
                if (!sameShapeOrColor) {
                    throw new QwirkleException("Toutes les tuiles doivent avoir la même couleur ou la même forme.");
                }
                 */






                for (TileAtPosition elem : line) {
                    if (cantPlaceBetweenIdenticalTileAtPosition(elem.row(), elem.col())) {
                        throw new QwirkleException("Vous pouvez rien placer entre 2 tuiles identiques");
                    }
                }

            for (TileAtPosition elem : line) {
                if (cantPlaceBetweenNoCommonTraitsTileAtPositon(elem.row(), elem.col())) {
                    throw new QwirkleException("Vous pouvez rien placer entre 2 tuiles qui ne partagent aucun trait");
                }
            }

                // Verify that there are no duplicate tiles on the same row or column

                // Place les tuiles sur le plateau de jeu
                for (TileAtPosition x : line) {
                    int curRow = x.row();
                    int curCol = x.col();
                    if (curRow >= 0 && curRow < tiles.length && curCol >= 0 && curCol < tiles[0].length) {
                        tiles[curRow][curCol] = x.tile();
                    }
                }
                return calculatePoints(line);
            }
    }