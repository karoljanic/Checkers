package org.checkers.boards;

import org.checkers.piece.BlackPiece;
import org.checkers.piece.WhitePiece;
import org.checkers.piece.coordinate.PathsArray;
import org.checkers.utils.CheckerColor;

public class BrazilianBoard extends Board {

    public static final int BRAZILIAN_BOARD_SIZE = 8;

    public BrazilianBoard() {
        super(BRAZILIAN_BOARD_SIZE);

        //insert white pieces
        for (int i = 0; i <= 2; i++)
            for (int j = (i % 2 == 0 ? 1 : 0); j < 8; j += 2)
                pieces[j][i] = new WhitePiece(j, i);

        //insert black pieces
        for (int i = 7; i >= 5; i--)
            for (int j = (i % 2 == 0 ? 1 : 0); j < 8; j += 2)
                pieces[j][i] = new BlackPiece(j, i);
    }

    public BrazilianBoard(BrazilianBoard brazilianBoard) {
        super(brazilianBoard);
    }

    @Override
    public void generatePossibleMoves() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                currentPossibleMovesForWhite[i][j] = new PathsArray();
                currentPossibleMovesForBlack[i][j] = new PathsArray();
                if(pieces[i][j] != null) {
                    if(pieces[i][j].getColor() == CheckerColor.WHITE)
                        currentPossibleMovesForWhite[i][j].add(pieces[i][j].getPossibleMoves(this));
                    else
                        currentPossibleMovesForBlack[i][j].add(pieces[i][j].getPossibleMoves(this));
                }
            }
        }
    }

    @Override
    public Board copy() {
        return new BrazilianBoard(this);
    }
}
