package org.checkers;

import java.util.ArrayList;
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
     * plansza do symulacji ruchów
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
     * @return ruch bota
     * funkcja znajduje losowo ruch bota
     */
    public CoordinatesArray makeMove() {
        PathsArray[][] possibleMoves = board.getPossibleMoves(color);
        PathsArray movesToChoose = new PathsArray();

        int bestBalance = -100;
        ArrayList<Integer> balances = new ArrayList<>();

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (!board.coordinateIsWithPiece(i, j, color))
                    continue;

                for (CoordinatesArray path : possibleMoves[i][j].getList()) {
                    if (path.getList().size() > 0) {
                        CoordinatesArray tempCoorArray = new CoordinatesArray(i, j);
                        tempCoorArray.add(path);
                        movesToChoose.add(tempCoorArray);

                        int nowGained = howManyGainedIn2Moves(tempCoorArray);
                        balances.add(nowGained);
                        if (nowGained > bestBalance)
                            bestBalance = nowGained;
                    }
                }
            }
        }

        PathsArray movesToRemove = new PathsArray();
        for (int i = 0; i < movesToChoose.getList().size(); i++) {
            if (balances.get(i) < bestBalance)
                movesToRemove.add(movesToChoose.getList().get(i));
        }
        for (CoordinatesArray path : movesToRemove.getList()) {
            movesToChoose.getList().remove(path);
        }

        int idxOfChosenMove = 0;
        if (movesToChoose.getList().size() > 1)
            idxOfChosenMove = ThreadLocalRandom.current().nextInt(0, movesToChoose.getList().size() - 1);

        return movesToChoose.getList().get(idxOfChosenMove);
    }

    public int howManyGainedIn2Moves(CoordinatesArray path) {
        Board boardCopy = board.copy();
        CheckerColor otherColor = color == CheckerColor.WHITE ? CheckerColor.BLACK : CheckerColor.WHITE;

        //applying given move to the board copy
        int x1 = path.getList().get(0).getX();
        int y1 = path.getList().get(0).getY();
        int x2 = path.getList().get(path.getList().size() - 1).getX();
        int y2 = path.getList().get(path.getList().size() - 1).getY();

        boardCopy.move(x1, y1, x2, y2, color);
        
        for (int i = 1; i < path.size(); i++) {
            Coordinate last = path.getList().get(i - 1);
            Coordinate now = path.getList().get(i);

            int dx = now.getX() > last.getX() ? 1 : -1;
            int dy = now.getY() > last.getY() ? 1 : -1;
            int steps = Math.abs(now.getX() - last.getX());

            for (int x = last.getX(), y = last.getY(), iter = 0; iter < steps; iter++, x += dx, y += dy) {
                if (boardCopy.coordinateIsWithPiece(x, y, otherColor)) {
                    boardCopy.removePiece(x, y);
                }
            }
        }

        //checking opponent's moves
        int maxNumOfBeats = 0;
        boardCopy.generatePossibleMoves();
        PathsArray[][] possibleMoves = boardCopy.getPossibleMoves(otherColor);

        for (int i = 0; i < boardCopy.getSize(); i++) {
            for (int j = 0; j < boardCopy.getSize(); j++) {
                if (possibleMoves[i][j].getList().size() == 0)
                    continue;

                for (CoordinatesArray temp : possibleMoves[i][j].getList()) {
                    if (temp.getNumOfAttacks() > maxNumOfBeats)
                        maxNumOfBeats = temp.getNumOfAttacks();
                }
            }
        }

        return path.getNumOfAttacks() - maxNumOfBeats;
    }
}
