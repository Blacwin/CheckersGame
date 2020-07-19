package checkersgame.frontend.panes;

import checkersgame.frontend.GuiManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class GuidePane extends BorderPane {
    private final List<String> rulesTexts;
    private Label ruleTextLabel;
    private List<String> guideTexts;
    private String title;
    private Label guideTextLabel;
    private ImageView pageImageView;
    private int pageNumber;

    public GuidePane() {
        this.getStyleClass().add("rules-stage");
        title = "Útmutató";
        rulesTexts = new ArrayList<>();
        String[] rawPageTexts = GuiManager.fileLoader.loadGuideText();
        for(String rawPageText: rawPageTexts) {
            rulesTexts.add(getPageString(rawPageText));
        }
        guideTextLabel = new Label();
        pageNumber = 0;

        pageImageView = new ImageView(GuiManager.fileLoader.loadGuideImage("guide"+ pageNumber) );
        ruleTextLabel = new Label();
        ruleTextLabel.getStyleClass().add("rules-label");
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

    private void changePageImage() {
        try {
            pageImageView.setImage(GuiManager.fileLoader.loadGuideImage("guide" + (pageNumber)) );
        } catch (Exception ex) {
            pageImageView.setImage(GuiManager.fileLoader.loadGuideImage("guide0") );
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

    public String getTitle() {
        return title;
    }
}
