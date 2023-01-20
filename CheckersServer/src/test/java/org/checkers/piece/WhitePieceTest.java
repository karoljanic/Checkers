package org.checkers.piece;

import org.checkers.enums.CheckerColor;
import org.checkers.enums.CheckerType;
import org.checkers.enums.MoveDirection;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WhitePieceTest {
    @Test
    public void testWhitePieceCreatingAndCopying() {
        WhitePiece whitePiece = new WhitePiece(10, 5);
        assertEquals(CheckerColor.WHITE, whitePiece.getColor());
        assertEquals(CheckerType.NORMAL, whitePiece.getType());
        assertEquals(MoveDirection.UP, whitePiece.getMoveDirection());

        WhitePiece whitePieceCopy = new WhitePiece(whitePiece);
        assertEquals(CheckerColor.WHITE, whitePieceCopy.getColor());
        assertEquals(CheckerType.NORMAL, whitePieceCopy.getType());
        assertEquals(MoveDirection.UP, whitePieceCopy.getMoveDirection());
    }
}
