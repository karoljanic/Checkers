package org.checkers.menu;

import java.util.ArrayList;

/**
 * klasa reprezentująca początkowe menu do wyboru rodzaju gry
 */
public class Menu {
    /**
     * możliwe rodzaje gry
     */
    private final ArrayList<String> checkersTypes;

    /**
     * @param checkersTypes rodzaj gry
     * konstruktor ustawia rodzaje gry
     */
    Menu(ArrayList<String> checkersTypes) {
        this.checkersTypes = checkersTypes;
    }

    /**
     * @return możliwe rodzaje gry
     */
    public ArrayList<String> getCheckersTypes() {
        return checkersTypes;
    }
}
