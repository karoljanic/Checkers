package org.checkers;

import java.io.*;
import java.net.*;

import org.checkers.boards.Board;

public class CheckersGame extends Thread {
    private final Socket player1;
    private final Socket player2;
    private final Board board;

    public CheckersGame(Socket player1, Socket player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }

    @Override
    public void run() {
        try {
            InputStream input1 = player1.getInputStream();
            BufferedReader in1 = new BufferedReader(new InputStreamReader(input1));
            ObjectInputStream objectIn1 = new ObjectInputStream(input1);
            OutputStream output1 = player2.getOutputStream();
            PrintWriter out1 = new PrintWriter(output1, true);
            ObjectOutputStream objectOut1 = new ObjectOutputStream(output1);

            InputStream input2 = player1.getInputStream();
            BufferedReader in2 = new BufferedReader(new InputStreamReader(input2));
            ObjectInputStream objectIn2 = new ObjectInputStream(input2);
            OutputStream output2 = player2.getOutputStream();
            PrintWriter out2 = new PrintWriter(output2, true);
            ObjectOutputStream objectOut2 = new ObjectOutputStream(output2);

            out1.println("white");
            objectOut1.writeObject(board.getPieces());
            out2.println("black");
            objectOut2.writeObject(board.getPieces());
    
            String line;
            do {
                //TODO: wait for player1 or player2 depending on the situation

                line = in1.readLine();
                String[] command = line.split(" ");
                switch (command[0]) {
                case "move":        // move/2/1/3/2 -> (2,1) na (3,2)
                    //TODO: handle moves
                    break;

                //TODO: handle 

                default:
                    out1.println("Unrecognised command");
                }
    
            } while (!line.equals("exit")); //TODO: update connection finishing condition

            player1.close();
            player2.close();
            System.out.println("Client disconnected");
        }
        catch (final IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}