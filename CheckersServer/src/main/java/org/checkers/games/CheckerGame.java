package org.checkers.games;

import org.checkers.boards.Board;
import org.checkers.database.DatabaseManager;
import org.checkers.database.IDatabase;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.GameStatus;
import org.checkers.enums.GameType;
import org.checkers.piece.coordinate.Coordinate;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;

public abstract class CheckerGame implements Runnable {
    /**
     * plansza gry
     */
    protected final Board board;
    /**
     * aktualny status gry
     */
    protected GameStatus gameStatus;

    protected DatabaseManager databaseManager;

    protected int gameIdInDatabase;

    protected int currentTurn;

    CheckerGame(GameType gameType, Board board, DatabaseManager databaseManager) {
        this.board = board;
        this.databaseManager = databaseManager;
    }

    /**
     * @return opis parametrów planszy dla klienów
     */
    protected String prepareBoardDescription() {
        CoordinatesArray whitePieces = board.getPieces(CheckerColor.WHITE);
        CoordinatesArray blackPieces = board.getPieces(CheckerColor.BLACK);

        StringBuilder result = new StringBuilder("/" + board.getSize() + "/" + whitePieces.size());
        for(Coordinate coordinate: whitePieces.getList()) {
            result.append("/").append(coordinate.getX()).append("/").append(coordinate.getY());
        }

        for(Coordinate coordinate: blackPieces.getList()) {
            result.append("/").append(coordinate.getX()).append("/").append(coordinate.getY());
        }

        return result.toString();
    }

    /**
     * @param checkerColor kolor pionków klienta
     * @return możliwe ruchy dla klienta o określonym kolorze pinków
     */
    protected String preparePossibleMoves(CheckerColor checkerColor) {
        PathsArray[][] possibleMoves = board.getPossibleMoves(checkerColor);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                for (CoordinatesArray coordinatesArray : possibleMoves[i][j].getList()) {
                    result.append("/").append(i).append("/").append(j);
                    for (Coordinate coordinate : coordinatesArray.getList()) {
                        result.append("/").append(coordinate.getX()).append("/").append(coordinate.getY());
                    }
                    result.append("/;");
                }
            }
        }

        return result.toString();
    }
}
