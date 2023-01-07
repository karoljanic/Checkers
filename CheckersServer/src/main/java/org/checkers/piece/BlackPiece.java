package org.checkers.piece;

import org.checkers.enums.MoveDirection;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.CheckerType;

/**
 * klasa reprezentuje czarny pionek
 */
public class BlackPiece extends Piece {
    /**
     * @param x x-owa współrzędna
     * @param y y-owa współrzędna
     * konstruktor ustawia niezbędne parametry pionka
     */
    public BlackPiece(int x, int y) {
        super(x, y, CheckerColor.BLACK, CheckerType.NORMAL, MoveDirection.DOWN);
    }

    /**
     * @param blackPiece obiekt do skopiowania
     * funkcja kopiuje obiekt podany jako argument
     */
    public BlackPiece(BlackPiece blackPiece) { super(blackPiece); }
}
