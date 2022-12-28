package org.checkers.piece;

import org.checkers.enums.MoveDirection;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.CheckerType;

public class WhitePiece extends Piece {

    public WhitePiece(int x, int y) {
        super(x, y, CheckerColor.WHITE, CheckerType.NORMAL, MoveDirection.UP);
    }

    public WhitePiece(WhitePiece whitePiece) { super(whitePiece); }
}
