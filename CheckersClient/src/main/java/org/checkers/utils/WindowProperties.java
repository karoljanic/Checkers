package org.checkers.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class WindowProperties {
    static public double calculateWindowStageSize() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double height = primaryScreenBounds.getHeight();
        double width = primaryScreenBounds.getWidth();

        return Math.min(height, width);
    }
}
