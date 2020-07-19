package checkersgame.backend.controllers;

import checkersgame.backend.game.Game;
import checkersgame.backend.game.opponents.Opponent;

public class Demo {
    private int bottomStepper;
    private int topStepper;
    private Opponent starterOpponent;
    private Game game;
    private int numberOfMoves;
    private int boardIndex;

    public Demo(Game game, int numberOfMoves) {
        this.game = game;
        this.numberOfMoves = numberOfMoves;
        this.bottomStepper = 0;
        this.topStepper = 0;
        this.boardIndex = 0;
        this.starterOpponent = game.getStarterOpponent();
    }

    public int getBottomStepper() {
        return bottomStepper;
    }

    public int getTopStepper() {
        return topStepper;
    }

    public Opponent getStarterOpponent() {
        return starterOpponent;
    }

    public void increaseBottomStepper() {
        if(bottomStepper+1 <= game.getBottomOpponent().getMoveHistory().size()) {
            this.bottomStepper++;
            this.boardIndex++;
        }
    }

    public void decreaseBottomStepper() {
        if(bottomStepper-1 >= 0) {
            this.bottomStepper--;
            this.boardIndex--;
        }
    }

    public void increaseTopStepper() {
        if(topStepper+1 <= game.getTopOpponent().getMoveHistory().size()) {
            this.topStepper++;
            this.boardIndex++;
        }
    }

    public void decreaseTopStepper() {
        if(topStepper-1 >= 0) {
            this.topStepper--;
            this.boardIndex--;
        }
    }

    public boolean atTheEnd() {
        return (topStepper == game.getTopOpponent().getMoveHistory().size())
                && (bottomStepper == game.getBottomOpponent().getMoveHistory().size());
    }

    public boolean atTheStart() {
        return (topStepper == 0 && bottomStepper == 0);
    }

    public int getBoardIndex() {
        return boardIndex;
    }

}
