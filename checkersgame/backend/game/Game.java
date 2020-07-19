package checkersgame.backend.game;

import checkersgame.backend.game.board.Board;
import checkersgame.backend.game.movements.Move;
import checkersgame.backend.game.opponents.Ai;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;
import checkersgame.backend.game.opponents.Opponent;
import checkersgame.backend.game.opponents.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private int GameMode;
    private PieceColor currentOpponentColor;
    private Board board;
    private Opponent bottomOpponent;
    private Opponent topOpponent;
    private Opponent currentOpponent;
    private Opponent starterOpponent;
    private List<Board> boardHistory;
    private boolean forceAttack;
    private boolean withdraw;
    private long gameTime;

    public Game() {

    }

    public Game(PieceColor bottomOpponentColor, PieceColor topOpponentColor, PieceColor currentColor, PieceDirection playerDirection, int difficulty, String playerName, Board board, boolean forceAttack, boolean withdraw) {
        this.GameMode = 0;
        this.currentOpponentColor = currentColor;
        this.board = board;
        initBoardHistory(board);
        if(playerDirection == PieceDirection.UP) {
            this.topOpponent = new Ai(topOpponentColor, PieceDirection.DOWN, board.getSize(), difficulty, forceAttack);
            this.bottomOpponent = new Player(playerName, bottomOpponentColor, PieceDirection.UP);
        } else if(playerDirection == PieceDirection.DOWN) {
            this.topOpponent = new Player(playerName, topOpponentColor, PieceDirection.DOWN);
            this.bottomOpponent = new Ai(bottomOpponentColor, PieceDirection.UP, board.getSize(), difficulty, forceAttack);
        }
        this.forceAttack = forceAttack;
        this.withdraw = withdraw;
        this.gameTime = 0;
    }

    public Game(PieceColor bottomPlayerColor, PieceColor topPlayerColor, PieceColor currentColor, String bottomName, String topName, Board board, boolean forceAttack, boolean withdraw) {
        this.GameMode = 1;
        this.currentOpponentColor = currentColor;
        this.board = board;
        initBoardHistory(board);
        this.bottomOpponent = new Player(bottomName, bottomPlayerColor, PieceDirection.UP);
        this.topOpponent = new Player(topName, topPlayerColor, PieceDirection.DOWN);
        this.forceAttack = forceAttack;
        this.withdraw = withdraw;
        this.gameTime = 0;
    }

    public Game(PieceColor bottomComputerColor, PieceColor topComputerColor, PieceColor currentColor, int bottomDifficult, int topDifficult, Board board, boolean forceAttack) {
        this.GameMode = 2;
        this.currentOpponentColor = currentColor;
        this.board = board;
        initBoardHistory(board);
        this.topOpponent = new Ai(topComputerColor, PieceDirection.DOWN, board.getSize(), topDifficult, forceAttack);
        this.bottomOpponent = new Ai(bottomComputerColor, PieceDirection.UP, board.getSize(), bottomDifficult, forceAttack);
        if(topComputerColor == currentColor) {
            this.starterOpponent = topOpponent;
        }else {
            this.starterOpponent = bottomOpponent;
        }
        this.forceAttack = forceAttack;
        this.withdraw = false;
        this.gameTime = 0;
    }

    public int getGameMode() {
        return GameMode;
    }

    public void setGameMode(int gameMode) {
        this.GameMode = gameMode;
    }

    public PieceColor getCurrentOpponentColor() {
        return currentOpponentColor;
    }

    public void setCurrentOpponentColor(PieceColor currentOpponentColor) {
        this.currentOpponentColor = currentOpponentColor;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board.setBoardFromArray(board.getFieldsMatrix());
    }

    public void setAisDifficulty(int bottomDiff, int topDiff) {
        if(bottomOpponent instanceof Ai) {
            ((Ai) bottomOpponent).setDifficult(bottomDiff);
        }
        if(topOpponent instanceof Ai) {
            ((Ai) topOpponent).setDifficult(topDiff);
        }
    }

    public void setAisDifficulty(int difficulty) {
        if(bottomOpponent instanceof Ai) {
            ((Ai) bottomOpponent).setDifficult(difficulty);
        } else if(topOpponent instanceof Ai) {
            ((Ai) topOpponent).setDifficult(difficulty);
        }
    }

    public Opponent getBottomOpponent() {
        return bottomOpponent;
    }

    public Opponent getTopOpponent() {
        return topOpponent;
    }

    public void nextOpponent() {
        if (currentOpponentColor == PieceColor.DARK) {
            currentOpponentColor = PieceColor.LIGHT;
        } else if (currentOpponentColor == PieceColor.LIGHT) {
            currentOpponentColor = PieceColor.DARK;
        }
    }

    public Opponent getCurrentOpponent() {
        if(currentOpponentColor == topOpponent.getColor()) {
            return topOpponent;
        }
        if(currentOpponentColor == bottomOpponent.getColor()) {
            return bottomOpponent;
        }
        return null;
    }

    public Opponent getNextOpponent() {
        if(currentOpponentColor == topOpponent.getColor()) {
            return bottomOpponent;
        }
        if(currentOpponentColor == bottomOpponent.getColor()) {
            return topOpponent;
        }
        return null;
    }

    public String toString() {
        String result;
        String topOpp = (topOpponent instanceof Ai) ? "AI" : "PLAYER";
        String bottomOpp = (bottomOpponent instanceof Ai) ? "AI" : "PLAYER";
        result = "TopOpp: " + topOpp + "; Color: " + topOpponent.getColor() + "\n";
        result += "BottomOpp: " + bottomOpp + "; Color: " + bottomOpponent.getColor();
        return result;
    }

    public Map<String, String> getOpponentDataMap() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("mode", String.valueOf(getGameMode()));
        if(bottomOpponent instanceof Ai) {
            resultMap.put("bottomType", "AI");
            resultMap.put("bottomName", "CPU (" + getDifficultString(((Ai) bottomOpponent).getDifficult()) + ")");
            resultMap.put("bottomDiff", Integer.toString(  ((Ai) bottomOpponent).getDifficult() ));
        } else {
            resultMap.put("bottomType", "PLAYER");
            resultMap.put("bottomName", bottomOpponent.getName());
        }
        if(topOpponent instanceof Ai) {
            resultMap.put("topType", "AI");
            resultMap.put("topName", "CPU (" + getDifficultString(((Ai) topOpponent).getDifficult()) + ")");
            resultMap.put("topDiff", Integer.toString(  ((Ai) topOpponent).getDifficult() ));
        } else {
            resultMap.put("topType", "PLAYER");
            resultMap.put("topName", topOpponent.getName());
        }
        if(bottomOpponent.getColor() == PieceColor.LIGHT) {
            resultMap.put("bottomColor", "LIGHT");
            resultMap.put("topColor", "DARK");
        } else {
            resultMap.put("bottomColor", "DARK");
            resultMap.put("topColor", "LIGHT");
        }
        if(currentOpponent != null) {
            if (currentOpponent.getColor() == PieceColor.LIGHT) {
                resultMap.put("currentColor", "LIGHT");
            } else {
                resultMap.put("currentColor", "DARK");
            }
        }
        if(forceAttack) resultMap.put("forceAttack", "TRUE");
        else resultMap.put("forceAttack", "FALSE");
        if(withdraw) resultMap.put("withdraw", "TRUE");
        else resultMap.put("withdraw", "FALSE");
        return resultMap;
    }

    private String getDifficultString(int difficult) {
        switch (difficult) {
            case 1: return "Kezdő";
            case 2: return "Nagyon Könnyű";
            case 3: return "Könnyű";
            case 4: return "Normál";
            case 5: return "Haladó";
            case 6: return "Nehéz";
            case 7: return "Mester";
            default: return "?";
        }
    }

    public List<Board> getBoardHistory() {
        return boardHistory;
    }

    public void setBoardHistory(List<Board> boardHistory) {
        this.boardHistory = boardHistory;
    }

    public void setOpponentsMoveHistory(List<List<Move>> bottomMoves, List<List<Move>> topMoves) {
        this.bottomOpponent.setMoveHistory(bottomMoves);
        this.topOpponent.setMoveHistory(topMoves);
    }

    public void setWithdrawCounters(int bottomWithdrawCounter, int topWithdrawCounter) {
        this.bottomOpponent.setWithdrawCounter(bottomWithdrawCounter);
        this.topOpponent.setWithdrawCounter(topWithdrawCounter);
    }

    public Board getBoardStateOf(int index) {
        return boardHistory.get(index);
    }

    public Board getPreviousBoardState() {
        if(boardHistory.size() > 2) {
            Board resultBoard = boardHistory.get(boardHistory.size()-3);
            boardHistory.remove(boardHistory.size()-1);
            boardHistory.remove(boardHistory.size()-1);
            return resultBoard;
        }
        return null;
    }

    public void addBoardToHistory(Board board) {
        Board anotherBoard = new Board(board.getSize());
        anotherBoard.setBoardFromArray(board.getFieldsMatrix());
        this.boardHistory.add(anotherBoard);
    }

    public void saveCurrentBoardState() {
        Board newBoard = new Board(board.getSize());
        newBoard.setBoardFromArray(board.getFieldsMatrix());
        this.boardHistory.add(newBoard);
    }

    private void initBoardHistory(Board board) {
        this.boardHistory = new ArrayList<>();
        Board newBoard = new Board(board.getSize());
        newBoard.setBoardFromArray(board.getFieldsMatrix());
        this.boardHistory.add(newBoard);
    }

    public PieceDirection getPlayerDirection() {
        if(bottomOpponent instanceof Player) {
            return PieceDirection.UP;
        }else if(topOpponent instanceof Player) {
            return PieceDirection.DOWN;
        } else {
            return null;
        }
    }

    public Board getFirstBoardState() {
        return boardHistory.get(0);
    }

    public Opponent getStarterOpponent() {
        return starterOpponent;
    }

    public void reset() {
        currentOpponentColor = starterOpponent.getColor();
    }

    public void stepBack() {
        Board prevBoard = getPreviousBoardState();
        if(prevBoard != null) {
            setBoard(prevBoard);
            if (getCurrentOpponent() instanceof Player) {
                ((Player) getCurrentOpponent()).decreaseStepCounter();
            }
            bottomOpponent.stepBack();
            topOpponent.stepBack();
        }
    }

    public boolean isForceAttack() {
        return forceAttack;
    }

    public boolean isWithdraw() {
        return withdraw;
    }

    public void setGameTime(long elapsedTime) {
        this.gameTime = elapsedTime;
    }

    public long getGameTime() {
        return gameTime;
    }
}
