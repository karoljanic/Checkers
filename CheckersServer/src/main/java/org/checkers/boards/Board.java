package org.checkers;

import java.util.ArrayList;
import java.awt.geom.Point2D;

public abstract class Board {
    final ArrayList<Piece> whitePieces;
    final ArrayList<Piece> blackPieces;

    protected abstract boolean canMove(Point2D pointBefore, Point2D pointAfter);

    public Board() {
        whitePieces = null;
        blackPieces = null;
    }

    public boolean move(Point2D pointBefore, Point2D pointAfter) {

        return true;
    }
    
}
