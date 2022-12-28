package org.checkers;

import java.io.*;
import java.net.*;
import java.util.*;

import org.checkers.boards.*;
import org.checkers.enums.GameType;

public class MainServer {
    private static final int SOCKET_PORT = 4444;
    private static final HashMap<GameType, ArrayList<Socket>> waitingForGame = new HashMap<>();

    public static void main(String[] args)  {
        waitingForGame.put(GameType.INTERNATIONAL, new ArrayList<>());
        waitingForGame.put(GameType.BRAZILIAN, new ArrayList<>());
        waitingForGame.put(GameType.THAI, new ArrayList<>());

        try (ServerSocket serverSocket = new ServerSocket(SOCKET_PORT))  {
            System.out.println("Server is listening on port " + SOCKET_PORT);

            while(true) {
                Socket socket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inComm = in.readLine();

                String[] command = inComm.split("/");
                System.out.println(Arrays.toString(command));

                if(command.length == 2 && Objects.equals(command[0], "init-game")) {
                    GameType type = GameType.valueOf(command[1]);
                    System.out.println("New client connected: " + type);

                    waitingForGame.get(type).add(socket);
                    if (waitingForGame.get(type).size() >= 2) {
                        startGame(type);
                    }
                }

            }
        }
        catch (final IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void startGame(GameType type) {
        System.out.println("Starting new game with type " + type);

        Board board = BoardFactory.getFactory().getBoard(type);
        Thread checkersGameThread = new Thread(new CheckersGame(waitingForGame.get(type).get(0), waitingForGame.get(type).get(1), board));
        checkersGameThread.start();

        waitingForGame.get(type).remove(0);
        waitingForGame.get(type).remove(0);
    }
}