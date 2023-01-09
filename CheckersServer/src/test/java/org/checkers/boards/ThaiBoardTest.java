package org.checkers.boards;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ThaiBoardTest {
    @Test
    public void testCreatingBoard() {
        ThaiBoard thaiBoard = new ThaiBoard();
        assertEquals(8, thaiBoard.getSize());
    }

    @Test
    public void testCopyingBoard() {
        ThaiBoard thaiBoard = new ThaiBoard();
        ThaiBoard thaiBoardCopy = new ThaiBoard(thaiBoard);
        assertEquals(8, thaiBoardCopy.getSize());
    }

    @Test
    public void testGeneratingPossibleMoves() {

    }
}
