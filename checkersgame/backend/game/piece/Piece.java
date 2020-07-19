package checkersgame.backend.game.piece;

import checkersgame.backend.game.board.field.Position;

import java.util.ArrayList;
import java.util.List;

public class Piece {

//    private boolean playerPiece;
    private boolean dame;
    private PieceColor color;
    private PieceDirection direction;
    private int posX;
    private int posY;
    private Position position;

    public Piece(PieceDirection direction, PieceColor color, int posX, int posY) {
//        this.playerPiece = playerPiece;
        this.direction = direction;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.position = new Position(posX, posY);
        this.dame = false;
    }

    public Piece(PieceDirection direction, PieceColor color, int posX, int posY, boolean dame) {
//        this.playerPiece = playerPiece;
        this.direction = direction;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.position = new Position(posX, posY);
        this.dame = dame;
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceDirection getDirection() { return direction; }

    public List<Position> getPossibleSteps(int boardSize) {
        List<Position> steps = new ArrayList<>();
        if (this.direction == PieceDirection.UP) {
            if (posX - 1 >= 0) {
                if (posY - 1 >= 0) {
                    steps.add(new Position(posX - 1, posY - 1));
                }
                if (posY + 1 < boardSize) {
                    steps.add(new Position(posX - 1, posY + 1));
                }
            }
            if (dame) {
                if (posX + 1 < boardSize) {
                    if (posY - 1 >= 0) {
                        steps.add(new Position(posX + 1, posY - 1));
                    }
                    if (posY + 1 < boardSize) {
                        steps.add(new Position(posX + 1, posY + 1));
                    }
                }
            }
        } else {
            if (posX + 1 < boardSize) {
                if (posY - 1 >= 0) {
                    steps.add(new Position(posX + 1, posY - 1));
                }
                if (posY + 1 < boardSize) {
                    steps.add(new Position(posX + 1, posY + 1));
                }
            }
            if (dame) {
                if (posX - 1 >= 0) {
                    if (posY - 1 >= 0) {
                        steps.add(new Position(posX - 1, posY - 1));
                    }
                    if (posY + 1 < boardSize) {
                        steps.add(new Position(posX - 1, posY + 1));
                    }
                }
            }
        }
        return steps;
    }

    public void setDame() {
        this.dame = true;
    }

    public boolean isDame() {
        return dame;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Position getPosition() {
        return position;
    }

    public Piece copy() {
        Piece clone = new Piece(direction, color, posX, posY, dame);
        return clone;
    }

}
