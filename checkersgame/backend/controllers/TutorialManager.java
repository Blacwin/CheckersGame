package checkersgame.backend.controllers;

import checkersgame.backend.game.Game;
import checkersgame.backend.game.board.Board;
import checkersgame.backend.game.board.field.Position;
import checkersgame.backend.game.movements.Move;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;

import java.util.ArrayList;
import java.util.List;

public class TutorialManager {
    private final int BOARD_SIZE = 8;
    private List<List<Move>> playerMoves;
    private List<Move> answerMoves;
    private int level;
    private int stepCounter;
    private List<String> levelDescription;

    public TutorialManager() {
        playerMoves = new ArrayList<>();
        answerMoves = new ArrayList<>();
        level = 1;
        levelDescription = new ArrayList<>();
    }

    public Game getGameForLevel() {
        switch (this.level) {
            case 1: return level1();
            case 2: return level2();
            case 3: return level3();
            case 4: return level4();
            case 5: return level5();
            case 6: return level6();
            default: return null;
        }
    }

    private List<Move> moveMaker(int[][] positions) {
        List<Move> moveList = new ArrayList<>();
        for(int[] pos : positions) {
            Position piecePos = new Position(pos[0], pos[1]);
            Position stepPos = new Position(pos[2], pos[3]);
            Move move = new Move(piecePos, stepPos);
            moveList.add(move);
        }
        return moveList;
    }

    public List<List<Move>> getPlayerMoves() {
        return playerMoves;
    }

    public int getStepCounter() {
        return stepCounter;
    }

    public void removeStep() {
        this.playerMoves.remove(stepCounter-1);
    }

    public void decreaseStepCounter() {
        this.stepCounter--;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean nextLevel() {
        this.level += 1;
        if(this.level > 6) {
            return false;
        }else {
            return true;
        }
    }

    public void goToPreviousLevel() {
        if(this.level > 1) {
            this.level -= 1;
        }
    }

    public List<Move> getPossiblePlayerMoves() {
        return playerMoves.get(stepCounter-1);
    }

    public Game level1() {
        this.level = 1;
        this.playerMoves.clear();
        this.levelDescription.clear();
        this.levelDescription.add("" +
                "Első lépésként ki  kell választanod\n" +
                "a bábut, amivel majd lépni szeretnél.\n" +
                "Ehhez kattints a bábura.");
        this.levelDescription.add("" +
                "A megjelenő sárga körökkel azok a\n" +
                "mezők vannak jelölve, amelyekre a\n" +
                "bábuval léphetsz.\n" +
                "Kattints az egyikre.");
        PieceColor bottomColor = PieceColor.LIGHT;
        PieceColor topColor = PieceColor.DARK;
        PieceColor currentColor = PieceColor.LIGHT;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 1;
        String playerName = "Oktató 1";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.DOWN, PieceDirection.UP);
        this.stepCounter = 1;
        int[][] moveArray = {
                {6, 3, 5, 2},
                {6, 3, 5, 4}};
        this.playerMoves.add(moveMaker(moveArray));
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public Game level2() {
        this.level = 2;
        this.playerMoves.clear();
        this.levelDescription.clear();
        this.levelDescription.add("" +
                "Az egyszerű lépés után a következő\n" +
                "lépés típus a támadás. \n" +
                "Előszőr kattints a bábura.");
        this.levelDescription.add("" +
                "Most is a sárgával jelölt mezőre\n" +
                "tudsz lépni. A piroson pedig az\n" +
                "ellenség megtámadott bábuja van.\n" +
                "A támadáshoz kattints a sárgával\n" +
                "jelölt mezőre.");
        PieceColor bottomColor = PieceColor.LIGHT;
        PieceColor topColor = PieceColor.DARK;
        PieceColor currentColor = PieceColor.LIGHT;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 1;
        String playerName = "Oktató 2";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.DOWN, PieceDirection.UP);
        this.stepCounter = 1;
        int[][] moveArray = {
                {6, 3, 4, 5}};
        this.playerMoves.add(moveMaker(moveArray));
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public Game level3() {
        this.level = 3;
        this.playerMoves.clear();
        this.levelDescription.clear();
        this.levelDescription.add("" +
                "Ha több lehetőséged is van támadni\n" +
                "akkor választhatsz közülök.\n" +
                "Jelöld ki az egyik, majd a másik bábut\n" +
                "és nézd meg melyik hova tud lépni.");
        this.levelDescription.add("" +
                "A szint befejezéséhez hajtsd végre\n" +
                "valamelyik támadást.");
        PieceColor bottomColor = PieceColor.LIGHT;
        PieceColor topColor = PieceColor.DARK;
        PieceColor currentColor = PieceColor.LIGHT;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 1;
        String playerName = "Oktató 3";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 1, 0, 1, 0, 1, 0},
                {0, 0, 0, 2, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.DOWN, PieceDirection.UP);
        this.stepCounter = 1;
        int[][] moveArray1 = {
                {6, 3, 4, 1},
                {6, 3, 4, 5},
                {6, 5, 4, 3}};
        this.playerMoves.add(moveMaker(moveArray1));
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public Game level4() {
        this.level = 4;
        this.playerMoves.clear();
        this.levelDescription.clear();
        this.levelDescription.add("" +
                "Lánc ütés. Egy támadás utána újra\n" +
                "támadhatunk, ha van ütésben álló\n" +
                "ellenséges bábu.\n" +
                "Jelöld ki a bábut, majd hajtsd\n" +
                "végre az első támadás.");
        this.levelDescription.add("" +
                "A támadás után jelölve marad a\n" +
                "bábunk, ha újra támadhatunk.\n" +
                "Folytasd a támadást a következő\n" +
                "sárgával jelölt mezőre kattintva.");
        PieceColor bottomColor = PieceColor.LIGHT;
        PieceColor topColor = PieceColor.DARK;
        PieceColor currentColor = PieceColor.LIGHT;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 1;
        String playerName = "Oktató 4";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.DOWN, PieceDirection.UP);
        this.stepCounter = 2;
        int[][] moveArray1 = {
                {4, 5, 2, 7}};
        int[][] moveArray2 = {
                {6, 3, 4, 5}};
        this.playerMoves.add(moveMaker(moveArray1));
        this.playerMoves.add(moveMaker(moveArray2));
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public Game level5() {
        this.level = 5;
        this.playerMoves.clear();
        this.levelDescription.clear();
        this.levelDescription.add("" +
                "Ha egy bábu eléri a szemközti\n" +
                "oldalt, akkor dámává válik.\n" +
                "Jelöld ki a bábut és juss el\n" +
                "vele a tábla széléig.");
        this.levelDescription.add("" +
                "A dáma tulajdonsága hogy\n" +
                "vissza felé is tud lépni.\n" +
                "A befejezéshez lépj egyet\n" +
                "a dámával.");
        PieceColor bottomColor = PieceColor.LIGHT;
        PieceColor topColor = PieceColor.DARK;
        PieceColor currentColor = PieceColor.LIGHT;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 1;
        String playerName = "Oktató 5";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.DOWN, PieceDirection.UP);
        this.stepCounter = 3;
        int[][] moveArray1 = {
                {0, 1, 1, 0},
                {0, 1, 1, 2},
                {0, 3, 1, 2},
                {0, 3, 1, 4},
                {0, 5, 1, 4},
                {0, 5, 1, 6}};
        int[][] moveArray2 = {
                {1, 2, 0, 1},
                {1, 2, 0, 3},
                {1, 4, 0, 3},
                {1, 4, 0, 5}};
        int[][] moveArray3 = {
                {2, 3, 1, 2},
                {2, 3, 1, 4}};
        this.playerMoves.add(moveMaker(moveArray1));
        this.playerMoves.add(moveMaker(moveArray2));
        this.playerMoves.add(moveMaker(moveArray3));
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public Game level6() {
        this.level = 6;
        this.playerMoves.clear();
        this.levelDescription.clear();
        this.levelDescription.add("" +
                "Dámával nem csak lépni, de\n" +
                "ütni is tudsz minden irányba.\n" +
                "Jelöld ki a dámát és nézd meg\n" +
                "milyen lehetőségeid vannak.");
        this.levelDescription.add("" +
                "A szint befejezéséhez válaszd\n" +
                "ki azt a támadás amivel a legtöbb\n" +
                "ellenséges bábut tudod leütni.\n");
        PieceColor bottomColor = PieceColor.LIGHT;
        PieceColor topColor = PieceColor.DARK;
        PieceColor currentColor = PieceColor.LIGHT;
        PieceDirection playerDirection = PieceDirection.UP;
        int difficulty = 1;
        String playerName = "Oktató 6";
        Board board = new Board(BOARD_SIZE);
        int[][] intArray = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 2, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};
        board.setBoardFromIntArray(intArray, PieceDirection.DOWN, PieceDirection.UP);
        this.stepCounter = 2;
        int[][] moveArray1 = {
                {5, 6, 7, 4}};
        int[][] moveArray2 = {
                {3, 4, 5, 6}};
        this.playerMoves.add(moveMaker(moveArray1));
        this.playerMoves.add(moveMaker(moveArray2));
        return new Game(bottomColor, topColor, currentColor, playerDirection, difficulty, playerName, board, true, false);
    }

    public List<String> getDescription() {
        return levelDescription;
    }
}
