package org.checkers.piece.coordinate;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinatesArrayTest {
    @Test
    public void testEmptyCoordinatesArrayCreating() {
        CoordinatesArray coordinatesArray = new CoordinatesArray();

        assertEquals(0, coordinatesArray.size());

        ArrayList<Coordinate> elements = coordinatesArray.getList();
        assertNotNull(elements);
        assertEquals(0, elements.size());
    }

    @Test
    public void testOneElementCoordinatesArrayCreating() {
        CoordinatesArray coordinatesArray = new CoordinatesArray(2, 0);

        assertEquals(1, coordinatesArray.size());

        ArrayList<Coordinate> elements = coordinatesArray.getList();
        assertNotNull(elements);
        assertEquals(1, elements.size());

        assertEquals(2, elements.get(0).getX());
        assertEquals(0, elements.get(0).getY());
    }

    @Test
    public void testCoordinatesArrayCopying() {
        CoordinatesArray coordinatesArray = new CoordinatesArray(3, 1);
        CoordinatesArray coordinatesArrayCopy = new CoordinatesArray(coordinatesArray);

        assertEquals(1, coordinatesArrayCopy.size());

        ArrayList<Coordinate> elements = coordinatesArrayCopy.getList();

        assertEquals(3, elements.get(0).getX());
        assertEquals(1, elements.get(0).getY());
    }

    @Test
    public void testAttacksNumberTest() {
        CoordinatesArray coordinatesArray = new CoordinatesArray();
        assertEquals(0, coordinatesArray.getNumOfAttacks());

        coordinatesArray.addAttack();
        assertEquals(1, coordinatesArray.getNumOfAttacks());
    }

    @Test
    public void testAddingElementsTest() {
        CoordinatesArray coordinatesArray = new CoordinatesArray();
        assertEquals(0, coordinatesArray.size());

        coordinatesArray.add(2, 1);
        assertEquals(1, coordinatesArray.size());

        CoordinatesArray subCoordinatesArray = new CoordinatesArray(5, 10);
        coordinatesArray.add(subCoordinatesArray);
        assertEquals(2, coordinatesArray.size());
    }
}
