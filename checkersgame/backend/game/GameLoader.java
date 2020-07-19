package checkersgame.backend.game;

import checkersgame.backend.game.board.Board;
import checkersgame.backend.game.board.field.CheckersField;
import checkersgame.backend.game.movements.Move;
import checkersgame.backend.game.opponents.Ai;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;
import checkersgame.frontend.GuiManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameLoader {


//LOADING PART
    public Game getGameFromFile(File file) {
        try {
            Scanner scan = new Scanner(file);
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();

            JSONObject obj = new JSONObject(str);
            int mode = obj.getJSONObject("game").getInt("mode"); //0..2 integer;
            int topOpp = obj.getJSONObject("game").getInt("topOpp"); //1 = dark; 2 = light;
            int bottomOpp = obj.getJSONObject("game").getInt("bottomOpp"); //1 = dark; 2 = light;
            int nxt = obj.getJSONObject("game").getInt("next"); //1 = top; 2 = bottom;
            int topDiff = obj.getJSONObject("game").getInt("topDiff"); //1..3 integer;
            int bottomDiff = obj.getJSONObject("game").getInt("bottomDiff"); //1..3 integer;
            String plyDirect = obj.getJSONObject("game").getString("plyDirect"); //UP or DOWN;
            String bottomName = obj.getJSONObject("game").getString("bottomName");
            String topName = obj.getJSONObject("game").getString("topName");
            boolean forceAttack = obj.getJSONObject("game").getBoolean("forceAttack");
            boolean withdraw = obj.getJSONObject("game").getBoolean("withdraw");
            int bottomWithdrawCounter = obj.getJSONObject("game").getInt("bottomWCounter");
            int topWithdrawCounter = obj.getJSONObject("game").getInt("topWCounter");
            long gameTime = obj.getJSONObject("game").getLong("gameTime");
            Board newBoard = getBoardFromJSONArray(obj.getJSONArray("board"), topOpp, bottomOpp);
            List<Board> boardHistory = getBoardHistoryFromJSONArray(obj.getJSONArray("boardHistory"), topOpp, bottomOpp);
            List<List<Move>> topMoves = getMovesFromJSONArray(obj.getJSONArray("topMoves"));
            List<List<Move>> bottomMoves = getMovesFromJSONArray(obj.getJSONArray("bottomMoves"));
            Game newGame = new Game();
            if(mode == 0) {
                PieceDirection playerDirection = PieceDirection.UP;
                PieceColor topOpponentColor;
                PieceColor bottomOpponentColor;
                PieceColor currentColor;
                String playerName = "Mentett Játékos";
                int difficult = 1;
                if(plyDirect.equals("UP")) {
                    playerDirection = PieceDirection.UP;
                    difficult = topDiff;
                    playerName = bottomName;
                } else if(plyDirect.equals("DOWN")) {
                    playerDirection = PieceDirection.DOWN;
                    difficult = bottomDiff;
                    playerName = topName;
                }
                if(topOpp == 1) {
                    topOpponentColor = PieceColor.DARK;
                    bottomOpponentColor = PieceColor.LIGHT;
                } else {
                    topOpponentColor = PieceColor.LIGHT;
                    bottomOpponentColor = PieceColor.DARK;
                }
                currentColor = (nxt==1) ? PieceColor.DARK : PieceColor.LIGHT;
                newGame = new Game(bottomOpponentColor, topOpponentColor, currentColor, playerDirection, difficult, playerName, newBoard, forceAttack, withdraw);
            } else if(mode == 1) {
                if(topOpp == 1) {
                    PieceColor topPlayerColor = PieceColor.DARK;
                    PieceColor bottomPlayerColor = PieceColor.LIGHT;
                    PieceColor currentColor = (nxt==1) ? PieceColor.DARK : PieceColor.LIGHT;
                    newGame = new Game(bottomPlayerColor, topPlayerColor, currentColor, bottomName, topName, newBoard, forceAttack, withdraw);
                } else {
                    PieceColor topPlayerColor = PieceColor.LIGHT;
                    PieceColor bottomPlayerColor = PieceColor.DARK;
                    PieceColor currentColor = (nxt==1) ? PieceColor.DARK : PieceColor.LIGHT;
                    newGame = new Game(bottomPlayerColor, topPlayerColor, currentColor, bottomName, topName, newBoard, forceAttack, withdraw);
                }
            } else if(mode == 2) {
                if(topOpp == 1) {
                    PieceColor topComputerColor = PieceColor.DARK;
                    PieceColor bottomComputerColor = PieceColor.LIGHT;
                    PieceColor currentColor = (nxt==1) ? PieceColor.DARK : PieceColor.LIGHT;
                    newGame = new Game(bottomComputerColor, topComputerColor, currentColor, bottomDiff, topDiff, newBoard, forceAttack);
                } else {
                    PieceColor topComputerColor = PieceColor.LIGHT;
                    PieceColor bottomComputerColor = PieceColor.DARK;
                    PieceColor currentColor = (nxt==1) ? PieceColor.DARK : PieceColor.LIGHT;
                    newGame = new Game(bottomComputerColor, topComputerColor, currentColor, bottomDiff, topDiff, newBoard, forceAttack);
                }
            }
            newGame.setBoardHistory(boardHistory);
            newGame.setOpponentsMoveHistory(bottomMoves, topMoves);
            newGame.setWithdrawCounters(bottomWithdrawCounter, topWithdrawCounter);
            newGame.setGameTime(gameTime);
            return newGame;

        } catch (Exception ex) {
            GuiManager.getErrorMsg("Betöltési hiba", "Hibás mentési fájl vagy elavult.");
        }
        return null;
    }

    private List<List<Move>> getMovesFromJSONArray(JSONArray moveList) {
        List<List<Move>> moveHistory = new ArrayList<>();
        for(int i=0; i<moveList.length(); i++) {
            JSONArray chainMoveArray = moveList.getJSONArray(i);
            List<Move> chainMove = new ArrayList<>();
            for(int j=0; j<chainMoveArray.length(); j++) {
                int moveInteger = chainMoveArray.getInt(j);
                Move move = new Move(moveInteger);
                chainMove.add(move);
            }
            moveHistory.add(chainMove);
        }
        return moveHistory;
    }

    private Board getBoardFromJSONArray(JSONArray jsonArray, int topOpp, int bottomOpp) {
        Board board = new Board(GuiManager.model.getBOARD_SIZE());
        board.setBoardFromJsonArray(jsonArray, topOpp, bottomOpp);
        return board;
    }

    private List<Board> getBoardHistoryFromJSONArray(JSONArray jsonArray, int topOpp, int bottomOpp) {
        List<Board> boardList = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            boardList.add(getBoardFromJSONArray(jsonArray.getJSONArray(i), topOpp, bottomOpp));
        }
        return boardList;
    }

//SAVING PART
    public String getGameString(Game game) {
        if(game != null) {
            JSONObject saveObject = new JSONObject();
            JSONObject gameObject = new JSONObject();

            gameObject.put("mode", game.getGameMode());
            gameObject.put("topOpp", (game.getTopOpponent().getColor() == PieceColor.DARK) ? 1 : 2);
            gameObject.put("bottomOpp", (game.getBottomOpponent().getColor() == PieceColor.DARK) ? 1 : 2);
            gameObject.put("next", (game.getCurrentOpponent().getColor() == PieceColor.DARK) ? 1 : 2);
            gameObject.put("topDiff", (game.getTopOpponent() instanceof Ai) ? ((Ai) game.getTopOpponent()).getDifficult() : 1 );
            gameObject.put("bottomDiff", (game.getBottomOpponent() instanceof Ai) ? ((Ai) game.getBottomOpponent()).getDifficult() : 1 );
            gameObject.put("plyDirect", (game.getPlayerDirection() != null) ? game.getPlayerDirection() : "UP");
            gameObject.put("bottomName", (game.getBottomOpponent().getName() != null) ? game.getBottomOpponent().getName() : "");
            gameObject.put("topName", (game.getTopOpponent().getName() != null) ? game.getTopOpponent().getName() : "");
            gameObject.put("forceAttack", game.isForceAttack());
            gameObject.put("withdraw", game.isWithdraw());
            gameObject.put("bottomWCounter", game.getBottomOpponent().getWithdrawCounter());
            gameObject.put("topWCounter", game.getTopOpponent().getWithdrawCounter());
            gameObject.put("gameTime", game.getGameTime());
            saveObject.put("game", gameObject);

            saveObject.put("board", getBoardToArray(game.getBoard()));

            saveObject.put("boardHistory", getBoardHistoryToArray(game.getBoardHistory()));
            saveObject.put("bottomMoves", getMovesToArray(game.getBottomOpponent().getMoveHistory()));
            saveObject.put("topMoves", getMovesToArray(game.getTopOpponent().getMoveHistory()));
            return saveObject.toString();
        }
        return "empty";
    }

    private JSONArray getMovesToArray(List<List<Move>> moveHistory) {
        JSONArray moveArray = new JSONArray();
        for(List<Move> chainMove : moveHistory) {
            JSONArray chainMoveArray = new JSONArray();
            for(Move move : chainMove) {
                chainMoveArray.put(move.getSaveFormat());
            }
            moveArray.put(chainMoveArray);
        }
        return moveArray;
    }

    private JSONArray getBoardHistoryToArray(List<Board> boardHistory) {
        JSONArray historyArray = new JSONArray();
        for(Board board : boardHistory) {
            historyArray.put(getBoardToArray(board));
        }
        return historyArray;
    }

    private JSONArray getBoardToArray(Board board) {
        JSONArray boardArray = new JSONArray();
        for(CheckersField[] line: board.getFieldsMatrix()) {
            JSONArray arrayLine = new JSONArray();
            for(CheckersField field : line) {
                arrayLine.put(field.toInt());
            }
            boardArray.put(arrayLine);
        }
        return boardArray;
    }
}
