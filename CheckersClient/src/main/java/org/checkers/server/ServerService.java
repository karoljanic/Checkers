package org.checkers.server;

import org.checkers.utils.GameType;

public class ServerService {
    private static CheckersServer _cheCheckersServer = null;

    public static void initializeCheckers(GameType gameType) {
        if(_cheCheckersServer == null) {
            _cheCheckersServer = new CheckersServer();
        }

        _cheCheckersServer.initializeNewGame(gameType);
    }

    public static void sendPlayerMove(int oldX, int oldY, int newX, int newY) {
        _cheCheckersServer.checkerMove(oldX, oldY, newX, newY);
    }
}
