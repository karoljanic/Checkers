package org.checkers.board;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import javafx.stage.Stage;
import javafx.util.Pair;
import org.checkers.utils.CheckerColor;
import org.checkers.utils.WindowProperties;

import java.util.ArrayList;

public class BoardView extends GridPane {

    private final Button[][] buttons;
    private final BoardController boardController;

    BoardView(int size, Piece[][] pieces, boolean[][] possibleMoves, BoardController boardController) {
        this.boardController = boardController;
        double windowSize = WindowProperties.calculateWindowStageSize();
        double buttonSize = windowSize * 0.9 / size;
        double pieceSize = 0.8 * buttonSize / 2;

        buttons = new Button[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {

                Button button = new Button();
                button.setPrefHeight(buttonSize);
                button.setPrefWidth(buttonSize);

                button.setOnAction(boardController);
                buttons[i][j] = button;

                String backgroundColor;
                if(possibleMoves[i][j]) {
                    backgroundColor = "#B19C2B";
                }
                else if((i+j) % 2 == 0) {
                    backgroundColor = "#E3C193";
                }
                else {
                    backgroundColor = "#BD7A44";
                }

                button.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 0; -fx-background-insets: 0 0 -1 0, 0, 1, 2;", backgroundColor));

                if(pieces[i][j] != null) {
                    Circle circle = new Circle(pieceSize);
                    if(pieces[i][j].getColor() == CheckerColor.WHITE)
                        circle.setFill(Paint.valueOf("#FFF"));
                    else
                        circle.setFill(Paint.valueOf("#000"));

                    circle.setCenterX(buttonSize / 2.0);
                    circle.setCenterY(buttonSize / 2.0);

                    circle.setOnMouseClicked(mouseEvent -> {button.fire();});

                    Group group = new Group(button, circle);

                    add(group, i, j);
                }
                else {
                    add(button, i, j);
                }
            }
        }
    }

    Button[][] getButtons() {
        return buttons;
    }
}
