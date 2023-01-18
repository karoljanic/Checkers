package org.checkers;

import java.util.concurrent.ThreadLocalRandom;

import org.checkers.boards.*;
import org.checkers.enums.*;
import org.checkers.piece.coordinate.Coordinate;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;

/**
 * klasa symuluje ruchy bota
 */
public class Bot {
    /**
     * kopia planszy do symulacji ruchów
     */
    private final Board board;
    /**
     * kolor pinków bota
     */
    private final CheckerColor color;
    
    /**
     * @param b kopia planszy do gry
     * @param c kolor pionków bota
     */
    public Bot(Board b, CheckerColor c) {
        this.board = b;
        this.color = c;
    }

    /**
     * @return najlepszy ruch dla komputera
     */
    public CoordinatesArray makeMove() {
        board.generatePossibleMoves();
        PathsArray[][] possibleMoves = board.getPossibleMoves(color);
        PathsArray movesToChoose = new PathsArray();

        // {opponent's pieces beated} - {your pieces beated}
        int maxPiecesGained = 0;

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (!board.coordinateIsWithPiece(i, j, color))
                    continue;

                for (CoordinatesArray path : possibleMoves[i][j].getList()) {
                    //int currentNumOfBeatsAfterMove = numOfBeatsAfterMove(board.copy(), path);
                    //int currentNumOfBeats = path.getNumOfAttacks();
                    //if (currentNumOfBeats - currentNumOfBeatsAfterMove > maxPiecesGained)
                    //    maxPiecesGained = currentNumOfBeats - currentNumOfBeatsAfterMove;

                    CoordinatesArray tempCoorArray = new CoordinatesArray(i, j);
                    tempCoorArray.add(path);
                    movesToChoose.add(tempCoorArray);
                    return tempCoorArray;
                }
            }
        }

        if (maxPiecesGained > 0) {
            PathsArray pathsToRemove = new PathsArray();

            for (CoordinatesArray path : movesToChoose.getList()) {
                int currentNumOfBeatsAfterMove = numOfBeatsAfterMove(board.copy(), path);
                int currentNumOfBeats = path.getNumOfAttacks();
                if (currentNumOfBeats - currentNumOfBeatsAfterMove < maxPiecesGained)
                    pathsToRemove.add(path);
            }

            for (CoordinatesArray pathToRemove : pathsToRemove.getList()) {
                movesToChoose.getList().remove(pathToRemove);
            }
        }

        CoordinatesArray chosenMove;
        //TODO: losować ruch z pozostałych
        chosenMove = movesToChoose.getList().get(0);

        return chosenMove;
    }

    /**
     * @param x1 początkowa x-owa współrzędna pionka
     * @param y1 początkowa y-owa współrzędna pionka
     * @param x2 końcowa x-owa współrzędna pionka
     * @param y2 końcowa y-owa współrzędna pionka
     * @param c kolor pionka wykonującego ruch
     * funkcja dodaje wykonany ruch do planszy
     */
    public void addMove(int x1, int y1, int x2, int y2, CheckerColor c) {
        board.move(x1, y1, x2, y2, c);
    }

    /**
     * @param x x-owa współrzędna pionka do usunięcia
     * @param y y-owa współrzędna pionka do usunięcia
     * funkcja usuwa pionek z planszy
     */
    public void removePiece(int x, int y) {
        board.removePiece(x, y);
    }

    /**
     * @param boardCopy kopia planszy do gry
     * @param path sprawdzany ruch
     * @return największa liczba bić dla przeciwnika po ruchu
     */
    private int numOfBeatsAfterMove(Board boardCopy, CoordinatesArray path) {
        CheckerColor opponentsColor = (color == CheckerColor.WHITE ? CheckerColor.BLACK : CheckerColor.WHITE);
        //make chosen move
        for (int i = 1; i < path.size(); i++) {
            Coordinate last = path.getList().get(i - 1);
            Coordinate now = path.getList().get(i);

            boardCopy.move(last.getX(), last.getY(), now.getX(), now.getY(), color);

            int dx = now.getX() > last.getX() ? 1 : -1;
            int dy = now.getY() > last.getY() ? 1 : -1;
            int steps = Math.abs(now.getX() - last.getX());

            for (int x = last.getX(), y = last.getY(), iter = 0; iter < steps; iter++, x += dx, y += dy) {
                if (boardCopy.coordinateIsWithPiece(x, y, opponentsColor)) {
                    boardCopy.removePiece(x, y);
                }
            }
        }

        //check how many beats for oppponent after move
        boardCopy.generatePossibleMoves();
        PathsArray[][] possibleMoves = boardCopy.getPossibleMoves(opponentsColor);

        int maxNumOfBeats = 0;

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (!board.coordinateIsWithPiece(i, j, opponentsColor))
                    continue;

                for (CoordinatesArray currPath : possibleMoves[i][j].getList()) {
                    if (currPath.getNumOfAttacks() > maxNumOfBeats)
                        maxNumOfBeats = currPath.getNumOfAttacks();
                }
            }
        }

        return maxNumOfBeats;
    }
}
