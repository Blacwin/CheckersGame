package checkersgame.backend.game.opponents;

import checkersgame.backend.game.board.Board;
import checkersgame.backend.game.movements.Move;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;

import java.util.ArrayList;
import java.util.List;

public class Opponent {
    private PieceColor color;
    private PieceDirection direction;
    private List<List<Move>> moveHistory;
    private String name;
    private int withdrawCounter;

    public Opponent(PieceColor color, PieceDirection direction) {
        this.color = color;
        this.direction = direction;
        this.moveHistory = new ArrayList<>();
    }

    public boolean areTherePossibleMoves(Board board, boolean forceAttack) {
        return true;
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceColor getOppositeColor() {
        if(color == PieceColor.LIGHT) {
            return PieceColor.DARK;
        }else {
            return PieceColor.LIGHT;
        }
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }

    public PieceDirection getDirection() {
        return direction;
    }

    public void setDirection(PieceDirection direction) {
        this.direction = direction;
    }

    public List<List<Move>> getMoveHistory() {
        return moveHistory;
    }

    public void addMoveToHistoryList(Move move) {
        List<Move> moveList = new ArrayList<>();
        moveList.add(move);
        moveHistory.add(moveList);
    }

    public void addMovesToHistoryList(List<Move> moves) {
        moveHistory.add(moves);
    }

    public void setMoveHistory(List<List<Move>> moveHistory) {
        this.moveHistory = moveHistory;
    }

//    public String getMoveHistoryToHunString() {
//        String result = "";
//        String[] abc = {"a", "b", "c", "d", "e", "f", "g", "h"};
//        for(Move move : moveHistory) {
//            result += "Bábu: "+abc[move.getPiecePos().y] +"-"+ (move.getPiecePos().x+1) +" Lépés: "+ abc[move.getStepPos().y] +"-"+(move.getStepPos().x+1);
//            if(move.getStep().isHitStep()) {
//                result += " Ütés: "+abc[move.getStep().getHitPosition().y] +"-"+(move.getStep().getHitPosition().x+1);
//            }
//            result += "\n";
//        }
//        return result;
//    }

    public List<String> getMoveHistoryList() {
        List<String> resultList = new ArrayList<>();
        String[] abc = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for(List<Move> chainMove : moveHistory) {
            String stringMove = "";
            for (Move move : chainMove) {
                stringMove += "Bábu: " + abc[move.getPiecePos().y] + "-" + (move.getPiecePos().x + 1) + " Lépés: " + abc[move.getStepPos().y] + "-" + (move.getStepPos().x + 1);
                if (move.getStep().isHitStep()) {
                    stringMove += " Ütés: " + abc[move.getStep().getHitPosition().y] + "-" + (move.getStep().getHitPosition().x + 1);
                }
                stringMove += " -> ";
            }
            resultList.add(stringMove);
        }
        return resultList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWithdrawCounter() {
        return withdrawCounter;
    }

    public void setWithdrawCounter(int withdrawCounter) {
        this.withdrawCounter = withdrawCounter;
    }

    public void stepBack() {
        moveHistory.remove(moveHistory.size()-1);
    }
}
