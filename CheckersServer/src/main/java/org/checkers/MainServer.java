package org.checkers;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.checkers.boards.*;

public class MainServer
{
    private enum GameType {INTERNATIONAL, BRAZILIAN, THAI}

    private static final HashMap<GameType, ArrayList<Socket>> waitingForGame = new HashMap<>();

    private static void startGame(GameType type) {
        if (waitingForGame.get(type).size() >= 2) {
            Board board = null;
            switch (type) {
                case INTERNATIONAL:
                    board = new InternationalBoard();
                    break;
                case BRAZILIAN:
                    board = new BrazilianBoard();
                    break;
                case THAI:
                    board = new ThaiBoard();
                    break;
            }

            new CheckersGame(waitingForGame.get(type).get(0),
                waitingForGame.get(type).get(1), board).start();
            
            waitingForGame.get(type).remove(0);
            waitingForGame.get(type).remove(0);

            System.out.println("Started new game type " + type.toString());
        }
    }

    public static void main(String[] args)
    {
        waitingForGame.put(GameType.INTERNATIONAL, new ArrayList<>());
        waitingForGame.put(GameType.BRAZILIAN, new ArrayList<>());
        waitingForGame.put(GameType.THAI, new ArrayList<>());

        try (ServerSocket serverSocket = new ServerSocket(4444))
        {
            System.out.println("Server is listening on port 4444");

            while (true)
            {
                Socket socket = serverSocket.accept();

                InputStream input = socket.getInputStream();
                ObjectInputStream in = new ObjectInputStream(input);

                GameType type = null;
                try { type = (GameType) in.readObject(); }
                catch (ClassNotFoundException e) {}

                waitingForGame.get(type).add(socket);
                startGame(type);

                System.out.println("New client connected: " + type.toString());
            }

        }
        catch (final IOException ex)
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}