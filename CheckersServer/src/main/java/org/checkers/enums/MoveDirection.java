package org.checkers.enums;

public enum MoveDirection {
    UP(1),
    DOWN(-1);

    private final int ordinal;

    private MoveDirection(int val) {
        this.ordinal = val;
    }

    public int getOrdinal() {
        return ordinal;
    }
}
