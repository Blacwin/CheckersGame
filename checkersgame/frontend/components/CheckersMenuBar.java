/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.frontend.components;

import checkersgame.frontend.GuiManager;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Magyar Bálint
 */
public class CheckersMenuBar extends MenuBar{
    
    public CheckersMenuBar() {
        Menu gameMenu = new Menu("Játék");
        this.getMenus().add(gameMenu);

        MenuItem menuItemQuit = new MenuItem("Kilépés");
        menuItemQuit.setOnAction(e -> onQuit());
        //menuItemQuit.setGraphic( new ImageView( new Image("assets/icons/quit.png", 16, 16, true, true) ) );
        menuItemQuit.setAccelerator( new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN) );
        gameMenu.getItems().add(menuItemQuit);

        Menu menuHelp = new Menu("Segítség");
        this.getMenus().add(menuHelp);

        MenuItem menuItemRules = new MenuItem("Játékszabályok");
        
        menuItemRules.setAccelerator( new KeyCodeCombination(KeyCode.F1) );
        menuItemRules.setOnAction(e -> onDisplayRules());
        menuHelp.getItems().add(menuItemRules);
    }
    
    private MenuBar generateMenuBar()
    {
        MenuBar menuBar = new MenuBar();

        Menu gameMenu = new Menu("Game");
        menuBar.getMenus().add(gameMenu);

        MenuItem menuItemQuit = new MenuItem("Quit");
        menuItemQuit.setOnAction(e -> onQuit());
        //menuItemQuit.setGraphic( new ImageView( new Image("assets/icons/quit.png", 16, 16, true, true) ) );
        menuItemQuit.setAccelerator( new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN) );
        gameMenu.getItems().add(menuItemQuit);

        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().add(menuHelp);

        MenuItem menuItemAbout = new MenuItem("About");
        //menuItemAbout.setGraphic( new ImageView( new Image("assets/icons/about.png", 16, 16, true, true) ) );
        // Note: Accelerator F1 does not work if TextField is
        //       in focus. This is a known issue in JavaFX.
        //       https://bugs.openjdk.java.net/browse/JDK-8148857
        menuItemAbout.setAccelerator( new KeyCodeCombination(KeyCode.F1) );
        menuItemAbout.setOnAction(e -> onDisplayRules());
        menuHelp.getItems().add(menuItemAbout);

        return menuBar;
    }
    
    private void onQuit() {
        GuiManager.Login();
    }

    private void onDisplayRules() {
        BorderPane root;
        try {
            //root = FXMLLoader.load(getClass().getClassLoader().getResource("path/to/other/view.fxml"), resources);
            root = new BorderPane();
            Text rulesText = new Text("Játékszabályok szövege");
            root.setCenter(rulesText);
            Stage stage = new Stage();
            stage.setTitle("Játékszabályok");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
