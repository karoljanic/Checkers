package org.checkers.piece;

import org.checkers.enums.MoveDirection;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.CheckerType;

/**
 * klasa reprezentuje biały pionek
 */
public class WhitePiece extends Piece {
    /**
     * @param x x-owa współrzędna
     * @param y y-owa współrzędna
     * konstruktor ustawia niezbędne parametry pionka
     */
    public WhitePiece(int x, int y) {
        super(x, y, CheckerColor.WHITE, CheckerType.NORMAL, MoveDirection.UP);
    }

    /**
     * @param blackPiece obiekt do skopiowania
     * funkcja kopiuje obiekt podany jako argument
     */
    public WhitePiece(WhitePiece whitePiece) { super(whitePiece); }
}
