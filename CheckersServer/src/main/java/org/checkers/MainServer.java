package org.checkers;

import java.io.*;
import java.net.*;
import java.util.*;

import org.checkers.boards.*;
import org.checkers.database.DatabaseManager;
import org.checkers.database.MariaDB;
import org.checkers.database.entities.GameEntity;
import org.checkers.enums.GameType;
import org.checkers.games.BotCheckersGame;
import org.checkers.games.NoBotCheckersGame;
import org.checkers.games.ReplayCheckersGame;

/**
 * główna klasa obsługująca serwer do gry w warcaby
 */
public class MainServer {
    /**
     * port, na którym serwer czeka na połączenie
     */
    private static final int SOCKET_PORT = 5555;
    /**
     * kolejka klientów czekających na połączenie
     */
    private static final HashMap<GameType, ArrayList<Socket>> waitingForGame = new HashMap<>();

    private static final DatabaseManager databaseManager = new DatabaseManager(new MariaDB());

    /**
     * główna metoda obsługująca łączących się klientów
     */
    public static void main(String[] args)  {
        waitingForGame.put(GameType.INTERNATIONAL, new ArrayList<>());
        waitingForGame.put(GameType.BRAZILIAN, new ArrayList<>());
        waitingForGame.put(GameType.THAI, new ArrayList<>());

        try (ServerSocket serverSocket = new ServerSocket(SOCKET_PORT))  {
            System.out.println("Server is listening on port " + SOCKET_PORT);

            databaseManager.connect();

            while(true) {
                Socket socket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inComm = in.readLine();

                System.out.println("DOSTALEM: " + inComm);

                String[] command = inComm.split("/");

                if(command.length == 2 && command[0].equals("init-game")) {
                    GameType type = GameType.valueOf(command[1]);
                    System.out.println("New client connected: " + type);

                    waitingForGame.get(type).add(socket);
                    if (waitingForGame.get(type).size() >= 2) {
                        startGame(type);
                    }
                }
                else if (command.length == 2 && command[0].equals("init-game-bot")) {
                    GameType type = GameType.valueOf(command[1]);
                    System.out.println("New client connected: " + type);

                    Board board = BoardFactory.getFactory().getBoard(type);
                    Thread checkersGameThread = new Thread(new BotCheckersGame(socket, type, board, databaseManager));
                    checkersGameThread.start();
                }
                else if (command.length == 1 && command[0].equals("show-saved-games")) {
                    ArrayList<GameEntity> games = databaseManager.getAllGames();
                    StringBuilder history = new StringBuilder("history");
                    for(GameEntity game: games) {
                        history.append("/").append(game.getId()).append("/").append(game.getGameType()).append("/").append(game.getSaveTime());
                    }

                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println(history);

                    while (true) {
                        inComm = in.readLine();
                        command = inComm.split("/");

                        if (command.length == 2 && command[0].equals("replay-game")) {
                            int gameId = Integer.parseInt(command[1]);
                            GameType gameType = databaseManager.getGameType(gameId);

                            Board board = BoardFactory.getFactory().getBoard(gameType);
                            Thread checkersGameThread = new Thread(new ReplayCheckersGame(socket, gameId, gameType, board, databaseManager));
                            checkersGameThread.start();
                        }
                    }
                }
            }
        }
        catch (final Exception ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally {
            databaseManager.disconnect();
        }
    }

    /**
     * @param type typ gry, która ma się zacząć
     * funkcja tworzy nowy wątek, który obsługuje nową grę
     */
    private static void startGame(GameType type) {
        System.out.println("Starting new game with type " + type);

        Board board = BoardFactory.getFactory().getBoard(type);
        Thread checkersGameThread = new Thread(new NoBotCheckersGame(waitingForGame.get(type).get(0), waitingForGame.get(type).get(1), type, board, databaseManager));
        checkersGameThread.start();

        waitingForGame.get(type).remove(0);
        waitingForGame.get(type).remove(0);
    }
}