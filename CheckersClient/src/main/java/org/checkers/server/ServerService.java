package org.checkers.server;

import org.checkers.utils.GameType;

public class ServerService {
    private static CheckersServer _checkersServer = null;

    public static void initializeService(CheckersServer checkersServer) {
        _checkersServer = checkersServer;
    }

    public static void initializeCheckers(GameType gameType) {
        _checkersServer.initializeNewGame(gameType);
    }

    public static void sendPlayerMove(int oldX, int oldY, int newX, int newY) {
        _checkersServer.checkerMove(oldX, oldY, newX, newY);
    }

    public static String getInput() {
        return _checkersServer.getInput();
    }

    public static void closeConnection() {
        _checkersServer.closeConnection();
    }
}
