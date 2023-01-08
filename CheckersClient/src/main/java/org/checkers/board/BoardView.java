package org.checkers.board;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import javafx.scene.shape.Polyline;
import org.checkers.utils.CheckerColor;
import org.checkers.utils.WindowProperties;

/**
 * klasa obsługuje wyświetlanie planszy do gry
 */
public class BoardView extends GridPane {
    /**
     * pola do gry
     */
    private final Button[][] buttons;
    /**
     * obiekt do obługi kliknięcia na pole
     */
    private final BoardController boardController;

    /**
     * @param size rozmiar planszy
     * @param pieces pionki na planszy
     * @param possibleMoves możliwe ruchy dla pionków
     * @param isPlayerTurn true, jeśli gracz się teraz powinien ruszyć
     * @param isHost true, jeśli gracz ma białe pionki
     * @param boardController obiekt do obsługi kliknięcia na pole
     * konstruktor ustawia niezbędne parametry nowego obiektu i ustawia widok planszy
     */
    BoardView(int size, Piece[][] pieces, short[][] possibleMoves, boolean isPlayerTurn, boolean isHost, BoardController boardController) {
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

                String backgroundColor = "#BD7A44";
                if(possibleMoves[i][j] == 2) {
                    backgroundColor = "#B19C2B";
                }
                else if (possibleMoves[i][j] == 1) {
                    backgroundColor = "#DB2727";
                }
                else if((i+j) % 2 == 0) {
                    backgroundColor = "#E3C193";
                }

                button.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 0; -fx-background-insets: 0 0 -1 0, 0, 1, 2;", backgroundColor));

                if(pieces[i][j] != null) {
                    Circle circle = new Circle(pieceSize);
                    circle.setCenterX(buttonSize / 2.0);
                    circle.setCenterY(buttonSize / 2.0);

                    if(pieces[i][j].getColor() == CheckerColor.WHITE)
                        circle.setFill(Paint.valueOf("#FFF"));
                    else
                        circle.setFill(Paint.valueOf("#000"));

                    if(pieces[i][j].getPossibleMoves().size() > 0 && isPlayerTurn) {
                        circle.setStroke(Paint.valueOf("#89CE54"));
                        circle.setStrokeWidth(5);
                    }

                    circle.setOnMouseClicked(mouseEvent -> button.fire());

                    if(pieces[i][j].isKing()) {
                        double crownCenterX = buttonSize * 0.5;
                        double crownCenterY = buttonSize * 0.5;
                        double crownWidth = buttonSize * 0.6;
                        double crownHeight = buttonSize * 0.3;
                        Polyline crown = new Polyline();
                        crown.getPoints().addAll(
                                crownCenterX - crownWidth * 0.5, crownCenterY - crownHeight * 0.5,
                                crownCenterX + crownWidth * 0.5, crownCenterY - crownHeight * 0.5,
                                crownCenterX + crownWidth * 0.5, crownCenterY + crownHeight * 0.5,
                                crownCenterX + crownWidth * 0.25, crownCenterY,
                                crownCenterX, crownCenterY + crownHeight * 0.5,
                                crownCenterX - crownWidth * 0.25, crownCenterY,
                                crownCenterX - crownWidth * 0.5, crownCenterY + crownHeight * 0.5,
                                crownCenterX - crownWidth * 0.5, crownCenterY - crownHeight * 0.5);

                        if(pieces[i][j].getColor() == CheckerColor.WHITE)
                            crown.setStroke(Paint.valueOf("#000"));
                        else
                            crown.setStroke(Paint.valueOf("#FFF"));

                        if(!isHost) {
                            crown.setRotate(180);
                        }

                        Group group = new Group(button, circle, crown);
                        add(group, i, j);
                    }
                    else {
                        Group group = new Group(button, circle);
                        add(group, i, j);
                    }
                }
                else {
                    add(button, i, j);
                }
            }
        }

        if(isHost) {
            setRotate(180);
        }
    }

    /**
     * @return pola na planszy
     */
    Button[][] getButtons() {
        return buttons;
    }
}
