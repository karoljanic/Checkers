package org.checkers;

import java.io.*;
import java.net.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import org.checkers.boards.Board;
import org.checkers.boards.Piece;
import org.checkers.utils.CheckerColor;
import org.checkers.utils.GameStatus;

import javax.sound.midi.Soundbank;

public class CheckersGame implements Runnable {

    private enum MoveStatus { SUCCESS, ERROR }

    private final Socket player1;
    private final Socket player2;
    private final Board board;

    private GameStatus turn;

    public CheckersGame(Socket player1, Socket player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        turn = GameStatus.FIRST;
    }

    @Override
    public void run() {
        try {
            PrintWriter out1 = new PrintWriter(player1.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));

            PrintWriter out2 = new PrintWriter(player2.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));

            PrintWriter out, otherOut;
            BufferedReader in, otherIn;
            CheckerColor color;

            out1.println("set-id/" + CheckerColor.WHITE.ordinal() + "\n");
            out2.println("set-id/" + CheckerColor.BLACK.ordinal() + "\n");

            out1.write("init-board" + prepareBoardDescriptionForPlayer1() + "\n");
            out2.write("init-board" + prepareBoardDescriptionForPlayer2() + "\n");

            out1.write("possible-moves" + preparePossibleMovesForPlayer1() + "\n");
            out2.write("possible-moves" + preparePossibleMovesForPlayer2() + "\n");

            int playerIdWithMove;
            while(turn != GameStatus.FINISHED) {
                if(turn == GameStatus.FIRST) {
                    color = CheckerColor.WHITE;
                    out = out1;
                    otherOut = out2;
                    in = in1;
                    otherIn = in2;
                    playerIdWithMove = 0;
                }
                else { //turn == GameStatus.SECOND
                    color = CheckerColor.BLACK;
                    out = out2;
                    otherOut = out1;
                    in = in2;
                    otherIn = in1;
                    playerIdWithMove = 1;
                }

                out.println("move-now/" + playerIdWithMove + "\n");
                otherOut.println("move-now/" + playerIdWithMove + "\n");

                String request = in.readLine();

                String[] tokens = request.split("/");
                System.out.println(Arrays.toString(tokens));

                if(tokens[0].equals("move")) {
                    int x1 = Integer.parseInt(tokens[1]);
                    int y1 = Integer.parseInt(tokens[2]);
                    int x2 = Integer.parseInt(tokens[3]);
                    int y2 = Integer.parseInt(tokens[4]);

                    if(!board.moveIsCorrect(x1, y1, x2, y2)) {
                        out.println("bad-move");
                    }
                    else {
                        if (turn == GameStatus.FIRST)
                            board.move(x1, y1, x2, y2, color);
                        else
                            board.move(x1, board.getSize() - 1 - y1, x2, board.getSize() - 1 - y2, color);

                        out.println("update-piece-position/" + x1 + "/" + y1 + "/" + x2 + "/" + y2);
                        otherOut.println("update-piece-position/" + x1 + "/" + (board.getSize() - 1 - y1) + "/" + x2 + "/" + (board.getSize() - 1 - y2));

                        board.generatePossibleMoves();
                        out1.write("possible-moves" + preparePossibleMovesForPlayer1() + "\n");
                        out2.write("possible-moves" + preparePossibleMovesForPlayer2() + "\n");

                        if (turn == GameStatus.FIRST)
                            turn = GameStatus.SECOND;
                        else
                            turn = GameStatus.FIRST;
                    }
                }

                //TODO: check if game has finished
            }

            player1.close();
            player2.close();

            System.out.println("Game finished");
        }
        catch (final IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String prepareBoardDescriptionForPlayer1() {
        ArrayList<Piece> whitePieces = board.getPieces(CheckerColor.WHITE);
        ArrayList<Piece> blackPieces = board.getPieces(CheckerColor.BLACK);

        StringBuilder result = new StringBuilder("/" + board.getSize() + "/" + whitePieces.size());
        for(Piece piece: whitePieces) {
            result.append("/").append(piece.getX()).append("/").append(piece.getY());
        }

        for(Piece piece: blackPieces) {
            result.append("/").append(piece.getX()).append("/").append(piece.getY());
        }

        return result.toString();
    }

    private String prepareBoardDescriptionForPlayer2() {
        ArrayList<Piece> whitePieces = board.getPieces(CheckerColor.WHITE);
        ArrayList<Piece> blackPieces = board.getPieces(CheckerColor.BLACK);

        StringBuilder result = new StringBuilder("/" + board.getSize() + "/" + whitePieces.size());
        for(Piece piece: whitePieces) {
            result.append("/").append(piece.getX()).append("/").append(board.getSize() - 1 - piece.getY());
        }

        for(Piece piece: blackPieces) {
            result.append("/").append(piece.getX()).append("/").append(board.getSize() - 1 - piece.getY());
        }

        //return "8/4/1/7/3/7/5/7/7/7/0/0/2/0/4/0/6/0";
        return result.toString();
    }

    private String preparePossibleMovesForPlayer1() {
        StringBuilder result = new StringBuilder();
        Board.PiecesArray[][] possibleMoves = board.getPossibleMoves();
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                for(Piece piece: possibleMoves[i][j].getList()) {
                    if(piece.getColor() == CheckerColor.WHITE) {
                        result.append("/").append(i).append("/").append(j).append("/").append(piece.getX()).append("/").append(piece.getY());
                    }
                }
            }
        }

        //return "0/7/1/6";
        return result.toString();
    }

    private String preparePossibleMovesForPlayer2() {
        StringBuilder result = new StringBuilder();
        Board.PiecesArray[][] possibleMoves = board.getPossibleMoves();
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                for(Piece piece: possibleMoves[i][j].getList()) {
                    if(piece.getColor() == CheckerColor.BLACK) {
                        result.append("/").append(i).append("/").append(board.getSize() - 1 - j).append("/").append(piece.getX()).append("/").append(board.getSize() - 1 - piece.getY());
                    }
                }
            }
        }

        //return "1/7/0/6/1/7/2/6";
        return result.toString();
    }
}