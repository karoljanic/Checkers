package org.checkers.menu;

import java.util.ArrayList;

public class Menu {
    private final ArrayList<String> checkersTypes;

    Menu(ArrayList<String> checkersTypes) {
        this.checkersTypes = checkersTypes;
    }

    public ArrayList<String> getCheckersTypes() {
        return checkersTypes;
    }
}
