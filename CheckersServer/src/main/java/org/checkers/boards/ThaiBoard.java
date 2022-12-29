package org.checkers.boards;

import org.checkers.piece.BlackPiece;
import org.checkers.piece.WhitePiece;
import org.checkers.piece.coordinate.PathsArray;
import org.checkers.enums.CheckerColor;

public class ThaiBoard extends Board {

    public static final int THAI_BOARD_SIZE = 8;

    public ThaiBoard() {
        super(THAI_BOARD_SIZE);

        //insert white pieces
        for (int i = 0; i <= 1; i++)
            for (int j = (i % 2 == 0 ? 1 : 0); j < 8; j += 2)
                pieces[j][i] = new WhitePiece(j, i);

        //insert black pieces
        for (int i = 7; i >= 6; i--)
            for (int j = (i % 2 == 0 ? 1 : 0); j < 8; j += 2)
                pieces[j][i]  = new BlackPiece(j, i);
    }

    public ThaiBoard(ThaiBoard thaiBoard) {
        super(thaiBoard);
    }

    @Override
    public void generatePossibleMoves() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                currentPossibleMovesForWhite[i][j] = new PathsArray();
                currentPossibleMovesForBlack[i][j] = new PathsArray();
                if(pieces[i][j] != null) {
                    if(pieces[i][j].getColor() == CheckerColor.WHITE)
                        currentPossibleMovesForWhite[i][j].add(pieces[i][j].getPossibleMoves(this, false));
                    else
                        currentPossibleMovesForBlack[i][j].add(pieces[i][j].getPossibleMoves(this, false));
                }
            }
        }
    }

    @Override
    public Board copy() {
        return new ThaiBoard(this);
    }
}
