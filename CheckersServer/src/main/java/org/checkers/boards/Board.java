package org.checkers.boards;

import org.checkers.piece.coordinate.Coordinate;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.Piece;
import org.checkers.piece.coordinate.PathsArray;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.CheckerType;

/**
 * klasa reprezentuje planszę do gry w warcaby
 */
public abstract class Board {
    /**
     *przechowuje rozmiar planszy, np. size=10 oznacza planszę 10x10
     */
    protected final int size;
    /**
     *tablica pionków rozmiaru [size][size], null jeśli na danym polu nic nie ma
     */
    protected final Piece[][] pieces;
    /**
     *tablica możliwych ruchów dla białych pionków
     */
    protected PathsArray[][] currentPossibleMovesForWhite;
    /**
     *tablica możliwych ruchów dla czarnych pionków
     */
    protected PathsArray[][] currentPossibleMovesForBlack;

    /**
     * @param size rozmiar planszy
     * konstruktor ustawia atrybuty klasy Board
     */
    public Board(int size) {
        this.size = size;

        pieces = new Piece[size][size];
        currentPossibleMovesForWhite = new PathsArray[size][size];
        currentPossibleMovesForBlack = new PathsArray[size][size];
    }

    /**
     * @param board instancja klasy Board
     * konstruktor kopiuje obiekt podany jako parametr do nowego
     */
    public Board(Board board) {
        this.size = board.size;
        this.pieces = new Piece[board.size][board.size];
        this.currentPossibleMovesForWhite = new PathsArray[board.size][board.size];
        this.currentPossibleMovesForBlack = new PathsArray[board.size][board.size];

        for(int i = 0; i < board.size; i++) {
            for(int j = 0; j < board.size; j++) {
                if(board.pieces[i][j] == null)
                    this.pieces[i][j] = null;
                else
                    this.pieces[i][j] = new Piece(board.pieces[i][j]);

                if(board.currentPossibleMovesForWhite[i][j] == null)
                    this.currentPossibleMovesForWhite[i][j] = null;
                else
                    this.currentPossibleMovesForWhite[i][j] = new PathsArray(board.currentPossibleMovesForWhite[i][j]);

                if(board.currentPossibleMovesForBlack[i][j] == null)
                    this.currentPossibleMovesForBlack[i][j] = null;
                else
                    this.currentPossibleMovesForBlack[i][j] = new PathsArray(board.currentPossibleMovesForBlack[i][j]);
            }
        }
    }

    /**
     * @return rozmiar planszy
     */
    public int getSize() {
        return size;
    }

    /**
     * @param color kolor, dla którego mają być zwrócone pionki
     * @return tablica pionków podanego koloru
     */
    public CoordinatesArray getPieces(CheckerColor color) {
        CoordinatesArray result = new CoordinatesArray();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(pieces[i][j] != null && pieces[i][j].getColor() == color) {
                    result.add(pieces[i][j].getX(), pieces[i][j].getY());
                }
            }
        }

        return result;
    }

    /**
     * @param x współrzędna x-owa pionka
     * @param y współrzędna y-owa pionka
     * funkcja usuwa pionek o podanych współrzędnych
     */
    public void removePiece(int x, int y) {
        pieces[x][y] = null;
    }

    /**
     * @param x współrzędna x-owa pola
     * @param y współrzędna y-owa pola
     * @return true, jeśli na podanym polu jest pionek, w przeciwnym wypadku false
     */
    public boolean coordinateIsFree(int x, int y) {
        if(x < 0 || x >= size || y < 0 || y >= size)
            return false;

        return pieces[x][y] == null;
    }

    /**
     * @param x współrzędna x-owa pola
     * @param y współrzędna y-owa pola
     * @param color kolor, dla którego sprawdzamy pole
     * @return true, jeśli na podanym polu jest pionek danego koloru, w przeciwnym wypadku false
     */
    public boolean coordinateIsWithPiece(int x, int y, CheckerColor color) {
        if(coordinateIsFree(x, y))
            return false;

        return pieces[x][y].getColor() == color;
    }

    /**
     * @param x współrzędna x-owa pionka
     * @param y współrzędna y-owa pionka
     * @return true, jeśli na podanym polu jest pionek, który jest damką, w przeciwnym wypadku false
     */
    public boolean isKing(int x, int y) {
        if(pieces[x][y] != null)
            return pieces[x][y].getType() == CheckerType.KING;

        return false;
    }

    /**
     * @param checkerColor kolor pionków
     * @return tablica możliwych ruchów dla pionków podanego koloru
     */
    public PathsArray[][] getPossibleMoves(CheckerColor checkerColor) {
        if(checkerColor == CheckerColor.WHITE)
            return currentPossibleMovesForWhite;
        return currentPossibleMovesForBlack;
    }

    /**
     * @param x1 początkowa x-owa współrzędna pionka
     * @param y1 początkowa y-owa współrzędna pionka
     * @param x2 końcowa x-owa współrzędna pionka
     * @param y2 końcowa y-owa współrzędna pionka
     * @param checkerColor kolor pionka na polu (x1, y1)
     * @return możliwy ruch z pola (x1, y1) na pole (x2, y2) dla pionka podanego koloru
     */
    public CoordinatesArray getPossibleMove(int x1, int y1, int x2, int y2, CheckerColor checkerColor) {
        PathsArray paths;
        if(checkerColor == CheckerColor.WHITE)
            paths = currentPossibleMovesForWhite[x1][y1];
        else
            paths = currentPossibleMovesForBlack[x1][y1];

        CoordinatesArray resultPath = null;
        for(CoordinatesArray path: paths.getList()) {
            Coordinate lastCoordinate = path.getList().get(path.size() - 1);
            if(lastCoordinate.getX() == x2 && lastCoordinate.getY() == y2) {
                resultPath = path;
            }
        }

        return resultPath;
    }

    /**
     * @param x1 początkowa x-owa współrzędna pionka
     * @param y1 początkowa y-owa współrzędna pionka
     * @param x2 końcowa x-owa współrzędna pionka
     * @param y2 końcowa y-owa współrzędna pionka
     * @param color kolor pionka wykonującego ruch
     * funkcja obsługuje ruch pionka na planszy, tj. zmienia jego pozycję i ewentualnie robi go damką
     */
    public void move(int x1, int y1, int x2, int y2, CheckerColor color) {
        pieces[x2][y2] = new Piece(pieces[x1][y1]);
        pieces[x2][y2].moveTo(x2, y2);

        if(color == CheckerColor.WHITE) {
            if(y2 == size - 1) {
                pieces[x2][y2].makeKing();
            }
        }
        else {
            if(y2 == 0) {
                pieces[x2][y2].makeKing();
            }
        }

        pieces[x1][y1] = null;
    }

    /**
     * @return "white", jeśli wygrał biały; "black", jeśli czarny; "draw", jeśli jest remis; null w przypadku niezakończonej gry
     * funkcja sprawdza, czy gra się skończyła i jeśli tak, to zwraca końcowy status gry
     */
    public String whoWins() {
        generatePossibleMoves();
        int num_of_white_pieces = 0, num_of_black_pieces = 0;
        int num_of_white_poss = 0, num_of_black_poss = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (pieces[i][j] != null) {
                    if (pieces[i][j].getColor() == CheckerColor.WHITE) {
                        num_of_white_pieces++;
                        num_of_white_poss += currentPossibleMovesForWhite[i][j].getList().size();
                    }
                    else {
                        num_of_black_pieces++;
                        num_of_black_poss += currentPossibleMovesForBlack[i][j].getList().size();
                    }
                }
            }
        }

        if (num_of_white_poss == 0 && num_of_black_poss == 0)
            return "draw";

        if (num_of_white_pieces == 0 || num_of_white_poss == 0)
            return "black";
        if (num_of_black_pieces == 0 || num_of_black_poss == 0)
            return "white";

        return null;
    }

    /**
     * funkcja generuje możliwe ruchy dla danej odmiany gry
     */
    public abstract void generatePossibleMoves();

    /**
     * @return kopia obecnej planszy
     * funkcja kopiuje obecną planszę
     */
    public abstract Board copy();
}
