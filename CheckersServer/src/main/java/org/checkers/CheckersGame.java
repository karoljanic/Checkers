package org.checkers;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import org.checkers.boards.Board;
import org.checkers.boards.elements.Point;
import org.checkers.boards.elements.Piece.Color;

public class CheckersGame extends Thread {

    private enum MoveStatus {SUCCESS, ERROR}

    private final Socket player1;
    private final Socket player2;
    private final Board board;

    private int turn;
    private static final int FIRST = 1;
    private static final int SECOND = 2;

    public CheckersGame(Socket player1, Socket player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        turn = FIRST;
    }

    @Override
    public void run() {
        try {
            InputStream input1 = player1.getInputStream();
            ObjectInputStream in1 = new ObjectInputStream(input1);
            OutputStream output1 = player2.getOutputStream();
            ObjectOutputStream out1 = new ObjectOutputStream(output1);

            InputStream input2 = player1.getInputStream();
            ObjectInputStream in2 = new ObjectInputStream(input2);
            OutputStream output2 = player2.getOutputStream();
            ObjectOutputStream out2 = new ObjectOutputStream(output2);

            out1.writeObject(Board.SIZE);
            if (turn == FIRST) {
                out1.writeObject(Color.WHITE);
                out1.writeObject(board.getPieces());
                out2.writeObject(Color.BLACK);
                out2.writeObject(board.getPieces());
            }
            else {
                out2.writeObject(Color.WHITE);
                out2.writeObject(board.getPieces());
                out1.writeObject(Color.BLACK);
                out1.writeObject(board.getPieces());
            }
    
            gameLoop(in1, out1, in2, out2);

            player1.close();
            player2.close();

            System.out.println("Game finished");
        }
        catch (final IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void gameLoop(ObjectInputStream in1, ObjectOutputStream out1, ObjectInputStream in2, ObjectOutputStream out2)
    throws IOException {
        
        while (true) { //TODO: update connection finishing condition

            Color color = Color.WHITE;
            ObjectOutputStream out = out1;
            ObjectInputStream in = in1;
            if (turn == SECOND) {
                color = Color.BLACK;
                out = out2;
                in = in2;
            }

            out.writeObject(board.getPossibleMoves(color));

            ArrayList<Point> movePoints = new ArrayList<>();
            try { movePoints = (ArrayList<Point>) in.readObject(); }
            catch (ClassNotFoundException e) {}

            while (!board.move(movePoints, color)) {
                out.writeObject(MoveStatus.ERROR);

                try { movePoints = (ArrayList<Point>) in.readObject(); }
                catch (ClassNotFoundException e) {}
            }
                
            out.writeObject(MoveStatus.SUCCESS);
            
            if (turn == FIRST)
                turn = SECOND;
            else
                turn = FIRST;

            //TODO: check if game has finished
        }

    }

}