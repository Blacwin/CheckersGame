package checkersgame.backend.controllers;

import checkersgame.backend.game.Game;
import checkersgame.backend.game.board.Board;
import checkersgame.backend.game.movements.Move;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;

import java.util.List;

public class ExtraGameManager {

    private static final int BOARD_SIZE = 8;
    private int level;
    private List<Move> playerMoves;
    private String levelDescription;
    private int stepCounter;

    public ExtraGameManager() {
        this.level = 1;
    }

    public Game getGameForLevel() {
        switch (level) {
            case 1: return level1();
            case 2: return level2();
            case 3: return level3();
            case 4: return level4();
            case 5: return level5();
            case 6: return level6();
            default: return null;
        }
    }

    public Game getGameByLevel(int level) {
        switch (level) {
            case 1: return level1();
            case 2: return level2();
            case 3: return level3();
            case 4: return level4();
            case 5: return level5();
            case 6: return level6();
            default: return null;
        }
    }

    public Game level1() {
        this.level = 1;
        this.levelDescription = "Első pálya";
        PieceColor bottomColor = PieceColor.DARK;
        PieceColor topColor = PieceColor.LIGHT;
        PieceColor currentColor = PieceColor.DARK;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 2;
        String playerName = "1 szint";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 0, 0, 2, 0, 2, 0, 2},
                {0, 0, 0, 0, 2, 0, 2, 0},
                {0, 0, 0, 0, 0, 2, 0, 2},
                {1, 0, 0, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 0, 0, 2},
                {1, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0, 0, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.UP, PieceDirection.DOWN);
        this.stepCounter = 1;
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public Game level2() {
        this.level = 2;
        this.levelDescription = "Második pálya";
        PieceColor bottomColor = PieceColor.DARK;
        PieceColor topColor = PieceColor.LIGHT;
        PieceColor currentColor = PieceColor.DARK;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 4;
        String playerName = "2 szint";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 2, 0, 2, 0, 2, 0, 2},
                {0, 0, 2, 0, 2, 0, 2, 0},
                {0, 0, 0, 2, 0, 2, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 0},
                {1, 0, 1, 0, 1, 0, 1, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.UP, PieceDirection.DOWN);
        this.stepCounter = 1;
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public Game level3() {
        this.level = 3;
        this.levelDescription = "";
        PieceColor bottomColor = PieceColor.DARK;
        PieceColor topColor = PieceColor.LIGHT;
        PieceColor currentColor = PieceColor.DARK;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 4;
        String playerName = "3 szint";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 2, 0, 2, 0, 2, 0, 0},
                {2, 0, 2, 0, 2, 0, 0, 0},
                {0, 2, 0, 2, 0, 0, 0, 1},
                {2, 0, 2, 0, 0, 0, 1, 0},
                {0, 2, 0, 0, 0, 1, 0, 1},
                {2, 0, 0, 0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0, 1, 0, 1},
                {0, 0, 1, 0, 1, 0, 1, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.UP, PieceDirection.DOWN);
        this.stepCounter = 1;
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public Game level4() {
        this.level = 4;
        this.levelDescription = "";
        PieceColor bottomColor = PieceColor.DARK;
        PieceColor topColor = PieceColor.LIGHT;
        PieceColor currentColor = PieceColor.DARK;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 4;
        String playerName = "4 szint";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 2, 0, 2, 0, 2, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.UP, PieceDirection.DOWN);
        this.stepCounter = 1;
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public Game level5() {
        this.level = 5;
        this.levelDescription = "";
        PieceColor bottomColor = PieceColor.DARK;
        PieceColor topColor = PieceColor.LIGHT;
        PieceColor currentColor = PieceColor.DARK;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 4;
        String playerName = "5 szint";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 4, 0, 4, 0, 4, 0, 4},
                {4, 0, 4, 0, 4, 0, 4, 0},
                {0, 4, 0, 4, 0, 4, 0, 4},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 3, 0, 3, 0, 3, 0},
                {0, 3, 0, 3, 0, 3, 0, 3},
                {3, 0, 3, 0, 3, 0, 3, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.UP, PieceDirection.DOWN);
        this.stepCounter = 1;
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public Game level6() {
        this.level = 6;
        this.levelDescription = "";
        PieceColor bottomColor = PieceColor.DARK;
        PieceColor topColor = PieceColor.LIGHT;
        PieceColor currentColor = PieceColor.DARK;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 4;
        String playerName = "6 szint";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 3, 0, 3, 0, 3, 0, 3},
                {2, 0, 2, 0, 2, 0, 2, 0},
                {0, 2, 0, 2, 0, 2, 0, 2},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {4, 0, 4, 0, 4, 0, 4, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.UP, PieceDirection.DOWN);
        this.stepCounter = 1;
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public String getDescription() {
        return levelDescription;
    }

    public void start() {
        this.level = 1;
    }

    public void nextLevel() {
        if(level+1 <= 6) {
            this.level++;
        }
    }

    public int getLevel() {
        return level;
    }
}
