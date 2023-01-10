package org.checkers.boards;

import org.checkers.enums.CheckerColor;
import org.checkers.piece.coordinate.PathsArray;
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
    public void testCopyingBoard1() {
        InternationalBoard internationalBoard = new InternationalBoard();
        InternationalBoard internationalBoardCopy = new InternationalBoard(internationalBoard);
        assertEquals(10, internationalBoardCopy.getSize());
    }

    @Test
    public void testCopyingBoard2() {
        InternationalBoard internationalBoard = new InternationalBoard();
        Board internationalBoardCopy = internationalBoard.copy();
        assertEquals(10, internationalBoardCopy.getSize());
    }


    @Test
    public void testGeneratingPossibleMoves() {
        InternationalBoard internationalBoard = new InternationalBoard();
        internationalBoard.move(1, 6, 0, 5, CheckerColor.BLACK);
        internationalBoard.move(5, 6, 6, 5, CheckerColor.BLACK);
        internationalBoard.move(7, 6, 8, 5, CheckerColor.BLACK);
        internationalBoard.move(2, 3, 1, 4, CheckerColor.WHITE);
        internationalBoard.move(4, 3, 3, 4, CheckerColor.WHITE);
        internationalBoard.move(6, 3, 5, 4, CheckerColor.WHITE);

        internationalBoard.generatePossibleMoves();
        PathsArray[][] possibleMovesWhite = internationalBoard.getPossibleMoves(CheckerColor.WHITE);
        PathsArray[][] possibleMovesBlack = internationalBoard.getPossibleMoves(CheckerColor.BLACK);

        assertEquals(0, possibleMovesWhite[1][1].size());
        assertEquals(1, possibleMovesBlack[0][5].size());
    }
}
