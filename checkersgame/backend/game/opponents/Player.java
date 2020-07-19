package checkersgame.backend.game.opponents;

import checkersgame.backend.game.board.Board;
import checkersgame.backend.game.movements.Move;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;
import checkersgame.backend.game.board.field.Position;
import checkersgame.backend.game.movements.StepGenerator;

import java.util.List;

public class Player extends Opponent {
    private StepGenerator sg;

    public Player(String name, PieceColor color, PieceDirection direction) {
        super(color, direction);
        setName(name);
        this.sg = new StepGenerator(color);
        setWithdrawCounter(3);
    }

//    public Move getMove(Board board, Position piecePosition) {
//        return sg.calculatePossibleMove(board, getColor(), piecePosition.x, piecePosition.y);
//    }

    public boolean isThereHitPossibilitiesInPosition(Board board, PieceColor opponentColor, Position piecePosition) {
        return sg.searchHits(board, opponentColor, piecePosition.x, piecePosition.y);
    }

    @Override
    public boolean areTherePossibleMoves(Board board, boolean forceAttack) {
        if(forceAttack) {
            if(sg.generateMoves(board, true).size() == 0) {
                return false;
            }
        }else {
            if(sg.generateMovesWithoutForceAttack(board, true).size() == 0) {
                return false;
            }
        }
        return true;
    }

    public List<Move> getAllPossibleMoves(Board board, boolean forceAttack) {
        if(forceAttack) {
            return sg.getAllPossibleMoves(board, getColor());
        }else {
            return sg.getAllPossibleMovesWithoutForceAttack(board, getColor());
        }
    }

    public void decreaseStepCounter() {
        if(getWithdrawCounter()-1 >= 0) {
            setWithdrawCounter(getWithdrawCounter()-1);
        }
    }
}
