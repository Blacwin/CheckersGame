package test;

import checkersgame.backend.Model;
import checkersgame.frontend.GuiManager;
import checkersgame.frontend.panes.BoardPane2;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameBoardTest extends Application {

    static final int BOARD_SIZE = 8;
    static final int WIDTH = 950;
    static final int HEIGHT = 660;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GuiManager.init(primaryStage, new Model());
        BoardPane2 boardPane2 = new BoardPane2(BOARD_SIZE);
        primaryStage.setTitle("GameBoard Test Application");
        primaryStage.setScene(new Scene(boardPane2, WIDTH, HEIGHT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
