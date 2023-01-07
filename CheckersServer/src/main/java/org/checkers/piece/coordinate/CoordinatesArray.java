package org.checkers.piece.coordinate;

import java.util.ArrayList;

/**
 * klasa jest tablicą przechowującą pola planszy, symulacja ruchu pionka
 */
public class CoordinatesArray {
    /**
     * tablica kolejnych pól przy ruchu
     */
    private final ArrayList<Coordinate> coordinates;
    /**
     * zmienna przechowuje liczbę bić w danym ruchu
     */
    private int numOfAttacks = 0;

    /**
     * domyślny konstruktor klasy CoordinatesArray
     */
    public CoordinatesArray() {
        coordinates = new ArrayList<>();
    }

    /**
     * @param x x-owa współrzędna pierwszego pola
     * @param y y-owa współrzędna pierwszego pola
     * konstruktor ustawia pierwsze pole w danym ruchu na (x, y)
     */
    public CoordinatesArray(int x, int y) {
        coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(x, y));
    }

    /**
     * @param coordinatesArray instacja klasy do skopiowania
     * konstruktor kopiuje podany jako argument obiekt
     */
    public CoordinatesArray(CoordinatesArray coordinatesArray) {
        this.coordinates = new ArrayList<>();
        for(Coordinate coordinate: coordinatesArray.getList())
            this.coordinates.add(new Coordinate(coordinate));
        this.numOfAttacks = coordinatesArray.getNumOfAttacks();
    }

    /**
     * funkcja dodaje bicie do ruchu
     */
    public void addAttack() {
        numOfAttacks++;
    }

    /**
     * @return liczba bić w danym ruchu
     */
    public int getNumOfAttacks() {
        return numOfAttacks;
    }

    /**
     * @return liczba pól w danym ruchu
     */
    public int size() {
        return coordinates.size();
    }

    /**
     * @param x x-owa współrzędna nowego pola
     * @param y y-owa współrzędna nowego pola
     * funkcja dodaje nowe pole do ruchu
     */
    public void add(int x, int y) {
        coordinates.add(new Coordinate(x, y));
    }

    /**
     * @param coordinatesArray pola do dodania do ruchu
     * funkcja skleja dwa ruchy
     */
    public void add(CoordinatesArray coordinatesArray) {
        coordinates.addAll(coordinatesArray.coordinates);
        numOfAttacks += coordinatesArray.getNumOfAttacks();
    }

    /**
     * @return lista pól przy ruchu
     */
    public ArrayList<Coordinate> getList() {
        return coordinates;
    }
};