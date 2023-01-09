package org.checkers.boards;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BrazilianBoardTest {
    @Test
    public void testCreatingBoard() {
        BrazilianBoard brazilianBoardTest = new BrazilianBoard();
        assertEquals(8, brazilianBoardTest.getSize());
    }

    @Test
    public void testCopyingBoard() {
        BrazilianBoard brazilianBoard = new BrazilianBoard();
        BrazilianBoard brazilianBoardCopy = new BrazilianBoard(brazilianBoard);
        assertEquals(8, brazilianBoardCopy.getSize());
    }

    @Test
    public void testGeneratingPossibleMoves() {

    }
}
