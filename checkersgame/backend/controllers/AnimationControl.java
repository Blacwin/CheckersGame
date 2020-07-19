package checkersgame.backend.controllers;

import checkersgame.backend.game.Game;
import checkersgame.backend.Model;
import checkersgame.backend.game.piece.Piece;
import checkersgame.backend.game.movements.Move;
import checkersgame.backend.game.opponents.Ai;
import checkersgame.backend.game.piece.PieceDirection;
import checkersgame.frontend.GuiManager;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.List;

public class AnimationControl {
    private Model model;
    private Game game;
    private Thread animationThread;
    private Demo demo;
    private boolean stop;
    private int speed;
    private long startTime;

    public AnimationControl(Model model, Game game) {
        this.model = model;
        this.game = game;
    }

    public AnimationControl(Model model) {
        this.model = model;
        this.speed = 50;
        stop();
    }

    public void setGame(Game game) {
        this.game = game;
        this.startTime = System.currentTimeMillis()-(game.getGameTime());
    }

    public void aiProcess(Ai currentAi) {
        if(this.animationThread != null) {
            this.animationThread.interrupt();
            this.animationThread = null;
//            System.gc();
        }
        if(!model.exit) {
            Task task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    animateMovements(currentAi);
                    return null;
                }

                @Override protected void succeeded() {
                    super.succeeded();
                    model.nextRound();
                }
            };
            this.animationThread = new Thread(task);
//            this.animationThread.setDaemon(true);
            this.animationThread.start();
        }
    }

    public void shutDown() {
        if(this.animationThread != null) {
            this.animationThread.interrupt();
        }
    }

    private void sleep(int val) {
        try {
            Thread.sleep(val);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        };
    }

    private void refreshUI(boolean makeSound) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                GuiManager.refreshGameBoard(game.getBoard().getBoard());
                model.refreshBoardByGameMode(game.getGameMode());
                if(makeSound) {
                    model.refreshUI();
                    GuiManager.makeStepSound();
                }
            }
        });
    }

    private void animateMovements(Ai ai) {
        try {
            List<Move> moves = ai.calculateStep(game.getBoard());
            ai.addMovesToHistoryList(moves);
            if (moves.size() > 0) {
                for (Move move : moves) {
                    sleep(500);
                    Piece piece = game.getBoard().removePieceByMove(move);
                    refreshUI(false);
                    sleep(200);
                    game.getBoard().movePieceByMoveAndPiece(move, piece);
                    refreshUI(true);
                }
            } else {
                model.isGameEnd();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void demoProcess(Ai currentAi) {
        if(this.animationThread != null) {
            this.animationThread.interrupt();
            this.animationThread = null;
            System.gc();
        }
        if(!model.exit && !stop) {
            Task task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    if(currentAi.getDirection() == PieceDirection.UP) {
                        demoAnimation(currentAi, demo.getBottomStepper());
                        demo.increaseBottomStepper();
                    }else {
                        demoAnimation(currentAi, demo.getTopStepper());
                        demo.increaseTopStepper();
                    }
                    return null;
                }

                @Override protected void succeeded() {
                    super.succeeded();
                    model.nextStateOnDemo();
                }
            };
            this.animationThread = new Thread(task);
            this.animationThread.setDaemon(true);
            this.animationThread.start();
        }
    }

    private void demoAnimation(Ai ai, int index) {
        try {
            List<Move> moves = ai.getMoveHistory().get(index);
            if (moves.size() > 0 && !stop) {
                for (Move move : moves) {
                    sleep(10*speed);
                    Piece piece = game.getBoard().removePieceByMove(move);
                    refreshUI(false);
                    sleep(2*speed);
                    game.getBoard().movePieceByMoveAndPiece(move, piece);
                    refreshUI(true);
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initDemo(int size) {
        this.demo = new Demo(game, size);
    }

    public boolean isStopped() {
        return stop;
    }

    public void stop() {
        this.stop = true;
    }

    public void setPlay() {
        this.stop = false;
    }

    public void increaseSpeed() {
        if(speed-40 >= 10) {
            speed -= 40;
        }
    }

    public void decreaseSpeed() {
        if(speed+40 <= 90) {
            speed += 40;
        }
    }

    public boolean demoIsEnding() {
        return demo.atTheEnd();
    }

    public Demo getDemo() {
        return demo;
    }

    public void stepForward(Ai currentAi) {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (currentAi.getDirection() == PieceDirection.UP) {
                    demoAnimation(currentAi, demo.getBottomStepper());
                    demo.increaseBottomStepper();
                } else {
                    demoAnimation(currentAi, demo.getTopStepper());
                    demo.increaseTopStepper();
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                stop = true;
                GuiManager.refreshDemoController(true, demo.atTheEnd(), demo.atTheStart());
            }
        };
        this.animationThread = new Thread(task);
        this.animationThread.setDaemon(true);
        this.animationThread.start();
    }

    public void backwardStep(Ai currentAi) {
        if(currentAi.getDirection() == PieceDirection.UP) {
            demo.decreaseBottomStepper();
        }else {
            demo.decreaseTopStepper();
        }
    }

    public void clockControl() {
        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long finish = System.currentTimeMillis();
                long elapsedTime = (finish - startTime);
                GuiManager.refreshElapsedTime( elapsedTime/1000 );
                game.setGameTime(elapsedTime);
            }
        };
        at.start();
    }
}
