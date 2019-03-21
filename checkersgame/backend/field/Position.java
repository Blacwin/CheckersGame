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
}
