package org.checkers.database;

import org.checkers.database.entities.GameEntity;
import org.checkers.database.entities.MoveEntity;
import org.checkers.database.entities.TurnEntity;
import org.checkers.enums.GameType;

import java.util.ArrayList;
public interface IDatabase {
    public void connect();
    public void disconnect();

    public int insertGame(int gameType);
    public int insertTurn(int gameId, int turnNumber, String checkerColor);
    public int insertMove(int turnId, int numberInTurn, String moveType, int startX, int startY, int endX, int endY);

    public ArrayList<GameEntity> getAllGames();
    public ArrayList<TurnEntity> getAllTurns(int gameId);
    public ArrayList<MoveEntity> getAllMoves(int turnId);

    public ArrayList<MoveEntity> getAllMovesInGame(int gameId);

    public int getGameType(int gameId);

}
