/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.backend.computer;

import checkersgame.backend.board.Board;
import checkersgame.backend.field.CheckersField;
import checkersgame.backend.field.Piece;
import checkersgame.backend.field.PieceColor;
import checkersgame.backend.field.Position;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Magyar Bálint
 */
public class Ai {

    private Position figPos;
    private Position stepPos;
    private Position hitPos;
    private PieceColor selfColor;
    private int boardSize;

    public Ai(PieceColor color, int size) {
        this.selfColor = color;
        this.boardSize = size;
    }

    public void calculateStep(Board board) {
        List<Move> moves = generateMoves(board, true);
        this.figPos = moves.get(0).getPiecePos();
        this.stepPos = moves.get(0).getStepPos(0);
        if (moves.get(0).getHitPositions().size() != 0) {
            this.hitPos = moves.get(0).getHitPositions().get(0);
        } else {
            this.hitPos = null;
        }
        minimax(fields);
    }

    private List<Move> generateMoves(Board board, boolean selfStep) {
        List<Move> nextMoves = new ArrayList<>();
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (!board.getBoard()[i][j].isEmpty()) {
                    Piece piece = board.getField(i, j).getPiece();
                    if (selfStep) {
                        if (piece.getColor() == this.selfColor) {
                            Move move = getHitMoves(piece, board);
                            if (move.hasSteps()) {
                                nextMoves.add(move);
                            }
                        }
                    } 
                    else {
                        if (piece.getColor() != this.selfColor) {
                            Move move = getHitMoves(piece, board);
                            if (move.hasSteps()) {
                                nextMoves.add(move);
                            }
                        }
                    }
                }
            }
        }
        if (nextMoves.size() == 0) {
            for (int i = 0; i < board.getBoard().length; i++) {
                for (int j = 0; j < board.getBoard()[i].length; j++) {
                    if (!board.getField(i, j).isEmpty()) {
                        Piece piece = board.getField(i, j).getPiece();
                        if (selfStep) {
                            if (piece.getColor() == this.selfColor) {
                                Move move = getSimpleMove(piece, board);
                                if (move.hasSteps()) {
                                    nextMoves.add(move);
                                }
                            }
                        } else {
                            if (piece.getColor() != this.selfColor) {

                            }
                        }
                    }
                }
            }
        }

        return nextMoves;
    }
    
    private Move getSimpleMove(Piece piece, Board board) {
        Move move = new Move(new Position(piece.getPosX(), piece.getPosY()));
        List<Position> steps = piece.getPossibleSteps(boardSize);
        for (Position step : steps) {
            if (board.getField(step.x, step.y).isEmpty()) {
                move.addPosToSteps(step);
            }
        }
        return move;
    }

    private Move getHitMoves(Piece piece, Board board) {
        Move move = new Move(new Position(piece.getPosX(), piece.getPosY()));
        List<Position> steps = piece.getPossibleSteps(boardSize);
        for (Position step : steps) {
            if (step.x < move.getPiecePos().x) {
                if (!board.getField(step.x, step.y).isEmpty()) {
                    if (board.getField(step.x, step.y).getPiece().getColor() == this.selfColor) {
                        if (step.y < piece.getPosY()) {
                            if (board.getField(step.x - 1, step.y - 1).isEmpty()) {
                                move.addPosToHits(step);
                                Position stepPos = new Position(step.x + 1, step.y - 1);
                                Position hitPos = new Position(step.x, step.y);
                                move.addPosToSteps(stepPos, hitPos);
                            }
                        } else if (step.y > piece.getPosY()) {
                            if (board.getField(step.x - 1, step.y + 1).isEmpty()) {
                                move.addPosToHits(step);
                                Position stepPos = new Position(step.x + 1, step.y + 1);
                                Position hitPos = new Position(step.x, step.y);
                                move.addPosToSteps(stepPos, hitPos);
                            }
                        }
                    }
                }
            } else if (step.x > move.getPiecePos().x) {
                if (!board.getField(step.x, step.y).isEmpty()) {
                    if (board.getField(step.x, step.y).getPiece().getColor() != this.selfColor) {
                        if (step.y < piece.getPosY()) {
                            if (board.getField(step.x + 1, step.y - 1).isEmpty()) {
                                move.addPosToHits(step);
                                Position stepPos = new Position(step.x + 1, step.y - 1);
                                Position hitPos = new Position(step.x, step.y);
                                move.addPosToSteps(stepPos, hitPos);
                            }
                        } else if (step.y > piece.getPosY()) {
                            if (board.getField(step.x + 1, step.y + 1).isEmpty()) {
                                move.addPosToHits(step);
                                Position stepPos = new Position(step.x + 1, step.y + 1);
                                Position hitPos = new Position(step.x, step.y);
                                move.addPosToSteps(stepPos, hitPos);
                            }
                        }
                    }
                }
            }
        }
        return move;
    }

    private void minimax(CheckersField[][] board, int level, boolean computer) {
        if (level == 0) {
            return;
        }
        if (computer) {

        }
        printBoard(board);
    }

    private void printBoard(CheckersField[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].isEmpty()) {
                    System.out.print("0, ");
                } else {
                    if (board[i][j].getPiece().getColor() == PieceColor.DARK) {
                        System.out.print("1, ");
                    } else {
                        System.out.print("2, ");
                    }
                }
            }
            System.out.println("");
        }
    }

    public Position getFigurePos() {
        return figPos;
    }

    public Position getStepPos() {
        return stepPos;
    }

    public Position getHitPos() {
        return hitPos;
    }
}
