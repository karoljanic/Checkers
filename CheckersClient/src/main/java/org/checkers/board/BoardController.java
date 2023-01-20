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

/**
 * klasa obsługuję klasę Board
 */
public class BoardController implements EventHandler<ActionEvent> {
    /**
     * instancja klasy Board
     */
    private final Board model;
    /**
     * instancja klasy BoardView do wyświetlania na ekranie
     */
    private BoardView view;
    /**
     * możliwe ruchy dla wszystkich pionków
     */
    private ArrayList<ArrayList<Pair<Integer, Integer>>> possibleMovesInNextStep;
    /**
     * akrualnie wybrany pionek
     */
    private Pair<Integer, Integer> chosenPiece;
    /**
     * instancja klasy Stage do wyświetlania zmian
     */
    private Stage stage;
    /**
     * true, jeśli teraz jest kolej gracza
     */
    private boolean moveAvailable;

    /**
     * @param stage instancja klasy Stage do wyświetlania zmian
     * konstruktor ustawia niezbędne parametry nowego obiektu
     */
    public BoardController(Stage stage) {
        //stage = new Stage();
        this.stage = stage;
        model = new Board();
        //view = new BoardView();
        possibleMovesInNextStep = new ArrayList<>();
        chosenPiece = null;
        moveAvailable = false;
    }

     /**
     * @param isHost informacja o kolorze pionków gracza
     * funkcja ustawia flagę isHost w klasie Board
     */
    public void setHost(boolean isHost) { model.setHost(isHost); }

    /**
     * @param n nowy rozmiar planszy
     * funkcja ustawia rozmiar planszy
     */
    public void setSize(int n) { model.setSize(n); }

    /**
     * @param x x-owa współrzędna nowego pionka
     * @param y y-owa współrzędna nowego pionka
     * funkcja dodaje nowy pionek białego koloru
     */
    public void setWhitePiece(int x, int y) {
        model.setWhitePiece(x, y);
    }

    /**
     * @param x x-owa współrzędna nowego pionka
     * @param y y-owa współrzędna nowego pionka
     * funkcja dodaje nowy pionek czarnego koloru
     */
    public void setBlackPiece(int x, int y) {
        model.setBlackPiece(x, y);
    }

    /**
     * @param x x-owa współrzędna pionka do usunięcia
     * @param y y-owa współrzędna pionka do usunięcia
     * funkcja usuwa pionek o podanych współrzędnych
     */
    public void removePiece(int x, int y) {
        model.removePiece(x, y);
        showView();
    }

    /**
     * funkcja usuwa wszystkie możliwe ruchy
     */
    public void resetAllPossibleMoves() {
        for(int i = 0; i < model.getSize(); i++) {
            for(int j = 0; j < model.getSize(); j++) {
                model.removePossibleMoves(i, j);
            }
        }
    }

    /**
     * @param fromX x-owa współrzędna pionka
     * @param fromY y-owa współrzędna pionka
     * @param possibleMoves możliwe ruchy dla danego pionka
     * funkcja ustawia możliwego ruchy dla podanego pionka
     */
    public void setPossibleMoves(int fromX, int fromY, ArrayList<Pair<Integer, Integer>> possibleMoves) {
        model.addPossibleMove(fromX, fromY, possibleMoves);
    }

    /**
     * @param oldX x-owa współrzędna pionka
     * @param oldY y-owa współrzędna pionka
     * @param newX nowa x-owa współrzędna pionka
     * @param newY nowa y-owa współrzędna pionka
     * funkcja zmienia położenie pionka
     */
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

    /**
     * @param availability nowa flaga możliwości ruchu
     * funkcja zmienia flagę możliwości ruchu
     */
    public void setMoveAvailable(boolean availability) {
        moveAvailable = availability;
        showView();
    }

    /**
     * @param x x-owa współrzędna pionka
     * @param y y-owa współrzędna pionka
     * funkcja zmienia podany pionek na damkę
     */
    public void makeKing(int x, int y) {
        model.makeKing(x, y);
    }

    /**
     * funkcja pokazuje planszę użytkownikowi
     */
    public void showView() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            view = new BoardView(model.getSize(), model.getPieces(), new short[model.getSize()][model.getSize()], moveAvailable, model.isHost(), this);

            showStage();
        });
    }

    /* (non-Javadoc)
     * @see javafx.event.EventHandler#handle(javafx.event.Event)
     */
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
                    short[][] possibleMoves = new short[model.getSize()][model.getSize()];
                    boolean found = false;

                    for(ArrayList<Pair<Integer, Integer>> path: possibleMovesInNextStep) {
                        Pair<Integer, Integer> step = path.get(path.size() - 1);
                            if(step.getKey() == i && step.getValue() == j) {
                                ServerService.sendPlayerMove(chosenPiece.getKey(), chosenPiece.getValue(), i, j);
                                possibleMovesInNextStep = new ArrayList<>();
                                found = true;
                                break;
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
                                    if (path.indexOf(step) == path.size() - 1)
                                        possibleMoves[step.getKey()][step.getValue()] = 2;
                                    else if (possibleMoves[step.getKey()][step.getValue()] != 2)
                                        possibleMoves[step.getKey()][step.getValue()] = 1;
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

    /**
     * funkcja otwiera okno gry
     */
    private void showStage() {
        stage.setScene(new Scene(view));
        stage.show();

        stage.setResizable(false);
    }

    /**
     * funkcja zamyka okno gry
     */
    public void closeStage() {
        stage.close();
    }

    /**
     * @return instancja klasy Stage do pokazywania planszy
     */
    public Stage getStage() {
        return stage;
    }
}
