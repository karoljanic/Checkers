package org.checkers;

import javafx.application.Application;
import javafx.stage.Stage;
import org.checkers.board.BoardController;
import org.checkers.menu.MenuController;
import org.checkers.server.CheckersServer;
import org.checkers.server.ServerService;

import java.util.Arrays;

public class Game extends Application implements Runnable {

    private MenuController menuController;
    private BoardController boardController;

    private int playerId;
    @Override
    public void init() { }

    @Override
    public void start(Stage primaryStage) {
        menuController = new MenuController(primaryStage);
        boardController = new BoardController(primaryStage);

        menuController.showView();

        ServerService.initializeService(new CheckersServer());

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void stop() { }

    @Override
    public void run() {
        while(true) {
            synchronized (this) {
                try {
                    wait(100);
                }
                catch (InterruptedException ignored) { }

                String command = ServerService.getInput();
                if(command != null) {
                    String[] tokens = command.split("/");
                    //System.out.println(command);
                    System.out.println(Arrays.toString(tokens));

                    if(tokens.length > 0) {
                        switch (tokens[0]) {
                            case "set-id":
                                playerId = Integer.parseInt(tokens[1]);
                                break;
                            case "init-board":
                                int size = Integer.parseInt(tokens[1]);
                                int piecesNumber = Integer.parseInt(tokens[2]);

                                boardController.setSize(size);

                                for (int i = 0; i < piecesNumber; i++) {
                                    int pieceX = Integer.parseInt(tokens[3 + 2 * i]);
                                    int pieceY = Integer.parseInt(tokens[3 + 2 * i + 1]);

                                    boardController.setWhitePiece(pieceX, pieceY);
                                }

                                for (int i = 0; i < piecesNumber; i++) {
                                    int pieceX = Integer.parseInt(tokens[3 + 2 * piecesNumber + 2 * i]);
                                    int pieceY = Integer.parseInt(tokens[3 + 2 * piecesNumber + 2 * i + 1]);

                                    boardController.setBlackPiece(pieceX, pieceY);
                                }

                                boardController.showView();
                                break;
                            case "possible-moves":
                                for (int i = 0; i < (tokens.length - 1) / 4; i++) {
                                    int oldX = Integer.parseInt(tokens[4 * i + 1]);
                                    int oldY = Integer.parseInt(tokens[4 * i + 2]);
                                    int newX = Integer.parseInt(tokens[4 * i + 3]);
                                    int newY = Integer.parseInt(tokens[4 * i + 4]);

                                    boardController.setPossibleMoves(oldX, oldY, newX, newY);
                                }
                                break;
                            case "update-piece-position":
                                int oldX = Integer.parseInt(tokens[1]);
                                int oldY = Integer.parseInt(tokens[2]);
                                int newX = Integer.parseInt(tokens[3]);
                                int newY = Integer.parseInt(tokens[4]);

                                boardController.updatePiecePosition(oldX, oldY, newX, newY);
                                break;
                            case "remove-piece":
                                int x = Integer.parseInt(tokens[1]);
                                int y = Integer.parseInt(tokens[2]);

                                boardController.removePiece(x, y);
                                break;
                            case "move-now":
                                int id = Integer.parseInt(tokens[1]);

                                boardController.setMoveAvailable(id == playerId);
                                break;
                            case "update-piece-to-king":
                                int posX = Integer.parseInt(tokens[1]);
                                int posY = Integer.parseInt(tokens[2]);

                                boardController.makeKing(posX, posY);
                                break;
                            case "bad-move":
                                System.out.println("Bad Move");
                                break;
                        }
                    }
                }

                notifyAll();
            }
        }
    }
    public static void main(String[] args) { launch(args); }
}