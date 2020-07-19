package checkersgame.frontend.components;

import checkersgame.frontend.GuiManager;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckersMenuBar extends MenuBar {
    private MenuItem menuItemQuit;
    private MenuItem menuItemEnd;
    private MenuItem menuItemSave;
    private MenuItem menuItemNew;
    private MenuItem menuItemTutorial;

    public CheckersMenuBar() {
        Menu gameMenu = new Menu("Játék");
        gameMenu.setGraphic(new ImageView(GuiManager.fileLoader.loadIcon("game-icon2")));
        this.getMenus().add(gameMenu);
        gameMenu.getItems().addAll(generateGameMenu());
        
//        Menu settings = new Menu("Beállítások");
//        settings.setGraphic(new ImageView(GuiManager.ImgLoader.loadIcon("setting-icon")));
//        this.getMenus().add(settings);

        Menu menuHelp = new Menu("Segítség");
        menuHelp.setGraphic(new ImageView(GuiManager.fileLoader.loadIcon("help-icon2")));
        this.getMenus().add(menuHelp);
        menuHelp.getItems().addAll(generateHelpMenu());
    }
    
    private List<MenuItem> generateGameMenu() {
        List<MenuItem> menuItems = new ArrayList<>();
        
        menuItemQuit = new MenuItem("Kilépés");
        menuItemQuit.setOnAction(e -> onQuit());
//        menuItemQuit.setGraphic( new ImageView( new Image("checkersgame/frontend/assets/icons/light-piece.png", 16, 16, true, true) ) );
        menuItemQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        
        menuItemEnd = new MenuItem("Játék befejezése");
        menuItemEnd.setOnAction(e -> onEndTheGame());
        
        menuItemSave = new MenuItem("Mentés");
        menuItemSave.setOnAction(e -> saveTheGame());

        menuItemNew = new MenuItem("Új játék");
        menuItemNew.setOnAction(e -> GuiManager.goToGameModePane());

        menuItemTutorial = new MenuItem("Oktató");
        menuItemTutorial.setOnAction(e -> GuiManager.startTutorial());
        
        menuItems.add(menuItemNew);
        menuItems.add(menuItemTutorial);
        menuItems.add(menuItemEnd);
        menuItems.add(menuItemSave);
        menuItems.add(menuItemQuit);
        return menuItems;
    }

    private List<MenuItem> generateHelpMenu() {
        List<MenuItem> menuItems = new ArrayList<>();

        MenuItem menuItemRules = new MenuItem("Játékszabályok");
        menuItemRules.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        menuItemRules.setOnAction(e -> onDisplayRules());

        MenuItem menuItemGuide = new MenuItem("Útmutató");
        menuItemGuide.setAccelerator(new KeyCodeCombination(KeyCode.F2));
        menuItemGuide.setOnAction(event -> onDisplayGuide());

        menuItems.add(menuItemRules);
//        menuItems.add(menuItemGuide);
        return menuItems;
    }

    private void onDisplayGuide() {
        GuiManager.showGuide();
    }

    private void saveTheGame() {
        GuiManager.saveTheGame();
    }

    private void onQuit() {
        GuiManager.exit();
    }

    private void onDisplayRules() {
        GuiManager.showGameRules();
//        BorderPane root;
//        try {
//            //root = FXMLLoader.load(getClass().getClassLoader().getResource("path/to/other/view.fxml"), resources);
//            root = new BorderPane();
//            Text rulesText = new Text("Játékszabályok szövege");
//            root.setCenter(rulesText);
//            Stage stage = new Stage();
//            stage.setTitle("Játékszabályok");
//            stage.setScene(new Scene(root, 450, 450));
//            stage.show();
//            // Hide this current window (if this is what you want)
//            //((Node)(event.getSource())).getScene().getWindow().hide();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    
    private void onEndTheGame() {
        GuiManager.finishTheGame();
    }

    public void setForMainPane() {
        menuItemSave.setVisible(false);
        menuItemEnd.setVisible(false);
        menuItemTutorial.setVisible(true);
        menuItemNew.setVisible(true);
    }

    public void setForBoardPane() {
        menuItemEnd.setVisible(true);
        menuItemSave.setVisible(true);
        menuItemNew.setVisible(false);
        menuItemTutorial.setVisible(false);
    }

    public void setForTutorPane() {
        menuItemSave.setVisible(false);
        menuItemEnd.setVisible(true);
        menuItemTutorial.setVisible(false);
        menuItemNew.setVisible(false);
    }

    private MenuBar generateMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu gameMenu = new Menu("Game");
        menuBar.getMenus().add(gameMenu);

        MenuItem menuItemQuit = new MenuItem("Quit");
        menuItemQuit.setOnAction(e -> onQuit());
        //menuItemQuit.setGraphic( new ImageView( new Image("assets/icons/quit.png", 16, 16, true, true) ) );
        menuItemQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        gameMenu.getItems().add(menuItemQuit);

        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().add(menuHelp);

        MenuItem menuItemAbout = new MenuItem("About");
        //menuItemAbout.setGraphic( new ImageView( new Image("assets/icons/about.png", 16, 16, true, true) ) );
        // Note: Accelerator F1 does not work if TextField is
        //       in focus. This is a known issue in JavaFX.
        //       https://bugs.openjdk.java.net/browse/JDK-8148857
        menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        menuItemAbout.setOnAction(e -> onDisplayRules());
        menuHelp.getItems().add(menuItemAbout);

        return menuBar;
    }
}
