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
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Magyar Bálint
 */
public class gameChooserPane extends BorderPane {
    private GridPane newGameLayout;
    private ToggleGroup difficultyGroup;
    private ToggleGroup colorGroup;

    public gameChooserPane() {
        
        this.newGameLayout = setupGridPane();
        Button newGameBtn = new Button("Új játék");
        newGameBtn.setOnAction(e -> startNewGame());
        
        this.difficultyGroup = new ToggleGroup();
        RadioButton easy = new RadioButton("Könnyű");
        easy.setUserData("Easy");
        easy.setToggleGroup(difficultyGroup);
        easy.setSelected(true);
        RadioButton medium = new RadioButton("Közepes");
        medium.setUserData("Medium");
        medium.setToggleGroup(difficultyGroup);
        RadioButton hard = new RadioButton("Nehéz");
        hard.setUserData("Hard");
        hard.setToggleGroup(difficultyGroup);
        
        this.colorGroup = new ToggleGroup();
        RadioButton light = new RadioButton("Világos");
        light.setUserData("Light");
        light.setToggleGroup(colorGroup);
        light.setSelected(true);
        RadioButton dark = new RadioButton("Sötét");
        dark.setUserData("Dark");
        dark.setToggleGroup(colorGroup);
        
        newGameLayout.add(new Text("Nehézség:"), 0, 0);
        newGameLayout.add(easy, 0, 1);
        newGameLayout.add(medium, 0, 2);
        newGameLayout.add(hard, 0, 3);
        newGameLayout.add(new Text("Szín:"), 0, 4);
        newGameLayout.add(light, 0, 5);
        newGameLayout.add(dark, 1, 5);
        newGameLayout.add(newGameBtn, 0, 6);
        setCenter(newGameLayout);
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
    
    private void startNewGame() {
        String difficult = (String) difficultyGroup.getSelectedToggle().getUserData();
        String color = (String) colorGroup.getSelectedToggle().getUserData();
        GuiManager.startNewGame(difficult, color);
    }
    
    
    
}
