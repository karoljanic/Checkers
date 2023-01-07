package org.checkers.piece.coordinate;

/**
 * klasa reprezentuje pole na planszy
 */
public class Coordinate {
    private final int x;
    private final int y;

    /**
     * @param x x-owa współrzędna
     * @param y y-owa współrzędna
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinate instancja klasy do skopiowania
     * konstruktor tworzy nowy obiekt klasy Coordinate będący kopią podanego
     */
    public Coordinate(Coordinate coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    /**
     * @return x-owa współrzędna pola
     */
    public int getX() { return x; }

    /**
     * @return y-owa współrzędna pola
     */
    public int getY() { return y; }
}
