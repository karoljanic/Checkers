package org.checkers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.checkers.board.BoardController;
import org.checkers.menu.MenuController;
import org.checkers.server.CheckersServer;
import org.checkers.server.ServerService;
import org.checkers.utils.GameType;

import java.util.ArrayList;;

/**
 * klasa nadzoruje przebieg gry w warcaby
 */
public class Game extends Application implements Runnable {
    /**
     * instancja klasy do obługi Menu
     */
    private MenuController menuController;
    /**
     * instancja klasy do obsługi planszy do gry
     */
    private BoardController boardController;

    /**
     * 0, jeśli gracz ma białe pionki i 1, jeśli czarne
     */
    private int playerId;
    /**
     * true, jeśli gra powinna dalej trwać lub false, jeśli się zakończy
     */
    private boolean keepWorking;

    /**
     * komunikat konca gry
     */
    private String endGameMessage;


    /* (non-Javadoc)
     * @see javafx.application.Application#init()
     */
    @Override
    public void init() { }

    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
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

    /* (non-Javadoc)
     * @see javafx.application.Application#stop()
     */
    @Override
    public void stop() {
        keepWorking = false;
        ServerService.closeConnection();
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
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
                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bad move!");
                                    alert.showAndWait();
                                });
                                break;
                            case "history":
                                StringBuilder content = new StringBuilder("Saved games: \n");
                                for(int i = 1; i < tokens.length; i += 3) {
                                    content.append(tokens[i]).append(": ").append(GameType.INTERNATIONAL).append("  -  ").append(tokens[i+2]).append("\n");
                                }
                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION, content.toString());
                                    alert.showAndWait();
                                });
                                break;
                            default:
                                if (tokens[0].equals("end"))
                                    endGameMessage = "End of replay!";
                                else if (tokens[0].equals("draw"))
                                    endGameMessage = "Draw!";
                                else if ((tokens[0].equals("black") && playerId == 1) || (tokens[0].equals("white") && playerId == 0)) {
                                    endGameMessage = "You win - " + tokens[0] + " win!";
                                }
                                else
                                    endGameMessage = "You loose - " + tokens[0] + " win!";

                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION, endGameMessage);
                                    alert.setOnCloseRequest(event -> System.exit(0));
                                    alert.showAndWait();
                                });

                                Platform.runLater(() -> boardController.closeStage());
                                Platform.runLater(() -> menuController.closeStage());

                                stop();
                        }
                    }
                }

                notifyAll();
            }
        }
    }
    /**
     * funkcja główna w programie uruchamia aplikację okienkową
     */
    public static void main(String[] args) { launch(args); }
}