package org.checkers.board;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.checkers.menu.Menu;
import org.checkers.menu.MenuView;
import org.checkers.utils.WindowProperties;

public class BoardController {
    private final Board model;
    private BoardView view;

    private Stage stage;

    public BoardController(Stage stage) {
        //stage = new Stage();
        this.stage = stage;
        model = new Board();
//        view = new BoardView();
    }

    public void setSize(int n) { model.setSize(n); }

    public void setWhitePiece(int x, int y) {
        model.setWhitePiece(x, y);
    }

    public void removeWhitePiece(int x, int y) {
        model.removeWhitePiece(x, y);
    }

    public void setBlackPiece(int x, int y) {
        model.setBlackPiece(x, y);
    }

    public void removeBlackPiece(int x, int y) {
        model.removeBlackPiece(x, y);
    }

    public void showView() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            view = new BoardView(model.getSize(), model.getWhitePieces(), model.getBlackPieces(), this);

            stage.setScene(new Scene(view));
            stage.hide();
            stage.show();

            stage.setResizable(false);
        });
    }

}
