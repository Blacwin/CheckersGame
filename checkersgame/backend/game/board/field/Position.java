package checkersgame.backend.game.board.field;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean equalsTo(Position other) {
        if(this.x == other.x && this.y == other.y) {
            return true;
        }
        return false;
    }

    public double distanceOf(Position other) {
        double distance = Math.sqrt(Math.pow(x-other.x, 2) + Math.pow(y-other.y, 2));
        return distance;
    }
    
    @Override
    public String toString() {
        return "x: "+x+" y: "+y;
    }
}
