package org.checkers.server;

import org.checkers.utils.GameType;

/**
 * klasa obsługuje klasę CheckersServer
 */
public class ServerService {
    /**
     * instancja klasy CheckersServer
     */
    private static CheckersServer _checkersServer = null;

    public static void showSavedGames() {
        _checkersServer.showSavedGames();
    }

    public static void replayGame(int savedGameId) {
        _checkersServer.replayGame(savedGameId);
    }

    /**
     * @param checkersServer nowa instancja klasy CheckersServer
     * funkcja ustawia obiekt CheckersServer
     */
    public static void initializeService(CheckersServer checkersServer) {
        _checkersServer = checkersServer;
    }

    /**
     * @param gameType typ gry
     * @param againstBot true, jeśli klient chce grać z botem
     * funkcja ustawia typ gry
     */
    public static void initializeCheckers(GameType gameType, boolean againstBot) {
        _checkersServer.initializeNewGame(gameType, againstBot);
    }

    /**
     * @param oldX x-owa współrzędna pionka
     * @param oldY y-owa współrzędna pionka
     * @param newX nowa x-owa współrzędna pionka
     * @param newY nowa y-owa współrzędna pionka
     * funkcja wykonuje ruch i wysyła go na serwer
     */
    public static void sendPlayerMove(int oldX, int oldY, int newX, int newY) {
        _checkersServer.checkerMove(oldX, oldY, newX, newY);
    }

    /**
     * @return input od serwera
     * funkcja pobiera i zwraca input od serwera
     */
    public static String getInput() {
        return _checkersServer.getInput();
    }

    /**
     * funkcja zamyka połaczenie z serwerem
     */
    public static void closeConnection() {
        _checkersServer.closeConnection();
    }
}
