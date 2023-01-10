package org.checkers.piece;

import org.checkers.boards.Board;
import org.checkers.boards.ThaiBoard;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.CheckerType;
import org.checkers.enums.MoveDirection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {
    @Test
    public void testPieceCreating() {
        Piece piece = new Piece(10, 4, CheckerColor.WHITE, CheckerType.NORMAL, MoveDirection.UP);

        assertEquals(10, piece.getX());
        assertEquals(4, piece.getY());
        assertEquals(CheckerColor.WHITE, piece.getColor());
        assertEquals(CheckerType.NORMAL, piece.getType());
        assertEquals(MoveDirection.UP, piece.getMoveDirection());
    }

    @Test
    public void testPieceCopying() {
        Piece piece = new Piece(4, 10, CheckerColor.BLACK, CheckerType.KING, MoveDirection.DOWN);
        Piece pieceCopy = new Piece(piece);

        assertEquals(4, pieceCopy.getX());
        assertEquals(10, pieceCopy.getY());
        assertEquals(CheckerColor.BLACK, pieceCopy.getColor());
        assertEquals(CheckerType.KING, pieceCopy.getType());
        assertEquals(MoveDirection.DOWN, pieceCopy.getMoveDirection());
    }

    @Test
    public void testMovingPiece() {
        WhitePiece whitePiece = new WhitePiece(2, 3);
        assertEquals(2, whitePiece.getX());
        assertEquals(3, whitePiece.getY());

        whitePiece.moveTo(3, 2);
        assertEquals(3, whitePiece.getX());
        assertEquals(2, whitePiece.getY());
    }

    @Test
    public void testMakingKing() {
        WhitePiece whitePiece = new WhitePiece(2, 3);
        assertEquals(CheckerType.NORMAL, whitePiece.getType());

        whitePiece.makeKing();
        assertEquals(CheckerType.KING, whitePiece.getType());
    }

    @Test
    public void testKingMovesGenerating() {
        Board board = new ThaiBoard();
        board.removePiece(1, 0);
        board.removePiece(3, 0);
        board.removePiece(5, 0);
        board.removePiece(7, 0);
        board.removePiece(0, 1);
        board.removePiece(2, 1);
        board.removePiece(4, 1);

        board.removePiece(0, 7);
        board.removePiece(2, 7);
        board.removePiece(4, 7);
        board.removePiece(6, 7);

        board.move(6, 1, 4, 7, CheckerColor.WHITE);
        board.move(1, 6, 1, 0, CheckerColor.BLACK);
        board.move(1, 0, 1, 2, CheckerColor.BLACK);
        board.move(3, 6, 2, 5, CheckerColor.BLACK);
        board.move(5, 6, 5, 2, CheckerColor.BLACK);
        board.move(7, 6, 6, 5, CheckerColor.BLACK);

        assertTrue(board.isKing(4, 7));
        assertTrue(board.isKing(1, 2));

        board.generatePossibleMoves();

        assertEquals(15, board.getPossibleMoves(CheckerColor.WHITE)[4][7].size());
    }
}
