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
public class Step{
    private Position step;
    private boolean hitStep;
    private Position hitPos;
    
    public Step(int x, int y) {
        this.step = new Position(x, y);
        this.hitStep = false;
    }
    
    public Step(Position pos) {
        this.step = pos;
        this.hitStep = false;
    }
    
    public Position getStep() {
        return step;
    }
    
    public boolean isHitStep() {
        return hitStep;
    }
    
    public void setHitPosition(Position hitPos) {
        this.hitStep = true;
        this.hitPos = hitPos;
    }
    
    public Position getHitPosition() {
        return hitPos;
    }
    
}
