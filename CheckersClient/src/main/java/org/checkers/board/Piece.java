package org.checkers.board;

import javafx.util.Pair;
import org.checkers.utils.CheckerColor;
import org.checkers.utils.CheckerType;

import java.util.ArrayList;

/**
 * klasa reprezentuje pionek na planszy
 */
public class Piece {
    /**
     * x-owa współrzędna pionka
     */
    private int x;
    /**
     * y-owa współrzędna pionka
     */
    private int y;
    /**
     * kolor pionka
     */
    private CheckerColor color;
    /**
     * typ pionka
     */
    private CheckerType type;
    /**
     * możliwe ruchy dla pionka
     */
    private ArrayList<ArrayList<Pair<Integer, Integer>>> possibleMoves;

    /**
     * @param x x-owa współrzędna pionka
     * @param y y-owa współrzędna pionka
     * @param color kolor pionka
     * @param possibleMoves możliwe ruchy pionka
     * konstruktor ustawia niezbędne parametry dla nowego obiektu
     */
    public Piece(int x, int y, CheckerColor color, ArrayList<ArrayList<Pair<Integer, Integer>>> possibleMoves) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.possibleMoves = possibleMoves;
        this.type = CheckerType.NORMAL;
    }

    /**
     * @return x-owa współrzędna pionka
     */
    public int getX() { return x; }

    /**
     * @return y-owa współrzędna pionka
     */
    public int getY() { return y; }

    /**
     * @return kolor pionka
     */
    public CheckerColor getColor() { return color; }

    /**
     * @return typ pionka
     */
    public CheckerType getType() { return type; }

    /**
     * @return możliwe ruchy dla pionka
     */
    public ArrayList<ArrayList<Pair<Integer, Integer>>> getPossibleMoves() {
        return possibleMoves;
    }

    /**
     * funkcja usuwa możliwe ruchy dla pionka
     */
    public void clearPossibleMoves() {
        possibleMoves = new ArrayList<>();
    }

    /**
     * @param possibleMove możliwy ruch
     * funkcja dodaje możliwy ruch
     */
    public void addPossibleMove(ArrayList<Pair<Integer, Integer>> possibleMove) {
        possibleMoves.add(possibleMove);
    }

    /**
     * funkcja zmienia pionek na damkę
     */
    public void changeToKing() {
        type = CheckerType.KING;
    }

    /**
     * @return true, jeśli pionek jest damką
     */
    public boolean isKing() {
        return type == CheckerType.KING;
    }
}
