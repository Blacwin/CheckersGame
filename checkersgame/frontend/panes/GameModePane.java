package checkersgame.frontend.panes;

import checkersgame.frontend.GuiManager;
import checkersgame.frontend.components.CheckersMenuBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameModePane extends BorderPane {
    private final GridPane layout;
    private ChoiceBox leftDiff;
    private ChoiceBox rightDiff;
    private List<String> difficulties;
    private FileChooser fileChooser;
    private Button changeColorButton;
    private VBox leftBox;
    private VBox rightBox;
    private Button leftOppButton;
    private Button rightOppButton;
    private File file;
    private String leftOpp, rightOpp;
    private String leftOppColor, rightOppColor;
    private Image aiImage, playerImage;
    private TextField leftOppName, rightOppName;
    private String leftName, rightName;
    private Button fileButton;
    private CheckBox forceAttackCheckBox;
    private CheckBox withdrawCheckBox;

    public GameModePane() {
        this.setId("main");
        this.layout = setupGridPane();

        difficulties = new ArrayList<>();
        difficulties.add("Kezdő");
        difficulties.add("Nagyon könnyű");
        difficulties.add("Könnyű");
        difficulties.add("Normál");
        difficulties.add("Haladó");
        difficulties.add("Nehéz");
        difficulties.add("Mester");

        leftName = "NévtelenJátékos";
        rightName = "CPU";

        aiImage = GuiManager.fileLoader.loadIcon("cpu2");
        playerImage = GuiManager.fileLoader.loadIcon("player2");

        leftOppName = new TextField("NévtelenJátékos");
        leftOppName.getStyleClass().addAll("textfield-name","textfield-name-light");
        leftOppName.setDisable(false);
        rightOppName = new TextField("CPU");
        rightOppName.getStyleClass().addAll("textfield-name", "textfield-name-dark");
        rightOppName.setDisable(true);

        leftOppButton = new Button();
        leftOppButton.setGraphic(new ImageView(playerImage));
        leftOppButton.getStyleClass().add("image-button");
        leftOppButton.setOnAction(e -> changeLeftOpponent());
        leftOpp = "PLAYER";
        leftDiff = new ChoiceBox();
        leftDiff.getItems().addAll(difficulties);
        leftDiff.getSelectionModel().selectFirst();
        leftDiff.setDisable(true);

        rightOppButton = new Button();
        rightOppButton.setGraphic(new ImageView(aiImage));
        rightOppButton.getStyleClass().add("image-button");
        rightOppButton.setOnAction((e -> changeRightOpponent()));
        rightOpp = "AI";
        rightDiff = new ChoiceBox();
        rightDiff.getItems().addAll(difficulties);
        rightDiff.getSelectionModel().selectFirst();

        leftBox = new VBox();
        leftBox.getStyleClass().addAll("box", "light-box");
        leftBox.getChildren().addAll(leftOppName, leftOppButton, leftDiff);
        leftOppColor = "LIGHT";

        rightBox = new VBox();
        rightBox.getStyleClass().addAll("box", "dark-box");
        rightBox.getChildren().addAll(rightOppName, rightOppButton, rightDiff);
        rightOppColor = "DARK";

        changeColorButton = new Button("< Szín csere >");
        changeColorButton.getStyleClass().addAll("main-button", "ingame-button");
        changeColorButton.setOnAction(e -> changeColor());
        forceAttackCheckBox = new CheckBox("Ütés kényszer");
        forceAttackCheckBox.getStyleClass().addAll("ingame-button");
        forceAttackCheckBox.setSelected(true);
        withdrawCheckBox = new CheckBox("Visszalépés");
        withdrawCheckBox.getStyleClass().addAll("ingame-button");
        withdrawCheckBox.setSelected(true);

        VBox centerBox = new VBox();
        centerBox.getStyleClass().add("box-center");
        centerBox.getChildren().addAll(new Label("VS"), changeColorButton, forceAttackCheckBox, withdrawCheckBox);

        fileChooser = GuiManager.fileLoader.getFileChooserForSaveFolder("Játék betöltése");
        Button loadButton = getLoadButton();
        fileButton = new Button();
        fileButton.getStyleClass().addAll("main-button", "delete-file-button");
        fileButton.setOnAction(e -> deleteFile());
        fileButton.setVisible(false);
        fileButton.setTooltip(new Tooltip("A betöltött fájl neve. Kattints a visszavonáshoz!"));

        Button startButton = new Button("Indítás");
        startButton.setOnAction(e -> start());
        startButton.getStyleClass().add("main-button");

        Button backButton = new Button("Vissza");
        backButton.setOnAction(e -> back());
        backButton.getStyleClass().add("main-button");

        layout.add(leftBox, 0, 0);
        layout.add(rightBox, 2,0);
        layout.add(centerBox, 1, 0);
        layout.add(loadButton, 1, 2);
        layout.add(fileButton, 2, 2);
        layout.add(startButton, 1,1);
        layout.add(backButton, 1, 3);
        setCenter(layout);
        setRight(null);
        setLeft(null);
        setBottom(null);

    }

    private void changeRightOpponent() {
        if(rightOpp.equals("AI")) {
            rightOppButton.setGraphic(new ImageView(playerImage));
            rightOpp = "PLAYER";
            rightDiff.setDisable(true);
            rightOppName.setDisable(false);
            rightOppName.setText("NévtelenJátékos");
        } else if(rightOpp.equals("PLAYER")) {
            rightOppButton.setGraphic(new ImageView(aiImage));
            rightOpp = "AI";
            rightDiff.setDisable(false);
            rightOppName.setDisable(true);
            rightOppName.setText("CPU");
        }
    }

    private void changeLeftOpponent() {
        if(leftOpp.equals("AI")) {
            leftOppButton.setGraphic(new ImageView(playerImage));
            leftOpp = "PLAYER";
            leftDiff.setDisable(true);
            leftOppName.setDisable(false);
            leftOppName.setText("NévtelenJátékos");
        } else if(leftOpp.equals("PLAYER")) {
            leftOppButton.setGraphic(new ImageView(aiImage));
            leftOpp = "AI";
            leftDiff.setDisable(false);
            leftOppName.setDisable(true);
            leftOppName.setText("CPU");
        }
    }

    private void changeColor() {
        if(leftOppColor.equals("LIGHT")) {
            leftBox.getStyleClass().removeAll("light-box");
            leftOppName.getStyleClass().removeAll("textfield-name-light");
            leftBox.getStyleClass().add("dark-box");
            leftOppName.getStyleClass().add("textfield-name-dark");
            leftOppColor = "DARK";

            rightBox.getStyleClass().removeAll("dark-box");
            rightOppName.getStyleClass().removeAll("textfield-name-dark");
            rightBox.getStyleClass().add("light-box");
            rightOppName.getStyleClass().add("textfield-name-light");
            rightOppColor = "LIGHT";
        } else {
            leftBox.getStyleClass().removeAll("dark-box");
            leftOppName.getStyleClass().removeAll("textfield-name-dark");
            leftBox.getStyleClass().add("light-box");
            leftOppName.getStyleClass().add("textfield-name-light");
            leftOppColor = "LIGHT";

            rightBox.getStyleClass().removeAll("light-box");
            rightOppName.getStyleClass().removeAll("textfield-name-light");
            rightBox.getStyleClass().add("dark-box");
            rightOppName.getStyleClass().add("textfield-name-dark");
            rightOppColor = "DARK";
        }

    }

    private void back() {
        GuiManager.backToMain();
    }

    private void start() {
        String leftValue = (String) leftDiff.getValue();
        String rightValue =(String) rightDiff.getValue();
        int cpu1Difficult = getValueFromString(leftValue);
        int cpu2Difficult = getValueFromString(rightValue);
        String leftName = leftOppName.getText();
        String rightName = rightOppName.getText();
        boolean forceAttack = forceAttackCheckBox.isSelected();
        boolean withdraw = withdrawCheckBox.isSelected();
        if(leftOpp.equals("AI") && rightOpp.equals("AI")) {
            GuiManager.startNewGame(leftOppColor, rightOppColor, cpu1Difficult, cpu2Difficult, forceAttack, file);
        } else if(leftOpp.equals("PLAYER") && rightOpp.equals("PLAYER")) {
            GuiManager.startNewGame(leftOppColor, rightOppColor, leftName, rightName, forceAttack, withdraw, file);
        } else if(!leftOpp.equals(rightOpp)) {
            if(leftOpp.equals("AI")) {
                GuiManager.startNewGame(leftOppColor, rightOppColor, "DOWN", cpu1Difficult, rightName, forceAttack, withdraw, file);
            }else {
                GuiManager.startNewGame(leftOppColor, rightOppColor, "UP", cpu2Difficult, leftName, forceAttack, withdraw, file);
            }
        }
    }

    private int getValueFromString(String str) {
        if(str.equals(difficulties.get(0)) ) {
            return 1;
        }
        if(str.equals(difficulties.get(1)) ) {
            return 2;
        }
        if(str.equals(difficulties.get(2)) ) {
            return 3;
        }
        if(str.equals(difficulties.get(3)) ) {
            return 4;
        }
        if(str.equals(difficulties.get(4)) ) {
            return 5;
        }
        if(str.equals(difficulties.get(5)) ) {
            return 6;
        }
        if(str.equals(difficulties.get(6)) ) {
            return 7;
        }
        return 0;
    }

    private Button getLoadButton() {
        Button button = new Button("Betöltés");
        button.getStyleClass().add("main-button");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                file = fileChooser.showOpenDialog(GuiManager.window);
                if(file != null) {
                    fileButton.setVisible(true);
                    fileButton.setText("\""+ file.getName().substring(0, file.getName().lastIndexOf(".")) + "\"");
                    GuiManager.getDataFromFile(file);
                }
            }
        });
        return button;
    }

    private void deleteFile() {
        file = null;
        changeColorButton.setDisable(false);
        fileButton.setText("");
        fileButton.setVisible(false);
    }

    public void updateUI(Map<String, String> dataMap) {
        if(dataMap != null) {
            changeColorButton.setDisable(true);
            if (dataMap.get("bottomColor").equals("LIGHT")) {
                if (!leftOppColor.equals("LIGHT")) {
                    changeColor();
                }
            } else {
                if (!leftOppColor.equals("DARK")) {
                    changeColor();
                }
            }
            if (dataMap.get("bottomType").equals("AI")) {
                if (!leftOpp.equals("AI")) {
                    changeLeftOpponent();
                }
                setLeftDifficulty(dataMap.get("bottomDiff"));
            } else {
                if (!leftOpp.equals("PLAYER")) {
                    changeLeftOpponent();
                    leftOppName.setText(dataMap.get("bottomName"));
                } else {
                    leftOppName.setText(dataMap.get("bottomName"));
                }
            }
            if (dataMap.get("topType").equals("AI")) {
                if (!rightOpp.equals("AI")) {
                    changeRightOpponent();
                }
                setRightDifficulty(dataMap.get("topDiff"));
            } else {
                if (!rightOpp.equals("PLAYER")) {
                    changeRightOpponent();
                    rightOppName.setText(dataMap.get("topName"));
                } else {
                    rightOppName.setText(dataMap.get("topName"));
                }
            }
            if (dataMap.get("forceAttack").equals("TRUE")) {
                forceAttackCheckBox.setSelected(true);
            } else {
                forceAttackCheckBox.setSelected(false);
            }
            if (dataMap.get("withdraw").equals("TRUE")) {
                withdrawCheckBox.setSelected(true);
            } else {
                withdrawCheckBox.setSelected(false);
            }
        }else {
            fileButton.setVisible(false);
            file = null;
        }
    }

    private void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Játék betöltése");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+GuiManager.fileLoader.getPathname()));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save files", "*.chg"));
    }

    private void setLeftDifficulty(String diff) {
        int difficult = Integer.parseInt(diff);
        if(leftDiff.getSelectionModel().getSelectedIndex()+1 != difficult) {
            leftDiff.getSelectionModel().select(difficult-1);
        }
    }

    private void setRightDifficulty(String diff) {
        int difficult = Integer.parseInt(diff);
        if(rightDiff.getSelectionModel().getSelectedIndex()+1 != difficult) {
            rightDiff.getSelectionModel().select(difficult-1);
        }
    }

    private GridPane setupGridPane() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        return pane;
    }

    public void setMenuBar(CheckersMenuBar menuBar) {
        setTop(menuBar);
    }
}
