package org.checkers.piece.coordinate;

import java.util.ArrayList;

public class CoordinatesArray {
    private final ArrayList<Coordinate> coordinates;

    public CoordinatesArray() {
        coordinates = new ArrayList<>();
    }

    public CoordinatesArray(int x, int y) {
        coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(x, y));
    }

    public CoordinatesArray(CoordinatesArray coordinatesArray) {
        this.coordinates =new ArrayList<>();
        for(Coordinate coordinate: coordinatesArray.getList())
            this.coordinates.add(new Coordinate(coordinate));
    }

    public int size() {
        return coordinates.size();
    }

    public void add(int x, int y) {
        coordinates.add(new Coordinate(x, y));
    }

    public void add(CoordinatesArray coordinatesArray) {
        coordinates.addAll(coordinatesArray.coordinates);
    }

    public ArrayList<Coordinate> getList() {
        return coordinates;
    }
};