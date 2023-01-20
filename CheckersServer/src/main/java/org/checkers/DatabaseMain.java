package org.checkers;


import org.checkers.database.DatabaseManager;
import org.checkers.database.MariaDB;
import org.checkers.database.entities.GameEntity;
import org.checkers.database.entities.MoveEntity;
import org.checkers.database.entities.TurnEntity;
import org.checkers.enums.GameType;

import java.util.ArrayList;


public class DatabaseMain {
    public static void main(String[] args) {
        MariaDB mariaDB = new MariaDB();
        DatabaseManager databaseManager = new DatabaseManager(mariaDB);

        databaseManager.connect();

        ArrayList<MoveEntity> moves = databaseManager.getAllMovesInGame(16);
        for(MoveEntity moveEntity: moves) {
            System.out.println(moveEntity.getId() + " - " + moveEntity.getTurnId() + " - " + moveEntity.getMoveNumberInTurn() + " - " + moveEntity.getStartX() + " - " + moveEntity.getStartY() + " - " + moveEntity.getEndX() + " - " + moveEntity.getEndY());
        }

        /*
        GameType type = databaseManager.getGameType(18);
        System.out.println(type);


        ArrayList<GameEntity> games = databaseManager.getAllGames();
        ArrayList<TurnEntity> turns = databaseManager.getAllTurns(14);
        ArrayList<MoveEntity> moves = databaseManager.getAllMoves(11);

        for(GameEntity gameEntity: games) {
            System.out.println(gameEntity.getId() + " - " + gameEntity.getGameType() + " - " + gameEntity.getSaveTime());
        }
        System.out.println();

        for(TurnEntity turnEntity: turns) {
            System.out.println(turnEntity.getId() + " - " + turnEntity.getGameId() + " - " + turnEntity.getTurnNumber() + " - " + turnEntity.getCheckerColor());
        }
        System.out.println();

        for(MoveEntity moveEntity: moves) {
            System.out.println(moveEntity.getId() + " - " + moveEntity.getTurnId() + " - " + moveEntity.getMoveNumberInTurn() + " - " + moveEntity.getStartX() + " - " + moveEntity.getStartY() + " - " + moveEntity.getEndX() + " - " + moveEntity.getEndY());
        }
         */

        databaseManager.disconnect();
    }
}
