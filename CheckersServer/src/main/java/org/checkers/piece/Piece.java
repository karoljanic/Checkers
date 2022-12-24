package org.checkers.piece;

import org.checkers.boards.Board;
import org.checkers.piece.coordinate.Coordinate;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;
import org.checkers.utils.CheckerColor;
import org.checkers.utils.CheckerType;

public abstract class Piece {
    protected Coordinate coordinate;
    protected CheckerColor color;
    protected CheckerType type;

    public Piece(int x, int y, CheckerColor color, CheckerType type) {
        this.coordinate = new Coordinate(x, y);
        this.color = color;
        this.type = type;
    }

    public void moveTo(int x, int y) {
        this.coordinate = new Coordinate(x, y);
    }

    public int getX() { return coordinate.getX(); }

    public int getY() { return coordinate.getY(); }

    public CheckerColor getColor() {
        return color;
    }

    public CheckerType getType() {
        return type;
    }

    public void makeKing() {
        type = CheckerType.KING;
    }

    public abstract PathsArray getPossibleMoves(Board currentBoard);

    public abstract void findAttacks(Board currentBoard, Coordinate currentCoordinate, CoordinatesArray path, PathsArray pathsArray);
    public abstract void findAttacks(Board currentBoard, Coordinate currentCoordinate, int dx, int dy, CoordinatesArray path, PathsArray pathsArray);

    public abstract Piece copy();
}
