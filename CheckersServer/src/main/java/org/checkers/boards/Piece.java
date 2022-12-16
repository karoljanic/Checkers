package org.checkers.boards;

import org.checkers.utils.CheckerColor;
import org.checkers.utils.CheckerType;

public class Piece {
    private int x;
    private int y;
    private final CheckerColor color;
    private CheckerType type;

    public Piece(int x, int y, CheckerColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.type = CheckerType.NORMAL;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public CheckerColor getColor() {
        return color;
    }

    public CheckerType getType() {
        return type;
    }

    public void makeKing() {
        type = CheckerType.KING;
    }

    @Override
    protected Object clone() {
        try { return super.clone(); }
        catch (final CloneNotSupportedException exception) {
            return null;
        }
    }
}
