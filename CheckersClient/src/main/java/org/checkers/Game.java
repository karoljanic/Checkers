package org.checkers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.checkers.board.BoardController;
import org.checkers.menu.MenuController;
import org.checkers.server.CheckersServer;
import org.checkers.server.ServerService;

import java.util.Arrays;

public class Game extends Application implements Runnable {

    private MenuController menuController;
    private BoardController boardController;

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
                    wait(1000);
                }
                catch (InterruptedException ignore) { }

                String command = ServerService.getInput();
                if(command != null) {
                    String[] tokens = command.split("/");
                    System.out.println(command);
                    System.out.println(Arrays.toString(tokens));
                    if(tokens.length > 0) {
                        if(tokens[0].equals("set-id")) {
                            int id = Integer.parseInt(tokens[1]);
                            System.out.println("Ustawienie ID = " + id);
                        }
                        else if(tokens[0].equals("init-board")) {
                            int size = Integer.parseInt(tokens[1]);
                            int piecesNumber = Integer.parseInt(tokens[2]);

                            System.out.println("Rozmiar planszy: " + size);

                            boardController.setSize(size);

                            for(int i = 0; i < piecesNumber; i++) {
                                int pieceX = Integer.parseInt(tokens[3 + 2 * i]);
                                int pieceY = Integer.parseInt(tokens[3 + 2 * i + 1]);

                                boardController.setWhitePiece(pieceX, pieceY);

                                System.out.println("Pionek na polu: (" + pieceX + ", " + pieceY + ")");
                            }

                            for(int i = 0; i < piecesNumber; i++) {
                                int pieceX = Integer.parseInt(tokens[3 + 2 * piecesNumber + 2 * i]);
                                int pieceY = Integer.parseInt(tokens[3 + 2 * piecesNumber + 2 * i + 1]);

                                boardController.setBlackPiece(pieceX, pieceY);

                                System.out.println("Pionek na polu: (" + pieceX + ", " + pieceY +  ")");
                            }

                            boardController.showView();
                        }
                    }
                }

                notifyAll();
            }
        }
    }
    public static void main(String[] args) { launch(args); }
}