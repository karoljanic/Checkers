package org.checkers.boards;

import org.checkers.utils.CheckerColor;

public class ThaiBoard extends Board {

    public static final int THAI_BOARD_SIZE = 8;

    public ThaiBoard() {
        super(THAI_BOARD_SIZE);
    }

    @Override
    protected void initializePieces() {
        //insert white pieces
        for (int i = 0; i <= 1; i++)
            for (int j = (i % 2 == 0 ? 0 : 1); j < 8; j += 2)
                pieces[j][i] = new Piece(j, i, CheckerColor.WHITE);
        //insert black pieces
        for (int i = 7; i >= 6; i--)
            for (int j = (i % 2 == 0 ? 0 : 1); j < 8; j += 2)
                pieces[j][i]  = new Piece(j, i, CheckerColor.BLACK);
    }

    @Override
    public void generatePossibleMoves() {
        /*
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                currentPossibleMoves[i][j] = new ArrayList<ArrayList<Point>>();

                Piece piece = pieces[i][j];
                if (piece == null)
                    continue;

                int x = piece.getPosision().getX();
                int y = piece.getPosision().getY();
                CheckerColor color = piece.getColor();

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

                int verticalStep = 1;
                if (color.equals(CheckerColor.BLACK))
                    verticalStep = -1;

                if (pieces[x - 1][y + verticalStep] == null 
                    && x - 1 > 0 && y + verticalStep > 0 && y + verticalStep < size) {
                    ArrayList<Point> tempArrayList = new ArrayList<>();
                    tempArrayList.add(new Point(x, y));
                    tempArrayList.add(new Point(x - 1, y + verticalStep));
                    currentPossibleMoves[i][j].add(tempArrayList);
                }
                
                if (pieces[x + 1][y + verticalStep] == null 
                    && x + 1 < size && y + verticalStep > 0 && y + verticalStep < size) {
                    ArrayList<Point> tempArrayList = new ArrayList<>();
                    tempArrayList.add(new Point(x, y));
                    tempArrayList.add(new Point(x + 1, y + verticalStep));
                    currentPossibleMoves[i][j].add(tempArrayList);
                }

            }
        }
    */
    }

    @Override
    public boolean moveIsCorrect(int x1, int y1, int x2, int y2) {
        return true;
    }

    @Override
    public void move(int x1, int y1, int x2, int y2, CheckerColor whosMove) {

    }

}
