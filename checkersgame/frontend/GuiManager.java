package checkersgame.frontend;

import checkersgame.backend.Model;
import checkersgame.backend.game.board.field.CheckersField;
import checkersgame.frontend.components.CheckersMenuBar;
import checkersgame.frontend.components.FileLoader;
import checkersgame.frontend.panes.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class GuiManager {

    public static Stage window;
    public static Stage secondWindow;
    private static Scene primaryScene;
    private static Scene secondaryScene;
    private static MainPane mainPane;
    private static GameModePane gameModePane;
    private static GamePane gamePane;
    private static TutorPane tutorPane;
    private static ExtraPane extraPane;
    private static GameRulesPane gameRulesPane;
    private static GuidePane guidePane;
    private static StatPane statPane;
    public static Model model;
    private static Media sound;
    private static CheckersMenuBar menuBar;
    public static FileLoader fileLoader;

    public static final int SCREEN_WIDTH = 950;
    public static final int SCREEN_HEIGHT = 660;

    public static void init(Stage window, Model modelObject) {
        GuiManager.window = window;
        model = modelObject;
        fileLoader = new FileLoader();
        sound = fileLoader.loadSoundFile("chess-step-effect.wav");
    }

    public static void start() {
        try {
            menuBar = new CheckersMenuBar();
            setPanes();
            initSecondWindow();
            primaryScene = new Scene(mainPane, SCREEN_WIDTH, SCREEN_HEIGHT);
            primaryScene.getStylesheets().add(fileLoader.getStyleSheetsPathByName("stylesheet.css"));
            menuBar.setForMainPane();
            mainPane.setMenuBar(menuBar);

            window.setTitle("Dáma játék");
            window.setScene(primaryScene);
            window.show();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            getErrorMsg("Hiba a program indításakor", "Egy vagy több hiányzó fájl miatt a program nem tud" +
                    " elindulni. A program újratelepítése megoldhatja a problémát. \n" + iae.getLocalizedMessage());
        }
    }

    private static void initSecondWindow() {
        secondWindow = new Stage();
        secondWindow.setTitle(gameRulesPane.getTitle());
        secondaryScene = new Scene(gameRulesPane, 550, 500);
        secondaryScene.getStylesheets().add(fileLoader.getStyleSheetsPathByName("stylesheet.css"));
        secondWindow.setScene(secondaryScene);
    }

    private static void setPanes() {
        int boardSize = model.getBOARD_SIZE();
        mainPane = new MainPane();
        gameModePane = new GameModePane();
        gamePane = new GamePane(boardSize);
        tutorPane = new TutorPane(boardSize);
        extraPane = new ExtraPane(boardSize);
        gameRulesPane = new GameRulesPane();
        guidePane = new GuidePane();
        statPane = new StatPane();
    }

    public static Stage getStage() {
        return window;
    }

    public static void goToGameModePane() {
        primaryScene.setRoot(gameModePane);
        gameModePane.setMenuBar(menuBar);
    }

    public static void startNewGame(String bottomColor, String topColor, int bottomDifficult, int topDifficult, boolean forceAttack, File file) {
        primaryScene.setRoot(gamePane);
        gamePane.showDemoController();
        model.startNewGame(bottomColor, topColor, bottomDifficult, topDifficult, forceAttack, file);
    }

    public static void startNewGame(String bottomColor, String topColor, String leftName, String rightName, boolean forceAttack, boolean withdraw, File file) {
        primaryScene.setRoot(gamePane);
        gamePane.setWithdrawButtons(withdraw);
        gamePane.hideDemoController();
        model.startNewGame(bottomColor, topColor, leftName, rightName, forceAttack, withdraw, file);
    }

    public static void startNewGame(String bottomColor, String topColor, String playerDirection, int difficult, String playerName, boolean forceAttack, boolean withdraw, File file) {
        primaryScene.setRoot(gamePane);
        gamePane.setWithdrawButtons(withdraw);
        gamePane.hideDemoController();
        model.startNewGame(bottomColor, topColor, playerDirection, difficult, playerName, forceAttack, withdraw, file);
    }
    
    public static void backToMain() {
        primaryScene.setRoot(mainPane);
        menuBar.setForMainPane();
        mainPane.setMenuBar(menuBar);
    }

    public static void initGameBoard() {
        gamePane.initBoard(model.getBoard());
        gamePane.initOpponentView(model.getOpponentDataForUI());
        menuBar.setForBoardPane();
        gamePane.setMenuBar(menuBar);
    }

    public static void onFieldClick(int x, int y) {
        model.onFieldClick(x, y);
        model.click = true;
    }

    public static void refreshGameBoard() {
        refreshBoardPane(model.getBoard());
    }

    public static void refreshGameBoard(CheckersField[][] board) {
        refreshBoardPane(board);
    }

    private static void refreshBoardPane(CheckersField[][] board) {
        gamePane.refreshBoard(board);
    }

    public static void refreshSidePanels(int leftNum, int leftBackCounter, String leftColor, int rightNum, int rightBackCounter, String rightColor, boolean withdraw) {
        gamePane.refreshPanels(leftNum, leftBackCounter, leftColor, rightNum, rightBackCounter, rightColor, withdraw);
    }

    public static void refreshElapsedTime(long time) {
        gamePane.setTimeLabel(time);
    }

    public static void makeStepSound() {
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.setVolume(0.2);
//        mediaPlayer.play();
    }
    
    public static void endOfTheGame(int endNumber) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Játék vége");
        String endText = "";
        if(endNumber == 2) endText = "Világos szín nyert.";
        if(endNumber == 1) endText = "Sötét szín nyert.";
        if(endNumber == 3) endText = "Döntetlen.";
        alert.setHeaderText(endText);
        alert.setContentText("Szeretne új játékot kezdeni vagy visszatér a főmenübe?");
        
        ButtonType buttonReplay = new ButtonType("Új játék");
        ButtonType buttonMenu = new ButtonType("Vissza a menübe");
        
        alert.getButtonTypes().setAll(buttonReplay, buttonMenu);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonReplay) {
            goToGameModePane();
        } else if (result.get() == buttonMenu) {
            backToMain();
        }
    }

    public static void finishTheGame() {
        model.exit = true;
        model.gameCompletion();
        goToGameModePane();
    }
    
    public static void exit() {
        model.exit = true;
        Platform.exit();
        System.exit(0);
    }

    public static void getDataFromFile(File file) {
        gameModePane.updateUI(model.getDataMapFromFile(file));
    }

    public static void setActiveSideBox(String side, String currentColor, boolean withdraw) {
        gamePane.setActiveOpponentBox(side, currentColor, withdraw);
    }

    public static void showInfoPanel(String side) {
        secondaryScene.setRoot(statPane);
        if(side.equals("LEFT")) {
            statPane.refreshContent(model.getGame().getBottomOpponent().getMoveHistoryList());
        } else if(side.equals("RIGHT")) {
            statPane.refreshContent(model.getGame().getTopOpponent().getMoveHistoryList());
        }
        secondWindow.show();
        secondWindow.setTitle(statPane.getTitle());
    }

    public static void undoLastStep(String side) {
        model.undoLastStep();
    }

    public static void saveTheGame() {
        FileChooser fileChooser = fileLoader.getFileChooserForSaveFolder("Játék betöltése");
        File selectedFile = fileChooser.showSaveDialog(window);
        try {
            if(selectedFile != null) {
                if(selectedFile.exists()) {
                    FileWriter writer = new FileWriter(selectedFile);
                    writer.write(model.getGameString());
                    writer.flush();
                    writer.close();
                    getInformationMsg("Mentés sikerült", "A " + selectedFile.getName() + " nevű mentés felülírva");
//                    System.out.println("The file \"" + selectedFile.getName() + "\" overwrited." );
                }else if(selectedFile.createNewFile()) {
                    FileWriter writer = new FileWriter(selectedFile);
                    writer.write(model.getGameString());
                    writer.flush();
                    writer.close();
                    getInformationMsg("Mentés sikerült", "A " + selectedFile.getName() + " nevű mentés létrehozva");
//                    System.out.println("The file \"" + selectedFile.getName() + "\" created." );
                }
            }
        }catch (IOException ex) {
//            ex.printStackTrace();
            getErrorMsg("Hiba a mentés során", ex.getMessage());
        }
    }

    public static MenuBar getMenuBar() {
        return menuBar;
    }

    public static void startTutorial() {
        model.startTutorial();
        primaryScene.setRoot(tutorPane);
        showGameRules();
    }

    public static void showGameRules() {
        gameRulesPane.open();
        secondaryScene.setRoot(gameRulesPane);
        secondWindow.show();
        secondWindow.setTitle(gameRulesPane.getTitle());
    }

    public static void showGuide() {
        guidePane.open();
        secondaryScene.setRoot(guidePane);
        secondWindow.show();
        secondWindow.setTitle(guidePane.getTitle());
    }

    public static void closeSecondWindow() {
        secondWindow.close();
    }

    public static void initTutorBoard(CheckersField[][] modelField) {
        tutorPane.initBoard(modelField);
        menuBar.setForTutorPane();
        tutorPane.setMenuBar(menuBar);
    }

    public static void refreshTutorBoard(CheckersField[][] modelField) {
        tutorPane.refereshBoard(modelField);
    }

    public static void nextTutorLevel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Kész!");
        alert.setHeaderText("Gyakorló szint teljesítve.");
        alert.setContentText("Ugrás a következő szintre vagy kilépés?");

        ButtonType buttonNext = new ButtonType("Következő");
        ButtonType buttonMenu = new ButtonType("Kilépés");

        alert.getButtonTypes().setAll(buttonNext, buttonMenu);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonNext) {
            goToNextTutorialLevel();
        } else if (result.get() == buttonMenu) {
            backToMain();
        }
    }

    public static void replayTutorLevel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nem sikerült!");
        alert.setHeaderText("Sajnos nem sikerült teljesítened a szintet.");
        alert.setContentText("Szint újrakezdése vagy kilépés?");

        ButtonType buttonNext = new ButtonType("Újra");
        ButtonType buttonMenu = new ButtonType("Kilépés");

        alert.getButtonTypes().setAll(buttonNext, buttonMenu);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonNext) {
            goToNextTutorialLevel();
        } else if (result.get() == buttonMenu) {
            backToMain();
        }
    }

    public static void goToNextTutorialLevel() {
        model.startTutorialLevel();
    }

    public static void refreshTutorPanel(List<String> description, int level, String title) {
        tutorPane.refreshSidePanel(description, level, title);
    }

    public static void backToPreviousTutorLevel() {
        model.backToPreviousLevel();
    }

    public static void finishingTutorial() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Gyakroló rész vége!");
        alert.setHeaderText("Sikeresen teljesítetted a gyakorló összes szintjét.\n" +
                "Most már készen állsz hogy élesben is kipróbáld.");
        alert.setContentText("Szeretnél kezdeni egy új játékot?");

        ButtonType buttonNewGame = new ButtonType("Új játék");
        ButtonType buttonMenu = new ButtonType("Kilépés");

        alert.getButtonTypes().setAll(buttonNewGame, buttonMenu);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonNewGame) {
            goToGameModePane();
        } else if (result.get() == buttonMenu) {
            backToMain();
        }
    }

    public static void startChallengeMode() {
        model.startExtraMode();
        primaryScene.setRoot(extraPane);
    }

    public static void initChallengeBoard(CheckersField[][] modelfield) {
        extraPane.initBoard(modelfield);
    }

    public static void refreshChallengeBoard(CheckersField[][] modelfield) {
        extraPane.refreshBoard(modelfield);
    }

    public static void refreshChallengePanel(String desc, String title) {
        extraPane.refreshSidePanel(desc, title);
    }

    public static void extraGameEnded(int endNumber) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Teljesítve");
        String endText = "";
        String questionText = "";
        String buttonText = "";
        if(endNumber == 1) {
            endText = "Nyertél!";
            questionText = "Tovább a következő pályára vagy kilépés?";
            buttonText = "Következő";
        }else {
            endText = "Vesztettél!";
            questionText = "Újra vagy kilépés?";
            buttonText = "Újra";
        }
        alert.setHeaderText(endText);
        alert.setContentText(questionText);

        ButtonType buttonNextOrReplay = new ButtonType(buttonText);
        ButtonType buttonMenu = new ButtonType("Vissza a menübe");

        alert.getButtonTypes().setAll(buttonNextOrReplay, buttonMenu);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonNextOrReplay) {
            if(endNumber == 1) {
                model.nextExtraLevel();
            }else {
                model.reloadExtraLevel();
            }
        } else if (result.get() == buttonMenu) {
            backToMain();
        }
    }

    public static void setupGamePaneForDemoMode() {
        gamePane.showDemoController();
    }

    public static void playDemo() {
        model.playDemo();
    }

    public static void stopDemo() {
        model.stopDemo();
    }

    public static void speedUp() {
        model.speedUp();
    }

    public static void speedDown() {
        model.speedDown();
    }

    public static void forwardStepOnDemo() {
        model.forwardStepOnDemo();
    }

    public static void backwardStepOnDemo() {
        model.backwardStepOnDemo();
    }

    public static void disablePlayButton() {
        gamePane.disablePlayButton();
    }

    public static void enablePlayButton() {
        gamePane.enablePlayButton();
    }

    public static void refreshDemoController(boolean isStopped, boolean atTheEnd, boolean atTheStart) {
        gamePane.refreshDemoController(isStopped, atTheEnd, atTheStart);
    }

    public static void loadExtraLevel(int level) {
        model.loadExtraLevel(level);
    }

    public static void getErrorMsg(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void getInformationMsg(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
