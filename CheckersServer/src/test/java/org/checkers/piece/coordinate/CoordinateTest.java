package org.checkers.piece.coordinate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {
    @Test
    public void testCoordinateCreating() {
        Coordinate coordinate = new Coordinate(12, 18);

        assertEquals(12, coordinate.getX());
        assertEquals(18, coordinate.getY());
    }

    @Test
    public void testCoordinateCopying() {
        Coordinate coordinate = new Coordinate(1, 0);
        Coordinate coordinateCopy = new Coordinate(coordinate);

        assertEquals(1, coordinateCopy.getX());
        assertEquals(0, coordinateCopy.getY());
    }
}
