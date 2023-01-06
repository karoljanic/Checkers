package org.checkers.board;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.checkers.server.ServerService;
import org.checkers.utils.CheckerColor;
import java.util.ArrayList;

public class BoardController implements EventHandler<ActionEvent> {
    private final Board model;
    private BoardView view;

    private ArrayList<ArrayList<Pair<Integer, Integer>>> possibleMovesInNextStep;
    private Pair<Integer, Integer> chosenPiece;

    private Stage stage;

    private boolean moveAvailable;

    public BoardController(Stage stage) {
        //stage = new Stage();
        this.stage = stage;
        model = new Board();
//        view = new BoardView();
        possibleMovesInNextStep = new ArrayList<>();
        chosenPiece = null;
        moveAvailable = false;
    }

    public void setHost(boolean isHost) { model.setHost(isHost); }

    public void setSize(int n) { model.setSize(n); }

    public void setWhitePiece(int x, int y) {
        model.setWhitePiece(x, y);
    }

    public void setBlackPiece(int x, int y) {
        model.setBlackPiece(x, y);
    }

    public void removePiece(int x, int y) {
        model.removePiece(x, y);
    }

    public void resetAllPossibleMoves() {
        for(int i = 0; i < model.getSize(); i++) {
            for(int j = 0; j < model.getSize(); j++) {
                model.removePossibleMoves(i, j);
            }
        }
    }

    public void setPossibleMoves(int fromX, int fromY, ArrayList<Pair<Integer, Integer>> possibleMoves) {
        model.addPossibleMove(fromX, fromY, possibleMoves);
    }

    public void updatePiecePosition(int oldX, int oldY, int newX, int newY) {
        if(model.getCheckerColor(oldX, oldY) == CheckerColor.BLACK) {
            model.setBlackPiece(newX, newY);
        }
        else {
            model.setWhitePiece(newX, newY);
        }

        model.removePiece(oldX, oldY);

        showView();
    }

    public void setMoveAvailable(boolean availability) {
        moveAvailable = availability;
        showView();
    }

    public void makeKing(int x, int y) {
        model.makeKing(x, y);
    }

    public void showView() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            view = new BoardView(model.getSize(), model.getPieces(), new boolean[model.getSize()][model.getSize()], moveAvailable, model.isHost(), this);

            showStage();
        });
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if(!moveAvailable) {
            return;
        }

        Object source = actionEvent.getSource();
        Button[][] buttons = view.getButtons();

        for(int i = 0; i < model.getSize(); i++) {
            for(int j = 0; j < model.getSize(); j++) {
                if(source.equals(buttons[i][j])) {
                    Piece[][] pieces = model.getPieces();
                    boolean[][] possibleMoves = new boolean[model.getSize()][model.getSize()];
                    boolean found = false;

                    for(ArrayList<Pair<Integer, Integer>> path: possibleMovesInNextStep) {
                        for(Pair<Integer, Integer> step : path) {
                            if(step.getKey() == i && step.getValue() == j) {
                                ServerService.sendPlayerMove(chosenPiece.getKey(), chosenPiece.getValue(), i, j);
                                possibleMovesInNextStep = new ArrayList<>();
                                found = true;
                                break;
                            }
                        }
                        if(found)
                            break;
                    }

                    if(!found) {
                        if(pieces[i][j] != null) {
                            possibleMovesInNextStep = pieces[i][j].getPossibleMoves();
                            chosenPiece = new Pair<>(i, j);
                            for(ArrayList<Pair<Integer, Integer>> path: possibleMovesInNextStep) {
                                for(Pair<Integer, Integer> step: path) {
                                    possibleMoves[step.getKey()][step.getValue()] = true;
                                }
                            }
                        }
                    }

                    view = new BoardView(model.getSize(), model.getPieces(), possibleMoves, moveAvailable, model.isHost(), this);
                    showStage();

                    return;
                }
            }
        }
    }

    private void showStage() {
        stage.setScene(new Scene(view));
        stage.show();

        stage.setResizable(false);
    }

    public void closeStage() {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }
}
