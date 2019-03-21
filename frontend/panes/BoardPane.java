/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.frontend.panes;

import checkersgame.backend.field.CheckersField;
import checkersgame.backend.field.FieldStatus;
import checkersgame.backend.field.Piece;
import checkersgame.backend.field.PieceColor;
import checkersgame.frontend.GuiManager;
import checkersgame.frontend.components.CheckersMenuBar;
import checkersgame.frontend.components.Field;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Magyar Bálint
 */
public class BoardPane extends BorderPane {
    
    private Field fields[][];
    private Image darkpiece;
    private Image lightpiece;
    private GridPane layout;
    private boolean selected;

    public BoardPane() {
        layout = this.setupGridPane();
        createTable();
        setCenter(layout);
        setTop(new CheckersMenuBar());
    }
    
    private GridPane setupGridPane() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(0);
        pane.setVgap(0);
        //setPadding(new Insets(25, 25, 25, 25));
        return pane;
    }
    
    private void createTable() {
        this.darkpiece = new Image("checkersgame/frontend/assets/icons/dark-piece.png");
        this.lightpiece = new Image("checkersgame/frontend/assets/icons/light-piece.png");
        this.selected = false;
        fields = new Field[8][8];
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                fields[i][j] = new Field(((i+j)%2==0), i, j, 70, 70);
                this.layout.add(fields[i][j], j, i);
                final int xVal = i;
                final int yVal = j;
                fields[i][j].setOnAction(e -> onFieldClick(xVal, yVal, e) );
            }
        }
    }
    
    private void onFieldClick(int x, int y, ActionEvent event) {
        GuiManager.onFieldClick(x, y);
    }
    
    public void initBoard(CheckersField[][] modelfield) {
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                fields[i][j].setDefault();
                if(!modelfield[i][j].isEmpty()) {
                    if(modelfield[i][j].getPiece().getColor() == PieceColor.LIGHT) {
                        fields[i][j].setPiece(new ImageView(lightpiece));
                    }
                    else if(modelfield[i][j].getPiece().getColor() == PieceColor.DARK) {
                        fields[i][j].setPiece(new ImageView(darkpiece));
                    }
                }
            }
        }
    }
    
    public void refereshBoard(CheckersField[][] modelfield) {
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                this.fields[i][j].removePiece();
                this.fields[i][j].removeStyle();
                if(!modelfield[i][j].isEmpty()) {
                    if(modelfield[i][j].getPiece().getColor() == PieceColor.LIGHT) {
                        this.fields[i][j].setGraphic(new ImageView(lightpiece));
                    }
                    else if(modelfield[i][j].getPiece().getColor() == PieceColor.DARK) {
                        this.fields[i][j].setGraphic(new ImageView(darkpiece));
                    }
                }
                if(modelfield[i][j].getStatus() == FieldStatus.SELECTEDFIELD) {
                    this.fields[i][j].setSelectedField();
                }
                else if(modelfield[i][j].getStatus() == FieldStatus.HITFIELD) {
                    this.fields[i][j].setHitField();
                }
                else if(modelfield[i][j].getStatus() == FieldStatus.STEPFIELD) {
                    this.fields[i][j].setStepField();
                }
            }
        }
    }
}
