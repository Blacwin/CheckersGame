/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.backend.field;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Magyar Bálint
 */
public class Piece {
    private boolean playerPiece;
    private PieceColor color;
    private int posX;
    private int posY;
    
    public Piece(boolean playerPiece, PieceColor color, int posX, int posY) {
        this.playerPiece = playerPiece;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
    }
    
    public boolean isPlayerPiece() {
        return playerPiece;
    }
    
    public PieceColor getColor() {
        return color;
    }
    
    public List<Position> getPossibleSteps(int boardSize) {
        List<Position> steps = new ArrayList<>();
        if(this.playerPiece) {
            if(posX-1 > 0) {
                if(posY-1 >= 0) {
                    steps.add(new Position(posX-1, posY-1));
                }
                if(posY+1 < boardSize) {
                    steps.add(new Position(posX-1, posY+1));
                }
            }
        }else {
            if(posX+1 < boardSize) {
                if(posY-1 >= 0) {
                    steps.add(new Position(posX+1, posY-1));
                }
                if(posY+1 < boardSize) {
                    steps.add(new Position(posX+1, posY+1));
                }
            }
        }
        return steps;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    
    
}
