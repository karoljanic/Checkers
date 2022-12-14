package org.checkers.boards.elements;

public class Piece {
    private final Color color;
    private Type type;

    public enum Color {WHITE, BLACK}
    public enum Type {MAN, KING}

    public Piece(Color color) {
        this.color = color;
        this.type = Type.MAN;
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
