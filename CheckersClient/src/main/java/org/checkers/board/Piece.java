package org.checkers.board;

import javafx.util.Pair;
import org.checkers.utils.CheckerColor;
import org.checkers.utils.CheckerType;

import java.util.ArrayList;

public class Piece {
    private int x;
    private int y;
    private CheckerColor color;

    private CheckerType type;

    private ArrayList<Pair<Integer, Integer>> possibleMoves;

    public Piece(int x, int y, CheckerColor color, ArrayList<Pair<Integer, Integer>> possibleMoves) {
        this.x = x;
        this.y=  y;
        this.color = color;
        this.possibleMoves = possibleMoves;
        this.type = CheckerType.NORMAL;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public CheckerColor getColor() { return color; }

    public CheckerType getType() { return type; }

    public ArrayList<Pair<Integer, Integer>> getPossibleMoves() {
        return possibleMoves;
    }

    public void clearPossibleMoves() {
        possibleMoves = new ArrayList<>();
    }

    public void addPossibleMove(int x, int y) {
        possibleMoves.add(new Pair<>(x, y));
    }

    public void changeToKing() {
        type = CheckerType.KING;
    }
}
