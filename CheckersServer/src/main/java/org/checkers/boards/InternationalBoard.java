package org.checkers.boards;

import org.checkers.utils.CheckerColor;

public class InternationalBoard extends Board {

    public static final int INTERNATIONAL_BOARD_SIZE = 10;

    public InternationalBoard() {
        super(INTERNATIONAL_BOARD_SIZE);
    }

    @Override
    protected void initializePieces() {
        //insert white pieces
        for (int i = 0; i <= 3; i++)
            for (int j = (i % 2 == 0 ? 0 : 1); j < 10; j += 2)
                pieces[j][i] = new Piece(j, i, CheckerColor.WHITE);
        //insert black pieces
        for (int i = 9; i >= 6; i--)
            for (int j = (i % 2 == 0 ? 0 : 1); j < 10; j += 2)
                pieces[j][i] = new Piece(j, i, CheckerColor.BLACK);
    }

    @Override
    public void generatePossibleMoves() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Piece piece = pieces[i][j];
                if (piece == null)
                    continue;

                int x = piece.getX();
                int y = piece.getY();
                CheckerColor color = piece.getColor();

                /*
                if (piece.getType().equals(CheckerType.KING)) {
                    int newX, newY;

                    //right-top direction
                    newX = x + 1;
                    newY = y + 1;
                    while (newX < size && newY < size && pieces[newX][newY] == null) {
                        ArrayList<Point> tempArrayList = new ArrayList<>();
                        tempArrayList.add(new Point(x, y));
                        tempArrayList.add(new Point(newX, newY));
                        currentPossibleMoves[i][j].add(tempArrayList);
                        newX += 1;
                        newY += 1;
                    }

                    //left-top direction
                    newX = x - 1;
                    newY = y + 1;
                    while (newX >= 0 && newY < size && pieces[newX][newY] == null) {
                        ArrayList<Point> tempArrayList = new ArrayList<>();
                        tempArrayList.add(new Point(x, y));
                        tempArrayList.add(new Point(newX, newY));
                        currentPossibleMoves[i][j].add(tempArrayList);
                        newX -= 1;
                        newY += 1;
                    }

                    //right-bottom direction
                    newX = x + 1;
                    newY = y - 1;
                    while (newX < size && newY >= 0 && pieces[newX][newY] == null) {
                        ArrayList<Point> tempArrayList = new ArrayList<>();
                        tempArrayList.add(new Point(x, y));
                        tempArrayList.add(new Point(newX, newY));
                        currentPossibleMoves[i][j].add(tempArrayList);
                        newX += 1;
                        newY -= 1;
                    }

                    //left-bottom direction
                    newX = x - 1;
                    newY = y - 1;
                    while (newX >= 0 && newY >= 0 && pieces[newX][newY] == null) {
                        ArrayList<Point> tempArrayList = new ArrayList<>();
                        tempArrayList.add(new Point(x, y));
                        tempArrayList.add(new Point(newX, newY));
                        currentPossibleMoves[i][j].add(tempArrayList);
                        newX -= 1;
                        newY -= 1;
                    }

                    continue;
                }
                */

                int verticalStep = 1;
                if (color.equals(CheckerColor.BLACK))
                    verticalStep = -1;

                if (moveIsCorrect(x, y, x - 1, y + verticalStep)) {
                    currentPossibleMoves[i][j].add(new Piece(x - 1, y + verticalStep, color));
                }
                
                if (moveIsCorrect(x, y, x + 1, y + verticalStep)) {
                    currentPossibleMoves[i][j].add(new Piece(x + 1, y + verticalStep, color));
                }
            }
        }
    }

    @Override
    public boolean moveIsCorrect(int x1, int y1, int x2, int y2) {
        if (x2 >= 0 && x2 < size && y2 >= 0 && y2 < size && pieces[x2][y2] == null) {
            return true;
        }

        return false;
    }

    @Override
    public void move(int x1, int y1, int x2, int y2, CheckerColor whosMove) {
        pieces[x1][y1].move(x2, y2);
        pieces[x2][y2] = pieces[x1][y1].clone();
        pieces[x1][y1] = null;
    }

}
