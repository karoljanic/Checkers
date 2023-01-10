package org.checkers.boards;

import org.checkers.enums.CheckerColor;
import org.checkers.piece.coordinate.PathsArray;
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
    public void testCopyingBoard1() {
        BrazilianBoard brazilianBoard = new BrazilianBoard();
        BrazilianBoard brazilianBoardCopy = new BrazilianBoard(brazilianBoard);
        assertEquals(8, brazilianBoardCopy.getSize());
    }

    @Test
    public void testCopyingBoard2() {
        BrazilianBoard brazilianBoard = new BrazilianBoard();
        Board brazilianBoardCopy = brazilianBoard.copy();
        assertEquals(8, brazilianBoardCopy.getSize());
    }

    @Test
    public void testGeneratingPossibleMoves() {
        BrazilianBoard brazilianBoard = new BrazilianBoard();
        brazilianBoard.move(5, 2, 4, 3, CheckerColor.WHITE);
        brazilianBoard.move(2, 5, 3, 4, CheckerColor.BLACK);

        brazilianBoard.generatePossibleMoves();
        PathsArray[][] possibleMovesWhite = brazilianBoard.getPossibleMoves(CheckerColor.WHITE);
        PathsArray[][] possibleMovesBlack = brazilianBoard.getPossibleMoves(CheckerColor.BLACK);

        assertEquals(1, possibleMovesWhite[4][3].size());
        assertEquals(1, possibleMovesBlack[3][4].size());
    }
}
