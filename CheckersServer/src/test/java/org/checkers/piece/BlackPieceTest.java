package org.checkers.piece;

import org.checkers.enums.CheckerColor;
import org.checkers.enums.CheckerType;
import org.checkers.enums.MoveDirection;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlackPieceTest {
    @Test
    public void testBlackPieceCreatingAndCopying() {
        BlackPiece blackPiece = new BlackPiece(10, 5);
        assertEquals(CheckerColor.BLACK, blackPiece.getColor());
        assertEquals(CheckerType.NORMAL, blackPiece.getType());
        assertEquals(MoveDirection.DOWN, blackPiece.getMoveDirection());

        BlackPiece blackPieceCopy = new BlackPiece(blackPiece);
        assertEquals(CheckerColor.BLACK, blackPieceCopy.getColor());
        assertEquals(CheckerType.NORMAL, blackPieceCopy.getType());
        assertEquals(MoveDirection.DOWN, blackPieceCopy.getMoveDirection());
    }
}
