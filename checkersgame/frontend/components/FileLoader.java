package checkersgame.frontend.components;

import checkersgame.frontend.GuiManager;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLoader {
    private String pathname;

    public FileLoader() {
//        this.pathname = "src/checkersgame/frontend/assets/";
        this.pathname = "assets/";
    }

    public String getPathname() {
        return pathname;
    }

    public FileChooser getFileChooserForSaveFolder(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+ "/save/"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save files", "*.chg"));
        File file = new File("save");
        if(!file.isDirectory()) {
            file.mkdir();
        }
        return fileChooser;
    }

    public String[] loadGameRules() {
        File file = new File(pathname + "rules/rules.txt");
        try {
            Scanner scan = new Scanner(file);
            String str = "";
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();
            return str.split("\\+");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] loadGuideText() {
        File file = new File(pathname + "guide/guide.txt");
        try {
            Scanner scan = new Scanner(file);
            String str = "";
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();
            return str.split("\\+");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File loadFileFromAssets(String name) {
        File file = new File(pathname + name);
        return file;
    }

    public Media loadSoundFile(String name) {
        File file = new File(pathname + name);
        Media media = new Media(file.toURI().toString());
        return media;
    }

    public String getStyleSheetsPathByName(String name) {
        return pathname + name;
    }

    public Image loadIcon(String name) {
        return new Image( pathname + "icons/"+ name +".png");
    }

    public Image loadTutorialImage(String name) {
        return new Image(pathname + "tutorial/"+ name +".png");
    }

    public Image loadBackgroundImage(String name) {
        return new Image(pathname + name +".jpg");
    }

    public Image loadRulesImage(String name) {
        return new Image(pathname + "rules/"+ name +".png");
    }

    public Image loadGuideImage(String name) {
        return new Image(pathname + "guide/"+ name +".png");
    }
}
