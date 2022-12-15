package org.checkers.board;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.checkers.menu.Menu;
import org.checkers.menu.MenuView;
import org.checkers.utils.WindowProperties;

public class BoardController {
    private final Board model;
    private final BoardView view;

    private Stage stage;

    public BoardController(Stage stage) {
        stage = new Stage();
        //this.stage = stage;
        model = new Board();
        view = new BoardView(this);
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage sstage = new Stage();
                sstage.setScene(new Scene(view));
                sstage.show();
                sstage.setResizable(false);
                sstage.hide();
                sstage.show();
                System.out.println("BOARD ID VISIBLE!");
            }
        });
    }

    //public void updateView() {
    //    view.update(model.getSize(), model.getWhitePieces(), model.getBlackPieces());
    //}
}
