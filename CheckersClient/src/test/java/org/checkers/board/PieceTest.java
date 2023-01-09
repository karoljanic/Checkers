package org.checkers.board;

import javafx.util.Pair;
import org.checkers.utils.CheckerColor;
import org.checkers.utils.CheckerType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PieceTest {
    private static Piece whitePiece;
    private static Piece blackPiece;

    private static Piece pieceWithPossibleMoves;

    @Before
    public void initializePiece() {
        whitePiece = new Piece(2, 3, CheckerColor.WHITE, new ArrayList<>());
        blackPiece = new Piece(5, 7, CheckerColor.BLACK, new ArrayList<>());

        Pair<Integer, Integer > possibleMoves1Move1 = new Pair<>(2, 3);
        Pair<Integer, Integer > possibleMoves1Move2 = new Pair<>(5, 1);
        Pair<Integer, Integer > possibleMoves2Move1 = new Pair<>(4, 5);
        Pair<Integer, Integer > possibleMoves2Move2 = new Pair<>(1, 1);

        ArrayList<Pair<Integer, Integer>> possibleMoves1 = new ArrayList<>();
        possibleMoves1.add(possibleMoves1Move1);
        possibleMoves1.add(possibleMoves1Move2);
        ArrayList<Pair<Integer, Integer>> possibleMoves2 = new ArrayList<>();
        possibleMoves2.add(possibleMoves2Move1);
        possibleMoves2.add(possibleMoves2Move2);

        ArrayList<ArrayList<Pair<Integer, Integer>>> possibleMovesArray = new ArrayList<>();
        possibleMovesArray.add(possibleMoves1);
        possibleMovesArray.add(possibleMoves2);

        pieceWithPossibleMoves = new Piece(1, 4, CheckerColor.WHITE, possibleMovesArray);
    }

    @Test
    public void testInitializedFields() {
        assertEquals(2, whitePiece.getX());
        assertEquals(3, whitePiece.getY());
        assertEquals(CheckerColor.WHITE, whitePiece.getColor());
        assertFalse(whitePiece.isKing());
        assertEquals(CheckerType.NORMAL, whitePiece.getType());

        assertEquals(5, blackPiece.getX());
        assertEquals(7, blackPiece.getY());
        assertEquals(CheckerColor.BLACK, blackPiece.getColor());
        assertFalse(blackPiece.isKing());
        assertEquals(CheckerType.NORMAL, blackPiece.getType());

        ArrayList<ArrayList<Pair<Integer, Integer>>> possibleMoves = pieceWithPossibleMoves.getPossibleMoves();
        assertEquals(2, possibleMoves.size());
        assertEquals(2, possibleMoves.get(0).size());
        assertEquals(2, possibleMoves.get(1).size());
    }

    @Test
    public void testChangingPieceType() {
        whitePiece.changeToKing();

        assertTrue(whitePiece.isKing());
    }

    @Test
    public void testAddingPossibleMoves() {
        Pair<Integer, Integer > newPossibleMovesMove1 = new Pair<>(9, 7);
        Pair<Integer, Integer > newPossibleMovesMove2 = new Pair<>(0, 0);
        ArrayList<Pair<Integer, Integer>> newPossibleMoves = new ArrayList<>();
        newPossibleMoves.add(newPossibleMovesMove1);
        newPossibleMoves.add(newPossibleMovesMove2);

        pieceWithPossibleMoves.addPossibleMove(newPossibleMoves);

        assertEquals(3, pieceWithPossibleMoves.getPossibleMoves().size());
    }

    @Test
    public void testClearingPossibleMoves() {
        pieceWithPossibleMoves.clearPossibleMoves();

        assertTrue(pieceWithPossibleMoves.getPossibleMoves().isEmpty());
    }
}
