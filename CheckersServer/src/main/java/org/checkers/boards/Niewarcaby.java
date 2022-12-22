package org.checkers.boards;

import org.checkers.utils.CheckerColor;

public class Niewarcaby extends Board {

    public static final int NIEWARCABY_BOARD_SIZE = 8;

    public Niewarcaby() {
        super(NIEWARCABY_BOARD_SIZE);
    }

    @Override
    protected void initializePieces() {
        for(int i = 0; i < 4; i++) {
            pieces[2 * i][7] = new Piece(2 * i, 7, CheckerColor.WHITE);
            pieces[2 * i][0] = new Piece(2 * i, 0, CheckerColor.BLACK);
        }
    }

    @Override
    public void generatePossibleMoves() {
        restartCurrentPossibleMovesArray();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(pieces[i][j] != null) {
                    System.out.println(pieces[i][j].getX() + "  " + pieces[i][j].getY());
                    if(pieces[i][j].getColor() == CheckerColor.WHITE)
                        currentPossibleMoves[i][j].add(new Piece(i, j-1, CheckerColor.WHITE));
                    else
                        currentPossibleMoves[i][j].add(new Piece(i, j+1, CheckerColor.BLACK));
                }
            }
        }
    }

    @Override
    public boolean moveIsCorrect(int x1, int y1, int x2, int y2) {
        return x1 == x2 && ((y2 - y1) == 1 || ((y1 - y2) == 1 ));
    }

    @Override
    public void move(int x1, int y1, int x2, int y2, CheckerColor whosMove) {
        pieces[x1][y1].move(x2, y2);
        pieces[x2][y2]= pieces[x1][y1];
        pieces[x1][y1] = null;
    }
}
