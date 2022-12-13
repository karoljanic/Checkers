package org.checkers.boards;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.checkers.boards.Piece.Color;
import org.checkers.boards.Piece.Type;

public class InternationalBoard extends Board {

    private static final int SIZE = 10;

    @Override
    protected void initializePieces() {
        //insert white pieces
        for (int i = 0; i <= 3; i++)
            for (int j = 0; j < SIZE; j += 2)
                pieces.add(new Piece(new Point2D.Double(j, i), Color.WHITE));
        //insert black pieces
        for (int i = 9; i >= 6; i--)
            for (int j = 1; j < SIZE; j += 2)
                pieces.add(new Piece(new Point2D.Double(j, i), Color.BLACK));
    }

    @Override
    public ArrayList<ArrayList<Point2D>> getPossibleMoves(Color color) {

        for (Piece piece : pieces) {
            if (!piece.getColor().equals(color))
                continue;

            int index = pieces.indexOf(piece);
            Point2D position = piece.getPosision();

            if (piece.getType().equals(Type.KING)) {
                int newX, newY;

                //right-top direction
                newX = (int)(position.getX() + 1);
                newY = (int)(position.getY() + 1);
                while (newX < SIZE && newY < SIZE
                    && findPiece(new Point2D.Double(newX, newY), Color.WHITE) == null
                    && findPiece(new Point2D.Double(newX, newY), Color.BLACK) == null) {
                    currentPossibleMoves.get(index).add(new Point2D.Double(newX, newY));
                    newX += 1;
                    newY += 1;
                }

                //left-top direction
                newX = (int)(position.getX() - 1);
                newY = (int)(position.getY() + 1);
                while (newX >= 0 && newY < SIZE
                    && findPiece(new Point2D.Double(newX, newY), Color.WHITE) == null
                    && findPiece(new Point2D.Double(newX, newY), Color.BLACK) == null) {
                    currentPossibleMoves.get(index).add(new Point2D.Double(newX, newY));
                    newX -= 1;
                    newY += 1;
                }

                //right-bottom direction
                newX = (int)(position.getX() + 1);
                newY = (int)(position.getY() - 1);
                while (newX < SIZE && newY >= 0
                    && findPiece(new Point2D.Double(newX, newY), Color.WHITE) == null
                    && findPiece(new Point2D.Double(newX, newY), Color.BLACK) == null) {
                    currentPossibleMoves.get(index).add(new Point2D.Double(newX, newY));
                    newX += 1;
                    newY -= 1;
                }

                //left-bottom direction
                newX = (int)(position.getX() - 1);
                newY = (int)(position.getY() - 1);
                while (newX >= 0 && newY >= 0
                    && findPiece(new Point2D.Double(newX, newY), Color.WHITE) == null
                    && findPiece(new Point2D.Double(newX, newY), Color.BLACK) == null) {
                    currentPossibleMoves.get(index).add(new Point2D.Double(newX, newY));
                    newX -= 1;
                    newY -= 1;
                }

                continue;
            }

            int verticalStep = 1;
            if (color.equals(Color.BLACK))
                verticalStep = -1;

            if (findPiece(new Point2D.Double(position.getX() - 1, position.getY() + verticalStep), Color.WHITE) == null
                && findPiece(new Point2D.Double(position.getX() - 1, position.getY() + verticalStep), Color.BLACK) == null
                && position.getX() - 1 > 0 && position.getY() + verticalStep > 0 && position.getY() + verticalStep < SIZE)
                currentPossibleMoves.get(index).add(new Point2D.Double(position.getX() - 1, position.getY() + verticalStep));
            
            if (findPiece(new Point2D.Double(position.getX() + 1, position.getY() + verticalStep), Color.WHITE) == null
                && findPiece(new Point2D.Double(position.getX() + 1, position.getY() + verticalStep), Color.BLACK) == null
                && position.getX() + 1 < SIZE && position.getY() + verticalStep > 0 && position.getY() + verticalStep < SIZE)
                currentPossibleMoves.get(index).add(new Point2D.Double(position.getX() + 1, position.getY() + verticalStep));

        }

        return currentPossibleMoves;
    }
    
}
