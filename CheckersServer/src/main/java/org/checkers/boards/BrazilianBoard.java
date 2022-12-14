package org.checkers.boards;

import java.util.ArrayList;

import org.checkers.boards.elements.Piece;
import org.checkers.boards.elements.Point;
import org.checkers.boards.elements.Piece.Color;
import org.checkers.boards.elements.Piece.Type;

public class BrazilianBoard extends Board {

    public static final int SIZE = 8;

    @Override
    protected void initializePieces() {
        //insert white pieces
        for (int i = 0; i <= 2; i++)
            for (int j = 0; j < 8; j += 2)
                pieces[j][i] = new Piece(new Point(j, i), Color.WHITE);
        //insert black pieces
        for (int i = 7; i >= 5; i--)
            for (int j = 1; j < 8; j += 2)
                pieces[j][i] = new Piece(new Point(j, i), Color.BLACK);
        
    }

    @Override
    public ArrayList<ArrayList<Point>>[][] getPossibleMoves(Color color) {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                Piece piece = pieces[i][j];

                if (!piece.getColor().equals(color))
                    continue;

                int x = piece.getPosision().getX();
                int y = piece.getPosision().getY();

                if (piece.getType().equals(Type.KING)) {
                    int newX, newY;

                    //right-top direction
                    newX = x + 1;
                    newY = y + 1;
                    while (newX < SIZE && newY < SIZE && pieces[newX][newY] == null) {
                        ArrayList<Point> tempArrayList = new ArrayList<>();
                        tempArrayList.add(new Point(newX, newY));
                        currentPossibleMoves[i][j].add(tempArrayList);
                        newX += 1;
                        newY += 1;
                    }

                    //left-top direction
                    newX = x - 1;
                    newY = y + 1;
                    while (newX >= 0 && newY < SIZE && pieces[newX][newY] == null) {
                        ArrayList<Point> tempArrayList = new ArrayList<>();
                        tempArrayList.add(new Point(newX, newY));
                        currentPossibleMoves[i][j].add(tempArrayList);
                        newX -= 1;
                        newY += 1;
                    }

                    //right-bottom direction
                    newX = x + 1;
                    newY = y - 1;
                    while (newX < SIZE && newY >= 0 && pieces[newX][newY] == null) {
                        ArrayList<Point> tempArrayList = new ArrayList<>();
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
                        tempArrayList.add(new Point(newX, newY));
                        currentPossibleMoves[i][j].add(tempArrayList);
                        newX -= 1;
                        newY -= 1;
                    }

                    continue;
                }

                int verticalStep = 1;
                if (color.equals(Color.BLACK))
                    verticalStep = -1;

                if (pieces[x - 1][y + verticalStep] == null 
                    && x - 1 > 0 && y + verticalStep > 0 && y + verticalStep < SIZE) {
                    ArrayList<Point> tempArrayList = new ArrayList<>();
                    tempArrayList.add(new Point(x - 1, y + verticalStep));
                    currentPossibleMoves[i][j].add(tempArrayList);
                }
                
                if (pieces[x + 1][y + verticalStep] == null 
                    && x + 1 < SIZE && y + verticalStep > 0 && y + verticalStep < SIZE) {
                    ArrayList<Point> tempArrayList = new ArrayList<>();
                    tempArrayList.add(new Point(x + 1, y + verticalStep));
                    currentPossibleMoves[i][j].add(tempArrayList);
                }

            }
        }

        return currentPossibleMoves;
    }
    
}
