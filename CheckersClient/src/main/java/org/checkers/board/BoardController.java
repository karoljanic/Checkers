package org.checkers.board;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.checkers.utils.WindowProperties;

public class BoardController {
    private final Board model;
    private final BoardView view;

    private Stage stage = null;

    public BoardController(Board boardModel, BoardView boardView) {
        model = boardModel;
        view = boardView;
    }

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

    public void updateView() {
        if(stage == null) {
            stage = new Stage();
            stage.setScene(view.getBoardView(model.getSize(), model.getWhitePieces(), model.getBlackPieces()));
            stage.setTitle("Checkers");
            stage.setResizable(true);

            stage.show();
            stage.setMaxHeight(stage.getHeight());
            stage.setMaxWidth(stage.getWidth());
        }
        else {
            stage.hide();
            stage.setScene(view.getBoardView(model.getSize(), model.getWhitePieces(), model.getBlackPieces()));
            stage.show();
        }
    }
}
