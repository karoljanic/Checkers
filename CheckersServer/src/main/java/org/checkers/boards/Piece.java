package org.checkers.boards;

import java.awt.geom.Point2D;

public class Piece {
    private final Point2D position;
    private final Color color;
    private Type type;

    public enum Color {WHITE, BLACK}
    public enum Type {MAN, KING}

    public Piece(Point2D position, Color color) {
        this.position = position;
        this.color = color;
        this.type = Type.MAN;
    }

    public void move(Point2D newPosition) {
        position.setLocation(newPosition.getX(), newPosition.getY());
    }

    public Point2D getPosision() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public void makeKing() {
        type = Type.KING;
    }

    @Override
    protected Object clone() {
        try { return super.clone(); }
        catch (final CloneNotSupportedException exception) {
            return null;
        }
    }
}
