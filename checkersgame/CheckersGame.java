/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame;

import checkersgame.frontend.GuiManager;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Magyar Bálint
 */
public class CheckersGame extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        GuiManager.init(primaryStage);
        GuiManager.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
