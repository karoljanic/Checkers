package org.checkers.boards;

import java.util.ArrayList;

import org.checkers.boards.elements.Piece;
import org.checkers.boards.elements.Point;
import org.checkers.boards.elements.Piece.Color;

public abstract class Board {

    public static final int SIZE = 0;

    protected final ArrayList<ArrayList<Point>>[][] currentPossibleMoves;
    protected final Piece[][] pieces;

    private int howManyPieces(Color color) {
        int count = 0;
        for (Piece[] piecesRow : pieces) {
            for (Piece piece: piecesRow) {
                if (piece.getColor().equals(color))
                    count++;
            }
        }
        return count;
    }

    protected abstract void initializePieces();

    public Board() {
        currentPossibleMoves = new ArrayList[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                currentPossibleMoves[i][j] = new ArrayList<ArrayList<Point>>();

        pieces = new Piece[SIZE][SIZE];
        
        initializePieces();
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public abstract ArrayList<ArrayList<Point>>[][] getPossibleMoves(Color color);

    public boolean move(ArrayList<Point> movPoints, Color whosMove) {
        //TODO: delete that trash and write sth purposeful

        /*Piece pieceMakingMove = pieces[pointBefore.getX()][pointBefore.getY()];
        if (!pieceMakingMove.getColor().equals(whosMove))
            return false;

        Piece pieceOnDest = pieces[pointAfter.getX()][pointAfter.getY()];
        */
        
        /*if (pieceOnDest != null || !getPossibleMoves(whosMove).get(pieces.indexOf(pieceMakingMove)).contains(pointAfter))
            return false; */
            
        //pieceMakingMove.move(pointAfter);
        
        return true;
        
    }

    public Color whoWins() {
        //TODO: end by blocking enemy's moves
        //TODO: handle draw (?)
        if (howManyPieces(Color.WHITE) == 0)
            return Color.BLACK;
        if (howManyPieces(Color.BLACK) == 0)
            return Color.WHITE;
        return null;
    }

}
