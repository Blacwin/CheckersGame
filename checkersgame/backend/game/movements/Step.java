package checkersgame.backend.game.movements;

import checkersgame.backend.game.board.field.Position;

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
    
    public Position getPosition() {
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
