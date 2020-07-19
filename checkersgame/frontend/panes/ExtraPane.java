package checkersgame.frontend.panes;

import checkersgame.backend.game.board.field.CheckersField;
import checkersgame.frontend.GuiManager;
import checkersgame.frontend.components.CheckersMenuBar;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtraPane extends BorderPane {
    private BoardPane boardPane;
    private Label titleLabel;
    private Label msgLabel;
    private VBox leftBox;
    private Label levelDescription;
    private Collection<Button> levelButtons;

    public ExtraPane(int boardSize) {
        this.setId("main");
        this.boardPane = new BoardPane(boardSize);
        this.getStyleClass().add("pane");

        titleLabel = new Label("Feladványok");
        setupLeftPanel();

        msgLabel = new Label("Világos szín következik.");

        BorderPane bottomBorder = new BorderPane();
        bottomBorder.getStyleClass().addAll("bottom-box");
        bottomBorder.setCenter(msgLabel);

        setCenter(boardPane);
        setLeft(leftBox);
        setBottom(bottomBorder);
        setTop(GuiManager.getMenuBar());
    }

    private void setupLeftPanel() {
        levelDescription = new Label("");

        leftBox = new VBox();
        leftBox.getStyleClass().addAll("box-ingame", "light-box");
        leftBox.setPrefWidth(300);
        BorderPane pane = new BorderPane();
        pane.setPrefHeight(700);
        VBox topBox = new VBox();
        topBox.getChildren().addAll(titleLabel, levelDescription);
        topBox.setAlignment(Pos.CENTER);
        pane.setTop(topBox);
        VBox centerBox = new VBox();
        levelButtons = generateLevelButtons();
        centerBox.getChildren().addAll(levelButtons);
        pane.setCenter(centerBox);
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
//        Button previousButton = new Button("Vissza");
//        previousButton.getStyleClass().addAll("main-button", "ingame-button");
//        previousButton.setOnAction(event -> backToPreviousLevel());
        Button exitButton = new Button("Kilép");
        exitButton.setOnAction(event -> exit());
        exitButton.getStyleClass().addAll("main-button", "ingame-button");
        bottomBox.getChildren().addAll(exitButton);
        pane.setBottom(bottomBox);
        leftBox.getChildren().addAll(pane);
    }

    private List<Button> generateLevelButtons() {
        List<Button> buttonList = new ArrayList<>();
        String[] buttonsName = {"Szint 1", "Szint 2", "Szint 3", "Szint 4", "Szint 5", "Szint 6"};
        for(String name : buttonsName) {
            Button button = new Button(name);
            button.getStyleClass().addAll("main-button", "ingame-button");
            button.setOnAction(event -> loadLevel(event));
            buttonList.add(button);
        }
        return buttonList;
    }

    private void loadLevel(ActionEvent event) {
        String text = ((Button) event.getSource()).getText();
        int level = Integer.parseInt(text.split(" ")[1]);
        GuiManager.loadExtraLevel(level);
    }

    private void backToPreviousLevel() {
        GuiManager.backToPreviousTutorLevel();
    }

    public void refreshSidePanel(String desc, String title) {
        titleLabel.setText(title);
        levelDescription.setText(desc);
    }

    public void initBoard(CheckersField[][] modelfield) {
        boardPane.initBoard(modelfield);
    }

    public void refreshBoard(CheckersField[][] modelfield) {
        boardPane.refreshBoard(modelfield);
    }

    public void setMenuBar(CheckersMenuBar menuBar) {
        setTop(menuBar);
    }

    private void exit() {
        GuiManager.backToMain();
    }
}
