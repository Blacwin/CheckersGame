package checkersgame.frontend.panes;

import checkersgame.backend.game.board.field.CheckersField;
import checkersgame.frontend.GuiManager;
import checkersgame.frontend.components.CheckersMenuBar;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GamePane extends BorderPane {

    private Image darkpiece;
    private Image lightpiece;
    private Image aiImage;
    private Image playerImage;
    private Image playerThinkingImage;
    private Image aiThinkingImage;
    private ImageView imgvLeft, imgvRight;
    private BoardPane boardPane;
    private VBox leftBox, rightBox;
    private Label leftNameText, rightNameText;
    private Label msgLabel, timeLabel;
    private List<Button> leftPiecesButtons, rightPiecesButtons;
    private Button undoButtonLeft, undoButtonRight;
    private Button playButton, pauseButton;
    private Button rewindButton, forwardButton;
    private Button speedUpButton, speedDownButton;

    public GamePane(int boardSize) {
        this.setId("main");
        this.boardPane = new BoardPane(boardSize);
        BoardPane2 boardPane2 = new BoardPane2(boardSize);
        this.getStyleClass().add("pane");

//        aiImage = GuiManager.ImgLoader.loadIcon("cpu-mini");
        aiImage = GuiManager.fileLoader.loadIcon("cpu-mini");
        playerImage = GuiManager.fileLoader.loadIcon("player-mini2");
        playerThinkingImage = GuiManager.fileLoader.loadIcon("player-mini2-thinking");
        aiThinkingImage = GuiManager.fileLoader.loadIcon("cpu-mini-thinking");
        darkpiece = GuiManager.fileLoader.loadIcon("dark-wood-piece-white-edge");
        lightpiece = GuiManager.fileLoader.loadIcon("light-wood-piece");

        timeLabel = new Label("Idő: 00:00:00");
        setupLeftPanel();
        setupRightPanel();

        msgLabel = new Label("Várakozás: Sötét szín kezd.");

        BorderPane bottomBorder = new BorderPane();
        bottomBorder.getStyleClass().addAll("bottom-box");
        bottomBorder.setLeft(timeLabel);
        bottomBorder.setCenter(msgLabel);
        bottomBorder.setRight(generateDemoController());
        hideDemoController();
        enablePlayButton();

        setCenter(boardPane);
        setLeft(leftBox);
        setRight(rightBox);
        setBottom(bottomBorder);
        setTop(GuiManager.getMenuBar());
    }
    
    private GridPane setupGridPane() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(0);
        pane.setVgap(0);
        pane.getStyleClass().add("board");
        return pane;
    }

    private void setupLeftPanel() {
        leftNameText = new Label("Játékos");
        imgvLeft = new ImageView(aiImage);
        HBox leftPiecesBox1 = new HBox();
        leftPiecesBox1.setAlignment(Pos.CENTER);
        HBox leftPiecesBox2 = new HBox();
        leftPiecesBox2.setAlignment(Pos.CENTER);
        leftPiecesButtons = new ArrayList<>();
        fillListWithButtons(leftPiecesButtons, 12);
        for(int i=0; i<12; i++) {
            if(i<6) {
                leftPiecesBox1.getChildren().addAll(leftPiecesButtons.get(i));
            }
            if(i>=6) {
                leftPiecesBox2.getChildren().addAll(leftPiecesButtons.get(i));
            }
        }

        undoButtonLeft = new Button("Visszalépés");
        undoButtonLeft.setOnAction(e -> handleUndoButtonLeft());
        undoButtonLeft.getStyleClass().addAll("main-button", "ingame-button");
        Button statisticButton = new Button("Lépés történet");
        statisticButton.setOnAction(e -> handleStatButtonLeft());
        statisticButton.getStyleClass().addAll("main-button", "ingame-button");

        leftBox = new VBox();
        leftBox.getStyleClass().addAll("box-ingame", "light-box");
        BorderPane pane = new BorderPane();
        pane.setPrefHeight(700);
        VBox topBox = new VBox();
        topBox.getChildren().addAll(imgvLeft, leftNameText);
        topBox.setAlignment(Pos.CENTER);
        pane.setTop(topBox);
        VBox centerBox = new VBox();
        centerBox.getChildren().addAll(leftPiecesBox1, leftPiecesBox2);
        pane.setCenter(centerBox);
        VBox bottomBox = new VBox();
        bottomBox.getChildren().addAll(undoButtonLeft, statisticButton);
        bottomBox.setAlignment(Pos.CENTER);
        pane.setBottom(bottomBox);
//        leftBox.getChildren().addAll(imgvLeft, leftNameText, leftPiecesBox1, leftPiecesBox2);
        leftBox.getChildren().addAll(pane);
    }

    private void setupRightPanel() {
        rightNameText = new Label("Számítógép");
        imgvRight = new ImageView(playerImage);
        HBox rightPiecesBox1 = new HBox();
        rightPiecesBox1.setAlignment(Pos.CENTER);
        HBox rightPiecesBox2 = new HBox();
        rightPiecesBox2.setAlignment(Pos.CENTER);
        rightPiecesButtons = new ArrayList<>();
        fillListWithButtons(rightPiecesButtons, 12);
        for(int i=0; i<12; i++) {
            if(i<6) {
                rightPiecesBox1.getChildren().addAll(rightPiecesButtons.get(i));
            }
            if(i>=6) {
                rightPiecesBox2.getChildren().addAll(rightPiecesButtons.get(i));
            }
        }

        undoButtonRight = new Button("Visszalépés");
        undoButtonRight.setOnAction(e -> handleUndoButtonRight());
        undoButtonRight.getStyleClass().addAll("main-button", "ingame-button");
        Button statisticButton = new Button("Lépés történet");
        statisticButton.setOnAction(e -> handleStatButtonRight());
        statisticButton.getStyleClass().addAll("main-button", "ingame-button");

        rightBox = new VBox();
        rightBox.getStyleClass().addAll("box-ingame", "light-box");
        BorderPane pane = new BorderPane();
        pane.setPrefHeight(700);
        VBox topBox = new VBox();
        topBox.getChildren().addAll(imgvRight, rightNameText);
        topBox.setAlignment(Pos.CENTER);
        pane.setTop(topBox);
        VBox centerBox = new VBox();
        centerBox.getChildren().addAll(rightPiecesBox1, rightPiecesBox2);
        pane.setCenter(centerBox);
        VBox bottomBox = new VBox();
        bottomBox.getChildren().addAll(undoButtonRight, statisticButton);
        bottomBox.setAlignment(Pos.CENTER);
        pane.setBottom(bottomBox);
//        rightBox.getChildren().addAll(imgvRight, rightNameText, rightPiecesBox1, rightPiecesBox2);
        rightBox.getChildren().add(pane);
    }

    private HBox generateDemoController() {
        HBox hBox = new HBox();
        rewindButton = new Button();
        rewindButton.setGraphic(new ImageView(GuiManager.fileLoader.loadIcon("rewind")) );
        rewindButton.setOnAction(event -> backStep());
        forwardButton = new Button();
        forwardButton.setGraphic(new ImageView(GuiManager.fileLoader.loadIcon("forward")) );
        forwardButton.setOnAction(event -> nextStep());
        playButton = new Button();
        playButton.setGraphic(new ImageView(GuiManager.fileLoader.loadIcon("play")) );
        playButton.setOnAction(event -> playDemo());
        pauseButton = new Button();
        pauseButton.setGraphic(new ImageView(GuiManager.fileLoader.loadIcon("pause")) );
        pauseButton.setOnAction(event -> stopDemo());
        speedUpButton = new Button();
        speedUpButton.setGraphic(new ImageView(GuiManager.fileLoader.loadIcon("speedup")) );
        speedUpButton.setOnAction(event -> speedUp());
        speedDownButton = new Button();
        speedDownButton.setGraphic(new ImageView(GuiManager.fileLoader.loadIcon("speeddown")) );
        speedDownButton.setOnAction(event -> speedDown());

        hBox.getChildren().addAll(rewindButton, forwardButton, playButton, pauseButton, speedUpButton, speedDownButton);
        return hBox;
    }

    private void nextStep() {
        GuiManager.forwardStepOnDemo();
    }

    private void backStep() {
        GuiManager.backwardStepOnDemo();
    }

    private void speedUp() {
        GuiManager.speedUp();
    }

    private void speedDown() {
        GuiManager.speedDown();
    }

    private void playDemo() {
        GuiManager.playDemo();
    }

    private void stopDemo() {
        GuiManager.stopDemo();
    }

    public void showDemoController() {
        rewindButton.setVisible(true);
        forwardButton.setVisible(true);
        playButton.setVisible(true);
        pauseButton.setVisible(true);
        speedUpButton.setVisible(true);
        speedDownButton.setVisible(true);
    }

    public void hideDemoController() {
        rewindButton.setVisible(false);
        forwardButton.setVisible(false);
        playButton.setVisible(false);
        pauseButton.setVisible(false);
        speedUpButton.setVisible(false);
        speedDownButton.setVisible(false);
    }

    private void handleStatButtonLeft() {
        GuiManager.showInfoPanel("LEFT");
    }

    private void handleStatButtonRight() {
        GuiManager.showInfoPanel("RIGHT");
    }

    private void handleUndoButtonLeft() {
        GuiManager.undoLastStep("LEFT");
    }

    private void handleUndoButtonRight() {
        GuiManager.undoLastStep("RIGHT");
    }

    public void initOpponentView(Map<String, String> dataMap) {
        String bottomOppType = dataMap.get("bottomType");
        String topOppType = dataMap.get("topType");
        String bottomColor = dataMap.get("bottomColor");
        String topColor = dataMap.get("topColor");
        String bottomName = dataMap.get("bottomName");
        String topName = dataMap.get("topName");

        if(bottomOppType.equals("AI")){
            imgvLeft.setImage(aiImage);
        } else if(bottomOppType.equals("PLAYER")) {
            imgvLeft.setImage(playerImage);
        }
        if(topOppType.equals("AI")) {
            imgvRight.setImage(aiImage);
        } else if (topOppType.equals("PLAYER")) {
            imgvRight.setImage(playerImage);
        }
        leftBox.getStyleClass().removeAll("light-box", "dark-box");
        leftBox.getStyleClass().add((bottomColor.equals("LIGHT"))? "light-box" : "dark-box");
        rightBox.getStyleClass().removeAll("light-box", "dark-box");
        rightBox.getStyleClass().add((topColor.equals("LIGHT"))? "light-box" : "dark-box");

        leftNameText.setText(bottomName);
        rightNameText.setText(topName);
    }

    public void setActiveOpponentBox(String side, String currentColor, boolean withdraw) {
        if(side.equals("LEFT")) {
            changeImg(imgvLeft, imgvRight);
            rightBox.getStyleClass().removeAll("active-box");
            leftBox.getStyleClass().add("active-box");
            if(imgvLeft.getImage().equals(playerThinkingImage)) {
                if(withdraw) undoButtonLeft.setVisible(true);
                undoButtonRight.setVisible(false);
            } else {
                undoButtonLeft.setVisible(false);
                undoButtonRight.setVisible(false);
            }
        } else if(side.equals("RIGHT")) {
            changeImg(imgvRight, imgvLeft);
            leftBox.getStyleClass().removeAll("active-box");
            rightBox.getStyleClass().add("active-box");
            if(imgvRight.getImage().equals(playerThinkingImage)) {
                undoButtonLeft.setVisible(false);
                if(withdraw) undoButtonRight.setVisible(true);
            } else {
                undoButtonLeft.setVisible(false);
                undoButtonRight.setVisible(false);
            }
        }
        if(currentColor.equals("LIGHT")) {
            msgLabel.setText("Világos szín következik.");
        } else {
            msgLabel.setText("Sötét szín következik.");
        }
    }

    private void changeImg(ImageView imgv1, ImageView imgv2) {
        if(imgv1.getImage().equals(playerImage)) {
            imgv1.setImage(playerThinkingImage);
            if(imgv2.getImage().equals(playerThinkingImage)) {
                imgv2.setImage(playerImage);
            }else if(imgv2.getImage().equals(aiThinkingImage)) {
                imgv2.setImage(aiImage);
            }
        } else if(imgv1.getImage().equals(playerThinkingImage)) {
            imgv1.setImage(playerImage);
            if(imgv2.getImage().equals(playerImage)) {
                imgv2.setImage(playerThinkingImage);
            }else if(imgv2.getImage().equals(aiImage)) {
                imgv2.setImage(aiThinkingImage);
            }
        }
        if(imgv1.getImage().equals(aiImage)) {
            imgv1.setImage(aiThinkingImage);
            if(imgv2.getImage().equals(playerThinkingImage)) {
                imgv2.setImage(playerImage);
            }else if(imgv2.getImage().equals(aiThinkingImage)) {
                imgv2.setImage(aiImage);
            }
        } else if(imgv1.getImage().equals(aiThinkingImage)) {
            imgv1.setImage(aiImage);
            if(imgv2.getImage().equals(playerImage)) {
                imgv2.setImage(playerThinkingImage);
            }else if(imgv2.getImage().equals(aiImage)) {
                imgv2.setImage(aiThinkingImage);
            }
        }
    }

    public void initBoard(CheckersField[][] modelfield) {
        boardPane.initBoard(modelfield);
    }
    
    public void refreshBoard(CheckersField[][] modelfield) {
        boardPane.refreshBoard(modelfield);
    }

    private void fillListWithButtons(final List<Button> list, int buttonsNumber) {
        for(int i=0; i<buttonsNumber; i++) {
            Button btn = new Button();
            btn.getStyleClass().addAll("side-piece-buttons");
            list.add(btn);
        }
    }

    public void refreshPanels(int leftNum, int leftBackCounter, String leftColor, int rightNum, int rightBackCounter, String rightColor, boolean withdraw) {
        for(int i=0; i<12; i++) {
            leftPiecesButtons.get(i).setGraphic(null);
            rightPiecesButtons.get(i).setGraphic(null);
        }
        for(int i=0; i<12-leftNum; i++) {
            if(leftColor.equals("LIGHT")) {
                leftPiecesButtons.get(i).setGraphic(new ImageView(lightpiece));
            }else {
                leftPiecesButtons.get(i).setGraphic(new ImageView(darkpiece));
            }
        }
        for(int i=0; i<12-rightNum; i++) {
            if(rightColor.equals("LIGHT")) {
                rightPiecesButtons.get(i).setGraphic(new ImageView(lightpiece));
            }else {
                rightPiecesButtons.get(i).setGraphic(new ImageView(darkpiece));
            }
        }
        if(withdraw) {
            undoButtonLeft.setText("Visszalépés (" + leftBackCounter + ")");
            undoButtonRight.setText("Visszalépés (" + rightBackCounter + ")");
            if (leftBackCounter > 0) {
                undoButtonLeft.setDisable(false);
            } else {
                undoButtonLeft.setDisable(true);
            }
            if (rightBackCounter > 0) {
                undoButtonRight.setDisable(false);
            } else {
                undoButtonRight.setDisable(true);
            }
        }else {
            undoButtonLeft.setVisible(false);
            undoButtonRight.setVisible(false);
        }
    }

    public void setMenuBar(CheckersMenuBar menuBar) {
        setTop(menuBar);
    }

    public void disablePlayButton() {
        playButton.setDisable(true);
        pauseButton.setDisable(false);
        rewindButton.setDisable(true);
        forwardButton.setDisable(true);
    }

    public void enablePlayButton() {
        playButton.setDisable(false);
        pauseButton.setDisable(true);
        rewindButton.setDisable(false);
        forwardButton.setDisable(false);
    }

    public void refreshDemoController(boolean isStopped, boolean atTheEnd, boolean atTheStart) {
        if(isStopped) {
            enablePlayButton();
        }
        if(atTheEnd) {
            forwardButton.setDisable(true);
            playButton.setDisable(true);
        }
        if(atTheStart) {
            rewindButton.setDisable(true);
        }
    }

    public void setWithdrawButtons(boolean withdraw) {
        if(withdraw) {
            undoButtonLeft.setVisible(true);
            undoButtonRight.setVisible(true);
        }else {
            undoButtonLeft.setVisible(false);
            undoButtonRight.setVisible(false);
        }
    }

    public void setTimeLabel(long time) {
        long min = (time/60);
        long sec = (time-(min*60));
        String minString = (min < 10) ? ("0" + min) : Long.toString(min);
        String secString = (sec < 10) ? ("0" + sec) : Long.toString(sec);
        timeLabel.setText("Idő: " + minString + " : " + secString);
    }
}
