package org.checkers.enums;

/**
 * enum reprezentuje kierunek poruszania się pionka
 */
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
