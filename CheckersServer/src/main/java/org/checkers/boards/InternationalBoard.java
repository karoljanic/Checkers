package org.checkers.boards;

import org.checkers.piece.BlackPiece;
import org.checkers.piece.WhitePiece;
import org.checkers.piece.coordinate.PathsArray;
import org.checkers.enums.CheckerColor;

public class InternationalBoard extends Board {
    public static final int INTERNATIONAL_BOARD_SIZE = 10;

    public InternationalBoard() {
        super(INTERNATIONAL_BOARD_SIZE);

        //insert white pieces
        for (int i = 0; i <= 3; i++)
            for (int j = (i % 2 == 0 ? 1 : 0); j < 10; j += 2)
                pieces[j][i] = new WhitePiece(j, i);

        //insert black pieces
        for (int i = 9; i >= 6; i--)
            for (int j = (i % 2 == 0 ? 1 : 0); j < 10; j += 2)
                pieces[j][i] = new BlackPiece(j, i);
    }

    public InternationalBoard(InternationalBoard internationalBoard) {
        super(internationalBoard);
    }

    @Override
    public void generatePossibleMoves() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                currentPossibleMovesForWhite[i][j] = new PathsArray();
                currentPossibleMovesForBlack[i][j] = new PathsArray();
                if(pieces[i][j] != null) {
                    if(pieces[i][j].getColor() == CheckerColor.WHITE)
                        currentPossibleMovesForWhite[i][j].add(pieces[i][j].getPossibleMoves(this, true));
                    else
                        currentPossibleMovesForBlack[i][j].add(pieces[i][j].getPossibleMoves(this, true));
                }
            }
        }
    }

    @Override
    public Board copy() {
        return new InternationalBoard(this);
    }
}
