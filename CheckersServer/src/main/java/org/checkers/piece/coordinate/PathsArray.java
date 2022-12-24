package org.checkers.piece.coordinate;

import java.util.ArrayList;

public class PathsArray {
    private final ArrayList<CoordinatesArray> paths;

    public PathsArray() {
        paths = new ArrayList<>();
    }

    public PathsArray(PathsArray pathsArray) {
        this.paths = new ArrayList<>();
        for(CoordinatesArray coordinatesArray: pathsArray.getList())
            this.paths.add(new CoordinatesArray(coordinatesArray));
    }

    public int size() {
        return paths.size();
    }

    public void add(CoordinatesArray path) {
        paths.add(path);
    }

    public void add(PathsArray pathsArray) {
        paths.addAll(pathsArray.paths);
    }

    public ArrayList<CoordinatesArray> getList() {
        return paths;
    }
}
