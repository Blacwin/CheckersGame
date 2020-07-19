/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.backend.game.board;

import checkersgame.backend.game.movements.Move;
import checkersgame.backend.game.board.field.*;

import java.util.ArrayList;
import java.util.List;

import checkersgame.backend.game.movements.Step;
import checkersgame.backend.game.piece.Piece;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;
import org.json.JSONArray;

public class Board {

    private CheckersField[][] board;
    private final int BOARD_SIZE;

    public Board(int BOARD_SIZE) {
        this.BOARD_SIZE = BOARD_SIZE;
        this.board = new CheckersField[BOARD_SIZE][BOARD_SIZE];
    }

    public void initBoard(PieceColor topOpponentColor, PieceColor bottomOpponentColor) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new CheckersField(i, j);
                if ((i + j) % 2 == 1 && i < 3) {
                    board[i][j].setFigure(PieceDirection.DOWN, topOpponentColor);
                } else if ((i + j) % 2 == 1 && i > 4) {
                    board[i][j].setFigure(PieceDirection.UP, bottomOpponentColor);
                }
            }
        }
    }

    public void setBoardFromArray(CheckersField[][] fields) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.board[i][j] = new CheckersField(i, j);
                if (!fields[i][j].isEmpty()) {
                    Piece piece = fields[i][j].getPiece();
                    boolean isDame = fields[i][j].getPiece().isDame();
                    PieceDirection direction = (piece.getDirection() == PieceDirection.UP) ? PieceDirection.UP : PieceDirection.DOWN;
                    PieceColor color = (piece.getColor() == PieceColor.LIGHT) ? PieceColor.LIGHT : PieceColor.DARK;
                    this.board[i][j].setFigure(direction, color, isDame);
                }
            }
        }
    }

    public void setBoardFromIntArray(int[][] array, PieceDirection darkDirection, PieceDirection lightDirection) {
        CheckersField[][] fields = new CheckersField[BOARD_SIZE][BOARD_SIZE];
        for (int i=0; i<array.length; i++) {
            int[] cols = array[i];
            for (int j=0; j<cols.length; j++) {
                fields[i][j] = new CheckersField(i, j);
                PieceColor color;
                if (cols[j] == 1 || cols[j] == 3) {
                    fields[i][j].setFigure(darkDirection, PieceColor.DARK);
                    if(cols[j] == 3) {
                        fields[i][j].getPiece().setDame();
                    }
                }
                else if (cols[j] == 2 || cols[j] == 4) {
                    fields[i][j].setFigure(lightDirection, PieceColor.LIGHT);
                    if(cols[j] == 4) {
                        fields[i][j].getPiece().setDame();
                    }
                }
            }
        }
        setBoardFromArray(fields);
    }

    public void setBoardFromJsonArray(JSONArray array, int cpu, int ply) {
        CheckersField[][] fields = new CheckersField[BOARD_SIZE][BOARD_SIZE];
        for (int i=0; i<array.length(); i++) {
            JSONArray cols = array.getJSONArray(i);
            for (int j=0; j<cols.length(); j++) {
                fields[i][j] = new CheckersField(i, j);
                PieceColor color;
                if (cols.getInt(j) == 1) {
                    color = PieceColor.DARK;
                    if (1 == cpu) {
                        fields[i][j].setFigure(PieceDirection.DOWN, color);
                    }
                    else if (1 == ply) {
                        fields[i][j].setFigure(PieceDirection.UP, color);
                    }
                }
                else if (cols.getInt(j) == 2) {
                    color = PieceColor.LIGHT;
                    if (2 == cpu) {
                        fields[i][j].setFigure(PieceDirection.DOWN, color);
                    }
                    else if (2 == ply) {
                        fields[i][j].setFigure(PieceDirection.UP, color);
                    }
                }
                else if (cols.getInt(j) == 3) {
                    color = PieceColor.DARK;
                    if (1 == cpu) {
                        fields[i][j].setFigure(PieceDirection.DOWN, color);
                    }
                    else if (1 == ply) {
                        fields[i][j].setFigure(PieceDirection.UP, color);
                    }
                    fields[i][j].getPiece().setDame();
                }
                else if (cols.getInt(j) == 4) {
                    color = PieceColor.LIGHT;
                    if (2 == cpu) {
                        fields[i][j].setFigure(PieceDirection.DOWN, color);
                    }
                    else if (2 == ply) {
                        fields[i][j].setFigure(PieceDirection.UP, color);
                    }
                    fields[i][j].getPiece().setDame();
                }
            }
        }
        setBoardFromArray(fields);
    }

    public CheckersField[][] getFieldsMatrix() {
        return board;
    }

    public CheckersField getField(int x, int y) {
        return board[x][y];
    }
    
    public CheckersField getField(Position pos) {
        return board[pos.x][pos.y];
    }

    public Piece getPiece(Position pos) {
        return board[pos.x][pos.y].getPiece();
    }

    public CheckersField getFieldByPosition(Position position) {
        return board[position.x][position.y];
    }

    public int getSize() {
        return BOARD_SIZE;
    }

    public int getLightPieceNumber() {
        int number = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!board[i][j].isEmpty()) {
                    if (board[i][j].getPiece().getColor() == PieceColor.LIGHT) {
                        number += 1;
                    }
                }
            }
        }
        return number;
    }

    public int getDarkPieceNumber() {
        int number = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!board[i][j].isEmpty()) {
                    if (board[i][j].getPiece().getColor() == PieceColor.DARK) {
                        number += 1;
                    }
                }
            }
        }
        return number;
    }

    public void markStepableField(List<Move> moves) {
        for(Move move : moves) {
            Position figPos = move.getPiecePos();
            Position stepPos = move.getStepPos();
            Position hitPos = move.getStep().getHitPosition();
            this.board[figPos.x][figPos.y].setStatusSelectedField();
            this.board[stepPos.x][stepPos.y].setStatusStepField();
            if(hitPos != null) {
                this.board[hitPos.x][hitPos.y].setStatusHitField();
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
    
    private boolean checkPieceReachedTheWall(Piece piece) {
        if (!piece.isDame() && piece.getDirection() == PieceDirection.UP) {
            if (piece.getPosX() == 0) {
                return true;
            }
        }
        else if (!piece.isDame() && piece.getDirection() == PieceDirection.DOWN) {
            if (piece.getPosX() == BOARD_SIZE-1) {
                return true;
            }
        }
        return false;
    }

    public void movePiece(Position piece, Position step, boolean player, PieceDirection direction, PieceColor color, Piece figure) {
        this.board[piece.x][piece.y].setEmpty();
        this.board[step.x][step.y].setFigure(direction, color);
        if (checkPieceReachedTheWall(board[step.x][step.y].getPiece())) {
            this.board[step.x][step.y].getPiece().setDame();
        }
        if (figure.isDame()) {
            this.board[step.x][step.y].getPiece().setDame();
        }
    }

    public boolean movePiece(Move move, boolean player, PieceDirection direction, PieceColor color, Piece piece) {
        Step step = move.getSelectedStep();
        if (step == null) {
            step = move.getStep();
        }
//        System.out.println("x: "+step.getStep().x+" y: "+step.getStep().y+" ply: "+player+" color: "+color);
        if (step != null) {
            movePiece(move.getPiecePos(), step.getPosition(), player, direction, color, piece);
            if (step.isHitStep()) {
                Position hitPos = step.getHitPosition();
                this.board[hitPos.x][hitPos.y].setEmpty();
                return true;
            }
        }
        return false;
    }

    public void movePieceByPlayerMove(Move move) {
        Piece piece = getField(move.getPiecePos()).getPiece();
        Position piecePos = move.getPiecePos();
        board[piecePos.x][piecePos.y].setStatusPrevField();
        Position stepPos = move.getStepPos();
        board[stepPos.x][stepPos.y].setStatusCurrentField();
        Position hitPos = move.getStep().getHitPosition();
        if(hitPos != null) {board[hitPos.x][hitPos.y].setStatusPrevHitField();}
//        movePiece(move, piece.isPlayerPiece(), piece.getDirection(), piece.getColor(), piece);
        setEmpty(piecePos);
        putPiece(stepPos, piece);
        if(hitPos != null) {
            setEmpty(hitPos);
        }
        if(checkPieceReachedTheWall(board[stepPos.x][stepPos.y].getPiece())) {
            board[stepPos.x][stepPos.y].getPiece().setDame();
        }
        if(piece.isDame()) {
            board[stepPos.x][stepPos.y].getPiece().setDame();
        }
    }

    public void movePieceByAiMove(Move move) {
        Piece piece = getField(move.getPiecePos()).getPiece();
        Position piecePos = move.getPiecePos();
        Position stepPos = move.getStepPos();
        Position hitPos = move.getStep().getHitPosition();
        setEmpty(piecePos);
        putPiece(stepPos, piece);
        if(hitPos != null) {
            setEmpty(hitPos);
        }
        if(checkPieceReachedTheWall(board[stepPos.x][stepPos.y].getPiece())) {
            board[stepPos.x][stepPos.y].getPiece().setDame();
        }
        if(piece.isDame()) {
            board[stepPos.x][stepPos.y].getPiece().setDame();
        }
    }

    public Piece removePieceByMove(Move move) {
        Piece piece = getField(move.getPiecePos()).getPiece();
        Position piecePos = move.getPiecePos();
        board[piecePos.x][piecePos.y].setStatusPrevField();
        setEmpty(piecePos);
        return piece;
    }

    public void movePieceByMoveAndPiece(Move move, Piece pc) {
        Piece piece = pc;
        Position piecePos = move.getPiecePos();
        board[piecePos.x][piecePos.y].setStatusPrevField();
        Position stepPos = move.getStepPos();
        board[stepPos.x][stepPos.y].setStatusCurrentField();
        Position hitPos = move.getStep().getHitPosition();
        if(hitPos != null) {board[hitPos.x][hitPos.y].setStatusPrevHitField();}
//        movePiece(move, piece.isPlayerPiece(), piece.getDirection(), piece.getColor(), piece);
        setEmpty(piecePos);
        putPiece(stepPos, piece);
        if(hitPos != null) {
            setEmpty(hitPos);
        }
        if(checkPieceReachedTheWall(board[stepPos.x][stepPos.y].getPiece())) {
            board[stepPos.x][stepPos.y].getPiece().setDame();
        }
        if(piece.isDame()) {
            board[stepPos.x][stepPos.y].getPiece().setDame();
        }
    }

    private void putPiece(Position pos, Piece piece) {
        this.board[pos.x][pos.y].setPiece(piece);
//        this.board[pos.x][pos.y].setFigure(piece.isPlayerPiece(), piece.getDirection(), piece.getColor());
    }

    private void setEmpty(Position pos) {
        this.board[pos.x][pos.y].setEmpty();
    }

    public List<Piece> getPiecesList() {
        List<Piece> pieceList = new ArrayList<>();
        for(CheckersField[] row : board) {
            for(CheckersField field : row) {
                if(!field.isEmpty()) {
                    pieceList.add(field.getPiece());
                }
            }
        }
        return pieceList;
    }

    public List<Piece> getPiecesList(PieceColor color) {
        List<Piece> pieceList = new ArrayList<>();
        for(CheckersField[] row : board) {
            for(CheckersField field : row) {
                if(!field.isEmpty() && field.getPiece().getColor() == color) {
                    pieceList.add(field.getPiece());
                }
            }
        }
        return pieceList;
    }

    public int getPieceNumberOfColor(PieceColor color) {
        int sum = 0;
        for(CheckersField[] row : board) {
            for(CheckersField field : row) {
                if(!field.isEmpty() && field.getPiece().getColor() == color) {
                    sum += 1;
                }
            }
        }
        return sum;
    }
    
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].isEmpty()) {
                    result += "0, ";
                } else {
                    if (board[i][j].getPiece().getColor() == PieceColor.DARK) {
                        if (board[i][j].getPiece().isDame()) {
                            result += "3, ";
                        } else {
                            result += "1, ";
                        }
                    } else {
                        if (board[i][j].getPiece().isDame()) {
                            result += "4, ";
                        } else {
                            result += "2, ";
                        }
                    }
                }
            }
            result += "\n";
        }
        return result;
    }
    
    public void printBoard(CheckersField[][] board) {
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
        System.out.println("---------------------------");
    }

    public void setStatusSelected(List<Move> possibleMoves) {
        for(Move move : possibleMoves) {
            board[move.getPiecePos().x][move.getPiecePos().y].setStatusStepField();
        }
    }
}
