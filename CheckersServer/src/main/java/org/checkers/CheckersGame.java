package org.checkers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.checkers.boards.Board;
import org.checkers.piece.coordinate.Coordinate;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;
import org.checkers.utils.CheckerColor;
import org.checkers.utils.CustomClock;
import org.checkers.utils.GameStatus;


public class CheckersGame implements Runnable {
    private final Socket player1;
    private final Socket player2;
    private final Board board;
    private GameStatus gameStatus;

    public CheckersGame(Socket player1, Socket player2, Board board) {
        boolean changeOrder = new Random(System.currentTimeMillis()).nextBoolean();
        if(changeOrder) {
            this.player1 = player2;
            this.player2 = player1;
        }
        else {
            this.player1 = player1;
            this.player2 = player2;
        }

        this.board = board;
        gameStatus = GameStatus.WHITE_TURN;
    }

    @Override
    public void run() {
        try {
            board.generatePossibleMoves();

            PrintWriter out1 = new PrintWriter(player1.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));

            PrintWriter out2 = new PrintWriter(player2.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));

            out1.println("set-id/" + CheckerColor.WHITE.ordinal());
            out2.println("set-id/" + CheckerColor.BLACK.ordinal());

            out1.println("init-board" + prepareBoardDescription());
            out2.println("init-board" + prepareBoardDescription());

            out1.println("possible-moves" + preparePossibleMoves(CheckerColor.WHITE));
            out2.println("possible-moves" + preparePossibleMoves(CheckerColor.BLACK));

            PrintWriter out, otherOut;
            BufferedReader in;
            int playerIdWithMove;
            CheckerColor color, otherColor;

            while(gameStatus != GameStatus.FINISHED) {
                if (gameStatus == GameStatus.WHITE_TURN) {
                    color = CheckerColor.WHITE;
                    otherColor = CheckerColor.BLACK;
                    out = out1;
                    otherOut = out2;
                    in = in1;
                    playerIdWithMove = 0;
                }
                else { //turn == GameStatus.SECOND
                    color = CheckerColor.BLACK;
                    otherColor = CheckerColor.WHITE;
                    out = out2;
                    otherOut = out1;
                    in = in2;
                    playerIdWithMove = 1;
                }

                out.println("move-now/" + playerIdWithMove);
                otherOut.println("move-now/" + playerIdWithMove);

                String request = in.readLine();

                String[] tokens = request.split("/");
                System.out.println(Arrays.toString(tokens));

                if (tokens[0].equals("move")) {
                    int x1 = Integer.parseInt(tokens[1]);
                    int y1 = Integer.parseInt(tokens[2]);
                    int x2 = Integer.parseInt(tokens[3]);
                    int y2 = Integer.parseInt(tokens[4]);

                    if (!board.moveIsCorrect(x1, y1, x2, y2, color)) {
                        out.println("bad-move");
                    }
                    else {
                        board.move(x1, y1, x2, y2, color);

                        CoordinatesArray tmp = board.getPossibleMove(x1, y1, x2, y2, color);
                        CoordinatesArray path = new CoordinatesArray();
                        path.add(x1, y1);
                        for(Coordinate coordinate: tmp.getList())
                            path.add(coordinate.getX(), coordinate.getY());

                        for(int i = 1; i < path.size(); i++) {
                            Coordinate last = path.getList().get(i - 1);
                            Coordinate now = path.getList().get(i);

                            out.println("update-piece-position/" + last.getX() + "/" + last.getY() + "/" + now.getX() + "/" + now.getY());
                            otherOut.println("update-piece-position/" + last.getX() + "/" + last.getY() + "/" + now.getX() + "/" + now.getY());

                            int dx = now.getX() > last.getX() ? 1 : -1;
                            int dy = now.getY() > last.getY() ? 1 : -1;
                            int steps = Math.abs(now.getX() - last.getX());

                            CustomClock.waitMillis(200);

                            for(int x = last.getX(), y = last.getY(), iter = 0; iter < steps; iter++, x += dx, y += dy) {
                                if(board.coordinateIsWithPiece(x, y, otherColor)) {
                                    board.removePiece(x, y);
                                    out.println("remove-piece/" + x + "/" + y);
                                    otherOut.println("remove-piece/" + x + "/" + y);
                                }
                            }

                            if(i != path.size() - 1)
                                CustomClock.waitMillis(300);
                        }

                        if(board.isKing(x2, y2)) {
                            out.println("update-piece-to-king/" + x2 + "/" + y2);
                            otherOut.println("update-piece-to-king/" + x2 + "/" + y2);
                        }

                        board.generatePossibleMoves();
                        out1.println("possible-moves" + preparePossibleMoves(CheckerColor.WHITE));
                        out2.println("possible-moves" + preparePossibleMoves(CheckerColor.BLACK));

                        if (gameStatus == GameStatus.WHITE_TURN)
                            gameStatus = GameStatus.BLACK_TURN;
                        else
                            gameStatus = GameStatus.WHITE_TURN;
                    }
                }
            }

            player1.close();
            player2.close();

            System.out.println("Game finished");
        }
        catch(Exception ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String prepareBoardDescription() {
        CoordinatesArray whitePieces = board.getPieces(CheckerColor.WHITE);
        CoordinatesArray blackPieces = board.getPieces(CheckerColor.BLACK);

        StringBuilder result = new StringBuilder("/" + board.getSize() + "/" + whitePieces.size());
        for(Coordinate coordinate: whitePieces.getList()) {
            result.append("/").append(coordinate.getX()).append("/").append(coordinate.getY());
        }

        for(Coordinate coordinate: blackPieces.getList()) {
            result.append("/").append(coordinate.getX()).append("/").append(coordinate.getY());
        }

        return result.toString();
    }

    private String preparePossibleMoves(CheckerColor checkerColor) {
        PathsArray[][] possibleMoves = board.getPossibleMoves(checkerColor);

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                for(CoordinatesArray coordinatesArray: possibleMoves[i][j].getList()) {
                    result.append("/").append(i).append("/").append(j);
                    for(Coordinate coordinate: coordinatesArray.getList()) {
                        result.append("/").append(coordinate.getX()).append("/").append(coordinate.getY());
                    }
                    result.append("/;");
                }
            }
        }

        return result.toString();
    }

}