package org.checkers.board;

public class Board {
    private int size;
    private boolean[][] whitePieces;
    private boolean[][] blackPieces;

    public void setSize(int size) {
        this.size = size;
        whitePieces = new boolean[size][size];
        blackPieces = new boolean[size][size];
    }

    public int getSize() { return size; }

    public void setWhitePiece(int x, int y) { whitePieces[x][y] = true; }
    public void removeWhitePiece(int x, int y) { whitePieces[x][y] = false; }
    public boolean[][] getWhitePieces() { return whitePieces; }

    public void setBlackPiece(int x, int y) { blackPieces[x][y] = true; }
    public void removeBlackPiece(int x, int y) { blackPieces[x][y] = false; }
    public boolean[][] getBlackPieces() { return blackPieces; }
}
