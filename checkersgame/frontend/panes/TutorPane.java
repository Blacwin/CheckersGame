package checkersgame.frontend.panes;

import checkersgame.backend.game.board.field.CheckersField;
import checkersgame.frontend.GuiManager;
import checkersgame.frontend.components.CheckersMenuBar;
import checkersgame.frontend.components.Field;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class TutorPane extends BorderPane {

    private Field fields[][];
    private Image darkpiece;
    private Image lightpiece;
    private Image darkpieceDame;
    private Image lightpieceDame;
    private ImageView imgv1, imgv2;
    private BoardPane boardPane;
    private boolean selected;
    private VBox leftBox;
    private Label leftTopDescription, leftBottomDescription;
    private Label msgLabel;
    private Label titleLabel;

    public TutorPane(int boardSize) {
        this.setId("main");
        this.boardPane = new BoardPane(boardSize);
        this.getStyleClass().add("pane");

        titleLabel = new Label("Gyakorló");
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
        imgv1 = new ImageView();
        imgv2 = new ImageView();
        leftTopDescription = new Label("");
        leftBottomDescription = new Label("");

        leftBox = new VBox();
        leftBox.getStyleClass().addAll("box-ingame", "light-box");
        leftBox.setPrefWidth(300);
        BorderPane pane = new BorderPane();
        pane.setPrefHeight(700);
        VBox topBox = new VBox();
        topBox.getChildren().addAll(titleLabel, leftTopDescription, imgv1, leftBottomDescription, imgv2);
        topBox.setAlignment(Pos.CENTER);
        pane.setTop(topBox);
        VBox centerBox = new VBox();
        pane.setCenter(centerBox);
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        Button previousButton = new Button("Vissza");
        previousButton.getStyleClass().addAll("main-button", "ingame-button");
        previousButton.setOnAction(event -> backToPreviousLevel());
        Button exitButton = new Button("Kilép");
        exitButton.setOnAction(event -> exit());
        exitButton.getStyleClass().addAll("main-button", "ingame-button");
        bottomBox.getChildren().addAll(previousButton, exitButton);
        pane.setBottom(bottomBox);
        leftBox.getChildren().addAll(pane);
    }

    private void backToPreviousLevel() {
        GuiManager.backToPreviousTutorLevel();
    }

    public void refreshSidePanel(List<String> desc, int level, String title) {
        titleLabel.setText(title);
        leftTopDescription.setText(desc.get(0));
        leftBottomDescription.setText(desc.get(1));
        try {
            imgv1.setImage(GuiManager.fileLoader.loadTutorialImage("level" + level + "-1"));
            imgv2.setImage(GuiManager.fileLoader.loadTutorialImage("level" + level + "-2"));
        }catch (IllegalArgumentException ex) {
            imgv1.setImage(GuiManager.fileLoader.loadTutorialImage("default-table"));
            imgv2.setImage(GuiManager.fileLoader.loadTutorialImage("default-table"));
        }
    }

    public void initBoard(CheckersField[][] modelfield) {
        boardPane.initBoard(modelfield);
    }
    
    public void refereshBoard(CheckersField[][] modelfield) {
        boardPane.refreshBoard(modelfield);
    }

    public void setMenuBar(CheckersMenuBar menuBar) {
        setTop(menuBar);
    }

    private void exit() {
        GuiManager.backToMain();
    }
}
