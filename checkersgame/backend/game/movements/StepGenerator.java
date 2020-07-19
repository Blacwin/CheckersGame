package checkersgame.backend.game.movements;

import checkersgame.backend.game.board.Board;
import checkersgame.backend.game.board.field.*;
import checkersgame.backend.game.piece.Piece;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;

import java.util.ArrayList;
import java.util.List;

public class StepGenerator {
    private PieceColor selfColor;

    public StepGenerator(PieceColor selfColor) {
        this.selfColor = selfColor;
    }

    public List<Move> getAllPossibleMoves(Board board, PieceColor opponentColor) {
        List<Move> hitMoves = getAllHitMove(board, opponentColor);
        if(hitMoves.size() == 0) {
            return collectSimpleMoves(board, true);
        } else {
            return hitMoves;
        }
    }

    public List<Move> getAllPossibleMovesWithoutForceAttack(Board board, PieceColor opponentColor) {
        List<Move> hitMoves = getAllHitMove(board, opponentColor);
        hitMoves.addAll(collectSimpleMoves(board, true));
        return hitMoves;
    }

//    private Move getSimpleMove(Board board, Position piecePosition) {
//        List<Position> stepPosList = board.getPiece(piecePosition).getPossibleSteps(board.getSize());
//        Move move = new Move(piecePosition);
//        for (Position stepPos : stepPosList) {
//            if (board.getField(stepPos).isEmpty()) {
//                move.setStep(new Step(stepPos));
////                move.addPosToSteps(step);
//            }
//        }
//        return move;
//    }

    private List<Move> getAllHitMove(Board board, PieceColor opponentColor) {
        List<Move> hitPossibilities = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Position piecePosition = new Position(i, j);
                if (!board.getField(piecePosition).isEmpty()) {
                    Piece piece = board.getPiece(piecePosition);
                    if(piece.getColor() == opponentColor) {
                        List<Position> steps = piece.getPossibleSteps(board.getSize());
                        for(Position stepPos : steps) {
                            Move move = getHitMove(piece, board, stepPos, opponentColor);
                            if(move != null) {
                                hitPossibilities.add(move);
                            }
                        }
//                        Move move = searchHits(board, opponentColor, i, j);
//                        if (move.hasSteps()) {
//                            hitPossibilities.add(move);
//                        }
                    }
                }
            }
        }
        return hitPossibilities;
    }

    public boolean searchHits(Board board, PieceColor opponentColor, int i, int j) {
        Position piecePosition = new Position(i, j);
        Move move = new Move(piecePosition);
        Piece piece = board.getPiece(piecePosition);
        List<Position> steps = piece.getPossibleSteps(board.getSize());
        for (Position step : steps) {
            if (!board.getField(step).isEmpty()) {
                if (board.getPiece(step).getColor() != opponentColor && piece.getDirection() == PieceDirection.UP) {
                    if (step.x < i && step.y < j && step.x - 1 >= 0 && step.y - 1 >= 0) {
                        if (board.getField(step.x - 1, step.y - 1).isEmpty()) {
                            Position stepPos = new Position(step.x - 1, step.y - 1);
                            return true;
                        }
                    } else if (step.x < i && step.y > j && step.x - 1 >= 0 && step.y + 1 < board.getSize()) {
                        if (board.getField(step.x - 1, step.y + 1).isEmpty()) {
                            Position stepPos = new Position(step.x - 1, step.y + 1);
                            return true;
                        }
                    }
                    if (piece.isDame()) {
                        if (step.x > i && step.y < j && step.x + 1 < 8 && step.y - 1 >= 0) {
                            if (board.getField(step.x + 1, step.y - 1).isEmpty()) {
                                Position stepPos = new Position(step.x + 1, step.y - 1);
                                return true;
                            }
                        } else if (step.x > i && step.y > j && step.x + 1 < 8 && step.y + 1 < 8) {
                            if (board.getField(step.x + 1, step.y + 1).isEmpty()) {
                                Position stepPos = new Position(step.x + 1, step.y + 1);
                                return true;
                            }
                        }
                    }
                } else if (board.getPiece(step).getColor() != opponentColor && piece.getDirection() == PieceDirection.DOWN) {
                    if (step.x > i && step.y < j && step.x + 1 < 8 && step.y - 1 >= 0) {
                        if (board.getField(step.x + 1, step.y - 1).isEmpty()) {
                            Position stepPos = new Position(step.x + 1, step.y - 1);
                            return true;
                        }
                    } else if (step.x > i && step.y > j && step.x + 1 < 8 && step.y + 1 < 8) {
                        if (board.getField(step.x + 1, step.y + 1).isEmpty()) {
                            Position stepPos = new Position(step.x + 1, step.y + 1);
                            return true;
                        }
                    }
                    if (piece.isDame()) {
                        if (step.x < i && step.y < j && step.x - 1 >= 0 && step.y - 1 >= 0) {
                            if (board.getField(step.x - 1, step.y - 1).isEmpty()) {
                                Position stepPos = new Position(step.x - 1, step.y - 1);
                                return true;
                            }
                        } else if (step.x < i && step.y > j && step.x - 1 >= 0 && step.y + 1 < board.getSize()) {
                            if (board.getField(step.x - 1, step.y + 1).isEmpty()) {
                                Position stepPos = new Position(step.x - 1, step.y + 1);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public List<List<Move>> generateMoves(Board board, boolean selfStep) {
        List<List<Move>> nextMoves = new ArrayList<>();
        nextMoves.addAll(collectAttackMoves(board, selfStep));
        if (nextMoves.size() == 0) {
            List<Move> movesList = collectSimpleMoves(board, selfStep);
            for (Move move : movesList) {
                List<Move> newList = new ArrayList<>();
                newList.add(move);
                nextMoves.add(newList);
            }
        }
        return nextMoves;
    }

    public List<List<Move>> generateMovesWithoutForceAttack(Board board, boolean selfStep) {
        List<List<Move>> nextMoves = new ArrayList<>();
        nextMoves.addAll(collectAttackMoves(board, selfStep));
        List<Move> movesList = collectSimpleMoves(board, selfStep);
        for (Move move : movesList) {
            List<Move> newList = new ArrayList<>();
            newList.add(move);
            nextMoves.add(newList);
        }
        return nextMoves;
    }

    private List<List<Move>> collectAttackMoves(Board board, boolean selfStep) {
        List<List<Move>> nextMoves = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (!board.getFieldsMatrix()[i][j].isEmpty()) {
                    Piece piece = board.getField(i, j).getPiece();
                    if (selfStep) {
                        if (piece.getColor() == selfColor) {
                            List<List<Move>> movesList = getAttackMoves(piece, board, selfColor);
                            nextMoves.addAll(movesList);
                        }
                    } else {
                        if (piece.getColor() != selfColor) {
                            List<List<Move>> movesList = getAttackMoves(piece, board, selfColor);
                            nextMoves.addAll(movesList);
                        }
                    }
                }
            }
        }
//        System.out.println(nextMoves);
        return nextMoves;
    }

    private List<Move> collectSimpleMoves(Board board, boolean selfStep) {
        List<Move> nextMoves = new ArrayList<>();
        for (int i = 0; i < board.getFieldsMatrix().length; i++) {
            for (int j = 0; j < board.getFieldsMatrix()[i].length; j++) {
                if (!board.getField(i, j).isEmpty()) {
                    Piece piece = board.getField(i, j).getPiece();
                    if (selfStep) {
                        if (piece.getColor() == this.selfColor) {
                            List<Move> moves = getSimpleMove(piece, board);
                            for (Move move : moves) {
                                if (move.getStep() != null) {
                                    nextMoves.add(move);
                                }
                            }
                        }
                    } else {
                        if (piece.getColor() != this.selfColor) {
                            List<Move> moves = getSimpleMove(piece, board);
                            for (Move move : moves) {
                                if (move.getStep() != null) {
                                    nextMoves.add(move);
                                }
                            }
                        }
                    }
                }
            }
        }
        return nextMoves;
    }

    private List<Move> getSimpleMove(Piece piece, Board board) {
        List<Move> moves = new ArrayList<>();
        List<Position> steps = piece.getPossibleSteps(board.getSize());
        for (Position stepPos : steps) {
            if (board.getField(stepPos.x, stepPos.y).isEmpty()) {
                Move move = new Move(new Position(piece.getPosX(), piece.getPosY()));
                move.setStep(new Step(stepPos));
//                move.addPosToSteps(step);
                moves.add(move);
            }
        }
        return moves;
    }

    private List<List<Move>> getAttackMoves(Piece piece, Board board, PieceColor selfColor) {
        List<List<Move>> moves = new ArrayList<>();
        List<Position> steps = piece.getPossibleSteps(board.getSize());
        int x = piece.getPosX();
        int y = piece.getPosY();
        for (Position step : steps) {
            if (piece.getColor() != selfColor) {
                if (!board.getField(step.x, step.y).isEmpty()) {
                    if (board.getField(step.x, step.y).getPiece().getColor() == selfColor) {
                        moves.addAll(getHitMovesDirectionIndependent(piece, board, step, selfColor));
                    }
                }
            } else if (piece.getColor() == selfColor) {
                if (!board.getField(step.x, step.y).isEmpty()) {
                    if (board.getField(step.x, step.y).getPiece().getColor() != selfColor) {
                        moves.addAll(getHitMovesDirectionIndependent(piece, board, step, selfColor));
                    }
                }
            }
        }
//        System.out.println(moves);
        return moves;
    }

    private List<List<Move>> getHitMovesDirectionIndependent(Piece piece, Board board, Position step, PieceColor selfColor) {
        List<List<Move>> moves = new ArrayList<>();
        int x = piece.getPosX();
        int y = piece.getPosY();
        List<Move> validMoves = new ArrayList<>();
        if(piece.getDirection() == PieceDirection.DOWN) {
            if (step.y < y) {
                Move move = getValidMove(board, x, y, step.x, step.y, step.x + 1, step.y - 1, (step.x > x));
                if (move != null) {
                    validMoves.add(move);
                }
            } else if (step.y > y) {
                Move move = getValidMove(board, x, y, step.x, step.y, step.x + 1, step.y + 1, (step.x > x));
                if (move != null) {
                    validMoves.add(move);
                }
            }
            if (piece.isDame()) {
                if (step.y < y) {
                    Move move = getValidMove(board, x, y, step.x, step.y, step.x - 1, step.y - 1, (step.x < x));
                    if (move != null) {
                        validMoves.add(move);
                    }
                } else if (step.y > y) {
                    Move move = getValidMove(board, x, y, step.x, step.y, step.x - 1, step.y + 1, (step.x < x));
                    if (move != null) {
                        validMoves.add(move);
                    }
                }
            }
        } else if(piece.getDirection() == PieceDirection.UP) {
            if (step.y < y) {
                Move move = getValidMove(board, x, y, step.x, step.y, step.x - 1, step.y - 1, (step.x < x));
                if (move != null) {
                    validMoves.add(move);
                }
            } else if (step.y > y) {
                Move move = getValidMove(board, x, y, step.x, step.y, step.x - 1, step.y + 1, (step.x < x));
                if (move != null) {
                    validMoves.add(move);
                }
            }
            if (piece.isDame()) {
                if (step.y < y) {
                    Move move = getValidMove(board, x, y, step.x, step.y, step.x + 1, step.y - 1, (step.x > x));
                    if (move != null) {
                        validMoves.add(move);
                    }
                } else if (step.y > y) {
                    Move move = getValidMove(board, x, y, step.x, step.y, step.x + 1, step.y + 1, (step.x > x));
                    if (move != null) {
                        validMoves.add(move);
                    }
                }
            }
        }
        if (validMoves.size() > 0) {
            for (Move firstMove : validMoves) {
                moves.addAll(createMovesList(firstMove, getChainMoves(board, firstMove, selfColor)));
//                System.out.println(moves);
            }
        }
        return moves;
    }

    private List<List<Move>> createMovesList(Move originalMove, List<List<Move>> moveList) {
        List<List<Move>> resultList = new ArrayList<>();
        if (moveList != null && moveList.size() > 0) {
            for (List<Move> moves : moveList) {
                List<Move> newList = new ArrayList<>();
                newList.add(originalMove);
                newList.addAll(moves);
                resultList.add(newList);
            }
        } else {
            List<Move> newList = new ArrayList<>();
            newList.add(originalMove);
            resultList.add(newList);
        }
        return resultList;
    }

    private List<List<Move>> getChainMoves(Board board, Move move, PieceColor selfColor) {
        Board newBoard = executeMove(board, move);
        List<List<Move>> moveLists = new ArrayList<>();
        Piece piece = newBoard.getField(move.getStepPos()).getPiece();
        List<Position> steps = piece.getPossibleSteps(board.getSize());
        for (Position step : steps) {
            Move move1 = getHitMove(piece, newBoard, step, selfColor);
            if (move1 != null) {
                List<List<Move>> moveLists1 = getChainMoves(newBoard, move1, selfColor);
                if (moveLists1.size() != 0) {
                    for (List<Move> m : moveLists1) {
                        List<Move> moves = new ArrayList<>();
                        moves.add(move1);
                        moves.addAll(m);
                        moveLists.add(moves);
                    }
                } else {
                    List<Move> moves = new ArrayList<>();
                    moves.add(move1);
                    moveLists.add(moves);
                }
            }
        }
        return moveLists;
    }

    public Move getHitMove(Piece piece, Board board, Position step, PieceColor selfColor) {
        int x = piece.getPosX();
        int y = piece.getPosY();
        if (piece.getColor() != selfColor) {
            if (!board.getField(step.x, step.y).isEmpty()) {
                if (board.getField(step.x, step.y).getPiece().getColor() == selfColor) {
                    return getHitMoveDirectionIndependent(piece, board, step);
                }
            }
        } else if (piece.getColor() == selfColor) {
            if (!board.getField(step.x, step.y).isEmpty()) {
                if (board.getField(step.x, step.y).getPiece().getColor() != selfColor) {
                    return getHitMoveDirectionIndependent(piece, board, step);
                }
            }
        }
        return null;
    }

    private Move getHitMoveDirectionIndependent(Piece piece, Board board, Position step) {
        int x = piece.getPosX();
        int y = piece.getPosY();
        Move move = null;
        if(piece.getDirection() == PieceDirection.DOWN) {
            if (step.y < y) {
                move = getValidMove(board, x, y, step.x, step.y, step.x + 1, step.y - 1, (step.x > x));
                if (move != null) {
                    return move;
                }
            } else if (step.y > y) {
                move = getValidMove(board, x, y, step.x, step.y, step.x + 1, step.y + 1, (step.x > x));
                if (move != null) {
                    return move;
                }
            }
            if (piece.isDame()) {
                if (step.y < y) {
                    move = getValidMove(board, x, y, step.x, step.y, step.x - 1, step.y - 1, (step.x < x));
                    if (move != null) {
                        return move;
                    }
                } else if (step.y > y) {
                    move = getValidMove(board, x, y, step.x, step.y, step.x - 1, step.y + 1, (step.x < x));
                    if (move != null) {
                        return move;
                    }
                }
            }
        } else if(piece.getDirection() == PieceDirection.UP) {
            if (step.y < y) {
                move = getValidMove(board, x, y, step.x, step.y, step.x - 1, step.y - 1, (step.x < x));
                if (move != null) {
                    return move;
                }
            } else if (step.y > y) {
                move = getValidMove(board, x, y, step.x, step.y, step.x - 1, step.y + 1, (step.x < x));
                if (move != null) {
                    return move;
                }
            }
            if (piece.isDame()) {
                if (step.y < y) {
                    move = getValidMove(board, x, y, step.x, step.y, step.x + 1, step.y - 1, (step.x > x));
                    if (move != null) {
                        return move;
                    }
                } else if (step.y > y) {
                    move = getValidMove(board, x, y, step.x, step.y, step.x + 1, step.y + 1, (step.x > x));
                    if (move != null) {
                        return move;
                    }
                }
            }
        }
        return null;
    }

    private Move getValidMove(Board board, int figposx, int figposy, int hitposx, int hitposy, int stepx, int stepy, boolean xposition) {
        if (xposition) {
            int boardSize = board.getSize();
            if (stepy < boardSize && stepy >= 0 && stepx < boardSize && stepx >= 0) {
                if (board.getField(stepx, stepy).isEmpty()) {
                    Move move = new Move(new Position(figposx, figposy));
                    Position stepPos = new Position(stepx, stepy);
                    Position hitPos = new Position(hitposx, hitposy);
                    move.setStepWithHit(new Step(stepPos), hitPos);
//                    move.addPosToSteps(stepPos, hitPos);
//                    System.out.println("getValidMove:"+ move.toString());
                    return move;
                }
            }
        }
        return null;
    }

    public Board executeMove(Board board, Move move) {
//        System.out.println("execute:");
        Board newBoard = new Board(board.getSize());
        newBoard.setBoardFromArray(board.getFieldsMatrix());
//        printBoard(newBoard.getBoard());
        CheckersField field = newBoard.getField(move.getPiecePos().x, move.getPiecePos().y);
//        newBoard.movePiece(move, field.getPiece().isPlayerPiece(), direction, field.getPiece().getColor(), field.getPiece());
        newBoard.movePieceByAiMove(move);
//        printBoard(newBoard.getBoard());
//        System.out.println(calculateScore(newBoard));
        return newBoard;
    }
}
