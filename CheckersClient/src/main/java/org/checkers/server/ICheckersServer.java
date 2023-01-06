package org.checkers.server;

import org.checkers.utils.GameType;

public interface ICheckersServer {
    void initializeNewGame(GameType gameType);
    void checkerMove(int oldX, int oldY, int newX, int newY);
    void closeConnection();

    String getInput();
}
