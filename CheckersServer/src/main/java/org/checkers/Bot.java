package org.checkers;

import java.util.concurrent.ThreadLocalRandom;

import org.checkers.boards.*;
import org.checkers.enums.*;
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

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (possibleMoves[i][j].getList().size() == 0)
                    continue;

                for (CoordinatesArray path : possibleMoves[i][j].getList()) {
                    if (path.getList().size() == 0)
                        continue;

                    CoordinatesArray tempCoorArray = new CoordinatesArray(i, j);
                    tempCoorArray.add(path);
                    movesToChoose.add(tempCoorArray);
                }
            }
        }

        int idxOfChosenMove = 0;
        if (movesToChoose.getList().size() > 1)
            idxOfChosenMove = ThreadLocalRandom.current().nextInt(0, movesToChoose.getList().size() - 1);
        System.out.println(idxOfChosenMove);

        return movesToChoose.getList().get(idxOfChosenMove);
    }

}
