package org.checkers.board;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import javafx.stage.Stage;
import org.checkers.utils.WindowProperties;

public class BoardView extends GridPane {

    private final BoardController boardController;

    BoardView(int size, boolean[][] whitePieces, boolean[][] blackPieces, BoardController boardController) {
        this.boardController = boardController;
        double windowSize = WindowProperties.calculateWindowStageSize();
        double buttonSize = windowSize * 0.9 / size;
        double pieceSize = 0.8 * buttonSize / 2;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {

                Button button = new Button();
                button.setPrefHeight(buttonSize);
                button.setPrefWidth(buttonSize);

                if((i+j) % 2 == 0) {
                    button.setStyle("-fx-background-color: #ffffff");
                }
                else {
                    button.setStyle("-fx-background-color: #000000");
                }

                if(whitePieces[i][j]) {
                    Circle circle = new Circle(pieceSize);
                    circle.setFill(Paint.valueOf("#0000ff"));
                    circle.setCenterX(buttonSize / 2.0);
                    circle.setCenterY(buttonSize / 2.0);
                    Group group = new Group(button, circle);
                    add(group, i, j);
                }
                else if(blackPieces[i][j]) {
                    Circle circle = new Circle(pieceSize);
                    circle.setFill(Paint.valueOf("#00ff00"));
                    circle.setCenterX(buttonSize / 2.0);
                    circle.setCenterY(buttonSize / 2.0);
                    Group group = new Group(button, circle);
                    add(group, i, j);
                }
                else {
                    add(button, i, j );
                }
            }
        }
    }
}
