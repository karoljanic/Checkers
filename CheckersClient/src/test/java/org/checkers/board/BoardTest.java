package org.checkers.board;

import javafx.util.Pair;
import org.checkers.utils.CheckerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private static Board boardForSizeSetting;
    private static Board boardForHostSetting;
    private static Board boardForOperationsOnPieces;

    @Before
    public void initializeBoards() {
        boardForSizeSetting = new Board();
        boardForHostSetting = new Board();

        boardForOperationsOnPieces = new Board();
        boardForOperationsOnPieces.setSize(16);
    }

    @Test
    public void testSizeSetting() {
        boardForOperationsOnPieces.setSize(10);
        assertEquals(10, boardForOperationsOnPieces.getSize());
    }

    @Test
    public void testHostSetting() {
        boardForHostSetting.setHost(true);
        assertTrue(boardForHostSetting.isHost());
    }

    @Test
    public void testSettingPiece() {
        boardForOperationsOnPieces.setWhitePiece(2, 3);
        boardForOperationsOnPieces.setBlackPiece(4, 5);

        Piece[][] pieces = boardForOperationsOnPieces.getPieces();

        assertNotNull(pieces[2][3]);
        assertNotNull(pieces[4][5]);

        assertEquals(CheckerColor.WHITE, pieces[2][3].getColor());
        assertEquals(CheckerColor.BLACK, pieces[4][5].getColor());

        assertEquals(CheckerColor.WHITE, boardForOperationsOnPieces.getCheckerColor(2, 3));
        assertEquals(CheckerColor.BLACK, boardForOperationsOnPieces.getCheckerColor(4, 5));
    }

    @Test
    public void testMakingKing() {
        boardForOperationsOnPieces.setWhitePiece(0, 1);
        boardForOperationsOnPieces.makeKing(0, 1);

        assertTrue(boardForOperationsOnPieces.isKing(0, 1));
        assertFalse(boardForOperationsOnPieces.isKing(4, 1));
    }

    @Test
    public void testRemovingPiece() {
        boardForOperationsOnPieces.setBlackPiece(7, 5);
        assertNotNull(boardForOperationsOnPieces.getPieces()[7][5]);

        boardForOperationsOnPieces.removePiece(7, 5);
        assertNull(boardForOperationsOnPieces.getPieces()[7][5]);
    }

    @Test
    public void testAddingAndRemovingPossibleMoves() {
        Pair<Integer, Integer > possibleMovesMove1 = new Pair<>(2, 3);
        Pair<Integer, Integer > possibleMovesMove2 = new Pair<>(5, 1);

        ArrayList<Pair<Integer, Integer>> possibleMoves = new ArrayList<>();
        possibleMoves.add(possibleMovesMove1);
        possibleMoves.add(possibleMovesMove2);

        boardForOperationsOnPieces.setWhitePiece(11, 13);
        boardForOperationsOnPieces.setWhitePiece(13, 11);

        boardForOperationsOnPieces.addPossibleMove(11, 13, possibleMoves);
        boardForOperationsOnPieces.addPossibleMove(13, 11, possibleMoves);

        boardForOperationsOnPieces.removePossibleMoves(11, 13);
        boardForOperationsOnPieces.removePossibleMoves(13, 11);
    }
}
