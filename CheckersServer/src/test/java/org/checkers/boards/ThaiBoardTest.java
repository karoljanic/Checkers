package org.checkers.boards;

import org.checkers.enums.CheckerColor;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;
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
    public void testCopyingBoard1() {
        ThaiBoard thaiBoard = new ThaiBoard();
        ThaiBoard thaiBoardCopy = new ThaiBoard(thaiBoard);
        assertEquals(8, thaiBoardCopy.getSize());
    }

    @Test
    public void testCopyingBoard2() {
        ThaiBoard thaiBoard = new ThaiBoard();
        Board thaiBoardCopy = thaiBoard.copy();
        assertEquals(8, thaiBoardCopy.getSize());
    }

    @Test
    public void testGeneratingPossibleMoves() {
        ThaiBoard thaiBoard = new ThaiBoard();
        thaiBoard.move(2, 1, 4, 2, CheckerColor.WHITE);
        thaiBoard.move(4, 1, 4, 4, CheckerColor.WHITE);
        thaiBoard.move(5, 6, 5, 5, CheckerColor.BLACK);

        thaiBoard.generatePossibleMoves();
        PathsArray[][] possibleMovesWhite = thaiBoard.getPossibleMoves(CheckerColor.WHITE);
        PathsArray[][] possibleMovesBlack = thaiBoard.getPossibleMoves(CheckerColor.BLACK);

        assertEquals(1, possibleMovesWhite[4][4].size());
        assertEquals(2, possibleMovesBlack[5][5].size());
    }
}
