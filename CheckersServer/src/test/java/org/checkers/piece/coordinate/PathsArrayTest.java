package org.checkers.piece.coordinate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PathsArrayTest {
    @Test
    public void testPathsArrayCreating() {
        PathsArray pathsArray = new PathsArray();
        assertEquals(0, pathsArray.size());
    }

    @Test
    public void testPathsArrayCopying() {
        PathsArray pathsArray = new PathsArray();
        pathsArray.add(new CoordinatesArray(9, 0));
        PathsArray pathsArrayCopy = new PathsArray(pathsArray);
        assertEquals(1, pathsArrayCopy.size());
    }

    @Test
    public void testAddingPathsToArray() {
        PathsArray pathsArray = new PathsArray();
        pathsArray.add(new CoordinatesArray(2, 0));
        assertEquals(1, pathsArray.size());

        PathsArray subPathsArray = new PathsArray();
        subPathsArray.add(new CoordinatesArray(7, 1));
        pathsArray.add(subPathsArray);
        assertEquals(2, pathsArray.size());
    }
}
