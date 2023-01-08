package org.checkers.board;

import javafx.util.Pair;
import org.checkers.utils.CheckerColor;

import java.util.ArrayList;

/**
 * klasa reprezentuje planszę do gry w warcaby
 */
public class Board {
    /**
     * rozmiar planszy, np. size=8 oznacza plaszę 8x8
     */
    private int size;
    /**
     * pionki aktualnie znajdujące się na planszy
     */
    private Piece[][] pieces;

    /**
     * true dla białych pionków, false dla czarnych
     */
    private boolean isHost;

    /**
     * @param size nowy rozmiar planszy
     * funkcja zmienia rozmiar planszy
     */
    public void setSize(int size) {
        this.size = size;
        pieces = new Piece[size][size];
    }

    /**
     * @return informacja o kolorze pionków gracza
     */
    public boolean isHost() { return isHost; }

    /**
     * @param isHost informacja o kolorze pionków gracza
     */
    public void setHost(boolean isHost) { this.isHost = isHost; }

    /**
     * @return rozmiar planszy
     */
    public int getSize() { return size; }

    /**
     * @param x x-owa współrzędna nowego pionka
     * @param y y-owa współrzędna nowego pionka
     * funkcja dodaje nowy pionek białego koloru
     */
    public void setWhitePiece(int x, int y) { pieces[x][y] = new Piece(x, y, CheckerColor.WHITE, new ArrayList<>()); }

    /**
     * @param x x-owa współrzędna nowego pionka
     * @param y y-owa współrzędna nowego pionka
     * funkcja dodaje nowy pionek czarnego koloru
     */
    public void setBlackPiece(int x, int y) { pieces[x][y] = new Piece(x, y, CheckerColor.BLACK, new ArrayList<>()); }

    /**
     * @param x x-owa współrzędna pionka do usunięcia
     * @param y y-owa współrzędna pionka do usunięcia
     * funkcja usuwa pionek o podanych współrzędnych
     */
    public void removePiece(int x, int y) { pieces[x][y] = null; }

    /**
     * @param x x-owa współrzędna pionka
     * @param y y-owa współrzędna pionka
     * @return kolor pionka na podanym polu
     */
    public CheckerColor getCheckerColor(int x, int y) {
        return pieces[x][y].getColor();
    }

    /**
     * @return tablica dwuwymiarowa pionków na planszy
     */
    public Piece[][] getPieces() { return pieces; }

    /**
     * @param x x-owa współrzędna pionka
     * @param y y-owa współrzędna pionka
     * @param possibleMove możliwe ruchy dla pionka
     * funkcja dodaje możliwe ruchy dla danego pionka
     */
    public void addPossibleMove(int x, int y, ArrayList<Pair<Integer, Integer>> possibleMove) {
        if(pieces[x][y] != null) {
            pieces[x][y].addPossibleMove(possibleMove);
        }
    }

    /**
     * @param x x-owa współrzędna pionka
     * @param y y-owa współrzędna pionka
     * funkcja usuwa możliwe ruchy dla podanego pionka
     */
    public void removePossibleMoves(int x, int y) {
        if(pieces[x][y] != null) {
            pieces[x][y].clearPossibleMoves();
        }
    }

    /**
     * @param x x-owa współrzędna pionka
     * @param y y-owa współrzędna pionka
     * funkcja zmienia podany pionek na damkę
     */
    public void makeKing(int x, int y) {
        pieces[x][y].changeToKing();
    }
}
