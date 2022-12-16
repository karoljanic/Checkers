package org.checkers.boards;

import java.net.CookieHandler;
import java.util.ArrayList;

import org.checkers.utils.CheckerColor;

public abstract class Board {

    public class PiecesArray {
        private ArrayList<Piece> pieces;
        public PiecesArray() {
            pieces = new ArrayList<>();
        }

        public int size() {
            return pieces.size();
        }

        public void add(Piece piece) {
            pieces.add(piece);
        }

        public ArrayList<Piece> getList() {
            return pieces;
        }
    };

    protected final int size;
    protected Piece[][] pieces;
    protected PiecesArray[][] currentPossibleMoves;

    protected abstract void initializePieces();
    public abstract void generatePossibleMoves();
    public abstract boolean moveIsCorrect(int x1, int y1, int x2, int y2);
    public abstract void move(int x1, int y1, int x2, int y2, CheckerColor whosMove);

    public Board(int size) {
        this.size = size;

        pieces = new Piece[size][size];
        initializePieces();

        restartCurrentPossibleMovesArray();
        generatePossibleMoves();
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Piece> getPieces(CheckerColor color) {
        ArrayList<Piece> result = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(pieces[i][j] != null && pieces[i][j].getColor() == color) {
                    result.add(pieces[i][j]);
                }
            }
        }
        return result;
    }

    public PiecesArray[][] getPossibleMoves() {
        return currentPossibleMoves;
    }

    public CheckerColor whoWins() {
        //if player hasn't got any piece
        if (getPieces(CheckerColor.WHITE).size() == 0)
            return CheckerColor.BLACK;
        if (getPieces(CheckerColor.BLACK).size() == 0)
            return CheckerColor.WHITE;

        //if player can't move
        int whiteMoves = 0, blackMoves = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (pieces[i][j] != null && pieces[i][j].getColor().equals(CheckerColor.WHITE))
                    whiteMoves += currentPossibleMoves[i][j].size();
                else if (pieces[i][j] != null && pieces[i][j].getColor().equals(CheckerColor.BLACK))
                    blackMoves += currentPossibleMoves[i][j].size();
            }
        }
        if (whiteMoves == 0)
            return CheckerColor.BLACK;
        if (blackMoves == 0)
            return CheckerColor.WHITE;

        return null;
        //SPYTAĆ DRA MACYNĘ CZY TRZEBA OBSŁUGIWAĆ REMIS
    }

    protected void restartCurrentPossibleMovesArray() {
        currentPossibleMoves = new PiecesArray[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                currentPossibleMoves[i][j] = new PiecesArray();
    }
}
