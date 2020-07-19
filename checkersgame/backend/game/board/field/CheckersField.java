package checkersgame.backend.game.board.field;

import checkersgame.backend.game.piece.Piece;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;

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
    
    public void setLightFigure(boolean isPlayerPiece, PieceDirection direction) {
        this.empty = false;
        this.piece = new Piece(direction, PieceColor.LIGHT, positionX, positionY);
    }
    
    public void setDarkFigure(PieceDirection direction) {
        this.empty = false;
        this.piece = new Piece(direction, PieceColor.DARK, positionX, positionY);
    }
    
    public void setFigure(PieceDirection direction, PieceColor playerColor) {
        this.empty = false;
        this.piece = new Piece(direction, playerColor, positionX, positionY);
    }
    
    public void setFigure(PieceDirection direction, PieceColor playerColor, boolean isDame) {
        this.empty = false;
        this.piece = new Piece(direction, playerColor, positionX, positionY);
        if (isDame) {
            this.piece.setDame();
        }
    }

    public void setPiece(Piece piece) {
        this.empty = false;
        this.piece = new Piece(piece.getDirection(), piece.getColor(), positionX, positionY);
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

    public void setStatusPrevField() {
        this.status = FieldStatus.PREVFIELD;
    }

    public void setStatusPrevHitField() {
        this.status = FieldStatus.PREVHITFIELD;
    }

    public void setStatusCurrentField() {
        this.status = FieldStatus.CURRENTFIELD;
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
//        setStatusNone();
    }

    public int toInt() {
        if(piece != null && piece.getColor() == PieceColor.DARK) {
            if(piece.isDame()) {
                return 3;
            }else {
                return 1;
            }
        }else if(piece != null && piece.getColor() == PieceColor.LIGHT) {
            if(piece.isDame()) {
                return 4;
            }else {
                return 2;
            }
        }
        return 0;
    }
    
}
