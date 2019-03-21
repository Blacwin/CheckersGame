/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.backend;

import checkersgame.backend.board.Board;
import checkersgame.backend.computer.Ai;
import checkersgame.backend.computer.Move;
import checkersgame.backend.field.CheckersField;
import checkersgame.backend.field.FieldStatus;
import checkersgame.backend.field.Piece;
import checkersgame.backend.field.PieceColor;
import checkersgame.backend.field.Position;
import checkersgame.backend.field.Step;
import checkersgame.frontend.GuiManager;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 *
 * @author Magyar Bálint
 */
public class Model {

    private CheckersField fields[][];
    private Board board;
    private final int BOARD_SIZE = 8;
    private CheckersField selectedField;
    private PieceColor playerColor;
    private PieceColor computerColor;
    private PieceColor currentColor;
    private Move currentMove;
    private Ai ai;

    public Model() {
        this.fields = new CheckersField[BOARD_SIZE][BOARD_SIZE];
        this.board = new Board(BOARD_SIZE);
    }

    public void startNewGame(String difficult, String color) {
        if (color.equals("Light")) {
            setPlayerColorLight();
        } else if (color.equals("Dark")) {
            setPlayerColorDark();
        }
        this.ai = new Ai(computerColor, BOARD_SIZE);
        this.board.initBoard(computerColor, playerColor);
        this.currentColor = PieceColor.DARK;
//        this.fields[4][3].setFigure(false, computerColor);
//        this.fields[4][5].setFigure(false, computerColor);
        GuiManager.initGameBoard();
        if (currentColor != playerColor) {
            generateComputerStep();
        }
        this.currentMove = new Move();
    }

    public CheckersField[][] getBoard() {
        return board.getBoard();
    }

    public void setPlayerColorLight() {
        this.playerColor = PieceColor.LIGHT;
        this.computerColor = PieceColor.DARK;
    }

    public void setPlayerColorDark() {
        this.playerColor = PieceColor.DARK;
        this.computerColor = PieceColor.LIGHT;
    }

    public boolean isPlayerRound() {
        return (currentColor == playerColor);
    }

    private void nextPlayer() {
        if (currentColor == PieceColor.DARK) {
            currentColor = PieceColor.LIGHT;
        } else if (currentColor == PieceColor.LIGHT) {
            currentColor = PieceColor.DARK;
        }
    }

    public boolean onFieldClick(int x, int y) {
        if (isPlayerRound()) {
            CheckersField field = board.getField(x, y);//fields[x][y];
            if (!field.isEmpty() && field.getPiece().getColor() == this.playerColor) {
                if (selectedField != field) {
                    this.board.resetFieldsStatus();
                    this.selectedField = field;
//                    this.fields[x][y].setStatusSelectedField();
                    System.out.println("kijelol");
                    currentMove = calculatePossibleSteps(x, y);
                    board.updateBoardByMove(currentMove);
                    GuiManager.refreshGameBoard();
                }
            } else if (field.isEmpty() && field.getStatus() == FieldStatus.STEPFIELD) {
                this.currentMove.setSelectedStepPos(new Position(x, y));
                boolean hit = board.movePiece(currentMove, true, playerColor);
                this.board.resetFieldsStatus();
                GuiManager.refreshGameBoard();
                Move move = searchHits(x, y);
                //System.out.println(x + ", " + y);
                if(hit) {
                    if(!move.hasHits()) {
                        nextPlayer();
                        generateComputerStep();
                    }
                }else {
                    nextPlayer();
                    generateComputerStep();
                }
                return true;
            }
        }
        return false;
    }

    private Move calculatePossibleSteps(int piecex, int piecey) {
        int x = piecex;
        int y = piecey;

        List<Move> hitMoves = getHitPossibilities();
        if (hitMoves.size() > 0) {
            boolean found = false;
            Move hitMove = new Move();
            for (int i = 0; i < hitMoves.size() && !found; i++) {
                if (x == hitMoves.get(i).getPiecePos().x && y == hitMoves.get(i).getPiecePos().y) {
                    found = true;
                    hitMove = hitMoves.get(i);
                }
            }
            if (found) {
//                this.currentMove = hitMove;
                return hitMove;
            }
        } else if (hitMoves.size() == 0) {
            Position pos = new Position(selectedField.getPositionX(), selectedField.getPositionY());
            List<Position> stepPos = this.selectedField.getPiece().getPossibleSteps(BOARD_SIZE);
            Move move = new Move(pos);
//            fields[pos.x][pos.y].setStatusSelectedField();
            for (Position step : stepPos) {
                if (board.getField(step.x, step.y).isEmpty()) {
                    move.addPosToSteps(step);
//                    fields[step.x][step.y].setStatusStepField();
                }
            }
//            this.currentMove = move;
            return move;
        }
        return null;
    }

    private List<Move> getHitPossibilities() {
        List<Move> hitPossibilities = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!board.getField(i, j).isEmpty()) {
                    if (board.getField(i, j).getPiece().getColor() == playerColor) {
                        Move move = searchHits(i, j);
                        if (move.hasSteps()) {
                            hitPossibilities.add(move);
                        }
                    }
                }
            }
        }
        return hitPossibilities;
    }

    private Move searchHits(int i, int j) {
        Move move = new Move(new Position(i, j));
        List<Position> steps = board.getField(i, j).getPiece().getPossibleSteps(BOARD_SIZE);
        for (Position step : steps) {
            if (!board.getField(step.x, step.y).isEmpty()) {
                if (board.getField(step.x, step.y).getPiece().getColor() != playerColor) {
                    if (step.y < j && step.x - 1 >= 0 && step.y - 1 >= 0) {
                        if (board.getField(step.x-1, step.y-1).isEmpty()) {
                            move.addPosToHits(step);
                            Position stepPos = new Position(step.x - 1, step.y - 1);
                            move.addPosToSteps(stepPos, step);
                        }
                    } else if (step.y > j && step.x - 1 >= 0 && step.y + 1 < BOARD_SIZE) {
                        if (board.getField(step.x-1, step.y+1).isEmpty()) {
                            move.addPosToHits(step);
                            Position stepPos = new Position(step.x - 1, step.y + 1);
                            move.addPosToSteps(stepPos, step);
                        }
                    }
                }
            }
        }
        return move;
    }
    
    private boolean hitPieceByCoord() {
        if(this.currentMove.getSelectedStep() != null && this.currentMove.getSelectedStep().isHitStep()) {
            Position hitPos = this.currentMove.getSelectedStep().getHitPosition();
            board.getField(hitPos.x, hitPos.y).setEmpty();
            return true;
        }
        return false;
    }

    public void generateComputerStep() {
        ai.calculateStep(board.getBoard());
        Position figpos = ai.getFigurePos();
        Position steppos = ai.getStepPos();
        Position hitpos = ai.getHitPos();
        if (figpos != null && steppos != null) {
//            this.fields[steppos.x][steppos.y].setFigure(false, computerColor);
//            this.fields[figpos.x][figpos.y].setEmpty();
            board.movePiece(figpos, steppos, false, computerColor);
            if (hitpos != null) {
                this.board.getBoard()[hitpos.x][hitpos.y].setEmpty();
            }
            Move move = searchHits(steppos.x, steppos.y);
            if(!move.hasSteps()) {
                nextPlayer();
                GuiManager.refreshByAiStep();
            } else {
                generateComputerStep();
            }
            return;
        }
//        CheckersField field = fields[figpos.x][figpos.y];
//        if (!field.isEmpty() && field.getPiece().getColor() == this.computerColor) {
//            int x = figpos.x + 1;
//            int y = figpos.y;
//            if( (steppos.x == x) && (steppos.y == y-1 || steppos.y == y+1)) {
//                this.fields[steppos.x][steppos.y].setFigure(false, computerColor);
//                this.fields[figpos.x][figpos.y].setEmpty();
//                nextPlayer();
//                GuiManager.refreshByAiStep();
//                return;
//            }
//        }
        System.out.println("Nem tud lépni az AI");
        nextPlayer();
    }
}
