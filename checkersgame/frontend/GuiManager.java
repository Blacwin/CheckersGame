/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.frontend;

import checkersgame.backend.Model;
import checkersgame.frontend.components.CheckersMenuBar;
import checkersgame.frontend.panes.BoardPane;
import checkersgame.frontend.panes.LoginPane;
import checkersgame.frontend.panes.gameChooserPane;
import java.io.File;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author Magyar Bálint
 */
public class GuiManager {

    private static Stage window;
    private static Scene primaryScene;
    private static Pane loginPane;
    private static Pane gameChooserPane;
    private static BoardPane gameBoardPane;
    private static Model model;
    private static Media sound;

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    public static void init(Stage window) {
        GuiManager.window = window;
        String musicFile = "src/checkersgame/frontend/assets/chess-step-effect.wav";
        sound = new Media(new File(musicFile).toURI().toString());
    }

    public static void start() {
        model = new Model();
        setPanes();
        primaryScene = new Scene(loginPane, SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryScene.getStylesheets().add("checkersgame/frontend/assets/stylesheet.css");

        window.setTitle("Dáma játék");
        window.setScene(primaryScene);
        window.show();
    }

    private static void setPanes() {
        loginPane = new LoginPane();
        gameChooserPane = new gameChooserPane();
        gameBoardPane = new BoardPane();
    }

    public static void Login() {
        primaryScene.setRoot(gameChooserPane);
    }

    public static void startNewGame(String difficult, String color) {
        primaryScene.setRoot(gameBoardPane);
        model.startNewGame(difficult, color);
    }

    public static void initGameBoard() {
        gameBoardPane.initBoard(model.getBoard());
    }

    public static void refreshGameBoard() {
        gameBoardPane.refereshBoard(model.getBoard());
    }

    public static void onFieldClick(int x, int y) {
        if(model.onFieldClick(x, y) ){
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }
    }

    public static void refreshByAiStep() {
//        try {
//            Thread.sleep(1000);
//        }catch(InterruptedException ex) {}
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                gameBoardPane.refereshBoard (model.getBoard());
            }
        });
        new Thread(sleeper).start();
    }
}
