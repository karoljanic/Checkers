package org.checkers.boards;

import java.util.ArrayList;
import java.awt.geom.Point2D;
import org.checkers.boards.Piece.Color;

public abstract class Board {

    protected ArrayList<ArrayList<Point2D>> currentPossibleMoves;
    protected final ArrayList<Piece> pieces;

    protected Piece findPiece(Point2D point, Color color) {
        for (Piece piece : pieces) {
            if (piece.getColor().equals(color) && point.equals(piece.getPosision()))
                return piece;
        }
        return null;
    }

    private int howManyPieces(Color color) {
        int count = 0;
        for (Piece piece : pieces) {
            if (piece.getColor().equals(color))
                count++;
        }
        return count;
    }

    protected abstract void initializePieces();

    public Board() {
        pieces = new ArrayList<>();
        initializePieces();
    }

    public abstract ArrayList<ArrayList<Point2D>> getPossibleMoves(Color color);

    public boolean move(Point2D pointBefore, Point2D pointAfter, Color whosMove) {
        Piece pieceMakingMove = findPiece(pointAfter, whosMove);

        Piece pieceOnDest = findPiece(pointAfter, Color.WHITE);
        if (pieceOnDest == null)
            pieceOnDest = findPiece(pointAfter, Color.BLACK);
        
        if (pieceMakingMove == null || pieceOnDest != null
            || !getPossibleMoves(whosMove).get(pieces.indexOf(pieceMakingMove)).contains(pointAfter))
            return false;
            
        pieceMakingMove.move(pointAfter);
        
        return true;
        
    }

    public Color whoWins() {
        if (howManyPieces(Color.WHITE) == 0)
            return Color.BLACK;
        if (howManyPieces(Color.BLACK) == 0)
            return Color.WHITE;
        return null;
    }

}
