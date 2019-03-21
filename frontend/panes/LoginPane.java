/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.frontend.panes;

import checkersgame.frontend.GuiManager;
import checkersgame.frontend.components.CheckersMenuBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Magyar Bálint
 */
public class LoginPane extends BorderPane {
    private GridPane layout;
    
    public LoginPane() {
        this.layout = setupGridPane();
        
        Button btn = new Button("Belépés");
        btn.setOnAction(e -> login());
        Text userNameText = new Text("Felhasználónév");
        TextField userName = new TextField();
        Text passwordText = new Text("Jelszó");
        PasswordField password = new PasswordField();
        
        
        layout.add(userNameText, 0, 0);
        layout.add(userName, 1, 0);
        layout.add(passwordText, 0, 1);
        layout.add(password, 1, 1);
        layout.add(btn, 0, 2);
        setCenter(layout);
        setTop(new CheckersMenuBar());
    }
    
    private GridPane setupGridPane() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        return pane;
    }
    
    private void login() {
        GuiManager.Login();
    }
    
    
}
