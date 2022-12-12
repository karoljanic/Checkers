package org.checkers;

import javafx.application.Application;
import javafx.stage.Stage;
import org.checkers.board.Board;
import org.checkers.board.BoardController;
import org.checkers.board.BoardView;
import org.checkers.menu.Menu;
import org.checkers.menu.MenuController;
import org.checkers.menu.MenuView;

public class Main extends Application {

    @Override
    public void init() { }

    @Override
    public void start(Stage primaryStage) {
        Board boardModel = new Board();
        boardModel.setSize(10);

        boardModel.setWhitePiece(2, 2);
        boardModel.setWhitePiece(3, 1);
        boardModel.setWhitePiece(8, 5);

        boardModel.setBlackPiece(7, 1);
        boardModel.setBlackPiece(9, 6);
        boardModel.setBlackPiece(4, 4);

        BoardView boardView = new BoardView();
        BoardController boardController = new BoardController(boardModel, boardView);

        boardController.updateView();

        /*
        Menu menuModel = new Menu();
        menuModel.setCheckersTypes(new String[]{"International Checkers", "Brazilian Checkers", "Thain Checkers"});

        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuModel, menuView);

        menuController.updateView();
         */
    }

    @Override
    public void stop() { }
    public static void main(String[] args) { launch(args); }
}