package org.checkers.boards.elements;

public class Piece {
    private final Point position;
    private final Color color;
    private Type type;

    public enum Color {WHITE, BLACK}
    public enum Type {MAN, KING}

    public Piece(Point position, Color color) {
        this.position = position;
        this.color = color;
        this.type = Type.MAN;
    }

    public void move(Point newPosition) {
        position.set(newPosition.getX(), newPosition.getY());
    }

    public Point getPosision() {
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
