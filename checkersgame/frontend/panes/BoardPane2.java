package checkersgame.frontend.panes;

import checkersgame.backend.game.board.field.CheckersField;
import checkersgame.frontend.GuiManager;
import checkersgame.frontend.components.Field2;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BoardPane2 extends GridPane {
    private Canvas canvasForBoard;
    private GraphicsContext gc;
    private int BOARD_SIZE;

    public BoardPane2(int boardSize) {
        BOARD_SIZE = boardSize;
        canvasForBoard = new Canvas(600, 600);
        canvasForBoard.setOnMouseClicked(event -> mouseClicked(event));
        canvasForBoard.setOnMouseDragged(event -> mouseDragging(event));
        canvasForBoard.setOnMouseReleased(event -> mouseReleased(event));
        gc = canvasForBoard.getGraphicsContext2D();
        drawShapes(gc);
        add(canvasForBoard, 0,0);
    }

    private void mouseReleased(MouseEvent event) {

    }

    private void mouseDragging(MouseEvent event) {

    }

    private void mouseClicked(MouseEvent event) {
        System.out.println("click:" + event.getX() + " : " + event.getY());
    }

    private void drawShapes(GraphicsContext gc) {
        Image image = GuiManager.fileLoader.loadIcon("chessboard3");
        Image lightpiece = GuiManager.fileLoader.loadIcon("light-wood-piece");
        Image darkpiece = GuiManager.fileLoader.loadIcon("dark-wood-piece");
        gc.drawImage(image, 0, 0, 600, 600);
        double width = 600;
        for(int i=0; i<BOARD_SIZE+1; i++) {
            for (int j = 0; j < BOARD_SIZE + 1; j++) {
                if ((i+j)%2==1) {
                    if(j < 3) {
                        gc.drawImage(lightpiece, width/8*i+12, width/8*j+12);
                    }else if(j > 4) {
                        gc.drawImage(darkpiece, width/8*i+12, width/8*j+12);
                    }
                }
            }
        }
//        gc.setFill(Color.GREEN);
//        gc.setStroke(Color.BLUE);
//        gc.setLineWidth(5);
//        gc.strokeLine(40, 10, 10, 40);
//        gc.fillOval(10, 60, 30, 30);
//        gc.strokeOval(60, 60, 30, 30);
//        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
//        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
//        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
//        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
//        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
//        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
//        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
//        gc.fillPolygon(new double[]{10, 40, 10, 40},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolygon(new double[]{60, 90, 60, 90},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolyline(new double[]{110, 140, 110, 140},
//                new double[]{210, 210, 240, 240}, 4);
    }

    private void createBoard(CheckersField[][] modelfield) {
        Field2[][] checkersFields = new Field2[BOARD_SIZE][BOARD_SIZE];
        for(int i=0; i<BOARD_SIZE; i++) {
            for(int j=0; j<BOARD_SIZE; j++) {
                if(!modelfield[i][j].isEmpty()) {

                }
            }
        }
    }
}
