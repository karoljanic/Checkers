package org.checkers.piece;

import org.checkers.boards.Board;
import org.checkers.enums.MoveDirection;
import org.checkers.piece.coordinate.Coordinate;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.CheckerType;

public class Piece {
    protected Coordinate coordinate;
    protected CheckerColor color;
    protected CheckerType type;
    protected final MoveDirection moveDirection;

    public Piece(int x, int y, CheckerColor color, CheckerType type, MoveDirection moveDirection) {
        this.coordinate = new Coordinate(x, y);
        this.color = color;
        this.type = type;
        this.moveDirection = moveDirection;
    }

    public Piece(Piece piece) {
        this.coordinate = new Coordinate(piece.coordinate);
        this.color = piece.color;
        this.type = piece.type;
        this.moveDirection = piece.moveDirection;
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

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }

    public void makeKing() {
        type = CheckerType.KING;
    }

    public PathsArray getPossibleMoves(Board currentBoard, boolean attackBack) {
        PathsArray pathsArray = new PathsArray();

        if(type == CheckerType.NORMAL) {
            // standard moves
            if (moveDirection == MoveDirection.DOWN ? coordinate.getY() > 0 : coordinate.getY() < currentBoard.getSize() - 1) {
                if (coordinate.getX() > 0) {
                    if (currentBoard.coordinateIsFree(coordinate.getX() - 1, coordinate.getY() + moveDirection.getOrdinal())) {
                        pathsArray.add(new CoordinatesArray(coordinate.getX() - 1, coordinate.getY() + moveDirection.getOrdinal()));
                    }
                }

                if (coordinate.getX() < currentBoard.getSize() - 1) {
                    if (currentBoard.coordinateIsFree(coordinate.getX() + 1, coordinate.getY() + moveDirection.getOrdinal())) {
                        pathsArray.add(new CoordinatesArray(coordinate.getX() + 1, coordinate.getY() + moveDirection.getOrdinal()));
                    }
                }
            }

            // attacks
            findNormalPieceAttacks(currentBoard.copy(), new Coordinate(coordinate), new CoordinatesArray(), pathsArray, attackBack);
        }
        else { // type == CheckerType.KING
            findKingPieceMoves(currentBoard.copy(), new Coordinate(coordinate), new CoordinatesArray(), pathsArray);
        }

        return pathsArray;
    }

    private void findNormalPieceAttacks(Board currentBoard, Coordinate currentCoordinate, CoordinatesArray path, PathsArray pathsArray, boolean attackBack) {
        if (color == CheckerColor.WHITE || (color == CheckerColor.BLACK && attackBack)) {
            findNormalPieceAttacks(currentBoard, currentCoordinate, 2, 2, path, pathsArray, attackBack);
            findNormalPieceAttacks(currentBoard, currentCoordinate, -2, 2, path, pathsArray, attackBack);
        }
        if (color == CheckerColor.BLACK || (color == CheckerColor.WHITE && attackBack)) {
            findNormalPieceAttacks(currentBoard, currentCoordinate, 2, -2, path, pathsArray, attackBack);
            findNormalPieceAttacks(currentBoard, currentCoordinate, -2, -2, path, pathsArray, attackBack);
        }
    }

    private void findNormalPieceAttacks(Board currentBoard, Coordinate currentCoordinate, int dx, int dy, CoordinatesArray path, PathsArray pathsArray, boolean attackBack) {
        if(currentCoordinate.getX() + dx >= currentBoard.getSize() || currentCoordinate.getY() + dy >= currentBoard.getSize())
            return;

        if(currentCoordinate.getX() + dx / 2 >= currentBoard.getSize() || currentCoordinate.getY() + dy / 2 >= currentBoard.getSize())
            return;

        if(currentCoordinate.getX() + dx < 0 || currentCoordinate.getY() + dy < 0)
            return;

        if(currentCoordinate.getX() + dx / 2 < 0 || currentCoordinate.getY() + dy / 2 < 0)
            return;

        if(currentBoard.coordinateIsWithPiece(currentCoordinate.getX() + dx / 2, currentCoordinate.getY() + dy / 2, color == CheckerColor.WHITE ? CheckerColor.BLACK : CheckerColor.WHITE)) {
            if(currentBoard.coordinateIsFree(currentCoordinate.getX() + dx , currentCoordinate.getY() + dy)) {
                CoordinatesArray pathCopy = new CoordinatesArray(path);
                pathCopy.addAttack();
                pathCopy.add(new CoordinatesArray(currentCoordinate.getX() + dx, currentCoordinate.getY() + dy));
                pathsArray.add(pathCopy);

                CoordinatesArray pathCopyCopy = new CoordinatesArray(pathCopy);
                Board boardCopy = currentBoard.copy();
                boardCopy.removePiece(currentCoordinate.getX() + dx / 2, currentCoordinate.getY() + dy / 2);
                findNormalPieceAttacks(boardCopy, new Coordinate(currentCoordinate.getX() + dx, currentCoordinate.getY() + dy), pathCopyCopy, pathsArray, attackBack);
            }
        }
    }

    private void findKingPieceMoves(Board currentBoard, Coordinate currentCoordinate, CoordinatesArray path, PathsArray pathsArray) {
        findKingPieceMoves(currentBoard, currentCoordinate, false, false, 1, 1, path, pathsArray);
        findKingPieceMoves(currentBoard, currentCoordinate, false, false, -1, 1, path, pathsArray);
        findKingPieceMoves(currentBoard, currentCoordinate, false, false, 1, -1, path, pathsArray);
        findKingPieceMoves(currentBoard, currentCoordinate, false, false, -1, -1, path, pathsArray);
    }

    private void findKingPieceMoves(Board currentBoard, Coordinate currentCoordinate, boolean onlyBeat, boolean beatSide, int dx, int dy, CoordinatesArray path, PathsArray pathsArray) {
        int x = currentCoordinate.getX() + dx;
        int y = currentCoordinate.getY() + dy;

        while(x < currentBoard.getSize() && y < currentBoard.getSize() && x >= 0 && y >= 0) {
            if(currentBoard.coordinateIsFree(x, y)) { // simple move
                if (!onlyBeat) {
                    CoordinatesArray pathCopy = new CoordinatesArray(path);
                    pathCopy.add(x, y);
                    pathsArray.add(pathCopy);
                    if (beatSide) {
                        findKingPieceMoves(currentBoard.copy(), new Coordinate(x, y), true, false, -dx, dy, new CoordinatesArray(pathCopy), pathsArray);
                        findKingPieceMoves(currentBoard.copy(), new Coordinate(x, y), true, false, dx, -dy, new CoordinatesArray(pathCopy), pathsArray);
                    }
                }
            }
            else if(currentBoard.coordinateIsWithPiece(x, y, color) || !currentBoard.coordinateIsFree(x + dx, y + dy)) { // unable to move
                if (!onlyBeat) {
                    CoordinatesArray pathCopy = new CoordinatesArray(path);
                    if(pathCopy.size() > 0)
                        pathsArray.add(pathCopy);
                }
                break;
            }
            else { // beat opponent's piece
                Board boardCopy = currentBoard.copy();
                boardCopy.removePiece(x, y);

                x += dx;
                y += dy;

                CoordinatesArray pathCopy = new CoordinatesArray(path);
                pathCopy.addAttack();
                pathCopy.add(x, y);
                pathsArray.add(pathCopy);
                
                findKingPieceMoves(boardCopy.copy(), new Coordinate(x , y), false, true, dx, dy, new CoordinatesArray(pathCopy), pathsArray);
                findKingPieceMoves(boardCopy.copy(), new Coordinate(x , y), true, false, -dx, dy, new CoordinatesArray(pathCopy), pathsArray);
                findKingPieceMoves(boardCopy.copy(), new Coordinate(x , y), true, false, dx, -dy, new CoordinatesArray(pathCopy), pathsArray);
                break;
            }

            x += dx;
            y += dy;
        }
    }
}
