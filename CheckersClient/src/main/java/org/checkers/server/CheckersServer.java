package org.checkers.server;

import org.checkers.utils.GameType;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * klasa obsługuje połączenie z serwerem
 */
public class CheckersServer implements ICheckersServer {
    /**
     * adres serwera
     */
    private static final String SOCKET_HOST = "localhost";
    /**
     * port do połączenia z serwerem
     */
    private static final int SOCKET_PORT = 5555;

    /**
     * gniazdo do komunikacja z serwerem
     */
    private Socket serverSocket;
    /**
     * obiekt do wysyłania danych na serwer
     */
    private PrintWriter outputStream;
    /**
     * obiekt do odbierania danych z serwera
     */
    private BufferedReader inputStream;

    /**
     * konstruktor łączy się z serwerem i ustawia obiekty do komunikacji
     */
    public CheckersServer() {
        try {
            serverSocket = new Socket(SOCKET_HOST, SOCKET_PORT);
            outputStream = new PrintWriter(serverSocket.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + SOCKET_HOST);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O for given host: " + SOCKET_HOST + " and port: " + SOCKET_PORT);
            System.exit(1);
        }
    }

    @Override
    public void showSavedGames() {
        outputStream.println("show-saved-games/");
    }

    @Override
    public void replayGame(int savedGameId) {
        outputStream.println("replay-game/" + savedGameId);
    }


    /* (non-Javadoc)
     * @see org.checkers.server.ICheckersServer#initializeNewGame(org.checkers.utils.GameType)
     */
    @Override
    public void initializeNewGame(GameType gameType, boolean againstBot) {
        if (againstBot)
            outputStream.println("init-game-bot/" + gameType.name());
        else
            outputStream.println("init-game/" + gameType.name());
    }

    /* (non-Javadoc)
     * @see org.checkers.server.ICheckersServer#checkerMove(int, int, int, int)
     */
    @Override
    public void checkerMove(int oldX, int oldY, int newX, int newY) {
        outputStream.println(("move/" + oldX + "/" + oldY + "/" + newX + "/" + newY));
    }

    /* (non-Javadoc)
     * @see org.checkers.server.ICheckersServer#closeConnection()
     */
    @Override
    public void closeConnection() {
        try {
            outputStream.close();
            inputStream.close();
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see org.checkers.server.ICheckersServer#getInput()
     */
    @Override
    public String getInput() {
        try {
            return inputStream.readLine();
        }
        catch (Exception exception) {
            return null;
        }
    }
}
