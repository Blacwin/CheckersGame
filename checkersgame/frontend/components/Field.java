/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.frontend.components;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;

/**
 *
 * @author Magyar Bálint
 */
public class Field extends Button{
    private int x;
    private int y;

    public Field(boolean light, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        
        this.setPrefSize(width, height);
        this.setMinSize(width, height);
        this.setMaxSize(width, height);
        
        //this.getStyleClass()
        if(light) {
            this.getStyleClass().add("light-field");
        }else {
            this.getStyleClass().add("dark-field");
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
    }
    
    public void setDefault() {
        removePiece();
        removeStyle();
    }
}
