package org.checkers.piece;

import org.checkers.enums.MoveDirection;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.CheckerType;

public class BlackPiece extends Piece {

    public BlackPiece(int x, int y) {
        super(x, y, CheckerColor.BLACK, CheckerType.NORMAL, MoveDirection.DOWN);
    }

    public BlackPiece(BlackPiece blackPiece) { super(blackPiece); }
}
