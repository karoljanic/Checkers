package org.checkers.boards;

import org.checkers.enums.CheckerColor;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void testGettingPieces() {
        ThaiBoard thaiBoard = new ThaiBoard();

        assertEquals(8, thaiBoard.getPieces(CheckerColor.WHITE).size());
        assertEquals(8, thaiBoard.getPieces(CheckerColor.BLACK).size());
    }

    @Test
    public void testIfCoordinateIsFree() {
        ThaiBoard thaiBoard = new ThaiBoard();

        assertTrue(thaiBoard.coordinateIsFree(0, 0));
        assertFalse(thaiBoard.coordinateIsFree(1, 0));
        assertFalse(thaiBoard.coordinateIsFree(100, 0));
    }

    @Test
    public void testIfCoordinateIsPieceIsKing() {
        ThaiBoard thaiBoard = new ThaiBoard();

        assertFalse(thaiBoard.isKing(0, 0));
        assertFalse(thaiBoard.isKing(1, 0));
    }

    @Test
    public void testGettingPossibleMoves() {
        Board internationalBoard = new InternationalBoard();
        internationalBoard.move(1, 6, 0, 5, CheckerColor.BLACK);
        internationalBoard.move(5, 6, 6, 5, CheckerColor.BLACK);
        internationalBoard.move(7, 6, 8, 5, CheckerColor.BLACK);
        internationalBoard.move(2, 3, 1, 4, CheckerColor.WHITE);
        internationalBoard.move(4, 3, 3, 4, CheckerColor.WHITE);
        internationalBoard.move(6, 3, 5, 4, CheckerColor.WHITE);

        internationalBoard.generatePossibleMoves();

        CoordinatesArray possibleMove1 = internationalBoard.getPossibleMove(0, 5, 6, 3, CheckerColor.BLACK);
        CoordinatesArray possibleMove2 = internationalBoard.getPossibleMove(0, 6, 6, 5, CheckerColor.BLACK);
        CoordinatesArray possibleMove3 = internationalBoard.getPossibleMove(5, 4, 9, 4, CheckerColor.WHITE);

        assertNotNull(possibleMove1);
        assertNull(possibleMove2);
        assertNotNull(possibleMove3);
    }

    @Test
    public void testCheckingWinnerDraw() {
        Board board = new ThaiBoard();

        board.removePiece(1, 0);
        board.removePiece(3, 0);
        board.removePiece(5, 0);
        board.removePiece(7, 0);
        board.removePiece(0, 1);
        board.removePiece(2, 1);
        board.removePiece(4, 1);
        board.removePiece(6, 1);

        board.removePiece(0, 7);
        board.removePiece(2, 7);
        board.removePiece(4, 7);
        board.removePiece(6, 7);
        board.removePiece(1, 6);
        board.removePiece(3, 6);
        board.removePiece(5, 6);
        board.removePiece(7, 6);

        assertEquals("draw", board.whoWins());
    }

    @Test
    public void testCheckingWinnerNoWinner() {
        Board board = new ThaiBoard();

        board.removePiece(1, 0);
        board.removePiece(3, 0);
        board.removePiece(6, 1);

        board.removePiece(3, 6);
        board.removePiece(5, 6);
        board.removePiece(7, 6);

        assertNull(board.whoWins());
    }

    @Test
    public void testCheckingWinnerWhite() {
        Board board = new ThaiBoard();

        board.removePiece(1, 0);
        board.removePiece(3, 0);
        board.removePiece(5, 0);
        board.removePiece(7, 0);
        board.removePiece(2, 1);
        board.removePiece(4, 1);
        board.removePiece(6, 1);

        board.removePiece(0, 7);
        board.removePiece(2, 7);
        board.removePiece(4, 7);
        board.removePiece(6, 7);
        board.removePiece(1, 6);
        board.removePiece(3, 6);
        board.removePiece(5, 6);
        board.removePiece(7, 6);

        assertEquals("white", board.whoWins());
    }

    @Test
    public void testCheckingWinnerBlack() {
        Board board = new ThaiBoard();

        board.removePiece(1, 0);
        board.removePiece(3, 0);
        board.removePiece(5, 0);
        board.removePiece(7, 0);
        board.removePiece(0, 1);
        board.removePiece(2, 1);
        board.removePiece(4, 1);
        board.removePiece(6, 1);

        board.removePiece(0, 7);
        board.removePiece(2, 7);
        board.removePiece(4, 7);
        board.removePiece(6, 7);
        board.removePiece(1, 6);
        board.removePiece(3, 6);
        board.removePiece(7, 6);

        assertEquals("black", board.whoWins());
    }
}
