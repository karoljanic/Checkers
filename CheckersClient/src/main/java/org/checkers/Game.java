package org.checkers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.checkers.board.BoardController;
import org.checkers.menu.MenuController;
import org.checkers.server.CheckersServer;
import org.checkers.server.ServerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Game extends Application implements Runnable {

    private MenuController menuController;
    private BoardController boardController;

    private int playerId;
    private boolean keepWorking;

    @Override
    public void init() { }

    @Override
    public void start(Stage primaryStage) {
        menuController = new MenuController(primaryStage);
        boardController = new BoardController(primaryStage);
        keepWorking = true;

        menuController.showView();

        ServerService.initializeService(new CheckersServer());

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void stop() {
        keepWorking = false;
        ServerService.closeConnection();
        System.exit(0);
    }

    @Override
    public void run() {
        while(keepWorking) {
            synchronized (this) {
                try {
                    wait(100);
                }
                catch (InterruptedException ignored) { }

                String command = ServerService.getInput();
                if(command != null) {
                    String[] tokens = command.split("/");
                    System.out.println(command);
                    System.out.println(Arrays.toString(tokens));

                    if(tokens.length > 0) {
                        switch (tokens[0]) {
                            case "set-id":
                                playerId = Integer.parseInt(tokens[1]);
                                boardController.setHost(playerId == 0);
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
                                boardController.resetAllPossibleMoves();

                                ArrayList<Pair<Integer, Integer>> possibleMove = new ArrayList<>();
                                int iter = 1;
                                while(iter < tokens.length) {
                                    int x = Integer.parseInt(tokens[iter]);
                                    int y = Integer.parseInt(tokens[iter + 1]);
                                    possibleMove.add(new Pair<>(x, y));

                                    if(tokens[iter + 2].equals(";")) {
                                        int startX = possibleMove.get(0).getKey();
                                        int startY = possibleMove.get(0).getValue();
                                        possibleMove.remove(0);
                                        boardController.setPossibleMoves(startX, startY, possibleMove);
                                        possibleMove = new ArrayList<>();
                                        iter++;
                                    }
                                    iter += 2;
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
                            default:
                                String message = "";
                                if (tokens[0].equals("draw"))
                                    message = "Draw!";
                                else if ((tokens[0].equals("white") && playerId == 0) || (tokens[0].equals("black") && playerId == 1)) {
                                    message = "You win!";
                                }
                                else
                                    message = "You loose!";
                                Platform.runLater(() -> {boardController.closeStage();});
                                System.out.println(message);
                                stop();
                        }
                    }
                }

                notifyAll();
            }
        }
    }
    public static void main(String[] args) { launch(args); }
}