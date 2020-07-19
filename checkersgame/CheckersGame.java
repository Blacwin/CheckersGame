package checkersgame;

import checkersgame.backend.Model;
import checkersgame.frontend.GuiManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CheckersGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.DECORATED);
//        primaryStage.setFullScreen(true);

        Model model = new Model();
        GuiManager.init(primaryStage, model);
        GuiManager.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
