/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.backend.board;

import checkersgame.backend.computer.Move;
import checkersgame.backend.field.CheckersField;
import checkersgame.backend.field.PieceColor;
import checkersgame.backend.field.Position;
import checkersgame.backend.field.Step;
import java.util.List;

/**
 *
 * @author Magyar Bálint
 */
public class Board {

    private CheckersField board[][];
    private int BOARD_SIZE;

    public Board(int BOARD_SIZE) {
        this.BOARD_SIZE = BOARD_SIZE;
        this.board = new CheckersField[BOARD_SIZE][BOARD_SIZE];
    }

    public void initBoard(PieceColor computerColor, PieceColor playerColor) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new CheckersField(i, j);
                if ((i + j) % 2 == 1 && i < 3) {
                    board[i][j].setFigure(false, computerColor);
                } else if ((i + j) % 2 == 1 && i > 4) {
                    board[i][j].setFigure(true, playerColor);
                }
            }
        }
//        board[3][2].setFigure(true, playerColor);
//        board[6][5].setEmpty();
    }
    
    public CheckersField[][] getBoard() {
        return board;
    }
    
    public CheckersField getField(int x, int y) {
        return board[x][y];
    }
    
    public void updateBoardByMove(Move move) {
        if(move != null) {
            Position figPos = move.getPiecePos();
            List<Step> stepPos = move.getSteps();
            List<Position> hitPos = move.getHitPositions();
            this.board[figPos.x][figPos.y].setStatusSelectedField();
            for (Step step : stepPos) {
                this.board[step.getStep().x][step.getStep().y].setStatusStepField();
            }
            for (Position hit : hitPos) {
                this.board[hit.x][hit.y].setStatusHitField();
            }
        }
    }
    
    public void resetFieldsStatus() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j].setStatusNone();
            }
        }
    }
    
    public void movePiece(Position piece, Position step, boolean player, PieceColor color) {
        this.board[piece.x][piece.y].setEmpty();
        this.board[step.x][step.y].setFigure(player, color);
    }
    
    public boolean movePiece(Move move, boolean player, PieceColor color) {
        Step step = move.getSelectedStep();
        if(step != null) {
//            this.board[step.getStep().x][step.getStep().y].setFigure(player, color);
//            this.board[move.getPiecePos().x][move.getPiecePos().y].setEmpty();
            movePiece(move.getPiecePos(), step.getStep(), player, color);
            if(step.isHitStep() ) {
                Position hitPos = step.getHitPosition();
                board[hitPos.x][hitPos.y].setEmpty();
                return true;
            }
        }
        return false;
    }

}
