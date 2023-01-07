package org.checkers.piece.coordinate;

import java.util.ArrayList;

/**
 * klasa przechowuje możliwe ruchy dla danego pionka
 */
public class PathsArray {
    /**
     * tablica możliwych ruchów
     */
    private final ArrayList<CoordinatesArray> paths;

    /**
     * domyślny konstruktor klasy PathsArray
     */
    public PathsArray() {
        paths = new ArrayList<>();
    }

    /**
     * @param pathsArray obiekt do skopiowania
     * konstruktor kopjuje podany obiekt
     */
    public PathsArray(PathsArray pathsArray) {
        this.paths = new ArrayList<>();
        for(CoordinatesArray coordinatesArray: pathsArray.getList())
            this.paths.add(new CoordinatesArray(coordinatesArray));
    }

    /**
     * @return liczba możliwych ruchów
     */
    public int size() {
        return paths.size();
    }

    /**
     * @param path nowy ruch
     * funkcja dodaje nowy możliwy ruch
     */
    public void add(CoordinatesArray path) {
        paths.add(path);
    }

    /**
     * @param pathsArray obiekt do sklejenia
     * funkcja skleja dwa obiekty
     */
    public void add(PathsArray pathsArray) {
        paths.addAll(pathsArray.paths);
    }

    /**
     * @return lista możliwych ruchów
     */
    public ArrayList<CoordinatesArray> getList() {
        return paths;
    }
}
