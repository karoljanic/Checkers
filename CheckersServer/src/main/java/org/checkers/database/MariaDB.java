package org.checkers.database;

import org.checkers.database.entities.GameEntity;
import org.checkers.database.entities.MoveEntity;
import org.checkers.database.entities.TurnEntity;
import org.checkers.enums.GameType;

import javax.persistence.*;
import java.util.ArrayList;

public class MariaDB implements IDatabase {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public MariaDB() {

    }

    @Override
    public void connect() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void disconnect() {
        entityManagerFactory.close();
        entityManager.close();
    }

    @Override
    public int insertGame(int gameType) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        GameEntity gameEntity = new GameEntity();
        gameEntity.setGameType(gameType);

        entityManager.persist(gameEntity);
        transaction.commit();

        return gameEntity.getId();
    }

    @Override
    public int insertTurn(int gameId, int turnNumber, String checkerColor) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TurnEntity turnEntity = new TurnEntity();
        turnEntity.setGameId(gameId);
        turnEntity.setTurnNumber(turnNumber);
        turnEntity.setCheckerColor(checkerColor);

        entityManager.persist(turnEntity);
        transaction.commit();

        return turnEntity.getId();
    }

    @Override
    public int insertMove(int turnId, int numberInTurn, String moveType, int startX, int startY, int endX, int endY) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        MoveEntity moveEntity = new MoveEntity();
        moveEntity.setTurnId(turnId);
        moveEntity.setMoveNumberInTurn(numberInTurn);
        moveEntity.setMoveType(moveType);
        moveEntity.setStartX(startX);
        moveEntity.setStartY(startY);
        moveEntity.setEndX(endX);
        moveEntity.setEndY(endY);

        entityManager.persist(moveEntity);
        transaction.commit();

        return moveEntity.getId();
    }

    @Override
    public ArrayList<GameEntity> getAllGames() {
        Query allGamesQuery = entityManager.createNativeQuery("SELECT * FROM Game;", GameEntity.class);

        ArrayList<GameEntity> result = new ArrayList<>();
        for (Object gameObject : allGamesQuery.getResultList()) {
            result.add((GameEntity) gameObject);
        }

        return result;
    }

    @Override
    public ArrayList<TurnEntity> getAllTurns(int gameId) {
        Query allTurnsWithGameId = entityManager.createNativeQuery("SELECT * FROM Turn WHERE game_id = ?1 ORDER BY turn_number;", TurnEntity.class);
        allTurnsWithGameId.setParameter(1, gameId);

        ArrayList<TurnEntity> result = new ArrayList<>();
        for(Object turnObject : allTurnsWithGameId.getResultList()) {
            result.add((TurnEntity) turnObject);
        }

        return result;
    }

    @Override
    public ArrayList<MoveEntity> getAllMoves(int turnId) {
        Query allMovesWithGameId = entityManager.createNativeQuery("SELECT * FROM Move WHERE turn_id = ?1 ORDER BY move_number_in_turn;", MoveEntity.class);
        allMovesWithGameId.setParameter(1, turnId);

        ArrayList<MoveEntity> result = new ArrayList<>();
        for(Object moveObject : allMovesWithGameId.getResultList()) {
            result.add((MoveEntity) moveObject);
        }

        return result;
    }

    @Override
    public ArrayList<MoveEntity> getAllMovesInGame(int gameId) {
        Query allMovesInGame = entityManager.createNativeQuery("SELECT * FROM Move JOIN Turn T on Move.turn_id = T.id WHERE game_id = ?1 ORDER BY turn_number, move_number_in_turn", MoveEntity.class);
        allMovesInGame.setParameter(1, gameId);

        ArrayList<MoveEntity> result = new ArrayList<>();
        for(Object moveObject : allMovesInGame.getResultList()) {
            result.add((MoveEntity) moveObject);
        }

        return result;
    }

    @Override
    public int getGameType(int gameId) {
        Query getGameType = entityManager.createNativeQuery("SELECT game_type FROM Game WHERE id = ?1");
        getGameType.setParameter(1, gameId);

        return (int)getGameType.getSingleResult();
    }
}
