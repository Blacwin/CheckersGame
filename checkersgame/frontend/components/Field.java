package checkersgame.frontend.components;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Field extends Button{
    private int x;
    private int y;

    public Field(boolean light, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        
        this.setPrefSize(width, height);
        this.setMinSize(width, height);
        this.setMaxSize(width, height);

        if(light) {
            this.getStyleClass().add("light-field");
            this.setStyle("-fx-background-image: url('checkersgame/frontend/assets/icons/light-wood.jpg')");
        }else {
            this.getStyleClass().add("dark-field");
            this.setStyle("-fx-background-image: url('checkersgame/frontend/assets/icons/dark-wood.jpg')");
        }
    }
    
    public void setPiece(ImageView imgv) {
        setGraphic(imgv);
    }
    
    public void removePiece() {
        setGraphic(null);
    }
    
    public void setStepField() {
        removeStyle();
        this.getStyleClass().add("status-step");
    }
    
    public void setHitField() {
        removeStyle();
        this.getStyleClass().add("status-hit");
    }
    
    public void setSelectedField() {
        removeStyle();
        this.getStyleClass().add("status-selected");
    }
    
    public void getStyles() {
        System.out.println(this.getStyleClass());
    }
    
    public void removeStyle() {
        this.getStyleClass().removeAll("status-step");
        this.getStyleClass().removeAll("status-hit");
        this.getStyleClass().removeAll("status-selected");
        this.getStyleClass().removeAll("status-prev", "status-prevhit", "status-current");
    }
    
    public void setDefault() {
        removePiece();
        removeStyle();
    }

    public void setPrevField() {
        removeStyle();
        this.getStyleClass().add("status-prev");
    }

    public void setPrevHitField() {
        removeStyle();
        this.getStyleClass().add("status-prevhit");
    }

    public void setCurrentField() {
        removeStyle();
        this.getStyleClass().add("status-current");
    }
}
