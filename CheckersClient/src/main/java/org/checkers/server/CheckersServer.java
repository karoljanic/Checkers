package org.checkers.server;

import org.checkers.utils.GameType;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class CheckersServer implements ICheckersServer {
    private static final String SOCKET_HOST = "localhost";
    private static final int SOCKET_PORT = 4444;

    private static Socket serverSocket;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    @Override
    public void initializeNewGame(GameType gameType) {
        try {
            serverSocket = new Socket(SOCKET_HOST, SOCKET_PORT);
            outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
            inputStream = new ObjectInputStream(serverSocket.getInputStream());

            outputStream.writeObject(gameType);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + SOCKET_HOST);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O for given host: " + SOCKET_HOST + " and port: " + SOCKET_PORT);
            System.exit(1);
        }
    }

    @Override
    public void checkerMove(int oldX, int oldY, int newX, int newY) {

    }
}
