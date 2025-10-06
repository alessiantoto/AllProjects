package model.model.esi.pbt.qwirkle.model;


import g62098.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static g62098.model.Color.*;
import static g62098.model.Direction.*;
import static g62098.model.Shape.*;
import static model.model.esi.pbt.qwirkle.model.testUtils.*;

class GridTest implements Serializable {
    private Grid grid;
    @BeforeEach

    void setUp() {
        grid = new Grid();
    }

    @Test
    void rules_sonia_a() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        assertEquals(t1, grid.get(45,45));
        assertEquals(t2, grid.get(44,45));
        assertEquals(t3, grid.get(43,45));
    }

    @Test
    void rules_sonia_a_adapted_to_fail() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, DIAMOND);
        assertThrows(QwirkleException.class, ()->{
            grid.firstAdd(UP, t1, t2, t3);
        });
        assertNull(grid.get(0,0));
        assertNull(grid.get(-1,0));
        assertNull(grid.get(-2,0));
    }

    @Test
    void firstAdd_cannot_be_called_twice() {
        Tile redcross = new Tile(RED, CROSS);
        Tile reddiamond = new Tile(RED, DIAMOND);
        grid.firstAdd(UP, redcross, reddiamond);
        assertThrows(QwirkleException.class, () -> grid.firstAdd(DOWN, redcross, reddiamond));
    }

    @Test
    void firstAdd_must_be_called_first_simple() {
        Tile redcross = new Tile(RED, CROSS);
        assertThrows(QwirkleException.class, () -> grid.add(0, 0, redcross));
    }

    @Test
    @DisplayName("get outside the grid should return null, not throw")
    void can_get_tile_outside_virtual_grid() {
        var g = new Grid();
        assertDoesNotThrow(() -> g.get(-250, 500));
        assertNull(g.get(-250, 500));
    }

    public static final int INITIAL_ROW = 45;
    public static final int INITIAL_COL = 45;

    @Test
    void gridInitiallyEmpty() {
        var g = new Grid();
        for (int row = -45; row < 45; row++) {
            for (int col = -45; col < 45; col++) {
                assertNull(get(g, row, col));
            }
        }
    }

    @Test
    @DisplayName("get outside the grid should return null, not throw")
    void canGetOutsideVirtualGrid() {
        var g = new Grid();
        assertDoesNotThrow(() -> get(g, -250, 500));
        assertNull(get(g, -250, 500));
    }

    // simple adds

    @Test
    void addSimpleUP() {
        var g = new Grid();
        g.firstAdd(UP, TILE_RED_CROSS, TILE_RED_DIAMOND);
        assertSame(TILE_RED_CROSS, get(g, 0, 0));
        assertSame(TILE_RED_DIAMOND, get(g, -1, 0));
        assertNull(get(g, 1, 0));
        assertNull(get(g, 0, 1));
        assertNull(get(g, 0, -1));
    }

    @Test
    void addSimpleDOWN() {
        var g = new Grid();
        g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND);
        assertSame(TILE_RED_CROSS, get(g, 0, 0));
        assertSame(TILE_RED_DIAMOND, get(g, 1, 0));
        assertNull(get(g, -1, 0));
        assertNull(get(g, 0, 1));
        assertNull(get(g, 0, -1));
    }

    @Test
    void addSimpleLEFT() {
        var g = new Grid();
        g.firstAdd(LEFT, TILE_RED_CROSS, TILE_RED_DIAMOND);
        assertSame(TILE_RED_CROSS, get(g, 0, 0));
        assertSame(TILE_RED_DIAMOND, get(g, 0, -1));
        assertNull(get(g, 1, 0));
        assertNull(get(g, -1, 0));
        assertNull(get(g, 0, 1));
    }

    @Test
    void addSimpleRIGHT() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_DIAMOND);
        assertSame(TILE_RED_CROSS, get(g, 0, 0));
        assertSame(TILE_RED_DIAMOND, get(g, 0, 1));
        assertNull(get(g, 1, 0));
        assertNull(get(g, -1, 0));
        assertNull(get(g, 0, -1));
    }

    @Test
    void addSimpleDoubleShouldThrow() {
        var g = new Grid();
        for (Direction d : Direction.values()) {
            assertThrows(QwirkleException.class, () -> g.firstAdd(d, TILE_RED_CROSS, TILE_RED_CROSS));
            assertNull(get(g, 0, 0));
            assertNull(get(g, -1, 0));
            assertNull(get(g, 1, 0));
            assertNull(get(g, 0, -1));
            assertNull(get(g, 0, 1));
        }

    }

    // firstAdd must be called first

    @Test
    void addFirstCannotBeCalledTwice() {
        var g = new Grid();
        g.firstAdd(UP, TILE_RED_CROSS, TILE_RED_DIAMOND);
        assertThrows(QwirkleException.class, () -> g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND));
    }

    @Test
    void addFirstMustBeCalledFirst_dir() {
        var g = new Grid();
        assertThrows(QwirkleException.class, () -> add(g, 0, 0, DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND));
    }

    @Test
    void addFirstMustBeCalledFirst_tap() {
        var g = new Grid();
        assertThrows(QwirkleException.class, () -> g.add(createTileAtpos(0, 0, TILE_RED_CROSS)));
    }

    @Test
    void addFirstMustBeCalledFirst_simple() {
        var g = new Grid();
        assertThrows(QwirkleException.class, () -> add(g, 0, 0, TILE_RED_CROSS));
    }

    // neighbours

    @Test
    void aTileMustHaveNeighbours() {
        var g = new Grid();
        g.firstAdd(UP, TILE_RED_CROSS);
        assertThrows(QwirkleException.class, () -> add(g, 2, 0, TILE_RED_DIAMOND));
        assertNull(get(g, 2, 0));
    }


    // overwriting

    @Test
    void canNotAddTwiceAtTheSamePlace_equalTile() {
        var g = new Grid();
        g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND);
        assertThrows(QwirkleException.class, () -> add(g, 1, 0, TILE_RED_DIAMOND_2));
        assertSame(get(g, 1, 0), TILE_RED_DIAMOND);
    }

    @Test
    void canNotAddTwiceAtTheSamePlace_differentTile_simple() {
        var g = new Grid();
        g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND);
        assertThrows(QwirkleException.class, () -> add(g, 1, 0, TILE_RED_PLUS));
        assertSame(get(g, 1, 0), TILE_RED_DIAMOND);
    }

    @Test
    void canNotAddTwiceAtTheSamePlace_differentTile_dir() {
        var g = new Grid();
        g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND);
        assertThrows(QwirkleException.class, () -> add(g, 0, 0, DOWN, TILE_RED_PLUS, TILE_RED_STAR));
        assertSame(get(g, 0, 0), TILE_RED_CROSS);
        assertSame(get(g, 1, 0), TILE_RED_DIAMOND);
    }

    @Test
    void canNotAddTwiceAtTheSamePlace_differentTile_taps() {
        var g = new Grid();
        g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND);
        TileAtPosition tap1 = createTileAtpos(0, 0, TILE_RED_PLUS);
        TileAtPosition tap2 = createTileAtpos(1, 0, TILE_RED_STAR);
        assertThrows(QwirkleException.class, () -> g.add(tap1, tap2));
        assertSame(TILE_RED_CROSS, get(g, 0, 0));
        assertSame(TILE_RED_DIAMOND, get(g, 1, 0));
    }

    // must share common trait
    @Test
    void canNotAddIfNoCommonTrait_tap() {
        var g = new Grid();
        g.firstAdd(UP, TILE_RED_CROSS);
        var tap1 = createTileAtpos(0, 1, TILE_YELLOW_DIAMOND);
        assertThrows(QwirkleException.class, () -> g.add(tap1));
        assertSame(TILE_RED_CROSS, get(g, 0, 0));
        assertNull(get(g, 0, 1));
        assertNull(get(g, 1, 0));
    }

    @Test
    void canNotAddIfNoCommonTrait_simple() {
        var g = new Grid();
        g.firstAdd(UP, TILE_RED_CROSS);
        assertThrows(QwirkleException.class, () -> add(g, 0, 1, TILE_YELLOW_DIAMOND));
        assertSame(TILE_RED_CROSS, get(g, 0, 0));
        assertNull(get(g, 0, 1));
        assertNull(get(g, 1, 0));
    }

    @Test
    void canNotAddIfNoCommonTrait_dir() {
        var g = new Grid();
        g.firstAdd(UP, TILE_RED_CROSS);
        assertThrows(QwirkleException.class, () -> add(g, 0, 1, LEFT, TILE_YELLOW_DIAMOND));
        assertSame(TILE_RED_CROSS, get(g, 0, 0));
        assertNull(get(g, 0, 1));
        assertNull(get(g, 1, 0));
    }

    @Test
    void canNotCompleteToALineWithDifferentTraits_simple() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_RED_DIAMOND_2);
        add(g, 2, 0, TILE_RED_PLUS);

        add(g, 1, 2, TILE_GREEN_DIAMOND);
        add(g, 2, 2, TILE_YELLOW_DIAMOND);

        // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 share no trait
        for (var color : Color.values()) {
            for (var shape : Shape.values()) {
                assertThrows(QwirkleException.class, () -> add(g, 2, 1, new Tile(color, shape)));
                assertNull(get(g, 2, 1));
            }
        }
    }

    @Test
    void canNotCompleteToALineWithDifferentTraits_dir() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_RED_DIAMOND_2);
        add(g, 2, 0, TILE_RED_PLUS);

        add(g, 1, 2, TILE_GREEN_DIAMOND);
        add(g, 2, 2, TILE_YELLOW_DIAMOND);
        //RED PLUS   ....    YELLOW DIAMOND
        // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 share no trait
        for (var color : Color.values()) {
            for (var shape : Shape.values()) {
                for (Direction dir : Direction.values()) {
                    assertThrows(QwirkleException.class, () -> add(g, 2, 1, dir, new Tile(color, shape)));
                    assertNull(get(g, 2, 1));
                }
            }
        }
    }

    @Test
    void canNotCompleteToALineWithDifferentTraits_tap() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_RED_DIAMOND_2);
        add(g, 2, 0, TILE_RED_PLUS);

        add(g, 1, 2, TILE_GREEN_DIAMOND);
        add(g, 2, 2, TILE_YELLOW_DIAMOND);

        // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 share no trait
        for (var color : Color.values()) {
            for (var shape : Shape.values()) {
                assertThrows(QwirkleException.class, () -> g.add(createTileAtpos(2, 1, new Tile(color, shape))));
                assertNull(get(g, 2, 1));
            }
        }
    }

    // never identical
    @Test
    void canNotCompleteToALineWithIdenticalTiles_simple() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_RED_SQUARE);
        add(g, 2, 0, TILE_RED_PLUS);

        add(g, 1, 2, TILE_RED_ROUND);
        add(g, 2, 2, TILE_RED_PLUS_2);  //lance cette exception quand il ne doit pas avoir

        // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 are identical
        for (var color : Color.values()) {
            for (var shape : Shape.values()) {
                assertThrows(QwirkleException.class, () -> add(g, 2, 1, new Tile(color, shape)));
                assertNull(get(g, 2, 1));
            }
        }
    }

    @Test
    void canNotCompleteToALineWithIdenticalTiles_tap() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_RED_SQUARE);
        add(g, 2, 0, TILE_RED_PLUS);

        add(g, 1, 2, TILE_RED_ROUND);
        add(g, 2, 2, TILE_RED_PLUS_2);

        // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 are identical
        for (var color : Color.values()) {
            for (var shape : Shape.values()) {
                assertThrows(QwirkleException.class, () -> g.add(createTileAtpos(2, 1, new Tile(color, shape))));
                assertNull(get(g, 2, 1));
            }
        }
    }

    @Test
    void canNotCompleteToALineWithIdenticalTiles_dir() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_RED_SQUARE);
        add(g, 2, 0, TILE_RED_PLUS);

        add(g, 1, 2, TILE_RED_ROUND);
        add(g, 2, 2, TILE_RED_PLUS_2);

        // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 are identical
        for (var color : Color.values()) {
            for (var shape : Shape.values()) {
                // there is only one tile but let's try to add it in all directions anyway
                for (Direction direction : Direction.values()) {
                    assertThrows(QwirkleException.class, () -> add(g, 2, 1, direction, new Tile(color, shape)));
                    assertNull(get(g, 2, 1));
                }
            }
        }
    }

    // various other tests, pertaining to filling existing holes
    @Test
    void canCompleteToALineLeftRight() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_GREEN_CROSS);
        add(g, 2, 0, TILE_YELLOW_CROSS);

        add(g, 1, 2, TILE_GREEN_DIAMOND);
        add(g, 2, 2, TILE_YELLOW_DIAMOND);

        add(g, 2, 1, TILE_YELLOW_PLUS);
        assertSame(TILE_YELLOW_PLUS, get(g, 2, 1));

    }

    @Test
    void canCompleteToALineLeftRightUpDown() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_PLUS, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_GREEN_CROSS);
        add(g, 2, 0, TILE_YELLOW_CROSS);

        add(g, 1, 2, TILE_GREEN_DIAMOND);
        add(g, 2, 2, TILE_YELLOW_DIAMOND);

        add(g, 2, 1, TILE_YELLOW_PLUS);
        add(g, 1, 1, TILE_GREEN_PLUS);
        assertSame(TILE_GREEN_PLUS, get(g, 1, 1));
    }

    @Test
    @DisplayName("Complete a line leaving holes during intermediary steps")
    void canCompleteALine_Left_Middle_Right() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_PLUS, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_GREEN_CROSS);
        add(g, 2, 0, TILE_YELLOW_CROSS);

        add(g, 1, 2, TILE_GREEN_DIAMOND);
        add(g, 2, 2, TILE_YELLOW_DIAMOND);

        TileAtPosition plus_left = createTileAtpos(2, -1, TILE_YELLOW_PLUS);
        TileAtPosition round_center = createTileAtpos(2, 1, TILE_YELLOW_ROUND);
        TileAtPosition star_right = createTileAtpos(2, 3, TILE_YELLOW_STAR);
        assertDoesNotThrow(() -> {
            g.add(plus_left, star_right, round_center); // make sur having the center tile last does not throw.
        });
        assertAtCorrectPosition(g, plus_left);
        assertAtCorrectPosition(g, round_center);
        assertAtCorrectPosition(g, star_right);
    }

    @Test
    @DisplayName("Complete a line leaving holes during intermediary steps")
    void canCompleteALine_Left2_Left() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_PLUS, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_GREEN_CROSS);
        add(g, 2, 0, TILE_YELLOW_CROSS);

        add(g, 1, 2, TILE_GREEN_DIAMOND);
        add(g, 2, 2, TILE_YELLOW_DIAMOND);

        TileAtPosition plus_left_left = createTileAtpos(2, -2, TILE_YELLOW_PLUS);
        TileAtPosition round_left = createTileAtpos(2, -1, TILE_YELLOW_ROUND);
        assertDoesNotThrow(() -> {
            g.add(plus_left_left, round_left); // make sur having the "left" tile after the "left left" tile does not throw
        });
        assertAtCorrectPosition(g, plus_left_left);
        assertAtCorrectPosition(g, round_left);
    }


    @Test
    void canNotCompleteALine_leaving_a_hole() {
        var g = new Grid();
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_PLUS, TILE_RED_DIAMOND);

        add(g, 1, 0, TILE_GREEN_CROSS);
        add(g, 2, 0, TILE_YELLOW_CROSS);

        add(g, 1, 2, TILE_GREEN_DIAMOND);
        add(g, 2, 2, TILE_YELLOW_DIAMOND);

        TileAtPosition plus_left = createTileAtpos(2, -1, TILE_YELLOW_PLUS);
        TileAtPosition star_right = createTileAtpos(2, 3, TILE_YELLOW_STAR);
        assertThrows(QwirkleException.class, () -> {
            g.add(plus_left, star_right);
        });
        assertNull(get(g, 2, -1));
        assertNull(get(g, 2, 3));
    }

    @Test
    void calculPointsAdd() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        assertEquals(3, grid.firstAdd(RIGHT, t1, t2, t3));
        assertEquals(2, grid.add(44, 45, new Tile(YELLOW, ROUND)));
        assertEquals(3, grid.add(43, 45, new Tile(PURPLE, ROUND)));
        assertEquals(5, grid.add(45, 48, Direction.RIGHT, new Tile(RED, SQUARE),new Tile(RED,ROUND)));
        assertEquals(3, grid.add(44, 47, Direction.UP, new Tile(BLUE, PLUS),new Tile(YELLOW,PLUS)));
        assertThrows(QwirkleException.class, () -> {
            grid.add(new TileAtPosition(44, 46, new Tile(BLUE, ROUND)));
            grid.add(new TileAtPosition(43, 46, new Tile(RED, ROUND)));
        });
    }

    @Test
    void calculPointsAdd2() {
        Grid grid=new Grid();
        GridView gridView=new GridView(grid);
        assertEquals(3,grid.firstAdd(UP,new Tile(RED,CROSS),new Tile(RED,SQUARE),new Tile(RED,ROUND)));
        assertEquals(4, grid.add(46,45,new Tile(RED,STAR)));
        assertEquals(11, grid.add(new TileAtPosition(46, 44, new Tile(PURPLE, STAR)),new TileAtPosition(47,45,new Tile(RED,DIAMOND)),new TileAtPosition(45,44,new Tile(PURPLE,CROSS))));
        assertEquals(4,grid.add(45,46,RIGHT,new Tile(BLUE,CROSS),new Tile(YELLOW,CROSS)));
        assertEquals(5,grid.add(45,48,new Tile(ORANGE,CROSS)));
    }

    // private methods








        ////////////////// TEST POUR DEFENSE //////////////////

        //le PASS ne change pas le point du joueur dans c'est dans le app que ca se passe, sinon ca va creer un probleme,
        //car on fait de base pass() a chaque execution de mouvement et si on implementais ca dans game ca affecterai le score
        @Test
        public void testCalculatePoints() {


            List<String> noms = Arrays.asList("Alex", "Popo");
            Game game = new Game(noms);

            Player player = game.getCurrentPlayer();
            int expectedPoints = 9; // Le score attendu
            game.getCurrentPlayer().addToScore(grid.firstAdd(RIGHT,new Tile(RED,SQUARE), new Tile(RED,STAR), new Tile(RED,DIAMOND),new Tile(RED,PLUS)));
            assertEquals(expectedPoints, player.getScore());
            game.pass();
            player = game.getCurrentPlayer();
            game.pass();
            expectedPoints =5;
            assertEquals(expectedPoints, player.getScore());

        }

        @Test
        public void testCalculatePoints2() {
            List<String> noms = Arrays.asList("Alex", "Popo");
            Game game = new Game(noms);

            Player player = game.getCurrentPlayer();
            int expectedPoints = 8; // Le score attendu
            game.getCurrentPlayer().addToScore(grid.firstAdd(RIGHT,new Tile(RED,SQUARE), new Tile(RED,STAR),new Tile(RED,PLUS)));
            assertEquals(expectedPoints, player.getScore());
            game.pass();
            player = game.getCurrentPlayer();
            expectedPoints =5;
            assertEquals(expectedPoints, player.getScore());
            game.getCurrentPlayer().addToScore(grid.add(46,45,new Tile(RED,DIAMOND)));
            expectedPoints =7;
            assertEquals(expectedPoints, player.getScore());
        }


    @Test
    public void testCalculatePoints3() {
        List<String> noms = Arrays.asList("Alex", "Popo");
        Game game = new Game(noms);

        Player player = game.getCurrentPlayer();
        int expectedPoints = 6; // Le score attendu
        game.getCurrentPlayer().addToScore(grid.firstAdd(RIGHT,new Tile(RED,SQUARE)));
        assertEquals(expectedPoints, player.getScore());
        game.pass();
        player = game.getCurrentPlayer();
        expectedPoints =5;
        assertEquals(expectedPoints, player.getScore());
        game.getCurrentPlayer().addToScore(grid.add(46,45,new Tile(BLUE,SQUARE)));
        expectedPoints =7;
        assertEquals(expectedPoints, player.getScore());
        game.pass();
        game.getCurrentPlayer().addToScore(grid.add(47,45,new Tile(BLUE,DIAMOND)));
        expectedPoints =7;
        assertEquals(expectedPoints, player.getScore());

    }


    @Test
    public void testCalculatePoints4() {
        List<String> noms = Arrays.asList("Alex", "Popo");
        Game game = new Game(noms);

        Player player = game.getCurrentPlayer();
        int expectedPoints = 7; // Le score attendu
        game.getCurrentPlayer().addToScore(grid.firstAdd(UP,new Tile(BLUE,SQUARE), new Tile(YELLOW,SQUARE)));
        assertEquals(expectedPoints, player.getScore());
        game.pass();
        player = game.getCurrentPlayer();
        expectedPoints =5;
        assertEquals(expectedPoints, player.getScore());
        game.getCurrentPlayer().addToScore(grid.add(44,46,new Tile(YELLOW,DIAMOND)));
        expectedPoints =7;
        assertEquals(expectedPoints, player.getScore());
        game.pass();
        expectedPoints = 7; // Le score attendu
        game.getCurrentPlayer().addToScore(grid.add(45,46,new Tile(BLUE,ROUND)));
        assertEquals(expectedPoints, player.getScore());

        game.pass();
        expectedPoints = 10; // Le score attendu
        game.getCurrentPlayer().addToScore(grid.add(46,46,new Tile(BLUE,PLUS)));
        assertEquals(expectedPoints, player.getScore());

    }



    private void add(Grid g, int row, int col, Tile tile) {
        g.add(INITIAL_ROW + row, INITIAL_COL + col, tile);
    }

    private void add(Grid g, int row, int col, Direction d, Tile... line) {
        g.add(INITIAL_ROW + row, INITIAL_COL + col, d, line);
    }

    private Tile get(Grid g, int row, int col) {
        return g.get(INITIAL_ROW + row, INITIAL_COL + col);
    }

    private TileAtPosition createTileAtpos(int row, int col, Tile tile) {
        return new TileAtPosition(INITIAL_ROW + row, INITIAL_COL + col, tile);
    }


    private void assertAtCorrectPosition(Grid g, TileAtPosition tileAtPosition) {
        int row = tileAtPosition.row();
        int col = tileAtPosition.col();
        assertSame(tileAtPosition.tile(), g.get(row, col));
    }



}