package org.checkers.boards;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InternationalBoardTest {
    @Test
    public void testCreatingBoard() {
        InternationalBoard internationalBoard = new InternationalBoard();
        assertEquals(10, internationalBoard.getSize());
    }

    @Test
    public void testCopyingBoard() {
        InternationalBoard internationalBoard = new InternationalBoard();
        InternationalBoard internationalBoardCopy = new InternationalBoard(internationalBoard);
        assertEquals(10, internationalBoardCopy.getSize());
    }

    @Test
    public void testGeneratingPossibleMoves() {

    }
}
