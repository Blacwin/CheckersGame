package checkersgame.backend.game.movements;

import checkersgame.backend.game.board.field.Position;

public class Move {
    private Position piecePos;
    private Step step;
    private Step selectedStep;
    private double score;

    public Move(Position piecePos) {
        this.piecePos = piecePos;
        this.score = 0;
    }
    
    public Move() {
        this.score = 0;
    }

    public Move(Position piecePos, Position stepPos) {
        this.piecePos = piecePos;
        this.step = new Step(stepPos);
    }

    public Move(int moveInteger) {
        int piecex = moveInteger/100000;
        moveInteger -= piecex*100000;
        int piecey = moveInteger/10000;
        moveInteger -= piecey*10000;
        this.piecePos = new Position(piecex, piecey);
        int stepx =  moveInteger/1000;
        moveInteger -= stepx*1000;
        int stepy = moveInteger/100;
        moveInteger -= stepy*100;
        Position stepPos = new Position(stepx, stepy);
        this.step = new Step(stepPos);
        int hitx = moveInteger/10;
        moveInteger -= hitx*10;
        int hity = moveInteger;
        if(hitx > 0 && hity > 0) {
            this.step.setHitPosition(new Position(hitx, hity));
        }
    }
    
    public void setPiecePos(Position piecePos) {
        this.piecePos = piecePos;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public double getScore() {
        return score;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public void setStepWithHit(Step step, Position hit) {
        this.step = step;
        this.step.setHitPosition(hit);
    }
    
    public Position getPiecePos() {
        return piecePos;
    }
    
    public Step getStep() {
        return step;
    }

    public Position getStepPos() {
        return step.getPosition();
    }
    
    public Step getSelectedStep() {
        return selectedStep;
    }
    
    public boolean hasHits() {
        return step.isHitStep();
    }    
    
    @Override
    public String toString() {
        String result;
        String[] abc = {"a", "b", "c", "d", "e", "f", "g", "h"};
        result = "Piece: "+(piecePos.x+1) +"-"+abc[piecePos.y] +" Step: "+(step.getPosition().x+1) +"-"+abc[step.getPosition().y];
        if(step.isHitStep()) {
            result += " Hit: "+(step.getHitPosition().x+1) +"-"+abc[step.getHitPosition().y];
        }
        result += " score: " + score;
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if(!(other instanceof Move)) {
            return false;
        }

        Move otherMove = (Move) other;

        return ( otherMove.getPiecePos().equalsTo(this.piecePos) && otherMove.getStepPos().equalsTo(this.getStepPos()) );
    }

    public Move copy() {
        Move newMove = new Move(new Position(piecePos.x, piecePos.y));
        newMove.setStep(step);
        newMove.setScore(score);
        return newMove;
    }

    public int getSaveFormat() {
        int px = piecePos.x*100000;
        int py = piecePos.y*10000;
        int sx = step.getPosition().x*1000;
        int sy = step.getPosition().y*100;
        int hx = 0;
        int hy = 0;
        if(step.isHitStep()) {
            hx = step.getHitPosition().x*10;
            hy = step.getHitPosition().y;
        }
        return px + py + sx + sy + hx + hy;
    }
}
