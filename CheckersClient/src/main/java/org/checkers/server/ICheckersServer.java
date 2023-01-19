package org.checkers.server;

import org.checkers.utils.GameType;

/**
 * interfejs do obsługi połączenia z serwerem
 */
public interface ICheckersServer {
    /**
     * @param gameType typ gry
     * @param againstBot true, jeśli klient chce grać z botem
     * funkcja rozpoczyna nową grę
     */
    void initializeNewGame(GameType gameType, boolean againstBot);
    /**
     * @param oldX x-owa współrzędna pionka
     * @param oldY y-owa współrzędna pionka
     * @param newX nowa x-owa współrzędna pionka
     * @param newY nowa y-owa współrzędna pionka
     * funkcja wykonuje ruch i wysyła go na serwer
     */
    void checkerMove(int oldX, int oldY, int newX, int newY);
    /**
     * funkcja zamyka połaczenie z serwerem
     */
    void closeConnection();
    /**
     * @return input od serwera
     * funkcja pobiera i zwraca input od serwera
     */
    String getInput();
}
