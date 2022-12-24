package org.checkers.piece;

import org.checkers.boards.Board;
import org.checkers.piece.coordinate.Coordinate;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;
import org.checkers.utils.CheckerColor;
import org.checkers.utils.CheckerType;

public class WhitePiece extends Piece {
    public WhitePiece(int x, int y) {
        super(x, y, CheckerColor.WHITE, CheckerType.NORMAL);
    }

    public WhitePiece(Piece piece) { super(piece.getX(), piece.getY(), CheckerColor.WHITE, piece.getType()); }

    @Override
    public PathsArray getPossibleMoves(Board currentBoard) {
        PathsArray pathsArray = new PathsArray();

        // standard moves
        if(coordinate.getY() < currentBoard.getSize() - 1) {
            if(coordinate.getX() > 0) {
                if(currentBoard.coordinateIsFree(coordinate.getX() - 1, coordinate.getY() + 1)) {
                    pathsArray.add(new CoordinatesArray(coordinate.getX() - 1, coordinate.getY() + 1));
                }
            }

            if(coordinate.getX() < currentBoard.getSize() - 1) {
                if(currentBoard.coordinateIsFree(coordinate.getX() + 1, coordinate.getY() + 1)) {
                    pathsArray.add(new CoordinatesArray(coordinate.getX() + 1, coordinate.getY() + 1));
                }
            }
        }

        // attacks
        findAttacks(currentBoard.copy(), new Coordinate(coordinate), new CoordinatesArray(), pathsArray);

        return pathsArray;
    }

    @Override
    public void findAttacks(Board currentBoard, Coordinate currentCoordinate, CoordinatesArray path, PathsArray pathsArray) {
        findAttacks(currentBoard, currentCoordinate, 2, 2, path, pathsArray);
        findAttacks(currentBoard, currentCoordinate, -2, 2, path, pathsArray);
        findAttacks(currentBoard, currentCoordinate, 2, -2, path, pathsArray);
        findAttacks(currentBoard, currentCoordinate, -2, -2, path, pathsArray);
    }

    @Override
    public void findAttacks(Board currentBoard, Coordinate currentCoordinate, int dx, int dy, CoordinatesArray path, PathsArray pathsArray) {
        if(currentCoordinate.getX() + dx >= currentBoard.getSize() || currentCoordinate.getY() + dy >= currentBoard.getSize())
            return;

        if(currentCoordinate.getX() + dx / 2 >= currentBoard.getSize() || currentCoordinate.getY() + dy / 2 >= currentBoard.getSize())
            return;

        if(currentCoordinate.getX() + dx < 0 || currentCoordinate.getY() + dy < 0)
            return;

        if(currentCoordinate.getX() + dx / 2 < 0 || currentCoordinate.getY() + dy / 2 < 0)
            return;

        if(currentBoard.coordinateIsWithPiece(currentCoordinate.getX() + dx / 2, currentCoordinate.getY() + dy / 2, CheckerColor.BLACK)) {
            if(currentBoard.coordinateIsFree(currentCoordinate.getX() + dx , currentCoordinate.getY() + dy)) {
                CoordinatesArray pathCopy = new CoordinatesArray(path);
                pathCopy.add(new CoordinatesArray(currentCoordinate.getX() + dx, currentCoordinate.getY() + dy));
                pathsArray.add(pathCopy);

                CoordinatesArray pathCopyCopy = new CoordinatesArray(pathCopy);
                Board boardCopy = currentBoard.copy();
                boardCopy.removePiece(currentCoordinate.getX() + dx / 2, currentCoordinate.getY() + dy / 2);
                findAttacks(boardCopy, new Coordinate(currentCoordinate.getX() + dx, currentCoordinate.getY() + dy), pathCopyCopy, pathsArray);
            }
        }
    }

    @Override
    public Piece copy() {
        return new WhitePiece(this);
    }
}
