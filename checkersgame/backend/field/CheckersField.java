/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.backend.field;

/**
 *
 * @author Magyar Bálint
 */
public class CheckersField {
    private int positionX;
    private int positionY;
    private Piece piece;
    private boolean empty;
    private FieldStatus status;

    public CheckersField(int x, int y) {
        this.positionX = x;
        this.positionY = y;
        this.empty = true;
        this.status = FieldStatus.NONE;
    }
    
    public void setLightFigure(boolean isPlayerPiece) {
        this.empty = false;
        this.piece = new Piece(isPlayerPiece, PieceColor.LIGHT, positionX, positionY);
    }
    
    public void setDarkFigure(boolean isPlayerPiece) {
        this.empty = false;
        this.piece = new Piece(isPlayerPiece, PieceColor.DARK, positionX, positionY);
    }
    
    public void setFigure(boolean isPlayerPiece, PieceColor playerColor) {
        this.empty = false;
        this.piece = new Piece(isPlayerPiece, playerColor, positionX, positionY);
    }
    
    public Piece getPiece() {
        return piece;
    }
    
    public boolean isEmpty() {
        return empty;
    }
    
    public void setStatusStepField() {
        this.status = FieldStatus.STEPFIELD;
    }
    
    public void setStatusHitField() {
        this.status = FieldStatus.HITFIELD;
    }
    
    public void setStatusSelectedField() {
        this.status = FieldStatus.SELECTEDFIELD;
    }
    
    public void setStatusNone() {
        this.status = FieldStatus.NONE;
    }
    
    public FieldStatus getStatus() {
        return status;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setEmpty() {
        this.empty = true;
        this.piece = null;
        setStatusNone();
    }
    
}
