package org.checkers;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.checkers.boards.*;

public class MainServer
{
    private static final HashMap<String, ArrayList<Socket>> waitingForGame = new HashMap<>();

    private static void startGame(String gameType) {
        if (waitingForGame.get(gameType).size() >= 2) {
            Board board = null;
            switch (gameType) {
                case "international":
                    board = new InternationalBoard();
                    break;
                case "brazilian":
                    board = new BrazilianBoard();
                    break;
                case "thai":
                    board = new ThaiBoard();
                    break;
            }

            new CheckersGame(waitingForGame.get(gameType).get(0),
                waitingForGame.get(gameType).get(1), board).start();
            
            waitingForGame.get(gameType).remove(0);
            waitingForGame.get(gameType).remove(0);

            System.out.println("Started new game type " + gameType);
        }
    }

    public static void main(String[] args)
    {
        waitingForGame.put("international", new ArrayList<>());
        waitingForGame.put("brazilian", new ArrayList<>());
        waitingForGame.put("thai", new ArrayList<>());

        try (ServerSocket serverSocket = new ServerSocket(4444))
        {
            System.out.println("Server is listening on port 4444");

            while (true)
            {
                Socket socket = serverSocket.accept();

                InputStream input = socket.getInputStream();
                ObjectInputStream in = new ObjectInputStream(input);

                String line = "";
                try { line = (String)in.readObject(); }
                catch (ClassNotFoundException e) {}

                if (line.equals("international")) {
                    waitingForGame.get("International").add(socket);
                    startGame("international");
                }

                else if (line.equals("brazilian")) {
                    waitingForGame.get("brazilian").add(socket);
                    startGame("brazilian");
                }

                else if (line.equals("thai")) {
                    waitingForGame.get("thai").add(socket);
                    startGame("thai");
                }

                System.out.println("New client connected: " + line);
            }

        }
        catch (final IOException ex)
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}