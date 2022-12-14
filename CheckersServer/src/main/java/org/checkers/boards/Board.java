package org.checkers.boards;

import java.util.ArrayList;

import org.checkers.boards.elements.Piece;
import org.checkers.boards.elements.Point;
import org.checkers.boards.elements.Piece.Color;

public abstract class Board {

    public static final int SIZE = 0;

    protected final ArrayList<ArrayList<Point>>[][] currentPossibleMoves;
    protected final Piece[][] pieces;

    protected abstract void initializePieces();
    protected abstract ArrayList<ArrayList<Point>>[][] generatePossibleMoves();

    private int howManyPieces(Color color) {
        int count = 0;
        for (Piece[] piecesRow : pieces) {
            for (Piece piece: piecesRow) {
                if (piece != null && piece.getColor().equals(color))
                    count++;
            }
        }
        return count;
    }
    

    public Board() {
        currentPossibleMoves = new ArrayList[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                currentPossibleMoves[i][j] = new ArrayList<ArrayList<Point>>();

        pieces = new Piece[SIZE][SIZE];
        
        initializePieces();
        generatePossibleMoves();
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public ArrayList<ArrayList<Point>>[][] getPossibleMoves() {
        return currentPossibleMoves;
    }

    public boolean move(ArrayList<Point> movePoints, Color whosMove) {
        int initialX = movePoints.get(0).getX();
        int initialY = movePoints.get(0).getY();
        int finalX = movePoints.get(movePoints.size() - 1).getX();
        int finalY = movePoints.get(movePoints.size() - 1).getY();

        if (pieces[initialX][initialY] != null && pieces[initialX][initialY].getColor().equals(whosMove)
            && currentPossibleMoves[initialX][initialY].contains(movePoints)) {
            //beating
            if (movePoints.size() > 2) {
                for (int i = 1; i <= movePoints.size() - 2; i++) {
                    pieces[movePoints.get(i).getX()][movePoints.get(i).getY()] = null;
                }
            }
            //making actual move
            pieces[initialX][initialY].move(new Point(finalX, finalY));
            pieces[finalX][finalY] = pieces[initialX][initialY];
            pieces[initialX][initialY] = null;
            //making piece a KING if necessary
            if ((whosMove.equals(Color.WHITE) && finalY == SIZE - 1)
                || (whosMove.equals(Color.BLACK) && finalY == 0)) {
                pieces[finalX][finalY].makeKing();
            }

            generatePossibleMoves();

            return true;
        }
        
        return false;
    }

    public Color whoWins() {
        //if player hasn't got any piece
        if (howManyPieces(Color.WHITE) == 0)
            return Color.BLACK;
        if (howManyPieces(Color.BLACK) == 0)
            return Color.WHITE;
        //if player can't move
        int whiteMoves = 0, blackMoves = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (pieces[i][j] != null && pieces[i][j].getColor().equals(Color.WHITE))
                    whiteMoves += currentPossibleMoves[i][j].size();
                else if (pieces[i][j] != null && pieces[i][j].getColor().equals(Color.BLACK))
                    blackMoves += currentPossibleMoves[i][j].size();
            }
        }
        if (whiteMoves == 0)
            return Color.BLACK;
        if (blackMoves == 0)
            return Color.WHITE;

        return null;
        //SPYTAĆ DRA MACYNĘ CZY TRZEBA OBSŁUGIWAĆ REMIS
    }

}
