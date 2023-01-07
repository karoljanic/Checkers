package org.checkers.boards;

import org.checkers.piece.BlackPiece;
import org.checkers.piece.WhitePiece;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;

import java.util.ArrayList;

import org.checkers.enums.CheckerColor;

/**
 * klasa reprezentuje planszę do gry w warcaby w odmianie tajskiej
 */
public class ThaiBoard extends Board {
    /**
     * rozmiar planszy
     */
    public static final int THAI_BOARD_SIZE = 8;

    /**
     * konstruktor poza odpowiednimi parametrami ustawia także pionki na początkowych pozycjach
     */
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

    /**
     * @param thaiBoard instacja do skopiowania
     * konstruktor tworzy nowy obiekt identyczny z podanym jako parametr
     */
    public ThaiBoard(ThaiBoard thaiBoard) {
        super(thaiBoard);
    }

    /* (non-Javadoc)
     * @see org.checkers.boards.Board#generatePossibleMoves()
     */
    @Override
    public void generatePossibleMoves() {
        boolean canWhiteAttack = false;
        boolean canBlackAttack = false;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                currentPossibleMovesForWhite[i][j] = new PathsArray();
                currentPossibleMovesForBlack[i][j] = new PathsArray();
                if(pieces[i][j] != null) {
                    PathsArray pathsArray = pieces[i][j].getPossibleMoves(this, false);

                    if(pieces[i][j].getColor() == CheckerColor.WHITE)
                        currentPossibleMovesForWhite[i][j].add(pathsArray);
                    else
                        currentPossibleMovesForBlack[i][j].add(pathsArray);

                    for (CoordinatesArray coorArray : pathsArray.getList()) {
                        if (pieces[i][j].getColor() == CheckerColor.WHITE && coorArray.getNumOfAttacks() > 0)
                            canWhiteAttack = true;
                        if (pieces[i][j].getColor() == CheckerColor.BLACK && coorArray.getNumOfAttacks() > 0)
                            canBlackAttack = true;
                    }
                }
            }
        }

        if (canWhiteAttack || canBlackAttack) {
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
                        if (pieces[i][j].getColor() == CheckerColor.WHITE && canWhiteAttack && coorArray.getNumOfAttacks() == 0)
                            coorToRemove.add(coorArray);
                        if (pieces[i][j].getColor() == CheckerColor.BLACK && canBlackAttack &&coorArray.getNumOfAttacks() == 0)
                            coorToRemove.add(coorArray);
                    }
                    for (CoordinatesArray coorArray : coorToRemove) {
                        pathsArray.getList().remove(coorArray);
                    }
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see org.checkers.boards.Board#copy()
     */
    @Override
    public Board copy() {
        return new ThaiBoard(this);
    }
}
