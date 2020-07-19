package checkersgame.frontend.panes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class StatPane extends BorderPane {
    private ListView moveHistoryListView;
    private ObservableList moves;
    private String title;

    public StatPane() {
        this.getStyleClass().add("rules-stage");
        title = "Lépés történet";
        moves = FXCollections.observableArrayList();
        moveHistoryListView = new ListView(moves);
        setCenter(moveHistoryListView);
    }

    public void refreshContent(List<String> historyList) {
        moves.clear();
        moves.addAll(historyList);
    }

    public String getTitle() {
        return title;
    }
}
