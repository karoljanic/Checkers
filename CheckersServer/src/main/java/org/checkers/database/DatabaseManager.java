package org.checkers.database;

import org.checkers.database.entities.GameEntity;
import org.checkers.database.entities.MoveEntity;
import org.checkers.database.entities.TurnEntity;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.GameType;

import java.util.ArrayList;

public class DatabaseManager {
    private IDatabase iDatabase;

    public DatabaseManager(IDatabase database) {
        iDatabase = database;
    }

    public void connect() {
        iDatabase.connect();
    }

    public void disconnect() {
        iDatabase.disconnect();
    }

    public int addNewGame(GameType gameType) {
        return iDatabase.insertGame(gameType.ordinal() + 1);
    }

    public int addNewTurn(int gameId, int turnNumber, CheckerColor checkerColor) {
        return iDatabase.insertTurn(gameId, turnNumber, checkerColor.toString().toLowerCase());
    }

    public int addNewMove(int turnId, int moveNumber, boolean isBeat, int startX, int startY, int endX, int endY) {
        return iDatabase.insertMove(turnId, moveNumber, isBeat ? "beat" : "normal", startX, startY, endX, endY);
    }

    public ArrayList<GameEntity> getAllGames() {
        return iDatabase.getAllGames();
    }

    public ArrayList<TurnEntity> getAllTurns(int gameId) {
        return iDatabase.getAllTurns(gameId);
    }

    public ArrayList<MoveEntity> getAllMoves(int turnId) {
        return iDatabase.getAllMoves(turnId);
    }

    public ArrayList<MoveEntity> getAllMovesInGame(int gameId) {
        return iDatabase.getAllMovesInGame(gameId);
    }

    public GameType getGameType(int gameType) {
        return GameType.values()[iDatabase.getGameType(gameType) - 1];
    }
}
