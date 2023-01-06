package org.checkers.boards;

import org.checkers.piece.coordinate.Coordinate;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.Piece;
import org.checkers.piece.coordinate.PathsArray;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.CheckerType;

public abstract class Board {
    protected final int size;
    protected final Piece[][] pieces;
    protected PathsArray[][] currentPossibleMovesForWhite;
    protected PathsArray[][] currentPossibleMovesForBlack;

    public Board(int size) {
        this.size = size;

        pieces = new Piece[size][size];
        currentPossibleMovesForWhite = new PathsArray[size][size];
        currentPossibleMovesForBlack = new PathsArray[size][size];
    }

    public Board(Board board) {
        this.size = board.size;
        this.pieces = new Piece[board.size][board.size];
        this.currentPossibleMovesForWhite = new PathsArray[board.size][board.size];
        this.currentPossibleMovesForBlack = new PathsArray[board.size][board.size];

        for(int i = 0; i < board.size; i++) {
            for(int j = 0; j < board.size; j++) {
                if(board.pieces[i][j] == null)
                    this.pieces[i][j] = null;
                else
                    this.pieces[i][j] = new Piece(board.pieces[i][j]);

                if(board.currentPossibleMovesForWhite[i][j] == null)
                    this.currentPossibleMovesForWhite[i][j] = null;
                else
                    this.currentPossibleMovesForWhite[i][j] = new PathsArray(board.currentPossibleMovesForWhite[i][j]);

                if(board.currentPossibleMovesForBlack[i][j] == null)
                    this.currentPossibleMovesForBlack[i][j] = null;
                else
                    this.currentPossibleMovesForBlack[i][j] = new PathsArray(board.currentPossibleMovesForBlack[i][j]);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public CoordinatesArray getPieces(CheckerColor color) {
        CoordinatesArray result = new CoordinatesArray();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(pieces[i][j] != null && pieces[i][j].getColor() == color) {
                    result.add(pieces[i][j].getX(), pieces[i][j].getY());
                }
            }
        }

        return result;
    }

    public void removePiece(int x, int y) {
        pieces[x][y] = null;
    }

    public boolean coordinateIsFree(int x, int y) {
        if(x < 0 || x >= size || y < 0 || y >= size)
            return false;

        return pieces[x][y] == null;
    }

    public boolean coordinateIsWithPiece(int x, int y, CheckerColor color) {
        if(coordinateIsFree(x, y))
            return false;

        return pieces[x][y].getColor() == color;
    }

    public boolean isKing(int x, int y) {
        if(pieces[x][y] != null)
            return pieces[x][y].getType() == CheckerType.KING;

        return false;
    }

    public PathsArray[][] getPossibleMoves(CheckerColor checkerColor) {
        if(checkerColor == CheckerColor.WHITE)
            return currentPossibleMovesForWhite;
        return currentPossibleMovesForBlack;
    }

    public CoordinatesArray getPossibleMove(int x1, int y1, int x2, int y2, CheckerColor checkerColor) {
        PathsArray paths;
        if(checkerColor == CheckerColor.WHITE)
            paths = currentPossibleMovesForWhite[x1][y1];
        else
            paths = currentPossibleMovesForBlack[x1][y1];

        for(CoordinatesArray path: paths.getList()) {
            Coordinate lastCoordinate = path.getList().get(path.size() - 1);
            if(lastCoordinate.getX() == x2 && lastCoordinate.getY() == y2) {
                return path;
            }
        }

        return null;
    }

    public void move(int x1, int y1, int x2, int y2, CheckerColor color) {
        pieces[x2][y2] = new Piece(pieces[x1][y1]);
        pieces[x2][y2].moveTo(x2, y2);

        if(color == CheckerColor.WHITE) {
            if(y2 == size - 1) {
                pieces[x2][y2].makeKing();
            }
        }
        else {
            if(y2 == 0) {
                pieces[x2][y2].makeKing();
            }
        }

        pieces[x1][y1] = null;
    }

    public String whoWins() {
        int num_of_white_pieces = 0, num_of_black_pieces = 0;
        int num_of_white_poss = 0, num_of_black_poss = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (pieces[i][j] != null) {
                    if (pieces[i][j].getColor() == CheckerColor.WHITE) {
                        num_of_white_pieces++;
                        num_of_white_poss += currentPossibleMovesForWhite[i][j].getList().size();
                    }
                    else {
                        num_of_black_pieces++;
                        num_of_black_poss += currentPossibleMovesForBlack[i][j].getList().size();
                    }
                }
            }
        }

        if (num_of_white_poss == 0 && num_of_black_poss == 0)
            return "draw";

        if (num_of_white_pieces == 0 || num_of_white_poss == 0)
            return "black";
        if (num_of_black_pieces == 0 || num_of_black_poss == 0)
            return "white";

        return null;
    }

    public abstract void generatePossibleMoves();

    public abstract Board copy();
}
