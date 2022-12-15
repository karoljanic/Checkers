package org.checkers.server;

import org.checkers.utils.GameType;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class CheckersServer implements ICheckersServer {
    private static final String SOCKET_HOST = "localhost";
    private static final int SOCKET_PORT = 4444;

    private Socket serverSocket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;

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
    public void initializeNewGame(GameType gameType) {
        outputStream.println("game-type/" + gameType.name());
    }

    @Override
    public void checkerMove(int oldX, int oldY, int newX, int newY) {
        try {
            inputStream.readLine();
        }
        catch (Exception ignored) { }
    }

    @Override
    public String getInput() {
        try {
            return inputStream.readLine();
        }
        catch (Exception exception) {
            return null;
        }
    }

    //@Override
    //public boolean sendObject(Object object) {
    //    try {
    //        outputStream.writeObject(object);
    //
    //        return true;
    //    }
    //    catch (IOException exception) {
    //        return false;
    //    }
    //}
}