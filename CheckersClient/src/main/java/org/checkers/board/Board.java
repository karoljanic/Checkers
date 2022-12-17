package org.checkers.board;

import javafx.scene.paint.Paint;
import javafx.util.Pair;
import org.checkers.utils.CheckerColor;

import java.util.ArrayList;

public class Board {
    private int size;
    private Piece[][] pieces;

    public void setSize(int size) {
        this.size = size;
        pieces = new Piece[size][size];
    }

    public int getSize() { return size; }

    public void setWhitePiece(int x, int y) { pieces[x][y] = new Piece(x, y, CheckerColor.WHITE, new ArrayList<>()); }

    public void setBlackPiece(int x, int y) { pieces[x][y] = new Piece(x, y, CheckerColor.BLACK, new ArrayList<>()); }

    public void removePiece(int x, int y) { pieces[x][y] = null; }

    public CheckerColor getCheckerColor(int x, int y) {
        return pieces[x][y].getColor();
    }

    public Piece[][] getPieces() { return pieces; }

    public void addPossibleMove(int x, int y, int moveX, int moveY) {
        if(pieces[x][y] != null) {
            pieces[x][y].addPossibleMove(moveX, moveY);
        }
    }

    public void removePossibleMoves(int x, int y) {
        if(pieces[x][y] != null) {
            pieces[x][y].clearPossibleMoves();
        }
    }

    public void makeKing(int x, int y) {
        pieces[x][y].changeToKing();
    }
}
