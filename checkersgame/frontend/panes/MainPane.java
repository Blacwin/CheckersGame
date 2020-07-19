package checkersgame.frontend.panes;

import checkersgame.frontend.GuiManager;
import checkersgame.frontend.components.CheckersMenuBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainPane extends BorderPane {
    private GridPane layout;
    private Label title;
    
    public MainPane() {
        this.layout = setupGridPane();
        this.setId("main");
        this.getStyleClass().add("pane");

        title = new Label("Dáma Játék");
        title.getStyleClass().add("title-label");
        Button gameButton = new Button("Játék");
        gameButton.setOnAction(e -> goToGameModePane());
        gameButton.getStyleClass().add("main-button");
        Button tutorialButton = new Button("Oktató");
        tutorialButton.setOnAction(e -> enterTutorialMode());
        tutorialButton.getStyleClass().add("main-button");
        Button challengeButton = new Button("Extra");
        challengeButton.setOnAction(e -> enterChallengeMode());
        challengeButton.getStyleClass().add("main-button");
        Button exitButton = new Button("Kilépés");
        exitButton.setOnAction(e -> exit());
        exitButton.getStyleClass().add("main-button");

        layout.setGridLinesVisible(true);
        layout.add(title, 0, 0);
        layout.add(gameButton, 0, 1);
        layout.add(tutorialButton, 0, 2);
        layout.add(challengeButton, 0, 3);
        layout.add(exitButton, 0, 4);
        setCenter(layout);
        VBox topBox = new VBox();
        topBox.getChildren().addAll(GuiManager.getMenuBar(), title);
        topBox.setAlignment(Pos.CENTER);
        setTop(topBox);
    }

    private GridPane setupGridPane() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        return pane;
    }
    
    private void goToGameModePane() {
        GuiManager.goToGameModePane();
    }

    private void enterTutorialMode() {
        GuiManager.startTutorial();
    }

    private void enterChallengeMode() {
        GuiManager.startChallengeMode();
    }
    
    private void exit() {
        GuiManager.exit();
    }

    public void setMenuBar(CheckersMenuBar menuBar) {
        VBox topBox = new VBox();
        topBox.getChildren().addAll(GuiManager.getMenuBar(), title);
        topBox.setAlignment(Pos.CENTER);
        setTop(topBox);
    }
}
