package checkersgame.frontend.panes;

import checkersgame.backend.game.board.field.CheckersField;
import checkersgame.backend.game.board.field.FieldStatus;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.frontend.GuiManager;
import checkersgame.frontend.components.Field;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BoardPane extends GridPane {

    private Image darkpiece;
    private Image lightpiece;
    private Image darkpieceDame;
    private Image lightpieceDame;
    private Field[][] fields;
    private int BOARD_SIZE;

    public BoardPane(int boardSize) {
        this.BOARD_SIZE = boardSize;
        setupGridPane();
        createTable();
    }

    private GridPane setupGridPane() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(0);
        this.setVgap(0);
        this.getStyleClass().add("board");
        return this;
    }

    public void onFieldClick(int x, int y, ActionEvent event) {
        GuiManager.onFieldClick(x, y);
    }

    public void createTable() {
        this.darkpiece = GuiManager.fileLoader.loadIcon("dark-wood-piece-white-edge");
        this.lightpiece = GuiManager.fileLoader.loadIcon("light-wood-piece");
        this.darkpieceDame = GuiManager.fileLoader.loadIcon("dark-wood-piece-dame");
        this.lightpieceDame = GuiManager.fileLoader.loadIcon("light-wood-piece-dame");
        String[] abc = {"a", "b", "c", "d", "e", "f", "g", "h"};
        fields = new Field[BOARD_SIZE][BOARD_SIZE];
        for(int i=0; i<BOARD_SIZE+1; i++) {
            for(int j=0; j<BOARD_SIZE+1; j++) {
                if(i<BOARD_SIZE && j>0) {
                    fields[i][j-1] = new Field(((i+j-1)%2==0), i, j-1, 70, 70);
                    this.add(fields[i][j-1], j, i);
                    final int xVal = i;
                    final int yVal = j-1;
                    fields[i][j-1].setOnAction(e -> onFieldClick(xVal, yVal, e) );
                } else if(i==BOARD_SIZE && j>0) {
                    Label sideNumber = new Label(abc[j-1]);
                    sideNumber.getStyleClass().add("side-number-bottom");
                    this.add(sideNumber, j, i);
                } else if(j==0 && i<BOARD_SIZE) {
                    Label sideNumber = new Label(String.valueOf(i+1));
                    sideNumber.getStyleClass().add("side-number-left");
                    this.add(sideNumber, j, i);
                }
            }
        }
    }

    public void initBoard(CheckersField[][] modelfield) {
        for(int i=0; i<BOARD_SIZE; i++) {
            for(int j=0; j<BOARD_SIZE; j++) {
                fields[i][j].setDefault();
                if(!modelfield[i][j].isEmpty()) {
                    if(modelfield[i][j].getPiece().getColor() == PieceColor.LIGHT) {
                        if (modelfield[i][j].getPiece().isDame()) {
                            fields[i][j].setPiece(new ImageView(lightpieceDame));
                        }
                        else {
                            fields[i][j].setPiece(new ImageView(lightpiece));
                        }
                    }
                    else if(modelfield[i][j].getPiece().getColor() == PieceColor.DARK) {
                        if (modelfield[i][j].getPiece().isDame()) {
                            fields[i][j].setPiece(new ImageView(darkpieceDame));
                        }
                        else {
                            fields[i][j].setPiece(new ImageView(darkpiece));
                        }
                    }
                }
            }
        }
    }

    public void refreshBoard(CheckersField[][] modelfield) {
        for(int i=0; i<BOARD_SIZE; i++) {
            for(int j=0; j<BOARD_SIZE; j++) {
                this.fields[i][j].removePiece();
                this.fields[i][j].removeStyle();
                if(!modelfield[i][j].isEmpty()) {
                    if(modelfield[i][j].getPiece().getColor() == PieceColor.LIGHT) {
                        if (modelfield[i][j].getPiece().isDame()) {
                            this.fields[i][j].setGraphic(new ImageView(lightpieceDame));
                        }
                        else {
                            this.fields[i][j].setGraphic(new ImageView(lightpiece));
                        }
                    }
                    else if(modelfield[i][j].getPiece().getColor() == PieceColor.DARK) {
                        if (modelfield[i][j].getPiece().isDame()) {
                            this.fields[i][j].setGraphic(new ImageView(darkpieceDame));
                        }
                        else {
                            this.fields[i][j].setGraphic(new ImageView(darkpiece));
                        }
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
                else if(modelfield[i][j].getStatus() == FieldStatus.PREVFIELD) {
                    this.fields[i][j].setPrevField();
                }
                else if(modelfield[i][j].getStatus() == FieldStatus.PREVHITFIELD) {
                    this.fields[i][j].setPrevHitField();
                }
                else if(modelfield[i][j].getStatus() == FieldStatus.CURRENTFIELD) {
                    this.fields[i][j].setCurrentField();
                }
            }
        }
    }
}
