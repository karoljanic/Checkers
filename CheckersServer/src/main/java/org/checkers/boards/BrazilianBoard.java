package org.checkers.boards;

import org.checkers.piece.BlackPiece;
import org.checkers.piece.WhitePiece;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;

import java.util.ArrayList;

import org.checkers.enums.CheckerColor;

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
        int maxNumOfAttacksWhite = 0;
        int maxNumOfAttacksBlack = 0;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                currentPossibleMovesForWhite[i][j] = new PathsArray();
                currentPossibleMovesForBlack[i][j] = new PathsArray();

                if (pieces[i][j] != null) {
                    PathsArray pathsArray = pieces[i][j].getPossibleMoves(this, true);

                    if(pieces[i][j].getColor() == CheckerColor.WHITE)
                        currentPossibleMovesForWhite[i][j].add(pathsArray);
                    else
                        currentPossibleMovesForBlack[i][j].add(pathsArray);

                    for (CoordinatesArray coorArray : pathsArray.getList()) {
                        if (pieces[i][j].getColor() == CheckerColor.WHITE && coorArray.getNumOfAttacks() > maxNumOfAttacksWhite)
                            maxNumOfAttacksWhite = coorArray.getNumOfAttacks();
                        if (pieces[i][j].getColor() == CheckerColor.BLACK && coorArray.getNumOfAttacks() > maxNumOfAttacksBlack)
                            maxNumOfAttacksBlack = coorArray.getNumOfAttacks();
                    }
                }
            }
        }

        if (maxNumOfAttacksWhite > 0 || maxNumOfAttacksBlack > 0) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (pieces[i][j] == null)
                        continue;

                    PathsArray pathsArray = switch (pieces[i][j].getColor()) {
                        case WHITE -> currentPossibleMovesForWhite[i][j];
                        case BLACK -> currentPossibleMovesForBlack[i][j];
                    };

                    ArrayList<CoordinatesArray> coorToRemove = new ArrayList<>();
                    for (CoordinatesArray coorArray : pathsArray.getList()) {
                        if (pieces[i][j].getColor() == CheckerColor.WHITE && coorArray.getNumOfAttacks() < maxNumOfAttacksWhite)
                            coorToRemove.add(coorArray);
                        if (pieces[i][j].getColor() == CheckerColor.BLACK && coorArray.getNumOfAttacks() < maxNumOfAttacksBlack)
                            coorToRemove.add(coorArray);
                    }
                    for (CoordinatesArray coorArray : coorToRemove) {
                        pathsArray.getList().remove(coorArray);
                    }
                }
            }
        }
    }

    @Override
    public Board copy() {
        return new BrazilianBoard(this);
    }
}
