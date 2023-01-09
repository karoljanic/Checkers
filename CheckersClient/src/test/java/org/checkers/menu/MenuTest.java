package org.checkers.menu;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class MenuTest {
    private static Menu menuModel;

    @Before
    public void initializeMenu() {
        final ArrayList<String> buttonNames = new ArrayList<>();
        buttonNames.add("AAA");
        buttonNames.add("BBB");
        buttonNames.add("CCC");

        menuModel = new Menu(buttonNames);
    }

    @Test
    public void testInitializedFields() {
        final ArrayList<String> checkerTypes = menuModel.getCheckersTypes();

        assertEquals(3, checkerTypes.size());
        assertEquals("AAA", checkerTypes.get(0));
        assertEquals("BBB", checkerTypes.get(1));
        assertEquals("CCC", checkerTypes.get(2));
    }
}
