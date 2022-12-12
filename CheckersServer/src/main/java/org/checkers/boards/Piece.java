package org.checkers.boards;

import java.awt.geom.Point2D;

public class Piece {
    private final Point2D position;
    private Type type;

    private enum Type {MAN, KING}

    public Piece(Point2D position) {
        this.position = position;
        this.type = Type.MAN;
    }

    public void move(Point2D newPosition) {
        position.setLocation(newPosition.getX(), newPosition.getY());
    }

    public void makeKing() {
        type = Type.KING;
    }
}
