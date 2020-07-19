package checkersgame.frontend.panes;

import checkersgame.frontend.GuiManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class GameRulesPane extends BorderPane {
    private List<String> rulesTexts;
    private String title;
    private int pageNumber;
    private Label ruleTextLabel;
    private ImageView pageImageView;

    public GameRulesPane() {
        this.getStyleClass().add("rules-stage");
        title = "Játékszabályok";
        rulesTexts = new ArrayList<>();
        String[] rawPageTexts = GuiManager.fileLoader.loadGameRules();
        for(String rawPageText: rawPageTexts) {
            rulesTexts.add(getPageString(rawPageText));
        }
        pageImageView = new ImageView(GuiManager.fileLoader.loadRulesImage("rules-"+ pageNumber) );
        ruleTextLabel = new Label();
        ruleTextLabel.getStyleClass().add("rules-label");
//        ruleTextLabel.setTextAlignment(TextAlignment.CENTER);
        Button nextButton = new Button("Következő");
        nextButton.getStyleClass().addAll("main-button", "ingame-button");
        nextButton.setOnAction(event -> goToNextPage());

        Button backButton = new Button("Vissza");
        backButton.getStyleClass().addAll("main-button", "ingame-button");
        backButton.setOnAction(event -> goToPreviousPage());

        Button closeButton = new Button("Bezár");
        closeButton.getStyleClass().addAll("main-button", "ingame-button");
        closeButton.setOnAction(event -> close());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(ruleTextLabel, pageImageView);
        vBox.getStyleClass().add("rules-box");
        setCenter(vBox);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(backButton, nextButton, closeButton);
        hBox.getStyleClass().add("rules-box");
        setBottom(hBox);
    }

    public void open() {
        pageNumber = 0;
        ruleTextLabel.setText(rulesTexts.get(pageNumber));
        changePageImage();
    }

    private void close() {
        GuiManager.closeSecondWindow();
    }

    private void goToPreviousPage() {
        if(pageNumber-1 >= 0) {
            pageNumber--;
            ruleTextLabel.setText(rulesTexts.get(pageNumber));
            changePageImage();
        }
    }

    private void goToNextPage() {
        if(pageNumber+1 < rulesTexts.size()) {
            pageNumber++;
            ruleTextLabel.setText(rulesTexts.get(pageNumber));
            changePageImage();
        }
    }

    private String getPageString(String rawText) {
        String result = "";
        for(String token : rawText.split("%")) {
            result += token;
            result += "\n";
        }
        return result;
    }

    private void changePageImage() {
        try {
            pageImageView.setImage(GuiManager.fileLoader.loadRulesImage("rules-" + (pageNumber+1)) );
        } catch (Exception ex) {
            pageImageView.setImage(GuiManager.fileLoader.loadRulesImage("rules-0") );
        }
    }

    public String getTitle() {
        return title;
    }
}
